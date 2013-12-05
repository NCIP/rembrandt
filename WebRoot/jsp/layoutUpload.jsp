<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

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
	<tiles:insertAttribute name="htmlHead"/> <%-- include html head --%>
</head>
<body>
    <tiles:insertAttribute name="overlib"/> <%-- include div for overlib --%>
    <tiles:insertAttribute name="header"/> <%-- include header --%>
	<div class="content">
		<h3><tiles:getAsString name="title"/></h3>
		
		<tiles:insertAttribute name="mainForm"/>
		
	</div>
    
</body>
</html>