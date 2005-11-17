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

<script type='text/javascript' src='/rembrandt/dwr/interface/DynamicReport.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/util.js'></script>

<%

String key = "t2";
if(request.getParameter("key")!=null)
	key = (String) request.getParameter("key");

String filter_value1 = "";
if(request.getParameter("filter_value1")!=null)
	filter_value1 = (String) request.getParameter("filter_value1");
String filter_value4 = "gt";
if(request.getParameter("filter_value4")!=null)
	filter_value4 = (String) request.getParameter("filter_value4");

String filter_value2 = "0";
if(request.getParameter("filter_value2")!=null)
	filter_value2 = (String) request.getParameter("filter_value2");
String filter_value3 = "25";
if(request.getParameter("filter_value3")!=null)
	filter_value3 = (String) request.getParameter("filter_value3");


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
			a["filter_value1"] = "<%=filter_value1%>";
			a["filter_value4"] = "<%=filter_value4%>";
			
			a["filter_value2"] = "<%=filter_value2%>";
			a["filter_value3"] = "<%=filter_value3%>";
			
			a["two"] = "atwo";
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

