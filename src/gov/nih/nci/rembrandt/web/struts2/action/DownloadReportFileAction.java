/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.action;

import gov.nih.nci.caintegrator.application.mail.MailConfig;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.service.findings.RembrandtAsynchronousFindingManagerImpl;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * The DownloadZipFileAction class is used to get the zip file 
 * <P>
 * @author mholck
 * @see org.apache.struts.action.Action
 */
public class DownloadReportFileAction extends ActionSupport implements SessionAware, ServletRequestAware
{
	private static Logger logger = Logger.getLogger(DownloadReportFileAction.class);
    private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private String dirPath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	private MailConfig mailConfigInstance = MailConfig.getInstance(ApplicationContext.GOV_NIH_NCI_REMBRANDT_PROPERTIES);
	
	Map<String, Object> sessionMap;
	HttpServletRequest servletRequest;
	
	/**
	 * execute is called when this action is posted to
	 * <P>
	 * @param mapping The ActionMapping for this action as configured in struts
	 * @param form The ActionForm that posted to this action if any
	 * @param request The HttpServletRequest for the current post
	 * @param response The HttpServletResponse for the current post
	 */
	public String execute() throws Exception
	{
		String reportName = (String)this.servletRequest.getParameter("reportName");	
		
		if(reportName == null){
			reportName = (String) this.servletRequest.getSession().getAttribute("emailFileName");
		}
		if(reportName != null){
			reportName = URLDecoder.decode(reportName,"UTF-8");
		}
		// Check if the user is logged in
		String logged = (String)this.sessionMap.get("logged");
		//if already logged in  and file exists than download file
		if ((logged != null  && (logged.equals("yes")))){
				
			UserCredentials credentials = (UserCredentials)this.sessionMap.get(RembrandtConstants.USER_CREDENTIALS);
			        RembrandtAsynchronousFindingManagerImpl asynchronousFindingManagerImpl = new RembrandtAsynchronousFindingManagerImpl();
			        try {
						asynchronousFindingManagerImpl.retrieveResultsFromFile(
								this.servletRequest.getSession().getId(), reportName, credentials.getUserName(),
								this.servletRequest.getSession());
					} catch (FindingsQueryException e) {
						logger.error(e.getMessage());
					}
					// Set the forward
			        		
					logger.debug("redirecting to download");	
					return "viewResults";	
			}
		else //if((logged == null  || !logged.equals("yes")))
		{
			// Set the forward
			this.sessionMap.put("emailFileName", reportName);
			logger.debug("redirecting to login");	
			return "registration";	
		}

	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}
	
	
}
