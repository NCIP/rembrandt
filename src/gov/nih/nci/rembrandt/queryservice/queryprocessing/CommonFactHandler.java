package gov.nih.nci.rembrandt.queryservice.queryprocessing;

import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.rembrandt.dto.query.Query;

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
        InstitutionCriteria institCrit = query.getInstitutionCriteria ();
        if (institCrit  != null) {
            ArrayList<Long> instIDs = new ArrayList<Long>();
            Collection<InstitutionDE> institutes = institCrit.getInstitutes();

            for (InstitutionDE institution : institutes) {
               instIDs.add(institution.getValueObject());
            }

            String instIDAttr = QueryHandler.getAttrNameForTheDE(InstitutionDE.class.getName(), beanClass.getName());
            Criteria c = new Criteria();
            c.addIn(instIDAttr, instIDs);
            criteria.addAndCriteria(c);
        }
    }

}
