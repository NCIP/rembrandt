/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts.form;





import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;




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

public class UploadReporterSetForm extends ActionForm {
    
    private static Logger logger = Logger.getLogger(BaseForm.class);
    
    private RembrandtPresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
    private FormFile reporterSetFile;
    private String reporterSetName;    
    private String reporterType;
    private ArrayList cloneTypeColl = new ArrayList();
    private List<DomainElement> domainElementList = new ArrayList();
    private HttpServletRequest thisRequest;
    private SessionCriteriaBag sessionCriteriaBag; 
	


	public UploadReporterSetForm(){
        //abstract at a later point -- KR
        cloneTypeColl.add(new LabelValueBean("IMAGE Id", "imageId"));
        // cloneTypeColl.add( new LabelValueBean( "BAC Id", "BACId" ) );
        cloneTypeColl.add(new LabelValueBean("Probe Set Id", "probeSetId"));

	}

   
    /**
     * @return Returns the geneSetName.
     */
    public String getReporterSetName() {
        return reporterSetName;
    }




    /**
     * @param geneSetName The geneSetName to set.
     */
    public void setReporterSetName(String reporterSetName) {
        this.reporterSetName = reporterSetName;
    }

    /**
     * @return Returns the geneType.
     */
    public String getReporterType() {
        return reporterType;
    }

    /**
     * @param geneType The geneType to set.
     */
    public void setReporterType(String reporterType) {
        this.reporterType = reporterType;
    }
    /**
     * @return Returns the geneSetFile.
     */
    public FormFile getReporterSetFile() {
        return reporterSetFile;
    }




    /**
     * @param geneSetFile The geneSetFile to set.
     */
    public void setReporterSetFile(FormFile reporterSetFile) {
        this.reporterSetFile = reporterSetFile;
        if (thisRequest != null) {
            String thisReporterType = this.thisRequest.getParameter("reporterType");
            String thisReporterSetName = this.thisRequest.getParameter("reporterSetName");
            String sessionId = this.thisRequest.getSession().getId();
            
            // retrieve the file name & size
            String fileName = reporterSetFile.getFileName();
            int fileSize = reporterSetFile.getFileSize();

            if ( (thisReporterType.length() > 0) && (this.reporterSetFile != null)
                    && (this.reporterSetFile.getFileName().endsWith(".txt")|| this.reporterSetFile.getFileName().endsWith(".TXT"))
                    && (this.reporterSetFile.getContentType().equals("text/plain"))) {
                try {
                    InputStream stream = reporterSetFile.getInputStream();
                    String inputLine = null;
                    BufferedReader inFile = new BufferedReader(
                            new InputStreamReader(stream));

                    int count = 0;
                    
                    //GeneIdentifierDE geneIdentifierDE = null;
                    while ((inputLine = inFile.readLine()) != null
                            && count < RembrandtConstants.MAX_FILEFORM_COUNT) {
                        if (UIFormValidator.isAscii(inputLine)) { // make sure
                                                                    // all data
                                                                    // is ASCII
                            inputLine = inputLine.trim();
                            count++;
                            if (thisReporterType.equalsIgnoreCase("IMAGEId")) {
                                CloneIdentifierDE cloneIdentifierDE = new CloneIdentifierDE.IMAGEClone(inputLine);
                                domainElementList.add(cloneIdentifierDE);
                            } else if (thisReporterType.equalsIgnoreCase("BACId")) {
                                CloneIdentifierDE cloneIdentifierDE = new CloneIdentifierDE.BACClone(inputLine);
                                domainElementList.add(cloneIdentifierDE);
                            } else if (thisReporterType.equalsIgnoreCase("probeSetId")) {
                                CloneIdentifierDE cloneIdentifierDE = new CloneIdentifierDE.ProbesetID(inputLine);
                                domainElementList.add(cloneIdentifierDE);

                            }
                            
                        }
                    }// end of while

                    inFile.close();
                } catch (IOException ex) {
                    logger.error("Errors when uploading Reporter file:"
                            + ex.getMessage());
                }
                sessionCriteriaBag = cacheManager.getSessionCriteriaBag(sessionId);
                sessionCriteriaBag.putUserList(ListType.CloneProbeSetIdentifierSet,thisReporterSetName,domainElementList); 
                cacheManager.putSessionCriteriaBag(sessionId,sessionCriteriaBag);
            }
          }
            
        }
    
    /**
     * @return Returns the cloneTypeColl.
     */
    public ArrayList getCloneTypeColl() {
        return cloneTypeColl;
    }


    /**
     * @param cloneTypeColl The cloneTypeColl to set.
     */
    public void setCloneTypeColl(ArrayList cloneTypeColl) {
        this.cloneTypeColl = cloneTypeColl;
    }
    

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        reporterSetFile = null;
        reporterType = "";
        reporterSetName = "";
         //Set the Request Object
        this.thisRequest = request;
    }
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        
        // Make sure the cloneListFile uploaded is of type txt and MIME type
        // is text/plain
        errors = UIFormValidator.validateTextFileType(reporterSetFile,
                "cloneId", errors);
        
        return errors;
    }


   
}
