<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Occurrence_tooltip";
%>
<fieldset class="gray">
<legend class="red">Occurrence
<app:cshelp topic="<%=act%>" text="[?]"/>
</legend>

	
<input type="checkbox" id="firstPresentation" name="form.firstPresentation" class="radio" disabled="true" />
<label for="firstPresentation">First Presentation</label>
&nbsp;&nbsp;
<input id="recur" type="checkbox" name="form.recur" class="radio"  disabled="true"
onclick="javascript:document.forms[0].rec.disabled=(!(document.forms[0].rec.disabled));" />
<label for="recur">Recurrence&nbsp;&nbsp;</label>
	
	<s:select id="recurrence" name="form.recurrence" 
	list="form.recurrenceTypeColl" listKey="value" listValue="label" disabled="true" />
	<label for="recurrence">&nbsp;</label>
	<s:fielderror fieldName="recurrence"/> 
	

</fieldset>