<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
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
	
<html:checkbox styleId="karnofsky" styleClass="radio" property="karnofsky" value="Specify"/><label for="karnofsky">Karnofsky</label>
 	<!-- <app:help help="Score from the Karnofsky Performance status scale, representing the functional capabilities of a person."/>-->
 	<app:cshelp topic="<%=kHelp%>" text="[?]"/>    
    <html:select styleId="karnofskyType" property="karnofskyType">
	<html:optionsCollection property="karnofskyTypeColl" />
    </html:select>
	<label for="karnofskyType">&nbsp;&nbsp;</label>
	
<html:checkbox styleId="lansky" styleClass="radio" property="lansky" disabled="true" value="Specify"/><label for="lansky">Lansky</label>
    <!--  <app:help help="Score from an enumerated set of values representing performance status according to the Lansky scale. The Lansky scale is intended for use only with subjects under 12 years of age."/>-->
    <app:cshelp topic="<%=lHelp%>" text="[?]"/>    
    <html:select styleId="lanskyType" property="lanskyType">
	<html:optionsCollection property="lanskyTypeColl" />
    </html:select>	
	<label for="lanskyType">&nbsp;&nbsp;</label>
	
<html:checkbox styleId="neuroExam" styleClass="radio" property="neuroExam" value="Specify"/><label for="neuroExam">Neuro Exam</label>
    <!-- <app:help help="The participants neurologic exam score. Score definitions: +2: DEFINITELY BETTER, +1: POSSIBLY BETTER, 0:STABLE, -1: POSSIBLY WORSE, -2: DEFINITELY WORSE"/>-->
    <app:cshelp topic="<%=nHelp%>" text="[?]"/>    
    <html:select styleId="neuroExamType" property="neuroExamType">
	<html:optionsCollection property="neuroExamTypeColl" />
    </html:select>
	<label for="neuroExamType">&nbsp;&nbsp;</label>
<html:checkbox styleId="mri" styleClass="radio" property="mri" value="Specify"/><label for="mri">MRI</label>
    <!-- <app:help help="Relates to disease evaluation as measured by scan (MRI/CT). Score definitions: +3: DISAPPEARANCE OF TUMOR (CR), +2: DEFINITELY BETTER (PR), +1:POSSIBLY BETTER,0:UNCHANGED, -1:POSSIBLY WORSE, -2:DEFINITELY WORSE (PD), -3: DEVELOPMENT OF A NEW LESION (PD)"/>-->
    <app:cshelp topic="<%=mHelp%>" text="[?]"/>    
    <html:select styleId="mriType" property="mriType">
	<html:optionsCollection property="mriTypeColl" />
    </html:select>
<label for="mriType">&nbsp;&nbsp;</label>
	

</ fieldset>