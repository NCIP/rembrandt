/*
 * Created on Sep 14, 2004
 *
 */
package gov.nih.nci.nautilus.resultset;
import gov.nih.nci.nautilus.resultset.*;
import gov.nih.nci.nautilus.de.*;
import gov.nih.nci.nautilus.queryprocessing.ge.*;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr.GeneExprSingle;

import java.util.*;

/**
 * @author SahniH
 *
 * This class takes a DifferentialExpressionSfact and DifferentialExpressionGfact object and helps create a GeneCentricViewHandler or a SampleCentricViewHandler classes.
 */
public class ResultsetProcessor {
	private GeneExprSingleViewResultsContainer geneViewResultsContainer = new GeneExprSingleViewResultsContainer();
	private SampleViewResultsContainer sampleViewResultsContainer = new SampleViewResultsContainer();
 	public void handleGeneExprView(ResultSet[] geneExprObjects, GroupType groupType){
 	  	GeneExprSingleViewHandler geneExprSingleViewHandler = new GeneExprSingleViewHandler();
 	  	SampleViewHandler sampleViewHandler = new SampleViewHandler();
        for (int i = 0; i < geneExprObjects.length; i++) {
    		if(geneExprObjects[i] != null) {
            ResultSet obj = geneExprObjects[i];
              if (obj instanceof GeneExpr.GeneExprGroup)  {
              	GeneExpr.GeneExprGroup exprObj = (GeneExpr.GeneExprGroup) obj;
              }
               else if (obj instanceof GeneExpr.GeneExprSingle)  {
               	//Propulate the GeneExprSingleResultsContainer
               	GeneExprSingle  exprObj = (GeneExpr.GeneExprSingle) obj;
               	geneViewResultsContainer = geneExprSingleViewHandler.handleGeneSingleView(geneViewResultsContainer,exprObj, groupType);
               	//Populate the SampleViewResultsContainer
               	sampleViewResultsContainer = sampleViewHandler.handleSampleView(sampleViewResultsContainer,exprObj,groupType);
               }
    		}
        }//for
	}

	/**
	 * @return Returns the geneViewContainer.
	 */
	public GeneExprSingleViewResultsContainer getGeneViewResultsContainer() {
		return geneViewResultsContainer;
	}
	/**
	 * @return Returns the sampleViewResultsContainer.
	 */
	public SampleViewResultsContainer getSampleViewResultsContainer() {
		return this.sampleViewResultsContainer;
	}
}
