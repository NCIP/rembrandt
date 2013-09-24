<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	     String act = request.getParameter("act");
%>

<fieldset class="gray">
<legend class="red">Prior Therapy
<app:cshelp topic="clinical_Prior_tooltip" text="[?]"/>   
<!-- <app:help help="Specify the therapy received before enrollment in the current study"/>-->
</legend><br />	
<html:checkbox styleId="radiation" styleClass="radio" property="radiation" value="Specify"/><label for="radiation">Radiation&nbsp;&nbsp;</label>
<label for="radiationType">&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;</label>
<html:select styleId="radiationType" property="radiationType" >
<html:optionsCollection property="radiationTypeColl" />
</html:select><html:errors property="radiationType"/>

<br>
<html:checkbox styleId="chemo" styleClass="radio" property="chemo"  value="Specify"/><label for="chemo">Chemo&nbsp;&nbsp;</label>
<label for="chemoType">- agent:&nbsp;</label>
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<html:select styleId="chemoType" property="chemoType" >
<html:optionsCollection property="chemoAgentTypeColl" />
</html:select><html:errors property="chemoType"/>

<Br>
<html:checkbox styleId="surgery" styleClass="radio" property="surgery"  value="Specify"/><label for="surgery">Surgery&nbsp;&nbsp;</label>
<label for="surgeryTitle">&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;</label>
<html:select styleId="surgeryTitle" property="surgeryTitle" >
<html:optionsCollection property="surgeryTitleColl" />
</html:select><html:errors property="surgeryTitle"/>

<Br>
<label for="surgeryOutcome">&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;</label>
<html:select styleId="surgeryOutcome" property="surgeryOutcome" >
<html:optionsCollection property="surgeryOutcomeColl" />
</html:select><html:errors property="surgeryOutcome"/>


</fieldset>