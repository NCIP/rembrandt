<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, java.net.URLEncoder " %>
<%@ page import="gov.nih.nci.caintegrator.application.cache.*" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.*" %>
<%@ page import="gov.nih.nci.caintegrator.exceptions.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="gov.nih.nci.rembrandt.service.findings.*" %>


<script type="text/javascript">Help.insertHelp("View_results_overview", " align='right'", "padding:2px;");</script>        
     <fieldset>
     	<legend>Gene Pattern Job Result</legend>
       	<div id="loadingMsg" style="color:red;font-weight:bold;">&nbsp;</div>
       		<table border="0" cellpadding="3" cellspacing="3">
       			<tr>
       				<!-- td width="20%">Job Id: <br><bean:write name="jobId"/></td> -->
       				<td> Your request has been sent to GenePattern for processing, and  
       				your job id is :  <span style="color:red;font-weight:bold"><bean:write name="jobId"/></span>.
       				When your task is complete, your data will be ready 
       				for analysis in GenePattern.  Your available tasks will appear in the right 
       				sidebar of the GenePattern when they are ready.  The approximate 
       				processing time is 2-3 minutes.<br><br>
       				
				<!-- to check if the gene pattern job is completed -->
				<% String jobId = (String)request.getAttribute("jobId"); %>
				<%-- --%>
				<script language="javascript" src="js/a_genePattern.js"></script>
				<script type='text/javascript' src='dwr/interface/GenePatternHelper.js'></script>
				<script language="javascript">	
					//testMap("testingtesting");
					var customError = function(message)	{};
					DWREngine.setWarningHandler(customError);
					DWREngine.setErrorHandler(customError);
					
					setTimeout("A_checkGenePatternStatus('<%= jobId %>')", 0200);
					var vr_checker = setInterval("A_checkGenePatternStatus('<%= jobId %>')", 5000);
	
				</script>
				<%-- --%>
				<%
					//Check completion status
					//String jobId = (String)request.getAttribute("jobId");
					String currentStatus = (String)request.getAttribute("gpStatus");
					System.out.println("gpJobView.jsp: " + currentStatus);
					String gpurl = (String)request.getAttribute("genePatternURL");
					if(currentStatus.equals("completed"))
						currentStatus = "<b id=\"" + jobId + "_status\">completed</b>  <img src='images/check.png' alt='complete' id=\"" + jobId + "_image\"/>";
					else if(currentStatus.equals("running"))
						currentStatus = "<b id=\"" + jobId + "_status\" >running</b> <img src='images/circle.gif' alt='running' id=\"" + jobId + "_image\" />";
					else if(currentStatus.equals("error"))	{
					
						String comments = "An error occured during sending your job request to GenePattern or during GenePattern processing";
						currentStatus = "<b id=\"" + jobId + "_status\" ><script language=\"javascript\">document.write(showErrorHelp('"+comments+"','error'));</script></b> <img src='images/error.png' alt='error' id=\"" + jobId + "_image\" />";
					}
					//System.out.println(f.getTaskId() + ": " + comments);
					
					out.println("<span style='color:red; float:right'>" + currentStatus + "</span> ");
					
					String onclick="";	
					if(!currentStatus.equals("completed"))	{
						onclick = "javascript:alert('Gene Pattern Processing Not yet complete');return false;";
					}
					out.println("<a id=\"" + jobId + "_link\" href=\"" + gpurl + "\" onclick=\"" + onclick + "\" target=\"new\">GenePattern Job " + jobId +"</a>");
					
					out.println("<br clear=\"all\" />");
					out.println("<br clear=\"all\" />");
				%>
       				Please click the above link to lunch GenePattern.  If your task does not appear in 
       				the sidebar, please wait a minute and refresh the GenePattern page to try again.
     			</tr>
     		</table>
		</fieldset>
<br /><br />
     