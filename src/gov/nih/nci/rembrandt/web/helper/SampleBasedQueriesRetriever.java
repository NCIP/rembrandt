/**
 * 
 */
package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

/**
 * @author sahnih
 *
 */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class SampleBasedQueriesRetriever implements Serializable {

	/**
	 * 
	 */
    private static Logger logger = Logger.getLogger(SampleBasedQueriesRetriever.class);
    private RembrandtPresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	private Map<String,ClinicalDataQuery> predefinedQueryMap = new TreeMap();
	private List allPredefinedAndSampleSetNames;
    private LabelValueBean lvb;
    private SessionCriteriaBag sessionCriteriaBag;
    
	/**
	 *  Constructor creates the PredefinedQueries Disease Queries
	 */
	public SampleBasedQueriesRetriever() {
		createPredefinedDiseaseQueries();
	}
	private void createPredefinedDiseaseQueries(){		
		try {
			DiseaseTypeLookup[] myDiseaseTypes =LookupManager.getDiseaseType();
			if(myDiseaseTypes != null){
				for (DiseaseTypeLookup diseaseTypeLookup : myDiseaseTypes){
					DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
			        diseaseCrit.setDisease(new DiseaseNameDE(diseaseTypeLookup.getDiseaseType()));
			        ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
			        clinicalDataQuery.setQueryName(diseaseTypeLookup.getDiseaseDesc());
			        clinicalDataQuery.setDiseaseOrGradeCrit(diseaseCrit);
			        predefinedQueryMap.put(diseaseTypeLookup.getDiseaseDesc(),clinicalDataQuery);
				}
				//Logic to handle All Disease!
		        
				ClinicalDataQuery allDiseaseQuery = predefinedQueryMap.get(RembrandtConstants.ALL_GLIOMA);
				if(allDiseaseQuery != null){
				Collection<DiseaseNameDE> allDiseases = new ArrayList<DiseaseNameDE>();
					for (DiseaseTypeLookup diseaseTypeLookup : myDiseaseTypes){
						if((!(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.NON_TUMOR)== 0) &&
								!(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNKNOWN) == 0) &&
								!(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.ALL )== 0))){
							allDiseases.add(new DiseaseNameDE(diseaseTypeLookup.getDiseaseType()));		
						}
						
					}
						DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
				        diseaseCrit.setDiseases(allDiseases);	
				        allDiseaseQuery.setDiseaseOrGradeCrit(diseaseCrit);
				        predefinedQueryMap.put(RembrandtConstants.ALL_GLIOMA,allDiseaseQuery);
				}

			}
		} catch (Exception e) {
			logger.error("Error in SampleSetBag when calling createPredefinedDiseaseQueries method");
			logger.error(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	/**
	 * This is a list of defined Disease query names. Typically used in Class Comparision Analysis queries  
	 * 
	 */
	public List getAllPredefinedDiseaseQueryNames(){
		return new ArrayList( predefinedQueryMap.keySet());
	}
    
       
	/**
	 * This is a list of samples selected from a Resultset.
	 * It is used to create a SampleCriteria that is placed in a ClinicalDataQuery that
	 * has a ClinicalView.  This gives us the ability to extract or look at these
	 * "Samples of Interest" at any time, and applying them to other queries if
	 * the user desires.    
	 * 
	 * @param sessionId --identifies the sessionCache that you want a complete
	 * list of SampleSetNames stored in.
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllSampleSetNames(String sessionID){
		List<String> sampleSetList = new ArrayList<String>(); 
		sampleSetList.addAll(cacheManager.getSampleSetNames(sessionID));
		SessionCriteriaBag mySessionCriteriaBag = cacheManager.getSessionCriteriaBag(sessionID);
		if(mySessionCriteriaBag != null){
			
			sampleSetList.addAll(mySessionCriteriaBag.getUserListNames(SessionCriteriaBag.ListType.SampleIdentifierSet));
		}
		return  sampleSetList;
	}
    
    /**This is the list of names of all the predefined queries and sample sets
     * that are in the session. There is a list of defined Disease query names that are
     * retrieved as well as samples the user has selected and saved. After all these 
     * names have been retieved, they are stored as LabelValueBeans in an ArrayList.
     * 
     * NOTE: may want only call predefined queries once. 
     * 
     @param sessionId --identifies the sessionCache that you want a complete
     * list of SampleSetNames stored in. -KR
     */
    public List getAllPredefinedAndSampleSetNames(String sessionID){
        sessionCriteriaBag = cacheManager.getSessionCriteriaBag(sessionID);
        
         List predefined = new ArrayList(getAllPredefinedDiseaseQueryNames());
         List sampleSet = new ArrayList(getAllSampleSetNames(sessionID));
         /*
          * TODO:Change --- For now...we are using the "old way" of retrieving sampleSet names by calling
          * the method getAllSampleSetNames(String) above. In the near future,
          * sampleSets (lists) will be saved as a "type" and stored as a userList like so: 
          * List sampleSet = new ArrayList(sessionCriteriaBag.getUsetListNames(ListType.SampleIdentifierSet));
          */
                   allPredefinedAndSampleSetNames = new ArrayList();
                       for(int i =0; i < predefined.size(); i++){
                           lvb = new LabelValueBean((String)predefined.get(i),(String)predefined.get(i));
                           allPredefinedAndSampleSetNames.add(lvb);
                       }
                       for(int i =0; i < sampleSet.size(); i++){
                           lvb = new LabelValueBean((String)sampleSet.get(i),(String)sampleSet.get(i));
                           allPredefinedAndSampleSetNames.add(lvb);
                       }
        
        return allPredefinedAndSampleSetNames;
        }
    
	/**
	 * This method, when given a sessionId and a queryName, will check the 
	 * session cache for the stored ClinicalDataQuery.  If it is not stored or 
	 * causes an exception it will return Null.  If you are getting Null values,
	 * when you know that you have stored the query in the sessionCache, check
	 * the log files as any exceptions will be written there.
	 *   
	 * @param sessionId --the session that should have the query stored
	 * @param queryName --the query that is desired
	 * @return ClinicalDataQuery
	 */
	public ClinicalDataQuery getQuery(String sessionID, String queryName){
		if( sessionID != null &&  queryName != null){
            sessionCriteriaBag = cacheManager.getSessionCriteriaBag(sessionID);
            
			if(predefinedQueryMap.containsKey(queryName)){
				return predefinedQueryMap.get(queryName);
			}
			else if(sessionCriteriaBag.getUserList(ListType.SampleIdentifierSet,queryName)!= null){
                ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		        clinicalDataQuery.setQueryName(queryName);
		        clinicalDataQuery.setSampleIDCrit(sessionCriteriaBag.getSampleCriteria(queryName));

				return clinicalDataQuery;
			}
			else {
				/*
				 * This is where we will retrieve a result set of sample ids that was created 
				 * when the report waas saved.

				 */
				CompoundQuery resultSetCompoundQuery = (CompoundQuery) cacheManager.getQuery(sessionID, queryName);
	        	/*
	    		 * At this time there is only a single query in any result set.
	    		 * So let me grab that single query out of the compound query 
	    		 * and extract it's sampleCriteria to apply to the compoundQuery
	    		 */
		    		Queriable query1 = resultSetCompoundQuery.getAssociatiedQueries()[0];
		    		SampleCriteria sampleCrit = null;
		    		if(query1 instanceof Query){
		    			sampleCrit = ((Query)query1).getSampleIDCrit();
				        ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
				        clinicalDataQuery.setQueryName(queryName);
				        clinicalDataQuery.setSampleIDCrit(sampleCrit);

						return clinicalDataQuery;
		    		}

				}
		}
		return null;
	}
}
