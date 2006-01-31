package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.analysis.messaging.PCAresultEntry;
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
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.caintegrator.service.findings.PrincipalComponentAnalysisFinding;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.resultset.annotation.GeneExprAnnotationService;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        insts.add(new InstitutionDE("PUBLIC",new Long(8)));
		classComparisonQueryDTO.setInstitutionDEs(insts);
		List<ClinicalQueryDTO> groupCollection= new ArrayList<ClinicalQueryDTO>();
		//Create ClinicalQueryDTO 1 (Class 1) for the class comparison
		ClinicalDataQuery group1 = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		group1.setQueryName("OLIGO_GROUP");
		DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
		//diseaseCrit.setDisease(new DiseaseNameDE("OLIGODENDROGLIOMA"));
		//group1.setDiseaseOrGradeCrit(diseaseCrit);
		SampleCriteria sampleCriteria = new SampleCriteria();
		Collection samplesGroupA = new ArrayList();
		samplesGroupA.add(new SampleIDDE("E09137"));
		samplesGroupA.add(new SampleIDDE("NT7"));
		samplesGroupA.add(new SampleIDDE("NT8"));
		samplesGroupA.add(new SampleIDDE("xyz"));
		samplesGroupA.add(new SampleIDDE("HF0088"));
		samplesGroupA.add(new SampleIDDE("HF0120"));
		samplesGroupA.add(new SampleIDDE("HF0131"));
		samplesGroupA.add(new SampleIDDE("HF0137"));
		sampleCriteria.setSampleIDs(samplesGroupA);
		group1.setSampleIDCrit(sampleCriteria);
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
		samplesGroupB.add(new SampleIDDE("E09137"));
		samplesGroupB.add(new SampleIDDE("NT7"));
		samplesGroupB.add(new SampleIDDE("NT8"));
		samplesGroupB.add(new SampleIDDE("xyz"));
		samplesGroupB.add(new SampleIDDE("HF0088"));
		samplesGroupB.add(new SampleIDDE("HF0120"));
		samplesGroupB.add(new SampleIDDE("HF0131"));
		samplesGroupB.add(new SampleIDDE("HF0137"));
		sampleCriteria.setSampleIDs(samplesGroupB);
		//group2.setSampleIDCrit(sampleCriteria);
		group1.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
		pcaQueryDTO.setComparisonGroup(group1);
		
		
			
	}
    private void setUpHCQuery(){
        hcQueryDTO = (HierarchicalClusteringQueryDTO) ApplicationFactory.newQueryDTO(QueryType.HC_QUERY);
        hcQueryDTO.setQueryName("HCTestQuery");
        hcQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(99),Operator.GE));
        hcQueryDTO.setArrayPlatformDE(new ArrayPlatformDE(ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString()));
        hcQueryDTO.setDistanceMatrixTypeDE(new DistanceMatrixTypeDE(DistanceMatrixType.Correlation));
        hcQueryDTO.setLinkageMethodTypeDE(new LinkageMethodTypeDE(LinkageMethodType.Average));
        hcQueryDTO.setClusterTypeDE(new ClusterTypeDE(ClusterByType.Genes));
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
				e.printStackTrace();
			}
		}
		status = finding.getStatus();
		assert(status == FindingStatus.Completed);
		displayAnnotations(finding);
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
        HCAFinding finding = null;
        try {
            finding = factory.createHCAFinding(hcQueryDTO,"mySession",hcQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        FindingStatus status = finding.getStatus();
        assert(status == FindingStatus.Running);
        
        while(finding.getStatus() == FindingStatus.Running){
             finding = (HCAFinding) businessTierCache.getSessionFinding(finding.getSessionId(),finding.getTaskId());
             try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        status = finding.getStatus();
        assert(status == FindingStatus.Completed);
        displayAnnotations(finding);
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
	public void testHCForSamplesQueryCompleted(){
        RembrandtFindingsFactory factory = new RembrandtFindingsFactory();
        HCAFinding finding = null;
        hcQueryDTO.setGeneVectorPercentileDE(new GeneVectorPercentileDE(new Double(70),Operator.GE));
        hcQueryDTO.setClusterTypeDE(new ClusterTypeDE(ClusterByType.Samples));
        try {
            finding = factory.createHCAFinding(hcQueryDTO,"mySession",hcQueryDTO.getQueryName());
        } catch (FrameworkException e) {
            e.printStackTrace();
        }
        FindingStatus status = finding.getStatus();
        assert(status == FindingStatus.Running);
        
        while(finding.getStatus() == FindingStatus.Running){
             finding = (HCAFinding) businessTierCache.getSessionFinding(finding.getSessionId(),finding.getTaskId());
             try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        status = finding.getStatus();
        assert(status == FindingStatus.Completed);
        displaySampleAnnotationsforHC((HCAFinding)finding);
    }
	public void displaySampleAnnotationsforHC(HCAFinding hCAFinding){
			List<String> sampleIds = hCAFinding.getClusteredSampleIDs();
			Map<String, SampleResultset> sampleResultsetMap;
			try {
				sampleResultsetMap = ClinicalDataValidator.getClinicalAnnotationsMapForSamples(sampleIds);
				if(sampleResultsetMap != null  && sampleIds != null){
					int count = 0;
					for(String sampleId:sampleIds){
						SampleResultset sampleResultset = sampleResultsetMap.get(sampleId);
						if(sampleResultset != null && sampleResultset.getSampleIDDE()!= null)
						System.out.println(++count+" SampleID :" +sampleResultset.getSampleIDDE().getValue().toString());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@SuppressWarnings("unchecked")
	public void displayAnnotations(Finding finding){
		if(finding instanceof HCAFinding){
			HCAFinding hCAFinding = (HCAFinding) finding;
			List<String> reportIds = hCAFinding.getClusteredReporterIDs();
			if(reportIds != null){
				try {
					List<ReporterResultset> reporterResultsets = GeneExprAnnotationService.getAnnotationsForReporters(reportIds);
					for(ReporterResultset reporterResultset: reporterResultsets){
						if(reporterResultset != null){
							System.out.println("ReporterID :" +reporterResultset.getReporter().getValue().toString());
							Collection<String> geneSymbols = (Collection<String>)reporterResultset.getAssiciatedGeneSymbols();
							if(geneSymbols != null){
								for(String geneSymbol: geneSymbols){
									System.out.println("\tAssocitaed GeneSymbol :" +geneSymbol);
								}
							}
							Collection<String> genBank_AccIDS = (Collection<String>)reporterResultset.getAssiciatedGenBankAccessionNos();
							if(genBank_AccIDS != null){
								for(String genBank_AccID: genBank_AccIDS){
									System.out.println("\tAssocitaed GenBankAccessionNo :" +genBank_AccID);
								}
							}
							Collection<String> locusLinkIDs = (Collection<String>)reporterResultset.getAssiciatedLocusLinkIDs();
							if(locusLinkIDs != null){
								for(String locusLinkID: locusLinkIDs){
									System.out.println("\tAssocitaed LocusLinkID :" +locusLinkID);
								}
							}
							Collection<String> goIds = (Collection<String>)reporterResultset.getAssociatedGOIds();
							if(goIds != null){
								for(String goId: goIds){
									System.out.println("\tAssocitaed GO Id :" +goId);
								}
							}
							Collection<String> pathways = (Collection<String>)reporterResultset.getAssociatedPathways();
							if(pathways != null){
								for(String pathway: pathways){
									System.out.println("\tAssocitaed Pathway :" +pathway);
								}
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(finding instanceof ClassComparisonFinding){
			ClassComparisonFinding ccFinding = (ClassComparisonFinding)finding;
			List<ClassComparisonResultEntry> classComparisonResultEntrys = ccFinding.getResultEntries();
			List<String> reporterIds = new ArrayList<String>();
			for (ClassComparisonResultEntry classComparisonResultEntry: classComparisonResultEntrys){
				if(classComparisonResultEntry.getReporterId() != null){
					reporterIds.add(classComparisonResultEntry.getReporterId());
				}
			}	
				try {
					Map<String,ReporterResultset> reporterResultsetMap = GeneExprAnnotationService.getAnnotationsMapForReporters(reporterIds);

					if(reporterResultsetMap != null  && reporterIds != null){
						int count = 0;
						for(String reporterId:reporterIds){
							ReporterResultset reporterResultset = reporterResultsetMap.get(reporterId);
							System.out.println(++count+" ReporterID :" +reporterResultset.getReporter().getValue().toString());
							Collection<String> geneSymbols = (Collection<String>)reporterResultset.getAssiciatedGeneSymbols();
							if(geneSymbols != null){
								for(String geneSymbol: geneSymbols){
									System.out.println("\tAssocitaed GeneSymbol :" +geneSymbol);
								}
							}
							Collection<String> genBank_AccIDS = (Collection<String>)reporterResultset.getAssiciatedGenBankAccessionNos();
							if(genBank_AccIDS != null){
								for(String genBank_AccID: genBank_AccIDS){
									System.out.println("\tAssocitaed GenBankAccessionNo :" +genBank_AccID);
								}
							}
							Collection<String> locusLinkIDs = (Collection<String>)reporterResultset.getAssiciatedLocusLinkIDs();
							if(locusLinkIDs != null){
								for(String locusLinkID: locusLinkIDs){
									System.out.println("\tAssocitaed LocusLinkID :" +locusLinkID);
								}
							}
							Collection<String> goIds = (Collection<String>)reporterResultset.getAssociatedGOIds();
							if(goIds != null){
								for(String goId: goIds){
									System.out.println("\tAssocitaed GO Id :" +goId);
								}
							}
							Collection<String> pathways = (Collection<String>)reporterResultset.getAssociatedPathways();
							if(pathways != null){
								for(String pathway: pathways){
									System.out.println("\tAssocitaed Pathway :" +pathway);
								}
							}
						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

		}
	}
}
