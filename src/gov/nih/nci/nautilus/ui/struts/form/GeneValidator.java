/*
 * Created on Mar 9, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
