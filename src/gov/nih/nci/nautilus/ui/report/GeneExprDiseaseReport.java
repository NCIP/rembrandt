package gov.nih.nci.nautilus.ui.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.nautilus.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.DiseaseGroupResultset;
import gov.nih.nci.nautilus.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;

import org.javaby.jbyte.Template;

/**
 * @author Landyr
 * Feb 8, 2005
 * 
 */
public class GeneExprDiseaseReport{// implements ReportGenerator{

	/**
	 * 
	 */
	public GeneExprDiseaseReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.ui.report.ReportGenerator#getTemplate(gov.nih.nci.nautilus.resultset.Resultant, java.lang.String)
	 */
	public Template getReportTemplate(Resultant resultant, String skin) {
		// TODO Auto-generated method stub
		
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
		    //	Template cssTemplate = null;
		    //	is this a CSV report
		    boolean csv = false;
		    
		    //are we doing a csv (assuming that the string "csv" is in the csv template name
		    if(skin.indexOf("csv") != -1)
		    	csv = true;
		    else
		    	csv = false;
		    
		    ResultsContainer  resultsContainer = resultant.getResultsContainer();
		    
		    StringBuffer sb = new StringBuffer();
			
			//String helpFul = helpLink + "?sect=diseaseGroup" + helpLinkClose;
			
			GeneExprResultsContainer geneExprDiseaseContainer = (GeneExprResultsContainer) resultsContainer;
			StringBuffer css = new StringBuffer();
			int recordCount = 0;
						if(geneExprDiseaseContainer != null)	{
					    	Collection genes = geneExprDiseaseContainer.getGeneResultsets();
					    	Collection labels = geneExprDiseaseContainer.getGroupsLabels();
					    	Collection sampleIds = null;
					        
					    	//get group size (as Disease or Agegroup )from label.size
					        String label = null;
					        
					    	//sb.append("<table cellpadding=\"0\" cellspacing=\"0\">\n");
					    	
					        //set up the header for the table
					        headerCell = report.get("headerCell");
						        headerCell.set("headerValue", "Gene");
						        headerCell.set("headerColspan", "1");
					        report.append("headerCell", headerCell);
					        headerCell = report.get("headerCell");
						        headerCell.set("headerValue", "Reporter");
						        headerCell.set("headerColspan", "1");
					        report.append("headerCell", headerCell);
					    	//sb.append("<tr><Td id=\"header\">Gene</td><td id=\"header\">Reporter</td>");
						   
					    	ArrayList cssLabels = new ArrayList();
					    	
					    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
					        	label = (String) labelIterator.next();
					        	
					        	if(!csv)	{
					        		//create a new cell with colspan for every group/label and append to the row
					        		headerCell = report.get("headerCell");
						        		headerCell.set("headerColspan", "1" );
						        		headerCell.set("headerValue", label+" Samples");
					        		report.append("headerCell", headerCell);
					        	}
					        	
					        	//sb.append("<Td id=\"header\" class=\""+label+"\">"+label+"</td>");
					        	cssLabels.add(label);
					    	}
					    	
							//sb.append("</tr>\n");
							
					    	if(!csv)	{
						    	//	generate the CSS on the fly
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
					    	}
					    	else	{
					    		report.set("css", ""); //or dont include {css} in csv template
					    	}
					    	
					    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
					    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();

					    		Collection reporters = geneResultset.getReporterResultsets();
					    		
					    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
					    			recordCount += reporters.size();
					    			
					        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
					        		Collection groupTypes = reporterResultset.getGroupByResultsets();

					        		String reporterName = "-";
					        		try	{
					        			reporterName = reporterResultset.getReporter().getValue().toString();
					        		}
					        		catch(Exception e)	{
					        			reporterName = "-";
					        		}
					        		
						    		GeneSymbol gene = geneResultset.getGeneSymbol();
					        		String geneSymbol = "&nbsp;";
					        		if( gene != null){
					        			try{
					        				geneSymbol = geneResultset.getGeneSymbol().getValueObject().toString();
					        			}
					        			catch(Exception e){
					        				geneSymbol = " - ";
					        			}
					        			//logger.debug("Gene Symbol: "+ geneSymbol);
					        		}
					        		
					        		//start the new row
					        		row = report.get("row");
					        		
					        		cell = row.get("cell");
						        		cell.set("value", geneSymbol);
						        		cell.set("class", geneSymbol);
					        		row.append("cell", cell);
					        		cell = row.get("cell");
						        		cell.set("value", reporterName);
						        		cell.set("class", reporterName);
					        		row.append("cell", cell);
					        		//sb.append("<tr><td>"+geneSymbol+"</td><td>" + reporterName + "</td>");
					        		      		
					        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
					        			sb = new StringBuffer();
					    	        	label = (String) labelIterator.next();
					    	        	DiseaseGroupResultset diseaseResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(label);
					    	        	if(diseaseResultset != null){
					    	        		cell = row.get("cell");
							        			cell.set("class", label);
						 
					    	        		//sb.append("<td class=\""+label+"\">");
					    	        		try	{
					    	        			Double ratio = (Double)diseaseResultset.getFoldChangeRatioValue().getValue();
					    	        			sb.append(resultFormat.format(ratio));
					    	        		}
					    	        		catch(Exception e)	{
					    	        			sb.append("-&nbsp;");
					    	        		}
					    	        		try	{
					    	        			Double pvalue = (Double)diseaseResultset.getRatioPval().getValue();
					    	        			sb.append(" ("+resultFormat.format(pvalue) + ")");
					    	        		}
					    	        		catch(Exception e){
					    	        			sb.append("&nbsp;");
					    	        		}
					    	        		cell.set("value", sb.toString());
					    	        		//sb.append("</td>");
				                   		}
				                   		else	{
				                   			cell = row.get("cell");
				                   			cell.set("class", label);
				                   			cell.set("value", "-");
				                   			//sb.append("<Td class=\""+label+"\">-</td>");
				                   		}
					    	    	}
					        		row.append("cell", cell);
		   	                   		//sb.append("</tr>");
					    		}
					    		
					    		//append the extra row here
					        	//is !csv for loop thru and append a new blank td foreach theColspan
					        	if(!csv)	{
					        		row = report.get("row");
					        		for(int i=0; i<(labels.size() + 2); i++)	{
					        			cell = row.get("cell");
					        				cell.set("value", "&nbsp;");
					        				cell.set("class", "geneSpacerStyle");
					        			row.append("cell", cell);
					        		}
					        		report.append("row", row);
					        	}
					        	
					    		// add the line between genes
					    		//sb.append("<tr><td colspan=\""+(labels.size() + 2)+"\" class=\"geneSpacerStyle\">&nbsp;</td></tr>\n");
							    
					    	}
						//sb.append("</table>\n\n");
					}
					else	{
						sb.append("<Br><br>Gene Disease View container is empty");
						//TODO: something fancy here w/errors
					}
		
					//return "<div class=\"rowCount\">"+helpFul +recordCount+" records returned &nbsp;&nbsp;&nbsp;" + links + "</div>\n" + css.toString() + sb.toString();
		    
		    return report;
		}
		catch(Exception e)	{
			return null;
		}
	}

}
