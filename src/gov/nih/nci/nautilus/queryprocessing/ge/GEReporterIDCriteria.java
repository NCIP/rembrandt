package gov.nih.nci.nautilus.queryprocessing.ge;

import java.util.ArrayList;

import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 2, 2004
 * Time: 12:17:51 PM
 * To change this template use Options | File Templates.
 */
public class GEReporterIDCriteria {
    private ArrayList multipleCloneIDsSubQueries = new ArrayList();
    private ArrayList multipleProbeIDsSubQueries = new ArrayList();
    public ArrayList getMultipleCloneIDsSubQueries() {
        return multipleCloneIDsSubQueries;
    }

    public void setMultipleCloneIDsSubQueries(ArrayList multipleCloneIDsSubQueries) {
        this.multipleCloneIDsSubQueries = multipleCloneIDsSubQueries;
    }

    public ArrayList getMultipleProbeIDsSubQueries() {
        return multipleProbeIDsSubQueries;
    }

    public void setMultipleProbeIDsSubQueries(ArrayList multipleProbeIDsSubQueries) {
        this.multipleProbeIDsSubQueries = multipleProbeIDsSubQueries;
    }


    public void setCloneIDsSubQuery(ReportQueryByCriteria cloneIDsSubQuery) {
        multipleCloneIDsSubQueries.add(cloneIDsSubQuery);
        //this.cloneIDsSubQuery = cloneIDsSubQuery;
    }


    public void setProbeIDsSubQuery(ReportQueryByCriteria probeIDsSubQuery) {
        multipleProbeIDsSubQueries.add(probeIDsSubQuery);
        //this.probeIDsSubQuery = probeIDsSubQuery;
    }
}
