<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Welcome Page</title>
</head>
<app:checkLogin name="logged" page="/login.jsp" />
<body>
WELCOME!
<% 
/*
out.println(session.getAttribute("logged"));
if(session.getAttribute("logged")== "yes")	{
out.println("Youre logged in");

}
*/
%>

<a href="logout.jsp">logout</a>
</body>
</html>
