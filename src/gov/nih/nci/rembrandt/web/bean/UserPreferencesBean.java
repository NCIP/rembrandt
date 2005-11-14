package gov.nih.nci.rembrandt.web.bean;

import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.ExecutionContext;

public class UserPreferencesBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private String geneSetName = "";
    private String reporterSetName = "";
    private int variancePercentile = 70;
    
    public UserPreferencesBean(){
        
        
    }
    
    /**
     * @return Returns the geneSetName.
     */
    public String getGeneSetName() {
        return geneSetName;
    }



    /**
     * @param geneSetName The geneSetName to set.
     */
    public void setGeneSetName(String geneSetName) {
        this.geneSetName = geneSetName;
    }



    /**
     * @return Returns the reporterSetName.
     */
    public String getReporterSetName() {
        return reporterSetName;
    }



    /**
     * @param reporterSetName The reporterSetName to set.
     */
    public void setReporterSetName(String reporterSetName) {
        this.reporterSetName = reporterSetName;
    }



    /**
     * @return Returns the variancePercentile.
     */
    public int getVariancePercentile() {
        return variancePercentile;
    }



    /**
     * @param variancePercentile The variancePercentile to set.
     */
    public void setVariancePercentile(int variancePercentile) {
        this.variancePercentile = variancePercentile;
    }



    

   
    
    
    
}