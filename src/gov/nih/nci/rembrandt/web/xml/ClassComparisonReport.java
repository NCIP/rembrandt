package gov.nih.nci.rembrandt.web.xml;

import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResult;
import gov.nih.nci.caintegrator.analysis.messaging.ClassComparisonResultEntry;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.caintegrator.service.findings.ClassComparisonFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.annotation.GeneExprAnnotationService;
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
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author LandyR
 * Feb 8, 2005
 * 
 */
public class ClassComparisonReport{

	/**
	 * 
	 */
	public ClassComparisonReport() {
		//super();
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.ui.report.ReportGenerator#getTemplate(gov.nih.nci.nautilus.resultset.Resultant, java.lang.String)
	 */
	
	public static Document getReportXML(Finding finding, Map filterMapParams) {

		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		
		
		/*
		 *  this is for filtering, we will want a p-value filter for CC
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
			
		String defaultV = "--";
		String delim = " | ";
		
		Document document = DocumentHelper.createDocument();

			Element report = document.addElement( "Report" );
			Element cell = null;
			Element data = null;
			Element dataRow = null;
			//add the atts
	        report.addAttribute("reportType", "Class Comparison");
	        //fudge these for now
	        report.addAttribute("groupBy", "none");
	        String queryName = "none";
	        try	{
	        	queryName = finding.getQueryDTO().getQueryName();
	        }
	        catch (Exception e) {
				// TODO: handle exception
	        	queryName = finding.getTaskId();
			}
	        //set the queryName to be unique for session/cache access
	        report.addAttribute("queryName", queryName);
	        report.addAttribute("sessionId", "the session id");
	        report.addAttribute("creationTime", "right now");
		    
			StringBuffer sb = new StringBuffer();
			
			int recordCount = 0;
			int totalSamples = 0;
			
			//TODO: instance of
			ClassComparisonFinding ccf = (ClassComparisonFinding) finding;
			
			if(ccf != null)	{
					
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
		        	String isAdj = ccf.arePvaluesAdjusted() ? " (Adjusted) " : "";
			        data = cell.addElement("Data").addAttribute("type", "header").addText("P-Value"+isAdj);
			        data = null;
		        cell = null;

			    cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Fold Change");
			        data = null;
		        cell = null;	
		        
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Gene Symbol");
			        data = null;
		        cell = null;
		        
		        //starting annotations...leave these here for now, as we may want them
		        
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("GenBank Acc");
			        data = null;
		        cell = null;
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "csv").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Locus link");
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
		        
		        
		    	/* done with the headerRow and SampleRow Elements, time to add data rows */
					
		        List<ClassComparisonResultEntry> classComparisonResultEntrys = ccf.getResultEntries();
				List<String> reporterIds = new ArrayList<String>();
				Map<String,ReporterResultset> reporterResultsetMap = null;
				for (ClassComparisonResultEntry classComparisonResultEntry: classComparisonResultEntrys){
					if(classComparisonResultEntry.getReporterId() != null){
						reporterIds.add(classComparisonResultEntry.getReporterId());
					}
				}
		        try {
		        	reporterResultsetMap = GeneExprAnnotationService.getAnnotationsMapForReporters(reporterIds);
		        }
		        catch(Exception e){}
		        
		        for(ClassComparisonResultEntry ccre : ccf.getResultEntries())	{

		        	dataRow = report.addElement("Row").addAttribute("name", "dataRow");
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "reporter").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(ccre.getReporterId());
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(resultFormat.format(ccre.getMeanGrp1()) + " / " + resultFormat.format(ccre.getMeanGrp2()));
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
			        	//String pv = (ccre.getPvalue() == null) ? String.valueOf(ccre.getPvalue()) : "N/A";
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(String.valueOf(resultFormat.format(ccre.getPvalue())));
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(String.valueOf(resultFormat.format(ccre.getFoldChange())));
			        	data = null;
			        cell = null;
			        
			        //get the gene symbols for this reporter
			        //ccre.getReporterId()
			        String genes = defaultV;
			        
			        //start annotations
			        String accIds = defaultV;
			        String llink = defaultV;
			        String go = defaultV;
			        String pw = defaultV;
			          
						if(reporterResultsetMap != null  && reporterIds != null){
							//int count = 0;
							String reporterId = ccre.getReporterId();
							ReporterResultset reporterResultset = reporterResultsetMap.get(reporterId);
							Collection<String> geneSymbols = (Collection<String>)reporterResultset.getAssiciatedGeneSymbols();
							if(geneSymbols != null){
								genes = StringUtils.join(geneSymbols.toArray(), delim);
							}
							Collection<String> genBank_AccIDS = (Collection<String>)reporterResultset.getAssiciatedGenBankAccessionNos();
							if(genBank_AccIDS != null){
								accIds = StringUtils.join(genBank_AccIDS.toArray(), delim);
							}
							Collection<String> locusLinkIDs = (Collection<String>)reporterResultset.getAssiciatedLocusLinkIDs();
							if(locusLinkIDs != null){
								llink = StringUtils.join(locusLinkIDs.toArray(), delim);
							}
							Collection<String> goIds = (Collection<String>)reporterResultset.getAssociatedGOIds();
							if(goIds != null){
								go = StringUtils.join(goIds.toArray(), delim);
							}
							Collection<String> pathways = (Collection<String>)reporterResultset.getAssociatedPathways();
							if(pathways != null){
								pw = StringUtils.join(pathways.toArray(), delim);
							}
						}

					cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(genes);
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(accIds);
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(llink);
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(go);
			        	data = null;
			        cell = null;
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "csv").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(pw);
			        	data = null;
			        cell = null;
			        
		        }
 
			}
			else {
				//TODO: handle this error
				sb.append("<br><Br>Class Comparison is empty<br>");
			}
		    
		    return document;
	}

}

