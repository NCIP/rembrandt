package gov.nih.nci.nautilus.ui.helper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.cache.ConvenientCache;
import gov.nih.nci.nautilus.cache.RembrandtContextListener;
import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsetManager;

import gov.nih.nci.nautilus.ui.bean.ReportBean;
import gov.nih.nci.nautilus.ui.report.ReportGenerator;
import gov.nih.nci.nautilus.ui.report.ReportGeneratorFactory;
import gov.nih.nci.nautilus.ui.report.Transformer;
import gov.nih.nci.nautilus.util.ApplicationContext;
import gov.nih.nci.nautilus.view.View;
import gov.nih.nci.nautilus.view.Viewable;

/**
 * The ReportGeneratorHelper was written to act as a Report Generation manager
 * for the UI. It provides a single avenue where, if a UI element has a Query to
 * execute or the cache key to a previously stored Resultant, the necesary calls
 * are made to generate an XML document representing the report. The generated
 * XML will then be stored in a ReportBean that will also contain the cache key
 * where the resultant can be called again, if needed.
 * 
 * @author BauerD, LandyR 
 * Feb 8, 2005
 * 
 */
public class ReportGeneratorHelper {
	private static Logger logger = Logger
			.getLogger(ReportGeneratorHelper.class);
	private static Logger xmlLogger = Logger.getLogger("XML_LOGGER");

	//This is the element that is used to store all relevant report data
	//it sores the resultant, the sessionId, the latest reportXML
	private ReportBean _reportBean;
	private CompoundQuery _cQuery;
	private String _queryName = null;
	private String _sessionId = null;
	private static Properties applicationResources = applicationResources = ApplicationContext.getLabelProperties();
	private ConvenientCache _cacheManager = CacheManagerDelegate.getInstance();
	private static boolean xmlLogging;
	static {
		String property = (String)applicationResources.get("nautilus.xml_logging");
		if("true".equals(property)){
			xmlLogging = true;
		}else {
			xmlLogging = false;
		}
		
	}
		
	
	/**
	 * This is intended to be used to generate a ReportBean when you have a
	 * query and want to reduce the result set by limiting the results to 
	 * sample ids listed in the String[] sampleIds. 
	 * 
	 * @param query --This is the CompundQuery that you are selected sample ids from
	 * @param sampleIds --this is the array of sample ids that you would like to
	 * contstrain
	 */
	public ReportGeneratorHelper(Queriable query, String[] sampleIds) {
		//check the query to make sure that it is a compound query
		checkCompoundQuery(query);
		//check to make sure that we have a sessionId
		checkSessionId( _cQuery.getSessionId());
		//check that we have a queryName
		checkQueryName( _cQuery.getQueryName());
		
		
	}

	/**
	 * Constructor
	 * Execute the CompoundQuery and store in the sessionCache. Create a
	 * ReportBean and store there for later retrieval.
	 * 
	 * @param query
	 */
	public ReportGeneratorHelper(Queriable query) {
		try {
			//check the query to make sure that it is a compound query
			checkCompoundQuery(query);
			//check to make sure that we have a sessionId
			checkSessionId( _cQuery.getSessionId());
			//check that we have a queryName
			checkQueryName( _cQuery.getQueryName());
			//check the cache for the resultant of the query
			checkCache((View)_cQuery.getAssociatedView());
			/*
			 * If the _reportBean is null then we know that we could not
			 * find an appropriate result set in the session cache. So
			 * lets run a query and get a result. Always run a query
			 * if it is a preview report.
			 * 
			 */
			if (_reportBean == null||NautilusConstants.PREVIEW_RESULTS.equals(_queryName)) {
				logger.debug("Executing Query");
				executeQuery();
			}
			this.generateReportXML();
		}catch(Exception e) {
			logger.error("Unable to create the ReportBean");
			logger.error(e);
		}
			
		
		
	}

	private void checkCompoundQuery(Queriable query) throws UnsupportedOperationException {
		if (query != null && query instanceof CompoundQuery) {
			_cQuery = (CompoundQuery)query;
		}else {
			/*
			 * Some other object implementing the Queriable interface has been
			 * passed. Currently we only support the compound query. But that is
			 * not to say that we won't later support others. Hence, the if-else
			 * statement here. I'll bet we want the run report button to use
			 * this class so I better add some code here if we just get a query.
			 */
			logger.error("Non compound query submitted");
			throw new UnsupportedOperationException(
					"You must pass a CompoundQuery at this time");
		}
	}

