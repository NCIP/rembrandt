<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>

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

</head>
<body>
<!--header image map-->
<div style="width:765px; border-bottom: 1px solid #000000; margin:0px;">
  <img src="images/header.jpg" width="765" height="102" border="0" alt="National Cancer Institute" usemap="#headerMap"><map name="headerMap">
  <area alt="NCI Logo" coords="3,1,284,36" href="http://www.cancer.gov">
  <area alt="cancer.gov website" coords="650,12,741,32" href="http://www.cancer.gov">
  <area alt="REMBRANDT logo" coords="6,46,270,87" href="http://rembrandt.nci.nih.gov">
  </map>
</div>
<!--end header-->

<!--navigation bar-->
<div style="background-color:#D5E0E9; width:765; padding:1px 0px 1px 0px;"><a style="font-size:.8em" href="#">home</a>&nbsp;&nbsp;&nbsp;<a style="font-size:.8em" href="#">contact</a>&nbsp;&nbsp;&nbsp;<a style="font-size:.8em" href="#">support</a>&nbsp;&nbsp;&nbsp;</div>
<!--end nav bar-->

<!--site description for readers-->
<div style="display:none">REMBRANDTs the REpository for Molecular BRAin Neoplasia DaTa - Empowering translational research for brain tumor studies.</div>

<!--banner graphic begins-->
<div style="background-image: url('images/bannerGraphic.jpg'); width:765; height:221"></div>
<!--banner graphic ends-->

<!--main content div with table for description and login-->
<div style="width:765;">
      
  <!--content table--> 
  <table border="0" cellspacing="0" cellpadding="0" style="margin-top:2px; width:765">
	 <tr>
	  <!--begin description cell-->
	  <td style="width:462; margin-bottom:0px; border-right:1px solid #374554; border-bottom: 1px solid #000000;">
         <p style="font-size:1.2em; font-weight:bold;">
		 About this application 
		 </p>
	
		 <p style="padding:0px 5px 0px 0px">We are designing a robust bioinformatics knowledgebase framework called
	     CaIntegrator that leverages data warehousing technology to host and
	     integrate GMDI trial data. The knowledge framework will provide
	     researchers with the ability to perform ad hoc querying and
	     reporting across multiple domains.
		 </p>
		 
	     <p style="margin-bottom:10px; padding:0px 5px 0px 0px">Scientists will be able to
	     answer basic scientific questions related to a patient or patient population
	     and view the integrated data sets in a variety of scientific based contexts.
	     Tools that link data to other annotations such as cellular pathways,
	     gene ontology terms and genomic information will be embedded.</p>
	  </td>
	  <!--end description cell-->
	  
      <!--begin login cell-->
	  <td style="background-color:#D5E0E9;">
         <p style="font-size:1.2em; font-weight:bold; margin-left:0px;width:100%; background-color:#416599; color:#FFFFFF">
		 &nbsp;&nbsp;Login 
         </p>
		 
		 <!--begin login section-->
           <html:errors/>
           <% if(request.getParameter("m") != null)
	       out.println("<p style='color:red'>You have been logged out." + "</p>");
	       %>
	       <!--login form/table begins--> 
	       <html:form action="login.do">  
		   <table border="0" style="margin: 17px 0px 0px 85px">
	            <tr><Td>User Name:</td></tr>
	            <tr><td><html:text property="userName" /></td><td rowspan="3"><img src="images/remLogo_10.gif" alt="REMBRANDT logo" /></td></tr>
	            <tr><Td>Password:</td></tr>
	            <tr><td><html:password property="password" /></td></tr>
			  </table>
	          <span style="margin: 0px 0px 0px 85px">&nbsp;<html:submit/>&nbsp;&nbsp;<html:reset/></span>
	       </html:form>
	       <!--end login form-->
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
    <a href="#">HOME</a>  |  <a href="#">CONTACT</a>  |  <a href="#">SUPPORT</a>  |  <a href="#">NCICB HOME</a>
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


</body>
</html>
