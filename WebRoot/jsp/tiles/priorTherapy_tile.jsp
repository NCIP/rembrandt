
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	     String act = request.getParameter("act");
%>

<fieldset class="gray">
<legend class="red">Prior Therapy
<app:help help="Future implementation"/>
</legend><br />	
<input type="checkbox" name="radiation"  class="radio"  onclick="javascript:document.forms[0].rad.disabled=(!(document.forms[0].rad.disabled));">Radiation&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;
<html:select property="radiationType" >
<html:optionsCollection property="radiationTypeColl" />
</html:select><html:errors property="radiationType"/>

<br>
<input name="chemo" type="checkbox" class="radio"  onclick="javascript:document.forms[0].chemotherapy.disabled=(!(document.forms[0].chemotherapy.disabled));">Chemo&nbsp;&nbsp;
- agent:&nbsp;
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<html:select property="chemoType" >
<html:optionsCollection property="chemoAgentTypeColl" />
</html:select><html:errors property="chemoType"/>

<Br>
<input name="sugery" type="checkbox" class="radio" 
 onclick="javascript:document.forms[0].surgery.disabled=(!(document.forms[0].surgery.disabled));">Surgery&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;
<html:select property="surgeryTitle" >
<html:optionsCollection property="surgeryTitleColl" />
</html:select><html:errors property="surgeryTitle"/>

&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;
<html:select property="surgeryOutcome" >
<html:optionsCollection property="surgeryOutcomeColl" />
</html:select><html:errors property="surgeryOutcome"/>


</fieldset>