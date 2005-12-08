package gov.nih.nci.rembrandt.web.taglib;

import gov.nih.nci.caintegrator.enumeration.ClusterByType;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.queryservice.resultset.annotation.GeneExprAnnotationService;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class HCPlotReport extends TagSupport {
	
	private String taskId = "";
	//private Logger logger = Logger.getLogger(HCPlotReport.class);
	private static Logger logger = Logger.getLogger(HCPlotReport.class);	
	
	private PresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	private BusinessTierCache businessTierCache = ApplicationFactory.getBusinessTierCache();
	
	private String dv = "--";
	
	public enum ClusterBy { Genes, Reporters }
	
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			HttpSession session = pageContext.getSession();
			StringBuffer xhtml = new StringBuffer();
			if(taskId != null)	{
				
				HCAFinding hcaFinding = (HCAFinding)businessTierCache.getSessionFinding(session.getId(),taskId);
	            List<String> clusterByIds = new ArrayList();
	            //ok, what did we cluster by?...can only be 1
	            if(hcaFinding.getClusteredReporterIDs()!=null && hcaFinding.getClusteredReporterIDs().size() > 0)	{
	            	clusterByIds = (List) hcaFinding.getClusteredReporterIDs();
	            	xhtml.append(quickReporterReport(clusterByIds));
	            }
	            else if(hcaFinding.getClusteredSampleIDs()!=null && hcaFinding.getClusteredSampleIDs().size() > 0)	{
	            	clusterByIds = (List) hcaFinding.getClusteredSampleIDs();
	            	xhtml.append(quickSampleReport(clusterByIds));
	            }
	            
				out.println(xhtml.toString());
			}
			else	{
				out.println("No Report Available.");
			}
		} 
		catch (Exception ex) {
			throw new Error("All is not well in the world.");
		}
		// Must return SKIP_BODY because we are not supporting a body for this tag.
		return SKIP_BODY;
	}
	public int doEndTag(){
		try {
			//JspWriter out = pageContext.getOut();
			//out.println("</table>");
		} 
		catch (Exception ex){
			//throw new Error("All is not well in the world.");
		}
		return EVAL_PAGE;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public StringBuffer quickReporterReport(List<String> reporters){
		StringBuffer html = new StringBuffer();
		Document document = DocumentHelper.createDocument();
		Element table = document.addElement("table").addAttribute("id", "reportTable").addAttribute("class", "report");
		Element tr = null;
		Element td = null;
		
		tr = table.addElement("tr").addAttribute("class", "header");
		td = tr.addElement("td").addAttribute("class", "header").addText("Reporter");
		td = tr.addElement("td").addAttribute("class", "header").addText("Gene Symbol");
		td = tr.addElement("td").addAttribute("class", "header").addText("GenBank AccId");
		td = tr.addElement("td").addAttribute("class", "header").addText("LocusLink");
		
		if(reporters != null)	{
			try {
				List<ReporterResultset> reporterResultsets = GeneExprAnnotationService.getAnnotationsForReporters(reporters);
				for(ReporterResultset reporterResultset: reporterResultsets){
					if(reporterResultset != null){
						
						tr = table.addElement("tr").addAttribute("class", "data");
						
						String reporter = reporterResultset.getReporter()!=null ? reporterResultset.getReporter().getValue().toString() : "N/A";
						td = tr.addElement("td").addText(reporter);
						//html.append("ReporterID :" +reporterResultset.getReporter().getValue().toString() + "<br/>");
						Collection<String> geneSymbols = (Collection<String>)reporterResultset.getAssiciatedGeneSymbols();
						String genes = "";
						if(geneSymbols != null){
							genes = StringUtils.join(geneSymbols.toArray(), ",");
						}
						
						td = tr.addElement("td").addText(genes);
						
						Collection<String> genBank_AccIDS = (Collection<String>)reporterResultset.getAssiciatedGenBankAccessionNos();
						String accs = "";
						if(genBank_AccIDS != null){
							accs = StringUtils.join(genBank_AccIDS.toArray(), ",");
						}
						
						td = tr.addElement("td").addText(accs);
						
						Collection<String> locusLinkIDs = (Collection<String>)reporterResultset.getAssiciatedLocusLinkIDs();
						String ll = "";
						if(locusLinkIDs != null){
							ll = StringUtils.join(locusLinkIDs.toArray(), ",");
						}
						
						td = tr.addElement("td").addText(ll);
						
						/*
						Collection<String> goIds = (Collection<String>)reporterResultset.getAssociatedGOIds();
						if(goIds != null){
							for(String goId: goIds){
								html.append("Associtaed GO Id :" +goId + "<br/>");
							}
						}
						Collection<String> pathways = (Collection<String>)reporterResultset.getAssociatedPathways();
						if(pathways != null){
							for(String pathway: pathways){
								html.append("Associated Pathway :" +pathway + "<br/>");
							}
						}
						*/
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//return html;
		return html.append(document.asXML());
	}
	public StringBuffer quickSampleReport(List<String> sampleIds){
		StringBuffer html = new StringBuffer();
		Document document = DocumentHelper.createDocument();
		
		if(sampleIds != null)	{
			Map<String, SampleResultset> sampleResultsetMap;
			try {
				sampleResultsetMap = ClinicalDataValidator.getClinicalAnnotationsMapForSamples(sampleIds);
				if(sampleResultsetMap != null  && sampleIds != null){
					int count = 0;

					Element table = document.addElement("table").addAttribute("id", "reportTable").addAttribute("class", "report");
					Element tr = null;
					Element td = null;
					tr = table.addElement("tr").addAttribute("class", "header");
					td = tr.addElement("td").addAttribute("class", "header").addText("Sample ID");
					td = tr.addElement("td").addAttribute("class", "header").addText("Disease");
					td = tr.addElement("td").addAttribute("class", "header").addText("Gender");
					td = tr.addElement("td").addAttribute("class", "header").addText("Age");
					td = tr.addElement("td").addAttribute("class", "header").addText("Survival Length");
					
					for(String sampleId:sampleIds){
						SampleResultset sampleResultset = sampleResultsetMap.get(sampleId);
						//lose this
						if(sampleResultset != null && sampleResultset.getSampleIDDE()!= null)	{
							System.out.println(++count+" SampleID :" +sampleResultset.getSampleIDDE().getValue().toString());
						}
						//end lose
						if(sampleResultset!=null)	{
							tr = table.addElement("tr").addAttribute("class", "data");
							
							String sid = sampleResultset.getSampleIDDE()!=null && sampleResultset.getSampleIDDE().getValue() != null ?sampleResultset.getSampleIDDE().getValue().toString() : dv;
							td = tr.addElement("td").addText(sid);
							
							String dis = sampleResultset.getDisease() != null && sampleResultset.getDisease().getValue() != null ?sampleResultset.getDisease().getValue().toString() : dv;
							td = tr.addElement("td").addText(dis);
							
							String gen = sampleResultset.getGenderCode() != null && sampleResultset.getGenderCode().getValue() != null ? sampleResultset.getGenderCode().getValue().toString() : dv;
							td = tr.addElement("td").addText(gen);
							
							String age = sampleResultset.getAgeGroup() != null && sampleResultset.getAgeGroup().getValue() != null ? sampleResultset.getAgeGroup().getValue().toString() : dv;
							td = tr.addElement("td").addText(age);
							
							String slength = sampleResultset.getSurvivalLength() != null ? String.valueOf(sampleResultset.getSurvivalLength()) : dv;
							td = tr.addElement("td").addText(slength);
							
						}
						
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return html.append(document.asXML());
	}
	
}
