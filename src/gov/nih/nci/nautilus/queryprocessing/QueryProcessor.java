package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.Query;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 2:38:58 PM
 * To change this template use Options | File Templates.
 */
public class QueryProcessor {
    public static void execute(Query q) throws Exception {
         q.getQueryHandler().handle(q);

    }
}
