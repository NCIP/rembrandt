package gov.nih.nci.nautilus.queryprocessing.cgh;

import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 2, 2004
 * Time: 12:17:51 PM
 * To change this template use Options | File Templates.
 */
public class CGHReporterIDCriteria {
    //private ReportQueryByCriteria cloneIDsSubQuery;
    private ReportQueryByCriteria snpProbeIDsSubQuery;

   /* public ReportQueryByCriteria getCloneIDsSubQuery() {
        return cloneIDsSubQuery;
    }

    public void setCloneIDsSubQuery(ReportQueryByCriteria cloneIDsSubQuery) {
        this.cloneIDsSubQuery = cloneIDsSubQuery;
    }
    */
    public ReportQueryByCriteria getSnpProbeIDsSubQuery() {
        return snpProbeIDsSubQuery;
    }

    public void setSnpProbeIDsSubQuery(ReportQueryByCriteria snpProbeIDsSubQuery) {
        this.snpProbeIDsSubQuery = snpProbeIDsSubQuery;
    }
}
