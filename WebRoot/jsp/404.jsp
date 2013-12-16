<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<html>
<head><title>REMBRANDT - Repository for Molecular Brain Neoplasia Data (Error)</title>
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
<script language="javascript" src="<%=request.getContextPath()%>/js/caIntScript.js"></script>
</head>
<body>

<!--header NCI-->
<table align="center" width="765" border="0" cellspacing="0" cellpadding="0" bgcolor="#A90101">
<tr bgcolor="#A90101">
		<td width="283" height="37" align="left"><a href="http://www.cancer.gov"><img src="<%=request.getContextPath()%>/images/logotype.gif" width="283" height="37" border="0" alt="logo"></a></td>
		<td>&nbsp;</td>
		<td width="295" height="37" align="right"><a href="http://www.cancer.gov"><img src="<%=request.getContextPath()%>/images/tagline.gif" width="295" height="37" border="0" alt="Tag"></a></td>

</tr>
</table>
<!--header REMBRANDT image map-->
<div align="center" width="765px">
<div style="width:765px; border-bottom: 1px solid #000000; margin:0px;">
<map name="headerMap">
<area alt="REMBRANDT website" coords="7,8,272,50" href="login.do">
</map>
<img src="<%=request.getContextPath()%>/images/header.jpg" width="765" height="65" alt="REMBRANDT application logo" border="0" usemap="#headerMap">
</div>
<!--end all headers-->

<br clear="both"/><br clear="both"/>
<fieldset style="border: 1px solid #000066;width:700px">
<legend style="text-align:center;background-color:#ffffff">REMBRANDT Message</legend>
<p style="text-align:left">
<br clear="both"/>
  <strong>Our apologies, 
   this page cannot be found.</strong>
</p><br /><br /><br />
</fieldset>
<br /><br />


<!--begin footer-->
<div style="width:765; text-align:center; padding: 3px 0px 10px 0px; background-color:#D5E0E9">
    <a href="menu.do">HOME</a>  |  <a href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">SUPPORT</a>  |  <a href="http://ncicb.nci.nih.gov" target="_blank">NCICB HOME</a>
    <br /><span style="font-size:.8em;text-align:right;">Release <%=System.getProperty("rembrandt.application.version")!=null ? System.getProperty("rembrandt.application.version") : "1.5"%></span> </div>
<!--end footer-->

<!--begin NCI footer-->
<div>
<table width="765" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px">
<tr>
<td valign="top"><div align="center">
	<a href="http://www.cancer.gov/"><img src="<%=request.getContextPath()%>/images/footer_nci.gif" width="63" height="31" alt="National Cancer Institute" border="0"></a>
	<a href="http://www.dhhs.gov/"><img src="<%=request.getContextPath()%>/images/footer_hhs.gif" width="39" height="31" alt="Department of Health and Human Services" border="0"></a>

	<a href="http://www.nih.gov/"><img src="<%=request.getContextPath()%>/images/footer_nih.gif" width="46" height="31" alt="National Institutes of Health" border="0"></a>
	<a href="http://www.firstgov.gov/"><img src="<%=request.getContextPath()%>/images/footer_firstgov.gif" width="91" height="31" alt="FirstGov.gov" border="0"></a></div>
</td>
</tr>
</table>
<!--end NCI footer-->
</div>

</body>
</html>
