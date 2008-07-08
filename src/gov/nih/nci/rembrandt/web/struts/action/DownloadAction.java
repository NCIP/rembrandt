package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.lookup.DownloadFileLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

public class DownloadAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(RefineQueryAction.class);
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	
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
		
		//parse the form, and use the API to start the download
		return  mapping.findForward("success");
	}

}
