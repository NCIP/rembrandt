package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.UserPreferencesBean;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

import uk.ltd.getahead.dwr.ExecutionContext;

public class UserPreferencesHelper{
      
    private PresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
    private Collection geneSetList = new ArrayList();
    private Collection reporterSetList = new ArrayList();
    private HttpSession session;
    private String sessionId;
    private UserPreferencesBean userPreferencesBean;
    private String name;
    private SessionCriteriaBag sessionCriteriaBag;
    
    public UserPreferencesHelper(HttpSession session){
        userPreferencesBean = (UserPreferencesBean) session.getAttribute(RembrandtConstants.USER_PREFERENCES);        
        
    }
    public UserPreferencesHelper(){       
        session = ExecutionContext.get().getSession(false); 
        sessionId = ExecutionContext.get().getSession(false).getId(); 
        userPreferencesBean = (UserPreferencesBean) session.getAttribute(RembrandtConstants.USER_PREFERENCES);        
               
    }
    public Collection updateGeneSetList(){ 
            sessionCriteriaBag = cacheManager.getSessionCriteriaBag(sessionId);
            geneSetList = sessionCriteriaBag.getUserListNames(ListType.GeneIdentifierSet);            
        return geneSetList;
    }
    public Collection updateReporterSetList(){
          sessionCriteriaBag = cacheManager.getSessionCriteriaBag(sessionId);
          reporterSetList = sessionCriteriaBag.getUserListNames(ListType.CloneProbeSetIdentifierSet);
        return reporterSetList;
    }
   
    /**
     * @return Returns the variancePercentile.
     */
    public int getPCAVariancePercentile() {
        return userPreferencesBean.getPcaVariancePercentile();
    }

    /**
     * @param variancePercentile The variancePercentile to set.
     */
    public void setPCAVariancePercentile(int pcaVariancePercentile) {
        userPreferencesBean.setPcaVariancePercentile(pcaVariancePercentile);
    }
    /**
     * @return Returns the variancePercentile.
     */
    public int getHCVariancePercentile() {
        return userPreferencesBean.getHcVariancePercentile();
    }

    /**
     * @param variancePercentile The variancePercentile to set.
     */
    public void setHCVariancePercentile(int hcVariancePercentile) {
        userPreferencesBean.setHcVariancePercentile(hcVariancePercentile);
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