package gov.nih.nci.nautilus.ui.report;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.nautilus.resultset.DimensionalViewContainer;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.sample.SampleResultset;
import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;

import org.javaby.jbyte.Template;

/**
 * @author LandyR
 * Feb 8, 2005
 * 
 */
public class ClinicalSampleReport implements ReportGenerator {

	/**
	 * 
	 */
	public ClinicalSampleReport () {
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
		    //Template cssTemplate = null;
		    //	is this a CSV report
		    boolean csv = false;
		    
		    //are we doing a csv (assuming that the string "csv" is in the csv template name
		    if(skin.indexOf("csv") != -1)
		    	csv = true;
		    else
		    	csv = false;
		    
		    //	there is no CSS for this report, so put an empty one in
		    report.set("css", "");
		    
		    
		    /*********** orig *************/
		    boolean gLinks = false;
			boolean cLinks = false;
			StringBuffer sb = new StringBuffer();
			
			//String helpFul = helpLink + "?sect=clinical" + helpLinkClose;
	
			//logger.debug("HERE IS THE CLINICAL VIEW");
			ResultsContainer  resultsContainer = resultant.getResultsContainer();
			SampleViewResultsContainer sampleViewContainer = null;
			if(resultsContainer instanceof DimensionalViewContainer){
				
				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
						// Are we making hyperlinks?
						if(dimensionalViewContainer.getGeneExprSingleViewContainer() != null)	{
							// show the geneExprHyperlinks
							gLinks = true;						
						}
						if(dimensionalViewContainer.getCopyNumberSingleViewContainer() != null)	{
							// show the copyNumberHyperlinks
							cLinks = true;
						}
				/*		
				// RCL : i cant remember what this does, so commenting out		
				//RCL: add the DimVC to the session once here, actually the same thing		
				request.getSession().setAttribute("_dv", dimensionalViewContainer);
				//request.getSession().setAttribute("_gene", dimensionalViewContainer);
				*/
				sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
				
			}else if (resultsContainer instanceof SampleViewResultsContainer){
				
				sampleViewContainer = (SampleViewResultsContainer) resultsContainer;
				
			}
			
			Collection samples = sampleViewContainer.getBioSpecimenResultsets();
			/*
			sb.append("<div class=\"rowCount\">"+helpFul+samples.size()+" records returned &nbsp;&nbsp;&nbsp;" + links + "</div>\n");
			sb.append("<table cellpadding=\"0\" cellspacing=\"0\">\n");
			*/
			
			//	set up the headers for this table
			headerCell = report.get("headerCell");
		        headerCell.set("headerValue", "SAMPLE");
		        headerCell.set("headerColspan", "1");
	        report.append("headerCell", headerCell);
			headerCell = report.get("headerCell");
		        headerCell.set("headerValue", "AGE at Dx (years)");
		        headerCell.set("headerColspan", "1");
	        report.append("headerCell", headerCell);			
	        headerCell = report.get("headerCell");
		        headerCell.set("headerValue", "GENDER");
		        headerCell.set("headerColspan", "1");
		    report.append("headerCell", headerCell);	        
			headerCell = report.get("headerCell");
		        headerCell.set("headerValue", "SURVIVAL (months)");
		        headerCell.set("headerColspan", "1");
	        report.append("headerCell", headerCell);			
	        headerCell = report.get("headerCell");
		        headerCell.set("headerValue", "DISEASE");
		        headerCell.set("headerColspan", "1");
		    report.append("headerCell", headerCell);	        
			
		    //sb.append("<Tr><Td id=\"header\">SAMPLE</td><td id=\"header\">AGE at Dx (years)</td><td id=\"header\">GENDER</td><td id=\"header\">SURVIVAL (months)</td><td id=\"header\">DISEASE</td>");
 		   	
		    //	we will not have a sample row, so insert a blank one
		    //	or use a different template (suggested)
		    sampleCell = report.get("sampleCell");
		        sampleCell.set("sampleValue", "");
		        sampleCell.set("sampleColspan", "10");
		    report.append("sampleCell", sampleCell);
		    
			Iterator si = samples.iterator(); 
			if(si.hasNext())	{
				SampleResultset sampleResultset =  (SampleResultset)si.next();
   				if(sampleResultset.getGeneExprSingleViewResultsContainer() != null)	{
   					headerCell = report.get("headerCell");
	   			        headerCell.set("headerValue", "GeneExp");
	   			        headerCell.set("headerColspan", "1");
	   		        report.append("headerCell", headerCell);
   					//sb.append("<Td id=\"header\">GeneExp</td>");
   				}
   	 		   	if(sampleResultset.getCopyNumberSingleViewResultsContainer()!= null)	{
	   	 		   	headerCell = report.get("headerCell");
				        headerCell.set("headerValue", "CopyNumber");
				        headerCell.set("headerColspan", "1");
			        report.append("headerCell", headerCell);
   	 		   		//sb.append("<td id=\"header\">CopyNumber</td>");
   	 		   	}
   	 		   	//sb.append("</tr>\n");
			}
			
   			for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {

   				SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
   				
   	   			String sampleName = sampleResultset.getBiospecimen().getValue().toString();
   	   			row = report.get("row");
   	   			cell = row.get("cell");
	   	   			cell.set("value", sampleResultset.getBiospecimen().getValue().toString().substring(2));
	   	   			cell.set("class", "");
	   	   		row.append("cell", cell);
		   	   	cell = row.get("cell");
		   			cell.set("value", sampleResultset.getAgeGroup().getValue());
		   			cell.set("class", "");
		   		row.append("cell", cell);
		   		cell = row.get("cell");
		   			cell.set("value", sampleResultset.getGenderCode().getValue());
		   			cell.set("class", "");
		   		row.append("cell", cell);
		   		cell = row.get("cell");
		   			cell.set("value", sampleResultset.getSurvivalLengthRange().getValue());
		   			cell.set("class", "");
		   		row.append("cell", cell);
		   		cell = row.get("cell");
		   			cell.set("value", sampleResultset.getDisease().getValue());
		   			cell.set("class", "");
		   		row.append("cell", cell);
		   		/*
   	   			sb.append("<tr><td>"+sampleResultset.getBiospecimen().getValue().toString().substring(2)+ "</td>" +
   					"<Td>"+sampleResultset.getAgeGroup().getValue()+ "</td>" +
					"<td>"+sampleResultset.getGenderCode().getValue()+ "</td>" +
					"<td>"+sampleResultset.getSurvivalLengthRange().getValue()+ "</td>" +
					"<Td>"+sampleResultset.getDisease().getValue() + "</td>");
				*/
	   			if(sampleResultset.getGeneExprSingleViewResultsContainer() != null)	{
	   				//TODO: create the links
	   				cell = row.get("cell");
		   				cell.set("value", "G");
		   				cell.set("class", "");
		   			row.append("cell", cell);
	   				//sb.append("<td><a href=\"report.do?s="+sampleName+"_gene&report=gene\">G</a></td>");
	   			}
		   		else if (gLinks){
	   				cell = row.get("cell");
		   				cell.set("value", "");
		   				cell.set("class", "");
		   			row.append("cell", cell);
		   			//sb.append("<td>&nbsp;</td>"); //empty cell
		   		}
	   			if(sampleResultset.getCopyNumberSingleViewResultsContainer()!= null)	{
	   				//	TODO: create the links
	   				cell = row.get("cell");
		   				cell.set("value", "C");
		   				cell.set("class", "");
		   			row.append("cell", cell);
	   				//sb.append("<Td><a href=\"report.do?s="+sampleName +"_copy&report=copy\">C</a></td>");
	   			}
	   			else if (cLinks){
	   				cell = row.get("cell");
		   				cell.set("value", "");
		   				cell.set("class", "");
		   			row.append("cell", cell);
		   			//sb.append("<td>&nbsp;</td>"); //empty cell
		   		}
	   			
	   			report.append("row", row);
	   			//sb.append("</tr>\n");
    		}
    		//sb.append("</table>\n<br>");
    		//return sb.toString(); 
		    return report;
		    
		   /********* end orig *************/
		     
		}
		catch(Exception e){
			//cant get template
			return null;
		}

	}

}
