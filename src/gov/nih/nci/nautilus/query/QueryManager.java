package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.queryprocessing.QueryProcessor;
import gov.nih.nci.nautilus.resultset.ResultSet;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 2:40:15 PM
 * To change this template use Options | File Templates.
 */
public class QueryManager {

    public static Query createQuery(QueryType typeOfQuery) {
        return QueryFactory.newQuery(typeOfQuery);
    }
    public static ResultSet[] executeQuery(Queriable queryToExecute) throws Exception {
        return QueryProcessor.execute((Query) queryToExecute);
    }

}
