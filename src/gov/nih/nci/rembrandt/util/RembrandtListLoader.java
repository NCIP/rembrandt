package gov.nih.nci.rembrandt.util;

import gov.nih.nci.caintegrator.application.lists.ListLoader;
import gov.nih.nci.caintegrator.application.lists.ListManager;
import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBean;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.dto.lookup.DiseaseTypeLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.rembrandt.service.findings.strategies.StrategyHelper;
import gov.nih.nci.rembrandt.web.helper.InsitutionAccessHelper;
import gov.nih.nci.rembrandt.web.helper.RembrandtListValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class RembrandtListLoader extends ListLoader {
    private static Logger logger = Logger.getLogger(RembrandtListLoader.class);    

    public RembrandtListLoader() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static UserListBean loadDiseaseGroups(UserListBean userListBean, HttpSession session) throws OperationNotSupportedException{
        ListManager listManager = new ListManager();
        List<String> allSamplesList = new ArrayList<String>();
        List<String> allGliomaSamplesList = new ArrayList<String>();
        /**
         * this section loops through all REMBRANDTs disease groups found
         * in the getDiseaseType below. Based on credentials, queries are
         * run to return sample according to each disease and made into 
         * default user lists.
         */
		try {
			DiseaseTypeLookup[] myDiseaseTypes =LookupManager.getDiseaseType();
			if(myDiseaseTypes != null){
				for (DiseaseTypeLookup diseaseTypeLookup : myDiseaseTypes){
					//1. Get the sample Ids from the each disease type
					Collection<InstitutionDE> insitutions = InsitutionAccessHelper.getInsititutionCollection(session);
					List<SampleIDDE> sampleIDDEs = LookupManager.getSampleIDDEs(diseaseTypeLookup.getDiseaseDesc(),insitutions);
			        //2. Extracts sampleIds as Strings
			        Collection<String> sampleIDs = StrategyHelper.extractSamples(sampleIDDEs);
			        List<String> pdids = new ArrayList<String>(sampleIDs);
			        RembrandtListValidator listValidator = new RembrandtListValidator(ListSubType.Default, ListType.PatientDID, pdids);
			        if(sampleIDs != null){
			            //create userlist with valid samples included
			            UserList myList = listManager.createList(ListType.PatientDID,diseaseTypeLookup.getDiseaseType(),pdids,listValidator);
			            if(!myList.getList().isEmpty()){
			               myList.setListSubType(ListSubType.Default);
			               /**
			                * add valid samples to allSamplesList to be created last.
			                * Do not add unknown and unclassified samples. 
			                */
			               if(!(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNKNOWN)==0)
			                       && !(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.UNCLASSIFIED)==0)){
			                   allSamplesList.addAll(myList.getList());
			                   if(!(diseaseTypeLookup.getDiseaseType().compareToIgnoreCase(RembrandtConstants.NON_TUMOR)==0)){
			                	   allGliomaSamplesList.addAll(myList.getList());
				               }
			               }
			               
			               //add my list to the userListBean
			                userListBean.addList(myList);
			            }
			        }
				}
			}
		} catch (OperationNotSupportedException e1) {
			logger.error(e1);
		} catch (Exception e1) {
			logger.error(e1);
		}
        
         //now add the all samples userlist
        if(!allSamplesList.isEmpty()){

            RembrandtListValidator listValidator = new RembrandtListValidator(ListSubType.Default, ListType.PatientDID, allGliomaSamplesList);
            UserList myAllGliomaSampleList = listManager.createList(ListType.PatientDID,RembrandtConstants.ALL_GLIOMA,allGliomaSamplesList,listValidator); 
            myAllGliomaSampleList.setListSubType(ListSubType.Default);
            userListBean.addList(myAllGliomaSampleList);
            
            listValidator = new RembrandtListValidator(ListSubType.Default, ListType.PatientDID, allSamplesList);
            UserList myAllSampleList = listManager.createList(ListType.PatientDID,RembrandtConstants.ALL,allSamplesList,listValidator); 
            myAllSampleList.setListSubType(ListSubType.Default);
            userListBean.addList(myAllSampleList);
        }
        return userListBean;        
    }
}
