<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*, gov.nih.nci.nautilus.constants.NautilusConstants" %>

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
//below, we need to set the form action to the name cooresponding bean/action -
//get if from the request, and make lower to match the struts-config
String act = "";
String actProps = "";
String ag ="notAllGenes";

/* If this is an all genes query, the character ':' will be in the request
 * and will be split into a string array, so that the appropriate props file
 * can be read. Otherwise, the props will be determined by the s parameter set to
 * lowercase.
 */
String param = request.getParameter("s");
  if(param.indexOf(':') != -1){
    String[] paramStrings = param.split(":");
    actProps = paramStrings[0];
    act = actProps.toLowerCase();
    ag = paramStrings[1];
  }
  else
    act = param.toLowerCase();
%>
<Br>
<span id="popup" name="popup"></span>
<%
	String preview = (String) request.getAttribute("preview");
	if(preview != null && preview.equals("yes"))
	{ %>
<%@ include file="popup.jsp" %>
<%
	}
%>
<div class="setQuery">
<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
</div>

<html:form action="<%=act%>" enctype="multipart/form-data">
  <%
    request.getSession().setAttribute("currentPage2", "1");
  	session.removeAttribute("currentPage");
  	String pageStr = (String)request.getSession().getAttribute("currentPage");
  	Properties props = new Properties();
  	  
  	if(ag.charAt(0) == 'a'){  //this denotes that the query is 'all genes'
        try {
            props.load(new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+ag+".properties"));
            }
         catch (IOException e) {
            out.println("cant read props");
        	  }
     }
  	 else{ // this denoted that the query is not all genes and is specified right from request as 'param'
      try {
      props.load(new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+param+".properties"));
      }
  	  catch (IOException e) {
      out.println("cant read props");
  	  }
  	 }	
  	String strIncFile = "";
  	for (int t=1; t<props.size()+1; t++)	{
  		strIncFile = "/jsp/tiles/"+props.getProperty(String.valueOf(t))+"_tile.jsp?act="+act;
 	%>
  <div class="report" style="padding:3px">

      <tiles:insert page="<%= strIncFile %>" flush="false" />
	</div>
  <%
  }
  %>
</html:form>
