package gov.nih.nci.nautilus.queryprocessing.clinical;

import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 2, 2004
 * Time: 12:17:51 PM
 * To change this template use Options | File Templates.
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
