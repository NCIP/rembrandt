/*
 * Created on Sep 14, 2004
 *
 */
package gov.nih.nci.nautilus.resultset;
import gov.nih.nci.nautilus.queryprocessing.cgh.CopyNumber;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr.GeneExprSingle;
import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberSingleViewHandler;
import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneExprDiseaseGroupViewHandler;
import gov.nih.nci.nautilus.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneExprSampleViewContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneExprSingleViewHandler;
import gov.nih.nci.nautilus.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.sample.SampleViewHandler;
import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;
import gov.nih.nci.nautilus.view.GroupType;

/**
 * @author SahniH
 *
 * This class takes a DifferentialExpressionSfact and DifferentialExpressionGfact object and helps create a GeneCentricViewHandler or a SampleCentricViewHandler classes.
 */
public class ResultsetProcessor {
 	public static ResultsContainer handleGeneExprSingleView(GeneExpr.GeneExprSingle[] geneExprObjects, GroupType groupType){
 		ResultsContainer resultsContainer = null;
 	  	GeneExprSingleViewHandler geneExprSingleViewHandler = new GeneExprSingleViewHandler();
 	  	SampleViewHandler sampleViewHandler = new SampleViewHandler();
      	GeneExprSingleViewResultsContainer geneExprSingleResultsContainer = new GeneExprSingleViewResultsContainer();
    	SampleViewResultsContainer sampleViewResultsContainer = new SampleViewResultsContainer();
    	GeneExprSampleViewContainer geneExprSampleViewContainer = new GeneExprSampleViewContainer();
      	GeneExprResultsContainer geneExprResultsContainer = new GeneExprResultsContainer();
          for (int i = 0; i < geneExprObjects.length; i++) {
    		if(geneExprObjects[i] != null) {
            ResultSet obj = geneExprObjects[i];
            	if (obj instanceof GeneExpr.GeneExprSingle)  {
	              	//Propulate the GeneExprSingleResultsContainer
	               	GeneExprSingle  exprObj = (GeneExpr.GeneExprSingle) obj;
	               	geneExprSingleResultsContainer = geneExprSingleViewHandler.handleGeneExprSingleView(geneExprSingleResultsContainer,exprObj, groupType);
	               	geneExprSampleViewContainer.setGeneExprSingleViewContainer(geneExprSingleResultsContainer);
	               	//Populate the SampleViewResultsContainer
	               	sampleViewResultsContainer = sampleViewHandler.handleSampleView(sampleViewResultsContainer,exprObj,groupType);
	               	geneExprSampleViewContainer.setSampleViewResultsContainer(sampleViewResultsContainer);
	               	geneExprSampleViewContainer.setGeneExprSingleViewContainer(geneExprSingleResultsContainer);
	               	resultsContainer = geneExprSampleViewContainer;
               }
    		}
        }//for
        return resultsContainer;
	}

	/**
	 * @param resultsets
	 * @return
	 */
	public static ResultsContainer handleGeneExprDiseaseView(GeneExpr.GeneExprGroup[] geneExprObjects) {
		ResultsContainer resultsContainer = null;
 	  	GeneExprDiseaseGroupViewHandler geneExprDiseaseViewHandler = new GeneExprDiseaseGroupViewHandler();
 	  	SampleViewHandler sampleViewHandler = new SampleViewHandler();
      	GeneExprSingleViewResultsContainer geneExprSingleResultsContainer = new GeneExprSingleViewResultsContainer();
    	SampleViewResultsContainer sampleViewResultsContainer = new SampleViewResultsContainer();
    	GeneExprSampleViewContainer geneExprSampleViewContainer = new GeneExprSampleViewContainer();
      	GeneExprResultsContainer geneExprResultsContainer = new GeneExprResultsContainer();
          for (int i = 0; i < geneExprObjects.length; i++) {
    		if(geneExprObjects[i] != null) {
            ResultSet obj = geneExprObjects[i];
              if (obj instanceof GeneExpr.GeneExprGroup)  {
              	GeneExpr.GeneExprGroup exprObj = (GeneExpr.GeneExprGroup) obj;
               	//Propulate the GeneExprSingleResultsContainer
              	geneExprResultsContainer = geneExprDiseaseViewHandler.handleGeneExprDiseaseView(geneExprResultsContainer,exprObj);
              	resultsContainer = geneExprResultsContainer;
              }
    		}
        }//for
        return resultsContainer;
	}
	public static ResultsContainer handleCopyNumberSingleView(CopyNumber[] copyNumberObjects, GroupType groupType){
 		ResultsContainer resultsContainer = null;
 	  	CopyNumberSingleViewHandler  copyNumberSingleViewHandler = new  CopyNumberSingleViewHandler();
 	  	SampleViewHandler sampleViewHandler = new SampleViewHandler();
      	CopyNumberSingleViewResultsContainer copyNumberSingleViewResultsContainer = new CopyNumberSingleViewResultsContainer();
    	//SampleViewResultsContainer sampleViewResultsContainer = new SampleViewResultsContainer();
    	//GeneExprSampleViewContainer geneExprSampleViewContainer = new GeneExprSampleViewContainer();
      	//GeneExprResultsContainer geneExprResultsContainer = new GeneExprResultsContainer();
          for (int i = 0; i < copyNumberObjects.length; i++) {
    		if(copyNumberObjects[i] != null) {
            ResultSet obj = copyNumberObjects[i];
            	if (obj instanceof CopyNumber)  {
	              	//Propulate the GeneExprSingleResultsContainer
            		CopyNumber  copyNumberObj = (CopyNumber) obj;
            		copyNumberSingleViewResultsContainer = copyNumberSingleViewHandler.handleCopyNumberSingleView(copyNumberSingleViewResultsContainer,copyNumberObj, groupType);
	               	//geneExprSampleViewContainer.setGeneExprSingleViewContainer(geneExprSingleResultsContainer);
	               	//Populate the SampleViewResultsContainer
	               	//sampleViewResultsContainer = sampleViewHandler.handleSampleView(sampleViewResultsContainer,copyNumberObj,groupType);
	               	//geneExprSampleViewContainer.setSampleViewResultsContainer(sampleViewResultsContainer);
	               	//geneExprSampleViewContainer.setGeneExprSingleViewContainer(geneExprSingleResultsContainer);
	               	resultsContainer = copyNumberSingleViewResultsContainer;
               }
    		}
        }//for
        return resultsContainer;
	}

}
