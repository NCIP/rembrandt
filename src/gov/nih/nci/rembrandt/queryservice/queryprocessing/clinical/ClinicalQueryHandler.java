package gov.nih.nci.rembrandt.queryservice.queryprocessing.clinical;

import gov.nih.nci.caintegrator.dto.critieria.AgeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GenderCriteria;
import gov.nih.nci.caintegrator.dto.critieria.KarnofskyClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.LanskyClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.MRIClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.NeuroExamClinicalEvalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SurvivalCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RaceCriteria;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.RaceDE;
import gov.nih.nci.rembrandt.dbbean.NeuroEvaluation;
import gov.nih.nci.rembrandt.dbbean.PatientData;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.CommonFactHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * @author BhattarR
 */
public class ClinicalQueryHandler extends QueryHandler {
	private static Logger logger = Logger.getLogger(ClinicalQueryHandler.class);
	private Collection patientDIDs = new ArrayList();
	private ResultSet[] clinicalEvalDataResult = null;	

    /**
	 * @return Returns the clinicalEvalDataResult.
	 */
	public ResultSet[] getClinicalEvalDataResult() {
		return clinicalEvalDataResult;
	}

	/**
	 * @param clinicalEvalDataResult The clinicalEvalDataResult to set.
	 */
	public void setClinicalEvalDataResult(ResultSet[] clinicalEvalDataResult) {
		this.clinicalEvalDataResult = clinicalEvalDataResult;
	}

