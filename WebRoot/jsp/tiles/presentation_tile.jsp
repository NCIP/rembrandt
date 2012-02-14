<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<!-- <html:form action="present.do">-->


<br>
		<B>1. Select Presentation Focus</B>&nbsp;&nbsp;
		<a href="javascript:void(0);" title="For 'Sample View' all results are for returned samples that meets the query criteria and  'GeneView'  returns results for every Gene that meets the query criteria or select a previouly saved presentation template" >[?]</a><br>
		
		<logic:equal name="selectPresentationForm" property="currentView" value="sample">
			<br>
			Sample View
			<br>
		</logic:equal>
		<logic:equal name="selectPresentationForm" property="currentView" value="geneSample">
			<br>
			Gene Expression Sample View
			<br>
		</logic:equal>
		<logic:equal name="selectPresentationForm" property="currentView" value="geneDisease">
			<br>
			Gene Expression Disease View
			<br>
		</logic:equal>
		<logic:equal name="selectPresentationForm" property="currentView" value="copynumber">
			<br>
			Copy Number Sample View
			<br>
		</logic:equal>
			<div class="pOptions">
			Select which elements you want to appear as columns in your Report (in order). <a href="javascript:void(0);" title="Select the elements you want to appear in the report from the left box, and click '>>' to move them to the right box.  The elements in the right box will appear in the report.">[?]</a>
					<table>
						<tbody>
						<tr>
							<td>
								<html:select property="listFrom" multiple="multiple" size="10" style="width: 150px;">
									    <html:optionsCollection property="viewPresentList" />
								</html:select>

							</td>
							<td align="center" valign="middle"><br><br><br>
						  	 <input onclick="move(this.form.listTo,this.form.listFrom)" value="&lt;&lt;" title="Remove selection from the report." type="button">
							 <input onclick="move(this.form.listFrom,this.form.listTo)" value="&gt;&gt;" title="Add selection to the report." type="button">
							</td>
							<td>
 							 <html:select property="listTo" multiple="multiple" size="10" style="width: 150px;">
							 </html:select>

							</td>
							<td style="padding: 5px; vertical-align: middle;" valign="middle">
							 <a href="javascript:void(0);" title="You may highlight an item in the right box and select to move it up or down in the list.  This will control the order in which the field is displayed in the report.">[?]</a><br><br>
							 <input style="width: 20px; font-size: 16px; font-weight: normal;" value="/\" title="Move the selection up." onclick="javascript:moveUpList(list2);" type="button"><br><br><!-- value="&uarr;" -->
							 <input style="width: 20px; font-size: 16px; font-weight: normal;" value="\/" title="Move the selection down." onclick="javascript:moveDownList(list2);" type="button">
							</td>
						</tr>
						</tbody>
					</table>
					<Br><a href="JavaScript:window.new('output_sample.html')">sample view example</a>
		</div>
		<br><br>
		Apply a saved Template 	<a href="javascript:void(0);" title="You can use a previously saved template">[?]</a>
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
	
	<html:button property="method2" styleClass="xbutton" value="back" onclick="javascript:history.back();"/>
	<html:button property="runreport" styleClass="xbutton" value="Run Report" onclick="JavaScript:selAndSubmit('runReport')"/>

	</p>
	</div>
	<html:hidden property="method" />

 </html:form> 
	
	<br>

	<!-- value="&uarr;" -->
	<br>