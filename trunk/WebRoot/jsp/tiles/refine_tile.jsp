<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<html:form action="refineQuery.do">

<script type="text/javascript">Help.insertHelp("Refine_query", " align='right'", "padding:2px;");</script><br clear="all"/>

<html:errors/>
<fieldset class="grayRefine">
<legend class="red">Step 1: Refine your result set</legend>
	
    <input type="radio" name="isAllGenesQuery" class="radio" value="false" checked="true" onclick="javascript:onRadio(this, 0);"/>Please refine your results by grouping the queries<br />
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
								type="gov.nih.nci.rembrandt.web.bean.SelectedQueryBean">
				  <%@ include file="/jsp/selectQueryRow.jsp" %>
				</nested:iterate>
				<!-- END looping over SelectedQueries -->
		</nested:nest>
		<!-- End  Selected Queries -->
	</table><br />
	</div>
	<input type="radio" name="isAllGenesQuery" class="radio" value="true"  onclick="javascript:onRadio(this, 1);" />Please select an "All Genes" query
    <html:select property="allGeneQuery" disabled="true">
     		<option/>
		    <html:optionsCollection property="allGenesQueries" label="queryName" value="queryName" />
	 </html:select>

</fieldset>
<br clear="all"/>
<!--Step 2-->
<fieldset class="grayRefine">
<legend class="red">Step 2: Select Result set (mandatory for "All Genes" queries)</legend>
&nbsp;&nbsp;&nbsp;Select Result set to apply the above Query:

<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script language="javascript">
	function updateG(){
    	UserListHelper.getGenericListNamesFromString("PatientDID",createSampleList);
	}
	function createSampleList(data){   	
    	DWRUtil.removeAllOptions("srs");

   		DWRUtil.addOptions("srs", eval("[ { name:'', id:'none' } ]") , "name", "id");

    	DWRUtil.addOptions("srs", data);
	}
	

</script>

    <html:select styleId="srs" name="refineQueryForm" property="selectedResultSet" onfocus="updateG();" style="width:150px;">
    	<option value=""></option>  
    		<html:options name="refineQueryForm" property="resultSets"/>
  	</html:select>
  	<!--
  	<html:submit styleId="validateButton" styleClass="xbutton"  property="method" onclick="javascript:document.forms[0].target = '_self';">
				<bean:message key="RefineQueryAction.validateButton"/>
	</html:submit>
	-->
	<!-- 
	<input type="button" value="refresh" onclick="javascript:location.href='refinecheck.do'">
	-->
</fieldset>
<br clear="all"/>
<!--Step 3-->
<fieldset class="grayRefine">
<legend class="red">Step 3: Validate your query</legend>
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
<br clear="all"/>
<!--Step 4-->
<fieldset class="grayRefine">
<legend class="red">Step 4: Please select a View</legend>	
	<table width="100%" border="0">
		<tr><td>
				<html:select property="compoundView" onchange="" style="width:300px">
				    <html:optionsCollection property="compoundViewColl" />
				</html:select><html:errors property="compoundView"/>
			</td>
		</tr>
	</table>
	<br>
	
</fieldset>
<br clear="all"/>
<!--Step 5-->
<fieldset class="grayRefine">
<legend class="red">Step 5: Please select data source(s) to filter the query by ( Optional )</legend>	
	<table width="100%" border="0">
		<tr><td>
				<html:select property="instituteView" multiple="true" onchange="">
					 <!--  <option value ="ALL" selected="selected">ALL</option>  -->
				    <html:optionsCollection property="institueViewColl" label="displayName" value="displayName"/>
				</html:select><html:errors property="instituteView"/>
			</td>
		</tr>
	</table>
	<br>
	
</fieldset>
<br clear="all"/>
<!--Step 6-->
<fieldset class="grayRefine">
<legend class="red">Step 6: Run report or return to previous screen</legend>
			<br />
			    <input type="button" id="clearButton" class="xbutton" value="Clear" onclick="javascript:location.href='refinecheck.do'"/>
				<html:button property="backbutton" styleClass="xbutton" value="<< Back" 
					onclick="javascript:history.back();"/>&nbsp;&nbsp
				<!--check to see if query has been validated and the runFlag has been set on the form-->
				<logic:equal name="refineQueryForm" property="runFlag" value="yes" >
					<!-- JavaScript here is to create a popup for the ReportResults -->
					<% //<html:submit styleClass="xbutton" styleId="runReportButton" property="method" onclick="javascript:return formNewTargetSimple('_report', 770, 550);"> %>
					<html:submit styleClass="xbutton" styleId="runReportButton" property="method">
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
		