<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, java.net.URLEncoder " %>


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
		    <td class="message">Type</th>
            <td class="message">Resultant Name</th>
            <td class="message">Compound Query</th>
         
            
            
          </tr>
          <nested:iterate name="viewResultsForm" 
				property="reportBeans" 
				id="reportBean"  
				indexId="index">
              <tr style="background-color:#f2f2f2;font-size:.9em">
                <nested:equal property="isSampleSetQuery" value="true">
                 <td style="font-size:.9em">sample</td>
                </nested:equal>
                <nested:equal property="isSampleSetQuery" value="false">
                 <td style="font-size:.9em">results</td>
                </nested:equal>
	            <td>
	              <a href="runReport.do?method=runGeneViewReport&queryName=<nested:write property='encodedResultantCacheKey'/>&showSampleSelect=false" style="font-size:.9em" target="_blank">
	              <nested:write property="resultantCacheKey"/></a></td>
	            <td><nested:write property="beanText"/></td>
	            
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
     