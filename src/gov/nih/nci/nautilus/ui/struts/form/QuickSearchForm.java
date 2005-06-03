package gov.nih.nci.nautilus.ui.struts.form;

import gov.nih.nci.nautilus.lookup.AllGeneAliasLookup;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class QuickSearchForm extends BaseForm implements GeneValidator{
	private String plot = null;
    private AllGeneAliasLookup[] allGeneAlias;
    private String quickSearchName = null;
	private String quickSearchType = null;
	
	public String getPlot() {
		return plot;
	}

	public void setPlot(String str) {
		plot = str;
	}

	public void setQuickSearchName(String str) {	    
	    String[] quickSearchNameStrings = str.split(":");
	    str = quickSearchNameStrings[0];
		this.quickSearchName = str.trim();
	}

	public String getQuickSearchName() {
		return this.quickSearchName;
	}

	public void setQuickSearchType(String str) {
		this.quickSearchType = str;
	}

	public String getQuickSearchType() {
		return this.quickSearchType;
	}
	public void setAllGeneAlias(AllGeneAliasLookup[] allGeneAlias){
	    this.allGeneAlias = allGeneAlias;
	    
	}
	public AllGeneAliasLookup[] getAllGeneAlias(){
	    return this.allGeneAlias;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
	    ActionErrors errors = new ActionErrors();
	    UIFormValidator.validateGeneSymbolisNotEmpty(quickSearchName, errors);
		return errors;

	}

    /* (non-Javadoc)
     * @see gov.nih.nci.nautilus.ui.struts.form.GeneValidator#setGeneSymbol(java.lang.String)
     */
    public void setGeneSymbol(String geneSymbol) {
        if(geneSymbol != null){
            geneSymbol = geneSymbol.trim();
        }
       this.quickSearchName = geneSymbol;
        
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.nautilus.ui.struts.form.GeneValidator#getGeneSymbol()
     */
    public String getGeneSymbol() {
        return this.quickSearchName;
    }
	
}