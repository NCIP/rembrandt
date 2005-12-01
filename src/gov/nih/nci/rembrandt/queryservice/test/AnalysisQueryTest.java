package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.ClusterTypeDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.DistanceMatrixTypeDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.LinkageMethodTypeDE;
import gov.nih.nci.caintegrator.dto.de.MultiGroupComparisonAdjustmentTypeDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.de.StatisticTypeDE;
import gov.nih.nci.caintegrator.dto.de.StatisticalSignificanceDE;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;
import gov.nih.nci.caintegrator.dto.query.PrincipalComponentAnalysisQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.enumeration.*;
import gov.nih.nci.caintegrator.exceptions.FrameworkException;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * This test case was written to simulate a Front End call to the Middle-Tier to 
 * create a Class Comparison Finding
 * 
 * @author BauerD, SahniH
 *
 */

public class AnalysisQueryTest extends TestCase {
	private ClassComparisonQueryDTO classComparisonQueryDTO;
	private PrincipalComponentAnalysisQueryDTO pcaQueryDTO;
    private HierarchicalClusteringQueryDTO hcQueryDTO;
	private static BusinessTierCache businessTierCache  = ApplicationFactory.getBusinessTierCache();
	public AnalysisQueryTest(String string) {
		super(string);
		
	}

	public static Test suite() {
		TestSuite suite =  new TestSuite();
        //suite.addTest(new AnalysisQueryTest("testCCQueryCompleted"));       
        //suite.addTest(new AnalysisQueryTest("testPCAQueryCompleted"));
        //suite.addTest(new AnalysisQueryTest("testCCQueryError")); 
        suite.addTest(new AnalysisQueryTest("testHCQueryCompleted"));


        return suite;
	}

