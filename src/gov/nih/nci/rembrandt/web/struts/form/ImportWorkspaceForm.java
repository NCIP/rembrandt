package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ImportWorkspaceForm extends ActionForm{

    private static Logger logger = Logger.getLogger(ImportWorkspaceForm.class);
    
	private FormFile workspaceFile;	
	private String xmlDoc = null;
	private String importFileName = null;
	
	public FormFile getWorkspaceFile() {
		return workspaceFile;
	}
	public void setWorkspaceFile(FormFile workspaceFile) {
		this.workspaceFile = workspaceFile;
		
		setImportFileName(this.workspaceFile.getFileName() );
		
        if ( this.workspaceFile != null
                && this.workspaceFile.getFileName().toLowerCase().endsWith(".xml")
                && this.workspaceFile.getContentType().equals("text/xml") )   {
            try {
                InputStream stream = workspaceFile.getInputStream();
                String inputLine = null;
                BufferedReader inFile = new BufferedReader( new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                
                while ( (inputLine = inFile.readLine()) != null ) {
                    if (UIFormValidator.isAscii(inputLine)) { 
                    	buffer.append( inputLine.trim() );
                    }
                }// end of while

                inFile.close();
                
                setXmlDoc( buffer.toString() );
            } catch (IOException ex) {
                logger.error("Errors when uploading Workspace file:" + ex.getMessage());
            }
        }
      }
		
	
	public String getXmlDoc() {
		return xmlDoc;
	}
	public void setXmlDoc(String xmlDoc) {
		this.xmlDoc = xmlDoc;
	}
	public String getImportFileName() {
		return importFileName;
	}
	public void setImportFileName(String importFileName) {
		this.importFileName = importFileName;
	}
	
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        
        errors = UIFormValidator.validateXmlFileType(getWorkspaceFile(), "Import File", errors);
        
        return errors;
    }
	
}
