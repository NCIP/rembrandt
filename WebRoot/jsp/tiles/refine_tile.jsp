<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<html:form action="refineQuery.do">
<html:errors/>
<fieldset class="grayRefine">
<legend class="red">Step 1: Refine your result set</legend>
	
    <input type="radio" name="queryOption" class="radio" value="standard" checked="true"  onclick="javascript:checkToggle(this, 'qrows');"/>Please refine your results by grouping the queries<br />
    <div id="qrows">
	<table align="center" border="0" width="95%" cellpadding="2" cellspacing="1" id="rosso">
		<tr>
			<td colspan="4" class="message">
				Group Your Queries<BR>
			</td>
		</tr>
		<tr>
			<td class="message" width="10%">(</td>
			<td class="message" width="30%">Query Name</td>
			<td class="message" width="10%">)</td>
			<td class="message" width="10%">and/or</td>
		</tr>
		<!-- BEGIN  Selected Queries -->
		<nested:nest>
				<!-- Begin looping over SelectedQueries -->
				<nested:iterate name="refineQueryForm" 
								property="selectedQueries" 
								id="selectedQuery"  
								indexId="index" 
								type="gov.nih.nci.nautilus.ui.bean.SelectedQueryBean">
				  <%@ include file="/jsp/selectQueryRow.jsp" %>
				</nested:iterate>
				<!-- END looping over SelectedQueries -->
		</nested:nest>
		<!-- End  Selected Queries -->
	</table><br />
	</div>
	<input type="radio" name="queryOption" class="radio" value="allgenes"  onclick="javascript:checkToggle(this, 'qrows');" />Please select an "All Genes" query
    <html:select property="allGeneQuery">
     		<option/>
		    <html:optionsCollection property="allGenesQueries" label="queryName" value="queryName" />
	 </html:select>

</fieldset>
<!--Display buttons here to add later-->			
<!--<div class="midButtons">
	<b class="message">[add more rows]</b><br />
		<br><input type="reset" value="reset query" class="sbutton">
</div>	-->


<fieldset class="grayRefine">
<legend class="red">Step 2: Validate your query</legend>
	<table border="0" cellpadding="2" cellspacing="2">
		<tr>
			<td align="center">&nbsp;&nbsp;&nbsp;
			<!--JavaScript added here to reset the response target back to the submitting window-->
			<html:submit styleId="validateButton" styleClass="xbutton"  property="method" onclick="javascript:document.forms[0].target = '_self';">
				<bean:message key="RefineQueryAction.validateButton"/>
			</html:submit>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			<td align="right">
				<html:textarea property="queryText"  style="width:300px; height:40px;"></html:textarea>
			</td>
			
		</tr>
		
	</table>
		
</fieldset>	
	
<fieldset class="grayRefine">
<legend class="red">Step 3 (optional): Select Result set</legend>
&nbsp;&nbsp;&nbsp;Select Result set to apply the above Query:
    <html:select name="refineQueryForm" property="selectedResultSet">
    	<option></option>  
    		<html:options name="refineQueryForm" property="resultSets"/>
  	</html:select>
</fieldset>

<fieldset class="grayRefine">
<legend class="red">Step 4: Please select a View</legend>	
	<table width="100%" border="0">
		<tr><td>
				<html:select property="compoundView" onchange="">
				    <html:optionsCollection property="compoundViewColl" />
				</html:select><html:errors property="compoundView"/>
			</td>
		</tr>
	</table>
	<br>
	
</fieldset>

<fieldset class="grayRefine">
<legend class="red">Step 5: Please name your result set (Optional)</legend>
	 <table border="0">
		<tr>
			<td>
				<html:text property="resultSetName" size="66" onchange="">
				</html:text>&nbsp;&nbsp;&nbsp;
				<logic:equal name="refineQueryForm" property="runFlag" value="yes">
					<html:submit styleClass="xbutton"  styleId="storeResultsButton"property="method" onclick="javascript:document.forms[0].target = '_self';">
						<bean:message key="RefineQueryAction.storeResultsButton"/>
					</html:submit>
				</logic:equal>
				<logic:notEqual name="refineQueryForm" property="runFlag" value="yes">
					<html:button styleClass="xbutton" property="method" disabled="true">
						<bean:message key="RefineQueryAction.storeResultsButton"/>
					</html:button>
				</logic:notEqual>
			</td>
		</tr>
	</table>
</fieldset>	
	

<fieldset class="grayRefine">
<legend class="red">Step 6: Run report or return to previous screen</legend>
			<br />
				<html:button property="backbutton" styleClass="xbutton" value="<< Back" 
					onclick="javascript:history.back();"/>&nbsp;&nbsp
				<!--check to see if query has been validated and the runFlag has been set on the form-->
				<logic:equal name="refineQueryForm" property="runFlag" value="yes" >
					<!-- JavaScript here is to create a popup for the ReportResults -->
					<html:submit styleClass="xbutton" styleId="runReportButton" property="method" onclick="javascript:return formNewTargetSimple('_report', 770, 550);">
						<bean:message key="RefineQueryAction.runReportButton"/>
					</html:submit>
				</logic:equal> 
</fieldset>
</html:form>
	

<script language="JavaScript">
function operandChange(){
  document.forms[0].validateButton.value="ChangedOperand";
  document.forms[0].validateButton.click();
}
</script>	
		