<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act");
%>
<fieldset class="gray">
<legend class="red">Occurrence
<!--  <app:help help="Future implementation"/>-->
<a href="javascript: Help.popHelp('<%=act%>_Occurrence_tooltip');">[?]</a>    

</legend>

	
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
	

</ fieldset>