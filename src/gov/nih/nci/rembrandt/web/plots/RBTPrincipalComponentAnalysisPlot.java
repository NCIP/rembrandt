package gov.nih.nci.rembrandt.web.plots;

import gov.nih.nci.caintegrator.enumeration.DiseaseType;
import gov.nih.nci.caintegrator.enumeration.GenderType;
import gov.nih.nci.caintegrator.ui.graphing.chart.plot.PrincipalComponentAnalysisPlot;
import gov.nih.nci.caintegrator.ui.graphing.data.DataRange;
import gov.nih.nci.caintegrator.ui.graphing.data.clinical.ClinicalDataPoint;
import gov.nih.nci.caintegrator.ui.graphing.data.principalComponentAnalysis.PrincipalComponentAnalysisDataPoint;
import gov.nih.nci.caintegrator.ui.graphing.data.principalComponentAnalysis.PrincipalComponentAnalysisDataPoint.PCAcomponent;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.StandardURLTagFragmentGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.util.ShapeUtilities;
import org.jfree.util.StringUtils;


public class RBTPrincipalComponentAnalysisPlot extends PrincipalComponentAnalysisPlot {

	

	public RBTPrincipalComponentAnalysisPlot(Collection<PrincipalComponentAnalysisDataPoint> dataPoints, PCAcomponent component1, PCAcomponent component2, PCAcolorByType colorBy) {
		super(dataPoints, component1, component2, colorBy);
	}
	
	/**
	 * This chart uses the XYAnnotation as a glyph to represent
	 * a single pca data point. Glyph shape is determined by survival time.
	 * Survival of more than 10 months is represented by a circle. 10 months or less
	 * is represented by a square. Component1 values are represented by X 
	 * Component2 values are represented by Y
	 */
	protected void createGlyphsAndAddToPlot(XYPlot plot) {
		
		//for RBT, if its non_tumor, show a diamond meaning "N/A for survival"
		
	  XYShapeAnnotation glyph;
	  Shape glyphShape;
	  Color glyphColor;
	  
	  PrincipalComponentAnalysisDataPoint pcaPoint;
	  double x, y;
	  for (Iterator i=dataPoints.iterator(); i.hasNext(); ) {
	    pcaPoint = (PrincipalComponentAnalysisDataPoint) i.next();
	    
	    x = pcaPoint.getComponentValue(component1);
	    y = pcaPoint.getComponentValue(component2);
	    double survival = pcaPoint.getSurvivalInMonths();
	    String diseaseName = pcaPoint.getDiseaseName();
	    
	    if(this.colorBy.equals(PCAcolorByType.NONE))	{
	    	Ellipse2D.Double circle = new Ellipse2D.Double();
		    circle.setFrameFromCenter(x,y, x+2, y+2);
		    glyphShape = circle;
	    }
	    else if(diseaseName.equals(RembrandtConstants.NON_TUMOR))	{
	    	//glyphShape = ShapeUtilities.createDiamond(new Float(x));
	    	Rectangle2D.Double rect = new Rectangle2D.Double();
		    rect.setFrameFromCenter(x,y, x+2,y+2);
		    glyphShape = rect;
		    Shape gShape = ShapeUtilities.rotateShape(glyphShape, new Double(0.785398163), new Float(x),new Float(y));
		    glyphShape = gShape;
	    }
	    else if ((survival > 0) && (survival < 10.0)) {
	      Rectangle2D.Double rect = new Rectangle2D.Double();
	      rect.setFrameFromCenter(x,y, x+2,y+2);
	      glyphShape = rect;
	    }
	    else if ((survival > 0) && (survival >= 10.0)) {
	      Ellipse2D.Double circle = new Ellipse2D.Double();
	      circle.setFrameFromCenter(x,y, x+2, y+2);
	      glyphShape = circle;
	    }
	    else {
	      GeneralPath gp = new GeneralPath();
	      float xf = (float)x;
	      float yf = (float)y;
	      //make a triangle
	      gp.moveTo(xf,yf);
	      gp.lineTo(xf+3.0f,yf-3.0f);
	      gp.lineTo(xf-3.0f,yf-3.0f);
	      gp.closePath();
	      glyphShape = gp;
	    }
	    
	    glyphColor = getColorForDataPoint(pcaPoint); 
	    glyph = new XYShapeAnnotation(glyphShape, new BasicStroke(1.0f), Color.BLACK, glyphColor);
        String tooltip = "";   
            if(pcaPoint.getSurvivalInMonths()<=0.0){
    	        tooltip = pcaPoint.getSampleId() + " " + pcaPoint.getDiseaseName();
            }
            else{
               tooltip = pcaPoint.getSampleId() + " " + pcaPoint.getDiseaseName() + " survivalMonths=" + nf.format(pcaPoint.getSurvivalInMonths());
            }
        glyph.setToolTipText(tooltip);
	    plot.addAnnotation(glyph);
	  }
	  
		
	}

	
	/**
	 * Get the color for a PCA data point. The color is determined by the Color by parameter.
	 * @param pcaPoint
	 * @return
	 */
	/*
	protected Color getColorForDataPoint(PrincipalComponentAnalysisDataPoint pcaPoint) {
	  Color defaultColor = Color.GRAY;
	  Color retColor = null;
	  
	  if (colorBy == PCAcolorByType.Disease) {
		String diseaseName = pcaPoint.getDiseaseName();
		Color diseaseColor = Color.GRAY;
		if (diseaseName != null) {
		  DiseaseType disease = DiseaseType.valueOf(diseaseName);
		  diseaseColor = disease.getColor();
		}
	    
	    int grade = pcaPoint.getDiseaseGrade();
	    for (int i=0; i < grade-1; i++) {
	      diseaseColor = diseaseColor.brighter();
	    }
	    retColor = diseaseColor;
	  }
	  else if (colorBy == PCAcolorByType.Gender) {
	    GenderType gender = pcaPoint.getGender();
	    
	    if (gender != null) { 
	      retColor = gender.getColor();
	    }

	  }
	  
	  if (retColor == null) {
	    retColor = defaultColor;
	  }
	  
	  return retColor;
	}
*/

}

