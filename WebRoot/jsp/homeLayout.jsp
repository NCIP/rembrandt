<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>

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
			<td width="500"> 

				<Table cellpadding="0" cellspacing="0" border="0" width="100%"> 
					<!--<tr class="report"><td><h3><tiles:getAsString name="title"/></h3></td></tr>-->   
					<tiles:insert attribute="mainForm"/> <%-- include the main form --%>
				</table>
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