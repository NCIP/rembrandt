/*
 * Created on Mar 9, 2005
 */
package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.rembrandt.dto.lookup.AllGeneAliasLookup;

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
