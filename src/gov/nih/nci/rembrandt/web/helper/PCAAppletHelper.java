package gov.nih.nci.rembrandt.web.helper;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import gov.nih.nci.caintegrator.analysis.messaging.PCAresultEntry;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.dto.de.GenderDE;
import gov.nih.nci.caintegrator.enumeration.ClinicalFactorType;
import gov.nih.nci.caintegrator.enumeration.DiseaseType;
import gov.nih.nci.caintegrator.enumeration.GenderType;
import gov.nih.nci.caintegrator.service.findings.PrincipalComponentAnalysisFinding;
import gov.nih.nci.caintegrator.ui.graphing.data.principalComponentAnalysis.PrincipalComponentAnalysisDataPoint;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

public class PCAAppletHelper {

	private static BusinessTierCache businessTierCache = ApplicationFactory.getBusinessTierCache();

	public static String generateParams(String sessionId, String taskId){
		String htm = "";
		DecimalFormat nf = new DecimalFormat("0.0000");
		
		try {
            //retrieve the Finding from cache and build the list of PCAData points
            PrincipalComponentAnalysisFinding principalComponentAnalysisFinding = (PrincipalComponentAnalysisFinding)businessTierCache.getSessionFinding(sessionId, taskId);
            
            ArrayList<PrincipalComponentAnalysisDataPoint> pcaData = new ArrayList();
            
            Collection<ClinicalFactorType> clinicalFactors = new ArrayList<ClinicalFactorType>();
            List<String> sampleIds = new ArrayList();
            Map<String,PCAresultEntry> pcaResultMap = new HashMap<String, PCAresultEntry>();
            
            List<PCAresultEntry> pcaResults = principalComponentAnalysisFinding.getResultEntries();
            for (PCAresultEntry pcaEntry : pcaResults) {
            	sampleIds.add(pcaEntry.getSampleId());
                pcaResultMap.put(pcaEntry.getSampleId(), pcaEntry);
            }
            
            Collection<SampleResultset> validatedSampleResultset = ClinicalDataValidator.getValidatedSampleResultsetsFromSampleIDs(sampleIds, clinicalFactors);
            
            if (validatedSampleResultset!=null){
                String id;                
                PCAresultEntry entry;
                
                for (SampleResultset rs:validatedSampleResultset){
                    id = rs.getBiospecimen().getSpecimenName();
                    entry  = pcaResultMap.get(id); 
                    PrincipalComponentAnalysisDataPoint pcaPoint = new PrincipalComponentAnalysisDataPoint(id,entry.getPc1(),entry.getPc2(),entry.getPc3());
                    String diseaseName = rs.getDisease().getValueObject();
                    if(diseaseName!=null){
                        pcaPoint.setDiseaseName(diseaseName);
                    }
                    else{
                    	pcaPoint.setDiseaseName(DiseaseType.NON_TUMOR.name());
                    }
                    GenderDE genderDE = rs.getGenderCode();
                    if(genderDE != null){
                    	String gt =genderDE.getValueObject();
                    	if(gt!=null){
	                        GenderType genderType = GenderType.valueOf(gt);
	                        if(genderType!=null){
	                            pcaPoint.setGender(genderType);
	                        }
                    	}
                    }
                    Long survivalLength = rs.getSurvivalLength();
                        if(survivalLength !=null){
                        	//survival length is stored in days in the DB so divide by 30 to get the 
                        	//approx survival in months
                            double survivalInMonths = survivalLength.doubleValue()/30.0;
                            pcaPoint.setSurvivalInMonths(survivalInMonths);
                        }
                    pcaData.add(pcaPoint);
                }
            }
            
            //make a hashmap
            // [key=group] hold the array of double[][]s
            HashMap<String, ArrayList> hm = new HashMap();
            
            //now we should have a collection of PCADataPts
            double[][] pts = new double[pcaData.size()][3];
            for(int i=0; i<pcaData.size();i++)	{
            	//just create a large 1 set for now
            	//are we breaking groups by gender or disease?
            	PrincipalComponentAnalysisDataPoint pd = pcaData.get(i);
            	
        		pts[i][0] = pd.getPc1value();
        		pts[i][1] = pd.getPc2value();
        		pts[i][2] = pd.getPc3value();
        		ArrayList<double[]> al;
        		
        		try	{
	        		if(hm.containsKey(pd.getDiseaseName())){
	            		//already has it, so add this one
	            		al = (ArrayList) hm.get(pd.getDiseaseName());
	            	}
	            	else	{
	            		al = new ArrayList();
	            		hm.put(pd.getDiseaseName(), new ArrayList());
	            	}
	            	if(!al.contains(pts[i]))	{
	            		al.add(pts[i]);
	            	}
	            	hm.put(pd.getDiseaseName(), al);
        		}
        		catch(Exception e){
        			System.out.print(e.toString());
        		}
            }
            int r = hm.size();
            if(r==1){}
            //hm should now contain a hashmap of all the disease groups
            
            //generate the param tags
            htm += "<param name=\"key\" value=\""+taskId+"\" >\n";
            htm += "<param name=\"totalPts\" value=\""+pts.length+"\" >\n";
            htm += "<param name=\"totalGps\" value=\""+hm.size()+"\" >\n";
            int ii = 0;
            for(Object k : hm.keySet())	{
            	String key = k.toString();
            	//for each group
            	
            	Color diseaseColor = Color.GRAY;
            	if(DiseaseType.valueOf(key)!=null)	{
            		DiseaseType disease = DiseaseType.valueOf(key);
            		diseaseColor = disease.getColor();
            	}
      		  
            	ArrayList<double[]> al = hm.get(key);
            	htm+= "<param name=\"groupLabel_"+ii+"\" value=\""+key+"\" >\n";
            	htm+= "<param name=\"groupCount_"+ii+"\" value=\""+al.size()+"\" >\n";
            	htm+= "<param name=\"groupColor_"+ii+"\" value=\""+diseaseColor.getRGB()+"\" >\n";
            	int jj=0;
            	for(double[] d : al){
            		String comm = nf.format(d[0]) + "," +
                	nf.format(d[1]) + "," +
                	nf.format(d[2]);
            		String h = "<param name=\"pt_"+ii+"_"+jj+"\" value=\""+ comm +"\">\n";
            		htm+=h;
            		jj++;
            	}
            	ii++;
            }
            /*
             //for bulk rendering
            for(int i=0; i<pts.length; i++)	{
            	String comm = String.valueOf(pts[i][0]) + "," +
            	String.valueOf(pts[i][1]) + "," +
            	String.valueOf(pts[i][2]);
            	
            	String h = "<param name=\"pt_"+i+"\" value=\""+ comm +"\">\n";
            	//htm += h;
            }
            */
            
		}//try
		catch(Exception e){
			
		}
		
		return htm;
	}
}
