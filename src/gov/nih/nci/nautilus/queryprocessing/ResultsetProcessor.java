/*
 * Created on Sep 14, 2004
 *
 */
package gov.nih.nci.nautilus.queryprocessing;
import gov.nih.nci.nautilus.resultset.*;
import gov.nih.nci.nautilus.de.*;
import java.util.*;

/**
 * @author SahniH
 *
 * This class takes a DifferentialExpressionSfact and DifferentialExpressionGfact object and helps create a GeneCentricViewHandler or a SampleCentricViewHandler classes.
 */
public class ResultsetProcessor {
	private GeneViewContainer geneViewContainer = new GeneViewContainer();
	public void handleGeneView(ResultSet[] geneExprObjects, GroupType groupType){

        for (int i = 0; i < geneExprObjects.length; i++) {
    		if(geneExprObjects[i] != null) {
            ResultSet obj = geneExprObjects[i];
              if (obj instanceof GeneExpr.GeneExprGroup)  {
              	GeneExpr.GeneExprGroup exprObj = (GeneExpr.GeneExprGroup) obj;
              }
               else if (obj instanceof GeneExpr.GeneExprSingle)  {
               	GeneExpr.GeneExprSingle  exprObj = (GeneExpr.GeneExprSingle) obj;
               	GeneSingleViewHandler geneSingleViewHandler = new GeneSingleViewHandler(geneViewContainer);
               	geneViewContainer = geneSingleViewHandler.handleGeneSingleView(exprObj, groupType);
               }
    		}
        }//for
	}

	/**
	 * @return Returns the geneViewContainer.
	 */
	public GeneViewContainer getGeneViewContainer() {
		return geneViewContainer;
	}
	public static void main(String[] args) {
	}
}
