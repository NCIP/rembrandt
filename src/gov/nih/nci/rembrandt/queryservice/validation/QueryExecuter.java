package gov.nih.nci.rembrandt.queryservice.validation;


import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.dbbean.PriorRadiationtherapy;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.metadata.ClassDescriptor;
import org.apache.ojb.broker.metadata.DescriptorRepository;
import org.apache.ojb.broker.metadata.FieldDescriptor;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * This class provide a single point for UI related classes to 
 * get lookup data (data that a user can select to mofify a query)
 * It uses that BusinessCacheManager to determine if the data
 * has already been loaded.  If not it executes the query and
 * stores it in the ApplicationCache, else it retrives that data
 * from the Cache and returns it to the UI.
 * 
 * @author SahniH
 */
public class QueryExecuter{
    private static Logger logger = Logger.getLogger(QueryExecuter.class);

	public static final String NO_CACHE = "NoCache";
	private static PresentationTierCache presentationTierCache;
	
	
	/**
	 * Performs the actual lookup query.  Gets the application
	 * PersistanceBroker and then passes to the 
	 * @param bean the lookup class 
	 * @param crit the criteria for the lookup
	 * @return the collection of lookup values
	 * @throws Exception
	 */
	 
	public  static Collection executeQuery(Class bean, Criteria crit, String lookupType, boolean distinct)throws Exception{
		  
		presentationTierCache = ApplicationFactory.getPresentationTierCache();
		Collection resultsetObjs = presentationTierCache.checkLookupCache(lookupType);
		if(resultsetObjs == null) {
			logger.debug("LookupType "+lookupType+" was not found in ApplicationCache");
			PersistenceBroker broker = PersistenceBrokerFactory.defaultPersistenceBroker();
			broker.clearCache();
		    resultsetObjs = createQuery(bean, crit, broker, distinct);
            if(!lookupType.equals(QueryExecuter.NO_CACHE)){  //Never cache Quick search type queries
            	presentationTierCache.addToPresentationCache(lookupType,(Serializable)resultsetObjs);
            }
		    broker.close();
		    
		}else {
			logger.debug("LookupType "+lookupType+" found in ApplicationCache");
			
		}
	    return resultsetObjs;
	     
	}
	private static Collection createQuery(Class bean, Criteria crit, PersistenceBroker broker, boolean distinct) throws Exception{
			
			Collection resultsetObjs = null;
	        Query exprQuery = QueryFactory.newQuery(bean, crit,distinct);
	       
	        resultsetObjs = broker.getCollectionByQuery(exprQuery);
	        logger.debug("Got " + resultsetObjs.size() + " resultsetObjs objects.");
	        return resultsetObjs;
	}

	/**
	 * @return Returns the cytobands. 
	 */ 
 
	public static  Collection lookUpClinicalQueryTermValues(Class bean, Criteria crit,String fieldToSelect, boolean distinct) {
		
		PersistenceBroker broker = PersistenceBrokerFactory.defaultPersistenceBroker();
		crit.addColumnNotNull(fieldToSelect);
	    ReportQueryByCriteria q = QueryFactory.newReportQuery(bean, crit, true);	
        q.setAttributes(new String[] {fieldToSelect});  
        Iterator iter =  broker.getReportQueryIteratorByQuery(q); 
        Collection col = new ArrayList();    
      
        while (iter.hasNext()) {
            Object[] values =  (Object[]) iter.next();
            col.add(values[0]);
         }
       broker.close();
       return col;
    }
}
