package gov.nih.nci.rembrandt.schedule;

import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xml.sax.InputSource;

/**
 * The SearchSubjectJob class is the job to search for subjects
 * using the FTP method for large results. This is a quartz job that will
 * be scheduled to run when there are more than MAX_RESULTS returned.
 * <P>
 * @author mholck
 * @see import org.quartz.Job;
 */
public class GenerateReportJob implements Job
{
	private static Logger logger = Logger.getLogger(GenerateReportJob.class);
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		// Set the thread priority to a nicer value for background processing
		Thread.currentThread().setPriority(4);
		
		logger.debug("SearchSubjectJob is running");
		logger.debug("Our priority is " + Thread.currentThread().getPriority());
		String userName = context.getJobDetail().getJobDataMap().getString("userName");
		String email = context.getJobDetail().getJobDataMap().getString("email");
		String queryName = context.getJobDetail().getJobDataMap().getString("queryName");
		String sessionId = context.getJobDetail().getJobDataMap().getString("sessionId");

		ActionMessages errors = new ActionMessages();
		if (userName != null && email != null &&  queryName != null && sessionId != null){
		// Get all the needed elements from the form and build the query
			logger.debug("Email is " + email);
		SessionQueryBag queryBag = presentationTierCache.getSessionQueryBag(sessionId);
		Queriable cQuery = (Queriable) queryBag.getQuery(queryName);
	    CompoundQuery compoundQuery = null;
	    ReportBean reportBean = null;
		try {
			compoundQuery = new CompoundQuery(cQuery);
		    compoundQuery.setQueryName(queryName);
	        logger.debug("Setting query name to:"+compoundQuery.getQueryName());
		    compoundQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
	        logger.debug("Associated View for the Preview:"+compoundQuery.getAssociatedView().getClass());
		    //Save the sessionId that this preview query is associated with
	        compoundQuery.setSessionId(sessionId);
			ReportGeneratorHelper rgHelper = new ReportGeneratorHelper(compoundQuery, new HashMap());
			reportBean = rgHelper.getReportBean();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


			if(reportBean != null){
				String filename = null;
				try {
					filename = sereializeReportBean(userName, reportBean);
				} catch (Exception e) {
					sendErrorMail(email,  cQuery.getQueryName());
					logger.error( e.getMessage());
				}	
				if(filename != null){
					sendMail(email,  filename);
				}
			}
		}
	}

	private void sendErrorMail(String email, String queryName) {
		// TODO Auto-generated method stub
		
	}

	private void sendMail(String email, String filename) {
		// TODO Auto-generated method stub
		
	}

	private String sereializeReportBean(String userName ,ReportBean reportBean) throws Exception{
		try
		{
			String dirPath = System.getProperty("gov.nih.nci.rembrandt.data_directory");   	
			String filename = dirPath+ File.separator+userName + "-" +reportBean.getResultantCacheKey();
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename)); 
			//StringWriter writer = new StringWriter();
			
				InputSource is = new InputSource(getClass().getClassLoader().getResource("castor_query.xml").getPath());
				Mapping castorMapping = new Mapping();
				castorMapping.loadMapping(is);

				Marshaller marshaller = new Marshaller(writer);
				marshaller.setMapping(castorMapping);
				marshaller.marshal(reportBean);
			
			//out.( writer );
			writer.close();
			return filename;
		}
		catch( IOException e )
		{
			logger.error( e.getMessage());
			throw new Exception(e.getMessage());
		}
		catch( MappingException e )
		{
			logger.error( e.getMessage());
			throw new Exception(e.getMessage());
		} catch (MarshalException e) {
			logger.error( e.getMessage());
			throw new Exception(e.getMessage());
		} catch (ValidationException e) {
			logger.error( e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
}
