/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag.ListType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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
	private FileTypes fileType = FileTypes.QUERY;	
	
	public enum FileTypes 
	{
	     QUERY("1"),
	     LIST("2");

	     private static final Map<String,FileTypes> hashMap 
	          = new HashMap<String,FileTypes>();

	     static {
	          for(FileTypes s : EnumSet.allOf(FileTypes.class))
	        	  hashMap.put(s.getId(), s);
	     }

	     private String id;

	     private FileTypes(String id) {
	          this.id = id;
	     }

	     public String getId() { return id; }

	     public static FileTypes get(String id) { 
	          return hashMap.get(id); 
	     }
	}
	
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
	
    public String getFileType() {
    	return fileType.getId();
	}

    public FileTypes getFileTypeEnum() {
    	return fileType;
	}
    
    public void setFileType(String fileType) {
    	this.fileType = FileTypes.get( fileType );
	}

	public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        
        errors = UIFormValidator.validateXmlFileType(getWorkspaceFile(), "Import File", errors);
        
        return errors;
    }
	
}
