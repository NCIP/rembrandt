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
<%@ page import="gov.nih.nci.rembrandt.cache.*" %>

<html>
	<head>
		<title>Rembrandt Report</title>
		<script language="JavaScript" type="text/javascript" src="js/overlib.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
		<script language="JavaScript" type="text/javascript" src="XSL/js.js"></script>
		<script language="JavaScript" type="text/javascript" src="XSL/a_js.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/JSFX_ImageZoom.js"></script>  
		<LINK href="XSL/css.css" rel="stylesheet" type="text/css" />
		
		


		
	</head>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<span style="z-index:1000; float:right;">
	<!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	<a href="javascript:top.close()"><img align="right" src="images/close.png" border="0"></a>
	<a href="javascript: spawn('help.jsp?sect=hcPlot', 350, 500);"><img align="right" src="images/help.png" border="0" onmouseover="return overlib('Click here for additional information about this report.', CAPTION, 'Help');" onmouseout="return nd();" /></a>
	<a href="#" onclick="javascript:window.print();"><img align="right" src="images/print.png" border="0" onmouseover="return overlib('Print this report.', CAPTION, 'Help');" onmouseout="return nd();"/> </a> 	
</span>

<div style="background-color: #ffffff"><img src="images/smallHead.jpg" /></div>


<%
String key = "taskId";
if(request.getParameter("key")!=null)
	key = (String) request.getParameter("key");
%>
Image Control: 
<a href="#" onclick="fullsize()">fullsize</a> |
<a href="#" onclick="shrink()">small</a> 
<!-- 
<a href="#" onmouseover="grow()" onmouseout="stop()">grow</a> |
<a href="#" onmouseover="small()" onmouseout="stop()">shrink</a> |
<a href="#" onclick="stop()">stop</a> 
-->
<br clear="all"/>
<graphing:HCPlot taskId="<%=key%>" />

<script language="javascript">
var i = document.getElementById("rbt_image");


function init()	{
	i.style.width = "100%";
}

function shrink()	{
	//i.setAttribute("height", "200");
	//i.setAttribute("width", "500");
	init();
}
function fullsize()	{
	i.removeAttribute("width");
	i.removeAttribute("height");
	i.style.width = "";
}

var step = 200;
var gr = "";

function grow()	{
	gr = setInterval("growIt()", 100);
}
function small()	{
	gr = setInterval("smallIt()", 100);
}

function stop()	{
	clearInterval(gr);
}
function growIt()	{
	i.setAttribute("height", i.height+step);
	i.setAttribute("width", i.width+step);	
}
function smallIt()	{
	if(i.height-step > 0 && i.width-step > 0)	{
		i.setAttribute("height", i.height-step);
		i.setAttribute("width", i.width-step);	
	}
}

//init
init();

//i.style.border = "1px solid red";
//i.style.width = "100%";
</script>


<div style="height:300px; overflow:auto;">
<graphing:HCPlotReport taskId="<%=key%>" />
</div>
</body>
</html>

