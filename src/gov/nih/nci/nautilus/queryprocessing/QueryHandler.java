package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.Query;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 3:06:52 PM
 * To change this template use Options | File Templates.
 */
abstract public interface QueryHandler {
     abstract void handle(Query query);
}
