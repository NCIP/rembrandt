package gov.nih.nci.nautilus.ui.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.nautilus.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.nautilus.resultset.DimensionalViewContainer;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;
import gov.nih.nci.nautilus.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.nautilus.resultset.gene.ViewByGroupResultset;

import org.javaby.jbyte.Template;

/**
 * @author BauerD
 * Feb 8, 2005
 * 
 */
public class GeneExprSampleReport implements ReportGenerator{

	/**
	 * 
	 */
	public GeneExprSampleReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.ui.report.ReportGenerator#getTemplate(gov.nih.nci.nautilus.resultset.Resultant, java.lang.String)
	 */
	public Template getReportTemplate(Resultant resultant, String skin) {

		//	TODO: have setter or put in props file
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
		    //	Template cssTemplate = null;
		    //	is this a CSV report
		    boolean csv = false;
		    
		    //are we doing a csv (assuming that the string "csv" is in the csv template name
		    if(skin.indexOf("csv") != -1)
		    	csv = true;
		    else
		    	csv = false;
		    
		    ResultsContainer  resultsContainer = resultant.getResultsContainer();
		    
			GeneExprSingleViewResultsContainer geneViewContainer = null;
			StringBuffer sb = new StringBuffer();
			
			//String helpFul = helpLink + "?sect=sample" + helpLinkClose;
			
			DimensionalViewContainer dimensionalViewContainer = null;
			int recordCount = 0;
			int totalSamples = 0;
			
			if(resultsContainer instanceof DimensionalViewContainer)	{
				dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
				if(dimensionalViewContainer != null)	{
					geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
					// bind the whole thing to the session once
					//request.getSession(true).setAttribute("_dv", dimensionalViewContainer);
				}
			}
			else if(resultsContainer instanceof GeneExprSingleViewResultsContainer)	{ //for single
				geneViewContainer = (GeneExprSingleViewResultsContainer) resultsContainer;
			}
			
			
			
			if(geneViewContainer != null)	{
		    	Collection genes = geneViewContainer.getGeneResultsets();
		    	Collection labels = geneViewContainer.getGroupsLabels();
		    	Collection sampleIds = null;
	
		    	StringBuffer header = new StringBuffer();
		    	
		    	//header.append("<table cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n");
		    	StringBuffer sampleNames = new StringBuffer();
		        StringBuffer stringBuffer = new StringBuffer();
		    	
		        //set up the header for the table
		        headerCell = report.get("headerCell");
			        headerCell.set("headerValue", "Gene");
			        headerCell.set("headerColspan", "1");
		        report.append("headerCell", headerCell);
		        headerCell = report.get("headerCell");
			        headerCell.set("headerValue", "Reporter");
			        headerCell.set("headerColspan", "1");
		        report.append("headerCell", headerCell);
		        
		    	//header.append("<Td id=\"header\">Gene</td>\n<td id=\"header\">Reporter</td>\n");
		        
		        sampleCell = report.get("sampleCell");
		        	sampleCell.set("sampleValue", "&nbsp;");
		        	sampleCell.set("sampleColspan", "1");
		        report.append("sampleCell", sampleCell);
		        sampleCell = report.get("sampleCell");
		        	sampleCell.set("sampleValue", "&nbsp;");
		        	sampleCell.set("sampleColspan", "1");
		        report.append("sampleCell", sampleCell);
		        
		    	//sampleNames.append("<tr><Td> &nbsp;</td><Td> &nbsp;</tD>"); 
			   
		    	int theColspan = 2; // start counting with the 2 cells above
		    	
				ArrayList cssLabels = new ArrayList();
			   
		    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
		        	String label = (String) labelIterator.next();
		        	sampleIds = geneViewContainer.getBiospecimenLabels(label);    	
			    	theColspan += sampleIds.size();
			    	totalSamples += sampleIds.size();
			    	
			    	if(!csv)	{
		        		//create a new cell with colspan for every group/label and append to the row
		        		headerCell = report.get("headerCell");
			        		headerCell.set("headerColspan", Integer.toString(sampleIds.size()) );
			        		headerCell.set("headerValue", label+" Samples");
		        		report.append("headerCell", headerCell);
		        	}
			    	
		        	//header.append("<td colspan="+sampleIds.size()+" class='"+label+"' id=\"header\">"+label+" Samples</td>"); 
			    	cssLabels.add(label);
			    	
			           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {

			        		if(csv)	{
			        			//in csv, make a new header cell for each sample, since no colspan
			        			headerCell = report.get("headerCell");
				        			headerCell.set("headerValue", label);
				        			headerCell.set("headerColspan", "");
			        			report.append("headerCell", headerCell);
			        		}
			            	String s = sampleIdIterator.next().toString();
			            	sampleCell = report.get("sampleCell");
				            	sampleCell.set("sampleValue", s.substring(2));
				           		sampleCell.set("sampleColspan", "1");
			           		report.append("sampleCell", sampleCell);
			            	//sampleNames.append("<td class='"+label+"' id=\"header\"><a href=\"report.do?s="+s+"&report=ss\">"+s.substring(2)+"</a></td>"); 
			            	//header.append("\t");
			 
			           	}
		           	//header.deleteCharAt(header.lastIndexOf("\t"));
		    	}
		    	//sampleNames.append("</tr>");
		    	//header.append("</tr>"); 
		    	
		    	if(!csv)	{	
					//generate the CSS once we have all the labels
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
					
					report.set("css", css.toString());
					//sb.append(css.toString());
			    	//sb.append(header.toString());
					//sb.append(sampleNames.toString());
		    	}
					
		    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
		    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
		    		Collection reporters = geneResultset.getReporterResultsets();
		    		
		    		recordCount+=reporters.size();
		    		
		    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
		        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
		        		Collection groupTypes = reporterResultset.getGroupByResultsets();
		        		String reporterName = reporterResultset.getReporter().getValue().toString();
		        		GeneSymbol gene = geneResultset.getGeneSymbol();
		        		String geneSymbol = "&nbsp;";
		        		if( gene != null){
		        			geneSymbol = geneResultset.getGeneSymbol().getValueObject().toString();
		        		}
		        		
		        		row = report.get("row");
		        		cell = row.get("cell");
		        			cell.set("value", geneSymbol);
		        			cell.set("class", geneSymbol);
		        		row.append("cell", cell);
		        		cell = row.get("cell");
			        		cell.set("value", reporterName);
		        			cell.set("class", reporterName);
		        		row.append("cell", cell);
		        		
		        		//sb.append("<tr><td>"+geneSymbol+"</td><td>"+reporterName+"</td>");
		        		
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
			                       				cell = row.get("cell");
				                       				cell.set("value", resultFormat.format(ratio));
				                       				cell.set("class", label);
			                       				row.append("cell", cell);
			                       			
				                       			//sb.append("<Td class='"+label+"'>"+resultFormat.format(ratio)+" </td>");
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
		    		}
		    		// add the line between genes
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
					//sb.append("</table>");
			}
			else {
				//TODO: handle this error
				sb.append("<br><Br>Gene Container is empty<br>");
			}
		    
		    //return "<div class=\"rowCount\">"+ helpFul +recordCount+" records returned. " + totalSamples +" samples returned. &nbsp;&nbsp;&nbsp;" + links  + "</div>\n" + sb.toString();
 
		    return report;
		}
		catch(Exception e)	{
			return null;
		}
	}

}
