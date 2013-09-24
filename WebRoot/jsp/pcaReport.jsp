<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*, gov.nih.nci.rembrandt.web.helper.*,
gov.nih.nci.rembrandt.web.factory.*, gov.nih.nci.rembrandt.web.bean.*, org.dom4j.Document, gov.nih.nci.rembrandt.util.*" %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache" %>

<html>
<head>
	<title>Rembrandt PCA Plots</title>
	<LINK href="css/tabs.css" rel="stylesheet" type="text/css" />
	
	<script language="javascript" type="text/javascript" src="js/caIntScript.js"></script> 
	<script language="javascript">
		function rbtFrame(page)	{
			var winw = 450;
			var winh = 700;
			spawn('rbtFrame.jsp?p='+page, winw, winh);
		}
	</script> 
	
	<script language="javascript" src="js/box/browserSniff.js"></script>
		
    <script type='text/javascript' src='dwr/interface/DynamicReport.js'></script>
	<script type='text/javascript' src='dwr/engine.js'></script>
	<script type='text/javascript' src='dwr/util.js'></script>
    
    <script language="javascript" src="js/a_saveSamples.js"></script>
	<script language="javascript" src="js/box/x_core.js"></script>
	<script language="javascript" src="js/box/x_event.js"></script>
	<script language="javascript" src="js/box/x_dom.js"></script>
	<script language="javascript" src="js/box/x_drag.js"></script>
	<script language="javascript" src="js/box/wz_jsgraphics.js"></script>

	<script language="javascript" src="js/box/dbox.js"></script>
	<script type="text/javascript" src="js/lib/Help.js"></script>
	<script language="JavaScript" type="text/javascript" src="js/rembrandtScript.js"></script>
	<script type="text/javascript" src="js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
		
</head>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<span style="z-index:1000; float:right;">
	<!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	<a href="javascript:top.close()"><img alt="Close" align="right" src="images/close.png" border="0"></a>
	<app:cshelp topic="PCA_report" />
	<a href="#" onclick="javascript:window.print();" title="Print this report."><img alt="Print" align="right" src="images/print.png" border="0" /> </a> 
	
</span>
<div style="background-color: #ffffff"><img alt="Rembrandt" src="images/smallHead.jpg" /></div>


<%
String colorBy = request.getParameter("colorBy")!=null ? (String) request.getParameter("colorBy") : "Disease"; 
String key = request.getParameter("key")!=null ? (String) request.getParameter("key") : "taskId";
String pcaView = request.getParameter("pcaView")!=null ? (String) request.getParameter("pcaView") : "PC1vsPC2";
%>

<div style="text-align:center; width:100%; font-size:11px; font-family:arial;">
	<a href="pcaApplet.jsp?tid=<%=key%>" onclick="rbtFrame('pcaApplet.jsp?tid=<%=key%>');return false;" target="_blank">View 3D Applet - Color by Disease (your browser must be able to support Java version 1.4 or later)</a><br/>
</div>
<div id="header">
	<ul id="primary">
		<li> 
		<%
		if(pcaView.equals("PC1vsPC2"))
			out.write("<span>PC1vsPC2</span>");
		else
			out.write("<a href=\"pcaReport.do?key="+key+"&pcaView=PC1vsPC2\">PC1vsPC2</a>");		
		%>
		</li>
		<li>
		<%
		if(pcaView.equals("PC1vsPC3"))
			out.write("<span>PC1vsPC3</span>");
		else
			out.write("<a href=\"pcaReport.do?key="+key+"&pcaView=PC1vsPC3\">PC1vsPC3</a>");		
		%>
		<li>
		<%
		if(pcaView.equals("PC2vsPC3"))
			out.write("<span>PC2vsPC3</span>");
		else
			out.write("<a href=\"pcaReport.do?key="+key+"&pcaView=PC2vsPC3\">PC2vsPC3</a>");		
		%>
	</ul>
</div>
<div id="main" style="font-family:arial; font-size:12px">
<div style="margin-left:10px">
<b>Color By: </b>
<%
if(!colorBy.equals("Disease"))
	out.write("<a href=\"pcaReport.do?key="+key+"&pcaView="+pcaView+"&colorBy=Disease\">Disease</a>");		
else
	out.write("Disease");
	
out.write(" | ");

if(!colorBy.equals("Gender"))
	out.write("<a href=\"pcaReport.do?key="+key+"&pcaView="+pcaView+"&colorBy=Gender\">Gender</a>");		
else
	out.write("Gender");

out.write(" | ");	

if(!colorBy.equals("NONE"))
	out.write("<a href=\"pcaReport.do?key="+key+"&pcaView="+pcaView+"&colorBy=NONE\">Remove Colors and Shapes</a>");		
else
	out.write("Remove Colors and Shapes");
	
%>
<br/>
</div>
<table summary="This is a layout table">
	<tr><th></th></tr>
	<tr>
		<td>
<% if(pcaView.equals("PC1vsPC2"))	{ %>
<graphing:PCAPlot taskId="<%=key%>" components="PC1vsPC2" colorBy="<%=colorBy%>" />
<% } %>
<% if(pcaView.equals("PC1vsPC3"))	{ %>
<p><graphing:PCAPlot taskId="<%=key%>" components="PC1vsPC3" colorBy="<%=colorBy%>" /></p>
<% } %>
<% if(pcaView.equals("PC2vsPC3"))	{ %>
<p><graphing:PCAPlot taskId="<%=key%>" components="PC2vsPC3" colorBy="<%=colorBy%>" /></p>
<% } %>
		</td>
		<td style="vertical-align:top">
		<div style="border:1px dashed silver;height:300px;width:100px; margin-left:10px; margin-top:30px; overflow:auto;" id="sample_list">
			<div style="background-color: #ffffff; width:100px; font-weight: bold; text-align:center;"><label for="sampleGroupName">Samples:</label></div><br/>
			<div style="font-size:9px; text-align:center;" id="sampleCount"></div><br/>
			<span id="pending_samples" style="font-size:11px"></span>
		</div>
		<br/>
		<div style="margin-left:10px; text-align:center">
			<input type="text" id="sampleGroupName" name="sampleGroupName" style="width:95px"/><br/>
			<input type="button" style="width:95px" value="save samples" onclick="javascript:A_saveSamples();" /><br/>			
		</div>
		<div style="margin-left:10px; font-size:11px; text-decoration:none; text-align:center;">
			<a href="#" onclick="javascript: if(confirm('clear samples?')) { clearPending(); } ">[clear samples]</a><br/>
			<a href="#" onclick="processQuickClinical(); return false;">view clinical data</a><br/><br/>		
			
		</div>
		</td>
	</tr>
</table>
</div>

<!--  translate samples to clinical report -->
<form id="quickClinicalWrapper"></form>

<script language="javascript">

	function processQuickClinical()	{
		var f = document.getElementById("quickClinicalWrapper");
		
		//quickly clear the node, so we dont get duplicate elements when the back button is used
		while(f.firstChild) f.removeChild(f.firstChild);
		
		if(!f)	{ return; }
		//set up the form
		f.setAttribute("method", "post");
		f.setAttribute("action", "quickClinical.do");
		f.setAttribute("name", "quickClinicalWrapper");
		
		for(var i=0; i<pendingSamples.length; i++)	{
			var hid = document.createElement("input");
			hid.setAttribute("type", "hidden");
			hid.setAttribute("name", "sampleList");
			hid.setAttribute("value", pendingSamples[i]);
			f.appendChild(hid);
		}
		
		f.submit();
	}
</script>
<script language="javascript" src="js/box/lassoHelper.js"></script>

</body>
</html>

