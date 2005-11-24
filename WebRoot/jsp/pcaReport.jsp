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
		<LINK href="css/tabs.css" rel="stylesheet" type="text/css" />
		
		<script language="JavaScript" src="/rembrandt/js/box/browserSniff.js"></script>
		<script language="Javascript">
	    //some global vars for the  yellow marker
		var marker, markersrc, markersize;
		var coordx   = new Array();
		var coordy   = new Array();
	  
		  if (ie) {
			markersrc = "/rembrandt/images/marker.gif";
		  } else {
			markersrc = "/rembrandt/images/marker.png";
		  }
		  
		   markersize = 32;
	    </script>
		<script language="JavaScript" src="/rembrandt/js/box/x_core.js"></script>
    	<script language="JavaScript" src="/rembrandt/js/box/x_event.js"></script>
    	<script language="JavaScript" src="/rembrandt/js/box/x_dom.js"></script>
    	<script language="JavaScript" src="/rembrandt/js/box/x_drag.js"></script>
    	<script language="JavaScript" src="/rembrandt/js/box/wz_jsgraphics.js"></script>
    
		<script language="JavaScript" src="/rembrandt/js/box/dbox.js"></script>
		<script type="text/javascript" src="/rembrandt/js/overlib.js"><!-- overLIB (c) Erik Bosrup --></script>

<script language="javascript">
		//saved sample management - incorporate AJAX here
//hold our pending samples
var pendingSamples = new Array();
		
function addToPending(sample)	{
	if(!pendingSamples.inArray(sample))	{
		pendingSamples[pendingSamples.length] = sample;
	}
}		
function clearPending()	{
	pendingSamples = new Array();
	writePendings();
}		
function writePendings()	{
	var html = "";	
	document.getElementById("pending_samples").innerHTML = "";
	if(pendingSamples.length>0)	{
		for(var j=0; j<pendingSamples.length; j++)	{
				html += "<a href=\"#\" onmouseover=\"mapshow('"+pendingSamples[j]+"');return overlib('Some cool annotations about:<br>\\n "+pendingSamples[j]+"');\" onmouseout=\"maphide();return nd();\">"+ pendingSamples[j] + "</a><br/>\n";
		}
	}
	//html = pendingSamples;	
	document.getElementById("pending_samples").innerHTML = html;
}
		
		
/* http://www.embimedia.com/resources/labs/js-inarray.html */
Array.prototype.inArray = function (value)	{
	// Returns true if the passed value is found in the
	// array.  Returns false if it is not.
	var i;
	for (i=0; i < this.length; i++) {
		// Matches identical (===), not just similar (==).
		if (this[i] == value) {
			return true;
		}
	}
	return false;
};

//Create the dBox object (initialize a few things)
var main = new dBox("geneChart");
main.box = true;
main.color = "red";
main.verbose = true;
		
//implement some methods	
function setbox_handler(name, minx, miny, maxx, maxy) {
	// document.mapserv.imgbox.value = minx + " " + miny + " " + maxx + " " + maxy;
	// document.mapserv.imgxy.value = minx + " " + miny;
	// document.mapserv.submit();

	//if(minx != maxx && miny != maxy)
		//alert("Your box: " + minx +", "+miny + ", "+maxx+", "+maxy);
   		//alert(name); //which lasso, since we now have 3
  
	didSelectAnything(name, minx, miny, maxx, maxy);
}

function seterror_handler(message) {
   alert(message);
}

function reset_handler(name, minx, miny, maxx, maxy) { 
	//some thing goes here      
}

		function startup() {
   			//Initialize the dBox object
   			main.initialize();
 		}
</script>
		
		
		
	</head>
<body>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<%
String key = "taskId";
if(request.getParameter("key")!=null)
	key = (String) request.getParameter("key");
	
String pcaView = "PC1vsPC2"; // | PC1vsPC3 | PC2vsPC3
if(request.getParameter("pcaView")!=null)
	pcaView = (String) request.getParameter("pcaView");
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
<div id="main">

<% if(pcaView.equals("PC1vsPC2"))	{ %>
<graphing:PCAPlot taskId="<%=key%>" components="PC1vsPC2" colorBy="Gender" />
<% } %>
<% if(pcaView.equals("PC1vsPC3"))	{ %>
<p><graphing:PCAPlot taskId="<%=key%>" components="PC1vsPC3" /></p>
<% } %>
<% if(pcaView.equals("PC2vsPC3"))	{ %>
<p><graphing:PCAPlot taskId="<%=key%>" components="PC2vsPC3" /></p>
<% } %>

</div>

<br/>
<div style="border:1px solid red; width:200px;height:200px; margin-left:10px;overflow:auto;" id="sample_list">
	Samples:<br/>
	<span id="pending_samples"></span>
</div>
<br/>
<div style="margin-left:10px;">
	<input type="button" value="clear" onclick="javascript: if(confirm('clear samples?')) { clearPending(); } "/><br/>
</div>

