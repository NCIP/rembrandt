package gov.nih.nci.nautilus.ui.helper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import gov.nih.nci.nautilus.cache.CacheManagerWrapper;
import gov.nih.nci.nautilus.cache.RembrandtContextListener;
import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.Queriable;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsetManager;

import gov.nih.nci.nautilus.ui.bean.ReportBean;
import gov.nih.nci.nautilus.ui.report.ReportGenerator;
import gov.nih.nci.nautilus.ui.report.ReportGeneratorFactory;
import gov.nih.nci.nautilus.ui.report.SessionTempReportCounter;
import gov.nih.nci.nautilus.ui.report.Transformer;
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

	static public String REPORT_ACTION = "generatedReport.do";

	private ReportBean reportBean;

	private Document reportXML;

	/**
	 * Execute the CompoundQuery and store in the sessionCache. Create a
	 * ReportBean and store there for later retrieval.
	 * 
	 * @param query
	 */
	public ReportGeneratorHelper(Queriable query) {
		Resultant resultant = null;
		
		// used to retrieve the cache associated with this session
		String cacheKey = null;
		Element resultSetCacheElement = null;

		if (query != null && query instanceof CompoundQuery) {
			try {
				CompoundQuery cQuery = (CompoundQuery) query;
				//Get the desired view for this report
				Viewable view = cQuery.getAssociatedView();
				// Get the sessionId to needed to retrieve the sessionCache
				cacheKey = cQuery.getSessionId();
				
				if (cQuery.getQueryName()== null
						|| cQuery.getQueryName().equals("")) {
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
					cQuery.setQueryName(getTempReportName(cQuery.getSessionId()));
				}
				
				if (cacheKey != null && !cacheKey.equals("")) {
					/*
					 * Use the cacheKey to get the cache and see if 
					 * a result set already exists for the query we have been 
					 * given
					 */
					Cache sessionCache = CacheManagerWrapper.getSessionCache(cacheKey);
					resultSetCacheElement = sessionCache.get(cQuery.getQueryName());
					/*
					 * If there is no reportStored in the cache or this is a preview
					 * report, always run.
					 */
					if (resultSetCacheElement == null||cQuery.getQueryName().equals(NautilusConstants.PREVIEW_RESULTS)) {
						
						resultant = ResultsetManager
								.executeCompoundQuery(cQuery);
						/*
						 * Create the reportBean that will store everything we 
						 * may need later when messing with the reports associated
						 * with this result set.  This reportBean will also 
						 * be stored in the cache.
						 */				
						reportBean = new ReportBean();
						reportBean.setResultant(resultant);
						//The cache key will always be the compound query name
						reportBean.setResultantCacheKey(cQuery.getQueryName());
						
					}else {
						/*
						 * The reportBean was found in the cache
						 * so load it up.
						 */
						reportBean = (ReportBean)resultSetCacheElement.getValue();
					}
					/*
					 * Get the correct report XML generator for the desired view
					 * of the results.
					 * 
					 */
					if (reportBean != null) {
						resultant = reportBean.getResultant();
						Viewable oldView = resultant.getAssociatedView();
						/*
						 * Make sure that we change the view on the resultSet
						 * if the user changes the desired view from already
						 * stored view of the results
						 */
						if(oldView!=view||reportBean.getReportXML()==null) {
							//we must generate a new XML document for the
							//view they want
							ReportGenerator reportGen = ReportGeneratorFactory
									.getReportGenerator(view);
							reportXML = reportGen.getReportXML(resultant);
							reportBean.getResultant().setAssociatedView(view);
						}else {
							//Old view is the current view
							reportXML = reportBean.getReportXML();
						}
						//
						reportBean.setReportXML(reportXML);
					    resultSetCacheElement = new Element(reportBean.getResultantCacheKey(), reportBean);
					    if(sessionCache!=null) {
							sessionCache.put(resultSetCacheElement);
						}
					    	
					}else {
						throw new IllegalStateException("There is no resultant to create report");
					}
					
					
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
					throw new RuntimeException(
							"There does not appear to be a session associated with this query");
				}

			} catch (Throwable t) {
				logger.error("Result set manager threw unknown Exception");
				logger.error(t);
			}
		} else {
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
	
	public ReportBean getReportBean() {
		return reportBean;
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
	
	private String getTempReportName(String sessionId) {
		String name = "";
		if(sessionId ==null) {
			throw new NullPointerException("SessionId must not be null");
		}
		Cache sessionCache = CacheManagerWrapper.getSessionCache(sessionId);
		if(sessionCache!=null) {
			try {
			Element counterElement = sessionCache.get(NautilusConstants.REPORT_COUNTER);
			SessionTempReportCounter counter = (SessionTempReportCounter)counterElement.getValue();
			name =  counter.getNewTempReportName();
			sessionCache.put(new Element(NautilusConstants.REPORT_COUNTER, counter));
			}catch(IllegalStateException ise) {
				logger.error("Cache get threw an exception");
				logger.error(ise);
			}
			catch(CacheException ce) {
				logger.error("Cache get threw an exception");
				logger.error(ce);
			}
		}else {
			throw new RuntimeException("No Session Cache can be found for session: "+sessionId);
		}
		return name;
	}
}
