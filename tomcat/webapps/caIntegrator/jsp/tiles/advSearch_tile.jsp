<fieldset class="sidebar" style="border-width: 2px">
<legend style="background-color:#ffffff">Advanced Search</legend>

<form action="menu.do" method="POST">
<input type="radio" class="radio" name="menu" value="0" checked selected onclick="javascript:document.forms[0].rpt.disabled=true;">
		<b>Create a New Query</b>
		<a href="javascript:void(0);" onmouseover="return overlib('Select this option if you do not have a saved query to upload.  You will be able to build a query based on the Advanced Search Areas listed below.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
		<br><Br>	
				<table border="0">
						<tr>
							<Td>
							Study Data Set<br>
							<select name="dataSet" onchange="javascript:moveEm(this);">
								<option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								<option>Rembrandt (GMDI)</option>
								<option>I-SPY</option>
								<option>Other1</option>
								
							</select><br>
							Generating Institution<br>
							<select name="generatingInstitution">
								<option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								
							</select>
							</td>
						</tr>
					</table>
		<hr width="150" color="#ffffff">
		<input type="Radio" name="menu" class="radio" value="1" onclick="javascript:document.forms[0].rpt;" disabled="true">
		<b>Upload a saved Query</b>
		<a href="javascript:void(0);" onmouseover="return overlib('You may choose to start from an existing query.  Please upload that saved query here.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
		&nbsp;&nbsp;
		<br><Br>
		<input type="file" name="rpt" disabled="true">
		<div class="advButton">
			<input type="submit" class="xbutton" style="width:100px;" value="continue">
		</div>
		
     </form>
		<div class="message">Note: Please move your mouse over the 
			<a href="javascript:void(0);" onmouseover="return overlib('Help messages will appear here.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
			links for help throughout the application.		
		</div>
</fieldset>