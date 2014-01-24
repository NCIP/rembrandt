/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.action;

import gov.nih.nci.rembrandt.dbbean.DownloadFile;
import gov.nih.nci.rembrandt.dto.lookup.DownloadFileLookup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class FileDownloadforDownloadpage extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{
	private static Logger logger = Logger.getLogger(FileDownloadforDownloadpage.class);
	
	Map<String, Object> sessionMap;
	HttpServletRequest servletRequest;
	HttpServletResponse servletResponse;

	public String brbFileDownload()
					throws Exception {

		String id = this.servletRequest.getParameter("fileId")!=null ? 
				(String) this.servletRequest.getParameter("fileId") : null;
		
		if (id == null){
			logger.info("File Id not available from URL.");
			return null;
		}
		Long fileId = new Long(id);
		DownloadFile downloadFile = null;
		List fileList = (ArrayList)this.sessionMap.get("downloadFileList");

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
			this.servletResponse.setContentType("application/octet-stream");
			this.servletResponse.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			ServletOutputStream sos = this.servletResponse.getOutputStream();

			byte[] buf = new byte[1024 * 10]; // ~= 10KB
			for (int len = -1; (len = bis.read(buf)) != -1; ){
				sos.write(buf, 0, len);
			}
			this.servletResponse.setContentLength((int)file.length());

			sos.flush(); // let the Servlet container handle closing of this ServletOutputStream
			bis.close();
		}
		catch(Exception e)	{
			logger.error("Error occured: " + e.getMessage());
		}
		logger.info(downloadFile.getFileName() + " was downloaded.");
		return null;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.servletResponse = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}
	
	
}
