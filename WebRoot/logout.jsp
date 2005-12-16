<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld"  prefix="app" %>

<html:form action="logout.do">
	<fieldset class="gray">
		<legend class="red">
		Thank you for visiting the REMBRANDT application
		</legend>
	
		<html:radio styleClass="radio" property="procedure" value="logoutSave" />
		Save my current session and logout. 
		<app:help help="This will save all queries and preferences from your current browser session in addition to queries from your previous session(s)." />
		<br />
		<html:radio styleClass="radio" property="procedure" value="logoutNoSave" />
		Do not save my current session and logout. 
		<app:help help="This will not save any queries or preferences from your current browser session." />
		<br/>
		<html:radio styleClass="radio" property="procedure" value="dontLogout" />
		Continue working in the application and do not logout. 
		
		<br /><br />
		<html:submit styleClass="xbutton" /><br />
   </fieldset>
</html:form>




