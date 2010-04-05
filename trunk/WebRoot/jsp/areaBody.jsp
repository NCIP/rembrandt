<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>
<% 	/*
		This page receives the section identifier in the query string (s)
		Read properties file based on this ID, then assembles the
	*/
%>
<%String act = request.getParameter("area").toLowerCase();%>
<%Properties  props = new Properties ();
        try {
	        fsi = new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+request.getParameter("area")+".properties");
            props.load(fsi);
            fsi.close();
        }
        catch (IOException e) {
        	out.println("cant read props");
        	if(fsi != null)
		      	fsi.close();
        }
        finally	{
	  	  	if(fsi != null)
		      	fsi.close();
		}
out.print("<div>");
out.print(props);
out.print("</div>");
%>
