package gov.nih.nci.nautilus.ui.helper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

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
import gov.nih.nci.nautilus.query.QueryManager;
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
	//This is the general purpose Nautilus Logger
	private static Logger logger = Logger
			.getLogger(ReportGeneratorHelper.class);
	//this is the XML logger for the reports
	private static Logger xmlLogger;
	//This is the element that is used to store all relevant report data
	//it sores the resultant, the sessionId, the latest reportXML
	private ReportBean _reportBean;
	private CompoundQuery _cQuery;
	private String _queryName = null;
	private String _sessionId = null;
	private static Properties applicationResources = applicationResources = ApplicationContext.getLabelProperties();
	private ConvenientCache _cacheManager = CacheManagerDelegate.getInstance();
	//Check the applications resource file and turn on report xml logging if 
	//property nautilus.xml_logging = true, else then no xml report logging.
	private static boolean xmlLogging;
	static {
		String property = (String)applicationResources.get("nautilus.xml_logging");
		if("true".equals(property)){
			xmlLogging = true;
			//Get the XML_LOGGER specified in log4j.properties
			xmlLogger = Logger.getLogger("XML_LOGGER");
		}else {
			xmlLogging = false;
		}
		
	}
	public ReportGeneratorHelper(ReportBean reportBean, Map filterParams) {
		Resultant resultant = reportBean.getResultant();
		Resultant showAllResults;
		try {
			showAllResults = ResultsetManager.executeShowAllQuery(resultant);
			//check to make sure that we have a sessionId
			Queriable showAllQuery = showAllResults.getAssociatedQuery();
			//check the query to make sure that it is a compound query
			checkCompoundQuery(showAllQuery);
			//check the sessionId
			checkSessionId(_cQuery.getSessionId());
			//check that we have a queryName
			checkQueryName( _cQuery.getQueryName()+" show all values report");
			//set the query name to a show all report
			resultant.getAssociatedQuery().setQueryName(_queryName);
			//create a new ReportBean
			_reportBean = new ReportBean();
			//store the results into the report bean
			_reportBean.setResultant(showAllResults);
			//store the cache key that can be used to retrieve this bean later
			_reportBean.setResultantCacheKey(_queryName);
			//check the filterParam map for processing
			_reportBean.setFilterParams(processFilterParamMap(filterParams));
			//generate the reportXML and store in the ReportBean
			generateReportXML();
			//drop this ReportBean in the session cache, use the _queryName as the 
			//parameter
			_cacheManager.addToSessionCache(_sessionId,_queryName,_reportBean);
		}catch(Exception e) {
			logger.error("Exception when trying to generate a Show All Values Report");
			logger.error(e);
		}
	}
	
	/**
	 * @param filterParams
	 */
	private Map processFilterParamMap(Map filterParams) {
		List tokens = null;
		if(filterParams.containsKey("filter_string")) {
			StringTokenizer tokenizer = new StringTokenizer((String)filterParams.get("filter_string"), ",", false);
			tokens = new ArrayList();
			while(tokenizer.hasMoreTokens()) {
				tokens.add(tokenizer.nextToken());
			}
			filterParams.put("filter_string", tokens);
		}
		return filterParams;
		
	}

	/**
	 * This is intended to be used to generate a ReportBean when you have a
	 * query and want to reduce the result set by limiting the results to 
	 * sample ids listed in the String[] sampleIds. As it is currently implemented
	 * it does not check the cache for any result sets, because at present, we
	 * execute the query all over again.  
	 * 
	 * In the future if performance is important we may consider just taking the
	 * already existing result set and just extract the relevant sampleIds 
	 * 
	 * @param query --currently a CompoundQuery that you are selecting sample
	 * ids from. No other Queriable object will actually work.  I know,I know
	 * bad design. But this is to allow for the possibility of other types 
	 * in the future.  We may later decide to constrain this to a CompoundQuery
	 * but for now... well it is just going to have to be the way it is.
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
		//create a new ReportBean
		_reportBean = new ReportBean();
		//execute the sample id sub select query
		Resultant sampleIdResults = QueryManager.runReportSampleIdSelection(_cQuery, sampleIds);
		//store the results into the report bean
		_reportBean.setResultant(sampleIdResults);
		//store the cache key that can be used to retrieve this bean later
		_reportBean.setResultantCacheKey(_queryName);
		//generate the reportXML and store in the ReportBean
		generateReportXML();
		//drop this ReportBean in the session cache, use the _queryName as the 
		//parameter
		_cacheManager.addToSessionCache(_sessionId,_queryName,_reportBean);
	}

	/**
	 * This contructor use a TemplateMethod pattern to perform the following
	 * tasks (They must happen in this order, though some may be omitted if the
	 * use case does not require it): 
	 * 		1-Check to see if the query is a CompoundQuery and cast if is
	 * 		2-Check that there is a sessionId specified for the query
	 * 		3-Check that we have a query name for the CompoundQuery
	 * 		4-Check the SessionCache for any stored results for this query
	 *      to avoid running to the database if not needed.
	 *      5-Execute the query if there is no result set to use in the cache
	 *      6-Generate the report xml based on the desired view
	 *      7-store the new report bean in cache with the new xml for later 
	 *      retrieval by the UI.
	 *        
	 * @param query --the query that you want some new view (Report) of
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
	/**
	 * Checks the Queriable object and determines if it is a CompoundQuery.  If
	 * so then set the class variable _cQuery = (CompoundQuery)query else
	 * throw a new UnsupportedOperationException as we can not currently handle 
	 * anything but CompoundQueries at this time.  I know that it doesn't make 
	 * much sense to only allow a single implementation of the Queriable Interface
	 * to really be passed when we specify in the signature that any Queriable
	 * object will do.  It is just that in order to avoid tons of casting and
	 * maintain flexibility for later implementations I did it this way.
	 *  
	 * @param query
	 * @throws UnsupportedOperationException
	 */
	private void checkCompoundQuery(Queriable query) throws UnsupportedOperationException {
		if (query != null && query instanceof CompoundQuery) {
			//sets the class variable _cQuery 
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
	/**
	 * Check the sessionId in the Queriable object that we have gotten
	 * @param sessionId
	 * @throws IllegalStateException
	 */
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
	/**
	 * make sure that we have a name for our report
	 * @param queryName
	 */
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
	/**
	 * check the cache for the results sets for the query
	 * @param view
	 */
	private void checkCache(View view) {
		/*
		 * Use the sessionId to get the cache and see if 
		 * a result set already exists for the queryName we have been 
		 * given.
		 */
		_reportBean = _cacheManager.getReportBean(_sessionId, _queryName, view);
	}
	/**
	 * This method will take the class variable ReportBean _reportBean and 
	 * create the correct XML representation for the desired view of the results.  When
	 * completed it adds the XML to the _reportBean and drops the _reportBean
	 * into the sessionCache for later retrieval when needed
	 * 
	 * @throws IllegalStateException this is thrown when there is no resultant
	 * found in _reportBean
	 */
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
			Map filterParams = _reportBean.getFilterParams();
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
				reportXML = reportGen.getReportXML(resultant, filterParams);
				
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
	/**
	 * This executes the current Compound Query that is referenced in the _cQuery
	 * class scope variable.
	 * @throws Exception
	 */
	private void executeQuery() throws Exception{
		if(_cQuery!=null) {
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
		}else{
			logger.error("Compound Query is Null.  Can not execute!");
			throw new NullPointerException("CompoundQuery is null.");
		}
	}
	/**
	 * 
	 * @return
	 */
	public ReportBean getReportBean() {
		return _reportBean;
	}
	/**
	 * This static method will render a query report of the passed reportXML, in
	 * HTML using the XSLT whose name has been passed, to the jsp whose 
	 * jspWriter has been passed. The request is used to acquire any filter 
	 * params that may have been added to the request and that may be applicable
	 * to the XSLT.
	 *  
	 * @param request --the request that will contain any parameters you want applied
	 * to the XSLT that you specify
	 * @param reportXML -- this is the XML that you want transformed to HTML
	 * @param xsltFilename --this the XSLT that you want to use
	 * @param out --the JSPWriter you want the transformed document to go to...
	 */
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
