<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Occurrence
<app:help help="Future implementation"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<input type="checkbox" name="firstPresentation" class="radio" disabled="true" >First Presentation
&nbsp;&nbsp;
<input type="checkbox" name="recur" class="radio"  disabled="true"
onclick="javascript:document.forms[0].rec.disabled=(!(document.forms[0].rec.disabled));">Recurrence&nbsp;&nbsp;
	<!--- <select property="recurrence" name="rec" disabled="true">
		<option>any</option>
		<option>1</option>
		<option>2</option>
		<option>3</option>
	</select>	  --->
	
	<html:select property="recurrence" disabled="true">
	<html:optionsCollection property="recurrenceTypeColl" />
    </html:select><html:errors property="recurrence"/> 
	
	<!-- </html:form> -->
</ fieldset>