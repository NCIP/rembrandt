<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<app:checkLogin name="logged" page="/login.jsp" />

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
		<table cellspacing="0" cellpadding="0" border="0">
			<tr>
			<td width="575"> 
				<tiles:insert attribute="tabs"/>
				<div id="main">
				<Table cellpadding="0" cellspacing="0" border="0" width="100%"> 
				<tr><td>
					<!--<tr class="report"><td><h3><tiles:getAsString name="title"/></h3></td></tr>-->   
					<tiles:insert attribute="mainForm"/> <%-- include the main form --%>
				</td></tr>
				</table>
				</div>
			</td>
			<td valign="top" class="sideBar">
			    <tiles:insert attribute="sideBar"/> <%-- include sidebar --%>
			</td>
			</tr>
		</table>
	</div>
   <tiles:insert attribute="footer"/> <%-- include footer --%>
</body>
</html>