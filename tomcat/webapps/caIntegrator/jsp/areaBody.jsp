<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %> 
<% 	/*	
		This page receives the section identifier in the query string (s)
		Read properties file based on this ID, then assembles the 
		
	*/
%>

<%
String act = request.getParameter("area").toLowerCase();
%>

<%
Properties  props = new Properties ();
try {
props.load(new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+request.getParameter("area")+".properties"));
} 
catch (IOException e) {
out.println("cant read props");
}
//FileReader reader = new FileReader(props);
out.print("<div>");
out.print(props);
out.print("</div>");
%>