	private void checkSessionId(String sessionId)throws IllegalStateException {
		if (sessionId != null && !sessionId.equals("")) {
			_sessionId = sessionId;
		} else {
			/*
			 * We can not store the resultSet without a unique cache to
			 * store it in. And since we are currently implementing a
			 * session based cache system, that unique cache id should
			 * be the session id. If there is no session id, than we can
			 * assume that something is really wrong so throw a new
			 * RuntimeException
			 */
			logger.error("sessionId is empty for the compoundQuery");
			throw new IllegalStateException(
					"There does not appear to be a session associated with this query");
		}
	}
	
	private void checkQueryName(String queryName) {
		if (!"".equals(queryName)&& queryName != null){
			_queryName = queryName;
		}else {
			/*
			 * If the query name is empty than we can assume that
			 * the user has no interest in storing the query for
			 * later use. However the user may still want to use the
			 * current query report in other ways, like changing the
			 * view or downloading it. SO we should probably keep it
			 * around for a little bit. We do this by giving it a
			 * fixed temp name. This results set will be stored
			 * until another unnamed query is run.
			 * 
			 */
			_queryName = _cacheManager.getTempReportName(_sessionId);
			_cQuery.setQueryName(_queryName);
		}
	}
	
	private void checkCache(View view) {
		/*
		 * Use the sessionId to get the cache and see if 
		 * a result set already exists for the queryName we have been 
		 * given.
		 */
		_reportBean = _cacheManager.getReportBean(_sessionId, _queryName, view);
	}
	
	private void generateReportXML() throws IllegalStateException {
		/*
		 * Get the correct report XML generator for the desired view
		 * of the results.
		 * 
		 */
		Document reportXML;
		if (_reportBean != null) {
			Resultant resultant = _reportBean.getResultant();
			Viewable oldView = resultant.getAssociatedView();
			Viewable newView = _cQuery.getAssociatedView();
			/*
			 * Make sure that we change the view on the resultSet
			 * if the user changes the desired view from already
			 * stored view of the results
			 */
			if(!oldView.equals(newView)||_reportBean.getReportXML()==null) {
				//we must generate a new XML document for the
				//view they want
				ReportGenerator reportGen = ReportGeneratorFactory
						.getReportGenerator(newView);
				resultant.setAssociatedView(newView);
				reportXML = reportGen.getReportXML(resultant);
				
			}else {
				//Old view is the current view
				reportXML = _reportBean.getReportXML();
			}
			
			//XML Report Logging
			if(xmlLogging) {
				try {
					StringWriter out = new StringWriter();
					OutputFormat outformat = OutputFormat.createPrettyPrint();
					XMLWriter writer = new XMLWriter(out, outformat);
					writer.write(reportXML);
					writer.flush();
					xmlLogger.debug(out.getBuffer());
				}catch(IOException ioe) {
					logger.error("There was an error writing the XML to log");
					logger.error(ioe);
				}
			}
			_reportBean.setReportXML(reportXML);
			_cacheManager.addToSessionCache(_sessionId,_queryName,_reportBean);
		   			   
		    	
		}else {
			throw new IllegalStateException("There is no resultant to create report");
		}
	}
	
	private void executeQuery() throws Exception{
		Resultant resultant = ResultsetManager.executeCompoundQuery(_cQuery);
		/*
		 * Create the _reportBean that will store everything we 
		 * may need later when messing with the reports associated
		 * with this result set.  This _reportBean will also 
		 * be stored in the cache.
		 */				
		_reportBean = new ReportBean();
		_reportBean.setResultant(resultant);
		//The cache key will always be the compound query name
		_reportBean.setResultantCacheKey(_cQuery.getQueryName());
	}
	
	public ReportBean getReportBean() {
		return _reportBean;
	}
	
	public static void renderReport(HttpServletRequest request, Document reportXML, String xsltFilename, JspWriter out) {
		File styleSheet = new File(RembrandtContextListener.getContextPath()+"/XSL/"+xsltFilename);
		// load the transformer using JAXP
		logger.debug("Applying XSLT "+xsltFilename);
        Transformer transformer;
		try {
			transformer = new Transformer(styleSheet, (HashMap)request.getAttribute(NautilusConstants.FILTER_PARAM_MAP));
	     	Document transformedDoc = transformer.transform(reportXML);
	        OutputFormat format = OutputFormat.createPrettyPrint();
	        XMLWriter writer;
	       	writer = new XMLWriter( out, format );
			writer.write( transformedDoc );
			writer.close();
		}catch (UnsupportedEncodingException uee) {
			logger.error("UnsupportedEncodingException");
			logger.error(uee);
		}catch (IOException ioe) {
			logger.error("IOException");
			logger.error(ioe);
		}
	}

}
