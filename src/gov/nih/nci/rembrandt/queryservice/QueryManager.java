package gov.nih.nci.rembrandt.queryservice;

import gov.nih.nci.caintegrator.dto.query.Queriable;
import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.CompoundQueryProcessor;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryProcessor;
import gov.nih.nci.rembrandt.queryservice.resultset.CompoundResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

/**
 * @author BhattarR
 */
public class QueryManager {

    public static Query createQuery(QueryType typeOfQuery) {
        return QueryFactory.newQuery(typeOfQuery);
    }
    public static ResultSet[] executeQuery(Queriable queryToExecute) throws Exception {
    	ResultSet[] resultset = null;
    	if(queryToExecute instanceof Query){
    		resultset = QueryProcessor.execute((Query) queryToExecute);
    	}
    	return resultset;
    }
    public static CompoundResultSet executeCompoundQuery(Queriable queryToExecute) throws Exception {
    	CompoundResultSet compoundResultset = null;
    	if (queryToExecute instanceof CompoundQuery){
    		compoundResultset = CompoundQueryProcessor.execute((CompoundQuery) queryToExecute);
    	}
    	
        return compoundResultset;
    }

}
