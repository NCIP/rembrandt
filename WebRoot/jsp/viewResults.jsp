<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, java.net.URLEncoder " %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.caintegrator.application.cache.BusinessTierCache" %>
<%@ page import="gov.nih.nci.rembrandt.cache.RembrandtPresentationCacheManager"%>
<%@ page import="gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache"%>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="gov.nih.nci.caintegrator.service.task.*" %>
<%@ page import="gov.nih.nci.rembrandt.service.findings.*" %>
<%@ page import="gov.nih.nci.rembrandt.dto.query.*" %>
<%@ page import="gov.nih.nci.caintegrator.dto.view.*" %>


<script language="javascript">
	if(location.href.indexOf("viewResults") == -1)	{
		location.replace("viewResults.do");
	}
</script>
<script type="text/javascript">
function showPopup(url) {
newwindow=(url, "_blank",
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=750,height=500,scrollbars=no,resizable=no");
if (window.focus) {newwindow.focus()}
}
</script> 
<!--
<script type="text/javascript" src="soundMgr/soundmanager.js"></script>
<script type="text/javascript">soundManagerInit();</script>
-->
<%
String helpLink = "<a href=\"javascript: spawn('help.jsp";
String helpLinkClose = "', 350, 500);\" title=\"Click here for additional information about the view results page.\">"+
				"<img align=\"right\" src=\"images/help.png\" border=\"0\" "+
				">"+
				"</a><br clear=\"all\">";
%> 


  <app:cshelp topic="View_results_overview" />
    
    
    
    <!--  form action="#" -->    
      
     <br /><br />
     <!--  <form> -->
     <fieldset>
     	<legend>Query Results</legend>
       	<div id="loadingMsg" style="color:red;font-weight:bold;">&nbsp;</div>
        <%     	
		RembrandtPresentationTierCache ptc = RembrandtPresentationCacheManager.getInstance();						
		if(ptc!=null){
		%>
			<%
			//get all tasks from presentation tier
			List tasks = ptc.getAllSessionTaskResults(request.getSession().getId());
			if(tasks!=null && !tasks.isEmpty()){
		
						
				//looks like we have some findings, generate the JS to check the status of them
				%>
				<script language="javascript" src="js/a_functions.js"></script>
				<script language="javascript">	
					//testMap("testingtesting");
					var customError = function(message)	{};
					DWREngine.setWarningHandler(customError);
					DWREngine.setErrorHandler(customError);
					
					 setTimeout("A_checkAllTaskResultsStatus('<%=session.getId()%>')", 0200);
					var vr_checker1 = setInterval("A_checkAllTaskResultsStatus('<%=session.getId()%>')", 5000);
	
				</script>
				<%
				
				//why arent these in the backingbean?  - we need to check these directly from cache
				// because they are dynamic - we can not look at a copy placed in the backing bean
				//for(Object o : sessionFindings)	{ //no 1.5 stuff allowed
				for(Iterator i = tasks.iterator();i.hasNext();)	{
					RembrandtTaskResult task = (RembrandtTaskResult) i.next();
					String qname = "N/A";	
					if(task.getReportBeanCacheKey() == null ){								
					qname =  task.getTask().getQueryDTO().getQueryName();	
					}else{
					qname = task.getReportBeanCacheKey();
					}			
					String comments = "";
					String queryOnclick="";	
					String currentStatus = "Running";
					String emailIcon = " ";
					if(task.getTask().getStatus() == FindingStatus.Completed){
						currentStatus = "<b id=\"" +task.getTask().getId() + "_status\">Completed</b>  <img src='images/check.png' alt='complete' id=\"" + task.getTask().getId() + "_image\"/>";
						emailIcon = " ";
						queryOnclick = "";
						emailIcon = "<img src='images/blank.gif' alt='email results' BORDER=0  id=\"" + task.getTask().getId() + "_email\"/>";
						
						}
					else if(task.getTask().getStatus() == FindingStatus.Running){
						currentStatus = "<b id=\"" + task.getTask().getId() + "_status\" >Running</b> <img src='images/circle.gif' alt='running' id=\"" + task.getTask().getId() + "_image\" />";
						queryOnclick = "javascript:alert('Analysis Not yet complete');return false;";
						emailIcon = "<a id=\"" + task.getTask().getId() + "_email_link\" href=\"#\" ><img src='images/blank.gif' alt='email results' BORDER=0 /></a>";
	
						}
					else if(task.getTask().getStatus() == FindingStatus.Retrieving){
						currentStatus = "<b id=\"" + task.getTask().getId() + "_status\" >Retrieving</b> <img src='images/circle.gif' alt='loading' id=\"" + task.getTask().getId() + "_image\" />";
						queryOnclick = "javascript:alert('Analysis Not yet complete');return false;";
						emailIcon = "<a id=\"" + task.getTask().getId() + "_email_link\" href=\"#\" onclick=\"return false;\"><img src='images/blank.gif' alt='email results' BORDER=0 /></a>";
			
						}
					else if(task.getTask().getStatus() == FindingStatus.Error)	{					
						comments = StringEscapeUtils.escapeJavaScript(task.getTask().getStatus().getComment());
						currentStatus = "<b id=\"" + task.getTask().getId() + "_status\" ><script language=\"javascript\">document.write(showErrorHelp('"+comments+"','Error'));</script></b> <img src='images/error.png' alt='error' id=\"" + task.getTask().getId() + "_image\" />";
						emailIcon = "<img src='images/blank.gif' alt='email results'  BORDER=0 id=\"" + task.getTask().getId() + "_email\"/>";
						queryOnclick = "javascript:alert('Analysis Not yet complete');return false;";
						}
					else if(task.getTask().getStatus() == FindingStatus.Email)	{					
						comments = StringEscapeUtils.escapeJavaScript(task.getTask().getStatus().getComment());
						currentStatus = "<b id=\"" + task.getTask().getId() + "_status\" ><script language=\"javascript\">document.write(showErrorHelp('"+comments+"','Email'));</script></b> <img src='images/mail_icon.gif' alt='email' id=\"" + task.getTask().getId() + "_image\" />";
						emailIcon = "<img src='images/blank.gif' alt='email results'  BORDER=0 id=\"" + task.getTask().getId() + "_email\"/>";
						queryOnclick = "javascript:alert('Analysis Not yet complete');return false;";
					}
					
					out.println("<span style='color:red; float:right'>" + currentStatus + emailIcon +"</span> ");					
									
					//if(task.getTask().getStatus()!= FindingStatus.Completed)	{
					//	queryOnclick = "javascript:alert('Analysis Not yet complete');return false;";
					//}	
					//check the type of finding and create the appropriate link
					String	view = "";
					if(task.getTask().getQueryDTO() instanceof CompoundQuery){	
						CompoundQuery compoundQuery = (CompoundQuery) task.getTask().getQueryDTO();	
						if(compoundQuery.getAssociatedView() instanceof ClinicalSampleView ){
							view = "(C)";
						}else if(compoundQuery.getAssociatedView() instanceof GeneExprSampleView){
							view = "(GE)";
						}else if(compoundQuery.getAssociatedView()instanceof CopyNumberSampleView){
							view = "(CN)";
						}
					}
						//out.println("<li><a id=\"" + qname + "_link\" href=\"javascript:spawnx('runReport.do?method=runGeneViewReport&queryName=previewResults&showSampleSelect=false', 770, 550, '_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(Clinical)</i> ");
						out.println("<li><a id=\"" + task.getTask().getId() + "_link\" href=\"javascript:spawnx('runReport.do?method=runGeneViewReportFromCache&taskId=' + encodeURIComponent('" + URLEncoder.encode(task.getTask().getId()) + "') + '&cacheId=" + task.getTask().getCacheId() + "', 750, 500,'_report');\" onclick=\"" + queryOnclick + "\">" + qname + "</a> <i>"+view+"</i> ");


					out.println("<span style=\"font-size:10px\">(elapsed time: <span id=\"" + task.getTask().getId() + "_time\" >" + task.getTask().getElapsedTime()/1000 + "</span> sec) </span>");
					out.println("</li>");
					out.println("<br clear=\"all\" />");
					out.println("<br clear=\"all\" />");
				}
			}
			else{
			//no tasks found in cache
			out.println("<strong>No Query Results at this time.</strong><br/><br/>");
			}
		}
		else{
		  out.println("<strong>No presentation tier cache available at this time.</strong><br/><br/>");
		}
		      %>
          <br/><br/>
     <div style="font-size:9px;text-align:center;">
     Report View Type : (C)   Clinical Study Analysis | 
     (GE)  Gene Expression Analysis | 
     (CN)  Copy Number Analysis       
     </div>
    </fieldset>
    <app:cshelp topic="hoa_results_overview" /> <br /> <br />
    <fieldset>
     	<legend>High Order Analysis</legend>
       	<div id="loadingMsg" style="color:red;font-weight:bold;">&nbsp;</div>
     <%
     	//get the finding related HOA's
     	//will use scriptlet, as we arent accessing a Struts related form
     	//PresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
		BusinessTierCache btc = ApplicationFactory.getBusinessTierCache();
		Collection sessionFindings = btc.getAllSessionFindings(session.getId());
		if(sessionFindings!=null && !sessionFindings.isEmpty())	{
		
			//looks like we have some findings, generate the JS to check the status of them
			%>
			<script language="javascript" src="js/a_functions.js"></script>
			<script language="javascript">	
				//testMap("testingtesting");
				var customError = function(message)	{};
				DWREngine.setWarningHandler(customError);
				DWREngine.setErrorHandler(customError);
				
				setTimeout("A_checkAllFindingStatus('<%=session.getId()%>')", 0200);
				var vr_checker = setInterval("A_checkAllFindingStatus('<%=session.getId()%>')", 5000);

			</script>
			<%
			
			//why arent these in the backingbean?  - we need to check these directly from cache
			// because they are dynamic - we can not look at a copy placed in the backing bean
			//for(Object o : sessionFindings)	{ //no 1.5 stuff allowed
			
			boolean displayableFindings = false;
			
			for(Iterator i = sessionFindings.iterator();i.hasNext();)	{
	
				// Finding f = (Finding) o;
				Finding f = (Finding) i.next();
				String qname = "N/A";
				//q&d
				try	{
					qname =  f.getQueryDTO().getQueryName();
				}
				catch(Exception e)	{
					qname = f.getTaskId();
				}
				
				String comments = "";
				String _htm = "";
				
				String currentStatus = "Running";
				if(f.getStatus() == FindingStatus.Completed)
					currentStatus = "<b id=\"" +f.getTaskId() + "_status\">Completed</b>  <img src='images/check.png' alt='complete' id=\"" + f.getTaskId() + "_image\"/>";
				else if(f.getStatus() == FindingStatus.Running)
					currentStatus = "<b id=\"" + f.getTaskId() + "_status\" >Running</b> <img src='images/circle.gif' alt='running' id=\"" + f.getTaskId() + "_image\" />";
				else if(f.getStatus() == FindingStatus.Error)	{
					comments = f.getStatus().getComment() != null ? f.getStatus().getComment() : "Unspecified Error";
					//currentStatus = "<b id=\"" + f.getTaskId() + "_status\" ><a href=\"#\" onmouseover=\"return overlibWrapper('"+comments+"');return false;\" onmouseout=\"return nd();\" ><strong>error</strong></a></b> <img src='images/error.png' alt='error' id=\"" + f.getTaskId() + "_image\" />";
					comments = StringEscapeUtils.escapeJavaScript(comments);
					currentStatus = "<b id=\"" + f.getTaskId() + "_status\" ><script language=\"javascript\">document.write(showErrorHelp('"+comments+"','Error'));</script></b> <img src='images/error.png' alt='error' id=\"" + f.getTaskId() + "_image\" />";
				}
				
				_htm += "<span style='color:red; float:right'>" + currentStatus + "</span> ";
				
				String onclick="";	
				if(f.getStatus()!= FindingStatus.Completed)	{
					onclick = "javascript:alert('Analysis Not yet complete');return false;";
				}
				
				//check the type of finding and create the appropriate link
				if(f instanceof ClassComparisonFinding){
					displayableFindings = true;
					_htm += "<li><a id=\"" + f.getTaskId() + "_link\" href=\"javascript:spawnx('testReport.do?key=' + encodeURIComponent('" + URLEncoder.encode(f.getTaskId()) + "') + '&newReport=true', 750, 500,'hoa_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(CC)</i> ";
				}
				else if(f instanceof HCAFinding){
					displayableFindings = true;
					_htm += "<li><a id=\"" + f.getTaskId() + "_link\" href=\"javascript:spawnx('hcReport.do?key=' + encodeURIComponent('" + URLEncoder.encode(f.getTaskId()) + "'), 750, 500,'hoa_report');\" onclick=\"" + onclick + "\">" + qname + " </a> <i>(HC)</i> ";
				}
				else if(f instanceof PrincipalComponentAnalysisFinding){
					displayableFindings = true;
					_htm += "<li><a id=\"" + f.getTaskId() + "_link\" href=\"javascript:spawnx('pcaReport.do?key=' + encodeURIComponent('" + URLEncoder.encode(f.getTaskId()) + "'), 900, 600,'hoa_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(PCA)</i> ";
				}
				else if(f instanceof FTestFinding){
					displayableFindings = true;
					_htm += "<li><a id=\"" + f.getTaskId() + "_link\" href=\"javascript:spawnx('testReport.do?key=' + encodeURIComponent('" + URLEncoder.encode(f.getTaskId()) + "'), 750, 500,'hoa_report');\" onclick=\"" + onclick + "\">" + qname + "</a> <i>(CC)</i> ";
				}
				else	{
					//skip this one, as its not a valid finding to show
					continue;
				}
				
				out.println(_htm);
				_htm = "";
				out.println("<span style=\"font-size:10px\">(elapsed time: <span id=\"" + f.getTaskId() + "_time\" >" + f.getElapsedTime()/1000 + "</span> sec) </span>");
				out.println("</li>");
				out.println("<br clear=\"all\" />");
				out.println("<br clear=\"all\" />");
			}
			if(displayableFindings == false)	{
				out.println("<strong>No HOA Results at this time.</strong><br/><br/>");
			}
		}
		else	{
			//no findings yet
			out.println("<strong>No HOA Results at this time.</strong><br/><br/>");
		}
     
     
     %>
     <br/><br/>
     <div style="font-size:9px;text-align:center;">
     (CC) Class Comparison | 
     (HC) Hierarchical Clustering | 
     (PCA) Principal Component Analysis
     </div>
     
</fieldset>
<%--
<br /><br />

	<!--  BEGIN BULK DOWNLOAD SECTION -->
	<script type="text/javascript">
		function getDL(sel)	{
			var dl = sel.value;
			var lnk = "fileDownload.do?method=brbFileDownload&fileId=";
			window.location.href=lnk+dl;
		}
	</script>

      <fieldset style="background:transparent;background-color:#fff;">
        <legend>
          Bulk Downloads
        </legend>
        <br/>
        <logic:notEmpty name="downloadFileList">
        	<select>
        		<option>BRB Format</option>
        	</select>
        	<select id="idfile" style="width:300px">
		        <logic:iterate name="downloadFileList" id="downloadFile">
			       <option value="<bean:write name="downloadFile" property="fileId"/>"><bean:write name="downloadFile" property="fileName"/></option>
		        </logic:iterate>
	        </select>
	        <input type="button" onclick="getDL($('idfile'))" value="download" style="width:70px"/>
     	</logic:notEmpty>
	    <logic:empty name="downloadFileList">
		    <strong>There are no files to download at this time.</strong>
		    <br /><br />
	    </logic:empty>
     	<br/><br/>
	    &nbsp;&nbsp;&nbsp;
	    <a href="http://linus.nci.nih.gov/BRB-ArrayTools.html" target="_blank"><span style="font-size:.8em;text-align:left;"> BRB-Array Tools </span></a>
	  	<br /><br />   
	  </fieldset>
	  <br/><br/>
	  --%>