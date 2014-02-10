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
<legend class="red">Prior Therapy
<app:cshelp topic="clinical_Prior_tooltip" text="[?]"/>   
<!-- <app:help help="Specify the therapy received before enrollment in the current study"/>-->
</legend><br />	
<s:checkbox name="form.radiation" value="Specify" id="radiation" theme="simple" />

<label for="radiation">Radiation&nbsp;&nbsp;</label>
<label for="radiationType">&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;</label>

<s:select id="radiationType" name="form.radiationType" list="form.radiationTypeColl" listKey="value" listValue="label" theme="simple" />
<s:fielderror fieldName="radiationType"/>

<br>
<s:checkbox id="chemo" class="radio" name="form.chemo" value="Specify" theme="simple" />
<label for="chemo">Chemo&nbsp;&nbsp;</label>
<label for="chemoType">- agent:&nbsp;</label>
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<s:select id="chemoType" name="form.chemoType" list="form.chemoAgentTypeColl" listKey="value" listValue="label"/>
<s:fielderror fieldName="chemoType"/>

<Br>
<s:checkbox id="surgery" class="radio" name="form.surgery" value="Specify" theme="simple" />
<label for="surgery">Surgery&nbsp;&nbsp;</label>
<label for="surgeryTitle">&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;</label>
<s:select id="surgeryTitle" name="form.surgeryTitle" list="form.surgeryTitleColl" listKey="value" listValue="label" theme="simple"/>
<s:fielderror fieldName="surgeryTitle"/>

<Br>
<label for="surgeryOutcome">&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;</label>
<s:select id="surgeryOutcome" name="form.surgeryOutcome" list="form.surgeryOutcomeColl" listKey="value" listValue="label"/>
<s:fielderror fieldName="surgeryOutcome"/>


</fieldset>