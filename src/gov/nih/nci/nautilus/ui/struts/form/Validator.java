package gov.nih.nci.nautilus.ui.struts.form;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * @author BauerD
 * Dec 15, 2004
 * This class is used to validate input fields from the UI
 * 
 */
public class Validator {
	public static void validateGeneSymbol(String geneSymbol, ActionErrors errors) {
		if(geneSymbol==null||geneSymbol.equals("")) {
           errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("gov.nih.nci.nautilus.ui.struts.form.quicksearch.emptyGene"));
        }
        /*
        else {
           try {
            Collection results = LookupManager.getGeneSymbols();
           }catch(Exception e){
           	  e.printStackTrace();
           }
        }*/
     }
}
