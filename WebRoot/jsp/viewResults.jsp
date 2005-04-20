<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, java.net.URLEncoder " %>
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
          View Results
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
     <form>
     