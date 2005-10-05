package gov.nih.nci.rembrandt.queryservice.queryprocessing.cgh;

import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * @author BhattarR
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