	public ResultSet[] handle(Query query) throws Exception {
        ClinicalDataQuery clinicalQuery = (ClinicalDataQuery) query;
        Criteria allCriteria = new Criteria();

        buildSurvivalRangeCrit(clinicalQuery, allCriteria);
        buildAgeRangeCrit(clinicalQuery, allCriteria);
        buildGenderCrit(clinicalQuery, allCriteria);
        buildRaceCrit(clinicalQuery, allCriteria);     
       

        PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();     
        
        Class targetFactClass = PatientData.class;
        CommonFactHandler.addDiseaseCriteria(clinicalQuery, targetFactClass, _BROKER, allCriteria);
        CommonFactHandler.addSampleIDCriteria(clinicalQuery, targetFactClass, allCriteria);
        CommonFactHandler.addAccessCriteria(clinicalQuery, targetFactClass, allCriteria);
        
        ReportQueryByCriteria clinicalEvalQuery = getClinicalEvalSubQuery(clinicalQuery,_BROKER,NeuroEvaluation.class,NeuroEvaluation.PATIENT_DID);
        _BROKER.close();
        
        if(clinicalEvalQuery != null) {
           allCriteria.addIn(PatientData.PATIENT_DID, clinicalEvalQuery);        
        }
        
        PatientData[] results = executeQuery(allCriteria);
        
        if(clinicalEvalQuery != null || results.length >=1) {
            clinicalEvalDataResult = populateClinicalEval(clinicalQuery,patientDIDs); 
            results = addClinicalEvalToPatientData(results,clinicalEvalDataResult);
        }
        
        
        return results;
    }

	
	private PatientData[] addClinicalEvalToPatientData(PatientData[] patientDataResults, ResultSet[] clinicalEvalDataResult) {
		
		if(patientDataResults instanceof PatientData[]) {
			
			
			for(int i=0; i< patientDataResults.length;i++) {
				PatientData ptData = (PatientData)patientDataResults[i];
				Long patientDid = ptData.getPatientDid();
				StringBuffer timePoints = new StringBuffer();
				StringBuffer followUpdates = new StringBuffer();
				StringBuffer followupMonths = new StringBuffer();							
				StringBuffer neuroEvaluationDates = new StringBuffer();
				StringBuffer karnofskyScores = new StringBuffer();	
				StringBuffer lanskyScores = new StringBuffer();
				StringBuffer neuroExams = new StringBuffer();
				StringBuffer mriCtScores = new StringBuffer();
				StringBuffer steroidDoseStatuses = new StringBuffer();
				StringBuffer antiConvulsantStatuses = new StringBuffer();
				for (int j=0; j<clinicalEvalDataResult.length;j++) {
					NeuroEvaluation clinicalEvalData = (NeuroEvaluation)clinicalEvalDataResult[j];		
					
					Long ptDid = clinicalEvalData.getPatientDid();
					if(patientDid.toString().equals(ptDid.toString())) {
						//ptData.setTimePoint(clinicalEvalData.getTimePoint());
						//ptData.setFollowupDate(clinicalEvalData.getFollowupDate());
						//ptData.setFollowupMonth(clinicalEvalData.getFollowupMonth());
						//ptData.setNeuroEvaluationDate(clinicalEvalData.getNeuroEvaluationDate());
						//ptData.setKarnofskyScore(clinicalEvalData.getKarnofskyScore());
						//ptData.setLanskyScore(clinicalEvalData.getLanskyScore());
						//ptData.setNeuroExam(clinicalEvalData.getNeuroExam());
						//ptData.setMriCtScore(clinicalEvalData.getMriCtScore());
						//ptData.setSteroidDoseStatus(clinicalEvalData.getSteroidDoseStatus());
						//ptData.setAntiConvulsantStatus(clinicalEvalData.getAntiConvulsantStatus());	
						if(clinicalEvalData.getTimePoint()!= null) {
						   timePoints.append(clinicalEvalData.getTimePoint());
						   timePoints.append(", ");
						}
						if(clinicalEvalData.getFollowupDate() != null) {
						   followUpdates.append(clinicalEvalData.getFollowupDate());	
						   followUpdates.append(", ");
						}
						
						if(clinicalEvalData.getFollowupMonth() != null) {
						   followupMonths.append(clinicalEvalData.getFollowupMonth());
						   followupMonths.append(", ");
						}
						
						if(clinicalEvalData.getNeuroEvaluationDate() != null) {
						    neuroEvaluationDates.append(clinicalEvalData.getNeuroEvaluationDate());
						    neuroEvaluationDates.append(", ");
						}
						
						if(clinicalEvalData.getKarnofskyScore() != null) {
						    karnofskyScores.append(clinicalEvalData.getKarnofskyScore());
						    karnofskyScores.append(", ");
						}
						
						if(clinicalEvalData.getLanskyScore() != null) {
						   lanskyScores.append(clinicalEvalData.getLanskyScore());
						   lanskyScores.append(", ");
						}
						
						if(clinicalEvalData.getNeuroExam() != null) {
						    neuroExams.append(clinicalEvalData.getNeuroExam());
						    neuroExams.append(", ");
						}
						
						if(clinicalEvalData.getMriCtScore() != null) {
						   mriCtScores.append(clinicalEvalData.getMriCtScore());
						   mriCtScores.append(", ");
						}
						
						if(clinicalEvalData.getSteroidDoseStatus() != null) {
						   steroidDoseStatuses.append(clinicalEvalData.getSteroidDoseStatus());
						   steroidDoseStatuses.append(", ");
						}
						
						if(clinicalEvalData.getAntiConvulsantStatus() != null) {
						   antiConvulsantStatuses.append(clinicalEvalData.getAntiConvulsantStatus());
						   antiConvulsantStatuses.append(", ");		
						}
						
					}					 
					
				}
				
				if(timePoints.length() >0) {
				   timePoints.deleteCharAt(timePoints.length()-2);
				   ptData.setTimePoints(timePoints.toString());
				}
				
				if(followUpdates.length()>0) {
				   followUpdates.deleteCharAt(followUpdates.length()-2);
				   ptData.setFollowupDates(followUpdates.toString());
				}
				
				if(followupMonths.length()>0) {
				   followupMonths.deleteCharAt(followupMonths.length()-2);
				   ptData.setFollowupMonths(followupMonths.toString());
				}
				
				if(neuroEvaluationDates.length()>0) {				
				   neuroEvaluationDates.deleteCharAt(neuroEvaluationDates.length()-2);
				   ptData.setNeuroEvaluationDates(neuroEvaluationDates.toString());
				}
				
				if(karnofskyScores.length()>0) {	
				   karnofskyScores.deleteCharAt(karnofskyScores.length()-2);
				   ptData.setKarnofskyScores(karnofskyScores.toString());
				}
				
				if(lanskyScores.length()>0) {					
				  lanskyScores.deleteCharAt(lanskyScores.length()-2);
				  ptData.setLanskyScores(lanskyScores.toString());
				}
				
				if(neuroExams.length()>0) {					
				  neuroExams.deleteCharAt(neuroExams.length()-2);
				  ptData.setNeuroExams(neuroExams.toString());
				}
				
				if(mriCtScores.length()>0) {	
				  mriCtScores.deleteCharAt(mriCtScores.length()-2);
				  ptData.setMriCtScores(mriCtScores.toString());				 
				}
				
				if(steroidDoseStatuses.length()>0) {	
				  steroidDoseStatuses.deleteCharAt(steroidDoseStatuses.length()-2);
				  ptData.setSteroidDoseStatuses(steroidDoseStatuses.toString());
				}
				
				if(antiConvulsantStatuses.length()>0) {	
				  antiConvulsantStatuses.deleteCharAt(antiConvulsantStatuses.length()-2);
				  ptData.setAntiConvulsantStatuses(antiConvulsantStatuses.toString());
				}
				
			}
			
			
		 }
		
		return patientDataResults;
	
	}
	
	
	
