package gov.nih.nci.rembrandt.web.taglib;

import gov.nih.nci.caintegrator.ui.graphing.chart.CaIntegratorChartFactory;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeriesSet;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierStoredData;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.RembrandtImageFileHandler;
import gov.nih.nci.rembrandt.web.legend.LegendCreator;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import gov.nih.nci.rembrandt.web.legend.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemCollection;

public class KaplanMeierPlotTag extends AbstractGraphingTag {

	
    private String beanName = "";
	private String datasetName = "";
	private static Logger logger = Logger.getLogger(KaplanMeierPlotTag.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	
	public int doStartTag() {
        
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		Object o = request.getAttribute(beanName);
		JspWriter out = pageContext.getOut();
		ServletResponse response = pageContext.getResponse();
		String dataName;
		try {
			dataName = BeanUtils.getSimpleProperty(o,datasetName);
			KaplanMeierStoredData cacheData = (KaplanMeierStoredData)presentationTierCache.getSessionGraphingData(session.getId(),dataName);
			JFreeChart chart = CaIntegratorChartFactory.getKaplanMeierGraph(cacheData.getPlotPointSeriesCollection());
            
            
            RembrandtImageFileHandler imageHandler = new RembrandtImageFileHandler(session.getId(),"png",700,500);
			//The final complete path to be used by the webapplication
			String finalPath = imageHandler.getSessionTempFolder();
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
		    out.print(imageHandler.getImageTag());
		    out.print(createLegend(cacheData));
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
	
	private String createLegend(KaplanMeierStoredData cacheData) {		
        /**********
		 * This will create the legend with the color representations of the
		 * each of the available data sets.  It will also create a selectable 
		 * link to the underlying sample data.
		 * 
		 ****/
        String legendHTML = "";
        LegendCreator legendCreator = new LegendCreator();
		Collection<KaplanMeierPlotPointSeriesSet> plotPointSeriesSet = cacheData.getPlotPointSeriesCollection();
		for(KaplanMeierPlotPointSeriesSet set: plotPointSeriesSet) {
			Color setColor = set.getColor();
			String title = set.getLegendTitle();           
            legendHTML += "<span style='background-color:"+ legendCreator.c2hex(setColor)+"'>&nbsp;</span>" +  
                "&nbsp;&nbsp;" + "<span style='font-color:#000;font-size:.9em'>" + title + "</span>&nbsp;&nbsp;" ;
            }
		return "<br>" + legendHTML + "<br>";
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