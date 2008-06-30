package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.lookup.DownloadFileLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
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

public class DownloadAction extends Action {
	private static Logger logger = Logger.getLogger(RefineQueryAction.class);
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		List fileList = LookupManager.getDownloadBRBFileList();
		if (fileList == null || fileList.isEmpty())
		{
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
	return  mapping.findForward("success");
	}

}