	private PatientData[] executeQuery(Criteria allCriteria) throws Exception {
        final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        ReportQueryByCriteria sampleQuery = QueryFactory.newReportQuery(PatientData.class, allCriteria, true);
       
        sampleQuery.setAttributes(new String[] {
                       PatientData.BIOSPECIMEN_ID, PatientData.GENDER,
                       PatientData.DISEASE_TYPE, PatientData.AGE_GROUP,
                       PatientData.SAMPLE_ID, PatientData.SURVIVAL_LENGTH_RANGE,
                       PatientData.RACE,PatientData.PATIENT_DID} );        
   

        Iterator patientDataObjects =  pb.getReportQueryIteratorByQuery(sampleQuery);
        
        ArrayList results = new ArrayList();
        populateResults(patientDataObjects, results);
        
      
        PatientData[] finalResult = new PatientData[results.size()];
        for (int i = 0; i < results.size(); i++) {
            PatientData patientData = (PatientData) results.get(i);
            finalResult[i]  = patientData ;
        }
        pb.close();
        return finalResult;
    }

	  private NeuroEvaluation[] populateClinicalEval(ClinicalDataQuery clinicalQuery, Collection patientDIDs)throws Exception {
    	 
		  if(patientDIDs.size()>=1) {
		  Criteria  clinicalEvalCrit = buildClinicalEvalCriteria(clinicalQuery);   
    	  if(clinicalEvalCrit== null) {
    		  clinicalEvalCrit = new Criteria();
    	  }
    	   		  
    		  clinicalEvalCrit.addIn(NeuroEvaluation.PATIENT_DID, patientDIDs);
    		  final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
    	      ReportQueryByCriteria clinicalEvalQuery = QueryFactory.newReportQuery(NeuroEvaluation.class, clinicalEvalCrit, false);
    	       
    	      clinicalEvalQuery.setAttributes(new String[] {
    	    		  NeuroEvaluation.TIME_POINT, NeuroEvaluation.FOLLOWUP_DATE,
    	    		  NeuroEvaluation.FOLLOWUP_MONTH, NeuroEvaluation.NEURO_EVALUATION_DATE,
    	    		  NeuroEvaluation.KARNOFSKY_SCORE, NeuroEvaluation.LANSKY_SCORE,
    	    		  NeuroEvaluation.NEURO_EXAM,NeuroEvaluation.MRI_CT_SCORE,
    	    		  NeuroEvaluation.STEROID_DOSE_STATUS,NeuroEvaluation.ANTI_CONVULSANT_STATUS,
    	    		  NeuroEvaluation.PATIENT_DID} ); 
    	      
    	   
    	        Iterator clinicalEvalDataObjects =  pb.getReportQueryIteratorByQuery(clinicalEvalQuery);
    	        
    	        ArrayList results = new ArrayList();
    	        populateClinicalEvalResults(clinicalEvalDataObjects, results);
    	        
    	      
    	       NeuroEvaluation[] finalResult = new NeuroEvaluation[results.size()];
    	        for (int i = 0; i < results.size(); i++) {
    	        	NeuroEvaluation clinicalEvalData = (NeuroEvaluation) results.get(i);
    	            finalResult[i]  = clinicalEvalData ;
    	        }
    	        pb.close();
    	        
    	       return finalResult;  
              }
             else {
               return null;
             }
  
     }
    	