	public static void main (String[] args) {		
		junit.textui.TestRunner.run(suite());

    }
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext.init();
		setUpCCQuery();
		setUpPCAQuery();
        setUpHCQuery();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	private void setUpCCQuery(){
		classComparisonQueryDTO = (ClassComparisonQueryDTO)ApplicationFactory.newQueryDTO(QueryType.CLASS_COMPARISON_QUERY);
		classComparisonQueryDTO.setQueryName("CCQuery");
		classComparisonQueryDTO.setStatisticTypeDE(new StatisticTypeDE(StatisticalMethodType.TTest));
		classComparisonQueryDTO.setStatisticalSignificanceDE(new StatisticalSignificanceDE(0.5,Operator.GT,StatisticalSignificanceType.adjustedpValue));
		classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.FWER ));
		classComparisonQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString()));
		classComparisonQueryDTO.setExprFoldChangeDE(new ExprFoldChangeDE.UpRegulation(new Float(2)));
		Collection<InstitutionDE> insts = new ArrayList<InstitutionDE>();
        insts.add(new InstitutionDE("HENRY FORD(RETRO)",new Long(1)));
		classComparisonQueryDTO.setInstitutionDEs(insts);
		Collection<ClinicalQueryDTO> groupCollection= new ArrayList<ClinicalQueryDTO>();
		//Create ClinicalQueryDTO 1 (Class 1) for the class comparison
		ClinicalDataQuery group1 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		group1.setQueryName("OLIGO_GROUP");
		DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
		diseaseCrit.setDisease(new DiseaseNameDE("OLIGODENDROGLIOMA"));
		group1.setDiseaseOrGradeCrit(diseaseCrit);
		SampleCriteria sampleCriteria = new SampleCriteria();
		Collection samplesGroupA = new ArrayList();
		samplesGroupA.add(new SampleIDDE("HF0088"));
		samplesGroupA.add(new SampleIDDE("HF0120"));
		samplesGroupA.add(new SampleIDDE("HF0131"));
		samplesGroupA.add(new SampleIDDE("HF0137"));
		sampleCriteria.setSampleIDs(samplesGroupA);
		//group1.setSampleIDCrit(sampleCriteria);
		group1.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
		groupCollection.add(group1);
		
		//Create ClinicalQueryDTO 2 (Class 2) for the class comparison
		ClinicalDataQuery group2 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		group2.setQueryName("GBM_GROUP");
		diseaseCrit = new DiseaseOrGradeCriteria();
		diseaseCrit.setDisease(new DiseaseNameDE("GBM"));
		group2.setDiseaseOrGradeCrit(diseaseCrit);
		sampleCriteria = new SampleCriteria();
		Collection samplesGroupB = new ArrayList();
		samplesGroupB.add(new SampleIDDE("HF0024"));
		samplesGroupB.add(new SampleIDDE("HF0031"));
		samplesGroupB.add(new SampleIDDE("HF0048"));
		samplesGroupB.add(new SampleIDDE("HF0050"));
		sampleCriteria.setSampleIDs(samplesGroupB);
		//group2.setSampleIDCrit(sampleCriteria);
		group2.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
		groupCollection.add(group2);
		
		classComparisonQueryDTO.setComparisonGroups(groupCollection);
			
	}
	private void setUpPCAQuery(){
		pcaQueryDTO = (PrincipalComponentAnalysisQueryDTO) ApplicationFactory.newQueryDTO(QueryType.PCA_QUERY);
		pcaQueryDTO.setQueryName("PCATestQuery");
		pcaQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(70),Operator.GE));
		pcaQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString()));
		Collection<InstitutionDE> insts = new ArrayList<InstitutionDE>();
        insts.add(new InstitutionDE("HENRY FORD(RETRO)",new Long(1)));
        pcaQueryDTO.setInstitutionDEs(insts);
		//Create ClinicalQueryDTO 1 (Class 1) for the pca
		ClinicalDataQuery group1 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		group1.setQueryName("GBM_GROUP");
		DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
		diseaseCrit.setDisease(new DiseaseNameDE("GBM"));
		group1.setDiseaseOrGradeCrit(diseaseCrit);
		SampleCriteria sampleCriteria = new SampleCriteria();
		Collection samplesGroupB = new ArrayList();
		samplesGroupB.add(new SampleIDDE("HF0024"));
		samplesGroupB.add(new SampleIDDE("HF0031"));
		samplesGroupB.add(new SampleIDDE("HF0048"));
		samplesGroupB.add(new SampleIDDE("HF0050"));
		sampleCriteria.setSampleIDs(samplesGroupB);
		//group2.setSampleIDCrit(sampleCriteria);
		group1.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
		pcaQueryDTO.setComparisonGroup(group1);
		
		
			
	}
    private void setUpHCQuery(){
        hcQueryDTO = (HierarchicalClusteringQueryDTO) ApplicationFactory.newQueryDTO(QueryType.HC_QUERY);
        hcQueryDTO.setQueryName("HCTestQuery");
        hcQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(70),Operator.GE));
        hcQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString()));
        hcQueryDTO.setDistanceMatrixTypeDE(new DistanceMatrixTypeDE(DistanceMatrixType.Correlation));
        hcQueryDTO.setLinkageMethodTypeDE(new LinkageMethodTypeDE(LinkageMethodType.Average));
        hcQueryDTO.setClusterTypeDE(new ClusterTypeDE(ClusterByType.Samples));
		Collection<InstitutionDE> insts = new ArrayList<InstitutionDE>();
        insts.add(new InstitutionDE("HENRY FORD(RETRO)",new Long(1)));
        hcQueryDTO.setInstitutionDEs(insts);
            
    }
    
	public void testCCQueryCompleted(){
		RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
		Finding finding = null;
		try {
			finding = factory.createClassComparisonFinding(classComparisonQueryDTO,"mySession",classComparisonQueryDTO.getQueryName());
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
		FindingStatus status = finding.getStatus();
		assert(status == FindingStatus.Running);
		
		while(finding.getStatus() == FindingStatus.Running){
			 finding = businessTierCache.getSessionFinding(finding.getSessionId(),finding.getTaskId());
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		status = finding.getStatus();
		assert(status == FindingStatus.Completed);
	}
	public void testPCAQueryCompleted(){
		RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
		Finding finding = null;
		try {
			finding = factory.createPCAFinding(pcaQueryDTO,"mySession",pcaQueryDTO.getQueryName());
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
		FindingStatus status = finding.getStatus();
		assert(status == FindingStatus.Running);
		
		while(finding.getStatus() == FindingStatus.Running){
			 finding = businessTierCache.getSessionFinding(finding.getSessionId(),finding.getTaskId());
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		status = finding.getStatus();
		assert(status == FindingStatus.Completed);
	}
    public void testHCQueryCompleted(){
        RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
        Finding finding = null;
        try {
            finding = factory.createHCAFinding(hcQueryDTO,"mySession",hcQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        FindingStatus status = finding.getStatus();
        assert(status == FindingStatus.Running);
        
        while(finding.getStatus() == FindingStatus.Running){
             finding = businessTierCache.getSessionFinding(finding.getSessionId(),finding.getTaskId());
             try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        status = finding.getStatus();
        assert(status == FindingStatus.Completed);
    }
	public void testPCAQueryError(){
		RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
		pcaQueryDTO.setQueryName("PCAQueryError");
		Finding finding = null;
		try {
			finding = factory.createPCAFinding(pcaQueryDTO,"mySession",pcaQueryDTO.getQueryName());
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
		FindingStatus status = finding.getStatus();
		assert(status == FindingStatus.Running);
		
		while(finding.getStatus() == FindingStatus.Running){
			 finding = businessTierCache.getSessionFinding(finding.getSessionId(),finding.getTaskId());
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		status = finding.getStatus();
		assert(status == FindingStatus.Error);
	}
	public void testCCQueryError(){
		RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
		Finding finding = null;
		try {
			//create an error
			classComparisonQueryDTO.setStatisticalSignificanceDE(new StatisticalSignificanceDE(0.5,Operator.GT,StatisticalSignificanceType.adjustedpValue));
			classComparisonQueryDTO.setMultiGroupComparisonAdjustmentTypeDE(new MultiGroupComparisonAdjustmentTypeDE(MultiGroupComparisonAdjustmentType.NONE ));
			classComparisonQueryDTO.setQueryName("CC_ErrorQuery");
			finding = factory.createClassComparisonFinding(classComparisonQueryDTO,"mySession",classComparisonQueryDTO.getQueryName());
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
		FindingStatus status = finding.getStatus();
		assert(status == FindingStatus.Running);
		
		while(finding.getStatus() == FindingStatus.Running){
			 finding = businessTierCache.getSessionFinding(finding.getSessionId(),finding.getTaskId());
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		status = finding.getStatus();
		assert(status == FindingStatus.Error);
	}
	public void testPCAQuery(){
		
	}
}
