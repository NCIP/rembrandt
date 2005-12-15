package gov.nih.nci.rembrandt.web.xml;

import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.CytobandResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.copynumber.SampleCopyNumberValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ViewByGroupResultset;
import gov.nih.nci.rembrandt.web.helper.FilterHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * @author LandyR
 * Feb 8, 2005
 * 
 */
public class CopyNumberSampleReport implements ReportGenerator{

	/**
	 * 
	 */
	public CopyNumberSampleReport() {
		super();
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.ui.report.ReportGenerator#getTemplate(gov.nih.nci.nautilus.resultset.Resultant, java.lang.String)
	 */
	public Document getReportXML(Resultant resultant, Map filterMapParams) {

		//String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };
		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		String delim = " | ";
		ArrayList filter_string = new ArrayList();	// hashmap of genes | reporters | cytobands
		String filter_type = "show"; 		// show | hide
		String filter_element = "none"; 	// none | gene | reporter | cytoband

		if(filterMapParams.containsKey("filter_string") && filterMapParams.get("filter_string") != null)
			filter_string = (ArrayList) filterMapParams.get("filter_string");
		if(filterMapParams.containsKey("filter_type") && filterMapParams.get("filter_type") != null)		
			filter_type = (String) filterMapParams.get("filter_type");
		if(filterMapParams.containsKey("filter_element") && filterMapParams.get("filter_element") != null)		
			filter_element = (String) filterMapParams.get("filter_element");

			Document document = DocumentHelper.createDocument();

			Element report = document.addElement( "Report" );
			Element cell = null;
			Element data = null;
			Element dataRow = null;
			//add the atts
	        report.addAttribute("reportType", "Copy Number");
	        //fudge these for now
	        report.addAttribute("groupBy", "none");
	        String queryName = resultant.getAssociatedQuery().getQueryName();
	        //set the queryName to be unique for session/cache access
	        report.addAttribute("queryName", queryName);
	        report.addAttribute("sessionId", "the session id");
	        report.addAttribute("creationTime", "right now");
	        
			
			StringBuffer sb = new StringBuffer();
			int recordCount = 0;
			int totalSamples = 0;
			
			ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
	 		
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
				
				if(copyNumberContainer.getCytobandResultsets().size() > 0)	{
	
					Collection cytobands = copyNumberContainer.getCytobandResultsets();
			    	Collection labels = copyNumberContainer.getGroupsLabels();
			    	Collection sampleIds = null;
			    	
			    	StringBuffer header = new StringBuffer();
			    	StringBuffer sampleNames = new StringBuffer();
			        StringBuffer stringBuffer = new StringBuffer();
			        
			        /*
			        sampleNames.append("<Tr>");
			    	sampleNames.append("<Td>&nbsp;</td><Td>&nbsp;</td>");
			    	
			    	header.append("<tr>");
			    	header.append("<Td id=\"header\">Cytoband</td><td id=\"header\">Reporter</td>");
				   */
			        
			        Element headerRow = report.addElement("Row").addAttribute("name", "headerRow");
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Cytoband");
				        data = null;
			        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Reporter");
				        data = null;
			        cell = null;
			        
			        /*
			         * Start annotations (csv only)
			         *  1) Bp position
			         *  2) associated genes (pipe delimimted list)
			         */
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Bp Position");
				        data = null;
			        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Genes");
				        data = null;
			        cell = null;

		        
			        Element sampleRow = report.addElement("Row").addAttribute("name", "sampleRow");
			        cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(" ");
			        	data = null;
			        cell = null;
			        cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(" ");
			        	data = null;
			        cell = null;
			        cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(" ");
			        	data = null;
			        cell = null;
			        cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(" ");
			        	data = null;
			        cell = null;
			       
			    	//this nested loop generates the header row and the samples row
			    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        	String label = (String) labelIterator.next();
			        	
			        	sampleIds = copyNumberContainer.getBiospecimenLabels(label); 
			        	totalSamples += sampleIds.size();
			        	
			        		cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", label).addAttribute("group", label);
				        		data = cell.addElement("Data").addAttribute("type", "header").addText(label+" Samples");
					        	data = null;
					        cell = null;
			        	
			        	//header.append("<Td colspan='"+sampleIds.size()+"' class=\""+label+"\" id=\"header\">"+label+" Samples</td>"); 
	  		        	   	
				           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
        		
				        		String s = sampleIdIterator.next().toString();
				           		cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", label).addAttribute("group", label);
						        	//data = cell.addElement("Data").addAttribute("type", "header").addText(s.substring(2));
						        	data = cell.addElement("Data").addAttribute("type", "header").addText(s);
						        	data = null;
						        cell = null;
				           		
				           		//sampleNames.append("<td class='"+label+"' id=\"header\"><a href=\"report.do?s="+s+"&report=ss\">"+s.substring(2)+"</a></td>"); 
				            	//theColspan += sampleIds.size();
				           	}
			    	}
			    	//header.append("</tr>\n"); 
			    	//sampleNames.append("</tr>\n");
			    	
			    	/* done with the headerRow and SampleRow Elements, time to add data rows */
			    	
			    	for (Iterator cytobandIterator = cytobands.iterator(); cytobandIterator.hasNext();) {
			    		CytobandResultset cytobandResultset = (CytobandResultset)cytobandIterator.next();
			    		String cytoband = cytobandResultset.getCytoband().getValue().toString();
			    		Collection reporters = copyNumberContainer.getRepoterResultsets(cytoband); 
			    		
			 //   		if(!filter_element.equals("cytoband") || (filter_element.equals("cytoband") && !filter_string.contains(cytoband)))	{
			    		if(FilterHelper.checkFilter(filter_element, "cytoband", cytoband, filter_type, filter_string))	{
			    	        	
			    			recordCount += reporters.size();
				        	for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
				        		
				        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
				        		
				        		/*
				        		 * 
				        		 *  store our annotations
				        		 * 
				        		 */
				        		String bp_position = "";
				        		try	{
				        		    bp_position = reporterResultset.getStartPhysicalLocation().getValue().toString();
				        		}
				        		catch(Exception e) {}
				        		
				        		//there is a much better way to do this, but this is reused from 0.50
				        		//this code will be cleaned up for 1.0
				        		String genes = "";
			        			try	{
					        		HashSet geneSymbols = new HashSet(reporterResultset.getAssiciatedGeneSymbols());
					        		if(geneSymbols != null){
					        			genes = StringUtils.join(geneSymbols.toArray(), delim);
					        			
					        		}
					        		else	{
					        			genes = "-";
					        		}
			        			}
			        			catch(Exception e)	{
			        				genes = "--";	
			        			}
			        			
				        		
				        		String reporterName = reporterResultset.getReporter().getValue().toString();
				        		Collection groupTypes = copyNumberContainer.getGroupByResultsets(cytoband,reporterName); 
				        		
				        		if(FilterHelper.checkFilter(filter_element, "reporter", reporterName, filter_type, filter_string))	{   	
				        		//if(!filter_element.equals("reporter") || (filter_element.equals("reporter") && !filter_string.contains(reporterName)))	{		
					        		dataRow = report.addElement("Row").addAttribute("name", "dataRow");
							        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "cytoband").addAttribute("group", "header");
							        	data = cell.addElement("Data").addAttribute("type", "header").addText(cytoband);
							        	data = null;
							        cell = null;
							        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "reporter").addAttribute("group", "header");
							        	data = cell.addElement("Data").addAttribute("type", "header").addText(reporterName);
							        	data = null;
							        cell = null;
					        		//sb.append("<tr><td>"+cytoband+"</td><td>"+reporterName+"</td>");
							        
							        /*
							         * 
							         *  actually add the annotations to the report
							         * 
							         */
							        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "header");
							        	data = cell.addElement("Data").addAttribute("type", "header").addText(bp_position);
							        	data = null;
							        cell = null;
							        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "header");
							        	data = cell.addElement("Data").addAttribute("type", "header").addText(genes);
							        	data = null;
							        cell = null;
							        
					        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
					        			String label = (String) labelIterator.next();
					        			ViewByGroupResultset groupResultset = (ViewByGroupResultset) reporterResultset.getGroupByResultset(label);
					        			
