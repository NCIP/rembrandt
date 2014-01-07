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
<s:checkbox id="onStudyRadiation" class="radio" name="clinicalDataForm.onStudyRadiation" value="Specify"/>
<label for="onStudyRadiation">Radiation&nbsp;&nbsp;</label>
<label for="onStudyRadiationType">&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;</label>

<s:select id="onStudyRadiationType" name="clinicalDataForm.onStudyRadiationType" 
list="clinicalDataForm.onStudyRadiationTypeColl" listKey="value" listValue="label" />
<s:actionerror name="onStudyRadiationType"/>

<br>
<s:checkbox id="onStudyChemo" class="radio" name="clinicalDataForm.onStudyChemo" value="Specify"/>
<label for="onStudyChemo">Chemo&nbsp;&nbsp;</label>
<label for="onStudyChemoType">- agent:&nbsp;</label>
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<s:select id="onStudyChemoType" name="clinicalDataForm.onStudyChemoType" 
list="clinicalDataForm.onStudyChemoAgentTypeColl" listKey="value" listValue="label" />
<s:actionerror name="onStudyChemoType"/>

<Br>
<s:checkbox id="onStudySurgery" class="radio" name="clinicalDataForm.onStudySurgery"  value="Specify"/>
<label for="onStudySurgery">Surgery&nbsp;&nbsp;</label>
<label for="onStudySurgeryTitle">&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;</label>

<s:select sid="onStudySurgeryTitle" name="clinicalDataForm.onStudySurgeryTitle" 
list="clinicalDataForm.onStudySurgeryTitleColl" listKey="value" listValue="label" />
<s:actionerror name="onStudySurgeryTitle"/>

<Br>
<label for="onStudySurgeryOutcome">&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;</label>
<s:select id="onStudySurgeryOutcome" name="clinicalDataForm.onStudySurgeryOutcome" 
list="clinicalDataForm.onStudySurgeryOutcomeColl" listKey="value" listValue="label" />
<s:actionerror name="onStudySurgeryOutcome"/>


</fieldset>