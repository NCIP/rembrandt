<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<html>
<head><title>REMBRANDT - Repository for Molecular Brain Neoplasia Data</title>
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
<script language="javascript" src="js/caIntScript.js"></script>
<script language="javascript">
	function spawnf(url,winw,winh) {
  		var w = window.open(url, "_blank",
      	"screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      	",scrollbars=yes,resizable=yes, fullscreen=yes");
	}
	
	function spawnm(url)	{
		var winh = screen.height;
		var winw = screen.width; 
		var w = window.open(url, "_blank",
      	"screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      	",scrollbars=yes,resizable=yes,screenX=0,screenY=0,left=0,top=0");
	}
</script>
</head>
<body>
<!--header NCI-->
<%@ include file="/jsp/header.jsp" %>
<!--end all headers-->

<!--navigation bar-->
<div style="background-color:#D5E0E9; width:765px; padding:1px 0px 1px 0px;text-align:left">
<%@ include file="/jsp/tiles/crumbMenu_tile.jsp" %></div>
<!--end nav bar-->


<!--main content div with table for description and login-->
<div style="width:765px; text-align:left;">
<br/>
<span><h3>Online Tutorials</h3></span>
<fieldset>
	<legend>Simple Search</legend><br clear="both"/>
	<li>
		<b>Gene Expression Plot</b>
		<a title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/Gene_search.htm');">View</a>* the Gene Expression Plot tutorial describes the Median, Geometric Mean, Log2 Intensity and Box and Whisker plots. (opens in a new window) 
	</li>
	<li>
		<b>Kaplan-Meier Survival Plot</b>
		<a  title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/RBT_1_6_km.htm');">View</a>* the Kaplan-Meier Plot tutorial  (opens in a new window) 
	</li>

</fieldset>
<br/><br/>

<fieldset>
	<legend>Advanced Search</legend><br clear="both"/>
	<li>
		<b>Advanced Search (Gene Expression)</b>
		<a  title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/advanced_search_1.htm');">View</a>* the tutorial  (opens in a new window) 
	</li>
	<li>
		<b>Advanced Search (Copy Number)</b>
		<a  title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/advanced_search_2.htm');">View</a>* the tutorial  (opens in a new window) 
	</li>
</fieldset>
<br/><br/>

<fieldset>
 	<legend>High Order Analysis</legend><br clear="both"/>
	<li>
		<b>High Order Analysis (PCA)</b>
		<a  title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/pca.htm');">View</a>* the tutorial  (opens in a new window) 
	</li>
	<li>
		<b>High Order Analysis (Hierarchical Clustering)</b>
		<a  title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/hc.htm');">View</a>* the tutorial  (opens in a new window) 
	</li>
	<li>
		<b>Advanced Search/High Order Analysis (Class Comparison)</b>
		<a  title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/ccX.htm');">View</a>* the tutorial  (opens in a new window) 
	</li>
	<li>
		<b>F-Test (Class Comparison)</b>
		<a  title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/ftest.htm');">View</a>* the tutorial  (opens in a new window) 
	</li>
</fieldset>
<br/><br/>

<fieldset>
	<legend>List Management</legend><br clear="both"/>
	<li>
		<b>List Management</b>
		<a  title="This page shows a SWF Flash file." href="javascript:spawnm('tutorials/manage_lists2.htm');">View</a>* the tutorial  (opens in a new window) 
	</li>
</fieldset>
<br/><br/>
 
<fieldset>
	<legend>*Requirements</legend>
	<br/>
	<a href="http://www.macromedia.com/go/getflashplayer/" target="_blank"><img align="right" alt="Get Flash player" src="images/get_flashplayer.gif" border="0"></a>
	A monitor resolution of greater than 800x600 is highly recommended.  To view the online tutorials you may need to install the Flash Plug-in for your browser.  Please click the
	"Flash Player" logo to go to the Macromedia website to download this free plug-in.<br>
</fieldset>
<br/>
</div>
<!--end content div-->

<!--begin footer-->
<div style="width:765; text-align:center; padding: 3px 0px 10px 0px; background-color:#D5E0E9">
    <a href="menu.do">HOME</a>  |  <a href="http://ncicbsupport.nci.nih.gov/sw/" target="_blank">SUPPORT</a>  |  <a href="http://ncicb.nci.nih.gov" target="_blank">NCICB HOME</a>
    <br /><span style="font-size:.8em;text-align:right;">Release <%=System.getProperty("rembrandt.application.version")!=null ? System.getProperty("rembrandt.application.version") : "1.5"%></span> 
</div>
<!--end footer-->

<!--begin NCI footer-->
<%@ include file="/jsp/footer.jsp" %>
<!--end NCI footer-->
</div>

</body>
</html>
