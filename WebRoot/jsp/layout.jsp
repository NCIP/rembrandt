<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

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
	<tiles:insertAttribute name="htmlHead"/> <%-- include html head --%>
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
    <tiles:insertAttribute name="overlib"/>
    <%-- include header --%>
    <tiles:insertAttribute name="header"/> 
	<div class="content">
		<%-- include crumb menu --%>
		<tiles:insertAttribute name="crumbMenu"/> 
		<table cellspacing="0" cellpadding="0" border="0" width="100%" summary="This table is used to format page content">
			<tr>
				<th></th><th></th>
			</tr>
			<tr>
				<td width="575"> 
					<table cellpadding="4" cellspacing="2" border="0" width="100%" summary="This table is used to format page content"> 
						<tr><th></th></tr>
						<tr class="report">
							<td>
						    	<h3 style="padding:0px; margin:0px;"><tiles:getAsString name="title"/></h3>
							</td>
						</tr>   
						<tr>
							<td>
								<tiles:insertAttribute name="tabs"/>
								<div id="main" style="min-height:370px;_height:390px;">
									<a name="main_content"></a>
									<%-- include the main form --%>
									<tiles:insertAttribute name="mainForm"/> 
									<div>
										<tiles:insertAttribute name="reqdFieldsMsg"/><br/>
									</div>
								</div>
							</td>
						</tr>
						<tr><td>
							<tiles:insertAttribute name="applets"/>
						</td></tr>	
					</table>
				</td>
				<td valign="top" class="sideBar">
				<!-- 
				<div id="manageListLinkDiv" style="text-align:left; margin-top:20px;position:relative;top:5px">
			 		<a href="javascript: Help.popHelp('Blue_panel');"><img align="right" src="images/help.png" border="0" onmouseover="return overlib('Click here for additional information about the side bar.', CAPTION, 'Help');" onmouseout="return nd();" /></a>
				</div>
				-->
					<%-- include sidebar --%>
				    <tiles:insertAttribute name="sideBar"/> 
				</td>
			</tr>
		</table>
	<%-- include footer --%>	
	<tiles:insertAttribute name="footer"/> 
	</div>
</body>
</html>