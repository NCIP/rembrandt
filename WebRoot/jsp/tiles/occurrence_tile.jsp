<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Occurrence_tooltip";
%>
<fieldset class="gray">
<legend class="red">Occurrence
<!--  <app:help help="Future implementation"/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>
</legend>

	
<input type="checkbox" id="firstPresentation" name="firstPresentation" class="radio" disabled="true" ><label for="firstPresentation">First Presentation</label>
&nbsp;&nbsp;
<input id="recur" type="checkbox" name="recur" class="radio"  disabled="true"
onclick="javascript:document.forms[0].rec.disabled=(!(document.forms[0].rec.disabled));"><label for="recur">Recurrence&nbsp;&nbsp;</label>
	<!--- <select property="recurrence" name="rec" disabled="true">
		<option>any</option>
		<option>1</option>
		<option>2</option>
		<option>3</option>
	</select>	  --->
	
	<html:select styleId="recurrence" property="recurrence" disabled="true">
	<html:optionsCollection property="recurrenceTypeColl" />
    </html:select><label for="recurrence">&nbsp;</label><html:errors property="recurrence"/> 
	

</ fieldset>