<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %> 

<tr class="report"><td>
<br />
<form action="#">
<fieldset>
<legend>Quick Search</legend><br />
<strong>Enter a gene symbol</strong><br />
<input type="text" name="quickSearchName" size="40">
&nbsp;
<input class="xbutton" style="width:50px;" type="submit" value="Go">
<a href="javascript:void(0);" onmouseover="return overlib('Enter a name or the first few characters followed by *.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
<br /><br />
<strong>Select graph format:</strong><br /> 
<input type="radio" name="plot" class="radio" value="geneExpPlot" checked>Gene Expression plot&nbsp;
<input type="radio" name="plot" class="radio" value="kapMaiPlot">Kaplan-Meier survival plot
</fieldset>
</form>
<Br>

<form action="menu.do" method="POST">
<fieldset>
<legend>Advanced Search</legend>
<br />


  <table border="0" cellspacing="4" width="100%">		
    <tr>
     <td>
       <input type="radio" class="radio" name="menu" value="0" checked selected onclick="javascript:document.forms[0].rpt.disabled=true;">
       <b>Create a New Query</b>&nbsp;&nbsp;<a href="javascript:void(0);" onmouseover="return overlib('Select this option if you do not have a saved query to upload.  You will be able to build a query based on the Advanced Search Areas listed below.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
     </td>
     <td>
       <input type="Radio" name="menu" class="radio" value="1" onclick="javascript:document.forms[0].rpt;" disabled="true">
       <b>Upload a saved Query</b>&nbsp;&nbsp;<a href="javascript:void(0);" onmouseover="return overlib('You may choose to start from an existing query.  Please upload that saved query here.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
     </td>
    </tr>
			
				
	<tr>
	  <Td>Study Data Set<br>
		<select name="dataSet" onchange="javascript:changeList(this);">
		 <option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
		 <option>Rembrandt (GMDI)</option>
		 <!--<option>I-SPY</option>-->
		 <option>Other</option>
		</select>
	  </td>
	  <td><br><input type="file" name="rpt" disabled="true"></td>
	</tr>
	<tr>
	  <td colspan="2">Generating Institution<br>
		<select name="generatingInstitution">
		 <option>All</option>
		 <option>NCI</option>
		 <option>Johns Hopkins University</option>
		 <option>UCSF</option>
		</select>
	  </td>
	</tr>
		
	<tr>
	 <td colspan="2" align="center">
	   <input type="submit" class="xbutton" style="width:100px;" value="continue">
	 </td>
	</tr>
	
  </table>

     
		<div class="message">Note: Please move your mouse over the 
			<a href="javascript:void(0);" onmouseover="return overlib('Help messages will appear here.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
			links for help throughout the application.	 	
		</div>
		
</fieldset>
</form>
	</td></tr>	
		

