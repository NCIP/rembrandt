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
	
	<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
	
	<script language="JavaScript" src="js/box/browserSniff.js"></script>
		
    <script type='text/javascript' src='dwr/interface/DynamicReport.js'></script>
	<script type='text/javascript' src='dwr/engine.js'></script>
	<script type='text/javascript' src='dwr/util.js'></script>
    
    <script language="JavaScript" src="js/a_saveSamples.js"></script>
	<script language="JavaScript" src="js/box/x_core.js"></script>
	<script language="JavaScript" src="js/box/x_event.js"></script>
	<script language="JavaScript" src="js/box/x_dom.js"></script>
	<script language="JavaScript" src="js/box/x_drag.js"></script>
	<script language="JavaScript" src="js/box/wz_jsgraphics.js"></script>

	<script language="JavaScript" src="js/box/dbox.js"></script>
	<script type="text/javascript" src="js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
		
</head>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<span style="z-index:1000; float:right;">
	<!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	<a href="javascript:top.close()"><img align="right" src="images/close.png" border="0"></a>
	<a href="javascript: spawn('help.jsp?sect=pcaPlot', 350, 500);"><img align="right" src="images/help.png" border="0" onmouseover="return overlib('Click here for additional information about this report.', CAPTION, 'Help');" onmouseout="return nd();" /></a>
	<a href="#" onclick="javascript:window.print();"><img align="right" src="images/print.png" border="0" onmouseover="return overlib('Print this report.', CAPTION, 'Help');" onmouseout="return nd();"/> </a> 
	
</span>
<div style="background-color: #ffffff"><img src="images/smallHead.jpg" /></div>


<%
String colorBy = request.getParameter("colorBy")!=null ? (String) request.getParameter("colorBy") : "Disease"; 
String key = request.getParameter("key")!=null ? (String) request.getParameter("key") : "taskId";
String pcaView = request.getParameter("pcaView")!=null ? (String) request.getParameter("pcaView") : "PC1vsPC2";
%>


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
<table>
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
			<div style="background-color: #ffffff; width:100px; font-weight: bold; text-align:center;">Samples:</div><br/>
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
		</div>
		</td>
	</tr>
</table>
</div>

<script language="javascript" src="js/box/lassoHelper.js"></script>

</body>
</html>

