package gov.nih.nci.nautilus.ui;

import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.*;
import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.resultset.*;
import gov.nih.nci.nautilus.resultset.gene.*;
import gov.nih.nci.nautilus.resultset.sample.*;
import gov.nih.nci.nautilus.resultset.copynumber.*;
import gov.nih.nci.nautilus.view.*;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Landyr
 * Date: Nov 3, 2004
 * 
 */
public class ReportGenerator  {

	// the colors for the columns and dynamic css (blue/yellow)
	// TODO: move these colors to a props file and make more colors
	
	
	public static String theColors[] = {"0073E6","FFFF61"};
	public static final DecimalFormat resultFormat = new DecimalFormat("0.0000");
	
			
	public static String displayReport(QueryCollection queryCollection, boolean csv)	{
		
		StringBuffer html = new StringBuffer();
		StringBuffer errors = new StringBuffer();
		Resultant resultant;
		errors.append("<br><a href=\"menu.do\">[Back to Menu]</a><br>");
			
		try	{
			
		//	QueryCollection queryCollection = (QueryCollection) (session.getAttribute(gov.nih.nci.nautilus.constants.Constants.QUERY_KEY));
			CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();
	
			try	{
				resultant = ResultsetManager.executeQuery(myCompoundQuery);
	  		}
	  		catch (Exception e)	{
	  			errors.append("Error executing the query.<Br><Br>");
	  			return errors.toString();
	  		}
  		
			if(resultant != null) {      
		 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
		 		if(resultsContainer != null)	{
			 		html.append("<a href=\"jsp/geneViewReportCSV.jsp\" onclick=\"javascript:return false;\">[Download this report for Excel]</a> | <a href=\"menu.do\">[Back to Menu]</a><br>\n");
			 		Viewable view = resultant.getAssociatedView();
			 		 
			 		//4 views here, returning the String of HTML for report
			 		// need to add the html buffer here
			 		
		 			if (view instanceof GeneExprSampleView)	{ 
		 				//return this.geneExprSampleView(resultsContainer);
		 				html.append(geneExprSampleView(resultsContainer));
		 				return html.toString();
		 			}
		 			else if (view instanceof CopyNumberSampleView)	{ 
		 				html.append(copyNumberSampleView(resultsContainer));
		 				return html.toString();
		 			}
		 			else if (view instanceof GeneExprDiseaseView)	{
		 				html.append(geneExprDiseaseView(resultsContainer));
		 				return html.toString();
		 			}
	 				else if(view instanceof ClinicalSampleView){
	 					html.append(clinicalSampleView(resultsContainer));
	 					return html.toString();
	 				}	
	 				else	{
						errors.append("Error with report view<Br><Br>");
						return errors.toString();
					}
			 	}
			 	else	{
			 		errors.append("Results Container is Null<br>\n");
			 		return errors.toString();
			 	}
			 } //resultant != null
			 else	{
			 	errors.append("Resultant is NULL<br>\n");
			 	return errors.toString();
			 }
		}
		
		catch(Exception e)	{
			errors.append("Error Displaying the Report.<Br>\n");
			return errors.toString();
		}
		
	}
	
	
	
