<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<app:checkLogin name="logged" page="/login.jsp" />
<%
/*
 *		this is the main tiles template for the form based pages
*/
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title><tiles:getAsString name="title"/></title>
	<tiles:insert attribute="htmlHead"/> <%-- include html head --%>
</head>
<%
	String preview = (String) request.getAttribute("preview");
	if(preview != null && preview.equals("yes"))	{
%>
<body>
<% 	}
	else	{
%>
<body onload="javascript:checkForm();">
<% } %>
	 <%-- include div for overlib --%>
    <tiles:insert attribute="overlib"/>
    <%-- include header --%>
    <tiles:insert attribute="header"/> 
	<div class="content">
		<%-- include crumb menu --%>
		<tiles:insert attribute="crumbMenu"/> 
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td width="575"> 
					<table cellpadding="4" cellspacing="2" border="0" width="100%"> 
						<tr class="report">
							<td>
						    	<h3 style="padding:0px; margin:0px;"><tiles:getAsString name="title"/></h3>
							</td>
						</tr>   
						<tr>
							<td>
								<tiles:insert attribute="tabs"/>
								<div id="main" style="min-height:370px;_height:390px;">
									<%-- include the main form --%>
									<tiles:insert attribute="mainForm"/> 
									<div>
										<tiles:insert attribute="reqdFieldsMsg"/><br/>
									</div>
								</div>
							</td>
						</tr>	
					</table>
				</td>
				<td valign="top" class="sideBar">
				<div id="manageListLinkDiv" style="text-align:left; margin-top:20px;position:relative;top:5px">
			 		<a href="javascript: Help.popHelp('Blue_panel');"><img align="right" src="images/help.png" border="0" onmouseover="return overlib('Click here for additional information about the side bar.', CAPTION, 'Help');" onmouseout="return nd();" /></a>
				</div>
					<%-- include sidebar --%>
				    <tiles:insert attribute="sideBar"/> 
				</td>
			</tr>
		</table>
	<%-- include footer --%>	
	<tiles:insert attribute="footer"/> 
	</div>
</body>
</html>