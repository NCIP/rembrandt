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

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.javaby.jbyte.Template;

/**
 * @author LandyR
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
	public Document getReportXML(Resultant resultant) {

		//	TODO: have setter or put in props file
		String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };
		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		
		
		Document document = DocumentHelper.createDocument();

			Element report = document.addElement( "Report" );
			Element cell = null;
			Element data = null;
			Element dataRow = null;
			//add the atts
	        report.addAttribute("reportType", "Copy Number");
	        //fudge these for now
	        report.addAttribute("groupBy", "none");
	        report.addAttribute("queryName", "the query name");
	        report.addAttribute("sessionId", "the session id");
	        report.addAttribute("creationTime", "right now");
		    
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
		    	
				
				Element headerRow = report.addElement("Row").addAttribute("name", "headerRow");
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("data").addAttribute("type", "header").addText("Gene");
				        data = null;
			        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("data").addAttribute("type", "header").addText("Reporter");
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
					
		        //set up the header for the table	        
		    	//header.append("<Td id=\"header\">Gene</td>\n<td id=\"header\">Reporter</td>\n");        
		    	//sampleNames.append("<tr><Td> &nbsp;</td><Td> &nbsp;</tD>"); 

		    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
		        	String label = (String) labelIterator.next();
		        	sampleIds = geneViewContainer.getBiospecimenLabels(label);    	
//			    	theColspan += sampleIds.size();
			    	totalSamples += sampleIds.size();

			    	cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", label).addAttribute("group", label);
				        		data = cell.addElement("Data").addAttribute("type", "header").addText(label+" Samples");
					        	data = null;
					        cell = null;
		        	//header.append("<td colspan="+sampleIds.size()+" class='"+label+"' id=\"header\">"+label+" Samples</td>"); 
			    	
			           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {

			            	String s = sampleIdIterator.next().toString();
							cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", label).addAttribute("group", label);
						        data = cell.addElement("Data").addAttribute("type", "header").addText(s.substring(2));
						    	data = null;
						    cell = null;
			            	//sampleNames.append("<td class='"+label+"' id=\"header\"><a href=\"report.do?s="+s+"&report=ss\">"+s.substring(2)+"</a></td>"); 
			            	//header.append("\t");
			 
			           	}
		           	//header.deleteCharAt(header.lastIndexOf("\t"));
		    	}
		    	//sampleNames.append("</tr>");
		    	//header.append("</tr>"); 
		    	
		    	/* done with the headerRow and SampleRow Elements, time to add data rows */
					
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
		        		
		        		dataRow = report.addElement("Row").addAttribute("name", "dataRow");
					        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "header").addAttribute("group", "header");
					        	data = cell.addElement("Data").addAttribute("type", "header").addText(geneSymbol);
					        	data = null;
					        cell = null;
					        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "header").addAttribute("group", "header");
					        	data = cell.addElement("Data").addAttribute("type", "header").addText(reporterName);
					        	data = null;
					        cell = null;
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
			                       				cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", label).addAttribute("group", label);
				    					        	data = cell.addElement("Data").addAttribute("type", "data").addText(resultFormat.format(ratio));
				    					        	data = null;
				    					        cell = null;
			                       			
				                       			//sb.append("<Td class='"+label+"'>"+resultFormat.format(ratio)+" </td>");
			                       			}
				                       		else	{
				                       			cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", label).addAttribute("group", label);
				    					        	data = cell.addElement("Data").addAttribute("type", "data").addText("-");
				    					        	data = null;
				    					        cell = null;
				                      			//sb.append("<td class='"+label+"'>-</td>");
				                       		}
			                       		}
			                       		else	{
			                       				cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", label).addAttribute("group", label);
				    					        	data = cell.addElement("Data").addAttribute("type", "data").addText("-");
				    					        	data = null;
				    					        cell = null;
			                       			//sb.append("<td class='"+label+"'>-</td>");
			                       		}
			                       	}
		                       }
		                       else	{
		                       	for(int s=0;s<sampleIds.size();s++)	{
		                       		cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", label).addAttribute("group", label);
				    					data = cell.addElement("Data").addAttribute("type", "data").addText("-");
				    					data = null;
				    				cell = null;
		                       		//sb.append("<td class='"+label+"'>-</td>");
		                       	}
		                       }
		
		         		}
		         		
		        		//sb.append("</tr>\n");
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
 
		    return document;
	}

}
