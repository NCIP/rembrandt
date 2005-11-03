package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.rembrandt.cache.BusinessCacheManager;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

import uk.ltd.getahead.dwr.ExecutionContext;

public class DefaultsHelper{
    private static List sessionGeneBag = new ArrayList();
    private static List sessionReporterBag = new ArrayList();
    private static int variancePercentile = 70;
    
    private PresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
    private HttpSession sessionId = ExecutionContext.get().getSession(false);

    
    public DefaultsHelper(){
        
        // generic constructor
        sessionGeneBag.add("geneList1");
        sessionGeneBag.add("geneList2");
        sessionReporterBag.add("reporterList1");
        sessionReporterBag.add("reporterList2");
        
    }

    /**
     * @return Returns the sessionGeneBag.
     */
    public List getSessionGeneBag() {
        //this will retrieve saved and/or uploaded gene lists
            return sessionGeneBag;
        }

    

    /**
     * @param sessionGeneBag The sessionGeneBag to set.
     */
    public void setSessionGeneBag(List sessionGeneBag) {
        //this will set saved and/or uploaded gene lists
        this.sessionGeneBag = sessionGeneBag;
    }

    /**
     * @return Returns the sessionReporterBag.
     */
    public static List getSessionReporterBag() {
        return sessionReporterBag;
    }

    /**
     * @param sessionReporterBag The sessionReporterBag to set.
     */
    public static void setSessionReporterBag(List sessionReporterBag) {
        DefaultsHelper.sessionReporterBag = sessionReporterBag;
    }

    /**
     * @return Returns the variancePercentile.
     */
    public static int getVariancePercentile() {
        return variancePercentile;
    }

    /**
     * @param variancePercentile The variancePercentile to set.
     */
    public static void setVariancePercentile(int variancePercentile) {
        DefaultsHelper.variancePercentile = variancePercentile;
    }
    
    
    
}