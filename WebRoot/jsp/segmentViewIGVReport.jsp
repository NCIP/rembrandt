<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %><%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %><%@ page buffer="none" %><%@ page import="
gov.nih.nci.rembrandt.util.RembrandtConstants,
org.dom4j.Document,org.dom4j.io.XMLWriter,org.dom4j.io.OutputFormat"
%><%
response.setDateHeader ("Expires", -1);

	//generate the CSV
	response.setContentType("application/x-java-jnlp-file");
	response.setHeader("Content-Disposition", "attachment; filename=igv.jnlp");
	try{	
	String sb = (String)request.getAttribute(RembrandtConstants.REPORT_IGV);
	if(sb != null )
 		out.println(sb);
 	}
	catch(Exception e)	{
		out.println("Error Generating the report");
	}
%>
