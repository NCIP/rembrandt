<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<tr class="report"><td>
<form>
<table border="0" cellspacing="4" cellpadding="3" width="100%">
	<tr>
		<td>
		<strong>Add to query:</strong><br><html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
		<blockquote>
		<br><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Gene Expression Analysis" onclick="javascript:location.href='geneExpression.do';">
		&nbsp; <b class="message">- (0) Gene Expression Analysis Queries</b>
		<br><br><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Comparitive Genomic Analysis" onclick="javascript:location.href='comparitiveGenomic.do';">
		&nbsp; <b class="message">- (0) Comparitive Genomic Analysis Queries</b>
		<Br><br><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Clinical Study Analysis" onclick="javascript:location.href='clinicalData.do';">
		&nbsp;<b class="message">- (0) Clinical Study Analysis Queries</b>
		<!--
		<Br><br><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="MRI Imaging Analysis" onclick="javascript:location.href='mriImage.html';">
		&nbsp;<b class="message">- (0) MRI Imaging Analysis Queries</b>
		<Br><br><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="FISH Data Analysis" onclick="javascript:location.href='refine.do';">
		&nbsp;<b class="message">- (0) FISH Data Analysis Queries</b>
		-->
		</blockquote>
	</td>
	</tr>
	<tr>
		<td><strong>I am satisfied with my query and would like to finalize:</strong><br />
			<blockquote>
				<input type="button" class="xbutton" value="Finalize query" onclick="location.href='refinecheck.do'">
			</blockquote>
		</td>
	</tr>
				
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
</form>

</td></tr>
