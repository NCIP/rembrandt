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
		<title>Rembrandt PCA Plots</title>
		<LINK href="css/tabs.css" rel="stylesheet" type="text/css" />
		
		<script language="JavaScript" src="/rembrandt/js/box/browserSniff.js"></script>
		<script language="Javascript">
		    //some global vars for the  yellow marker
			var marker, markersrc, markersize;
			var coordx   = new Array();
			var coordy   = new Array();
	  
			if (ie) {
				markersrc = "/rembrandt/images/marker.gif";
			} else if(saf) {
				markersrc = "/rembrandt/images/blank.gif";
			} else {
				markersrc = "/rembrandt/images/marker.png";
			}
			
			markersize = 32;
	    </script>
	    
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

<script language="javascript">
		//saved sample management - incorporate AJAX here
//hold our pending samples
var pendingSamples = new Array();
		
function addToPending(sample)	{
	if((saf && !safInArray(pendingSamples, sample)) || !pendingSamples.inArray(sample))	{
		//add this to the JS array
		pendingSamples[pendingSamples.length] = sample;
		//add to array list
		A_saveTmpSample(sample);
	}
}		
function clearPending()	{
	//clear the JS array
	pendingSamples = new Array();
	//clear the array list and a_js arrays
	A_clearTmpSamples();
	writePendings();
}		

function writePendings()	{
	var html = "";	
	document.getElementById("pending_samples").innerHTML = "";
	if(pendingSamples.length>0)	{
		for(var j=0; j<pendingSamples.length; j++)	{
				html += "<span style=\"margin-left:5px;\">\n";
				html += "<a href=\"#\" onmouseover=\"mapshow('"+pendingSamples[j]+"');return overlib('Sample:<br>\\n "+pendingSamples[j]+"');\" onmouseout=\"maphide();return nd();\">"+ pendingSamples[j] + "</a><br/>\n";
				html += "</span>";
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

//saf doesnt like the above inArray
function safInArray(ar, value)	{
	var i;
	for (i=0; i < ar.length; i++) {
		// Matches identical (===), not just similar (==).
		if (ar[i] == value) {
			return true;
		}
	}
	return false;
}

//Create the dBox object (initialize a few things)
var main = new dBox("geneChart");
main.box = true;
main.color = "red";
main.verbose = true;
//main.thickness = Stroke.DOTTED;
		
//implement some methods	
function setbox_handler(name, minx, miny, maxx, maxy) {  
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

<span style="z-index:1000; float:right;"><a href="javascript:top.close()"><img src="images/close.png" border="0"></a></span>
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
if(colorBy.equals("Gender"))
	out.write("<a href=\"pcaReport.do?key="+key+"&pcaView="+pcaView+"&colorBy=Disease\">Disease</a>");		
else
	out.write("Disease");
	
out.write(" | ");

if(colorBy.equals("Disease"))
	out.write("<a href=\"pcaReport.do?key="+key+"&pcaView="+pcaView+"&colorBy=Gender\">Gender</a>");		
else
	out.write("Gender");
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

<script language="javascript">
	
	var DEBUG = true;
	
	function debug(txt)	{
		if(DEBUG)
			alert(txt);
	}
	
	function didSelectAnything(name, minx, miny, maxx, maxy)	{
	
		//alert(minx + ", " + miny + ", " +maxx + ", " +maxy );

		var gotem = "";
		
		//get the map
		var theMap = document.getElementsByTagName("map");
				
		//get the areas
		var theAreas = theMap[0].getElementsByTagName("area");

		//alert(theAreas[0].coords.replace(" ", "").split(","));
		//alert(minx + ", " + miny + ", " +maxx + ", " +maxy );
		
		//look at each area
		for(var i=0; i<theAreas.length; i++)	{
			//parse the coords
			var _minx, _miny, _maxx, _maxy;
			var s = theAreas[i].coords.replace(" ", "").split(",");
			//so there could be N coords, unless we force Jfreechart to use a square
			
			if(s[0] >= minx && s[1] >= miny && s[2] <= maxx && s[3] <= maxy)	{
			
				//we have lassoed this point
				gotem += theAreas[i].title + "\n";
				
				//RCL - write this to the pending list
				//addToPending(theAreas[i].title);
				addToPending(theAreas[i].id);
			}
			//alert(theAreas[i].coords);
		}
		if(gotem != "")	{
			//alert("lasso has: \n" + gotem);
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
			if(imgs[i].useMap != '')	{
				mapMap[imgs[i].name] = imgs[i].useMap.substring(1, imgs[i].useMap.length);
			}
		}		
	}
	
	
	//MAPMAP

</script>
		
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
			
			//get the center point...based on id now not title
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
  A_initSaveSample();
</script>


</body>
</html>

