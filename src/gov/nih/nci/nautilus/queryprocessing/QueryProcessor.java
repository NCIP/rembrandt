package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.resultset.ResultSet;

/**
 * @author BhattarR
 */
public class QueryProcessor {
    public static ResultSet[] execute(Query query ) throws Exception {
        /* TODO: uncomment the follwoing line.  This is validate() will make sure that for
            each criteria in query object, if criteria is not null, then it is also not empty
            by calling isEmpty() method for each of the criterias */
        // query.validate();
        return query.getQueryHandler().handle(query);

    }
}
