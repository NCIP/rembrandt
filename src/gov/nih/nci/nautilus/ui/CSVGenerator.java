package gov.nih.nci.nautilus.ui;

import gov.nih.nci.nautilus.de.GeneIdentifierDE.GeneSymbol;
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
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Landyr
 * Date: Nov 3, 2004
 * 
 */
public class CSVGenerator  {
	

	public static final DecimalFormat resultFormat = new DecimalFormat("0.0000");
				
	public static String displayReport(QueryCollection queryCollection, boolean csv)	{
		
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
	  			System.out.println("Error Executing the query");
	  			return errors.toString();
	  		}

			if(resultant != null) {      
		 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
		 		
		 		String theQuery  =  resultant.getAssociatedQuery().toString();

		 		if(resultsContainer != null)	{
		 			
			 		Viewable view = resultant.getAssociatedView();
			 		
		 			if (view instanceof GeneExprSampleView)	{ 
		 				html.append("Gene Expression Fold Change (Tumor/Non-tumor)\n");
		 				html.append(geneExprSampleView(resultsContainer));
		 				return html.toString();
		 			}
		 			else if (view instanceof CopyNumberSampleView)	{ 
		 				html.append("Copy Number Data\n");
		 				html.append(copyNumberSampleView(resultsContainer));
		 				return html.toString();
		 			}
		 			else if (view instanceof GeneExprDiseaseView)	{
		 				html.append("Mean Gene Expression Fold Change for Tumor Sub-types\n");
		 				html.append(geneExprDiseaseView(resultsContainer));
		 				return html.toString();
		 			}
	 				else if(view instanceof ClinicalSampleView){
	 					html.append("Sample Report\n");
	 					html.append(clinicalSampleView(resultsContainer));
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
	
	
	
	public static String clinicalSampleView(ResultsContainer resultsContainer)	{
			
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
	
	
	public static String geneExprDiseaseView(ResultsContainer resultsContainer)	{
		
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
				    		//String geneSymbol = geneResultset.getGeneSymbol().getValue().toString();
				    		//Collection reporters = geneExprDiseaseContainer.getRepoterResultsets(geneSymbol); 
				    		Collection reporters = geneResultset.getReporterResultsets();
				    		
				    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
				    			recordCount += reporters.size();
				    			
				        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();

				        		String reporterName = "-";
				        		try	{
				        			reporterName = reporterResultset.getReporter().getValue().toString();
				        		}
				        		catch(Exception e)	{
				        			reporterName = "-";
				        		}
				        		System.out.println("Reporter: "+ reporterName);
				        		
				        		
				        		GeneSymbol gene = geneResultset.getGeneSymbol();
				        		String geneSymbol = "-";
				        		if( gene != null){
				        			try{
				        				geneSymbol = geneResultset.getGeneSymbol().getValueObject().toString();
				        			}
				        			catch(Exception e){
				        				geneSymbol = "-";
				        			}
				        			System.out.println("Gene Symbol: "+ geneSymbol);
				        		}
				        		
				        		//Collection groupTypes = reporterResultset.getGroupByResultsets();
				        		//Collection groupTypes = geneExprDiseaseContainer.getGroupByResultsets(geneSymbol,reporterName); //reporterResultset.getGroupResultsets();

				        		sb.append(geneSymbol+"," + reporterName);
				        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				    	        	label = (String) labelIterator.next();
				    	        	DiseaseGroupResultset diseaseResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(label);
				    	        	if(diseaseResultset != null){
				    	        		try	{
				    	        			Double ratio = (Double)diseaseResultset.getFoldChangeRatioValue().getValue();
				    	        			sb.append(","+resultFormat.format(ratio));
				    	        		}
				    	        		catch(Exception e)	{
				    	        			sb.append(",-");
				    	        		}
				    	        		try	{
				    	        			Double pvalue = (Double)diseaseResultset.getRatioPval().getValue();
				    	        			sb.append(" ("+resultFormat.format(pvalue) + ")");
				    	        		}
				    	        		catch(Exception e){
				    	        			sb.append(" ");
				    	        		}
			                   			//Double ratio = (Double)diseaseResultset.getFoldChangeRatioValue().getValue();
			                   			//Double pvalue = (Double)diseaseResultset.getRatioPval().getValue();
			                   			//sb.append(","+resultFormat.format(ratio)+" ("+resultFormat.format(pvalue)+")");  
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


	public static String copyNumberSampleView(ResultsContainer resultsContainer)	{
		
				StringBuffer sb = new StringBuffer();
		    	StringBuffer header = new StringBuffer();
		    	StringBuffer sampleNames = new StringBuffer();
		        StringBuffer stringBuffer = new StringBuffer();
		        StringBuffer theLabels = new StringBuffer();
		        StringBuffer tempSampleNames = new StringBuffer();
		        
				int recordCount = 0;
				
				CopyNumberSingleViewResultsContainer copyNumberContainer = null;

				if(resultsContainer instanceof DimensionalViewContainer)	{
					DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
					if(dimensionalViewContainer != null)	{
						copyNumberContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
					}
				}
				else if(resultsContainer instanceof CopyNumberSingleViewResultsContainer)	{ //for single
					copyNumberContainer = (CopyNumberSingleViewResultsContainer) resultsContainer;
				}
				if(copyNumberContainer != null)	{		
				
						Collection cytobands = copyNumberContainer.getCytobandResultsets();
				    	Collection labels = copyNumberContainer.getGroupsLabels();
				    	Collection sampleIds = null;
				    	
				    	header = new StringBuffer();
				    	sampleNames = new StringBuffer();
				    	tempSampleNames = new StringBuffer();
				        stringBuffer = new StringBuffer();
				        			        
				    	sampleNames.append(" , ");
				    	
				    	header.append("Cytoband,Reporter");

				    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				        	String label = (String) labelIterator.next();
				        	
				        	sampleIds = copyNumberContainer.getBiospecimenLabels(label); 

					           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
					            	tempSampleNames.append("," + sampleIdIterator.next().toString().substring(2)); 
						        	theLabels.append(","+label); 
					           	}
				    	}
				    	//header.append("\n"); 
				    	theLabels.append("\n");
				    	
						//sb.append(header.toString());
						//sb.append(sampleNames.toString());
						
			    		boolean showLL = true;
			    		boolean showAcc = true;
			    		boolean showGenes = true;
			    		
				    	for (Iterator cytobandIterator = cytobands.iterator(); cytobandIterator.hasNext();) {
				    		CytobandResultset cytobandResultset = (CytobandResultset)cytobandIterator.next();
				    		String cytoband = cytobandResultset.getCytoband().getValue().toString();
				    		Collection reporters = copyNumberContainer.getRepoterResultsets(cytoband); 
				    		recordCount += reporters.size();
				        	for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
				        		
				        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
				        		String reporterName = reporterResultset.getReporter().getValue().toString();
				        		Collection groupTypes = copyNumberContainer.getGroupByResultsets(cytoband,reporterName); 

				        		stringBuffer.append(cytoband+","+reporterName);
				        		
				        		if(showGenes)	{
			        				header.append(",Gene Symbols");
			        				sampleNames.append(", ");
			        				showGenes = false;
			        			}
				        		if(showLL)	{
			        				header.append(",Locus Link");
			        				sampleNames.append(", ");
			        				showLL = false;
			        			}
				        		if(showAcc){
			        				header.append(",Acc.No.");
			        				sampleNames.append(", ");
			        				showAcc = false;
			        			}
				        		
				        		//show 3 annotations
			        			String genes = "";
			        			try	{
					        		HashSet geneSymbols = new HashSet(reporterResultset.getAssiciatedGeneSymbols());
					        		// Collection geneSymbols = reporterResultset.getAssiciatedGeneSymbols();
					        		if(geneSymbols != null){
	
					        			for(Iterator geneIterator = geneSymbols.iterator(); geneIterator.hasNext();)
					        			{
					        				try	{
						        				Object geneObj = geneIterator.next();
						        				if(geneObj != null){
							        				genes += geneObj.toString();
							        				genes += " | ";
						        				}
					        				}
					        				catch(Exception e)	{ }
					        			}
					        		}
					        		else	{
					        			genes = "xx";
					        		}
			        			}
			        			catch(Exception e)	{
			        				genes = "xxx";	
			        			}
			        			stringBuffer.append(","+genes);
				        		try	{
				        			stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("|"));
				        		}
				        		catch(Exception e) { }

			        			String ll = "";
			        			try	{
					        		HashSet locusLinkIds = new HashSet(reporterResultset.getAssiciatedLocusLinkIDs());
					        		if(locusLinkIds != null){
					        			for(Iterator LLIterator = locusLinkIds.iterator(); LLIterator.hasNext();)
					        			{
					        				try	{
						        				Object llObj = LLIterator.next();
						        				if(llObj!=null){
						        					ll += llObj.toString();
						        					ll += " | ";
						        				}
					        				}
					        				catch(Exception e) { }
					        			}	
					        		}
					        		else	{
					        			ll = "xx";
					        		}
			        			}
			        			catch(Exception e){
			        				ll = "xxx";
			        			}
				        		
				        		stringBuffer.append(","+ll);
				        		try	{
				        			stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("|"));
				        		}
				        		catch(Exception e){ }

			        			String acc = "";
			        			try	{
					        		HashSet accNumbers = new HashSet(reporterResultset.getAssiciatedGenBankAccessionNos());
					        		if(accNumbers!=null)	{
					        			for(Iterator accIterator = accNumbers.iterator(); accIterator.hasNext();)
					        			{
					        				try	{
						        				Object accObj = accIterator.next();
						        				if(accObj!=null){
						        					acc += accObj.toString();
						        					acc += " | ";
						        				}	
					        				}
					        				catch(Exception e){	}
					        			}
	
					        		}
					        		else	{
					        			acc = "xx";
					        		}
			        			}
			        			catch(Exception e){	}

				        		stringBuffer.append(", "+acc);
				        		try	{
				        			stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("|"));
				        		}
				        		catch(Exception e) { }
				        		//sampleNames.append("\n");

				        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				        			String label = (String) labelIterator.next();
				        			ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset.getGroupByResultset(label);
				        			
				        			sampleIds = copyNumberContainer.getBiospecimenLabels(label);
				        			if(groupResultset != null)
				        			{
				                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				                       		String sampleId = (String) sampleIdIterator.next();
				                       		SampleCopyNumberValuesResultset sampleResultset2 = (SampleCopyNumberValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
				                       		if(sampleResultset2 != null){
				                       			Double ratio = (Double)sampleResultset2.getCopyNumber().getValue();
				                       			if(ratio != null)
				                       				stringBuffer.append(","+resultFormat.format(ratio));
				                       			else 
				                       				stringBuffer.append(",-");
				                       		}
				                       		else 
				                       		{
				                       			stringBuffer.append(",-");
				                       		}
				                       	}
				        			}
				        			else	{
				                    	for(int s=0;s<sampleIds.size();s++) 
				                    		stringBuffer.append(",-");       
				                    }
				         		}
				        		stringBuffer.append("\n");
				    		}
				        	//append the extra row here
				        	//sb.append("\n");
				    	}
				
				}
			
