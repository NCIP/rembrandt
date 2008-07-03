<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c-rt" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld"  prefix="app" %>
<%@ page import="gov.nih.nci.rembrandt.util.StatisticsInfoJob" %>
<%
if(session.getAttribute("logged") == "yes")
{
//youre already logged in, why are you here?
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = "welcome.jsp";
response.setHeader("Location",newLocn);
}
StatisticsInfoJob job = new StatisticsInfoJob();
HashMap map = job.getStatisticsMap();
pageContext.setAttribute("map", map);

%>

<html>
<head><title>REMBRANDT - Repository for Molecular Brain Neoplasia Data</title>
<link rel="shortcut icon" href="images/favicon.ico" />
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
<script language="javascript" src="js/caIntScript.js"></script>
</head>
<body>
<!--header NCI-->
<%@ include file="/jsp/header.jsp" %>
<!--end all headers-->

<!--navigation bar-->
<div style="background-color:#D5E0E9; width:765px; padding:1px 0px 1px 0px;text-align:left">
<%@ include file="/jsp/tiles/crumbMenu_tile.jsp" %>
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
<table border="1" cellspacing="0" cellpadding="0" style="margin-top:2px; width:765">
	<tr>
		<!--begin description cell-->
		<td style="width:462; margin-bottom:0px; border-right:1px solid #374554; border-bottom: 1px solid #000000; border-left: 1px solid #fC4C5C5;">
			<table width="100%" border="0">
				<tr>
					<td>
						<p style="font-size:1.2em; font-weight:bold;padding:0px 5px 0px 5px">
						About this application	
						<br /><span style="font-size:.7em;text-align:right;">Release 1.5.1</span>
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
				</tr>
				<!--  statistics data -->
				<tr>
					<td>
						<table width="100%">
							<tr>
								<td width="40%">&nbsp;</td>
								<td width="35%" style="font-size:.9em;padding:0px 5px 0px 5px">
									<c:out value="No of Study Participants"/> 
								</td>
								<td style="font-size:0.9em;padding:0px 5px 0px 5px">
									<c:out value="No of Speciemen"/>
								</td>
							</tr>
							<c:forEach var="dataType" items="${map}">
							<tr>
								<td style="font-size:0.9em;padding:0px 5px 0px 5px"> <c:out value="${dataType.key}"/>
								</td>
								<c:forEach items="${dataType.value}" var="data">
								<td style="font-size:0.9em;padding:0px 5px 0px 5px"> 	
									<c:out value="${data.value}"/>
								</td>	
								</c:forEach>
							</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</td>
	  <!--end description cell-->
	  
      <!--begin login cell-->
	  <td style="background-color:#D5E0E9;">
         <p style="font-size:1.2em; font-weight:bold; margin-left:0px;width:100%; background-color:#416599; color:#FFFFFF; margin-bottom:5px;">
		 &nbsp;&nbsp;Browse Rembrandt Data 
         </p>
		 
		 <!-- 
		 <div id="loginDiv">
           <html:errors property="invalidLogin" />
	       <html:form action="login.do">  
		   <table border="0" style="margin: 0px 0px 0px 60px;font-size:8pt">
	            <tr><td>User Name:</td><td rowspan="5"><br /><img src="images/remLogo_10.gif" alt="REMBRANDT logo" /></td></tr>
	            <tr><td><html:text property="userName" /></td></tr>
	            <tr><Td>Password:</td></tr>
	            <tr><td><html:password property="password" /></td></tr>
			</table>
	          <span style="margin: 0px 0px 0px 65px">&nbsp;<html:submit/>&nbsp;&nbsp;<html:reset/></span>
	       </html:form>
  		</div>
		-->  		
  		<div id="browseRBTDiv" style="padding:15px; padding-bottom:10px;">
  		<table cellpadding="0" cellspacing="0" border="0">
	  		<tr>
		  		<td style="vertical-align:middle;">
		  			<b style="font-size:.8em;">For Access, new and existing <br/>users click the button below:</b><br/><br/>
		  			<input type="button" style="cursor:pointer;width:180px;border-color: #000; border-bottom:2px solid #000; border-right:2px solid #000;font-size:13px;" value="Browse Rembrandt Data" onclick="location.href='registration.do'"/><br clear="both"/>
		  		</td>
		  		<td>
		  			<img src="images/remLogo_10.gif" alt="REMBRANDT logo" />
		  		</td>
	  		</tr>
  		</table>
  		</div>
  			
	      <div style="width:80%;margin-left:20px;margin-top:3px;">
	      <ul>
	      <li style="list-style:none; color:#000;margin-left:-20px; text-decoration:underline;">Additional Information:</li>
	      <li><a style="font-size:.9em;" href="docs/Rembrandt1.5_Users_Guide.pdf">Download User Guide (PDF)</a></li>
	      <li><a style="font-size:.9em;" href="tutorials.jsp">View Tutorials</a></li>
	      <li><a style="font-size:.9em" href="javascript:Help.popHelp('cite_data');">How to Cite Rembrandt Data</a></li>	      
	      <li><a style="font-size:.9em;" href="mailto:REMBRANDT_UAT_L@list.nih.gov?subject=REMBRANDT feedback">Provide us your feedback</a></li>
		  <ul>
		 </div>
		 
		 <div style="padding:5px;margin:15px; background-color:#fff; border:1px solid #416599;">
 			<script type="text/javascript">Help.insertHelp("Opening_page", "align='left'", "padding:_8px;");</script>
			Throughout the application please click the Help Icon for context sensitive application help.<br/>
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
    <br /><span style="font-size:.8em;text-align:right;">Release 1.5.1</span> 
</div>
<!--end footer-->

<!--begin NCI footer-->
<%@ include file="/jsp/footer.jsp" %>
<!--end NCI footer-->
</div>

</body>
</html>