<script language="javascript">
	
	var DEBUG = true;
	
	function debug(txt)	{
		if(DEBUG)
			alert(txt);
	}
	
	function didSelectAnything(name, minx, miny, maxx, maxy)	{
	
		var gotem = "";
		
		//get the map
		var theMap = document.getElementsByTagName("map");
		//alert(theMap[0].name);
		
		//get the areas
		var theAreas = theMap[0].getElementsByTagName("area");

		//var theAreas = theMap[mapMap[name]].getElementsByTagName("area");
		/*
		for(var i=0; i<theMap.length; i++)	{
			alert(theMap[i].name + " = " + name);
			if(theMap[i].name == name)
				theAreas = theMap[i].getElementsByTagName("area");
		}
		*/
		//look at each area
		for(var i=0; i<theAreas.length; i++)	{
			//parse the coords
			var _minx, _miny, _maxx, _maxy;
			var s = theAreas[i].coords.replace(" ", "").split(",");
			//so there could be N coords, unless we force Jfreechart to use a square
			
			//debug("_"+s[0]+"_"); //look at the 1st coord for testing
			
			if(s[0] >= minx && s[1] >= miny && s[2] <= maxx && s[3] <= maxy)	{
				//we have lassoed this point
				gotem += theAreas[i].title + "\n";
				//alert("lasso has: " + theAreas[i].title);
				
				//RCL - write this to the pending list
				//addToPending(theAreas[i].title);
				addToPending(theAreas[i].id);
			}
			//alert(theAreas[i].coords);
		}
		if(gotem != "")	{
			alert("lasso has: \n" + gotem);
			writePendings();
		}
	
	}
	
	var mapNames = new Array();
	var mapMap = new Object();
	
	function getSetMapNames()	{
		var theMap = document.getElementsByTagName("map");
		for(var i=0; i<theMap.length; i++)	{
			mapNames[i] = theMap[i].name;
		}
		
		var imgs = document.getElementsByTagName("img");
		for(var i=0; i<imgs.length; i++)	{
			//alert(imgs[i].useMap);
			if(imgs[i].useMap != '')	{
				//alert(imgs[i].name + " => " + imgs[i].useMap.substring(1, imgs[i].useMap.length));
				mapMap[imgs[i].name] = imgs[i].useMap.substring(1, imgs[i].useMap.length);
			}
		}
		
		//alert(mapNames);
		
		/*
		var t_tmp = "";
		alert(mapMap.length);
		for(var key in mapMap)	{
		 t_tmp += "\n" + key +" => " + mapMap[key] + "\n";
		}
		 
		document.write(t_tmp);
		*/
		
	}
	
	
	//MAPMAP

</script>

<MAP NAME="chart_test">
		<AREA SHAPE="RECT" COORDS="55,161,61,167" href="javascript:void(0);" onmouseover="return overlib('3M - 5.09878');" onmouseout="return nd();" title="3M - 5.09878">
		<AREA SHAPE="RECT" COORDS="66,159,72,165" href="curvepoint_details.html?series=0&tenor=6M" title="6M - 5.15383">
		<AREA SHAPE="RECT" COORDS="87,160,93,166" href="curvepoint_details.html?series=0&tenor=1Y" title="1Y - 5.11204">
		<AREA SHAPE="RECT" COORDS="129,128,135,134" href="curvepoint_details.html?series=0&tenor=2Y" title="2Y - 5.92148">
		<AREA SHAPE="RECT" COORDS="171,98,177,104" href="curvepoint_details.html?series=0&tenor=3Y" title="3Y - 6.67381">
		<AREA SHAPE="RECT" COORDS="213,81,219,87" href="curvepoint_details.html?series=0&tenor=4Y" title="4Y - 7.11233">
		<AREA SHAPE="RECT" COORDS="255,63,261,69" href="curvepoint_details.html?series=0&tenor=5Y" title="5Y - 7.55392">

		<AREA SHAPE="RECT" COORDS="297,68,303,74" href="curvepoint_details.html?series=0&tenor=6Y" title="6Y - 7.43833">
		<AREA SHAPE="RECT" COORDS="340,72,346,78" href="curvepoint_details.html?series=0&tenor=7Y" title="7Y - 7.32241">
		<AREA SHAPE="RECT" COORDS="382,64,388,70" href="curvepoint_details.html?series=0&tenor=8Y" title="8Y - 7.52373">
		<AREA SHAPE="RECT" COORDS="424,50,430,56" href="curvepoint_details.html?series=0&tenor=9Y" title="9Y - 7.87864">
		<AREA SHAPE="RECT" COORDS="466,41,472,47" href="curvepoint_details.html?series=0&tenor=10Y" title="10Y - 8.12310">
		
</MAP>
		
<script language="javascript">

	function initMarkerPoints()	{
		//get the map
		var theMap = document.getElementsByTagName("map");
		
		//get the areas
		var theAreas = theMap[0].getElementsByTagName("area");
		//look at each area
		for(var i=0; i<theAreas.length; i++)	{
			//parse the coords
			var _minx, _miny, _maxx, _maxy;
			var s = theAreas[i].coords.replace(" ", "").split(",");
			//so there could be N coords, unless we force Jfreechart to use a square
			
			//convert to ints for comparisons
			for(var eachs = 0; eachs<s.length; eachs++)	{
				s[eachs] = parseInt(s[eachs]);
			}
			
			//get the center point
			//coordx[theAreas[i].title] = Math.ceil(s[0] + ((s[2]-s[0])/2));
			//coordy[theAreas[i].title] = Math.ceil(s[1] + ((s[3]-s[1])/2));
			coordx[theAreas[i].id] = Math.ceil(s[0] + ((s[2]-s[0])/2));
			coordy[theAreas[i].id] = Math.ceil(s[1] + ((s[3]-s[1])/2));
		}
	}
	
	initMarkerPoints();

function maphide () {
	var _marker = document.getElementById("the_marker");
    _marker.style.display = "none";
  }
  
  function mapshow (city) {
  main.reset();
    var offset = 0 - (markersize/2);
    var x = coordx[city] + offset;
    var y = coordy[city] + offset;
    var _marker = document.getElementById("the_marker");
    _marker.style.left = x + "px";
    _marker.style.top = y + "px";
    _marker.style.display = "block";
  }
  
  
  //RCL - must call this after <MAP>'s have been rendered
  //getSetMapNames();
  
  startup();
</script>


</body>
</html>

