<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Clinical Evaluation
<app:help help="Future implementation"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<html:checkbox styleClass="radio" property="karnofsky" value="Specify"/>Karnofsky
 
    <html:select property="karnofskyType">
	<html:optionsCollection property="karnofskyTypeColl" />
    </html:select>
	&nbsp;&nbsp;
	
<html:checkbox styleClass="radio" property="lansky" disabled="true" value="Specify"/>lansky
    
    <html:select property="lanskyType">
	<html:optionsCollection property="lanskyTypeColl" />
    </html:select>	
	&nbsp;&nbsp;
	
<html:checkbox styleClass="radio" property="neuroExam" value="Specify"/>Neuro Exam
    
    <html:select property="neuroExamType">
	<html:optionsCollection property="neuroExamTypeColl" />
    </html:select>
	&nbsp;&nbsp;
<html:checkbox styleClass="radio" property="mri" value="Specify"/>MRI
   
    <html:select property="mriType">
	<html:optionsCollection property="mriTypeColl" />
    </html:select>
&nbsp;&nbsp;
	

</ fieldset>