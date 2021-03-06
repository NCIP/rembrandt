<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.apache.struts2.views.jsp.TagUtils"%>  
<%@ page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="c-rt" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld"  prefix="app" %>
<%@ page import="java.util.HashMap,java.util.List,java.util.ArrayList,gov.nih.nci.rembrandt.util.StatisticsInfoJob" %>
<%

StatisticsInfoJob job = new StatisticsInfoJob();
HashMap statisticsMap = job.getStatisticsMap();
pageContext.setAttribute("map", statisticsMap);

OgnlValueStack valStack = (OgnlValueStack)TagUtils.getStack(pageContext);  

List<List<String>> statList = new ArrayList<List<String>>();
Iterator<String> ite = statisticsMap.keySet().iterator();
while (ite.hasNext()) {
	
	//List<List<String>> stats = new ArrayList<List<String>>();
	String key = ite.next();
	
	HashMap tempMap = (HashMap)statisticsMap.get(key);

	List<String> data = new ArrayList<String>();

	data.add(key);
	data.add((String)tempMap.get("sample"));
	data.add((String)tempMap.get("specieman"));	
	statList.add(data);
}

valStack.getContext().put("statList", statList);
valStack.setValue("#attr['statList']", statList, false);

%>

<html>
<head><title>REMBRANDT - Repository for Molecular Brain Neoplasia Data</title>
<link rel="shortcut icon" href="images/favicon.ico" />

<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>


