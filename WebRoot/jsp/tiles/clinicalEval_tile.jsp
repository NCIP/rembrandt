<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Clinical Evaluation
<app:help help="Future implementation"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<html:checkbox property="karnofsky" value="Specify"/>Karnofsky
 <app:help help="Future implementation"/>
    <html:select property="karnofskyType">
	<html:optionsCollection property="karnofskyTypeColl" />
    </html:select>
	&nbsp;&nbsp;
	
<html:checkbox property="lansky" disabled="true" value="Specify"/>lansky
    <app:help help="Future implementation"/>
    <html:select property="lanskyType">
	<html:optionsCollection property="lanskyTypeColl" />
    </html:select>	
	&nbsp;&nbsp;
	
<html:checkbox property="neuroExam" value="Specify"/>Neuro Exam
    <app:help help="Future implementation"/>
    <html:select property="neuroExamType">
	<html:optionsCollection property="neuroExamTypeColl" />
    </html:select>
	&nbsp;&nbsp;
<html:checkbox property="mri" value="Specify"/>MRI
    <app:help help="Future implementation"/>
    <html:select property="mriType">
	<html:optionsCollection property="mriTypeColl" />
    </html:select>
&nbsp;&nbsp;
	

</ fieldset>