<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:form action="refineQuery.do">
<tr class="report"><td>
<div class="steps">
<html:errors/>
<b>Step 1: Please refine your result set by grouping it</b>
	&nbsp;&nbsp;&nbsp;
	<table border="0" width="95%" cellpadding="2" cellspacing="1" id="rosso">
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
								type="gov.nih.nci.nautilus.ui.helper.SelectedQuery">
				  <%@ include file="/jsp/selectQueryRow.jsp" %>
				</nested:iterate>
				<!-- END looping over SelectedQueries -->
		</nested:nest>
		<!-- End  Selected Queries -->
	</table>
</div>
<!--Display buttons here to add later-->			
<!--<div class="midButtons">
	<b class="message">[add more rows]</b><br />
		<br><input type="reset" value="reset query" class="sbutton">
</div>	-->
<br/><br/>

<div class="steps">	
<b>Step 2: Validate your query</b><br>
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
		
</div>	
	
	
<div class="steps">
<b>Step 3: Please select a View</b>&nbsp;&nbsp;	
	<table width="100%" border="0">
		<tr><td>
				<html:select property="compoundView" onchange="">
				    <html:optionsCollection property="compoundViewColl" />
				</html:select><html:errors property="compoundView"/>
			</td>
		</tr>
	</table>
	<br>
	
</div>

<div class="steps">
	<b>Step 4: Please name your result set (Optional)</b>&nbsp;&nbsp;
	 <table border="0">
		<tr>
			<td>
				<html:text property="resultSetName" size="66" onchange="">
				</html:text>&nbsp;&nbsp;&nbsp;
				<html:submit styleClass="xbutton"  styleId="storeResultsButton"property="method" onclick="javascript:document.forms[0].target = '_self';">
					<bean:message key="RefineQueryAction.storeResultsButton"/>
				</html:submit>
			</td>
		</tr>
	</table>
</div>	
	<br>
<div class="steps">	
	<table border="0" width="100%">
		<tr>
			<td>
			<b>Step 5: Run report or return to previous screen</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="center"><br>
				<html:button property="backbutton" styleClass="xbutton" value="<< Back" 
					onclick="javascript:history.back();"/>&nbsp;&nbsp
				<!--check to see if query has been validated and the runFlag has been set on the form-->
				<logic:equal name="refineQueryForm" property="runFlag" value="yes" >
					<!-- JavaScript here is to create a popup for the ReportResults -->
					<html:submit styleClass="xbutton" styleId="runReportButton" property="method" onclick="javascript:return formNewTargetSimple('_report', 770, 550);">
						<bean:message key="RefineQueryAction.runReportButton"/>
					</html:submit>
				</logic:equal> 
			</td>
		</tr>
	</table>
</div>
</html:form>
</td></tr>	

<script language="JavaScript">
function operandChange(){
  document.forms[0].validateButton.value="ChangedOperand";
  document.forms[0].validateButton.click();
}
</script>	
		