package gov.nih.nci.rembrandt.web.xml;

import gov.nih.nci.caintegrator.analysis.messaging.FTestResultEntry;
import gov.nih.nci.caintegrator.dto.query.ClassComparisonQueryDTO;
import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.caintegrator.service.findings.FTestFinding;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations.AnnotationHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations.ReporterAnnotations;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author LandyR
 * Feb 8, 2005
 * 
 */


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

public class FTestReport{

	/**
	 * 
	 */
	public FTestReport() {
		//super();
	}

	public static Document getReportXML(Finding finding, Map filterMapParams) {
		//changed the sig to include an allannotation flag, hence this wrapper method is born
		return getReportXML(finding, filterMapParams, false);
	}
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.ui.report.ReportGenerator#getTemplate(gov.nih.nci.nautilus.resultset.Resultant, java.lang.String)
	 */
	
	public static Document getReportXML(Finding finding, Map filterMapParams, boolean allAnnotations) {

		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		DecimalFormat sciFormat = new DecimalFormat("0.00E0");
		DecimalFormat tmpsciFormat = new DecimalFormat("###0.0000#####################");
		
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
	        //lets flag this as Ftest
	        report.addAttribute("reportSubType", "FTEST");
	        //fudge these for now
	        report.addAttribute("groupBy", "none");
	        String queryName = "none";
	        queryName = finding.getTaskId();
	        
	        //set the queryName to be unique for session/cache access
	        report.addAttribute("queryName", queryName);
	        report.addAttribute("sessionId", "the session id");
	        report.addAttribute("creationTime", "right now");
		    
			StringBuffer sb = new StringBuffer();
			
			int recordCount = 0;
			int totalSamples = 0;
			
			//TODO: instance of
			FTestFinding ccf = (FTestFinding) finding;
			
			
			//process the query details
			
			ArrayList<String> queryDetails = new ArrayList();
			
			ClassComparisonQueryDTO ccdto = (ClassComparisonQueryDTO)ccf.getQueryDTO();
			
			if(ccdto != null)	{
				String tmp = "";
				tmp = ccdto.getQueryName()!=null ? ccdto.getQueryName() : "";
				queryDetails.add("Query Name: " + tmp);
				tmp = ccdto.getArrayPlatformDE() != null ? ccdto.getArrayPlatformDE().getValue().toString() : "";
				queryDetails.add("Array Platform: " + tmp);
				
				tmp = "";
				List<ClinicalQueryDTO> grps = ccdto.getComparisonGroups()!=null ? ccdto.getComparisonGroups() : new ArrayList();
				Collection grs = new ArrayList();
				for(ClinicalQueryDTO cdto : grps)	{
					if(cdto.getQueryName()!=null)
						grs.add(cdto.getQueryName());
				}
				
				tmp += StringUtils.join(grs.toArray(), ", ") + " (baseline)";
				queryDetails.add("Groups: " + tmp);
				
				tmp = ccdto.getExprFoldChangeDE() != null ? ccdto.getExprFoldChangeDE().getValue().toString() : "";
				queryDetails.add("Fold Change: " + tmp);
				//queryDetails.add("Institutions: " + ccdto.getInstitutionDEs());
				tmp = ccdto.getMultiGroupComparisonAdjustmentTypeDE()!=null ? ccdto.getMultiGroupComparisonAdjustmentTypeDE().getValue().toString() : "";
				queryDetails.add("Multi Group: " + tmp);			
				tmp = ccdto.getStatisticalSignificanceDE()!=null ? ccdto.getStatisticalSignificanceDE().getValue().toString() : "";
				queryDetails.add("Stat Sig.: " + tmp);
				tmp = ccdto.getStatisticTypeDE()!=null ? ccdto.getStatisticTypeDE().getValue().toString() : "";
				queryDetails.add("Stat Type: " + tmp);
			}

			String qd = "";
			for(String q : queryDetails){
				qd += q + " ||| ";
			}
			
			
			if(ccf != null)	{
				
				Element details = report.addElement("Query_details");
				cell = details.addElement("Data");
				cell.addText(qd);
				cell = null;
				
				Element headerRow = report.addElement("Row").addAttribute("name", "headerRow");
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Reporter");
			        data = null;
		        cell = null;
		        
		        //pvalue is fixed in the second column, essential for XSL
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
		        	String isAdj = ccf.arePvaluesAdjusted() ? " (Adjusted) " : "";
			        data = cell.addElement("Data").addAttribute("type", "header").addText(RembrandtConstants.PVALUE+isAdj);
			        data = null;
		        cell = null;
	        
		        //one col for each group in the comparison
		        List<String> groupNames = ccf.getGroupNames();
		        for(String n : groupNames)	{
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText(n + " Group Avg");
				        data = null;
			        cell = null;
		        }
		       

			    cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Fold Change");
			        data = null;
		        cell = null;	
		        
		        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        data = cell.addElement("Data").addAttribute("type", "header").addText("Gene Symbol");
			        data = null;
		        cell = null;
		        
		        //starting annotations...get them only if allAnnotations == true
		        if(allAnnotations)	{
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
		        }
		        
		    	/* done with the headerRow and SampleRow Elements, time to add data rows */
		       
		      
		        List<FTestResultEntry> fTestResultEntrys = ccf.getResultEntries();
				List<String> reporterIds = new ArrayList<String>();
				
				for (FTestResultEntry fTestResultEntry: fTestResultEntrys){
					if(fTestResultEntry.getReporterId() != null){
						reporterIds.add(fTestResultEntry.getReporterId());
					}
				}
				
				ArrayPlatformType arrayPlatform = ccdto.getArrayPlatformDE() != null ? ccdto.getArrayPlatformDE().getValueObjectAsArrayPlatformType() : ArrayPlatformType.AFFY_OLIGO_PLATFORM;
				Map reporterResultsetMap = null;
				if(allAnnotations){
					//Map<String, ReporterAnnotations> reporterResultsetMap = null;
					try {
						reporterResultsetMap = AnnotationHandler.getAllAnnotationsFor(reporterIds,arrayPlatform);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else	{
					//Map<String, String> reporterResultsetMap = null;
					try {
						reporterResultsetMap = AnnotationHandler.getGeneSymbolsFor(reporterIds,arrayPlatform);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		        for(FTestResultEntry ccre : ccf.getResultEntries())	{

		        	dataRow = report.addElement("Row").addAttribute("name", "dataRow");
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "reporter").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(ccre.getReporterId());
			        	data = null;
			        cell = null;
			        
			        cell = dataRow.addElement("Cell").addAttribute("type", "pval").addAttribute("class", "data").addAttribute("group", "data");
			        	//String pv = (ccre.getPvalue() == null) ? String.valueOf(ccre.getPvalue()) : "N/A";
			        	BigDecimal bigd = new BigDecimal(ccre.getPvalue());
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(bigd.toPlainString());
			        	data = null;
			        cell = null;
		        
			        //one col for each group
			        for(int i=0; i<ccre.getGroupMeans().length; i++)	{	
				        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
				        	data = cell.addElement("Data").addAttribute("type", "header").addText(resultFormat.format(ccre.getGroupMeans()[i]));
				        	data = null;
				        cell = null;
			        }
			        
			        
			        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText(String.valueOf(resultFormat.format(ccre.getMaximumFoldChange())));
			        	data = null;
			        cell = null;
			        
			        
			        //if only showing genes
			        if(!allAnnotations && reporterResultsetMap != null)	{
			        	String reporterId = ccre.getReporterId();
			        	String genes = reporterResultsetMap.get(reporterId)!=null ? (String)reporterResultsetMap.get(reporterId) : defaultV;
				        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "data");
				        	data = cell.addElement("Data").addAttribute("type", "header").addText(genes);
				        	data = null;
				        cell = null;
			        }
			        else	{
				        //get the gene symbols for this reporter
				        //ccre.getReporterId()
				        String genes = defaultV;
				        
				        //start annotations
				        String accIds = defaultV;
				        String llink = defaultV;
				        String go = defaultV;
				        String pw = defaultV;
				          
							if(reporterResultsetMap != null ){ // && reporterIds != null
								//int count = 0;
								String reporterId = ccre.getReporterId();
								//ReporterResultset reporterResultset = reporterResultsetMap.get(reporterId);
								ReporterAnnotations ra = (ReporterAnnotations) reporterResultsetMap.get(reporterId);
								
								//Collection<String> geneSymbols = (Collection<String>)reporterResultset.getAssiciatedGeneSymbols();
								String geneSymbols = ra.getGeneSymbol();
								if(geneSymbols != null)	
									genes = geneSymbols;
								/*
								if(geneSymbols != null){
									genes = StringUtils.join(geneSymbols.toArray(), delim);
								}
								*/
								Collection<String> genBank_AccIDS = (Collection<String>)ra.getAccessions();
								if(genBank_AccIDS != null){
									accIds = StringUtils.join(genBank_AccIDS.toArray(), delim);
								}
								Collection<String> locusLinkIDs = (Collection<String>)ra.getLocusLinks();
								if(locusLinkIDs != null){
									llink = StringUtils.join(locusLinkIDs.toArray(), delim);
								}
								Collection<String> goIds = (Collection<String>)ra.getGoIDS();
								if(goIds != null){
									go = StringUtils.join(goIds.toArray(), delim);
								}
								Collection<String> pathways = (Collection<String>)ra.getPathways();
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
			}
			else {
				//TODO: handle this error
				sb.append("<br/><br/>Class Comparison is empty<br/>");
			}
		    
		    return document;
	}

}

