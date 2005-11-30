package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.enumeration.ClinicalFactorType;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator;
import gov.nih.nci.rembrandt.service.findings.strategies.StrategyHelper;
import gov.nih.nci.rembrandt.util.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;

public class ClinicalDataValidationTest extends TestCase {
	private Collection<SampleIDDE> sampleIds = Collections.EMPTY_LIST;
	private Collection<SampleIDDE> validatedSamples = Collections.EMPTY_LIST;
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext.init();
		//create a ClinicalDataQuery to contrain by Insitition group
		 ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		 SampleCriteria sc = new SampleCriteria();
		 sc.setSampleID(new SampleIDDE("HF0017"));
		 //clinicalDataQuery.setSampleIDCrit(sc);
		Resultant resultant;
		try {
			CompoundQuery compoundQuery = new CompoundQuery(clinicalDataQuery);
			compoundQuery.setAssociatedView(ViewFactory
                .newView(ViewType.CLINICAL_VIEW));
			resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
			if(resultant != null){				
			sampleIds = StrategyHelper.extractSampleIDDEs(resultant.getResultsContainer());
			}
  		}
  		catch (Throwable t)	{
  			throw new Exception("Error executing clinical query/n"+t.getMessage());
  		}
  		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator.getValidatedSamples(Collection<SampleIDDE>, Collection<ClinicalFactorType>)'
	 */
	public void testGetValidatedSamples() {
		Collection<ClinicalFactorType> clinicalFactors = new ArrayList<ClinicalFactorType>();
		try {
			//Test all samples with No ClinicalFactorTypes
			validatedSamples = ClinicalDataValidator.getValidatedSamples(sampleIds,clinicalFactors);
			assert(validatedSamples.size() == sampleIds.size());
			System.out.println("Test all samples with No ClinicalFactorTypes");
			System.out.println("AllSamples Query:"+sampleIds.size()+" ValidatedSamples Query:"+validatedSamples.size() );

			//Add gender 
			clinicalFactors.add(ClinicalFactorType.Gender);
			validatedSamples = ClinicalDataValidator.getValidatedSamples(sampleIds,clinicalFactors);
			System.out.println("Test all samples with Gender as the ClinicalFactorTypes");
			System.out.println("AllSamples Query:"+sampleIds.size()+" ValidatedSamples Query:"+validatedSamples.size() );

			//Add gender And AgeAtDx
			clinicalFactors.add(ClinicalFactorType.AgeAtDx);
			validatedSamples = ClinicalDataValidator.getValidatedSamples(sampleIds,clinicalFactors);
			System.out.println("Test all samples with gender And AgeAtDx as the ClinicalFactorTypes");
			System.out.println("AllSamples Query:"+sampleIds.size()+" ValidatedSamples Query:"+validatedSamples.size() );
          
			//Add gender And AgeAtDx and Survival
			clinicalFactors.add(ClinicalFactorType.Survival);
			validatedSamples = ClinicalDataValidator.getValidatedSamples(sampleIds,clinicalFactors);
			System.out.println("Test all samples with gender And AgeAtDx and Survival as the ClinicalFactorTypes");
			System.out.println("AllSamples Query:"+sampleIds.size()+" ValidatedSamples Query:"+validatedSamples.size() );

			//Add gender And AgeAtDx and Survival and NeurologicalAssessment
			clinicalFactors.add(ClinicalFactorType.NeurologicalAssessment);
			validatedSamples = ClinicalDataValidator.getValidatedSamples(sampleIds,clinicalFactors);
			System.out.println("Test all samples with gender And AgeAtDx and Survival and NeurologicalAssessment as the ClinicalFactorTypes");
			System.out.println("AllSamples Query:"+sampleIds.size()+" ValidatedSamples Query:"+validatedSamples.size() );

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
