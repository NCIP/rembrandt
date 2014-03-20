<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
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
    
  //out.println("\n\n=============================");
  //out.println("\nThe act value is " + act);
  //out.println("\nThe s param value is " + param);
  //out.println("\n\n=============================");
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
<s:fielderror fieldName="org.apache.struts.action.GLOBAL_ERROR"/>
</div>

<s:set var="submitAction"><%=act%></s:set>
<s:form action="%{#submitAction}" enctype="multipart/form-data" theme="simple">
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
      	out.println("cant read props22");
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
    <app:cshelp topic="<%=act%>" style="cursor:pointer;float:right;padding:2px;" />

	  	<s:if test="form.selectedView.equals('regionView')">
			<script type="text/javascript">
				Event.observe(window, "load", function() {
					document.getElementById("segmentMean").style.display = "none";
					document.getElementById("segmentMean").style.visibility = "hidden";
					
					toggleGeneRegionView('regionView');
				});
			</script>
		</s:if>
		<s:else> 
			<script type="text/javascript">
				Event.observe(window, "load", function() {
					document.getElementById("segmentMean").style.display = "none";
					document.getElementById("segmentMean").style.visibility = "hidden";
					toggleGeneRegionView('geneView');
					
				});
			</script>
		</s:else>
	
	<script type="text/javascript">
		function toggleCopyNumberView(selectedView) {
			if ( selectedView == "calculatedCN" ) {
				document.getElementById("segmentMean").style.display = "none";
				document.getElementById("segmentMean").style.visibility = "hidden";
				
				document.getElementById("calculatedCN").style.display = "block";
				document.getElementById("calculatedCN").style.visibility = "visible";
			} else {
				document.getElementById("segmentMean").style.display = "block";
				document.getElementById("segmentMean").style.visibility = "visible";
				
				document.getElementById("calculatedCN").style.display = "none";
				document.getElementById("calculatedCN").style.visibility = "hidden";
			}
		}
		
		function toggleGeneRegionView(selectedView) {
			if ( selectedView == "regionView" ) {
				document.getElementById("geneView").style.display = "none";
				document.getElementById("geneView").style.visibility = "hidden";
				
				document.getElementById("regionView").style.display = "block";
				document.getElementById("regionView").style.visibility = "visible";
				
				document.getElementById("geneRegionView").value = "regionView";
			} else {
				document.getElementById("geneView").style.display = "block";
				document.getElementById("geneView").style.visibility = "visible";
				
				document.getElementById("regionView").style.display = "none";
				document.getElementById("regionView").style.visibility = "hidden";
				
				document.getElementById("geneRegionView").value = "geneView";
			}
		}
	</script>  	
	
	<%
  	for (int t=1; t<props.size()+1; t++)	{
  		strIncFile = "/jsp/tiles/"+props.getProperty(String.valueOf(t))+"_tile.jsp?act="+act;
  		System.out.println();
 	%>
 	<div class="report" style="padding:3px">
      <tiles:insertTemplate template="<%= strIncFile %>" flush="false"   />
	</div>
  <%
  }
  %>
</s:form>
