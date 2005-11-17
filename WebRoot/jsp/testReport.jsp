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
		<script language="JavaScript" type="text/javascript" src="XSL/a_js.js"></script> 
		<LINK href="XSL/css.css" rel="stylesheet" type="text/css" />
	</head>
<body>

<script type='text/javascript' src='/rembrandt/dwr/interface/DynamicReport.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/util.js'></script>

<%

String key = "t2";
if(request.getParameter("key")!=null)
	key = (String) request.getParameter("key");

String p_highlight = "";
if(request.getParameter("p_highlight")!=null)
	p_highlight = (String) request.getParameter("p_highlight");
String p_highlight_op = "gt";
if(request.getParameter("p_highlight_op")!=null)
	p_highlight_op = (String) request.getParameter("p_highlight_op");

String p_page = "0";
if(request.getParameter("p_page")!=null)
	p_page = (String) request.getParameter("p_page");
String p_step = "25";
if(request.getParameter("p_step")!=null)
	p_step = (String) request.getParameter("p_step");


String xhtml = "nada";
if(session.getAttribute(key+"_xhtml")!=null)	{
	xhtml = (String) session.getAttribute(key+"_xhtml");
	out.println(xhtml);
	session.removeAttribute(key+"_xhtml");
}
else	{

	%>
	<div id="imgContainer" style="display:block; position:absolute;">
		<center><img src="images/circleStatusGray200.gif" /></center>
	</div>
	<script language="javascript">
		
		var reportC = document.getElementById("reportContainer");
		var imgC = document.getElementById("imgContainer");
		//center the img
		imgC.style.top = document.body.clientHeight/2-100;
		imgC.style.left = document.body.clientWidth/2-100;
		
		function A_getReport(key)	{
			var a = new Object();
			a["key"] = "<%=key%>";
			a["p_highlight"] = "<%=p_highlight%>";
			a["p_highlight_op"] = "<%=p_highlight_op%>";
			
			a["p_page"] = "<%=p_page%>";
			a["p_step"] = "<%=p_step%>";
			
			//a["two"] = "atwo";
			//var a = { key1:"value1", key2:"value2" };
			DynamicReport.generateDynamicReport(key, a, A_getReport_cb);
		}
		
		function A_getReport_cb(html)	{
			//html is nothing now
			imgC.style.display = "none";
			location.replace(window.location);
			//location.replace("/rembrandt/testReport.do?key=<%=key%>");
		}
		
		setTimeout("A_getReport('<%=key%>')", 0250);
		
	</script>

<% 
} 
%>
</body>
</html>

