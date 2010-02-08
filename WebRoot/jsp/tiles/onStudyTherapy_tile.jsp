
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	     String act = request.getParameter("act");
%>

<fieldset class="gray">
<legend class="red">OnStudy Therapy
<!-- <app:help help="Specify the therapy received after enrollment the current study"/>-->
<a href="javascript: Help.popHelp('clinical_Onstudy_tooltip');">[?]</a>    
</legend><br />	
<html:checkbox styleClass="radio" property="onStudyRadiation"  value="Specify"/>Radiation&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;
<html:select property="onStudyRadiationType" >
<html:optionsCollection property="onStudyRadiationTypeColl" />
</html:select><html:errors property="onStudyRadiationType"/>

<br>
<html:checkbox styleClass="radio" property="onStudyChemo" value="Specify"/>Chemo&nbsp;&nbsp;
- agent:&nbsp;
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<html:select property="onStudyChemoType" >
<html:optionsCollection property="onStudyChemoAgentTypeColl" />
</html:select><html:errors property="onStudyChemoType"/>

<Br>
<html:checkbox styleClass="radio" property="onStudySurgery"  value="Specify"/>Surgery&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;
<html:select property="onStudySurgeryTitle" >
<html:optionsCollection property="onStudySurgeryTitleColl" />
</html:select><html:errors property="onStudySurgeryTitle"/>

<Br>
&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;
<html:select property="onStudySurgeryOutcome" >
<html:optionsCollection property="onStudySurgeryOutcomeColl" />
</html:select><html:errors property="onStudySurgeryOutcome"/>


</fieldset>