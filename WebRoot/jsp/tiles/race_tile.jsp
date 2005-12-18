<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Race

</legend>
<%
	String act = request.getParameter("act");
%>


	
<html:checkbox property="caucasion" value="Specify" />Caucasion 
<html:checkbox property="africanAmerican" value="Specify" />African American 
<html:checkbox property="nativeHawaiian"  value="Specify" />Native Hawaiian 
<html:checkbox property="asian" value="Specify" />Asian
<html:checkbox property="other" value="Specify" />Other
<html:checkbox property="unknown" value="Specify"  />Unknown
&nbsp;&nbsp;
	

</ fieldset>