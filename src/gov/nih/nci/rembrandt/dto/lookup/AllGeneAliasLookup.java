package gov.nih.nci.rembrandt.dto.lookup;

/**
 * @author SahniH
 * Date: Feb 18, 2005
 * 
 */
public interface AllGeneAliasLookup {
    /**
     * @return Returns the alias.
     */
    public abstract String getAlias();

    /**
     * @param alias The alias to set.
     */
    public abstract void setAlias(String alias);

    /**
     * @return Returns the approvedSymbol.
     */
    public abstract String getApprovedSymbol();

    /**
     * @param approvedSymbol The approvedSymbol to set.
     */
    public abstract void setApprovedSymbol(String approvedSymbol);

    /**
     * @return Returns the approvedName.
     */
    public abstract String getApprovedName();

    /**
     * @param approvedName The approvedName to set.
     */
    public abstract void setApprovedName(String approvedName);
}