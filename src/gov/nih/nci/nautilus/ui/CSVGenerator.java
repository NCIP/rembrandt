package gov.nih.nci.nautilus.ui;

import gov.nih.nci.nautilus.query.CompoundQuery;
import gov.nih.nci.nautilus.query.QueryCollection;
import gov.nih.nci.nautilus.resultset.DimensionalViewContainer;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsetManager;
import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.copynumber.CytobandResultset;
import gov.nih.nci.nautilus.resultset.copynumber.SampleCopyNumberValuesResultset;
import gov.nih.nci.nautilus.resultset.gene.DiseaseGroupResultset;
import gov.nih.nci.nautilus.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;
import gov.nih.nci.nautilus.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.nautilus.resultset.gene.ViewByGroupResultset;
import gov.nih.nci.nautilus.resultset.sample.SampleResultset;
import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;
import gov.nih.nci.nautilus.view.ClinicalSampleView;
import gov.nih.nci.nautilus.view.CopyNumberSampleView;
import gov.nih.nci.nautilus.view.GeneExprDiseaseView;
import gov.nih.nci.nautilus.view.GeneExprSampleView;
import gov.nih.nci.nautilus.view.Viewable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Landyr
 * Date: Nov 3, 2004
 * 
 */
public class CSVGenerator  {
	
//	public static String theColors[] = {"0073E6","FFFF61"};
	
	public static final DecimalFormat resultFormat = new DecimalFormat("0.0000");
				
	public static String displayReport(QueryCollection queryCollection, String[] theColors, boolean csv)	{
		
		StringBuffer html = new StringBuffer();
		StringBuffer errors = new StringBuffer();
		Resultant resultant;
			
		try	{
			
			CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();

			try	{
				resultant = ResultsetManager.executeCompoundQuery(myCompoundQuery);
	  		}
	  		catch (Throwable t)	{
	  			errors.append("Error executing the query.<Br><Br>");
	  			//errors.append(t.getStackTrace().toString());
	  			return errors.toString();
	  		}

			if(resultant != null) {      
		 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
		 		
		 		String theQuery  =  resultant.getAssociatedQuery().toString();

		 		if(resultsContainer != null)	{
		 			
			 		Viewable view = resultant.getAssociatedView();
			 		 
			 		//4 views here, returning the String of HTML for report
			 		// need to add the html buffer here
			 		
		 			if (view instanceof GeneExprSampleView)	{ 
		 				html.append("Gene Expression Fold Change (Tumor/Non-tumor)\n");
		 				html.append(geneExprSampleView(resultsContainer, theColors));
		 				return html.toString();
		 			}
		 			else if (view instanceof CopyNumberSampleView)	{ 
		 				html.append("Copy Number Data\n");
		 				html.append(copyNumberSampleView(resultsContainer, theColors));
		 				return html.toString();
		 			}
		 			else if (view instanceof GeneExprDiseaseView)	{
		 				html.append("Mean Gene Expression Fold Change for Tumor Sub-types\n");
		 				html.append(geneExprDiseaseView(resultsContainer, theColors));
		 				return html.toString();
		 			}
	 				else if(view instanceof ClinicalSampleView){
	 					html.append("Sample Report\n");
	 					html.append(clinicalSampleView(resultsContainer, theColors));
	 					return html.toString();
	 				}	
	 				else	{
						errors.append("Error with report view");
						return errors.toString();
					}
			 	}
			 	else	{
			 		errors.append("No Results Found, Try a Different Query\n");
			 		return errors.toString();
			 	}
			 } //resultant != null
			 else	{
			 	errors.append("Resultant is NULL\n");
			 	return errors.toString();
			 }
		}
		
		catch(Exception e)	{
			errors.append("Error Displaying the Report.\n");
			return errors.toString();
		}
		
	}
	
	
	
