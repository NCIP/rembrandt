package gov.nih.nci.rembrandt.queryservice.test;

import java.util.ArrayList;
import java.util.Collection;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ClassComparisonAnalysisCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.MultiGroupComparisonAdjustmentType;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.dto.query.ClassComparisonQuery;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.service.findings.FindingsFactory;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AnalysisQueryTest extends TestCase {
	private ClassComparisonQuery classComparisonQuery;
	private static ConvenientCache cacheManagerDelegate  = CacheManagerDelegate.getInstance();
	public AnalysisQueryTest(String string) {
		super(string);
		
	}

	public static Test suite() {
		TestSuite suite =  new TestSuite();
        suite.addTest(new AnalysisQueryTest("testCCQuery"));
        //suite.addTest(new AnalysisQueryTest("testPCAQuery"));
        //suite.addTest(new AnalysisQueryTest("testHCQuery"));


        return suite;
	}

	public static void main (String[] args) {		
		junit.textui.TestRunner.run(suite());

    }
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext.init();
		setUpCCQuery();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	private void setUpCCQuery(){
		classComparisonQuery = (ClassComparisonQuery) QueryManager.createQuery(QueryType.CLASS_COMPARISON_QUERY);
		classComparisonQuery.setQueryName("CCQuery");
		
		ClassComparisonAnalysisCriteria classComparisonAnalysisCriteria = new ClassComparisonAnalysisCriteria();
		classComparisonAnalysisCriteria.setStatisticTypeDE(new StatisticTypeDE(StatisticalMethodType.TTest));
		classComparisonAnalysisCriteria.setStatisticalSignificanceDE(new StatisticalSignificanceDE(0.5,Operator.GT,StatisticalSignificanceType.adjustedpValue));
		classComparisonAnalysisCriteria.setMultiGroupComparisonAdjustmentTypeDE(new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.FWER ));
		classComparisonQuery.setClassComparisonAnalysisCriteria(classComparisonAnalysisCriteria);
		
		ArrayPlatformCriteria arrayPlatformCriteria = 	new ArrayPlatformCriteria ();
		arrayPlatformCriteria.setPlatform(new ArrayPlatformDE(ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString()));
		classComparisonQuery.setArrayPlatformCriteria(arrayPlatformCriteria);
		
		FoldChangeCriteria foldChangeCriteria = new FoldChangeCriteria();
		foldChangeCriteria.setFoldChangeObject(new ExprFoldChangeDE.UpRegulation(new Float(2)));
		classComparisonQuery.setFoldChangeCriteria(foldChangeCriteria);
		
		Collection<ClinicalDataQuery> groupCollection= new ArrayList();
		ClinicalDataQuery group1 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		group1.setQueryName("GBM");
		DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
		diseaseCrit.setDisease(new DiseaseNameDE("GBM"));
		group1.setDiseaseOrGradeCrit(diseaseCrit);
		group1.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
		groupCollection.add(group1);
		ClinicalDataQuery group2 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		group1.setQueryName("OLIGO");
		diseaseCrit = new DiseaseOrGradeCriteria();
		diseaseCrit.setDisease(new DiseaseNameDE("OLIGO"));
		group2.setDiseaseOrGradeCrit(diseaseCrit);
		group2.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
		groupCollection.add(group2);
		classComparisonQuery.setClinicalDataQueryCollection(groupCollection);
			
	}
	public void testCCQuery(){
		FindingsFactory factory = new FindingsFactory();
		factory.createClassComparisonFinding(classComparisonQuery,"mySession","CCQuery");
		Collection results = cacheManagerDelegate.getAllFindingsResultsets("mySession");
		

	}
}