					        			sampleIds = copyNumberContainer.getBiospecimenLabels(label);
					        			String hClass = label;
					        			if(groupResultset != null)
					        			{		
					                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
					                       		String sampleId = (String) sampleIdIterator.next();
					                       						                       		
					                       		SampleCopyNumberValuesResultset sampleResultset2 = (SampleCopyNumberValuesResultset) groupResultset.getBioSpecimenResultset(sampleId);
					                       		
					                       		if(sampleResultset2 != null){
					                       			
					                       			if(sampleResultset2.isHighlighted())
				                       					hClass="highlighted";
					                       			else
				                       					hClass = label;
					                       			
					                       			Double ratio = (Double) sampleResultset2.getCopyNumber().getValue();
					                       			if(ratio != null)	{
					                       				//sb.append("<td class='"+label+"'>"+resultFormat.format(ratio)+"</td>");
					                       				cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", hClass).addAttribute("group", label);
						    					        	data = cell.addElement("Data").addAttribute("type", "data").addText(resultFormat.format(ratio));
						    					        	data = null;
						    					        cell = null;
					                       			}
					                       			else	{
					                       				//sb.append("<td class='"+label+"'>-</td>");
					                       				cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", hClass).addAttribute("group", label);
						    					        	data = cell.addElement("Data").addAttribute("type", "data").addText("-");
						    					        	data = null;
						    					        cell = null;
					                       			}
					                       		}
					                       		else	{
					                       			//sb.append("<td class='"+label+"'>-</td>");
				                       				
				                       				cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", hClass).addAttribute("group", label);
					    					        	data = cell.addElement("Data").addAttribute("type", "data").addText("-");
					    					        	data = null;
					    					        cell = null;
					                       		}
					                       	}
					        			}
					                    else	{
					                    	for(int s=0;s<sampleIds.size();s++)	{ 
					                    		cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", hClass).addAttribute("group", label);
				    					        	data = cell.addElement("Data").addAttribute("type", "data").addText("-");
				    					        	data = null;
				    					        cell = null;
					                    		//sb.append("<td class='"+label+"'>-</td>");
					                    	}
					                    }
					         		}
					        		//sb.append("</tr>\n");
					    		}/* close reporter filter */	
				        	} 
				        	//sb.append("<tr><td colspan=\""+theColspan+"\" class=\"geneSpacerStyle\">&nbsp;</td></tr>\n");
				    	}	/* close cyto filter */
				//sb.append("</table><Br><br>");
			    	} 
			}
			
			else	{
				//TODO: handle these errs
				sb.append("<br><br>Copy Number container is empty");
			}
			
		}
		else	{
			//TODO: handle these errs
			sb.append("<br><br>Copy Number container is empty");
		}	
		
		return document;
		//return "<div class=\"rowCount\">"+ helpFul +recordCount+" records returned. "+ totalSamples + " samples returned. &nbsp;&nbsp;&nbsp;" + links +"</div>\n" + sb.toString();
	}

}
