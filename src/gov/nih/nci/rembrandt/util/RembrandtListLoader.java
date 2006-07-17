package gov.nih.nci.rembrandt.util;

import gov.nih.nci.caintegrator.application.lists.ListLoader;
import gov.nih.nci.caintegrator.application.lists.ListManager;
import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.rembrandt.service.findings.strategies.StrategyHelper;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;
import gov.nih.nci.rembrandt.web.helper.RembrandtListValidator;
import gov.nih.nci.rembrandt.web.helper.SampleBasedQueriesRetriever;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sun.jdori.common.sco.HashSet;

public class RembrandtListLoader extends ListLoader {
    private static Logger logger = Logger.getLogger(RembrandtListLoader.class);    

    public RembrandtListLoader() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static UserListBean loadDiseaseGroups(UserListBean userListBean, HttpSession session){
        SampleBasedQueriesRetriever sampleBasedQueriesRetriever = new SampleBasedQueriesRetriever();
        Map diseaseGroupQueryMap = sampleBasedQueriesRetriever.getPredefinedQueryMap();
        ListManager listManager = new ListManager();
        
        //Set<ClinicalDataQuery> set =  diseaseGroupQueryMap.entrySet();
        for (Iterator it=diseaseGroupQueryMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            String queryName = (String) entry.getKey();
            ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) entry.getValue();
            Resultant resultant;
            
            try {
                CompoundQuery compoundQuery = new CompoundQuery(clinicalDataQuery);
                compoundQuery.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
                logger.debug("set institution for new compound query");
                compoundQuery.setInstitutionCriteria(InsitutionAccessHelper.getInsititutionCriteria(session));
                resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
                if(resultant != null) {      
                    ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
                    Viewable view = resultant.getAssociatedView();
                    if(resultsContainer != null)    {
                        if(view instanceof ClinicalSampleView){
                            try {
                                
                                //1. Get the sample Ids from the return Clinical query
                                Collection<SampleIDDE> sampleIDDEs = StrategyHelper.extractSampleIDDEs(resultsContainer);
                                //2. validate samples so that GE data exsists for these samples
                                Collection<SampleIDDE> validSampleIDDEs = DataValidator.validateSampleIds(sampleIDDEs);
                                //3. Extracts sampleIds as Strings
                                Collection<String> sampleIDs = StrategyHelper.extractSamples(validSampleIDDEs);
                                List<String> pdids = new ArrayList<String>(sampleIDs);
                                RembrandtListValidator listValidator = new RembrandtListValidator(ListSubType.Default, ListType.PatientDID, pdids);
                                if(sampleIDs != null){
                                    //3.1 add them to SampleGroup
                                    UserList myList = listManager.createList(ListType.PatientDID,queryName,pdids,listValidator);
                                    if(!myList.getList().isEmpty()){
                                       myList.setListSubType(ListSubType.Default);
                                        userListBean.addList(myList);
                                    }
                                    /**the next segment removes all valid ids and keeps the invalid ids
                                     * 
                                     */
//                                    //3.2 Find out any samples that were not processed  
//                                    Set<SampleIDDE> set = new HashSet<SampleIDDE>();
//                                    set.addAll(sampleIDDEs); //samples from the original query
//                                    //3.3 Remove all samples that are validated 
//                                    set.removeAll(validSampleIDDEs);
                                }
                            }catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        
        
        return userListBean;        
    }
}
