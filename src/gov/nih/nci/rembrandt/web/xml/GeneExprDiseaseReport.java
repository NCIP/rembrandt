package gov.nih.nci.rembrandt.web.xml;

import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE.GeneSymbol;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.DiseaseGroupResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Landyr
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
		
		//	have setter or put in props file
		//String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };
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
	        //hold a message to display on the report
	        report.addAttribute("msg", (resultant.isOverLimit() ? "over limit" : ""));
		 
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
							        cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "gene").addAttribute("group", "header");
							        	data = cell.addElement("Data").addAttribute("type", "header").addText(geneSymbol);
							        	data = null;
							        cell = null;
									cell = dataRow.addElement("Cell").addAttribute("type", "data").addAttribute("class", "reporter").addAttribute("group", "header");
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
						sb.append("<br/><br/>Gene Disease View container is empty");
						//TODO: something fancy here w/errors
					}
		
					//return "<div class=\"rowCount\">"+helpFul +recordCount+" records returned &nbsp;&nbsp;&nbsp;" + links + "</div>\n" + css.toString() + sb.toString();
		    
		    return document;
	}

}
