<tr class="report"><td>


<input type="radio" class="radio" name="menu" value="0" checked selected onclick="javascript:document.forms[0].rpt.disabled=true;">
		Create a New Query/Report
		<a href="javascript:void(0);" onmouseover="return overlib('Select this option if you do not have a saved query to upload.  You will be able to build a query based on the Advanced Search Areas listed below.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
		<blockquote>
				<table border="0" width="100%" style="">
						<tr>
							<Td>
							<div class="title">Study Data Set</div>
							<select name="dataSet" onchange="javascript:moveEm(this);">
								<option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								<option>Rembrandt (GMDI)</option>
								<option>I-SPY</option>
								<option>Other</option>
								
							</select>
							</td>
						
							<Td>
							<div class="title">Generating Institution</div>
							<select name="generatingInstitution">
								<option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								
							</select>
							</td>
						</tr>
					</table>
		</blockquote>
		<br><br>
		<input type="Radio" name="menu" class="radio" value="1" onclick="javascript:document.forms[0].rpt.disabled=false;">
		Upload a saved Query
		<a href="javascript:void(0);" onmouseover="return overlib('You may choose to start from an existing query.  Please upload that saved query here.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
		&nbsp;&nbsp;
		<blockquote>
			<input type="file" name="rpt" disabled>
		</blockquote>
		<center>
		&nbsp;&nbsp;<input type="button" class="xbutton" style="width:100px;" value="continue" onclick="javascript:location.href='aSearchMenu.html';">
		</center>

		<div style="padding:5px 30px 5px 30px;">
		<br>
		<strong>Advanced Search Areas</strong>
		<table border="0" cellspacing="2" cellpadding="4" style="border: 1px solid silver; padding:5px;">
		  <tr valign="top">
		    <td>
			<strong>Gene Expression Analysis</strong><br />
			Cytoband<br />
			Gene<br />
			Clone/Probeset<br />
			Gene Ontology (GO) Classifications<br />
			Pathways<br />
			Array Platform<br />
			Differential Gene Expression
			</td>
			<td>
			<strong>Comparative Genomic Analysis</strong><br />
            Cytoband<br />
			SNP Id<br />
			Genomic Annotation Track <br>
			Chromosomal Abnormality<br>
			Copy Number
			</td>
			<td>
			<strong>Clinical Study Analysis</strong><br />
            Prior Therapy<br />
			Demographics<br />
			Histopathology<br />
			Survival<br />
			Interventions
			</td>
		  </tr>
		</table>
		</div>
		<br>
		
		<b class="message">Note: Please move your mouse over the 
		<a href="javascript:void(0);" onmouseover="return overlib('Help messages will appear here.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
		links for help throughout the application.		
		</b>

</td></tr>