    private void populateResults(Iterator patientDataObjects, ArrayList results) {
        while(patientDataObjects.hasNext()) {
            Object[] objs = (Object[]) patientDataObjects.next();
            Long bspID = new Long(((BigDecimal)objs[0]).longValue());
            String gender = (String)objs[1];
            String diseaseType = (String)objs[2];
            String ageGroup = (String)objs[3];
            String sampleID = (String)objs[4];
            String survLenRange = (String)objs[5];
            String race = (String)objs[6];
            Long ptDID = new Long(((BigDecimal)objs[7]).longValue());
            PatientData p = new PatientData();
            p.setBiospecimenId(bspID);
            p.setGender(gender);           
            p.setDiseaseType(diseaseType);
            p.setAgeGroup(ageGroup);
            p.setSampleId(sampleID);
            p.setSurvivalLengthRange(survLenRange);  
			p.setRace(race);   
			p.setPatientDid(ptDID);
			
			patientDIDs.add(ptDID);

            results.add(p );
        }
    }

    private void populateClinicalEvalResults(Iterator clinicalEvalDataObjects, ArrayList results) {
		  while(clinicalEvalDataObjects.hasNext()) {
	            Object[] objs = (Object[]) clinicalEvalDataObjects.next(); 
	            String timePoint = null;
	            Date followupDate = null;
	            Long followupMonth = null;
	            Date neuroEvaluationDate = null;
	            Long karnofskyScore = null;
	            Long lanskyScore = null;
	            Long neuroExam = null;
	            Long mriCtScore = null;
	            String steroidDoseStatus = null;
	            String antiConvulsantStatus = null;
	            Long patientDid = null;
	            
	            NeuroEvaluation clinicalEval = new NeuroEvaluation();
	            
	            if(objs[0] != null) {
	               timePoint = (String)objs[0];
	               clinicalEval.setTimePoint(timePoint);
	            }	           
	            if(objs[1]!=null){
	                followupDate = new java.sql.Date(((java.util.Date)objs[1]).getTime());	
	                clinicalEval.setFollowupDate(followupDate);   
	            }
	            	           
	            if(objs[2] != null) {
	            	followupMonth = new Long(((BigDecimal)objs[2]).longValue());
	            	 clinicalEval.setFollowupMonth(followupMonth);
	            }
	            	           
	            if(objs[3] != null) {
	               neuroEvaluationDate =  new java.sql.Date(((java.util.Date)objs[3]).getTime());
	               clinicalEval.setNeuroEvaluationDate(neuroEvaluationDate);
	            }
	           	            
	            if(objs[4] != null) {
	                karnofskyScore = new Long(((BigDecimal)objs[4]).longValue());
	                clinicalEval.setKarnofskyScore(karnofskyScore);
	             }
	             
	            if(objs[5] != null) {
	            	 lanskyScore = new Long(((BigDecimal)objs[5]).longValue());
	            	 clinicalEval.setLanskyScore(lanskyScore);  
	             }
	           
	            if(objs[6] != null) {
	               neuroExam = new Long(((BigDecimal)objs[6]).longValue());
	               clinicalEval.setNeuroExam(neuroExam);    
	              }
	            if(objs[7] != null) {
	                mriCtScore = new Long(((BigDecimal)objs[7]).longValue());
	                clinicalEval.setMriCtScore(mriCtScore);  
	            }
	            
	            if(objs[8] != null) {
	                steroidDoseStatus = (String)objs[8];
	                clinicalEval.setSteroidDoseStatus(steroidDoseStatus);  
	            }
	            
	            if(objs[9] != null) {
	               antiConvulsantStatus = (String)objs[9];
	               clinicalEval.setAntiConvulsantStatus(antiConvulsantStatus); 
	            }     
	          
	            if(objs[10] != null) {
	            	
	               patientDid =  new Long(((BigDecimal)objs[10]).longValue());
		           clinicalEval.setPatientDid(patientDid);
	            }
	            
	            results.add(clinicalEval);
	        }
		  
	  }
    private void buildSurvivalRangeCrit(ClinicalDataQuery cghQuery, Criteria survivalCrit) {
        SurvivalCriteria crit = cghQuery.getSurvivalCriteria();
        if (crit != null) {
            long lowerLmtInMons = crit.getLowerSurvivalRange().getValueObject().longValue();
            long lowerLmtInDays  = lowerLmtInMons * 30;
            long upperLmtInMons = crit.getUpperSurvivalRange().getValueObject().longValue();
            long upperLmtInDays = upperLmtInMons * 30;
            survivalCrit.addBetween(PatientData.SURVIVAL_LENGTH, new Long(lowerLmtInDays), new Long(upperLmtInDays));
        }
    }
    private void buildAgeRangeCrit(ClinicalDataQuery cghQuery, Criteria ageCrit ) {
        AgeCriteria crit = cghQuery.getAgeCriteria();
        if (crit != null) {
            long lowerLmtInYrs= crit.getLowerAgeLimit().getValueObject().longValue();
            long upperLmtInYrs  = crit.getUpperAgeLimit().getValueObject().longValue();
            ageCrit.addBetween(PatientData.AGE, new Long(lowerLmtInYrs), new Long(upperLmtInYrs));
        }
    }
    private void buildGenderCrit(ClinicalDataQuery cghQuery, Criteria genderCrit) {
        GenderCriteria crit = cghQuery.getGenderCriteria();
        if (crit != null) {
            genderCrit.addEqualTo(PatientData.GENDER, crit.getGenderDE().getValueObject());
        }
    }
    
