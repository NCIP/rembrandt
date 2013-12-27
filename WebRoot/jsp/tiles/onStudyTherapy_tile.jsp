<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	     String act = request.getParameter("act");
%>

<fieldset class="gray">
<legend class="red">OnStudy Therapy
<!-- <app:help help="Specify the therapy received after enrollment the current study"/>-->
<app:cshelp topic="clinical_Onstudy_tooltip" text="[?]"/>   
</legend><br />	
<html:checkbox styleId="onStudyRadiation" styleClass="radio" property="onStudyRadiation"  value="Specify"/><label for="onStudyRadiation">Radiation&nbsp;&nbsp;</label>
<label for="onStudyRadiationType">&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;</label>
<html:select styleId="onStudyRadiationType" property="onStudyRadiationType" >
<html:optionsCollection property="onStudyRadiationTypeColl" />
</html:select><html:errors property="onStudyRadiationType"/>

<br>
<html:checkbox styleId="onStudyChemo" styleClass="radio" property="onStudyChemo" value="Specify"/><label for="onStudyChemo">Chemo&nbsp;&nbsp;</label>
<label for="onStudyChemoType">- agent:&nbsp;</label>
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<html:select styleId="onStudyChemoType" property="onStudyChemoType" >
<html:optionsCollection property="onStudyChemoAgentTypeColl" />
</html:select><html:errors property="onStudyChemoType"/>

<Br>
<html:checkbox styleId="onStudySurgery" styleClass="radio" property="onStudySurgery"  value="Specify"/><label for="onStudySurgery">Surgery&nbsp;&nbsp;</label>
<label for="onStudySurgeryTitle">&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;</label>
<html:select styleId="onStudySurgeryTitle" property="onStudySurgeryTitle" >
<html:optionsCollection property="onStudySurgeryTitleColl" />
</html:select><html:errors property="onStudySurgeryTitle"/>

<Br>
<label for="onStudySurgeryOutcome">&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;</label>
<html:select styleId="onStudySurgeryOutcome" property="onStudySurgeryOutcome" >
<html:optionsCollection property="onStudySurgeryOutcomeColl" />
</html:select><html:errors property="onStudySurgeryOutcome"/>


</fieldset>