package gov.nih.nci.rembrandt.service.findings.strategies;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonRequest;
import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;
import gov.nih.nci.caintegrator.analysis.messaging.SampleGroup;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.dto.view.ClinicalSampleView;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.enumeration.StatisticalSignificanceType;
import gov.nih.nci.caintegrator.exceptions.FindingsAnalysisException;
import gov.nih.nci.caintegrator.exceptions.FindingsQueryException;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy;
import gov.nih.nci.rembrandt.analysis.server.AnalysisServerClientManager;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

/**
 * @author sahnih
 *
 */
public class ClassComparisonFindingStrategy implements FindingStrategy {
	private static Logger logger = Logger.getLogger(ClassComparisonFindingStrategy.class);	
	@SuppressWarnings("unused")
	private ClassComparisonQueryDTO myQueryDTO;
	@SuppressWarnings("unused")
	private Collection<ClinicalDataQuery> clinicalQueries;
	@SuppressWarnings({"unchecked"})
	private Collection<SampleGroup> sampleGroups = testmethod();//new ArrayList();
	private String sessionId = null;
	private String taskId = null;
	private ClassComparisonRequest classComparisonRequest = null;
	private ClassComparisonResult classComparisonResult = null;
	private ClassComparisonFinding classComparisonFinding;
	private AnalysisServerClientManager analysisServerClientManager;
	private BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();
	
