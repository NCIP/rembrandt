<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Clinical Evaluation

</legend>
<%
	String act = request.getParameter("act");
	String kHelp = request.getParameter("act") + "_Karnofsky_tooltip";
	String lHelp = request.getParameter("act") + "_Lansky_tooltip";
	String nHelp = request.getParameter("act") + "_Neuro_tooltip";
	String mHelp = request.getParameter("act") + "_MRI_tooltip";
%>
	
<s:checkbox id="karnofsky" class="radio" name="clinicalDataForm.karnofsky" value="Specify"/>
<label for="karnofsky">Karnofsky</label>
 	<!-- <app:help help="Score from the Karnofsky Performance status scale, representing the functional capabilities of a person."/>-->
 	<app:cshelp topic="<%=kHelp%>" text="[?]"/>    
    <s:select id="karnofskyType" name="clinicalDataForm.karnofskyType" 
    list="clinicalDataForm.karnofskyTypeColl" listKey="value" listValue="label" />
	<label for="karnofskyType">&nbsp;&nbsp;</label>
	
<s:checkbox id="lansky" class="radio" name="clinicalDataForm.lansky" disabled="true" value="Specify"/>
<label for="lansky">Lansky</label>
    <!--  <app:help help="Score from an enumerated set of values representing performance status according to the Lansky scale. The Lansky scale is intended for use only with subjects under 12 years of age."/>-->
    <app:cshelp topic="<%=lHelp%>" text="[?]"/>    
    <s:select id="lanskyType" name="clinicalDataForm.lanskyType" 
    list="clinicalDataForm.lanskyTypeColl" listKey="value" listValue="label" />
	<label for="lanskyType">&nbsp;&nbsp;</label>
	
<s:checkbox id="neuroExam" class="radio" name="clinicalDataForm.neuroExam" value="Specify"/>
<label for="neuroExam">Neuro Exam</label>
    <!-- <app:help help="The participants neurologic exam score. Score definitions: +2: DEFINITELY BETTER, +1: POSSIBLY BETTER, 0:STABLE, -1: POSSIBLY WORSE, -2: DEFINITELY WORSE"/>-->
    <app:cshelp topic="<%=nHelp%>" text="[?]"/>    
    <s:select id="neuroExamType" name="clinicalDataForm.neuroExamType" 
     list="clinicalDataForm.neuroExamTypeColl" listKey="value" listValue="label" />
	<label for="neuroExamType">&nbsp;&nbsp;</label>

<s:checkbox id="mri" class="radio" name="clinicalDataForm.mri" value="Specify"/>
<label for="mri">MRI</label>
    <app:cshelp topic="<%=mHelp%>" text="[?]"/>    
    <s:select id="mriType" name="clinicalDataForm.mriType" 
     list="clinicalDataForm.mriTypeColl" listKey="value" listValue="label" />
<label for="mriType">&nbsp;&nbsp;</label>
	

</fieldset>