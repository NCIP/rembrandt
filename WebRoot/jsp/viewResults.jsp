<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="java.util.*, java.lang.*, java.io.*" %>


<tr class="report">
  <td>
    <br />
    <form action="#">
      
      
      <fieldset>
        <legend>
          View Results
        </legend>
        
        <table align="center" border="0" width="95%" cellpadding="2" cellspacing="1" id="rosso">
		  <tr>
            <td class="message">Resultant</th>
            <td class="message">Compound Query</th>
            <td class="message">View</th>
          </tr>
          <tr style="background-color:#f2f2f2;font-size:.9em">
	            <td><a href="#" style="font-size:.9em">Resultset name1</a></td>
	            <td style="font-size:.9em">(test5 AND test6)</td>
	            <td style="font-size:.9em">Gene Expression Data per sample view</td>
          </tr>
          <tr style="background-color:#f2f2f2;font-size:.9em">
		         <td><a href="#" style="font-size:.9em">Resultset name2</a></td>
		         <td style="font-size:.9em">(egfrQuery OR vegf5) AND test5</td>
		         <td style="font-size:.9em">Clinical View</td>
          </tr>
          <tr style="background-color:#f2f2f2;font-size:.9em">
		        <td><a href="#" style="font-size:.9em">Resultset name3</a></td>
		        <td style="font-size:.9em">(egfrQuery OR vegf5) OR p53</td>
		        <td style="font-size:.9em">Gene Expression Data per disease view</td>
         </tr>
         </table>
         
     </fieldset>
     <br /><br />
     <form>