<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %> 
<% 	/*	
		This page receives the section identifier in the query string (s)
		Read properties file based on this ID, then assembles the 
		tiles (only as includes) to build the form
 		[reads from props file and generate content with a loop ]
		
		props file example: dataType.properties
		1=dataElement
		2=dateElementTwo
		
		corresponding JSP tiles are called: dataElement_tile.jsp
	*/
%>

<%

	Properties props = new Properties();
    try {
    props.load(new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+request.getParameter("s")+".properties"));
    } 
	catch (IOException e) {
    out.println("cant read props");
	}
	String strIncFile = "";
	for (int t=1; t<props.size()+1; t++)	{	
		strIncFile = "/jsp/tiles/"+props.getProperty(String.valueOf(t))+"_tile.jsp";
		%>
		<tr class="report"><td><tiles:insert page="<%= strIncFile %>" flush="true" /></td></tr>
		<%
	}
%>




