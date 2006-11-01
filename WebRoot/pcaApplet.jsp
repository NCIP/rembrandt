<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.helper.PCAAppletHelper" %>
<%
		String taskId = request.getParameter("tid") != null ? (String) request.getParameter("tid") : "";
		String params = PCAAppletHelper.generateParams(session.getId(), taskId);
%>
<html>
<head>
	<title>PCA Applet</title>
</head>
<body>

<xmp>
<%=params%>
</xmp>

	<applet code="PCATestApplet.class" width="600" height="600"
	name="pcaApplet" id="pcaApplet" codebase="Applets/" archive="jmathplot.jar,commons-lang-2.0.jar">
	<%=params%>	
	<em>Your browser does not support Java!  Please download the Java Plugin for your browser, available from <a href="http://www.java.com/en/download/index.jsp" target="_blank">Sun</a></em>
	</applet> 


</body>
</html>