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
<table align="center" width="765" border="0" cellspacing="0" cellpadding="0" bgcolor="#A90101">
<tr bgcolor="#A90101">
		<td width="283" height="37" align="left"><a href="http://www.cancer.gov"><img src="images/logotype.gif" width="283" height="37" border="0"></a></td>
		<td>&nbsp;</td>
		<td width="295" height="37" align="right"><a href="http://www.cancer.gov"><img src="images/tagline.gif" width="295" height="37" border="0"></a></td>

</tr>
</table>
<!--header REMBRANDT image map-->
<div align="center" width="765px">
<div style="width:765px; border-bottom: 1px solid #000000; margin:0px;">
<map name="headerMap">
<area alt="REMBRANDT website" coords="7,8,272,50" href="http://rembrandt.nci.nih.gov">
</map>
<img src="images/header.jpg" width="765" height="65" alt="REMBRANDT application logo" border="0" usemap="#headerMap">
</div>
<!--end all headers-->

<!--navigation bar-->
<div style="background-color:#D5E0E9; width:765px; padding:1px 0px 1px 0px;text-align:left">
<a style="font-size:.8em" href="menu.do">home</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="#">contact</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="javascript:spawn('help.jsp', 350, 500);">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="tutorials.jsp">tutorials</a>&nbsp;&nbsp;&nbsp;
</div>
<!--end nav bar-->


<!--main content div with table for description and login-->
<div style="width:765px; text-align:left;">
<Br><br>
<span><h3>Online Tutorials</h3></span>
<fieldset>
 <legend style="padding:3px;background-color:#D4D0C8; font-size:12px">1. Gene Expression Plot</legend>
<br>
	<a href="javascript:spawnm('tutorials/qs_gplot.htm');">View</a>* the Gene Expression Plot tutorial in your browser (opens in a new window) 
	<!--  or <a href="tutorials/qs_gplot.exe">download</a> the tutorial for offline viewing. -->
</fieldset>
 <Br><br>
 <fieldset>
  <legend style="padding:3px;background-color:#D4D0C8; font-size:12px">2. Kaplan-Meier Survival Plot</legend>
  <br>
	<a href="javascript:spawnm('tutorials/qs_kmplot.htm');">View</a>* the Kaplan-Meier Plot tutorial in your browser (opens in a new window) 
	<!--  or <a href="tutorials/qs_kmplot.exe">download</a> the tutorial for offline viewing (.exe file). -->
 </fieldset>
 <br><br>
  <fieldset>
  <legend style="padding:3px;background-color:#D4D0C8; font-size:12px">3. Advanced Search (Gene Expression)</legend>
  <br>
	<a href="javascript:spawnm('tutorials/advanced_search_1.htm');">View</a>* the Advanced Search tutorial in your browser (opens in a new window) 
	<!--  or <a href="tutorials/advanced_search_1.exe">download</a> the tutorial for offline viewing (.exe file). -->
 </fieldset>
 <br><br>
  <fieldset>
  <legend style="padding:3px;background-color:#D4D0C8; font-size:12px">4. Advanced Search (Copy Number)</legend>
  <br>
	<a href="javascript:spawnm('tutorials/advanced_search_2.htm');">View</a>* the Advanced Search tutorial in your browser (opens in a new window) 
	<!--  or <a href="tutorials/advanced_search_2.exe">download</a> the tutorial for offline viewing (.exe file). -->
 </fieldset>
 <br><br>
 
  <fieldset>
  <legend style="padding:3px;background-color:#D4D0C8; font-size:12px">5. High Order Analysis (PCA)</legend>
  <br>
	<a href="javascript:spawnm('tutorials/pca.htm');">View</a>* the Advanced Search tutorial in your browser (opens in a new window) 
	<!--  or <a href="tutorials/pca.exe">download</a> the tutorial for offline viewing (.exe file). -->
 </fieldset>
 <br><br>
 
  <fieldset>
  <legend style="padding:3px;background-color:#D4D0C8; font-size:12px">6. High Order Analysis (Hierarchical Clustering)</legend>
  <br>
	<a href="javascript:spawnm('tutorials/hc.htm');">View</a>* the Advanced Search tutorial in your browser (opens in a new window) 
	<!--  or <a href="tutorials/hc.exe">download</a> the tutorial for offline viewing (.exe file). -->
 </fieldset>
 <br><br>
 
  <fieldset>
  <legend style="padding:3px;background-color:#D4D0C8; font-size:12px">7. Advanced Search/High Order Analysis (Class Comparison)</legend>
  <br>
	<a href="javascript:spawnm('tutorials/ccX.htm');">View</a>* the Advanced Search tutorial in your browser (opens in a new window) 
	<!--  or <a href="tutorials/ccX.exe">download</a> the tutorial for offline viewing (.exe file). -->
 </fieldset>
 <br><br>
 
  <fieldset>
  <legend style="padding:3px;background-color:#D4D0C8; font-size:12px">8. Web Genome Integration (Copy Number)</legend>
  <br>
	<a href="javascript:spawnm('tutorials/webgenome.htm');">View</a>* the Advanced Search tutorial in your browser (opens in a new window) 
	<!--  or <a href="tutorials/webgenome.exe">download</a> the tutorial for offline viewing (.exe file). -->
 </fieldset>
 <br><br>
 
 <fieldset>
 <legend style="padding:0px;background-color:#ffffff;">*Requirements</legend>
 <br>
 <a href="http://www.macromedia.com/go/getflashplayer/" target="_blank"><img align="right" src="images/get_flashplayer.gif" border="0"></a>
 A monitor resolution of greater than 800x600 is highly recommended.  To view the online tutorials you may need to install the Flash Plug-in for your browser.  Please click the
 "Flash Player" logo to go to the Macromedia website to download this free plug-in.<br>
 </fieldset>
 <br>
</div>
<!--end content div-->

<!--begin footer-->
<div style="width:765; text-align:center; padding: 3px 0px 10px 0px; background-color:#D5E0E9">
    <a href="menu.do">HOME</a>  |  <a href="#">CONTACT</a>  |  <a href="javascript:spawn('help.jsp', 350, 500);">SUPPORT</a>  |  <a href="http://ncicb.nci.nih.gov" target="_blank">NCICB HOME</a>
</div>
<!--end footer-->

<!--begin NCI footer-->
<table width="765" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px">
<tr>
<td valign="top"><div align="center">
	<a href="http://www.cancer.gov/"><img src="images/footer_nci.gif" width="63" height="31" alt="National Cancer Institute" border="0"></a>
	<a href="http://www.dhhs.gov/"><img src="images/footer_hhs.gif" width="39" height="31" alt="Department of Health and Human Services" border="0"></a>

	<a href="http://www.nih.gov/"><img src="images/footer_nih.gif" width="46" height="31" alt="National Institutes of Health" border="0"></a>
	<a href="http://www.firstgov.gov/"><img src="images/footer_firstgov.gif" width="91" height="31" alt="FirstGov.gov" border="0"></a></div>
</td>
</tr>
</table>
<!--end NCI footer-->
</div>

</body>
</html>
