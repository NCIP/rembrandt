<%@ page language="java" %>
<%
//splash page goes here
//head over to the login
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = "login.jsp";
response.setHeader("Location",newLocn);
%>

