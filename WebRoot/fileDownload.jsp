<%@ page language="java" %><%@ page buffer="none" %>
<%@ page import="gov.nih.nci.rembrandt.dbbean.DownloadFile"%>
<%@ page import="gov.nih.nci.rembrandt.dto.lookup.DownloadFileLookup"%>
<%@ page import="gov.nih.nci.rembrandt.dto.lookup.LookupManager"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.*"%>
<%
String key = request.getParameter("fileId")!=null ? (String) request.getParameter("fileId") : null;
if(key!=null)	{
	Long fileId = new Long(key);
	long randomness = System.currentTimeMillis();
		DownloadFile downloadFile = null;
		List fileList = LookupManager.getDownloadFileList();
		for (int i = 0; i < fileList.size(); i++){
			DownloadFileLookup lookup = (DownloadFileLookup)fileList.get(i);
			if (fileId.equals(lookup.getFileId())){
				downloadFile = (DownloadFile)lookup;
				break;
			}
		}
	
	response.setContentType("application/csv");
	response.setHeader("Content-Disposition", "attachment; filename="+downloadFile.getFileName());
	try	{
		
		File file = new File(downloadFile.getFilePath(), downloadFile.getFileName());
		BufferedInputStream bis =  new BufferedInputStream(new FileInputStream(file));
		ServletOutputStream sos = response.getOutputStream();

		byte[] buf = new byte[1024 * 10]; // ~= 10KB
		for (int len = -1; (len = bis.read(buf)) != -1; ){
			sos.write(buf, 0, len);
		}

	}
	catch(Exception e)	{
		out.write("error writing list");
	}
}
%>