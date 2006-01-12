package gov.nih.nci.rembrandt.web.bean;
import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneOntologyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PathwayCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RegionCriteria;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;
import gov.nih.nci.rembrandt.web.struts.form.ComparativeGenomicForm;
import gov.nih.nci.rembrandt.web.struts.form.GeneExpressionForm;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

/**
 * @author SahniH, BauerD, LandyR, RossoK 
 */
public class SessionQueryBag implements Serializable,Cloneable {
	private static transient Logger logger = Logger.getLogger(SessionQueryBag.class);
	/*
	 * queryMap is the current map of all queries the user has created, these
	 * are not the compoundQueries and resultants that are stored in the cache.
	 * These are the queries that the user creates and later "Refines" with 
	 * other queries to generate a result set.  I know that this is confusing. 
	 * Each query is stored where key=queryName and value=some Queriable object
	 * that was created by the user in the build query pages.
	 */
	private Map<String, Query> queryMap = new TreeMap<String, Query>();
    //this map is strictly for queryDTOs
    private transient Map<String, QueryDTO> queryDTOMap = new TreeMap<String, QueryDTO>();    
	//hold form beans
	private Map<String,ActionForm> formBeanMap = new HashMap<String,ActionForm>();
	
	/* This is the current compound query that has been validated and is ready
	 * to run...
	 */
	private transient CompoundQuery compoundQuery = null;

	public void putQuery(Query query, ActionForm form) {
		if (query != null && query.getQueryName() != null) {
			queryMap.put(query.getQueryName(), query);
			formBeanMap.put(query.getQueryName(), form);
		}
	}
    public void putQueryDTO(QueryDTO queryDTO, ActionForm form) {
        if (queryDTO != null && queryDTO.getQueryName() != null) {
            queryDTOMap.put(queryDTO.getQueryName(), queryDTO);
            formBeanMap.put(queryDTO.getQueryName(), form);
        }
    }

	public Collection getQueries() {
		return queryMap.values();
	}

	public Collection getQueryNames() {
		return queryMap.keySet();
	}
    public Collection getQueryDTOs() {
        return queryDTOMap.values();
    }

    public Collection getQueryDTONames() {
        return queryDTOMap.keySet();
    }
	
	public void putQuery(Query query) {
	  queryMap.put(query.getQueryName(), query);
	}

	public void removeQuery(String queryName) {
		if (queryName != null) {
			queryMap.remove(queryName);
			formBeanMap.remove(queryName);
		}
	}

	public Query getQuery(String queryName) {
		if (queryName != null) {
			return (Query) queryMap.get(queryName);
		}
		return null;
	}
    public QueryDTO getQueryDTO(String queryName) {
        if (queryName != null) {
            return (QueryDTO) queryDTOMap.get(queryName);
        }
        return null;
    }

	public void removeAllQueries() {
		queryMap.clear();
	}

	/**
	 * @return Returns the compoundQuery.
	 */
	public CompoundQuery getCompoundQuery() {
		return this.compoundQuery;
	}

	/**
	 * @param compoundQuery
	 *            The compoundQuery to set.
	 */
	public void setCompoundQuery(CompoundQuery compoundQuery) {
		this.compoundQuery = compoundQuery;
	}

	public boolean hasCompoundQuery() {
		if (this.getCompoundQuery() != null)
			return true;
		return false;
	}

	public boolean hasQuery() {
		return (!this.getQueryNames().isEmpty());
	}
	
