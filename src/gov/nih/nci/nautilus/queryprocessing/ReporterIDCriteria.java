package gov.nih.nci.nautilus.queryprocessing;

import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.apache.ojb.broker.query.Criteria;
import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 2, 2004
 * Time: 12:17:51 PM
 * To change this template use Options | File Templates.
 */
class ReporterIDCriteria {
    private ReportQueryByCriteria cloneIDsSubQuery;
    private ReportQueryByCriteria probeIDsSubQuery;

    public ReportQueryByCriteria getCloneIDsSubQuery() {
        return cloneIDsSubQuery;
    }

    public void setCloneIDsSubQuery(ReportQueryByCriteria cloneIDsSubQuery) {
        this.cloneIDsSubQuery = cloneIDsSubQuery;
    }

    public ReportQueryByCriteria getProbeIDsSubQuery() {
        return probeIDsSubQuery;
    }

    public void setProbeIDsSubQuery(ReportQueryByCriteria probeIDsSubQuery) {
        this.probeIDsSubQuery = probeIDsSubQuery;
    }
}
