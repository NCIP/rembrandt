package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.lookup.DownloadFileLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;
import gov.nih.nci.rembrandt.web.struts.form.DownloadForm;
import gov.nih.nci.caarray.domain.file.FileType;
import gov.nih.nci.caintegrator.application.download.DownloadStatus;
import gov.nih.nci.caintegrator.application.download.DownloadTask;
import gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager;
import gov.nih.nci.caintegrator.application.lists.ListItem;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.zip.FileNameGenerator;
import gov.nih.nci.rembrandt.download.caarray.RembrandtCaArrayFileDownloadManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class DownloadAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(RefineQueryAction.class);
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private CaArrayFileDownloadManager rbtCaArrayFileDownloadManager;
	private static int count = 0;
	public ActionForward setup(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		//prepopulate the fields for BRB downloads
		List fileList = LookupManager.getDownloadBRBFileList();
		if (fileList == null || fileList.isEmpty())	{
			request.setAttribute("downloadFileList", new ArrayList());
			return mapping.findForward("success");
		}
		List<DownloadFileLookup> downloadFileList = new ArrayList<DownloadFileLookup>();
		
		Collection<InstitutionDE> collection = InsitutionAccessHelper.getInsititutionCollection(request.getSession());

		for (int i = 0; i < fileList.size(); i++){
			DownloadFileLookup lookup = (DownloadFileLookup)fileList.get(i);
			if (lookup.getAccessCode().equals(new Long(8))){
				downloadFileList.add(lookup);
				continue;
			}
			for (Iterator it = collection.iterator(); it.hasNext();){
				InstitutionDE de = (InstitutionDE)it.next();
			
				if (lookup.getAccessCode().equals((Long)de.getValue())){
					downloadFileList.add(lookup);
				}
			}
		}
		request.getSession().setAttribute("downloadFileList", downloadFileList);
		
		//prepopulate the fields for caArray
		GroupRetriever groupRetriever = new GroupRetriever();
		List<LabelValueBean> al = new ArrayList<LabelValueBean>();
		al.addAll(groupRetriever.getClinicalGroupsCollectionNoPath(request.getSession()));
		//specifically remove only these values, not to effect the groupRetriever
		LabelValueBean tmp = new LabelValueBean("UNKNOWN", "UNKNOWN");
		al.remove(tmp);
		tmp = new LabelValueBean("ALL", "ALL");
		al.remove(tmp);
		tmp = new LabelValueBean("NON_TUMOR", "NON_TUMOR");
		al.remove(tmp);
		request.getSession().setAttribute("sampleGroupsList", al);
		
		return  mapping.findForward("success");
	}

	public ActionForward caarray(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		//parse the downloadForm, and use the API to start the download
		
		// 1: extract the samples from the group
		// 2: pass samples to the caARRAY API
		rbtCaArrayFileDownloadManager = RembrandtCaArrayFileDownloadManager.getInstance();
		rbtCaArrayFileDownloadManager.setBusinessCacheManager(ApplicationFactory.getBusinessTierCache());
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(100);
		taskExecutor.setQueueCapacity(400);
		taskExecutor.initialize();
		rbtCaArrayFileDownloadManager.setTaskExecutor(taskExecutor);
		//rbtCaArrayFileDownloadManager.executeDownloadStrategy(session, taskId, zipFileName, specimenList, type);
		DownloadForm dlForm = (DownloadForm)form;
		String groupNameList = dlForm.getGroupNameCompare();

		UserListBeanHelper helper = new UserListBeanHelper(request.getSession().getId());
		Set<String> patientIdset = new HashSet<String>();
  
		List<ListItem> listItemts = helper.getUserList(groupNameList).getListItems();            	
		List<String> specimenNames = null;
		for (ListItem item : listItemts) {
			patientIdset.add(item.getName());
		}               

		if(patientIdset != null && patientIdset.size()>0) {  
			// need to convert pt dids to the specimen ids
			specimenNames = LookupManager.getSpecimenNames(patientIdset);     
		}// end of if
		String tempName = groupNameList.toLowerCase();
		tempName = FileNameGenerator.generateUniqueFileName(tempName);
		String taskId = tempName + "_" + count++;
		FileType type = null;
		if (dlForm.getFileType().equals("CEL"))
			type = FileType.AFFYMETRIX_CEL;
		else if (dlForm.getFileType().equals("CHP"))
			type = FileType.AFFYMETRIX_CHP;

		
		rbtCaArrayFileDownloadManager.executeDownloadStrategy(
				request.getSession().getId(), 
				taskId,
				tempName + ".zip", 
				specimenNames, 
				type);

		return  mapping.findForward("success");
	}
	public ActionForward download(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		String dir = System.getProperty("rembrandt.caarray.download.output.zip.dir");
		String fileName = request.getParameter("file");
		String filePath = dir + "/"+ fileName;
		final ServletOutputStream out = response.getOutputStream(); 
		response.setContentType("application/octet-stream");
		
		response.setHeader( "Content-Disposition", "attachment; filename=\"" + fileName + "\"" );
		File file = null;
		try {
			file = new File(filePath);
			response.setContentLength( (int)file.length() );
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int bytesRead;
			while ((bytesRead = is.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
			is.close();
			out.close();
		}catch (IOException ioe){
			logger.error("IO exception in sending file " +  file.toString() + ioe.getMessage());
		}

		return  mapping.findForward("success");
	}
}
