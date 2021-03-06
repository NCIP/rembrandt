<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*, gov.nih.nci.rembrandt.web.helper.*,
gov.nih.nci.rembrandt.web.factory.*, gov.nih.nci.rembrandt.web.bean.*, org.dom4j.Document, gov.nih.nci.rembrandt.util.*" %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache" %>

<html>
	<head>
		<title>Rembrandt Report</title>
		<script language="JavaScript" type="text/javascript" src="js/overlib.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
		<script language="JavaScript" type="text/javascript" src="XSL/js.js"></script>
		<script language="JavaScript" type="text/javascript" src="XSL/a_js.js"></script>
		<script type="text/javascript" src="js/lib/Help.js"></script>
		<!-- 
		<script type="text/javascript" src="http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/utilities_2.1.0.js"></script>
		-->
		<script type="text/javascript" src="js/yui/utilities.js"></script>
		<script type="text/javascript" src="js/yui/yui-ext-nogrid.js"></script>
		<link rel="stylesheet" type="text/css" href="js/yui/resources/css/reset-min.css" />
    	<link rel="stylesheet" type="text/css" href="js/yui/resources/css/resizable.css"/>
		
		<script language="JavaScript" type="text/javascript" src="js/JSFX_ImageZoom.js"></script>  
		
		<script type="text/javascript">
		//This is to replace app:cshelp, which doesn't work for this page. Reason unknown
		function openHelp(){
  			window.open("https://wiki.nci.nih.gov/display/icrportals/5+Viewing+REMBRANDT+Results+v1.5.8#id-5ViewingREMBRANDTResultsv158-HierarchicalClusteringReport", "Help",  "status,scrollbars,resizable,width=800,height=500");
  		}
		</script>
		
		
	 	<LINK href="XSL/css.css" rel="stylesheet" type="text/css" />
	</head>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<span style="z-index:1000; float:right;">
	<!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	<a href="javascript:top.close()"><img alt="Close" align="right" src="images/close.png" border="0"></a>
	<!-- This doesn't work: app:cshelp topic="HCA_report" />-->
	<img align="right" onclick="javascript:openHelp()" name="helpIcon" id="helpIcon" alt="help" title="help" src="/rembrandt/images/help.png" style="cursor:pointer;border:0px;padding:2px;">
	<a href="#" onclick="javascript:window.print();" title="Print this report."><img alt="Print" align="right" src="images/print.png" border="0" /> </a> 	
</span>

<div style="background-color: #ffffff"><img alt="Rembrandt" src="images/smallHead.jpg" /></div>


<%
String key = "taskId";
if(request.getParameter("key")!=null)
	key = (String) request.getParameter("key");
%>

Image Control: Grab the blue border on the image and drag to resize 
<a href="#" onclick="fulls(); return false;">[full size]</a>
<a href="#" onclick="fulls('def'); return false;">[default size]</a>
<!-- 
<a href="#" onclick="fullsize()">fullsize</a> |
<a href="#" onclick="shrink()">small</a> 
-->

<br clear="all"/>
<div>
<graphing:HCPlot taskId="<%=key%>" />
</div>

<script language="javascript">
var rbt_image = document.getElementById("rbt_image");

function rbt_image_init()	{
	rbt_image.style.width = "100%";
}

function shrink()	{
	rbt_image_init();
}
function fullsize()	{
	rbt_image.removeAttribute("width");
	rbt_image.removeAttribute("height");
	rbt_image.style.width = "";
}

//init
//rbt_image_init();
</script>

<div style="height:300px;overflow:auto;">
<graphing:HCPlotReport taskId="<%=key%>" />
</div>
<script type="text/javascript">
/*
var t = rbt_image.src;
t = t.replace(/\\/g,"/");
*/
//scaling
//var ow = rbt_image.width;
//var oh = rbt_image.height;
var ow = rbt_image.getAttribute("width");
var oh = rbt_image.getAttribute("height");

var w = rbt_image.getAttribute("width");
var h = rbt_image.getAttribute("height");

var newWidth = 600;
var newHeight = 100;

if(ow > newWidth)	{
	var ratio = (ow/oh);
	//if the ratio is > 10, resize via fixed height
	if(ratio < 10)	{
		w = newWidth;
		h = Math.round((newWidth / ratio));
	}
	else	{
		h = newHeight;
		w = Math.round((newHeight * ratio)); //other way in case	
	}
	rbt_image.height = h;
	rbt_image.width = w;
}
	var wrapped = new YAHOO.ext.Resizable('rbt_image', {
    wrap:true,
    pinned:true,
    minWidth:50,
    minHeight:50,
    preserveRatio:true
	});
	
	
function fulls(f)	{
	if(f=='def')	{
		wrapped.resizeTo(w, h);
	}
	else	{
		wrapped.resizeTo(ow, oh);
	}
	
}
</script>
</body>
</html>

