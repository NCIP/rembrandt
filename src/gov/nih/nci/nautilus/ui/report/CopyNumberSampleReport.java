package gov.nih.nci.nautilus.ui.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.nautilus.resultset.DimensionalViewContainer;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.copynumber.CytobandResultset;
import gov.nih.nci.nautilus.resultset.copynumber.SampleCopyNumberValuesResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;
import gov.nih.nci.nautilus.resultset.gene.ViewByGroupResultset;

import org.javaby.jbyte.Template;

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
	public Template getReportTemplate(Resultant resultant, String skin) {

		//	have setter or put in props file
		String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };
		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		
		try	{
			//	main report template
			Template report = new Template(skin);
			
		    //	main data row/cell
		    Template row = null; 
		    Template cell = null;
		    //	header and sample cells, 1 row each
		    Template headerCell = null;
		    Template sampleCell = null;
		    //	template to hold CSS string
		    Template cssTemplate = null;
		    //	is this a CSV report
		    boolean csv = false;
		    
		    //are we doing a csv (assuming that the string "csv" is in the csv template name
		    if(skin.indexOf("csv") != -1)
		    	csv = true;
		    else
		    	csv = false;
	
			StringBuffer sb = new StringBuffer();
			int recordCount = 0;
			int totalSamples = 0;
			
			ResultsContainer  resultsContainer = resultant.getResultsContainer(); 
	 		
			CopyNumberSingleViewResultsContainer copyNumberContainer = null;
	
			if(resultsContainer instanceof DimensionalViewContainer)	{
				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
				if(dimensionalViewContainer != null)	{
					copyNumberContainer = dimensionalViewContainer.getCopyNumberSingleViewContainer();
					//request.getSession(true).setAttribute("_dv", dimensionalViewContainer);
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
			        			        
			        ArrayList cssLabels = new ArrayList(); //create the CSS dynamically
			        
			        //set up the header for the table
			        //do this in skin
			        /*
			        sampleNames.append("<Tr>");
			    	sampleNames.append("<Td>&nbsp;</td><Td>&nbsp;</td>");
			    	
			    	header.append("<tr>");
			    	header.append("<Td id=\"header\">Cytoband</td><td id=\"header\">Reporter</td>");
				   */
			        
			        //this generates the "Bar" spacer
			    	int theColspan = 2; // the 2 <Td>'s above (now defined in the skin)
			    	
			    	
			    	//this nested loop generates the header row and the samples row
			    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
			        	String label = (String) labelIterator.next();
			        	
			        	sampleIds = copyNumberContainer.getBiospecimenLabels(label); 
			        	totalSamples += sampleIds.size();
			        	
			        	if(!csv)	{
			        		//create a new cell with colspan for every group/label and append to the row
			        		headerCell = report.get("headerCell");
			        		headerCell.set("headerColspan", Integer.toString(sampleIds.size()) );
			        		headerCell.set("headerValue", label+" Samples");
			        		report.append("headerCell", headerCell);
			        	}
		        	
			        	
			        	//header.append("<Td colspan='"+sampleIds.size()+"' class=\""+label+"\" id=\"header\">"+label+" Samples</td>"); 
	  		        	cssLabels.add(label);
	  		        	   	
				           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
				           		
				           		sampleCell = report.get("sampleCell");
				           		
				        		if(csv)	{
				        			//in csv, make a new header cell for each sample, since no colspan
				        			headerCell = report.get("headerCell");
				        			headerCell.set("headerValue", label);
				        			report.append("headerCell", headerCell);
				        		}
				           		
				        		String s = sampleIdIterator.next().toString();
				           		sampleCell.set("sampleValue", s.substring(2));
				           		report.append("sampleCell", sampleCell);
				           		//sampleNames.append("<td class='"+label+"' id=\"header\"><a href=\"report.do?s="+s+"&report=ss\">"+s.substring(2)+"</a></td>"); 
				            	theColspan += sampleIds.size();
				           	}
			    	}
			    	//header.append("</tr>\n"); 
			    	//sampleNames.append("</tr>\n");
			    	
			    	if(!csv)	{	
				    	//generate the CSS once we have all the labels, not for CSV though
			    		// could make this its own template, but not now
						StringBuffer css = new StringBuffer();
						css.append("<style>\n");
						String color = "";
						String font = "";
						for (int i = 0; i < cssLabels.size(); i++) {
								int currentColor = i;
								if(currentColor < theColors.length)	{
									color = theColors[currentColor];	
								}
								else	{
								 currentColor = i - theColors.length;
								 color = theColors[currentColor];
								}
								css.append("td."+(String)(cssLabels.get(i))+ " { background-color: #"+color+"; }\n");
							}
						css.append("</style>\n");
						
						cssTemplate = report.get("css");
						cssTemplate.set("css", css.toString());
						report.append("css", cssTemplate);
						//sb.append(css.toString());
						
			    	}
			    	/*
					sb.append("<table cellpadding=\"0\" cellspacing=\"0\">\n");
					sb.append(header.toString());
					sb.append(sampleNames.toString());
					*/
			    	
			    	for (Iterator cytobandIterator = cytobands.iterator(); cytobandIterator.hasNext();) {
			    		CytobandResultset cytobandResultset = (CytobandResultset)cytobandIterator.next();
			    		String cytoband = cytobandResultset.getCytoband().getValue().toString();
			    		Collection reporters = copyNumberContainer.getRepoterResultsets(cytoband); 
			    		recordCount += reporters.size();
			        	for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
			        		
			        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
			        		String reporterName = reporterResultset.getReporter().getValue().toString();
			        		Collection groupTypes = copyNumberContainer.getGroupByResultsets(cytoband,reporterName); 
			        		
			        		//get the row, and hardcode-append the first 2 cols
			        		row = report.get("row");
			        		cell = row.get("cell");
			        			cell.set("value", cytoband);
			        			cell.set("class", cytoband);
			        		row.append("cell", cell);
			        		cell = row.get("cell");
				        		cell.set("value", reporterName);
			        			cell.set("class", reporterName);
			        		row.append("cell", cell);
			        		
			        		//sb.append("<tr><td>"+cytoband+"</td><td>"+reporterName+"</td>");
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
			                       			Double ratio = (Double) sampleResultset2.getCopyNumber().getValue();
			                       			if(ratio != null)	{
			                       				//sb.append("<td class='"+label+"'>"+resultFormat.format(ratio)+"</td>");
			                       				cell = row.get("cell");
				                       				cell.set("value", resultFormat.format(ratio));
				                       				cell.set("class", label);
			                       				row.append("cell", cell);
			                       			}
			                       			else	{
			                       				cell = row.get("cell");
				                       				cell.set("value", "-");
				                       				cell.set("class", label);
			                       				row.append("cell", cell);
			                       				//sb.append("<td class='"+label+"'>-</td>");
			                       			}
			                       		}
			                       		else	{
			                       			cell = row.get("cell");
			                       				cell.set("value", "-");
			                       				cell.set("class", label);
		                       				row.append("cell", cell);
			                       			//sb.append("<td class='"+label+"'>-</td>");
			                       		}
			                       	}
			        			}
			                    else	{
			                    	for(int s=0;s<sampleIds.size();s++)	{ 
			                    		cell = row.get("cell");
		                       				cell.set("value", "-");
		                       				cell.set("class", label);
		                   				row.append("cell", cell);
			                    		//sb.append("<td class='"+label+"'>-</td>");
			                    	}
			                    }
			         		}
			        		//sb.append("</tr>\n");
			        		report.append("row", row);
			    		}
			        	//append the extra row here
			        	//is !csv for loop thru and append a new blank td foreach theColspan
			        	if(!csv)	{
			        		row = report.get("row");
			        		for(int i=0; i<theColspan; i++)	{
			        			cell = row.get("cell");
			        			cell.set("value", "&nbsp;");
			        			cell.set("class", "geneSpacerStyle");
			        			row.append("cell", cell);
			        		}
			        		report.append("row", row);
			        	}
			        	//sb.append("<tr><td colspan=\""+theColspan+"\" class=\"geneSpacerStyle\">&nbsp;</td></tr>\n");
			    	}
				//sb.append("</table><Br><br>");	
			}
			
			else	{
				sb.append("<br><br>Copy Number container is empty");
			}
			
		}
		else	{
			sb.append("<br><br>Copy Number container is empty");
		}	
		
		return report;
			//return "<div class=\"rowCount\">"+ helpFul +recordCount+" records returned. "+ totalSamples + " samples returned. &nbsp;&nbsp;&nbsp;" + links +"</div>\n" + sb.toString();
		 }
		catch(Exception e){
			//failed to load template
			return null;
		}
	}

}