	public static String clinicalSampleView(ResultsContainer resultsContainer)	{
			
			boolean gLinks = false;
			boolean cLinks = false;
			StringBuffer sb = new StringBuffer();
			System.out.println("HERE IS THE CLINICAL VIEW");
			DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
		//	CopyNumberSingleViewResultsContainer copyNumberContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
			// Are we making hyperlinks?
			if(dimensionalViewContainer.getGeneExprSingleViewContainer() != null)	{
				// show the geneExprHyperlinks
				gLinks = true;
			}
			if(dimensionalViewContainer.getCopyNumberSingleViewContainer() != null)	{
				// show the copyNumberHyperlinks
				cLinks = true;
			}
			sb.append("<table>\n");
			SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
			Collection samples = sampleViewContainer.getBioSpecimenResultsets();
 		   	sb.append("<Tr><Td>SAMPLE</td><td>AGE at Dx</td><td>GENDER</td><td>SURVIVAL</td><td>DISEASE</td></tr>");
   			for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
   				SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
	   			sb.append("<tr><td>"+sampleResultset.getBiospecimen().getValue()+ "</td>" +
   					"<Td>"+sampleResultset.getAgeGroup().getValue()+ "</td>" +
					"<td>"+sampleResultset.getGenderCode().getValue()+ "</td>" +
					"<td>"+sampleResultset.getSurvivalLengthRange().getValue()+ "</td>" +
					"<Td>"+sampleResultset.getDisease().getValue() + "</td></tr>");
    		}
    		sb.append("</table>\n<br>");
    		return sb.toString();
	}
	
	
	public static String geneExprDiseaseView(ResultsContainer resultsContainer)	{
		
		StringBuffer sb = new StringBuffer();
		GeneExprResultsContainer geneExprDiseaseContainer = (GeneExprResultsContainer) resultsContainer;
		
					if(geneExprDiseaseContainer != null)	{
				    	Collection genes = geneExprDiseaseContainer.getGeneResultsets();
				    	Collection labels = geneExprDiseaseContainer.getGroupsLabels();
				    	Collection sampleIds = null;

				    	/*
				    	StringBuffer header = new StringBuffer();
				    	StringBuffer sampleNames = new StringBuffer();
				        StringBuffer stringBuffer = new StringBuffer();
				        */
				        
				    	//get group size (as Disease or Agegroup )from label.size
				        String label = null;
				    	
				    	sb.append("<table>\n");
				    	
				        //set up the header for the table
				    	sb.append("<tr><Td>Gene Name</td><td>Reporter Name</td>");
					   
				    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				        	label = (String) labelIterator.next();
				        	sb.append("<Td>"+label+"</td>"); 
				    	}
			
						sb.append("</tr>\n");
						
				    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
				    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
				    		String geneSymbol = geneResultset.getGeneSymbol().getValue().toString();
				    		Collection reporters = geneExprDiseaseContainer.getRepoterResultsets(geneSymbol); 

				    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
				        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
				        		String reporterName = reporterResultset.getReporter().getValue().toString();
				        		Collection groupTypes = geneExprDiseaseContainer.getGroupByResultsets(geneSymbol,reporterName); //reporterResultset.getGroupResultsets();
				        	//	stringBuffer = new StringBuffer();
							
							/*
				        		if(reporterName.length()< 10){ //Remove this from table
				        			reporterName= reporterName+"        ";
				        			reporterName = reporterName.substring(0,10);
				        		}
				        	*/
				        		
				        		sb.append("<tr><td>"+geneSymbol+"</td><td>" + reporterName + "</td>");
				        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				    	        	label = (String) labelIterator.next();
				    	        	DiseaseGroupResultset diseaseResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(label);
				    	        	if(diseaseResultset != null){
			                   			Double ratio = (Double)diseaseResultset.getFoldChangeRatioValue().getValue();
			                   			Double pvalue = (Double)diseaseResultset.getRatioPval().getValue();
			                   			sb.append("<td>"+resultFormat.format(ratio)+" ("+resultFormat.format(pvalue)+")"+"</td>");  
			                   			}
			                   		else	{
			                   			sb.append("<Td>-</td>");
			                   		}
				    	    	}
	   	                   		sb.append("</tr>");
				    		}
				    		// add the line between genes
				    		sb.append("<tr><td colspan=\"1000\" style=\"height:3px; font:3px; border-top:1px solid black\">&nbsp;</td></tr>\n");
					    	
				    	}
					sb.append("</table>\n\n");
				}
				else	{
					sb.append("<Br><br>Gene Disease View container is empty");
				}
				
				return sb.toString();
	}


	public static String copyNumberSampleView(ResultsContainer resultsContainer)	{
		
				StringBuffer sb = new StringBuffer();
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
				        			        
				        ArrayList cssLabels = new ArrayList(); //create the CSS dynamically
				        
				        //set up the header for the table
				        header.append("<Tr>");
				    	header.append("<Td colspan='2'>&nbsp;</td>");
				    	
				    	sampleNames.append("<tr>");
				    	sampleNames.append("<Td>Cytoband</td><td>Reporter Name</td>");
					   
				    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				        	String label = (String) labelIterator.next();
				        	
				        	sampleIds = copyNumberContainer.getBiospecimenLabels(label); 
				        	header.append("<Td colspan='"+sampleIds.size()+"'>"+label+"</td>"); 
		  		        	cssLabels.add(label);
		  		        	   	
					           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
					            	sampleNames.append("<td>" + sampleIdIterator.next()+"</td>"); 
					           	}
				    	}
				    	header.append("</tr>\n"); 
				    	sampleNames.append("</tr>\n");
				    	
				    	//generate the CSS once we have all the labels
						StringBuffer css = new StringBuffer();
						css.append("<style>\n");
						String color = "";
						String font = "";
						for (int i = 0; i < cssLabels.size(); i++) {
							if(i%2 == 0)
							{
								color = theColors[1];
								font = theColors[0];
							}
							else
							{
								color = theColors[0];
								font = theColors[1];
							}
						    css.append("td."+(String)(cssLabels.get(i))+ " { background-color: #"+color+"; color: #"+font+" }\n");
						}
						css.append("</style>\n");
						
						sb.append(css.toString());
						
						sb.append("<table>\n");
						sb.append(header.toString());
						sb.append(sampleNames.toString());
				    	
				    	for (Iterator cytobandIterator = cytobands.iterator(); cytobandIterator.hasNext();) {
				    		CytobandResultset cytobandResultset = (CytobandResultset)cytobandIterator.next();
				    		String cytoband = cytobandResultset.getCytoband().getValue().toString();
				    		Collection reporters = copyNumberContainer.getRepoterResultsets(cytoband); 
	
				        	for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
				        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
				        		String reporterName = reporterResultset.getReporter().getValue().toString();
				        		Collection groupTypes = copyNumberContainer.getGroupByResultsets(cytoband,reporterName); 
				        		//stringBuffer = new StringBuffer();
				        		
								/*
				        		if(reporterName.length()< 10){ //Remove this from table
				        			reporterName= reporterName+"        ";
				        			reporterName = reporterName.substring(0,10);
				        		}
				        		*/
				        		sb.append("<tr><td>"+cytoband+"</td><td>"+reporterName+"</td>");
				        		for (Iterator groupIterator = groupTypes.iterator(); groupIterator.hasNext();) {
				        			ViewByGroupResultset groupResultset = (ViewByGroupResultset)groupIterator.next();
				        			String label = groupResultset.getType().getValue().toString();
				        			sampleIds = copyNumberContainer.getBiospecimenLabels(label);
				                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				                       		String sampleId = (String) sampleIdIterator.next();
				                       		SampleCopyNumberValuesResultset sampleResultset2 = (SampleCopyNumberValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
				                       		if(sampleResultset2 != null){
				                       			Double ratio = (Double)sampleResultset2.getCopyNumber().getValue();
				                       			sb.append("<td class='"+label+"'>"+resultFormat.format(ratio)+"</td>");  
				                       			}
				                       		else 
				                       		{
				                       			sb.append("<td class='"+label+"'> - </td>");
				                       		}
				                       	}
				         		}
				        		sb.append("</tr>");
				    		}
				    	}
					sb.append("</table><Br><br>");	
				}
				
				else	{
					sb.append("<br><br>Copy Number container is empty");
				}
				
			}
			else	{
				sb.append("<br><br>Copy Number container is empty");
			}	
			
			return sb.toString();
				
	}


	public static String geneExprSampleView(ResultsContainer resultsContainer)	{
		
				StringBuffer sb = new StringBuffer();
		
			    DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
			    if(dimensionalViewContainer != null)	{
		        	GeneExprSingleViewResultsContainer geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
			    	Collection genes = geneViewContainer.getGeneResultsets();
			    	Collection labels = geneViewContainer.getGroupsLabels();
			    	Collection sampleIds = null;
			    	StringBuffer header = new StringBuffer();
			    	header.append("<table>\n<tr>\n");
			    	StringBuffer sampleNames = new StringBuffer();
			        StringBuffer stringBuffer = new StringBuffer();
			    	
			        //set up the header for the table
			    	header.append("<Td>Gene</td>\n<td>Reporter</td>\n");
			    	sampleNames.append("<tr><Td> &nbsp;</td><Td> &nbsp;</tD>"); 
				   
					ArrayList cssLabels = new ArrayList();
				   
			    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        	String label = (String) labelIterator.next();
			        	sampleIds = geneViewContainer.getBiospecimenLabels(label);    	
				    	header.append("<td colspan="+sampleIds.size()+" class='"+label+"'>"+label+"</td>"); 
				    	cssLabels.add(label);
				    	
				           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				            	sampleNames.append("<td class='"+label+"'>"+sampleIdIterator.next()+"</td>"); 
				            	header.append("\t");
				           	}
			           	header.deleteCharAt(header.lastIndexOf("\t"));
			    	}
			    	sampleNames.append("</tr>");
			    	header.append("</tr>"); 
			    	
					//generate the CSS once we have all the labels
					StringBuffer css = new StringBuffer();
					css.append("<style>\n");
					String color = "";
					String font = "";
					for (int i = 0; i < cssLabels.size(); i++) {
						if(i%2 == 0)
						{
							color = theColors[1];
							font = theColors[0];
						}
						else
						{
							color = theColors[0];
							font = theColors[1];
						}
					    css.append("td."+(String)(cssLabels.get(i))+ " { background-color: #"+color+"; color: #"+font+" }\n");
					}
					css.append("</style>\n");
					
					
					sb.append(css.toString());
			    	sb.append(header.toString());
					sb.append(sampleNames.toString());
			
			    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
			    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
			    		Collection reporters = geneResultset.getReporterResultsets();
			    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
			        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
			        		Collection groupTypes = reporterResultset.getGroupByResultsets();
			        	//	stringBuffer = new StringBuffer();
			        		String reporterName = reporterResultset.getReporter().getValue().toString();

			        		sb.append("<tr><td>"+geneResultset.getGeneSymbol().getValueObject().toString()+"</td><td>"+
			    					reporterName+"</td>");
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
					                       			sb.append("<Td class='"+label+"'>"+resultFormat.format(ratio)+" </td>");                                 
					                       		else
					                      			sb.append("<td class='"+label+"'>-</td>");
				                       		}
				                       		else 
				                       		{
				                       			sb.append("<td class='"+label+"'>-</td>");
				                       		}
				                       	}
			                       }
			                       else	{
			                       for(int s=0;s<sampleIds.size();s++) 
			                        	sb.append("<td>+</td>");                      
			                       }
			
			         		}
			         		
			        		sb.append("</tr>\n");
			    		}
			    		// add the line between genes
			    		sb.append("<tr><td colspan=\"1000\" style=\"height:3px; font:3px; border-top:1px solid black\">&nbsp;</td></tr>\n");
			    	}
						sb.append("</table>");
				}
				else {
					sb.append("<br><Br>Gene Container is empty<br>");
				}
				
				return sb.toString();
		
		
	}

}
