<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*, gov.nih.nci.rembrandt.web.helper.*,
gov.nih.nci.rembrandt.web.factory.*, gov.nih.nci.rembrandt.web.bean.*, org.dom4j.Document, gov.nih.nci.rembrandt.util.*" %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.rembrandt.cache.*" %>

<html>
	<head>
		<title>Rembrandt Clinical Plots</title>
		<LINK href="css/tabs.css" rel="stylesheet" type="text/css" />
		
		<script language="JavaScript" src="/rembrandt/js/box/browserSniff.js"></script>
		<script type='text/javascript' src='/rembrandt/dwr/interface/DynamicReport.js'></script>
		<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>
		<script type='text/javascript' src='/rembrandt/dwr/util.js'></script>
	   
	    <script language="JavaScript" src="/rembrandt/js/a_saveSamples.js"></script>
		<script language="JavaScript" src="/rembrandt/js/box/x_core.js"></script>
    	<script language="JavaScript" src="/rembrandt/js/box/x_event.js"></script>
    	<script language="JavaScript" src="/rembrandt/js/box/x_dom.js"></script>
    	<script language="JavaScript" src="/rembrandt/js/box/x_drag.js"></script>
    	<script language="JavaScript" src="/rembrandt/js/box/wz_jsgraphics.js"></script>
    
		<script language="JavaScript" src="/rembrandt/js/box/dbox.js"></script>
		<script type="text/javascript" src="/rembrandt/js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>
		
	</head>
<body>	
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<span style="z-index:1000; float:right;"><a href="javascript:top.close()"><img src="images/close.png" border="0"></a></span>
<div style="background-color: #ffffff"><img src="images/smallHead.jpg" /></div>
<br/><br/>
	<%
	String key = request.getParameter("taskId")!=null? (String) request.getParameter("taskId") : "noKey";
	String sessionId = session.getId();
	String componentStr = request.getParameter("comp")!=null ? (String) request.getParameter("comp") : "SurvivalvsAgeAtDx";
	if(componentStr.equals("ks"))
		componentStr = "KarnofskyScorevsAgeAtDx";
		
	//String colorBy = "Disease";
	%>

<div id="main" style="font-family:arial; font-size:12px">	
<div style="margin-left:10px">
<b>Component: </b>
<%
if(componentStr.equals("KarnofskyScorevsAgeAtDx"))
	out.write("<a href=\"clinicalPlots.do?taskId="+key+"&comp=SurvivalvsAgeAtDx\">SurvivalvsAgeAtDx</a>");		
else
	out.write("SurvivalvsAgeAtDx");
	
out.write(" | ");

if(componentStr.equals("SurvivalvsAgeAtDx"))
	out.write("<a href=\"clinicalPlots.do?taskId="+key+"&comp=KarnofskyScorevsAgeAtDx\">KarnofskyScorevsAgeAtDx</a>");		
else
	out.write("KarnofskyScorevsAgeAtDx");
%>
<br/><br/>
</div>
<table>
	<tr>
		<td>	
		<graphing:ClinicalPlot taskId="<%=key%>" components="<%=componentStr%>" />
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
<script language="javascript" src="/rembrandt/js/box/lassoHelper.js"></script>

</body>
</html>
