/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ImportWorkspaceForm {

    private static Logger logger = Logger.getLogger(ImportWorkspaceForm.class);
	
	String fileType;
	List<String> fileTypeList = new ArrayList<String>();
	
	public ImportWorkspaceForm() {
		fileTypeList.add("QUERY");
		fileTypeList.add("LIST");
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<String> getFileTypeList() {
		return fileTypeList;
	}

	public void setFileTypeList(List<String> fileTypeList) {
		this.fileTypeList = fileTypeList;
	}
	
	
	
	
//	private FileTypes fileType = FileTypes.QUERY;	
//	
//	public enum FileTypes 
//	{
//	     QUERY("1"),
//	     LIST("2");
//
//	     private static final Map<String,FileTypes> hashMap 
//	          = new HashMap<String,FileTypes>();
//
//	     static {
//	          for(FileTypes s : EnumSet.allOf(FileTypes.class))
//	        	  hashMap.put(s.getId(), s);
//	     }
//
//	     private String id;
//
//	     private FileTypes(String id) {
//	          this.id = id;
//	     }
//
//	     public String getId() { return id; }
//
//	     public static FileTypes get(String id) { 
//	          return hashMap.get(id); 
//	     }
//	}
//	
//	
//	
//	public void setWorkspaceFilexx(FormFile workspaceFile) {
//	
//		//this.workspaceFile = workspaceFile;
//		
////		setImportFileName(this.workspaceFile.getFileName() );
////		
////        if ( this.workspaceFile != null
////                && this.workspaceFile.getFileName().toLowerCase().endsWith(".xml")
////                && this.workspaceFile.getContentType().equals("text/xml") )   {
////            try {
////                InputStream stream = workspaceFile.getInputStream();
////                String inputLine = null;
////                BufferedReader inFile = new BufferedReader( new InputStreamReader(stream));
////
////                StringBuffer buffer = new StringBuffer();
////                
////                while ( (inputLine = inFile.readLine()) != null ) {
////                    if (UIFormValidator.isAscii(inputLine)) { 
////                    	buffer.append( inputLine.trim() );
////                    }
////                }// end of while
////
////                inFile.close();
////                
////                setXmlDoc( buffer.toString() );
////            } catch (IOException ex) {
////                logger.error("Errors when uploading Workspace file:" + ex.getMessage());
////            }
////        }
//      }
//		
//	
//	public String getXmlDoc() {
//		return xmlDoc;
//	}
//	public void setXmlDoc(String xmlDoc) {
//		this.xmlDoc = xmlDoc;
//	}
//	
//    public String getFileType() {
//    	return fileType.getId();
//	}
//
//    public FileTypes getFileTypeEnum() {
//    	return fileType;
//	}
//    
//    public List<ImportWorkspaceForm.FileTypes> getFileTypeList() {
//    	List<ImportWorkspaceForm.FileTypes> types = Arrays.asList(FileTypes.values());;
//    	return Arrays.asList(FileTypes.values()); 
//    }
//    
//    public void setFileType(String fileType) {
//    	this.fileType = FileTypes.get( fileType );
//	}
//
//
//	public File getWorkspaceFile() {
//		return workspaceFile;
//	}
//
//
//	public void setWorkspaceFile(File workspaceFile) {
//		this.workspaceFile = workspaceFile;
//	}
//
//
//	public String getWorkspaceFileContentType() {
//		return workspaceFileContentType;
//	}
//
//
//	public void setWorkspaceFileContentType(String workspaceFileContentType) {
//		this.workspaceFileContentType = workspaceFileContentType;
//	}
//
//
//	public void setFileType(FileTypes fileType) {
//		this.fileType = fileType;
//	}
//
//
//	public String getWorkspaceFileName() {
//		return workspaceFileName;
//	}
//
//	public void setWorkspaceFileName(String workspaceFileName) {
//		this.workspaceFileName = workspaceFileName;
//	}
//    

	
}
