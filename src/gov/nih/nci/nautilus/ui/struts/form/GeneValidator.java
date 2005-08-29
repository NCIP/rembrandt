/*
 * Created on Mar 9, 2005
 */
package gov.nih.nci.nautilus.ui.struts.form;

import gov.nih.nci.nautilus.lookup.AllGeneAliasLookup;

/**
 * @author rossok
 *
 *
 */
public interface GeneValidator {
    public void setGeneSymbol(String geneSymbol);
    public String getGeneSymbol();
    public void setAllGeneAlias(AllGeneAliasLookup[] allGeneAlias);
    public AllGeneAliasLookup[] getAllGeneAlias();
}
