package gov.nih.nci.nautilus.queryprocessing.clinical;

import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * @author BhattarR
 */
public class BIOSpecimenIDCriteria {
    private ReportQueryByCriteria bioSpecimenIDSubQuery;

    public ReportQueryByCriteria getBioSpecimenIDSubQuery() {
        return bioSpecimenIDSubQuery;
    }

    public void setBioSpecimenIDSubQuery(ReportQueryByCriteria bioSpecimenIDSubQuery) {
        this.bioSpecimenIDSubQuery = bioSpecimenIDSubQuery;
    }
}
