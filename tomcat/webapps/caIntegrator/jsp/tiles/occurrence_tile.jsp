<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<DIV class="title">Occurrence</DIV>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<input type="checkbox" property="firstPresentation" class="radio">First Presentation
&nbsp;&nbsp;
<input type="checkbox" class="radio" 
onclick="javascript:document.forms[0].rec.disabled=(!(document.forms[0].rec.disabled));">Recurrence&nbsp;&nbsp;
	<!--- <select property="recurrence" name="rec" disabled="true">
		<option>any</option>
		<option>1</option>
		<option>2</option>
		<option>3</option>
	</select>	  --->
	
	<html:select property="recurrence">
	<html:optionsCollection property="recurrenceTypeColl" />
    </html:select><html:errors property="recurrence"/> 
	
	<!-- </html:form> -->
