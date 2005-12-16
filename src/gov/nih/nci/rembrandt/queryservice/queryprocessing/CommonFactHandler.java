package gov.nih.nci.rembrandt.queryservice.queryprocessing;

import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.CopyNumberSampleView;
import gov.nih.nci.caintegrator.dto.view.GeneExprDiseaseView;
import gov.nih.nci.caintegrator.dto.view.GeneExprSampleView;
import gov.nih.nci.caintegrator.util.CaIntegratorConstants;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GEFactHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.query.Criteria;

/**
 * @author BhattarR
 */
public class CommonFactHandler {
    public static void addDiseaseCriteria(Query query, Class beanClass, PersistenceBroker pb, Criteria criteria)
    throws Exception {
        DiseaseOrGradeCriteria diseaseCrit = query.getDiseaseOrGradeCriteria();
        if (diseaseCrit != null) {
            ArrayList diseasesTypes = new ArrayList();
            for (Iterator iterator = diseaseCrit.getDiseases().iterator(); iterator.hasNext();)
                diseasesTypes.add(((DiseaseNameDE) iterator.next()).getValueObject());
            String columnName = QueryHandler.getColumnName(pb, DiseaseNameDE.class.getName(), beanClass.getName());
            criteria.addIn(columnName, diseasesTypes);
        }
    }
    public static void addSingleDiseaseCriteria(DiseaseNameDE disease, Class beanClass, PersistenceBroker pb, Criteria criteria)
    throws Exception {
        //DiseaseOrGradeCriteria diseaseCrit = query.getDiseaseOrGradeCriteria();
        String columnName = QueryHandler.getColumnName(pb, DiseaseNameDE.class.getName(), beanClass.getName());
        if (disease != null) {
            criteria.addEqualTo(columnName, disease.getValueObject());
        }
    }

                        
    public static void addSampleIDCriteria(Query query, Class beanClass, Criteria criteria)
    throws Exception {
        SampleCriteria sampleIDCrit = query.getSampleIDCrit();
        if (sampleIDCrit != null) {
            ArrayList sampleIDs = new ArrayList();
            for (Iterator iterator = sampleIDCrit.getSampleIDs().iterator(); iterator.hasNext();)
                sampleIDs.add(((SampleIDDE) iterator.next()).getValueObject());
            String sampleIDAttr = QueryHandler.getAttrNameForTheDE(SampleIDDE.class.getName(), beanClass.getName());
            Criteria c = new Criteria();
            c.addIn(sampleIDAttr, sampleIDs);
            criteria.addAndCriteria(c);
        }
    }

    public static void addAccessCriteria(Query query, Class beanClass, Criteria criteria)
    throws Exception {
    	//This is a work around interceptor to provide proper access for GE Disease Queries
    	
        InstitutionCriteria institCrit = interceptorForGEDiseaseGroupQuery(query);
        if (institCrit  != null) {
            ArrayList<Long> instIDs = new ArrayList<Long>();
            Collection<InstitutionDE> institutes = institCrit.getInstitutions();

            for (InstitutionDE institution : institutes) {
               instIDs.add(institution.getValueObject());
            }

            String instIDAttr = QueryHandler.getAttrNameForTheDE(InstitutionDE.class.getName(), beanClass.getName());
            Criteria c = new Criteria();
            c.addIn(instIDAttr, instIDs);
            criteria.addAndCriteria(c);
        }
    }
	private static InstitutionCriteria  interceptorForGEDiseaseGroupQuery(Query query) {
		// sahnih
		//This methods allows proper selection of access Codes for GE Group Queries
//		IF AFTER LOGIN ID = 8 ONLY THEN ID = 8 ONLY
//		IF ID = 8 + ANY ONE INSTITUTION_ID THEN THEN ID = THAT ONE INSTITUTION_ID
//		IF ID = 8 + 1 + 5 THEN ID = 5
//		IF ID = 8 + ALL OTHER INSTITUTION_IDS THEN ID = 13
		InstitutionCriteria institCrit = query.getInstitutionCriteria ();  
		InstitutionCriteria newInstitCriteria = new InstitutionCriteria();
		Collection<InstitutionDE> updatesInstitutes = new ArrayList<InstitutionDE>();
		if( 	query instanceof GeneExpressionQuery  &&
				query.getAssociatedView() instanceof GeneExprDiseaseView){
			if (institCrit  != null) {
		            Collection<InstitutionDE> institutes = institCrit.getInstitutions();		            
		            if(institutes!= null ){
		            	//	change to public
		            	if(institutes.size()== 1 ){
		            		for (InstitutionDE institution : institutes) {
			 		               if(!(institution.getValueObject().equals(CaIntegratorConstants.PUBLIC_ACCESS))){
				            			InstitutionDE institutionDE = new InstitutionDE("PUBLIC",CaIntegratorConstants.PUBLIC_ACCESS);
					 		            updatesInstitutes.add(institutionDE);
			 		               		}
			            		}
			 		    }
		            	// remove public
		            	else if(institutes.size() == 2){
		            		for (InstitutionDE institution : institutes) {
		 		               if(!(institution.getValueObject().equals(CaIntegratorConstants.PUBLIC_ACCESS))){
		 		            	  updatesInstitutes.add(institution);
		 		               		}
		            		}
		            	}
		            	// remove public + Henry Ford
		            	else if(institutes.size() == 3){
		            		InstitutionDE institutionDE = new InstitutionDE("NABTT/HENRY FORD",new Long(5));
		 		            updatesInstitutes.add(institutionDE);
		            	}
		            	//change to super
		            	else if(institutes.size() > 3){
		            		InstitutionDE institutionDE = new InstitutionDE("ALL INSTITITIONS",new Long(13));
			 		        updatesInstitutes.add(institutionDE);
			 		    }
		            	
		            }
		        }
		}
		if(updatesInstitutes.size()>0){
			newInstitCriteria.setInstitutions(updatesInstitutes);	
			return newInstitCriteria;    		
    	}
			return institCrit;
	}

}
