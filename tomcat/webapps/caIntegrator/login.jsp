<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>

<%
if(session.getAttribute("logged") == "yes")
{
//youre already logged in, why are you here?
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = "welcome.jsp";
response.setHeader("Location",newLocn);
}


%>

<html>
<head><title>caIntegrator Login Test</title>
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>

</head>
<body>
<div class="content">
<h3>Login to caIntegrator (Test)</h3>
<html:errors/>
<% if(request.getParameter("m") != null)
out.println("<p style='color:red'>You have been logged out." + "</p>");
%>
<html:form action="login.do">
<table border="0">
<tr><Td>User Name:</td><td><html:text property="userName" /></td></tr>
<tr><Td>Password:</td><td><html:password property="password" /></td></tr>
</table>
<html:submit/>&nbsp;&nbsp;<html:reset/>
</div>
</html:form>
</body>
</html>
