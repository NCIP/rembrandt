package gov.nih.nci.nautilus.ui.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import gov.nih.nci.nautilus.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.nautilus.resultset.DimensionalViewContainer;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.DiseaseGroupResultset;
import gov.nih.nci.nautilus.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.nautilus.resultset.gene.GeneResultset;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Landyr
 * Feb 8, 2005
 * 
 */
public class GeneExprDiseaseReport implements ReportGenerator{

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
	public Document getReportXML(Resultant resultant, Map filterMapParams) {
		// TODO Auto-generated method stub
		
		//	have setter or put in props file
		String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };
		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		
		Document document = DocumentHelper.createDocument();

			Element report = document.addElement( "Report" );
			Element cell = null;
			Element data = null;
			Element dataRow = null;
			//add the atts
	        report.addAttribute("reportType", "Gene Expression Disease");
	        //fudge these for now
	        report.addAttribute("groupBy", "none");
	        String queryName = resultant.getAssociatedQuery().getQueryName();
	        //set the queryName to be unique for session/cache access
	        report.addAttribute("queryName", queryName);
	        report.addAttribute("sessionId", "the session id");
	        report.addAttribute("creationTime", "right now");
		    
		    ResultsContainer  resultsContainer = resultant.getResultsContainer();
		    
		    StringBuffer sb = new StringBuffer();
			
			//String helpFul = helpLink + "?sect=diseaseGroup" + helpLinkClose;
			
			GeneExprResultsContainer geneExprDiseaseContainer = (GeneExprResultsContainer) resultsContainer;
/*			
			if(resultsContainer instanceof DimensionalViewContainer)	{
				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
				if(dimensionalViewContainer != null)	{
					geneExprDiseaseContainer = dimensionalViewContainer.;
				}
			}
			else if(resultsContainer instanceof CopyNumberSingleViewResultsContainer)	{ //for single
				geneExprDiseaseContainer = (CopyNumberSingleViewResultsContainer) resultsContainer;
			}
*/			
			
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
							Element headerRow = report.addElement("Row").addAttribute("name", "headerRow");
					        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
						        data = cell.addElement("Data").addAttribute("type", "header").addText("Gene");
						        data = null;
					        cell = null;
					        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
						        data = cell.addElement("Data").addAttribute("type", "header").addText("Reporter");
						        data = null;
					        cell = null;
					    	//sb.append("<tr><Td id=\"header\">Gene</td><td id=\"header\">Reporter</td>");
						   
					    	
					    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
					        	label = (String) labelIterator.next();
					        	
								cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", label).addAttribute("group", label);
					        		data = cell.addElement("Data").addAttribute("type", "header").addText(label);
						        	data = null;
						        cell = null;
					     
					        	//sb.append("<Td id=\"header\" class=\""+label+"\">"+label+"</td>");

					    	}
					    	
							//sb.append("</tr>\n");
					    	
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
					        		String geneSymbol = "-";
					        		if( gene != null){
					        			try{
					        				geneSymbol = geneResultset.getGeneSymbol().getValueObject().toString();
					        			}
					        			catch(Exception e){
					        				geneSymbol = " - ";
					        			}
					        			//logger.debug("Gene Symbol: "+ geneSymbol);
					        		}
					        		
					        		//start the new data row
									dataRow = report.addElement("Row").addAttribute("name", "dataRow");
							        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "header").addAttribute("group", "header");
							        	data = cell.addElement("Data").addAttribute("type", "header").addText(geneSymbol);
							        	data = null;
							        cell = null;
									cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "header").addAttribute("group", "header");
							        	data = cell.addElement("Data").addAttribute("type", "header").addText(reporterName);
							        	data = null;
							        cell = null;
					        		//sb.append("<tr><td>"+geneSymbol+"</td><td>" + reporterName + "</td>");
					        		      		
					        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
					        			sb = new StringBuffer();
					    	        	label = (String) labelIterator.next();
					    	        	DiseaseGroupResultset diseaseResultset = (DiseaseGroupResultset) reporterResultset.getGroupByResultset(label);
					    	        	if(diseaseResultset != null){
					    	        		
											cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", label).addAttribute("group", label);
				    					        
					    	        		//sb.append("<td class=\""+label+"\">");
					    	        		try	{
					    	        			Double ratio = (Double)diseaseResultset.getFoldChangeRatioValue().getValue();
					    	        			sb.append(resultFormat.format(ratio));
					    	        		}
					    	        		catch(Exception e)	{
					    	        			sb.append("-");
					    	        		}
					    	        		try	{
					    	        			Double pvalue = (Double)diseaseResultset.getRatioPval().getValue();
					    	        			sb.append(" ("+resultFormat.format(pvalue) + ")");
					    	        		}
					    	        		catch(Exception e){
					    	        			sb.append("-");
					    	        		}
												data = cell.addElement("Data").addAttribute("type", "data").addText(sb.toString());
				    					        data = null;
				    					    cell = null;
					    	        		//sb.append("</td>");
				                   		}
				                   		else	{
				                   			cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", label).addAttribute("group", label);
				    					    	data = cell.addElement("Data").addAttribute("type", "data").addText("-");
				    					        data = null;
				    					    cell = null;
				                   			//sb.append("<Td class=\""+label+"\">-</td>");
				                   		}
					    	    	}
		   	                   		//sb.append("</tr>");
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
		    
		    return document;
	}

}