    private void buildPTDIDs(ClinicalDataQuery cghQuery, Criteria genderCrit) {
         GenderCriteria crit = cghQuery.getGenderCriteria();
        if (crit != null) {
            genderCrit.addEqualTo(PatientData.GENDER, crit.getGenderDE().getValueObject());
        }
    }
    private void buildRaceCrit(ClinicalDataQuery clinicalQuery, Criteria raceCrit){
    	  RaceCriteria crit = clinicalQuery.getRaceCriteria();   
    	  
          if (crit != null) {
        	  ArrayList raceTypes = new ArrayList();
        	  for (Iterator iterator = crit.getRaces().iterator(); iterator.hasNext();) {
        		  raceTypes.add(((RaceDE) iterator.next()).getValueObject());
        	  }
        	 raceCrit.addIn(PatientData.RACE, raceTypes);
          }          
         
       }
    
    
    private Criteria buildKarnofskyClinicalEvalCrit(ClinicalDataQuery clinicalQuery) {    	 

    	KarnofskyClinicalEvalCriteria crit = clinicalQuery.getKarnofskyCriteria();    	 
        Criteria c = new Criteria();    	 
    	 if (crit != null) {
  	  	    c.addEqualTo(NeuroEvaluation.KARNOFSKY_SCORE, Integer.parseInt(crit.getKarnofskyClinicalEvalDE().getValueObject()));               	 
    	    return c;   
    	   }
    	 else {
    		 return null;
    	  }    	   
        }
    
