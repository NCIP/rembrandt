package gov.nih.nci.rembrandt.web.taglib;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.service.findings.HCAFinding;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.queryservice.resultset.annotation.GeneExprAnnotationService;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.reports.quick.QuickClinicalReport;

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



/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class HCPlotReport extends TagSupport {
	
	private String taskId = "";
	//private Logger logger = Logger.getLogger(HCPlotReport.class);
	private static Logger logger = Logger.getLogger(HCPlotReport.class);	
	
	private RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
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
		return QuickClinicalReport.quickSampleReport(sampleIds);
		/*
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
		*/
	}
	
}
