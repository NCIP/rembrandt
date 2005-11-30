<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Clinical Evaluation
<app:help help="Future implementation"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<input type="checkbox" name="karnofsky" class="radio" >Karnofsky
 <app:help help="Future implementation"/>
    <html:select property="karnofskyType">
	<html:optionsCollection property="karnofskyTypeColl" />
    </html:select>
	&nbsp;&nbsp;
	
<input type="checkbox" name="lansky" class="radio" disabled="true" >lansky
    <app:help help="Future implementation"/>
    <html:select property="lanskyType">
	<html:optionsCollection property="lanskyTypeColl" />
    </html:select>	
	&nbsp;&nbsp;
	
<input type="checkbox" name="neuroExam" class="radio" >Neuro Exam
    <app:help help="Future implementation"/>
    <html:select property="neuroExamType">
	<html:optionsCollection property="neuroExamTypeColl" />
    </html:select>
	&nbsp;&nbsp;
<input type="checkbox" name="mri" class="radio" >MRI
    <app:help help="Future implementation"/>
    <html:select property="mriType">
	<html:optionsCollection property="mriTypeColl" />
    </html:select>
&nbsp;&nbsp;
	

</ fieldset>