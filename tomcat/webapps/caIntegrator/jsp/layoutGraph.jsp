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
		<tiles:insert attribute="crumbMenu"/>
		<h3><tiles:getAsString name="title"/></h3>
		<tiles:insert attribute="mainForm"/>
	</div>
    <tiles:insert attribute="footer"/> <%-- include footer --%>
</body>
</html>