<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
long randomness = System.currentTimeMillis(); //prevent image caching
%>
<script language="javascript">
document.forms[0].target = "_self";
</script>


<html:reset styleId="clearButton" styleClass="xbutton" value="clear" />&nbsp;&nbsp;
<html:button styleClass="xbutton" property="method" value="cancel" onclick="javascript:alertUser('analysisHome');"/>&nbsp;&nbsp;

<logic:notPresent name="hierarchicalClusteringForm">
	<logic:present name="gpIntegrationForm">
		<html:submit styleId="submittalButton" styleClass="subButton" property="method" onclick="javascript: saveMe(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups')); return checkNull(document.forms[0].analysisResultName, 'false');">
			<bean:message key="buttons_tile.submittalButton" />
		</html:submit>
	</logic:present>
	<logic:notPresent name="gpIntegrationForm">
	<html:submit styleId="submittalButton" styleClass="subButton" property="method" onclick="javascript:saveMe(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'));return checkNull(document.forms[0].analysisResultName, 'true')">
    	<bean:message key="buttons_tile.submittalButton"/>
	</html:submit>
	</logic:notPresent>
</logic:notPresent>

<logic:present name="hierarchicalClusteringForm">
<html:submit styleId="submittalButton" styleClass="subButton" property="method" onclick="javascript: document.forms[0].target='_self'; return checkNull(document.forms[0].analysisResultName, 'true')">
     <bean:message key="buttons_tile.submittalButton"/>
</html:submit>
</logic:present>




