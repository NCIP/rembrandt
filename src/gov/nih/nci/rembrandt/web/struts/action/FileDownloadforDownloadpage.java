/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.rembrandt.dbbean.DownloadFile;
import gov.nih.nci.rembrandt.dto.lookup.DownloadFileLookup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class FileDownloadforDownloadpage extends DispatchAction{
	private static Logger logger = Logger.getLogger(FileDownloadforDownloadpage.class);

	public ActionForward brbFileDownload(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
			
			String id = request.getParameter("fileId")!=null ? (String) request.getParameter("fileId") : null;
			if (id == null){
				logger.info("File Id not available from URL.");
				return null;
			}
			Long fileId = new Long(id);
			DownloadFile downloadFile = null;
			List fileList = (ArrayList)request.getSession().getAttribute("downloadFileList");
			
			if (fileList == null || fileList.isEmpty()){
				logger.info("No downloadable files are available for this user.");
				return null;
			}
			for (int i = 0; i < fileList.size(); i++){
				DownloadFileLookup lookup = (DownloadFileLookup)fileList.get(i);
				if (fileId.equals(lookup.getFileId())){
					downloadFile = (DownloadFile)lookup;
					break;
				}
			}
			
			if (downloadFile.getFileName() == null || downloadFile.getFilePath() == null){
				logger.info("File name or file path is not available.");
				return null;
			}
					
	        try
	        {
	        	File file = new File(downloadFile.getFilePath(), downloadFile.getFileName());
	        	BufferedInputStream bis =  new BufferedInputStream(new FileInputStream(file));
	        	
	            // set a non-standard content type to force brower to open Save As dialog
	            response.setContentType("application/octet-stream");
	            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	            ServletOutputStream sos = response.getOutputStream();

				byte[] buf = new byte[1024 * 10]; // ~= 10KB
				for (int len = -1; (len = bis.read(buf)) != -1; ){
					sos.write(buf, 0, len);
				}
			    response.setContentLength((int)file.length());
			        
			    sos.flush(); // let the Servlet container handle closing of this ServletOutputStream
			    bis.close();
	        }
	        catch(Exception e)	{
	        	logger.error("Error occured: " + e.getMessage());
			}
	        logger.info(downloadFile.getFileName() + " was downloaded.");
	        return null;
	 }
}
