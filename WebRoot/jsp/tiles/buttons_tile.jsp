<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
long randomness = System.currentTimeMillis(); //prevent image caching
%>
<script language="javascript">
document.forms[0].target = "_self";
</script>

<html:reset styleClass="xbutton" value="clear"/>&nbsp;&nbsp;
<html:button styleClass="xbutton" property="method" value="cancel" onclick="javascript:alertUser();"/>&nbsp;&nbsp;
<html:submit styleClass="xbutton" property="method" value="run report"/>&nbsp;&nbsp; <!--  onclick="javascript: return formNewTarget('_report', 770, 550);" -->
<html:submit styleClass="subButton" property="method" value="submit" onclick="javascript: document.forms[0].target='_self'; return checkNull(document.forms[0].queryName);"/>

