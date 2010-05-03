package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.application.mail.MailConfig;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.service.findings.RembrandtAsynchronousFindingManagerImpl;
import gov.nih.nci.rembrandt.service.findings.RembrandtTaskResult;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.DownloadEmailedReportHelper;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * The DownloadZipFileAction class is used to get the zip file 
 * <P>
 * @author mholck
 * @see org.apache.struts.action.Action
 */
public class DownloadReportFileAction extends Action
{
	private static Logger logger = Logger.getLogger(DownloadReportFileAction.class);
    private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private String dirPath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	private MailConfig mailConfigInstance = MailConfig.getInstance(ApplicationContext.GOV_NIH_NCI_REMBRANDT_PROPERTIES);
	
	/**
	 * execute is called when this action is posted to
	 * <P>
	 * @param mapping The ActionMapping for this action as configured in struts
	 * @param form The ActionForm that posted to this action if any
	 * @param request The HttpServletRequest for the current post
	 * @param response The HttpServletResponse for the current post
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		ActionForward forward = null;
		String reportName = (String)request.getParameter("reportName");	
		if(reportName == null){
			reportName = (String) request.getSession().getAttribute("emailFileName");
		}
		// Check if the user is logged in
		String logged = (String)request.getSession().getAttribute("logged");
		//if already logged in  and file exists than download file
		if ((logged != null  && (logged.equals("yes")))){
				
			UserCredentials credentials = (UserCredentials)request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS);
			        RembrandtAsynchronousFindingManagerImpl asynchronousFindingManagerImpl = new RembrandtAsynchronousFindingManagerImpl();
			        try {
						asynchronousFindingManagerImpl.retrieveResultsFromFile(request.getSession().getId(), reportName, credentials.getUserName(),request.getSession());
					} catch (FindingsQueryException e) {
						logger.error(e.getMessage());
					}
					// Set the forward
					forward = mapping.findForward("viewResults");			
					logger.debug("redirecting to download");	
			}
		else //if((logged == null  || !logged.equals("yes")))
		{
			// Set the forward
			request.getSession().setAttribute("emailFileName", reportName);
			logger.debug("redirecting to login");	
			forward = mapping.findForward("registration");	
		}
//		else{ //you do not have access to this study
//			logger.debug("YOU DO NOT HAVE ACCESS TO THIS STUDY, PLEASE APPLY FOR REGISTERED ACCESS!");
//			logger.debug("File does not exsist");
//			request.getSession().removeAttribute("filePath");
//			request.getSession().removeAttribute("emailQueryName");
//			forward = mapping.findForward("accessWarning");
//		}

		return forward;
	}
}
