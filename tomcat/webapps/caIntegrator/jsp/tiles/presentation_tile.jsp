<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>

<!-- <html:form action="present.do">-->

<br>
		<B>1. Select Presentation Focus</B>&nbsp;&nbsp;
		<a href="javascript:void(0);" onmouseover="return overlib('For \'Sample View\' all results are for returned samples that meets the query criteria and  \'GeneView\'  returns results for every Gene that meets the query criteria or select a previouly saved presentation template', CAPTION, 'Help');" onmouseout="return nd();">[?]</a><br>
		Sample View
		<br>
		<div class="pOptions">
		Select which elements you want to appear as columns in the Sample View Report (in order). <a href="javascript:void(0);" onmouseover="return overlib('Select the elements you want to appear in the report from the left box, and click \'>>\' to move them to the right box.  The elements in the right box will appear in the report.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
				<table>
					<tbody><tr>
						<td>
							<html:select property="sampleListFrom" multiple="multiple" size="10" style="width: 150px;">
								<html:option value="disease">Disease</html:option>
								<html:option value="grade">Grade</html:option>
								<html:option value="survival">Survival Range</html:option>
								<html:option value="gender">Gender</html:option>
								<html:option value="age">Age at Diagnosis</html:option>
								<html:option value="geninstitute">Generating Institute</html:option>
								<html:option value="occurance">Occurance</html:option>
								<html:option value="gexpdata">Gene Expression data</html:option>
								<html:option value="copynumdata">Copy Number data</html:option>
							</html:select>

						</td>
						<td align="center" valign="middle"><br><br><br>
						<input onclick="move(this.form.sampleListTo,this.form.sampleListFrom)" value="&lt;&lt;" onmouseover="return overlib('Remove selection from the report.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" type="button">
						<input onclick="move(this.form.sampleListFrom,this.form.sampleListTo)" value="&gt;&gt;" onmouseover="return overlib('Add selection to the report.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" type="button">
						</td>
						<td>

						<html:select property="sampleListTo" multiple="multiple" size="10" style="width: 150px;">
						</html:select>

						</td>
						<td style="padding: 5px; vertical-align: middle;" valign="middle">
						<a href="javascript:void(0);" onmouseover="return overlib('You may highlight an item in the right box and select to move it up or down in the list.  This will control the order in which the field is displayed in the report.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a><br><br>
						<input style="width: 20px; font-size: 16px; font-weight: normal;" value="/\" onmouseover="return overlib('Move the selection up.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" onclick="javascript:moveUpList(list2);" type="button"><br><br><!-- value="&uarr;" -->
						<input style="width: 20px; font-size: 16px; font-weight: normal;" value="\/" onmouseover="return overlib('Move the selection down.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" onclick="javascript:moveDownList(list2);" type="button">
						</td>
					</tr>
				</tbody></table>
				<Br><a href="JavaScript:window.new('output_sample.html')">sample view example</a>
		</div>
		Gene View
		<blockquote>
			Filter by: &nbsp;&nbsp;


<!-- Prashant' code -->

			<html:select property="geneFilterBy" onchange="JavaScript:setDispMethod('changegenelist')" style="width: 150px;">
					<html:option value=""></html:option>
					<html:option value="geneexp">Gene Expression</html:option>
					<html:option value="copynumber">Copy Number</html:option>
			</html:select>
			<Br><br>
			View By:&nbsp;&nbsp;

			<html:select property="geneViewBy" style="width: 150px;">
					<html:option value=""></html:option>
					<html:option value="samples">Samples</html:option>
					<html:option value="diseases">Diseases</html:option>
			</html:select>

		</blockquote>
		<div class="pOptions">
		Select which elements you want to appear as columns in the Sample View Report (in order). <a href="javascript:void(0);" onmouseover="return overlib('Select the elements you want to appear in the report from the left box, and click \'>>\' to move them to the right box.  The elements in the right box will appear in the report.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
				<table>
					<tbody><tr>
						<td>
							<html:select property="geneListFrom" multiple="multiple" size="10" style="width: 150px;">
							</html:select>

						</td>
						<td align="center" valign="middle"><br><br><br>
						<input onclick="move(this.form.sampleListTo,this.form.sampleListFrom)" value="&lt;&lt;" onmouseover="return overlib('Remove selection from the report.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" type="button">
						<input onclick="move(this.form.sampleListFrom,this.form.sampleListTo)" value="&gt;&gt;" onmouseover="return overlib('Add selection to the report.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" type="button">
						</td>
						<td>

						<html:select property="geneListTo" multiple="multiple" size="10" style="width: 150px;">
						</html:select>

						</td>
						<td style="padding: 5px; vertical-align: middle;" valign="middle">
						<a href="javascript:void(0);" onmouseover="return overlib('You may highlight an item in the right box and select to move it up or down in the list.  This will control the order in which the field is displayed in the report.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a><br><br>
						<input style="width: 20px; font-size: 16px; font-weight: normal;" value="/\" onmouseover="return overlib('Move the selection up.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" onclick="javascript:moveUpList(list2);" type="button"><br><br><!-- value="&uarr;" -->
						<input style="width: 20px; font-size: 16px; font-weight: normal;" value="\/" onmouseover="return overlib('Move the selection down.', CAPTION, 'Help', DELAY, 500);" onmouseout="return nd();" onclick="javascript:moveDownList(list2);" type="button">
						</td>
					</tr>
				</tbody></table>
				<Br><a href="JavaScript:window.new('output_sample.html')">sample view example</a>
		</div>
		<br><br>
		Apply a saved Template 	<a href="javascript:void(0);" onmouseover="return overlib('You can use a previously saved template', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
		&nbsp;Upload Template:&nbsp;<input type="file">
		<br>
		<br>
		<B>2. Preview the report</b>&nbsp;&nbsp;  
		<input class="sbutton" value="preview" onclick="javascript:spawn('preview.html', 780, 300);" type="button"><br><br>
		<B>3. Save/Download the Presentation Template (optional)</b>&nbsp;&nbsp; <input class="sbutton" value="Save As..." type="button">

	<br>
	<br>
	<div>
	<p style="text-align:center">
	<input type="button" class="xbutton" value="back" onclick="javascript:history.back();">&nbsp;&nbsp;
	<input type="button" class="xbutton" value="Run Report" onclick="javascript:location.href='output_gene.html';">
	</p>
	</div>
	<html:hidden property="method" />

 </html:form> 
	
	<br>

	<!-- value="&uarr;" -->
	<br>