<script type='text/javascript' src='dwr/engine.js'></script>
<script language="javascript" src="js/caIntScript.js"></script>
</head>
<body>
<c:import url="jsp/tiles/overlib_tile.jsp"></c:import>



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
<table border="0" cellspacing="0" cellpadding="0" style="margin-top:2px; width:765" summary="This table is used to format page content">
	<tr>
		<th></th><th></th>
	</tr>	
	<tr>
		<!--begin description cell-->
		<td style="width:462; margin-bottom:0px; border-right:1px solid #374554; border-bottom: 1px solid #000000; border-left: 1px solid #fC4C5C5;">
			<table width="100%" border="0" summary="This table is used to format page content">
				<tr>
					<th></th>
				</tr>
				<tr>
					<td>
						<p style="font-size:1.2em; font-weight:bold;padding:0px 5px 0px 5px">
							About this application<br />
							<span style="font-size:.7em;text-align:right;">
								Release <%=System.getProperty("rembrandt.application.version")!=null ? System.getProperty("rembrandt.application.version") : "1.5"%>
								<a target="_blank" style="font-size:1em;" href="https://wiki.nci.nih.gov/display/icrportals/Rembrandt+1.5.8+Release+Notes">[more info]</a>
							</span>
							<div id="releaseNotesDiv" style="padding:0px 0px 5px 5px;display:none;font-size:0.8em;background-color:#e0e0e0;">
								
							</div>
							<script type="text/javascript">
								DynamicListHelper.getReleaseNotes(function(r) {$('releaseNotesDiv').update(r); });
							</script>
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
						<p style="margin-bottom:10px; padding:0px 5px 0px 5px; font-size:.9em;">Please visit <a href="https://wiki.nci.nih.gov/display/icrportals/REMBRANDT">https://wiki.nci.nih.gov/display/icrportals/REMBRANDT</a>
						for more information.</p>
					</td>
				</tr>
				<!--  statistics data -->
				<tr>
					<td>
						<table width="100%" align="center" id="statTable" style="" summary="This table is used to format page content">
							<tr>
								<th></th>
							</tr>
							<tr>
								<td colspan="3">
								<p style="font-size:1.2em; font-weight:bold;padding:0px 5px 0px 5px">
									About the Data<br/>
									<span style="font-size:0.7em;" class="mmsg">
									Release Date: <%=System.getProperty("rembrandt.data.releaseDate")!=null ? System.getProperty("rembrandt.data.releaseDate") : "2009"%>
									<a href="#" style="font-size:1.0em;" onclick="$(dataMoreInfo).toggle();return false;">[more info]</a>
									</span>
									<div id="dataMoreInfo" style="display:none; color:#000;font-size:0.8em;font-weight:normal; background-color:#e0e0e0;">
										<h3>Additional Information about this data update:</h3> 
										<%=System.getProperty("rembrandt.data.notes")!=null ? System.getProperty("rembrandt.data.notes") : "N/A"%>
										<br/><br/>
									</div>
								</p>
								</td>
							</tr>
							<tr style="background-color:#416599; color:#fff;">
								<td width="33%" style="background-color:#fff;font-size:.9em; color:gray;">Data Statistics</td>
								<td width="35%" style="font-size:.9em;padding:0px 5px 0px 5px;">
									<c:out value="No. of Study Participants"/> 
								</td>
								<td width="" style="font-size:0.9em;padding:0px 5px 0px 5px">
									<c:out value="No. of Specimens"/>
								</td>
							</tr>
							
							<s:if test="#statList != null">
							
							<s:iterator value="#statList" var="aStat">
							
							<tr>								
								<td style="color:#002185;background-color:#e0e0e0;font-size:0.9em;padding:0px 5px 0px 5px">
									<s:property value="#aStat.get(0)" /></td>
								<td style="background-color:#e0e0e0;font-size:0.9em;padding:0px 5px 0px 5px">
									<s:property value="#aStat.get(1)" /></td>
								<td style="background-color:#e0e0e0;font-size:0.9em;padding:0px 5px 0px 5px">
									<s:property value="#aStat.get(2)" /></td>	
								
							</tr>
							
							</s:iterator>
							</s:if>
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
  		<div id="browseRBTDiv" style="padding:15px; padding-bottom:10px;">
  		<table cellpadding="0" cellspacing="0" border="0" summary="This table is used to format page content">
  			<tr>
				<th></th><th></th>
			</tr>
	  		<tr>
		  		<td style="vertical-align:middle;">
		  			<b style="font-size:.8em;">For Access, new and existing <br/>users click the button below:</b><br/><br/>
		  			<input type="button" style="cursor:pointer;width:180px;border-color: #000; border-bottom:2px solid #000; border-right:2px solid #000;font-size:13px;" value="Browse Rembrandt Data" onclick="location.href='/rembrandt/legal.jsp'"/><br clear="both"/>
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
	      <li><a style="font-size: 0.9em;" href="http://www.ncbi.nlm.nih.gov/pubmed/19208739?ordinalpos=1&amp;itool=EntrezSystem2.PEntrez.Pubmed.Pubmed_ResultsPanel.Pubmed_DefaultReportPanel.Pubmed_RVDocSum" target="_blank">Rembrandt 2009 Publication [PMID: 19208739] in Molecular Cancer Research</a>&nbsp;<!-- a style="font-size: 0.9em;" href="docs/1541-7786.MCR-08-0435v1.pdf" target="_blank"></a --></li>	      
	      <li><a style="font-size:.9em;" href="tutorials.jsp">View Tutorials</a></li>
	      <li><app:cshelp topic="cite_data" style="font-size:.9em" text="How to Cite Rembrandt Data"/></li>	      
	      <li><a style="font-size:.9em;" href="mailto:REMBRANDT_UAT_L@list.nih.gov?subject=REMBRANDT feedback">Provide us your feedback</a></li>
		  <ul>
		 </div>
		 
		 <div style="padding:5px;margin:15px; background-color:#fff; border:1px solid #416599;">
 			<app:cshelp topic="Opening_page" style="padding:_8px;"/>
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
    <a href="menu.action">HOME</a>  |  <a href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">SUPPORT</a>  |  <a href="http://cbiit.nci.nih.gov" target="_blank">NCICB HOME</a>
    <br /><span style="font-size:.8em;text-align:right;">Release <%=System.getProperty("rembrandt.application.version")!=null ? System.getProperty("rembrandt.application.version") : "1.5"%></span> 
</div>
<!--end footer-->

<!--begin NCI footer-->
<!--  %@ include file="/jsp/footer.jsp" %> -->
<!--end NCI footer-->
</div>

</body>
</html>
