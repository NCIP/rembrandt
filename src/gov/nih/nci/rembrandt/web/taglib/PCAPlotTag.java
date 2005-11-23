package gov.nih.nci.rembrandt.web.taglib;

import gov.nih.nci.caintegrator.analysis.messaging.PCAresultEntry;
import gov.nih.nci.caintegrator.service.findings.PrincipalComponentAnalysisFinding;
import gov.nih.nci.caintegrator.ui.graphing.chart.CaIntegratorChartFactory;
import gov.nih.nci.caintegrator.ui.graphing.chart.plot.PrincipalComponentAnalysisPlot.PCAcolorByType;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierPlotPointSeriesSet;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierStoredData;
import gov.nih.nci.caintegrator.ui.graphing.data.principalComponentAnalysis.PrincipalComponentAnalysisDataPoint;
import gov.nih.nci.caintegrator.ui.graphing.data.principalComponentAnalysis.PrincipalComponentAnalysisDataPoint.PCAcomponent;
import gov.nih.nci.caintegrator.ui.graphing.util.ImageMapUtil;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.helper.RembrandtImageFileHandler;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;

/**
 * this class generates a PCAPlot tag which will take a taskId, the components
 * with which to compare, and possibly a colorBy attribute which colors the
 * samples either by Disease or Gender. Disease is colored by default.
 * @author rossok
 *
 */
public class PCAPlotTag extends AbstractGraphingTag {

	private String beanName = "";
	private String taskId = "";
    private String colorBy = "";
    private String components ="";
    private List<PCAresultEntry> pcaResults;
    private Collection<PrincipalComponentAnalysisDataPoint> pcaData = new ArrayList();
	private List<JFreeChart> jFreeChartsList;
    private JFreeChart chart;
    private Logger logger = Logger.getLogger(PCAPlotTag.class);
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private BusinessTierCache businessTierCache = ApplicationFactory.getBusinessTierCache();
    
	public int doStartTag() {
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		Object o = request.getAttribute(beanName);
		JspWriter out = pageContext.getOut();
		ServletResponse response = pageContext.getResponse();
		
		try {
            //retrieve the Finding from cache and build the list of PCAData points
            PrincipalComponentAnalysisFinding principalComponentAnalysisFinding = (PrincipalComponentAnalysisFinding)businessTierCache.getSessionFinding(session.getId(),taskId);
            pcaResults = principalComponentAnalysisFinding.getResultEntries();
                if (pcaResults!=null || pcaResults.size()>0){
                    for (int i=0;i<pcaResults.size();i++){
                        PrincipalComponentAnalysisDataPoint pcaPoint = new PrincipalComponentAnalysisDataPoint(pcaResults.get(i).getSampleId(),pcaResults.get(i).getPc1(),pcaResults.get(i).getPc2(),pcaResults.get(i).getPc3());
                        pcaPoint.setDiseaseName("ASTRO");
                        pcaPoint.setSurvivalInMonths(new Double(12));
                        pcaData.add(pcaPoint);
                    }
                }
            
            //check the components to see which graph to get
			if(components.equalsIgnoreCase("PC1vsPC2")){
                chart = (JFreeChart) CaIntegratorChartFactory.getPrincipalComponentAnalysisGraph(pcaData,PCAcomponent.PC2,PCAcomponent.PC1,PCAcolorByType.valueOf(PCAcolorByType.class,colorBy));
            }
            if(components.equalsIgnoreCase("PC1vsPC3")){
                chart = (JFreeChart) CaIntegratorChartFactory.getPrincipalComponentAnalysisGraph(pcaData,PCAcomponent.PC3,PCAcomponent.PC1,PCAcolorByType.Disease);
            }
            if(components.equalsIgnoreCase("PC2vsPC3")){
                chart = (JFreeChart) CaIntegratorChartFactory.getPrincipalComponentAnalysisGraph(pcaData,PCAcomponent.PC3,PCAcomponent.PC2,PCAcolorByType.Disease);
            }
            
            RembrandtImageFileHandler imageHandler = new RembrandtImageFileHandler(session.getId(),"png",700,500);
			//The final complete path to be used by the webapplication
			String finalPath = imageHandler.getSessionTempFolder();
            String finalURLpath = imageHandler.getFinalURLPath();
			/*
			 * Create the actual charts, writing it to the session temp folder
			*/ 
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            String mapName = imageHandler.createUniqueMapName();
            PrintWriter writer = new PrintWriter(new FileWriter(mapName));
			ChartUtilities.writeChartAsPNG(new FileOutputStream(finalPath),chart, 700,500,info);
            ImageMapUtil.writeBoundingRectImageMap(writer,"PCAimageMap",info,true);
            writer.close();
			/*	This is here to put the thread into a loop while it waits for the
			 *	image to be available.  It has an unsophisticated timer but at 
			 *	least it is something to avoid an endless loop.
			 **/ 
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
            
            out.print(ImageMapUtil.returnBoundingRectImageMap(writer,mapName,true,info));
		    out.print("<img src=\""+finalURLpath+"\" usemap=\"#"+mapName + "\"" + " id=\"geneChart\"" + " border=0>");
            
            
            //(imageHandler.getImageTag(mapFileName));
        
		}catch (IOException e) {
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
		Collection<KaplanMeierPlotPointSeriesSet> plotPointSeriesSet = cacheData.getPlotPointSeriesCollection();
		for(KaplanMeierPlotPointSeriesSet set: plotPointSeriesSet) {
			Color setColor = set.getColor();
			String title = set.getLegendTitle();
		}
		return "<br>This is where the legend will go<br>";
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
     * @return Returns the taskId.
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId The taskId to set.
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return Returns the colorBy.
     */
    public String getColorBy() {
        return colorBy;
    }

    /**
     * @param colorBy The colorBy to set.
     */
    public void setColorBy(String colorBy) {
        this.colorBy = colorBy;
    }

    /**
     * @return Returns the components.
     */
    public String getComponents() {
        return components;
    }

    /**
     * @param components The components to set.
     */
    public void setComponents(String components) {
        this.components = components;
    }
	
}