	public ClassComparisonFindingStrategy(String sessionId, String taskId, ClassComparisonQueryDTO queryDTO) throws ValidationException {
		//Check if the passed query is valid
		if(validate(queryDTO)){
			myQueryDTO = queryDTO;
			this.sessionId = sessionId;
			this.taskId = taskId;
			classComparisonRequest = new ClassComparisonRequest(sessionId,taskId);
            try {
                analysisServerClientManager = AnalysisServerClientManager.getInstance();
            } catch (NamingException e) {               
                logger.error(new IllegalStateException("Error getting an instance of  AnalysisServerClientManager" ));
                logger.error(e.getMessage());
                logger.error(e);
            } catch (JMSException e) {                
                logger.error(new IllegalStateException("Error getting an instance of  AnalysisServerClientManager" ));
                logger.error(e.getMessage());
                logger.error(e);
            }
		}
		/*
		 * Set the Finding into the cache! YOU HAVE TO DO THIS!
		 */
		
		FindingStatus currentStatus = FindingStatus.Running;
		classComparisonFinding = new ClassComparisonFinding(sessionId, taskId, currentStatus, null);
		cacheManager.addToSessionCache(sessionId, taskId, classComparisonFinding);
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#createQuery()
	 * This method validates that 2 groups were passed for TTest and Wincoin Test
	 */
	public boolean createQuery() throws FindingsQueryException {
		//because each layer is valid I am assured I will be getting a fulling populated query object
		StatisticalMethodType statisticType = myQueryDTO.getStatisticTypeDE().getValueObject();

		if(myQueryDTO.getComparisonGroups() != null ){
			switch (statisticType){
			case TTest:
			case Wilcoxin:
				if( myQueryDTO.getComparisonGroups().size() != 2){
					throw new FindingsQueryException("Incorrect Number of queries passed for the TTest and  Wilcoxin stat type");
				}
				break;
			default:
				throw new FindingsQueryException("No StatisticalMethodType selected");
			}
			/**
			 * We have to convert from ClinicalQueryDTO to ClinicalDataQuery (a rembrandt class)
			 * because at the time of this writing the DTO was only a marker interface.
			 * The ClinicalDataQuery was an exsisting Query that we could not change 
			 * in the interest of time.
			 */
			Collection<ClinicalQueryDTO> clinicalQueryDTOs = myQueryDTO.getComparisonGroups();
			clinicalQueries = new ArrayList<ClinicalDataQuery>();
			for(ClinicalQueryDTO clinicalQueryDTO: clinicalQueryDTOs) {
				clinicalQueries.add((ClinicalDataQuery)clinicalQueryDTO);
			}
			
			return true;	
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#executeQuery()
	 * this methods queries the database to get back sample Ids for the groups
	 */
	public boolean executeQuery() throws FindingsQueryException {
		if(clinicalQueries != null){
		CompoundQuery compoundQuery;
			    for (ClinicalDataQuery clinicalDataQuery: clinicalQueries){
			    Resultant resultant;
				try {
					compoundQuery = new CompoundQuery(clinicalDataQuery);
					compoundQuery.setAssociatedView(ViewFactory
		                .newView(ViewType.CLINICAL_VIEW));
					resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
		  		}
		  		catch (Throwable t)	{
		  			logger.error("Error Executing the query/n"+ t.getMessage());
		  			throw new FindingsQueryException("Error executing clinical query/n"+t.getMessage());
		  		}

				if(resultant != null) {      
			 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
			 		Viewable view = resultant.getAssociatedView();
			 		if(resultsContainer != null)	{
			 			if(view instanceof ClinicalSampleView){
			 				try {
								Collection<String> sampleIDs = StrategyHelper.extractSampleIds(resultsContainer);
								if(sampleIDs != null){
									SampleGroup group = new SampleGroup(clinicalDataQuery.getQueryName(),sampleIDs.size());
									group.addAll(sampleIDs);
									sampleGroups.add(group);
								}
							} catch (OperationNotSupportedException e) {
								logger.error(e.getMessage());
					  			throw new FindingsQueryException(e.getMessage());
							}

		 				}	
			 		}
				}
		  }
			    return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.service.findings.strategies.FindingStrategy#analyzeResultSet()
	 */
	public boolean analyzeResultSet() throws FindingsAnalysisException {
		StatisticalMethodType statisticType = myQueryDTO.getStatisticTypeDE().getValueObject();
		classComparisonRequest.setStatisticalMethod(statisticType);
		try{
			switch (statisticType){
			case TTest:
			case Wilcoxin:
				{
					//set MultiGroupComparisonAdjustmentType
					classComparisonRequest.setMultiGroupComparisonAdjustmentType(
							myQueryDTO.getMultiGroupComparisonAdjustmentTypeDE().getValueObject());				
					//set foldchange
				
					ExprFoldChangeDE foldChange = myQueryDTO.getExprFoldChangeDE();
					classComparisonRequest.setFoldChangeThreshold(foldChange.getValueObject());
					//set platform
					//TODO:Covert ArrayPlatform String to Enum -Himanso 10/15/05
					if(myQueryDTO.getArrayPlatformDE().getValueObject().equals(ArrayPlatformType.AFFY_OLIGO_PLATFORM)){
						classComparisonRequest.setArrayPlatform(ArrayPlatformType.AFFY_OLIGO_PLATFORM); //TODO: Needs to change
					}
					// set SampleGroups
                    Object[] obj = sampleGroups.toArray();
					//SampleGroup[] sampleGroupObjects =  (SampleGroup[]) sampleGroups.toArray();				
					if (obj.length == 5) {
						classComparisonRequest.setGroup1((SampleGroup)obj[0]);
						classComparisonRequest.setGroup2((SampleGroup)obj[1]);
					}
					// set PvalueThreshold
					classComparisonRequest.setPvalueThreshold(myQueryDTO.getStatisticalSignificanceDE().getValueObject());
                    analysisServerClientManager.sendRequest(classComparisonRequest);
                    return true;
				}
			}
			
		}catch(Exception e){
			throw new FindingsAnalysisException("Error in setting ClassComparisonRequest object");
		}

		return false;
	}

    private Collection<SampleGroup> testmethod(){ //TODO:DEBUG
    	Collection<SampleGroup> sampleGroups = new ArrayList<SampleGroup>();
    	 SampleGroup gbmGrp = new SampleGroup("GBM");
    	 SampleGroup astroGrp = new SampleGroup("ASTRO");
    	 SampleGroup normalGrp = new SampleGroup("NORMAL");
    	 SampleGroup oligoGrp = new SampleGroup("OLIGO");
    	 SampleGroup mixedGrp = new SampleGroup("MIXED");
    	
    	 String gbmHFids = "HF0024,HF0031,HF0048,HF0050,HF0066,HF0089,HF0138,HF0142,HF0180,HF0184,HF0212,HF0218,HF0244,HF0268,HF0300.3,HF0316,HF0350,HF0408,HF0435,HF0442.5,HF0445,HF0460,HF0505,HF0520,HF0543,HF0583,HF0627,HF0652.4,HF0654,HF0702,HF0790,HF0850,HF0855,HF0894,HF0936,HF0954.2,HF0963,HF0982,HF0986,HF0990,HF0992,HF0996,HF1057,HF1058,HF1077,HF1078,HF1097,HF1122,HF1137,HF1178,HF1186,HF1191,HF1220,HF1242,HF1255,HF1262,HF1280,HF1286,HF1292,HF1318,HF1326,HF1338,HF1356,HF1357,HF1382,HF1397,HF1409,HF1458,HF1475,HF1490,HF1492,HF1494,HF1509,HF1517,HF1534,HF1538,HF1540,HF1585,HF1589,HF1608,HF1618,HF1628,HF1640,HF1667,HF1671,HF1702";
    	 String oligoHFids = "HF0087,HF0251,HF0285,HF0291,HF0327,HF0329,HF0332,HF0434,HF0453,HF0471,HF0488,HF0510,HF0599,HF0615,HF0639,HF0670,HF0726,HF0813,HF0816,HF0822,HF0828,HF0835,HF0897,HF0899,HF0914,HF0920,HF0931,HF0960,HF0962,HF0966,HF0975,HF1136,HF1150,HF1156,HF1167,HF1185,HF1219,HF1227,HF1235,HF1325,HF1334,HF1345,HF1348,HF1380,HF1381,HF1489,HF1493,HF1502,HF1551,HF1606,HF1613,HF1677";
    	 String astroHFids = "HF0017,HF0026,HF0108,HF0152,HF0189,HF0223,HF0450,HF0491,HF0608,HF0757,HF0778,HF0953,HF1000,HF1032,HF1139,HF1232,HF1246,HF1269,HF1295,HF1316,HF1344,HF1366,HF1407,HF1442,HF1469,HF1487,HF1511,HF1568,HF1581,HF1587,HF1708";
    	 String mixedHFids = "HF0022,HF0183,HF0252,HF0305,HF0606,HF0802,HF0844,HF0891,HF1090,HF1297,HF1319,HF1588";
    	 String normalHFids = "HF0088,HF0120,HF0131,HF0137,HF0141,HF0151,HF0163,HF0171,HF0178,HF0201,HF0211,HF0232,HF0295,HF0303,HF0312,HF0377,HF0383,HF0467,HF0512,HF0523,HF0526,HF0533,HF0593,HF0616";
    	 
      initializeTestSampleGroup(gbmGrp, gbmHFids);
      initializeTestSampleGroup(astroGrp, astroHFids);
      initializeTestSampleGroup(oligoGrp, oligoHFids);
      initializeTestSampleGroup(mixedGrp, mixedHFids);
      initializeTestSampleGroup(normalGrp, normalHFids);
      sampleGroups.add(gbmGrp);
      sampleGroups.add(astroGrp);
      sampleGroups.add(oligoGrp);
      sampleGroups.add(mixedGrp);
      sampleGroups.add(normalGrp);
      
      return sampleGroups;
    }
    
    private void initializeTestSampleGroup(SampleGroup grp, String identifierStr) {//TODO:DEBUG
  	  StringTokenizer t = new StringTokenizer(identifierStr, ",");
  	  String id;
  	  while(t.hasMoreTokens()) {
  	    id = t.nextToken().trim();
  	    grp.add(id.trim());
  	  }
  	}


	public Finding getFinding() {
		return classComparisonFinding;
	}



	public boolean validate(QueryDTO queryDTO) throws ValidationException {
		boolean _valid = false;
		if(queryDTO instanceof ClassComparisonQueryDTO){
			_valid = true;
			ClassComparisonQueryDTO classComparisonQueryDTO = (ClassComparisonQueryDTO)queryDTO;
			boolean assertsEnabled = false;
	        assert assertsEnabled = true; // Intentional side effect!!!
			//		 Now assertsEnabled is set to the correct value 
	        String errorMsg = " In ClassComparisonQueryDTO ";
			try {
				//assert(this.institutionNameDE != null);
				assert classComparisonQueryDTO.getArrayPlatformDE() != null:errorMsg+"arrayPlatformDE cannot be Null";
				assert classComparisonQueryDTO.getComparisonGroups() != null:errorMsg+"comparisonGroups cannot be Null";
				assert classComparisonQueryDTO.getExprFoldChangeDE() != null:errorMsg+"exprFoldChangeDE cannot be Null";
				assert classComparisonQueryDTO.getMultiGroupComparisonAdjustmentTypeDE() != null:errorMsg+"multiGroupComparisonAdjustmentTypeDE cannot be Null";
				assert classComparisonQueryDTO.getQueryName() != null:errorMsg+"queryName cannot be Null";
				assert classComparisonQueryDTO.getStatisticalSignificanceDE() != null:errorMsg+"statisticalSignificanceDE cannot be Null";
				assert classComparisonQueryDTO.getStatisticTypeDE() != null:errorMsg+"statisticTypeDE cannot be Null";
					switch (classComparisonQueryDTO.getMultiGroupComparisonAdjustmentTypeDE().getValueObject()){
						case NONE:
							assert(classComparisonQueryDTO.getStatisticalSignificanceDE().getStatisticType() == StatisticalSignificanceType.pValue):
								errorMsg+"When multiGroupComparisonAdjustmentTypeDE is NONE, Statistical Type cannot equal pValue";
							break;
						case FWER:
						case FDR:
							assert(classComparisonQueryDTO.getStatisticalSignificanceDE().getStatisticType() == StatisticalSignificanceType.adjustedpValue):
								errorMsg+"When multiGroupComparisonAdjustmentTypeDE is FWER or FDR, Statistical Type cannot equal adjusted pValue";
							break;
						default:
								throw(new ValidationException("multiGroupComparisonAdjustmentTypeDE is does not match any options"));					
					}
				} catch (AssertionError e) {
					e.printStackTrace();
					throw(new ValidationException(e.getMessage()));
				}
		}		
		return _valid;
	}
}
