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
<s:checkbox name="clinicalDataForm.radiation" value="Specify" id="radiation"/>

<label for="radiation">Radiation&nbsp;&nbsp;</label>
<label for="radiationType">&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;</label>

<s:select id="radiationType" name="clinicalDataForm.radiationType" list="clinicalDataForm.radiationTypeColl" listKey="value" listValue="label"/>
<s:actionerror name="radiationType"/>

<br>
<s:checkbox id="chemo" class="radio" name="clinicalDataForm.chemo" value="Specify"/>
<label for="chemo">Chemo&nbsp;&nbsp;</label>
<label for="chemoType">- agent:&nbsp;</label>
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<s:select id="chemoType" name="clinicalDataForm.chemoType" list="clinicalDataForm.chemoAgentTypeColl" listKey="value" listValue="label"/>
<s:actionerror name="chemoType"/>

<Br>
<s:checkbox id="surgery" class="radio" name="clinicalDataForm.surgery" value="Specify"/>
<label for="surgery">Surgery&nbsp;&nbsp;</label>
<label for="surgeryTitle">&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;</label>
<s:select id="surgeryTitle" name="clinicalDataForm.surgeryTitle" list="clinicalDataForm.surgeryTitleColl" listKey="value" listValue="label"/>
<s:actionerror name="surgeryTitle"/>

<Br>
<label for="surgeryOutcome">&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;</label>
<s:select id="surgeryOutcome" name="clinicalDataForm.surgeryOutcome" list="clinicalDataForm.surgeryOutcomeColl" listKey="value" listValue="label"/>
<s:actionerror name="surgeryOutcome"/>


</fieldset>