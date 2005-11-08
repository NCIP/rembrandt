<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, java.net.URLEncoder " %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.rembrandt.cache.*" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.*" %>

<%
String helpLink = "<a href=\"javascript: spawn('help.jsp";
String helpLinkClose = "', 350, 500);\">"+
				"<img align=\"right\" src=\"images/help.png\" border=\"0\" "+
				"onmouseover=\"return overlib('Click here for additional information about the view results page.', CAPTION, 'Help', OFFSETX, -50);\" onmouseout=\"return nd();\">"+
				"</a><br clear=\"all\">";
%> 
<%=helpLink%>?sect=viewResults<%=helpLinkClose%>
<tr class="report">
  <td>
    <br />
    <form action="#">
      
      
      <fieldset>
        <legend>
          Query Results
        </legend>
        
        <logic:notEmpty name="viewResultsForm" property="reportBeans">
        
        <table align="center" border="0" width="95%" cellpadding="2" cellspacing="1" id="rosso">
		  <tr>
		    <td class="message">Type</td>
            <td class="message">Resultant Name</td>
            <td class="message">Compound Query</td>
            <td class="message">View</td>
          </tr>
          <nested:iterate name="viewResultsForm" 
				property="reportBeans" 
				id="reportBean"  
				indexId="index">
              <tr style="background-color:#f2f2f2;font-size:.8em">
                <nested:equal property="isSampleSetQuery" value="true">
                 <td>sample</td>
                </nested:equal>
                <nested:equal property="isSampleSetQuery" value="false">
                 <td>results</td>
                </nested:equal>
	            <td>
	              <a href="runReport.do?method=runGeneViewReport&queryName=<nested:write property='encodedResultantCacheKey'/>&showSampleSelect=false" style="font-size:.9em" target="_blank">
	              <nested:write property="resultantCacheKey"/></a></td>
	            <td><nested:write property="beanText"/></td>
	            <td><nested:write property="beanView"/></td>
	            
              </tr>
          </nested:iterate>
         </table>
         
     
     </logic:notEmpty>
     
     <logic:empty name="viewResultsForm" property="reportBeans">
     <strong>There are no results to view at this time.</strong>
     <br /><br />
     </logic:empty>
        
     </fieldset>
     <br /><br />
     <!--  <form> -->
     
     <fieldset>
     	<legend>High Order Analysis</legend>
       
     <%
     	//get the finding related HOA's
     	//will use scriptlet, as we arent accessing a Struts related form
     	//PresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
		BusinessTierCache btc = ApplicationFactory.getBusinessTierCache();
		Collection sessionFindings = btc.getAllSessionFindings(session.getId());
		if(sessionFindings!=null)	{
		
			//looks like we have some findings, generate the JS to check the status of them
			%>
			<script language="javascript" src="js/a_functions.js"></script>
			<script language="javascript">	
				//testMap("testingtesting");
			</script>
			<%
			
			//why arent these in the backingbean?  - we need to check these directly from cache
			// because they are dynamic - we can not look at a copy placed in the backing bean
			//for(Object o : sessionFindings)	{ //no 1.5 stuff allowed
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
				
				/* 
				//cant use 1.5
				String currentStatus = "running";
				switch(f.getStatus())	{
					case Completed:
						currentStatus = "complete  <img src='images/check.png' alt='running'/>";
					break;
					case Running:
						currentStatus = "running <img src='images/circle.gif' alt='running'/>";
					break;
					case Error:
						currentStatus = "error";
					break;
					default:
						currentStatus = f.getStatus().toString();
					break;
				
				}
				*/
				
				String currentStatus = "running";
				if(f.getStatus() == FindingStatus.Completed)
						currentStatus = "<b id=\"" +f.getTaskId() + "_status\">complete</b>  <img src='images/check.png' alt='running' id=\"" + f.getTaskId() + "_image\"/>";
				else if(f.getStatus() == FindingStatus.Running)
						currentStatus = "<b  id=\"" + f.getTaskId() + "_status\" >running</b> <img src='images/circle.gif' alt='running' id=\"" + f.getTaskId() + "_image\" />";
				else if(f.getStatus() == FindingStatus.Error)
						currentStatus = "error";


				out.println("<span style='color:red; float:right'>" + currentStatus + "</span> ");
				
				if(f.getStatus() == Completed)
					out.println("<li><a href=\"javascript:spawnx('testReport.do?key=" + f.getTaskId() + "', 700, 500,'hoa_report');\">" + qname + "</a> ");
				else
					out.println("<li>" + qname + " ");
				
				out.println("(elapsed time: " + f.getElapsedTime() + ") ");

				out.println("</li>");
				out.println("<br clear=\"all\" />");
				out.println("<br clear=\"all\" />");
			}
		}
		else	{
			//no findings yet
			out.println("No HOA findings yet....c'mon, go make some");
		}
     
     
     %>
</fieldset>
<br /><br />
     