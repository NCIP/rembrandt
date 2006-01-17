package gov.nih.nci.rembrandt.web.xml;

import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;
import gov.nih.nci.rembrandt.util.DEUtils;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
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
	public Document getReportXML(Resultant resultant, Map filterMapParams) {

		//String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };
		DecimalFormat resultFormat = new DecimalFormat("0.0000");
		String defaultV = "-";
		
			Document document = DocumentHelper.createDocument();

			try	{
			Element report = document.addElement( "Report" );
			Element cell = null;
			Element data = null;
			Element dataRow = null;
			//add the atts
	        report.addAttribute("reportType", "Clinical");
	        //fudge these for now
	        report.addAttribute("groupBy", "none");
	        String queryName = resultant.getAssociatedQuery().getQueryName();
	        
	        //set the queryName to be unique for session/cache access
	        report.addAttribute("queryName", queryName);
	        report.addAttribute("sessionId", "the session id");
	        report.addAttribute("creationTime", "right now");

	        
		    boolean gLinks = false;
			boolean cLinks = false;
			StringBuffer sb = new StringBuffer();
			
			ResultsContainer  resultsContainer = resultant.getResultsContainer();
			SampleViewResultsContainer sampleViewContainer = null;
			if(resultsContainer instanceof DimensionalViewContainer)	{
				
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

				sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
				
			}
			else if (resultsContainer instanceof SampleViewResultsContainer)	{
				
				sampleViewContainer = (SampleViewResultsContainer) resultsContainer;
				
			}
			
			Collection samples = sampleViewContainer.getSampleResultsets();
			/*
			sb.append("<div class=\"rowCount\">"+helpFul+samples.size()+" records returned &nbsp;&nbsp;&nbsp;" + links + "</div>\n");
			sb.append("<table cellpadding=\"0\" cellspacing=\"0\">\n");
			*/
			
			//	set up the headers for this table 
			Element headerRow = report.addElement("Row").addAttribute("name", "headerRow");
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Sample");
				        data = null;
			        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Age at Dx (years)");
				        data = null;
			        cell = null;
					cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Gender");
				        data = null;
			        cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Survival (months)");
				        data = null;
			        cell = null;
					cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("Disease");
				        data = null;
			        cell = null;
			        
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
		        	data = cell.addElement("Data").addAttribute("type", "header").addText("Grade");
		        	data = null;
	        	   cell = null;
			        cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Race");
			        	data = null;
		        	cell = null;
		        	
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Karnofsky");
			        	data = null;
		        	cell = null;
		        	
		       
		        	
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Neurological Exam Outcome");
			        	data = null;
		        	cell = null;
		        	
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("MRI Desc");
			        	data = null;
		        	cell = null;
		        	
		        
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Followup Month");
			        	data = null;
		        	 cell = null;
		        	 
		        
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Steroid Dose Status");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Anti-Convulsant Status");
			        	data = null;
		        	 cell = null;
		        	 
		        	
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Radiation Site");
			        	data = null;
		        	 cell = null;
		        	 
		        	
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Radiation Fraction Dose");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Radiation Fraction Number");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Radiation Type");
			        	data = null;
		        	 cell = null;		        	 
		        	
		        	 
		        	
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Chemo Agent Name");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Chemo Course Count");
			        	data = null;
		        	 cell = null;
		        
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Surgery Procedure Title");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Surgery Tumor Histology");
			        	data = null;
		        	 cell = null;
		        	 
		        
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("Prior Therapy Surgery Outcome");
			        	data = null;
		        	 cell = null;
		        	 
		        	 // starting onstudy areas
		        	 
		        	 
		        
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Radiation Site");
			        	data = null;
		        	 cell = null;
		        	 
		        
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Radiation Neurosis Status");
			        	data = null;
		        	 cell = null;
		        	 
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Radiation Fraction Dose");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Radiation Fraction Number");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Radiation Type");
			        	data = null;
		        	 cell = null;		        	 
		        	
		        	 
		        
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Chemo Agent Name");
			        	data = null;
		        	 cell = null;
		        	 
		        
		        	 
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Chemo Course Count");
			        	data = null;
		        	 cell = null;
		        	 
		        	
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Surgery Procedure Title");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Surgery Indication ");
			        	data = null;
		        	 cell = null;
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Surgery Histo Diagnosis ");
			        	data = null;
		        	 cell = null;
		        	 
		        	 
		        	
		        	 
		        	 cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
			        	data = cell.addElement("Data").addAttribute("type", "header").addText("OnStudy Therapy Surgery Outcome");
			        	data = null;
		        	 cell = null;
		        	 
		        	 
		    
		        	 
		    //sb.append("<Tr><Td id=\"header\">SAMPLE</td><td id=\"header\">AGE at Dx (years)</td><td id=\"header\">GENDER</td><td id=\"header\">SURVIVAL (months)</td><td id=\"header\">DISEASE</td>");
 		   	
		    
			Iterator si = samples.iterator(); 
			if(si.hasNext())	{
				SampleResultset sampleResultset =  (SampleResultset)si.next();
   				if(sampleResultset.getGeneExprSingleViewResultsContainer() != null)	{
					cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("GeneExp");
				        data = null;
			        cell = null;
   					//sb.append("<Td id=\"header\">GeneExp</td>");
   				}
   	 		   	if(sampleResultset.getCopyNumberSingleViewResultsContainer()!= null)	{
	   	 		   	cell = headerRow.addElement("Cell").addAttribute("type", "header").addAttribute("class", "header").addAttribute("group", "header");
				        data = cell.addElement("Data").addAttribute("type", "header").addText("CopyNumber");
				        data = null;
			        cell = null;
   	 		   		//sb.append("<td id=\"header\">CopyNumber</td>");
   	 		   	}
   	 		   	//sb.append("</tr>\n");
			}
			
   			for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {

   				SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();   			
				
				dataRow = report.addElement("Row").addAttribute("name", "dataRow");
					        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "sample").addAttribute("group", "sample");
   					        	data = cell.addElement("Data").addAttribute("type", "data").addText(sampleResultset.getSampleIDDE().getValue().toString());
   					        	data = null;
   					        cell = null;
							cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        	data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNV(sampleResultset.getAgeGroup()));
   					        	data = null;
   					        cell = null;
   					        String theGender = defaultV;
   					        if(!DEUtils.checkNV(sampleResultset.getGenderCode()).equalsIgnoreCase("O"))
   					        	theGender = DEUtils.checkNV(sampleResultset.getGenderCode());
							cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
						    	data = cell.addElement("Data").addAttribute("type", "data").addText(theGender);
   					        	data = null;
   					        cell = null;
							cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
						    	data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNV(sampleResultset.getSurvivalLengthRange()));
   					        	data = null;
   					        cell = null;
							cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
								data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNV(sampleResultset.getDisease()));
   					        	data = null;
   					        cell = null;
   					        
   					        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getWhoGrade()));
			        		data = null;
			        	   cell = null;
   					        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
	   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNV(sampleResultset.getRaceDE()));
				        		data = null;
				        	cell = null;
				        	
				        	cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
	   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNV(sampleResultset.getKarnofskyClinicalEvalDE()));
				        		data = null;
				        	cell = null;
				        	
				        	
				        	
				        	cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getNeuroExamDescs()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getMriScoreDescs()));
			        		data = null;
			        	    cell = null;
			        	    
			        	
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getFollowupMonths()));
			        		data = null;
			        	    cell = null;
			        	 
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getSteroidDoseStatuses()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getAntiConvulsantStatuses()));
			        		data = null;
			        	    cell = null;
			        	    
			        	 
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorRadiationRadiationSites()));
			        		data = null;
			        	    cell = null;
			        	    
			        	
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorRadiationFractionDoses()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorRadiationFractionNumbers()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorRadiationRadiationTypes()));
			        		data = null;
			        	    cell = null;
			        	    
			        	  
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorChemoAgentNames()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorChemoCourseCounts()));
			        		data = null;
			        	    cell = null;
			        	    
			        	  
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorSurgeryProcedureTitles()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorSurgeryTumorHistologys()));
			        		data = null;
			        	    cell = null;
			        	    
			        	 
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getPriorSurgerySurgeryOutcomes()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    
			        	    // starting onstudy
			        	    
			        	    
			        	 
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudyRadiationRadiationSites()));
			        		data = null;
			        	    cell = null;
			        	    
			        	  
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudyRadiationFractionDoses()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudyRadiationFractionNumbers()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudyRadiationNeurosisStatuses()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudyRadiationRadiationTypes()));
			        		data = null;
			        	    cell = null;
			        	    
			        	
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudyChemoAgentNames()));
			        		data = null;
			        	    cell = null;
			        	    
			        	  
			        	    
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudyChemoCourseCounts()));
			        		data = null;
			        	    cell = null;
			        	    
			        	  
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudySurgeryProcedureTitles()));
			        		data = null;
			        	    cell = null;
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudySurgeryIndications()));
			        		data = null;
			        	    cell = null;			        	    
			        	  
			        	    
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudySurgeryHistoDiagnoses()));
			        		data = null;
			        	    cell = null;
			        	    
			        	 
			        	    cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   					        data = cell.addElement("Data").addAttribute("type", "data").addText(DEUtils.checkNull(sampleResultset.getOnStudySurgerySurgeryOutcomes()));
			        		data = null;
			        	    cell = null;
		   		
		   		/*
   	   			sb.append("<tr><td>"+sampleResultset.getBiospecimen().getValue().toString().substring(2)+ "</td>" +
   					"<Td>"+sampleResultset.getAgeGroup().getValue()+ "</td>" +
					"<td>"+sampleResultset.getGenderCode().getValue()+ "</td>" +
					"<td>"+sampleResultset.getSurvivalLengthRange().getValue()+ "</td>" +
					"<Td>"+sampleResultset.getDisease().getValue() + "</td>");
				*/
	   			if(sampleResultset.getGeneExprSingleViewResultsContainer() != null)	{
	   				//TODO: create the links
					cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   						data = cell.addElement("Data").addAttribute("type", "data").addText("G");
   					    data = null;
   					cell = null;
	   				//sb.append("<td><a href=\"report.do?s="+sampleName+"_gene&report=gene\">G</a></td>");
	   			}
	   			/*
		   		else if (gLinks){
	   				cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   						data = cell.addElement("Data").addAttribute("type", "data").addText("-");
   					    data = null;
   					cell = null;
		   			//sb.append("<td>&nbsp;</td>"); //empty cell
		   		}
		   		*/
	   			if(sampleResultset.getCopyNumberSingleViewResultsContainer()!= null)	{
	   				//	TODO: create the links
	   				cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   						data = cell.addElement("Data").addAttribute("type", "data").addText("C");
   					    data = null;
   					cell = null;
	   				//sb.append("<Td><a href=\"report.do?s="+sampleName +"_copy&report=copy\">C</a></td>");
	   			}
	   			/*
	   			else if (cLinks){
	   				cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "data").addAttribute("group", "data");
   						data = cell.addElement("Data").addAttribute("type", "data").addText("-");
   					    data = null;
   					cell = null;
		   			//sb.append("<td>&nbsp;</td>"); //empty cell
		   		}
		   		*/
	   			
	   			//report.append("row", row);
	   			//sb.append("</tr>\n");
    		}
    		//sb.append("</table>\n<br>");
    		//return sb.toString(); 
			}
			catch(Exception e)	{
				//asdf
				System.out.println(e);
			}
		    return document;		     
	}

}
