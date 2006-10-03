<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*, gov.nih.nci.rembrandt.util.RembrandtConstants" %>
<%@ page import="java.io.FileInputStream" %>

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
<br/>
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
  	
  	FileInputStream fsi = null;
  	
  	if(ag.charAt(0) == 'a'){  //this denotes that the query is 'all genes'
        try {
	        fsi = new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+ag+".properties");
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
     }
  	 else	{ // this denoted that the query is not all genes and is specified right from request as 'param'
      try {
      	fsi = new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+param+".properties");
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

  	    	  
  	 }	
  	String strIncFile = "";
  	%>
    <script type="text/javascript">Help.convertHelp("<%=act%>", " align='right'", "float:right;padding:2px;");</script>
  	<%
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
