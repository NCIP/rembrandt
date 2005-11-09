package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.UserPreferencesBean;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

import uk.ltd.getahead.dwr.ExecutionContext;

public class UserPreferencesHelper{
      
    private PresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
    private List geneSetList = new ArrayList();
    private List reporterSetList = new ArrayList();
    private HttpSession session;
    private String sessionId;
    private UserPreferencesBean userPreferencesBean;
    private String name;
    
    public UserPreferencesHelper(HttpSession session){
        userPreferencesBean = (UserPreferencesBean) session.getAttribute(RembrandtConstants.USER_PREFERENCES);        
        
    }
    public UserPreferencesHelper(){       
        session = ExecutionContext.get().getSession(false); 
        sessionId = ExecutionContext.get().getSession(false).getId(); 
        userPreferencesBean = (UserPreferencesBean) session.getAttribute(RembrandtConstants.USER_PREFERENCES);        
               
    }
    public List updateGeneSetList(){        
            geneSetList = cacheManager.getSampleSetNames(sessionId);            
        return geneSetList;
    }
    public List updateReporterSetList(){
          reporterSetList = cacheManager.getSampleSetNames(sessionId);
        return reporterSetList;
    }
   
    /**
     * @return Returns the variancePercentile.
     */
    public int getVariancePercentile() {
        return userPreferencesBean.getVariancePercentile();
    }

    /**
     * @param variancePercentile The variancePercentile to set.
     */
    public void setVariancePercentile(int variancePercentile) {
        userPreferencesBean.setVariancePercentile(variancePercentile);
    }


    /**
     * @return Returns the geneSetName.
     */
    public String getGeneSetName() {
        return userPreferencesBean.getGeneSetName();
    }


    /**
     * @param geneSetName The geneSetName to set.
     */
    public void setGeneSetName(String geneSetName) {
        userPreferencesBean.setGeneSetName(geneSetName);
    }


    /**
     * @return Returns the reporterSetName.
     */
    public String getReporterSetName() {
        return userPreferencesBean.getReporterSetName();
    }


    /**
     * @param reporterSetName The reporterSetName to set.
     */
    public void setReporterSetName(String reporterSetName) {
        userPreferencesBean.setReporterSetName(reporterSetName);
    }
    
    
    
}