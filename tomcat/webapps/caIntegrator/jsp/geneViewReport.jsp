	<%@ page language="java" %>
<%@ page import="
gov.nih.nci.nautilus.criteria.*,
gov.nih.nci.nautilus.de.*,
gov.nih.nci.nautilus.query.GeneExpressionQuery,
gov.nih.nci.nautilus.query.*,
gov.nih.nci.nautilus.query.QueryManager,
gov.nih.nci.nautilus.query.QueryType,
gov.nih.nci.nautilus.view.*,
gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr,
gov.nih.nci.nautilus.resultset.*,
gov.nih.nci.nautilus.resultset.copynumber.*,
gov.nih.nci.nautilus.resultset.gene.*,
gov.nih.nci.nautilus.resultset.sample.*,
gov.nih.nci.nautilus.constants.Constants,
java.text.DecimalFormat,
java.util.*" %>


<%!

	String theColors[] = {"0073E6","FFFF61"};

	 DecimalFormat resultFormat = new DecimalFormat("0.00");
     FoldChangeCriteria foldCrit;
     GeneIDCriteria  geneIDCrit;
     GeneOntologyCriteria ontologyCrit;
     PathwayCriteria pathwayCrit;
     RegionCriteria regionCrit;
     CloneOrProbeIDCriteria cloneCrit;
     CloneOrProbeIDCriteria probeCrit;
     ArrayPlatformCriteria allPlatformCrit;
     ArrayPlatformCriteria affyOligoPlatformCrit;
     ArrayPlatformCriteria cdnaPlatformCrit;
 
     ResultSet[] geneExprObjects;
     Resultant resultant;
     
	//put the functions needed here

%>

<html>
<head>
<style>
	body { font-family:arial; }
	Td {
		font-size: 10px;
		background: #F2F2F2;
		padding: 5px;
		}
</style>
<body>
<%
System.out.println("Here We Go...");

