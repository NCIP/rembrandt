package gov.nih.nci.rembrandt.web.xml;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;
import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.rembrandt.dto.finding.ClassComparisonFindingsResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.SampleFoldChangeValuesResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ViewByGroupResultset;
import gov.nih.nci.rembrandt.web.helper.FilterHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author LandyR
 * Feb 8, 2005
 * 
 */
public class ClassComparisonReport implements ReportGenerator{

	/**
	 * 
	 */
	public ClassComparisonReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.ui.report.ReportGenerator#getTemplate(gov.nih.nci.nautilus.resultset.Resultant, java.lang.String)
	 */
	
	public Document getReportXML(Resultant resultant, Map filterMapParams) {

		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		
		/* testing hardcoded vals - these will be params of this method soon */
		/*
		ArrayList g = new ArrayList();
		g.add("EGFR");
		g.add("VEGF");
		String tmp_filter_type = "hide";
		String tmp_filter_element = "gene";

		HashMap filterMapParams = new HashMap();
		filterMapParams.put("filter_string", g);
		filterMapParams.put("filter_type", tmp_filter_type);
		filterMapParams.put("filter_element", tmp_filter_element);
		*/
		
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
	        report.addAttribute("reportType", "Class Comparison");
	        //fudge these for now
	        report.addAttribute("groupBy", "none");
	        String queryName = resultant.getAssociatedQuery().getQueryName();
	        //set the queryName to be unique for session/cache access
	        report.addAttribute("queryName", queryName);
	        report.addAttribute("sessionId", "the session id");
	        report.addAttribute("creationTime", "right now");
		    
		    //ResultsContainer  resultsContainer = resultant.getResultsContainer();
		    
			//GeneExprSingleViewResultsContainer geneViewContainer = null;
			StringBuffer sb = new StringBuffer();
			
			//String helpFul = helpLink + "?sect=sample" + helpLinkClose;
			
			//DimensionalViewContainer dimensionalViewContainer = null;
			int recordCount = 0;
			int totalSamples = 0;
			/*
			if(resultsContainer instanceof DimensionalViewContainer)	{
				dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
				if(dimensionalViewContainer != null)	{
					geneViewContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
				}
			}
			else if(resultsContainer instanceof GeneExprSingleViewResultsContainer)	{ //for single
				geneViewContainer = (GeneExprSingleViewResultsContainer) resultsContainer;
			}
			*/
			
			ClassComparisonFindingsResultset ccfr = (ClassComparisonFindingsResultset) resultant.getResultsContainer();
			//instance of
	        
			if(ccfr != null)	{
		    	//Collection genes = geneViewContainer.getGeneResultsets();
		    	//Collection labels = geneViewContainer.getGroupsLabels();
		    	//Collection sampleIds = null;
					
				Element headerRow = report.addElement("Row").addAttribute("name", "headerRow");
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Reporter");
			        data = null;
		        cell = null;
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Group Avg");
			        data = null;
		        cell = null;
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("P-Value");
			        data = null;
		        cell = null;
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Adj. P-Value");
			        data = null;
		        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Fold Change (log)");
			    data = null;
		        cell = null;			        
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Fold Change (abs)");
			        data = null;
		        cell = null;
			        
		        //starting annotations
		        /*
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Locus link");
			        data = null;
		        cell = null;
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("GenBank Acc");
			        data = null;
		        cell = null;

		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("GO Id");
			        data = null;
		        cell = null;
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Pathways");
			        data = null;
		        cell = null;
		        */
	        	/*
	         	//wtf? to force colspans?
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
		        cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
		        	data = cell.addElement("Data").addAttribute("type", "header").addText(" ");
		        	data = null;
		        cell = null;
		        cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
		        	data = cell.addElement("Data").addAttribute("type", "header").addText(" ");
		        	data = null;
		        cell = null;
				*/
			        
		        //set up the header for the table	        
		    	//header.append("<Td id=\"header\">Gene</td>\n<td id=\"header\">Reporter</td>\n");        
		    	//sampleNames.append("<tr><Td> &nbsp;</td><Td> &nbsp;</tD>"); 

		        /*
 				//build the sample row TD's
		    	for(Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
		        	String label = (String) labelIterator.next();
		        	sampleIds = geneViewContainer.getBiospecimenLabels(label);    	
		        	//theColspan += sampleIds.size();
			    	totalSamples += sampleIds.size();

			    	cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", label).addAttribute("group", label);
		        		data = cell.addElement("Data").addAttribute("type", "header").addText(label+" Samples");
			        	data = null;
			        cell = null;
		        	//header.append("<td colspan="+sampleIds.size()+" class='"+label+"' id=\"header\">"+label+" Samples</td>"); 
			    	
			           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {

			            	String s = sampleIdIterator.next().toString();
							cell = sampleRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", label).addAttribute("group", label);
						        //data = cell.addElement("Data").addAttribute("type", "header").addText(s.substring(2));
						        data = cell.addElement("Data").addAttribute("type", "header").addText(s);
						    	data = null;
						    cell = null;
			            	//sampleNames.append("<td class='"+label+"' id=\"header\"><a href=\"report.do?s="+s+"&report=ss\">"+s.substring(2)+"</a></td>"); 
			            	//header.append("\t");
			 
			           	}
		           	//header.deleteCharAt(header.lastIndexOf("\t"));
		    	}
		    	//sampleNames.append("</tr>");
		    	//header.append("</tr>"); 
		    	*/
			        
		    	/* done with the headerRow and SampleRow Elements, time to add data rows */
				
		        //get the CCResult, get the CC.getResultEntries, foreach add the cells
		        //this is a bogus ccr, should get this as a param (Resultant)
		        //ClassComparisonResult ccr = new ClassComparisonResult("test","test");
		        
		        //ClassComparisonFindingsResultset ccfr = (ClassComparisonFindingsResultset) resultant.getResultsContainer();
		        
		        for(ClassComparisonResultEntry ccre : ccfr.getResultEntries())	{
		        	dataRow = report.addElement("Row").addAttribute("name", "dataRow");
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(ccre.getReporterId());
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "reporter").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(ccre.getMeanGrp1() + " / " + ccre.getMeanGrp2());
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(String.valueOf(ccre.getPvalue()));
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(String.valueOf(ccre.getFoldChange()));
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(String.valueOf(ccre.getFoldChange()));
			        	data = null;
			        cell = null;
		        }

		        //TODO:  put the annotations in...make an annotations lookup?
 
			}
			else {
				//TODO: handle this error
				sb.append("<br><Br>Gene Container is empty<br>");
			}
		    

		    return document;
	}

}

