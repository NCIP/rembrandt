package gov.nih.nci.rembrandt.queryservice.queryprocessing;

import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

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