//get query collection from session
try	{
		QueryCollection queryCollection = (QueryCollection) (session.getAttribute(Constants.QUERY_KEY));
		CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();

		//execute the query
		try	{
			resultant = ResultsetManager.executeQuery(myCompoundQuery);
  		}
  		catch (Exception e)	{
  			out.println("I can not execute the query<Br><Br>");
  			e.printStackTrace();
  		}
  		
	if(resultant != null) {      
 		ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
 		System.out.println("HERE");
 		if(resultsContainer != null)	{
	 		%><a href="jsp/geneViewReportCSV.jsp" onclick="javascript:return false;">[Download this report for Excel]</a> | <a href="menu.do">[Back to Menu]</a><br><%
	
	 		Viewable view = resultant.getAssociatedView(); 
	 		
			//	GeneExprResultsContainer geneExprDiseaseContainer = (GeneExprResultsContainer) resultsContainer;

			if (view instanceof GeneExprSampleView){
				System.out.println("view:"+view);
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
			        System.out.println("GroupSize= "+labels.size());
			    	
			        //set up the header for the table
			    	header.append("<Td>Gene</td>\n<td>Reporter</td>\n");
			    	sampleNames.append("<tr><Td> </td><Td> </tD>"); //start the second pseudo row
				   
					ArrayList cssLabels = new ArrayList(); //try to create the CSS dynamically
				   
			    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        	String label = (String) labelIterator.next();
			        	sampleIds = geneViewContainer.getBiospecimenLabels(label);        // how many samples per group (label)?	
				    	header.append("<td colspan="+sampleIds.size()+" class='"+label+"'>"+label+"</td>"); //remove this for table
				    	cssLabels.add(label);
				    	
				           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				            	sampleNames.append("<td class='"+label+"'>"+sampleIdIterator.next()+"</td>"); // print the samples row
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
					out.println(css.toString());
			
			
			    	//System.out.println("Gene Count: "+genes.size());
			    	out.println(header.toString());
					out.println(sampleNames.toString());
			
			    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
			    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
			    		Collection reporters = geneResultset.getReporterResultsets();
			        	//System.out.println("Reporter Count: "+reporters.size());
			    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
			        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
			        		Collection groupTypes = reporterResultset.getGroupByResultsets();
			        		stringBuffer = new StringBuffer();
			            	//System.out.println("Group Count: "+groupTypes.size());
			        		String reporterName = reporterResultset.getReporter().getValue().toString();
			        		
			        		//get the gene name, and reporter Name
			        		stringBuffer.append("<tr><td>"+geneResultset.getGeneSymbol().getValueObject().toString()+"</td><td>"+
			    					reporterName+"</td>");
			    			// System.out.println(groupTypes.size());
			
							// iterate through the groups (lables) and get the results
			        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        			// ViewByGroupResultset groupResultset = (ViewByGroupResultset)groupIterator.next();
								String label = (String) labelIterator.next();
			        			ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset.getGroupByResultset(label);
			        			
				        			// String label = groupResultset.getType().getValue().toString();
				        			sampleIds = geneViewContainer.getBiospecimenLabels(label);
				        			if(groupResultset != null)
			        				{
				                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				                       		String sampleId = (String) sampleIdIterator.next();
				                       		SampleFoldChangeValuesResultset biospecimenResultset = (SampleFoldChangeValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
				                       		if(biospecimenResultset != null){
				                       			Double ratio = (Double)biospecimenResultset.getFoldChangeRatioValue().getValue();
				                       			if(ratio != null)
					                       			stringBuffer.append("<Td class='"+label+"'>"+resultFormat.format(ratio)+" </td>");                                 
					                       		else
					                      			stringBuffer.append("<td class='"+label+"'>-</td>");
				                       		}
				                       		else 
				                       		{
				                       			stringBuffer.append("<td class='"+label+"'>-</td>");
				                       		}
				                       	}
			                       }
			                       else	{
			                       for(int s=0;s<sampleIds.size();s++) 
			                        	stringBuffer.append("<td>+</td>");                      
			                       }
			
			         		}
			         		
			        		// System.out.println(stringBuffer.toString());
			        		out.println(stringBuffer.toString() + "</tr>");
			    		}
			    	}
						out.println("</table>");
				}
				else {
				out.println("<br><Br>Gene Container is empty<br>");
				}
			
			}
			
			
			// else if Sample View or DiseaseView
			else if (view instanceof CopyNumberSampleView){
			//do copy number per sample report here
			//System.out.println("view:"+view);
	
				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
				if(dimensionalViewContainer != null)	{		
					CopyNumberSingleViewResultsContainer copyNumberContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
					
					SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
					if(sampleViewContainer.getBioSpecimenResultsets().size() > 0 && copyNumberContainer.getCytobandResultsets().size() > 0)	{
					//System.out.println("Testing CopyNumber View for Every Sample >>>>>>>>>>>>>>>>>>>>>>>");
					Collection samples = sampleViewContainer.getBioSpecimenResultsets();
	
					//displayCopyNumberSingleView(copyNumberSingleViewResultsContainer);
					Collection cytobands = copyNumberContainer.getCytobandResultsets();
			    	Collection labels = copyNumberContainer.getGroupsLabels();
			    	Collection sampleIds = null;
			    	StringBuffer header = new StringBuffer();
			    	StringBuffer sampleNames = new StringBuffer();
			        StringBuffer stringBuffer = new StringBuffer();
			    	//get group size (as Disease or Agegroup )from label.size
			        
			        ArrayList cssLabels = new ArrayList(); //create the CSS dynamically
			        
			        //set up the header for the table
			        header.append("<Tr>");
			    	header.append("<Td colspan='2'>&nbsp;</td>");
			    	
			    	sampleNames.append("<tr>");
			    	sampleNames.append("<Td>Cytoband</td><td>Reporter Name</td>");
				   
			    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        	String label = (String) labelIterator.next();
			        	
			        	sampleIds = copyNumberContainer.getBiospecimenLabels(label); 
			        	// header.append("<Td colspan='"+sampleIds.size()+"'>"+label.substring(0,3)+"</td>"); //remove this for table
	  		        	header.append("<Td colspan='"+sampleIds.size()+"'>"+label+"</td>"); 
	  		        	cssLabels.add(label);
	  		        	   	
				           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				            	sampleNames.append("<td>" + sampleIdIterator.next()+"</td>"); 
				           	}
			    	}
			    	header.append("</tr>\n"); 
			    	sampleNames.append("</tr>\n");
		
			    	//System.out.println("Cytoband Count: "+cytobands.size());
			    	
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
					out.println(css.toString());
					
					out.println("<table>\n");
					out.println(header.toString());
					out.println(sampleNames.toString());
			    	
			    	for (Iterator cytobandIterator = cytobands.iterator(); cytobandIterator.hasNext();) {
			    		CytobandResultset cytobandResultset = (CytobandResultset)cytobandIterator.next();
			    		String cytoband = cytobandResultset.getCytoband().getValue().toString();
			    		Collection reporters = copyNumberContainer.getRepoterResultsets(cytoband); //geneResultset.getReporterResultsets();
			        	//System.out.println("Repoter Count: "+reporters.size());
			    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
			        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
			        		String reporterName = reporterResultset.getReporter().getValue().toString();
			        		Collection groupTypes = copyNumberContainer.getGroupByResultsets(cytoband,reporterName); //reporterResultset.getGroupResultsets();
			        		stringBuffer = new StringBuffer();
			            	//System.out.println("Group Count: "+groupTypes.size());
			        		if(reporterName.length()< 10){ //Remove this from table
			        			reporterName= reporterName+"        ";
			        			reporterName = reporterName.substring(0,10);
			        		}
			        		//get the gene name, and reporter Name
			        		
			        		stringBuffer.append("<tr><td>"+cytoband+"</td><td>"+
			    					reporterName+"</td>");
			        		for (Iterator groupIterator = groupTypes.iterator(); groupIterator.hasNext();) {
			        			ViewByGroupResultset groupResultset = (ViewByGroupResultset)groupIterator.next();
			        			String label = groupResultset.getType().getValue().toString();
			        			sampleIds = copyNumberContainer.getBiospecimenLabels(label);
			                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
			                       		String sampleId = (String) sampleIdIterator.next();
			                       		SampleCopyNumberValuesResultset sampleResultset2 = (SampleCopyNumberValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
			                       		//geneViewContainer.getBioSpecimentResultset(geneSymbol,reporterName,label,sampleId);
			                       		if(sampleResultset2 != null){
			                       			Double ratio = (Double)sampleResultset2.getCopyNumber().getValue();
			                       			stringBuffer.append("<td class='"+label+"'>"+resultFormat.format(ratio)+"</td>");  
			                       			}
			                       		else 
			                       		{
			                       			stringBuffer.append("<td class='"+label+"'> - </td>");
			                       		}
			                       	}
			         		}
			        		out.println(stringBuffer.toString() + "</tr>");
			    		}
			    	}
				out.println("</table><Br><br>");	
				}
				
				else	{
								out.println("<br><br>Copy Number container is empty");
						}
				}	
				else	{
					out.println("<br><br>Copy Number container is empty");
				}		     
			}
	
	
			else if (view instanceof GeneExprDiseaseView){
				// do disease view here
			 		GeneExprResultsContainer geneExprDiseaseContainer = (GeneExprResultsContainer) resultsContainer;
					if(geneExprDiseaseContainer != null)	{
				    	Collection genes = geneExprDiseaseContainer.getGeneResultsets();
				    	Collection labels = geneExprDiseaseContainer.getGroupsLabels();
				    	Collection sampleIds = null;
				    	StringBuffer header = new StringBuffer();
				    	StringBuffer sampleNames = new StringBuffer();
				        StringBuffer stringBuffer = new StringBuffer();
				    	//get group size (as Disease or Agegroup )from label.size
				        String label = null;
				    	
				    	out.println("<table>\n");
				    	
				        //set up the header for the table
				    	header.append("<tr><Td>Gene Name</td><td>Reporter Name</td>");
				    	//sampleNames.append("<Td colspan='2'> &nbsp;</td>");
					   
				    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				        	label = (String) labelIterator.next();
				        	header.append("<Td>"+label+"</td>"); 
				    	}
			
				    	//System.out.println("Gene Count: "+genes.size());
						out.println(header.toString() + "</tr>\n");
						//out.println(sampleNames.toString() + "</tr>\n");
						
				    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
				    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
				    		String geneSymbol = geneResultset.getGeneSymbol().getValue().toString();
				    		Collection reporters = geneExprDiseaseContainer.getRepoterResultsets(geneSymbol); //geneResultset.getReporterResultsets();
				        	//System.out.println("Repoter Count: "+reporters.size());
				    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
				        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
				        		String reporterName = reporterResultset.getReporter().getValue().toString();
				        		Collection groupTypes = geneExprDiseaseContainer.getGroupByResultsets(geneSymbol,reporterName); //reporterResultset.getGroupResultsets();
				        		stringBuffer = new StringBuffer();
				            	//System.out.println("Group Count: "+groupTypes.size());
				        		if(reporterName.length()< 10){ //Remove this from table
				        			reporterName= reporterName+"        ";
				        			reporterName = reporterName.substring(0,10);
				        		}
				        		//get the gene name, and reported Name
				        		
				        		stringBuffer.append("<tr><td>"+geneSymbol+"</td><td>"+
				    					reporterName+"</td>");
				        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
				    	        	label = (String) labelIterator.next();
				    	        	DiseaseGroupResultset diseaseResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(label);
				    	        	if(diseaseResultset != null){
			                   			Double ratio = (Double)diseaseResultset.getFoldChangeRatioValue().getValue();
			                   			Double pvalue = (Double)diseaseResultset.getRatioPval().getValue();
			                   			stringBuffer.append("<td>"+resultFormat.format(ratio)+" ("+resultFormat.format(pvalue)+")"+"</td>");  
			                   			}
			                   		else 
			                   		{
			                   			stringBuffer.append("<Td>-</td>");
			                   		}
				    	    	}
	   	                   		stringBuffer.append("</tr>");
				        		out.println(stringBuffer.toString());
				    		}
				    	}
			out.println("</table>\n\n");
			}
			else	{
				out.println("<Br><br>Gene Disease View container is empty");
			}
		}
		else if(view instanceof ClinicalSampleView)	{
			System.out.println("HERE IS THE CLINICAL VIEW");
			DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
			CopyNumberSingleViewResultsContainer copyNumberContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
			System.out.println("hello");
			out.println("<table>\n");
			SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
			Collection samples = sampleViewContainer.getBioSpecimenResultsets();
 		   	out.println("<Tr><Td>SAMPLE</td><td>AGE</td><td>GENDER</td><td>SURVIVAL</td><td>DISEASE</td></tr>");
			StringBuffer stringBuffer = new StringBuffer();
   			for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
   				SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
	   			out.println("<tr><td>"+sampleResultset.getBiospecimen().getValue()+ "</td>" +
   					"<Td>"+sampleResultset.getAgeGroup().getValue()+ "</td>" +
					"<td>"+sampleResultset.getGenderCode().getValue()+ "</td>" +
					"<td>"+sampleResultset.getSurvivalLengthRange().getValue()+ "</td>" +
					"<Td>"+sampleResultset.getDisease().getValue() + "</td></tr>");
    		}
    		out.println("</table>\n<br>");
		}
		else	{
			out.println("error with view<Br><Br>");
		}
	}
	else	{
		//resultsContainer is null
		out.println("Results Container is null<Br><br><a href=\"menu.do\">[Back to Menu]</a><br>");
	}
	}
	else	{
		// resultant is null
		out.println("<h4>No Results Available</h4>");
	}

}
		catch(Exception e) {
			System.out.println("error executing query");
			out.println("Error with Query");
			//redirect somewhere? back?
			e.printStackTrace();
		}

// clean up the session, but not now
//session.removeAttribute("geneViewResultSet");
%>


</body>
</html>
