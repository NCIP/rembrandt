<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<h4>Advanced Search</h4>
<div class"advancedSearch">
<form action="#">
<input type="radio" class="radio" name="menu" value="0" checked selected onclick="javascript:document.forms[0].rpt.disabled=true;">
		Create a New Query/Report
		<a href="javascript:void(0);" title="Select this option if you do not have a saved query to upload.  You will be able to build a query based on the Advanced Search Areas listed below.">[?]</a>
		<blockquote>
				<table border="0" width="100%" style="">
						<tr>
							<Td>
							<div class="title">Study Data Set</div>
							<select name="dataSet" onchange="javascript:moveEm(this);">
								<option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								<option>Rembrandt (GMDI)</option>
								<option>I-SPY</option>
								<option>Other1</option>
								
							</select>
							
						
				
							<div class="title">Generating Institution</div>
							<select name="generatingInstitution">
								<option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								
							</select>
							</td>
						</tr>
					</table>
		</blockquote>
		<br><br>
		<input type="Radio" name="menu" class="radio" value="1" onclick="javascript:document.forms[0].rpt;" disabled="true">
		Upload a saved Query
		<a href="javascript:void(0);" title="You may choose to start from an existing query.  Please upload that saved query here.">[?]</a>
		&nbsp;&nbsp;
		<blockquote>
			<input type="file" name="rpt" disabled="true">
		</blockquote>
		
		<div class="advButton">
		<input type="button" class="xbutton" style="width:100px;" value="continue" onclick="javascript:location.href='aSearchMenu.html';">
		</div>
		
		</div>
     </form>
		<div class="message">Note: Please move your mouse over the <br>
		<a href="javascript:void(0);" title="Help messages will appear here.">[?]</a>
		links for help throughout the application.		
		</div>
