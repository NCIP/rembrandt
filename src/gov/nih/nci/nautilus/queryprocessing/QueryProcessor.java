package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.Query;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 2:38:58 PM
 * To change this template use Options | File Templates.
 */
public class QueryProcessor {
    public static Map execute(Query query ) throws Exception {
        /* TODO: uncomment the follwoing line.  This is validate() will make sure that for
            each criteria in query object, if criteria is not null, then it is also not empty
            by calling isEmpty() method for each of the criterias */
        // query.validate();
        return query.getQueryHandler().handle(query);

    }
}
