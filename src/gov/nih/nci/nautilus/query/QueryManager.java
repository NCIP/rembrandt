package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.queryprocessing.CompoundQueryProcessor;
import gov.nih.nci.nautilus.queryprocessing.QueryProcessor;
import gov.nih.nci.nautilus.resultset.CompoundResultSet;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.resultset.Resultant;

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
	/**
	 * @return
	 */
	public static Resultant runReportSampleIdSelection(Queriable cQuery, String[] sampleIds) {
		/*This method will need to do the following:
		 * 	1- Cast the query to a compound query
		 *  2- Creat a new SampleCriteria
		 *  3- place the sampleIds into the SampleCriteria
		 *  4- apply the SampleCriteria to each of the individual
		 *  queries that make up the CompoundQuery
		 *  5- Reexecute the query setting the desrired view to a ClinicalView
		 *  6- Return the Resultant to the calling class
		 */
		return null;
	}

}
