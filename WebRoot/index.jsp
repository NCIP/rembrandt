<%@ page language="java" %>
  <%
  if(request.getSession().isNew() == true){
    
  //splash page goes here
 %>
   <!--begin splash page if session is new to client-->
   <html>
   <head><title>REMBRANDT - Repository for Molecular Brain Neoplasia Data (User Guidelines)</title>
   <%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
   <script language="javascript" src="js/caIntScript.js"></script>
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

   
   <fieldset style="border: 1px solid #000066;width:765px">
   <legend style="text-align:center;background-color:#ffffff">LEGAL RULES OF THE ROAD</legend>
   <p style="text-align:left">The Repository of Molecular Brain Neoplasia DaTa (REMBRANDT)
   		Database is provided as a public service by the National
   		Cancer Institute (NCI) to foster the rapid dissemination
   		of information to the scientific community and the
   		public.  This first release of the Database is intended
   		as an evaluation (Beta) version to solicit feedback and
   		input on the interface and the content from the
   		scientific community at large.  The samples used to
   		produce the data here presented were provided by the
   		Hermelin Brain Tumor Center and the Henry Ford Hospital.
   		All data may be subject to copyright provisions.
   		The data used for this evaluation version is confidential
   		information which has not yet been published. Thus,
   		you may browse, and use the data for the purposes of
   		familiarizing yourself with the structure of the database
   		and interface, evaluate its usefulness and provide
   		<a href="mailto:REMBRANDT_UAT_L@list.nih.gov?subject=REMBRANDT feedback">feedback</a> to the development team. You may not use the data provided
   		here for research projects in which you are involved at
   		present, or to test hypothesis to develop future research
   		projects. Although the raw data will be available for
   		download in the upcoming public release of the Database, 
   		this functionality is disabled in the evaluation version
   		you are about to enter. You may encounter documents or
   		portions of documents contributed by private institutions
   		or organizations.  Other parties may retain all rights
   		to publish or produce these documents. Commercial use
   		of the documents on this site may be protected under
   		United States and foreign copyright laws.  In addition,
   		some of the data may be the subject of patent applications
   		or issued patents, and you may need to seek a license
   		for its commercial use. </p>
   		
   		<p style="color:#002185;font-weight:bold;text-align:left">I HAVE READ AND UNDERSTOOD THE ABOVE PROVISIONS,
   		AND SIGNIFY MY AGREEMENT BY <a href="login.jsp">CLICKING HERE</a> </p>

   </fieldset>
  
  
   <!--begin NCI footer-->
   <div>
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
   <!--end splash page-->
  <%}
  
  else{
    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    String newLocn = "login.jsp";
    response.setHeader("Location",newLocn);
    }
  %>


