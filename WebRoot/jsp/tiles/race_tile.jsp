<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Race

</legend>
<%
	String act = request.getParameter("act");
%>


	
<html:checkbox styleClass="radio" property="caucasion" value="Specify" />Caucasion 
<html:checkbox styleClass="radio" property="africanAmerican" value="Specify" />African American 
<html:checkbox styleClass="radio" property="nativeHawaiian"  value="Specify" />Native Hawaiian 
<html:checkbox styleClass="radio" property="asian" value="Specify" />Asian
<html:checkbox styleClass="radio" property="other" value="Specify" />Other
<html:checkbox styleClass="radio" property="unknown" value="Specify"  />Unknown
&nbsp;&nbsp;
	

</ fieldset>