<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%
/*
 *		this is the main tiles template for the form based pages
 *		for pages with no sidebar
*/
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title><tiles:getAsString name="title"/></title>
	<tiles:insert attribute="htmlHead"/> <%-- include html head --%>
</head>
<body>
    <tiles:insert attribute="overlib"/> <%-- include div for overlib --%>
    <tiles:insert attribute="header"/> <%-- include header --%>
	
	<div class="content">
		<tiles:insert attribute="crumbMenu"/> <%-- include crumb menu --%>
		<form action="<tiles:getAsString name="formAction"/>" method="get" name="<tiles:getAsString name="formName"/>">
		<h3><tiles:getAsString name="title"/></h3>
		<div> <tiles:insert attribute="outputExamples"/></div>
		<tiles:insert attribute="mainForm"/>
		<tiles:insert attribute="buttons"/>
	</div>
    <tiles:insert attribute="reqdFieldsMsg"/> <%-- include required message note --%><br>
    <tiles:insert attribute="footer"/> <%-- include footer --%>
</body>
</html>