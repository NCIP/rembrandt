
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	     String act = request.getParameter("act");
%>

<fieldset class="gray">
<legend class="red">OnStudy Therapy
<app:help help="Future implementation"/>
</legend><br />	
<input type="checkbox" name="onStudyRadiation"  class="radio"  onclick="javascript:document.forms[0].rad.disabled=(!(document.forms[0].rad.disabled));">Radiation&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;
<html:select property="onStudyRadiationType" >
<html:optionsCollection property="onStudyRadiationTypeColl" />
</html:select><html:errors property="onStudyRadiationType"/>

<br>
<input name="onStudyChemo" type="checkbox" class="radio"  onclick="javascript:document.forms[0].chemotherapy.disabled=(!(document.forms[0].chemotherapy.disabled));">Chemo&nbsp;&nbsp;
- agent:&nbsp;
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<html:select property="onStudyChemoType" >
<html:optionsCollection property="onStudyChemoAgentTypeColl" />
</html:select><html:errors property="onStudyChemoType"/>

<Br>
<input name="onStudySurgery" type="checkbox" class="radio" 
 onclick="javascript:document.forms[0].surgery.disabled=(!(document.forms[0].surgery.disabled));">Surgery&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;
<html:select property="onStudySurgeryTitle" >
<html:optionsCollection property="onStudySurgeryTitleColl" />
</html:select><html:errors property="onStudySurgeryTitle"/>

&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;
<html:select property="onStudySurgeryOutcome" >
<html:optionsCollection property="onStudySurgeryOutcomeColl" />
</html:select><html:errors property="onStudySurgeryOutcome"/>


</fieldset>