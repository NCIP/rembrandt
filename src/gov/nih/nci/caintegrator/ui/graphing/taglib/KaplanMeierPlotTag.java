package gov.nih.nci.caintegrator.ui.graphing.taglib;

import gov.nih.nci.caintegrator.ui.graphing.chart.CaIntegratorChartFactory;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierStoredData;
import gov.nih.nci.caintegrator.ui.graphing.util.FileDeleter;
import gov.nih.nci.nautilus.cache.CacheManagerDelegate;
import gov.nih.nci.nautilus.cache.RembrandtContextListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class KaplanMeierPlotTag extends AbstractGraphingTag {

	private String width = "";
	private String height= "";
	private String legend= "";
	private String beanName = "";
	private String datasetName = "";
	private Logger logger = Logger.getLogger(KaplanMeierPlotTag.class);
	
	public int doStartTag() {
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		Object o = request.getAttribute(beanName);
		JspWriter out = pageContext.getOut();
		ServletResponse response = pageContext.getResponse();
		String dataName;
		try {
			dataName = BeanUtils.getSimpleProperty(o,datasetName);
			KaplanMeierStoredData cacheData = (KaplanMeierStoredData)CacheManagerDelegate.getInstance().getSessionGraphingData(session.getId(),dataName);
			JFreeChart chart = CaIntegratorChartFactory.getKaplanMeierGraph(cacheData.getPlotPointSeriesCollection());
			//Path to webapp context with in the server
			String contextPath = RembrandtContextListener.getContextPath();
			//Path that will be used in the <img /> tag without the file name
			String urlPath = "\\images\\"+session.getId()+"\\";
			//Path to the session's image files within the webapp and within the server
			String sessionWebAppImagePath = contextPath+urlPath;
			//the actual unique chart name
			String chartName = createUniqueChartName(session);
			//The final complete path to be used by the webapplication
			String finalPath = sessionWebAppImagePath + chartName;
			//the final url that will be given to the client to retrieve the image
			String finalURLPath = urlPath+chartName;
			/*
			 * Creates the session image temp folder if the
			 * folder does not already exist
			 */
			File dir = new File(sessionWebAppImagePath);
			boolean dirCreated = dir.mkdir();
			
			/*
			 * Cleans out the session image temp folder if it did already
			 * exist.  However, because of threading issues it appears to work
			 * intermitently, but it does work.
			 */
			if(!dirCreated) {
				FileDeleter fd = new FileDeleter();
				fd.deleteFiles(sessionWebAppImagePath, ".png");
			 }
			/**
			 * Create the actual chart, writing it to the session temp folder
			 */
			ChartUtilities.writeChartAsPNG(new FileOutputStream(finalPath),chart, 700,500);
			/*
			 *	This is here to put the thread into a loop while it waits for the
			 *	image to be available.  It has an unsophisticated timer but at 
			 *	least it is something to avoid an endless loop.
			 *  
			 */
			boolean imageReady = false;
			int timeout = 1000;
			while(!imageReady) {
				timeout--;
				try {
					FileInputStream inputStream = new FileInputStream(finalPath);
					inputStream.available();
					imageReady = true;
					inputStream.close();
				}catch(IOException ioe) {
					imageReady = false;
				}
				if(timeout <= 1) {
					break;
				}
			}
		    out.print("<img src=\""+finalURLPath+"\" width=700 height=500 border=0>");
		}catch (IllegalAccessException e1) {
			logger.error(e1);
		} catch (InvocationTargetException e1) {
			logger.error(e1);
		} catch (NoSuchMethodException e1) {
			logger.error(e1);
		} catch (IOException e) {
			logger.error(e);
		}catch(Exception e) {
			logger.error(e);
		}catch(Throwable t) {
			logger.error(t);
		}
	
		return EVAL_BODY_INCLUDE;
	}
	
	private String createUniqueChartName(HttpSession session) {
		double time = (double)System.currentTimeMillis();
		double random = (1-Math.random());
		String one = String.valueOf(random*time);
		String finalAnswer = one.substring(10);
		return finalAnswer+".png";
	}

	public int doEndTag() throws JspException {
		return doAfterEndTag(EVAL_PAGE);
	}
	public void reset() {
		//chartDefinition = createChartDefinition();
	}
	/**
	 * @return Returns the bean.
	 */
	public String getBean() {
		return beanName;
	}
	/**
	 * @param bean The bean to set.
	 */
	public void setBean(String bean) {
		this.beanName = bean;
	}
	/**
	 * @return Returns the dataset.
	 */
	public String getDataset() {
		return datasetName;
	}
	/**
	 * @param dataset The dataset to set.
	 */
	public void setDataset(String dataset) {
		this.datasetName = dataset;
	}
}