<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld"  prefix="app" %>

<%
if(session.getAttribute("logged") == "yes")
{
//youre already logged in, why are you here?
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = "welcome.jsp";
response.setHeader("Location",newLocn);
}


%>

<html>
<head><title>REMBRANDT - Repository for Molecular Brain Neoplasia Data</title>
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
<script language="javascript" src="js/caIntScript.js"></script>
</head>
<body onload="document.forms[0].userName.focus();">
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
<area alt="REMBRANDT application logo" coords="7,8,272,50" href="login.do">
</map>
<img src="images/header.jpg" width="765" height="65" alt="REMBRANDT application logo" border="0" usemap="#headerMap">
</div>
<!--end all headers-->

<!--navigation bar-->
<div style="background-color:#D5E0E9; width:765px; padding:1px 0px 1px 0px;text-align:left">
<a style="font-size:.8em" href="menu.do">home</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="javascript:Help.popHelp('Welcome');">help</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="tutorials.jsp">tutorials</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/Rembrandt1.5_Users_Guide.pdf">user guide</a>&nbsp;&nbsp;&nbsp;
</div>
<!--end nav bar-->

<!--site description for readers-->
<div style="display:none">REMBRANDTs the REpository for Molecular BRAin Neoplasia DaTa - Empowering translational research for brain tumor studies.</div>

<!--banner graphic begins-->
<div style="background-image: url('images/bannerGraphic.jpg'); width:765; height:221"></div>
<!--banner graphic ends-->

<!--main content div with table for description and login-->
<div style="width:765px;">
      
  <!--content table--> 
  <table border="0" cellspacing="0" cellpadding="0" style="margin-top:2px; width:765">
	 <tr>
	  <!--begin description cell-->
	  <td style="width:462; margin-bottom:0px; border-right:1px solid #374554; border-bottom: 1px solid #000000; border-left: 1px solid #fC4C5C5;">
         <p style="font-size:1.2em; font-weight:bold;padding:0px 5px 0px 5px">
		 About this application	
		 <br /><span style="font-size:.7em;text-align:right;">Release 1.5</span>
		 </p>
		 
	
		 <p style="padding:0px 5px 0px 5px ; font-size:.9em;">REpository for Molecular BRAin 
		 Neoplasia DaTa (REMBRANDT) is a robust bioinformatics
		 knowledgebase framework that leverages data warehousing 
		 technology to host and integrate clinical and functional
		 genomics data from clinical trials involving patients 
		 suffering from Gliomas. The knowledge framework will 
		 provide researchers with the ability to perform ad hoc
		 querying and reporting across multiple data domains,
		 such as Gene Expression, Chromosomal aberrations and 
		 Clinical data. 
		 </p>
		 
	     <p style="margin-bottom:10px; padding:0px 5px 0px 5px;font-size:.9em;">Scientists
	     will be able to answer basic questions related to a patient
	     or patient population and view the integrated data sets in
	     a variety of contexts. Tools that link data to other
	     annotations such as cellular pathways, gene ontology
	     terms and genomic information will be embedded.</p>
	     
	     <!-- 
	     <p style="margin-bottom:10px; padding:0px 5px 0px 5px; font-size:.9em;">For optimal
	     performance, IE 6.0+ is recommended. 
	     -->
	     <p style="margin-bottom:10px; padding:0px 5px 0px 5px; font-size:.9em;">Please visit <a href="http://rembrandt.nci.nih.gov">http://rembrandt.nci.nih.gov</a>
	         for more information.</p>
	     
	     
	  </td>
	  <!--end description cell-->
	  
      <!--begin login cell-->
	  <td style="background-color:#D5E0E9;">
         <p style="font-size:1.2em; font-weight:bold; margin-left:0px;width:100%; background-color:#416599; color:#FFFFFF">
		 &nbsp;&nbsp;Login 
         </p>
		 
		 <!--begin login section-->
           <html:errors property="invalidLogin" />
           
	       <!--login form/table begins--> 
	       <html:form action="login.do">  
		   <table border="0" style="margin: 0px 0px 0px 60px;font-size:8pt">
	            <tr><Td>User Name:</td><td rowspan="5"><br /><img src="images/remLogo_10.gif" alt="REMBRANDT logo" /></td></tr>
	            <tr><td><html:text property="userName" /></td></tr>
	            <tr><Td>Password:</td></tr>
	            <tr><td><html:password property="password" /></td></tr>
	            <!--take out GI and data set until later date?
	             <tr><td>
	              Study Data Set
	              <br>
	              <select style="font-size:8pt" name="dataSet" onchange="javascript:changeList(this);">
	                <option>
	                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                </option>
	                <option>
	                  GMDI
	                </option>
	                <option>
	                  Other
	                </option>
	               </select>
	              </td></tr>
	            <tr><td colspan="2">
	              Generating Institution
	              <br>
	              <select style="font-size:8pt" name="generatingInstitution">
	                <option>
	                  All
	                </option>
	                <option>
	                  NCI
	                </option>
	                <option>
	                  Johns Hopkins University
	                </option>
	                <option>
	                  UCSF
	                </option>
	              </select>
	            </td>
	           </tr>-->
			  </table>
	          <span style="margin: 0px 0px 0px 65px">&nbsp;<html:submit/>&nbsp;&nbsp;<html:reset/></span>
	       </html:form>
	       <!--end login form-->
	       

	      
	      <div style="width:80%;margin-left:40px;">
	      
	      <ul>
	      <li><a style="font-size:.9em;" href="mailto:ncicb@pop.nci.nih.gov?subject=REMBRANDT: Request username/password">request username/password</a></li>
	      <li><span style="font-size:.9em;">provide us your <a style="font-size:.9em;" href="mailto:REMBRANDT_UAT_L@list.nih.gov?subject=REMBRANDT feedback">feedback</a></li>
		  <ul>
		 </div>
		 <div style="border:0px solid red; margin:5px;">
 			<script type="text/javascript">Help.insertHelp("Logging_in", "align='left'", "padding:8px;");</script>
			Throughout the application please click the Help Icon for context sensitive application help.<br/><br/>
		 	
		 </div>
	      <!--end login section-->
	   </td>
	   <!--end login cell-->
	  </tr>
	 </table>
	<!--end content table-->
	
</div>
<!--end content div-->

<!--begin footer-->
<div style="width:765; text-align:center; padding: 3px 0px 10px 0px; background-color:#D5E0E9">
    <a href="menu.do">HOME</a>  |  <a href="http://ncicbsupport.nci.nih.gov/sw/" target="_blank">SUPPORT</a>  |  <a href="http://ncicb.nci.nih.gov" target="_blank">NCICB HOME</a>
    <br /><span style="font-size:.8em;text-align:right;">Release 1.5</span> </div>
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