	public static String clinicalSampleView(ResultsContainer resultsContainer, String[] theColors)	{
			
			boolean gLinks = false;
			boolean cLinks = false;
			StringBuffer sb = new StringBuffer();
			SampleViewResultsContainer sampleViewContainer = null;
			if(resultsContainer instanceof DimensionalViewContainer){
				
				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
						if(dimensionalViewContainer.getGeneExprSingleViewContainer() != null)	{
							// show the geneExprHyperlinks
							gLinks = true;
						}
						if(dimensionalViewContainer.getCopyNumberSingleViewContainer() != null)	{
							// show the copyNumberHyperlinks
							cLinks = true;
						}
				sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
				
			}else if (resultsContainer instanceof SampleViewResultsContainer){
				
				sampleViewContainer = (SampleViewResultsContainer) resultsContainer;
				
			}
			
			Collection samples = sampleViewContainer.getBioSpecimenResultsets();
			sb.append("SAMPLE,AGE at Dx,GENDER,SURVIVAL,DISEASE");
 		   	if(gLinks)
 		   		sb.append(",GeneExp");
 		   	if(cLinks)
 		   		sb.append(",CopyNumber");
 		   	sb.append("\n");
   			for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
   				SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
	   			sb.append(sampleResultset.getBiospecimen().getValue().toString().substring(2)+ "," +
   					sampleResultset.getAgeGroup().getValue()+ "," +
					sampleResultset.getGenderCode().getValue()+ "," +
					sampleResultset.getSurvivalLengthRange().getValue()+ "," +
					sampleResultset.getDisease().getValue());
	   			if(gLinks)
	   				sb.append(",G");
	   			if(cLinks)
	   				sb.append(",C");
	   			sb.append("\n");
    		}
    		return sb.toString();
	}
	
	
	public static String geneExprDiseaseView(ResultsContainer resultsContainer, String[] theColors)	{
		
		StringBuffer sb = new StringBuffer();
		GeneExprResultsContainer geneExprDiseaseContainer = (GeneExprResultsContainer) resultsContainer;

					int recordCount = 0;
					if(geneExprDiseaseContainer != null)	{
				    	Collection genes = geneExprDiseaseContainer.getGeneResultsets();
				    	Collection labels = geneExprDiseaseContainer.getGroupsLabels();
				    	Collection sampleIds = null;

				        String label = null;

				    	sb.append("Gene,Reporter");
					   

				    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				        	label = (String) labelIterator.next();
				        	sb.append(","+label);
				    	}
			
						sb.append("\n");
						
					
				    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
				    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
				    		String geneSymbol = geneResultset.getGeneSymbol().getValue().toString();
				    		Collection reporters = geneExprDiseaseContainer.getRepoterResultsets(geneSymbol); 

				    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
				    			recordCount += reporters.size();
				    			
				        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
				        		String reporterName = reporterResultset.getReporter().getValue().toString();
				        		Collection groupTypes = geneExprDiseaseContainer.getGroupByResultsets(geneSymbol,reporterName); //reporterResultset.getGroupResultsets();

				        		sb.append(geneSymbol+"," + reporterName);
				        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				    	        	label = (String) labelIterator.next();
				    	        	DiseaseGroupResultset diseaseResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(label);
				    	        	if(diseaseResultset != null){
			                   			Double ratio = (Double)diseaseResultset.getFoldChangeRatioValue().getValue();
			                   			Double pvalue = (Double)diseaseResultset.getRatioPval().getValue();
			                   			sb.append(","+resultFormat.format(ratio)+" ("+resultFormat.format(pvalue)+")");  
			                   			}
			                   		else	{
			                   			sb.append(",-");
			                   		}
				    	    	}
	   	                   		sb.append("\n");
				    		}
				    		// add the line between genes
				    		//sb.append("\n");
						    
				    	}
				}
				else	{
					sb.append("Gene Disease View container is empty");
				}
	
				return sb.toString();
	}


	public static String copyNumberSampleView(ResultsContainer resultsContainer, String[] theColors)	{
		
				StringBuffer sb = new StringBuffer();
				int recordCount = 0;
				
				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
				if(dimensionalViewContainer != null)	{		
					CopyNumberSingleViewResultsContainer copyNumberContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
					
					SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
					if(sampleViewContainer.getBioSpecimenResultsets().size() > 0 && copyNumberContainer.getCytobandResultsets().size() > 0)	{
						Collection samples = sampleViewContainer.getBioSpecimenResultsets();
		
						Collection cytobands = copyNumberContainer.getCytobandResultsets();
				    	Collection labels = copyNumberContainer.getGroupsLabels();
				    	Collection sampleIds = null;
				    	
				    	
				    	StringBuffer header = new StringBuffer();
				    	StringBuffer sampleNames = new StringBuffer();
				        StringBuffer stringBuffer = new StringBuffer();
				        			        

				    	sampleNames.append(" , ");
				    	
				    	header.append("Cytoband,Reporter");

				    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				        	String label = (String) labelIterator.next();
				        	
				        	sampleIds = copyNumberContainer.getBiospecimenLabels(label); 

					           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
					            	sampleNames.append("," + sampleIdIterator.next().toString().substring(2)); 
						        	header.append(","+label); 
					           	}
				    	}
				    	header.append("\n"); 
				    	sampleNames.append("\n");
				    	
						sb.append(header.toString());
						sb.append(sampleNames.toString());
						
				    	for (Iterator cytobandIterator = cytobands.iterator(); cytobandIterator.hasNext();) {
				    		CytobandResultset cytobandResultset = (CytobandResultset)cytobandIterator.next();
				    		String cytoband = cytobandResultset.getCytoband().getValue().toString();
				    		Collection reporters = copyNumberContainer.getRepoterResultsets(cytoband); 
				    		recordCount += reporters.size();
				        	for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
				        		
				        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
				        		String reporterName = reporterResultset.getReporter().getValue().toString();
				        		Collection groupTypes = copyNumberContainer.getGroupByResultsets(cytoband,reporterName); 

				        		sb.append(cytoband+","+reporterName);
				        		for (Iterator groupIterator = groupTypes.iterator(); groupIterator.hasNext();) {
				        			ViewByGroupResultset groupResultset = (ViewByGroupResultset)groupIterator.next();
				        			String label = groupResultset.getType().getValue().toString();
				        			sampleIds = copyNumberContainer.getBiospecimenLabels(label);
				                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				                       		String sampleId = (String) sampleIdIterator.next();
				                       		SampleCopyNumberValuesResultset sampleResultset2 = (SampleCopyNumberValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
				                       		if(sampleResultset2 != null){
				                       			Double ratio = (Double)sampleResultset2.getCopyNumber().getValue();
				                       			sb.append(","+resultFormat.format(ratio));  
				                       			}
				                       		else 
				                       		{
				                       			sb.append(",-");
				                       		}
				                       	}
				         		}
				        		sb.append("\n");
				    		}
				        	//append the extra row here
				        	//sb.append("\n");
				    	}
				}
				
				else	{
					sb.append("Copy Number container is empty");
				}
				
			}
			else	{
				sb.append("Copy Number container is empty");
			}	
				
			return sb.toString();
				
	}


	public static String geneExprSampleView(ResultsContainer resultsContainer, String[] theColors)	{
		
				StringBuffer sb = new StringBuffer();
				int recordCount = 0;
				
			    DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
			    if(dimensionalViewContainer != null)	{
		        	GeneExprSingleViewResultsContainer geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
			    	Collection genes = geneViewContainer.getGeneResultsets();
			    	Collection labels = geneViewContainer.getGroupsLabels();
			    	Collection sampleIds = null;
		
			    	StringBuffer header = new StringBuffer();
			    	
			    	StringBuffer sampleNames = new StringBuffer();
			        StringBuffer stringBuffer = new StringBuffer();
			    	
			    	header.append("Gene,Reporter");
			    	sampleNames.append(" , "); 
				   
			    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        	String label = (String) labelIterator.next();
			        	sampleIds = geneViewContainer.getBiospecimenLabels(label);    	
			        	//header.append(","+label); 

				           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				            	sampleNames.append(","+sampleIdIterator.next().toString().substring(2)); 
				            	header.append(","+label);
				           	}
			           	//header.deleteCharAt(header.lastIndexOf("\t"));
			    	}
			    	sampleNames.append("\n");
			    	header.append("\n"); 
			    	
			    	sb.append(header.toString());
					sb.append(sampleNames.toString());
		
			    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
			    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
			    		Collection reporters = geneResultset.getReporterResultsets();
			    		
			    		recordCount+=reporters.size();
			    		
			    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
			        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
			        		Collection groupTypes = reporterResultset.getGroupByResultsets();
			        		String reporterName = reporterResultset.getReporter().getValue().toString();

			        		sb.append(geneResultset.getGeneSymbol().getValueObject().toString()+","+
			    					reporterName);
			        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        			String label = (String) labelIterator.next();
			        			ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset.getGroupByResultset(label);
			        			
				        			sampleIds = geneViewContainer.getBiospecimenLabels(label);
				        			if(groupResultset != null)
			        				{
				                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				                       		String sampleId = (String) sampleIdIterator.next();
				                       		SampleFoldChangeValuesResultset biospecimenResultset = (SampleFoldChangeValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
				                       		if(biospecimenResultset != null){
				                       			Double ratio = (Double)biospecimenResultset.getFoldChangeRatioValue().getValue();
				                       			if(ratio != null)
					                       			sb.append(","+resultFormat.format(ratio));                                 
					                       		else
					                      			sb.append(",-");
				                       		}
				                       		else 
				                       		{
				                       			sb.append(",-");
				                       		}
				                       	}
			                       }
			                       else	{
			                       for(int s=0;s<sampleIds.size();s++) 
			                        	sb.append(",-");                      
			                       }
			
			         		}
			         		
			        		sb.append("\n");
			    		}
			    		// add the line between genes
			    		// sb.append("\n");
			    	}
				}
				else {
					sb.append("Gene Container is empty<br>");
				}
			    
			    return sb.toString();
	
		
	}

}
