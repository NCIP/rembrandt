<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Clinical Evaluation

</legend>
<%
	String act = request.getParameter("act");
%>
	
<html:checkbox styleClass="radio" property="karnofsky" value="Specify"/>Karnofsky
 <app:help help="Score from the Karnofsky Performance status scale, representing the functional capabilities of a person."/>
    <html:select property="karnofskyType">
	<html:optionsCollection property="karnofskyTypeColl" />
    </html:select>
	&nbsp;&nbsp;
	
<html:checkbox styleClass="radio" property="lansky" disabled="true" value="Specify"/>Lansky
    <app:help help="Score from an enumerated set of values representing performance status according to the Lansky scale. The Lansky scale is intended for use only with subjects under 12 years of age."/>
    <html:select property="lanskyType">
	<html:optionsCollection property="lanskyTypeColl" />
    </html:select>	
	&nbsp;&nbsp;
	
<html:checkbox styleClass="radio" property="neuroExam" value="Specify"/>Neuro Exam
    <app:help help="The participants neurologic exam score. Score definitions: +2: DEFINITELY BETTER, +1: POSSIBLY BETTER, 0:STABLE, -1: POSSIBLY WORSE, -2: DEFINITELY WORSE"/>
    <html:select property="neuroExamType">
	<html:optionsCollection property="neuroExamTypeColl" />
    </html:select>
	&nbsp;&nbsp;
<html:checkbox styleClass="radio" property="mri" value="Specify"/>MRI
    <app:help help="Relates to disease evaluation as measured by scan (MRI/CT). Score definitions: +3: DISAPPEARANCE OF TUMOR (CR), +2: DEFINITELY BETTER (PR), +1:POSSIBLY BETTER,0:UNCHANGED, -1:POSSIBLY WORSE, -2:DEFINITELY WORSE (PD), -3: DEVELOPMENT OF A NEW LESION (PD)"/>
    <html:select property="mriType">
	<html:optionsCollection property="mriTypeColl" />
    </html:select>
&nbsp;&nbsp;
	

</ fieldset>