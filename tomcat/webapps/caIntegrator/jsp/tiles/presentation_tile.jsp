<br>
	<B>1. Select Presentation Focus</B>&nbsp;&nbsp;
		<a href="javascript:void(0);" onmouseover="return overlib('For \'Sample View\' all results are for returned samples that meets the query criteria and  \'GeneView\'  returns results for every Gene that meets the query criteria or select a previouly saved presentation template', CAPTION, 'Help');" onmouseout="return nd();">[?]</a><br>
		<div class="pOptions">
		<input class="radio" name="Focus" value="yes" checked="checked" type="radio">Sample View
		<br>
		<div class="pOptions">
		Select which elements you want to appear as columns in the Sample View Report (in order). <a href="javascript:void(0);" onmouseover="return overlib('Select the elements you want to appear in the report from the left box, and click \'>>\' to move them to the right box.  The elements in the right box will appear in the report.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
				<form name="combo_box">
				<table>
					<tbody><tr>
						<td>
						<select multiple="multiple" size="10" name="list1" style="width: 150px;">
							<option>Disease Type</option>
							<option value="9">Treatment</option>
							<option value="7">Survival Range</option>
							<option value="77">Gender</option>
							<option value="36">Age at Diagnosis</option>
							<option value="87">Study Dataset</option>
							<option value="66">Generating Institute</option>
							<option value="34">Prior Therapy</option>
							<option value="24">Tumor Weight</option>
							<option value="30">Occurance</option>
							<option value="32">GeneExpression</option>
							<option value="15">CopyNumber</option>
						</select>
						</td>
						<td align="center" valign="middle"><br><br><br>
						<input onclick="move(this.form.list2,this.form.list1)" value="&lt;&lt;" onmouseover="return overlib('Remove selection from the report.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" type="button">
						<input onclick="move(this.form.list1,this.form.list2)" value="&gt;&gt;" onmouseover="return overlib('Add selection to the report.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" type="button">
						</td>
						<td>
						<select multiple="multiple" size="10" name="list2" style="width: 150px;"></select>
						</td>
						<td style="padding: 5px; vertical-align: middle;" valign="middle">
						<a href="javascript:void(0);" onmouseover="return overlib('You may highlight an item in the right box and select to move it up or down in the list.  This will control the order in which the field is displayed in the report.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a><br><br>
						<input style="width: 20px; font-size: 16px; font-weight: normal;" value="/\" onmouseover="return overlib('Move the selection up.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" onclick="javascript:moveUpList(list2);" type="button"><br><br><!-- value="&uarr;" -->
						<input style="width: 20px; font-size: 16px; font-weight: normal;" value="\/" onmouseover="return overlib('Move the selection down.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" onclick="javascript:moveDownList(list2);" type="button">
						</td>
					</tr>
				</tbody></table>
				<Br><a href="output_sample.html">sample view example</a>
			</form>
		</div>
		<input class="radio" name="Focus" value="geneView" type="radio">Gene View
		<blockquote>
			Filter by: &nbsp;&nbsp;
			<select>
				<option> </option>
				<option>Gene Expression</option>
				<option>Copy Number</option>
			</select>
			<Br><br>
			View By:&nbsp;&nbsp;
			<select>
				<option> </option>
				<option>Samples</option>
				<option>Diseases</option>
			</select>
			
		</blockquote>
		<br><br>
		<input class="radio" name="Focus" value="template" type="radio">Apply a saved Template 	<a href="javascript:void(0);" onmouseover="return overlib('You can use a previously saved template', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
		&nbsp;Upload Template:&nbsp;<input type="file">
		<br>
		<br>
		</div>
		<B>2. Preview the report</b>&nbsp;&nbsp;  <input class="sbutton" value="preview" onclick="javascript:spawn('preview.html', 780, 300);" type="button"><br><br>
		<B>3. Save/Download the Presentation Template (optional)</b>&nbsp;&nbsp; <input class="sbutton" value="Save As..." type="button">
	
	<br>

	<!-- value="&uarr;" -->
	<br>