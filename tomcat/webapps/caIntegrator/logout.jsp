<%@ page language="java" %>
<%
//null out the sesssion vars
session.setAttribute("logged", null);
// kill the session and unbinds any objects bound to it.
session.invalidate();

//redirect w/message
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = "login.jsp?m=1";
response.setHeader("Location",newLocn);

%>