    private Criteria  buildLanskyClinicalEvalCrit(ClinicalDataQuery clinicalQuery) {    	 

    	LanskyClinicalEvalCriteria crit = clinicalQuery.getLanskyCriteria();
    	Criteria c = new Criteria();     	 
   	    if (crit != null) {
   		   c.addEqualTo(NeuroEvaluation.LANSKY_SCORE, Integer.parseInt(crit.getLanskyClinicalEvalDE().getValueObject()));             	
   	       return c;
   	      }
   	    else {
   	    	return null;
   	     }
       }
    
    private Criteria  buildMRIClinicalEvalCrit(ClinicalDataQuery clinicalQuery) {    	 

    	MRIClinicalEvalCriteria crit = clinicalQuery.getMriCriteria();
    	Criteria c = new Criteria();    
   	 
   	   if (crit != null) {
   		 c.addEqualTo(NeuroEvaluation.MRI_CT_SCORE, Integer.parseInt(crit.getMRIClinicalEvalDE().getValueObject()));            	
   	     return c;
   	      }
   	   
   	   else {
   		    return null;
   	       }   	   
       }
    
    private Criteria  buildNeuroExamClinicalEvalCrit(ClinicalDataQuery clinicalQuery) {    	 

    	NeuroExamClinicalEvalCriteria crit = clinicalQuery.getNeuroExamCriteria();   
    	Criteria c = new Criteria();    
   	    if (crit != null) {
   		  c.addEqualTo(NeuroEvaluation.NEURO_EXAM, Integer.parseInt(crit.getNeuroExamClinicalEvalDE().getValueObject()));
   		 return c;
         }    	
   	    else {
   	    	return null;
   	     }
   	    
       }    
   
    
    private ReportQueryByCriteria getClinicalEvalSubQuery(ClinicalDataQuery clinicalQuery,PersistenceBroker _BROKER,Class subQueryClass, String fieldToSelect) throws Exception {
    	  Criteria clinalEvalCrit = buildClinicalEvalCriteria(clinicalQuery);
    	  if(clinalEvalCrit != null) {
    		  String ptIDCol = getColumnNameForBean(_BROKER, subQueryClass.getName(), fieldToSelect);
    		  org.apache.ojb.broker.query.ReportQueryByCriteria clinicalEvalQuery =
                  QueryFactory.newReportQuery(subQueryClass, new String[] {ptIDCol}, clinalEvalCrit , true);       
              return clinicalEvalQuery;  
    		  
    	    }
    	  else {
    		  return null;    
    		  
    	      }    	  
    
    	}
    
     private Criteria buildClinicalEvalCriteria(ClinicalDataQuery clinicalQuery){
    	 
    	 Criteria karnofskyCrit = buildKarnofskyClinicalEvalCrit(clinicalQuery);
         Criteria lanskyCrit  = buildLanskyClinicalEvalCrit(clinicalQuery);
         Criteria neuroExamCrit  = buildNeuroExamClinicalEvalCrit(clinicalQuery);
         Criteria mriCrit  = buildMRIClinicalEvalCrit(clinicalQuery);
         
         if(karnofskyCrit ==null && lanskyCrit == null && neuroExamCrit==null && mriCrit==null) {
              return null;
          }
         else {      	
         
	         Criteria clinalEvalCrit = new Criteria();
	         
	         if(karnofskyCrit != null) {
	            clinalEvalCrit.addOrCriteria(karnofskyCrit);
	         }
	         
	         if(lanskyCrit != null) {
	            clinalEvalCrit.addOrCriteria(lanskyCrit);
	         }
	         
	         if(neuroExamCrit != null) {
	            clinalEvalCrit.addOrCriteria(neuroExamCrit);
	         }
	         
	         if(mriCrit != null) {
	            clinalEvalCrit.addOrCriteria(mriCrit);
	         }
	         return clinalEvalCrit;
	       }
  
     }
     
       	  
        }
    
    
    
  