			else	{
				sb.append("Copy Number container is empty");
			}	
				
				sb.append(header.toString() + theLabels.toString()); // add header
				sb.append(sampleNames.toString() + tempSampleNames.toString() + "\n"); // add sample rows
				sb.append(stringBuffer.toString()); // add data
				
			return sb.toString();
				
	}


	public static String geneExprSampleView(ResultsContainer resultsContainer)	{
		
				StringBuffer sb = new StringBuffer();
		    	StringBuffer header = new StringBuffer();
		    	StringBuffer sampleNames = new StringBuffer();
		    	StringBuffer tempSampleNames = new StringBuffer();
		        StringBuffer stringBuffer = new StringBuffer();
		        StringBuffer theLabels = new StringBuffer();
		        
				int recordCount = 0;
				GeneExprSingleViewResultsContainer geneViewContainer = null;
				if(resultsContainer instanceof DimensionalViewContainer)	{
					DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
					if(dimensionalViewContainer != null)	{
						geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
					}
				}
				else if(resultsContainer instanceof GeneExprSingleViewResultsContainer)	{ //for single
					geneViewContainer = (GeneExprSingleViewResultsContainer) resultsContainer;
				}
				
				if(geneViewContainer != null)	{
					Collection genes = geneViewContainer.getGeneResultsets();
			    	Collection labels = geneViewContainer.getGroupsLabels();
			    	Collection sampleIds = null;
		
			    	header = new StringBuffer();
			    	
			    	sampleNames = new StringBuffer();
			        stringBuffer = new StringBuffer();
			    	tempSampleNames = new StringBuffer();
			    	
			    	header.append("Gene,Reporter");
			    	sampleNames.append(" , "); 
				   
			    	theLabels = new StringBuffer();
			    	
			    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        	String label = (String) labelIterator.next();
			        	sampleIds = geneViewContainer.getBiospecimenLabels(label);    	
			        	//header.append(","+label); 

				           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				            	tempSampleNames.append(","+sampleIdIterator.next().toString().substring(2)); 
				            	theLabels.append(","+label);
				           	}
			           	//header.deleteCharAt(header.lastIndexOf("\t"));
			    	}
			    	//sampleNames.append("\n");
			    	theLabels.append("\n"); 
			    	
			    	//sb.append(header.toString());
					//sb.append(sampleNames.toString());
		
		    		boolean showLL = true;
		    		boolean showAcc = true;
		    		
			    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
			    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
			    		Collection reporters = geneResultset.getReporterResultsets();
			    		
			    		recordCount+=reporters.size();
			    		
			    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
			        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
			        		Collection groupTypes = reporterResultset.getGroupByResultsets();
			        		String reporterName = reporterResultset.getReporter().getValue().toString();

			        		GeneSymbol gene = geneResultset.getGeneSymbol();
			        		String geneSymbol = " ";
			        		if( gene != null){
			        			geneSymbol = geneResultset.getGeneSymbol().getValueObject().toString();
			        		}
			        		stringBuffer.append(geneSymbol+","+reporterName);
			        		
			        		//stringBuffer.append(geneResultset.getGeneSymbol().getValueObject().toString()+","+reporterName);
			        		
			        		if(showLL)	{
		        				header.append(",Locus Link");
		        				sampleNames.append(", ");
		        				showLL = false;
		        			}
			        		if(showAcc){
		        				header.append(",Acc No");
		        				sampleNames.append(", ");
		        				showAcc = false;
		        			}
			        		
			        		String ll = "";
			        		try	{
				        		HashSet locusLinkIds = new HashSet(reporterResultset.getAssiciatedLocusLinkIDs());
				        		if(locusLinkIds != null){
				        			
				        			System.out.println("LLs for "+reporterName+": "+locusLinkIds.size());
				        			for(Iterator LLIterator = locusLinkIds.iterator(); LLIterator.hasNext();)
				        			{
				        				try	{
				        					Object llObj = LLIterator.next();
				        					if(llObj!=null){
				        						ll += llObj.toString();
				        						ll += " | ";
				        					}
				        				}
				        				catch(Exception e){
				        					
				        				}
				        			}
				        			/*
				        			if(showLL)	{
				        				header.append(",Locus Link");
				        				sampleNames.append(", ");
				        				showLL = false;
				        			}
				        			*/
				        			//stringBuffer.append(","+ll);
				        			//stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("|"));
				        			System.out.println("done with this LL");
				        		}
				        		else	{
				        			//stringBuffer.append(",xx");
				        			ll = "xx";
				        		}
			        		}
			        		catch(Exception e){
			        			//stringBuffer.append(",xxx");
			        			ll = "xxx";
			        		}
			        		
			        		stringBuffer.append(","+ll);
			        		try	{
			        			stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("|"));
			        		}
			        		catch(Exception e)	{
			        			
			        		}
			        		
			        		// do the acc no annotation
			        		String acc = "";
			        		try	{
				        		HashSet accNumbers = new HashSet(reporterResultset.getAssiciatedGenBankAccessionNos());
				        		if(accNumbers!=null)	{
				        			
				        			System.out.println("Acc nos for "+reporterName+": "+accNumbers.size());
				        			for(Iterator accIterator = accNumbers.iterator(); accIterator.hasNext();)
				        			{
				        				try	{
					        				Object accObj = accIterator.next();
					        				if(accObj!=null){
					        					acc += accObj.toString();
					        					acc += " | ";
					        				}
				        				}
				        				catch(Exception e)	{
				        					
				        				}
				        			}
				        			/*
				        			if(showAcc){
				        				header.append(",Acc No");
				        				sampleNames.append(", ");
				        				showAcc = false;
				        			}
				        			*/
				        			//stringBuffer.append(","+acc);
				        			//stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("|"));
				        			System.out.println("done with this acc");
				        		}
				        		else	{
				        			//stringBuffer.append(",xx");
				        			acc = "xx";
				        		}
			        		}
			        		catch(Exception e)	{
			        			//stringBuffer.append(",xxx");
			        			acc = "xxx";
			        		}
			        		
			        		stringBuffer.append(","+acc);
		        			try	{
		        				stringBuffer.deleteCharAt(stringBuffer.lastIndexOf("|"));
		        			}
		        			catch(Exception e)	{
		        			}
		        			
			        		//sampleNames.append("\n");
			        		
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
				                       			if(ratio != null)	{
				                       				try	{
				                       					stringBuffer.append(","+resultFormat.format(ratio));
				                       				}
				                       				catch(Exception e){
				                       					System.out.println("cant format result");
				                       					stringBuffer.append(",x");
				                       				}
				                       			}
					                       		else
					                       			stringBuffer.append(",x ");
				                       		}
				                       		else 
				                       		{
				                       			stringBuffer.append(",x ");
				                       		}
				                       	}
			                       }
			                       else	{
			                       for(int s=0;s<sampleIds.size();s++) 
			                       		stringBuffer.append(",x ");                      
			                       }
			
			         		}
			         		
			        		stringBuffer.append("\n");
			    		}
			    		// add the line between genes
			    		// sb.append("\n");
			    	}
				}
				else {
					stringBuffer.append("Gene Container is empty<br>");
				}
				sb.append(header.toString() + theLabels.toString()); // add header
				sb.append(sampleNames.toString() + tempSampleNames.toString() + "\n"); // add sample rows
				sb.append(stringBuffer.toString()); // add data
				
			    return sb.toString();
	
		
	}

}
