package gov.nih.nci.rembrandt.dbbean;

import gov.nih.nci.rembrandt.dto.lookup.AllGeneAliasLookup;

/**
 * @author SahniH
 * Date: Feb 17, 2005
 * 
 */
public class AllGeneAlias implements AllGeneAliasLookup {
	private String approvedSymbol;
	private String alias;
	private String approvedName;
    /**
     * @return Returns the alias.
     */
    public String getAlias() {
        return this.alias;
    }
    /**
     * @param alias The alias to set.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
    /**
     * @return Returns the approvedSymbol.
     */
    public String getApprovedSymbol() {
        return this.approvedSymbol;
    }
    /**
     * @param approvedSymbol The approvedSymbol to set.
     */
    public void setApprovedSymbol(String approvedSymbol) {
        this.approvedSymbol = approvedSymbol;
    }
    /**
     * @return Returns the approvedName.
     */
    public String getApprovedName() {
        return this.approvedName;
    }
    /**
     * @param approvedName The approvedName to set.
     */
    public void setApprovedName(String approvedName) {
        this.approvedName = approvedName;
    }
}
