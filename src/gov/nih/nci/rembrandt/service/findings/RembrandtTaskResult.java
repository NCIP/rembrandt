/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

/**
 * 
 */
package gov.nih.nci.rembrandt.service.findings;

import gov.nih.nci.caintegrator.application.mail.MailManager;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.service.task.TaskResultImpl;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class RembrandtTaskResult extends TaskResultImpl implements Callable<RembrandtTaskResult>{

	private static final long serialVersionUID = -1459623075327806364L;
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private final MailManager mailManager = new MailManager(ApplicationContext.GOV_NIH_NCI_REMBRANDT_PROPERTIES);

    private static Logger logger = Logger.getLogger(RembrandtTaskResult.class);   
    /** Synchronization control for FutureTask */
	private String reportBeanCacheKey = null;
	private String userName = null;
	private String email = null;
	private String queryText = null;
	private Boolean isEmailTask = false;
	public RembrandtTaskResult(Task task) {
		this.setTask(task);
	}
	/**
	 * @return the reportBeanCacheKey
	 */
	public String getReportBeanCacheKey() {
		return reportBeanCacheKey;
	}
	/**
	 * @param reportBeanCacheKey the reportBeanCacheKey to set
	 */
	public void setReportBeanCacheKey(String reportBeanCacheKey) {
		this.reportBeanCacheKey = reportBeanCacheKey;
	}
	public RembrandtTaskResult call() throws Exception{
        try {
       	 
            ReportGeneratorHelper reportHelper = new ReportGeneratorHelper((Queriable)this.getTask().getQueryDTO(),new HashMap()); 
            if(reportHelper.getReportBean()!= null ){
      		 
         		 this.setReportBeanCacheKey(reportHelper.getReportBean().getAssociatedQuery().getQueryName());
        		 this.getTask().setStatus(FindingStatus.Completed);


       	 }else{
       		 	if( !this.getTask().getStatus().equals(FindingStatus.Email)){
       		 		FindingStatus status = FindingStatus.Error;
                    status.setComment("Error occued while executing the query");
                    this.getTask().setStatus(status);
       		 	}

       	 }
        	
        //to generate error or completed.	                 
        }catch(Exception e) {
       	 if( e instanceof InterruptedException){
       		 System.out.println( "Cancelled "  + this.getTask().getId());
       		 logger.error("Cancelled issuing query", e);
       		 //FindingStatus status = FindingStatus.Error;
            //    status.setComment("Cancelled");
            //    this.getTask().setStatus(status);	                		 
       		 
       	 }else{
       		 logger.error("Error issuing query", e);
       		 FindingStatus status = FindingStatus.Error;
                status.setComment(e.getMessage());
                this.getTask().setStatus(status);
       	 }	                	 
        }finally {
           	presentationTierCache.addNonPersistableToSessionCache(this.getTask().getCacheId(), 
            		this.getTask().getId(), this);
            	logger.info("Query has completed, task has been placed back in cache");	 
           
        }

        return this;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the queryText
	 */
	public String getQueryText() {
		return queryText;
	}
	/**
	 * @param queryText the queryText to set
	 */
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	/**
	 * @return the isEmailTask
	 */
	public Boolean getIsEmailTask() {
		if(email != null && queryText != null && userName != null && getTask().getStatus().equals(FindingStatus.Email)){
			return true;
		}
		return false;
	}

	private void sendErrorMail(String email, String queryText) {
		mailManager.sendFTPErrorMail(email, queryText);

	}

	private void sendMail(String email, String filename, String queryText) {
		// Now send the notification email
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add(filename);
		mailManager.sendFTPMail(email, fileList, queryText);

	}

	private String sereializeReportBean(String uniqueFilename, ReportBean reportBean)
			throws Exception {
		try {
			reportBean.setResultantCacheKey(uniqueFilename);
			String dirPath = System
					.getProperty("gov.nih.nci.rembrandt.data_directory");
			String filename = dirPath + File.separator + uniqueFilename;
			FileOutputStream fo = new FileOutputStream(filename);
			ObjectOutputStream so = new ObjectOutputStream(fo);
			so.writeObject(reportBean);
			so.flush();
			so.close();
			fo.close();
			return filename;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

}