	/**
	 * This method will return the latest group of all genes querries available.
	 * It iterates through the current list of queries and checks for
	 * isAllGenesQuery() and stores them in the Map if they are. There is no
	 * setter for this property as it is only a subset of the current queries
	 * stored in the session.
	 * 
	 * @return -- a current Map of all the All Genes Queries
	 */
	public Map getAllGenesQueries() {
		//this map is generated from the queryMap, storing only the allGenesQueries
		Map<String, Query> allGenesQueries = new HashMap<String, Query>();
		Set keys = queryMap.keySet();
		for(Iterator i = keys.iterator();i.hasNext();) {
			Query query = (Query)queryMap.get(i.next());
			boolean possibleAllGeneQuery = false;
			if(query instanceof ComparativeGenomicQuery) {
				ComparativeGenomicQuery cgQuery = (ComparativeGenomicQuery)query;
				if(cgQuery.isAllGenesQuery()) {
					allGenesQueries.put(cgQuery.getQueryName(),cgQuery);
				}
			}else if(query instanceof GeneExpressionQuery) {
				GeneExpressionQuery geQuery = (GeneExpressionQuery)query;
				if(geQuery.isAllGenesQuery()) {
					allGenesQueries.put(geQuery.getQueryName(),geQuery);
				}
			}
		}
		return allGenesQueries;
	}
	/**
	 * Creates a new Map that will contain all the current queries that are not
	 * all gene queries.  This list is created dynamicly as the list of current
	 * queries can change at any time and we do not want a reference to a non
	 * existing query to show up when this method is called.
	 * 
	 * @return  -- a current Map of all non all genes queries.
	 */
	public Map getNonAllGeneQueries() {
		//this map is generated from the queryMap, storing only the non-allGenesQueries
		Map<String, Query> nonAllGeneQueries = new HashMap<String, Query>();
		Set keys = queryMap.keySet();
		for(Iterator i = keys.iterator();i.hasNext();) {
			Query query = (Query)queryMap.get(i.next());
			boolean possibleAllGeneQuery = false;
			if(query instanceof ComparativeGenomicQuery) {
				ComparativeGenomicQuery cgQuery = (ComparativeGenomicQuery)query;
				if(!cgQuery.isAllGenesQuery()) {
					nonAllGeneQueries.put(cgQuery.getQueryName(),cgQuery);
				}
			}else if(query instanceof GeneExpressionQuery) {
				GeneExpressionQuery geQuery = (GeneExpressionQuery)query;
				if(!geQuery.isAllGenesQuery()) {
					nonAllGeneQueries.put(geQuery.getQueryName(),geQuery);
				}
			}else {
				nonAllGeneQueries.put(query.getQueryName(), query);
			}
		}
		return nonAllGeneQueries;
		
	}

    /**
     * @return Returns the formBeanMap.
     */
    public Map getFormBeanMap() {
        return formBeanMap;
    }
    /**
     * @param formBeanMap The formBeanMap to set.
     */
    public void setFormBeanMap(Map<String,ActionForm> formBeanMap) {
        this.formBeanMap = formBeanMap;
    }
    /**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		SessionQueryBag myClone = null;
		
		myClone = new SessionQueryBag();
		Map<String, Query> clonedQueryMap = null;
	    if(queryMap != null){
	    	clonedQueryMap = new TreeMap<String, Query>();
        	Set keys = queryMap.keySet(); 
    		for(Object elementKey: keys) {
    			Query it = queryMap.get(elementKey);
    			Query q = (Query)it;
    			Query itClone = (Query)q.clone();
    			clonedQueryMap.put((String)elementKey,itClone);
    		}
        }
	    myClone.queryMap = clonedQueryMap;
	    Map<String,ActionForm> clonedformBeanMap = null;
	    if(formBeanMap != null){
	    	clonedformBeanMap = new HashMap<String,ActionForm>();
        	Set keys = formBeanMap.keySet(); 
    		for(Object elementKey: keys) {
    			ActionForm it = formBeanMap.get(elementKey);
    			ActionForm itClone = null;
    			if(it instanceof GeneExpressionForm) {
    				GeneExpressionForm gef = (GeneExpressionForm)it;
    				itClone = gef.cloneMe();
    			}else if(it instanceof ClinicalDataForm) {
    				ClinicalDataForm cdf = (ClinicalDataForm)it;
    				itClone = cdf.cloneMe();
    			}else if(it instanceof ComparativeGenomicForm) {
    				ComparativeGenomicForm cgf = (ComparativeGenomicForm)it;
    				itClone = cgf.cloneMe();
    			}else {
    				logger.error("Unsupported FormType to clone");
    			}
    			clonedformBeanMap.put((String)elementKey,itClone);
    		}
    		 myClone.formBeanMap = clonedformBeanMap;
        }
		return myClone;
	}

	private Map cloneMap(Map thisQueryMap) {
		HashMap myClone = new HashMap();
		
		return myClone;
	}

	class Handler {
	}
 }
