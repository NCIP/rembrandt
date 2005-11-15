<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*, gov.nih.nci.rembrandt.web.helper.*,
gov.nih.nci.rembrandt.web.factory.*, gov.nih.nci.rembrandt.web.bean.*, org.dom4j.Document, gov.nih.nci.rembrandt.util.*" %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.rembrandt.cache.*" %>

<html>
	<head>
		<title>Rembrandt Report</title>
		<script language="JavaScript" type="text/javascript" src="js/overlib.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
		<script language="JavaScript" type="text/javascript" src="XSL/js.js"></script> 
		<LINK href="XSL/css.css" rel="stylesheet" type="text/css" />
	</head>
<body>
	<div id="reportContainer" style="">&nbsp;</div>

<script type='text/javascript' src='/rembrandt/dwr/interface/DynamicReport.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>

<%

String key = "frb_test";
if(request.getParameter("key")!=null)
	key = (String) request.getParameter("key");

String xhtml = "nada";
if(session.getAttribute(key+"_xhtml")!=null)	{
	xhtml = (String) session.getAttribute(key+"_xhtml");
	out.println(xhtml);
	session.removeAttribute(key+"_xhtml");
}
else	{

	%>
	<div id="imgContainer" style="display:block">
		<center><img src="images/circleStatusGray200.gif" /></center>
	</div>

	<script language="javascript">
	
		var reportC = document.getElementById("reportContainer");
		var imgC = document.getElementById("imgContainer");
		
		function A_getReport(key)	{
			DynamicReport.generateDynamicReport(key, A_getReport_cb);
		}
		
		function A_getReport_cb(html)	{
			//html is nothing now
			imgC.style.display = "none";
			location.replace("/rembrandt/testReport.do?key=<%=key%>");
		}
		
		setTimeout("A_getReport('<%=key%>')", 0250);
		
	</script>

<% 
} 
%>
</body>
</html>

