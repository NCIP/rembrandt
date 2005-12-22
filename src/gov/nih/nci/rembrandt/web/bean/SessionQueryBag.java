package gov.nih.nci.rembrandt.web.bean;

/*
 *  @author: SahniH
 *  Created on Sep 24, 2004
 *  @version $ Revision: 1.0 $
 * 
 *	The caBIO Software License, Version 1.0
 *
 *	Copyright 2004 SAIC. This software was developed in conjunction with the National Cancer 
 *	Institute, and so to the extent government employees are co-authors, any rights in such works 
 *	shall be subject to Title 17 of the United States Code, section 105.
 * 
 *	Redistribution and use in source and binary forms, with or without modification, are permitted 
 *	provided that the following conditions are met:
 *	 
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above 
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
 *	other materials provided with the distribution.
 * 
 *	2.  The end-user documentation included with the redistribution, if any, must include the 
 *	following acknowledgment:
 *	
 *	"This product includes software developed by the SAIC and the National Cancer 
 *	Institute."
 *	
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the 
 *	software itself, wherever such third-party acknowledgments normally appear.
 *	 
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or 
 *	promote products derived from this software.
 *	 
 *	4. This license does not authorize the incorporation of this software into any proprietary 
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either 
 *	NCI or SAIC-Frederick.
 *	 
 *	
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED 
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE 
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR 
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *	
 */

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.action.ActionForm;

/**
 * @author SahniH, BauerD, LandyR, RossoK 
 */
public class SessionQueryBag implements Serializable {
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
 }
