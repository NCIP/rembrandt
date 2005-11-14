package gov.nih.nci.rembrandt.web.struts.form;





import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;


public class UploadReporterSetForm extends ActionForm {
    
    private static Logger logger = Logger.getLogger(BaseForm.class);
    
    private PresentationTierCache cacheManager = ApplicationFactory.getPresentationTierCache();
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