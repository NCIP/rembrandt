<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="/WEB-INF/rembrandt.tld"  prefix="app" %>
<%@ page import="java.util.*,
				 gov.nih.nci.rembrandt.dto.query.*,
				 gov.nih.nci.caintegrator.dto.query.*,
				 gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
				 gov.nih.nci.rembrandt.util.RembrandtConstants,
				 gov.nih.nci.caintegrator.security.UserCredentials" %>
<%@ page import="org.apache.log4j.Logger;" %>

<% 
//String dest = "menu";
//UserCredentials credent = (UserCredentials)session.getAttribute(RembrandtConstants.USER_CREDENTIALS);
//if (credent == null || (credent.getUserName() != null && credent.getUserName().equalsIgnoreCase("RBTuser"))) {
//	dest = "login";
//} 

//out.println("Url: " + dest);
 %>
 
<div class="crumb">
<span style="float:left">
<a href="#main_content"><img src="../../images/skipnav.gif" alt="Skip Navigation Link" name="skipnav" width="1" height="1" border="0" id="skipnav"></a>


<%
UserCredentials credential = (UserCredentials)session.getAttribute(RembrandtConstants.USER_CREDENTIALS);
if (credential == null || (credential.getUserName() != null && credential.getUserName().equalsIgnoreCase("RBTuser"))) {	
%>
	<s:url action="guestHome" namespace="/" id="aURL"></s:url>
<%} else {%>
	<s:url action="menu" namespace="/" id="aURL"></s:url>
<%} %>
<s:a style="font-size:.8em" href="%{aURL}">Home</s:a>&nbsp;&nbsp;&nbsp;


<app:cshelp topic="Welcome" style="font-size:.8em" text="Help"/>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">Support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="tutorials.jsp">Tutorials</a>&nbsp;&nbsp;&nbsp;
<app:cshelp topic="cite_data" style="font-size:.8em" text="Cite Data"/>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="html/disclaimer.html" target="new">Disclaimer</a>&nbsp;&nbsp;&nbsp;
</span>
  <span style="text-align:right;font-size:.85em;">
<%
if(session.getAttribute("name") != null)	{
%>
    Welcome, &nbsp;
    <% out.println(session.getAttribute("name")); %>
    &nbsp;|&nbsp;
    <s:url action="logoutPage" id="aURL" />
    <s:a style="font-size:.85em;" href="%{aURL}">
      Logout
    </s:a>
<% } 
	else	{
%>
	<s:url action="registration" id="aURL" />
	<s:a style="font-size:1.5em;" href="%{aURL}" onmouseover="return overlib('Please login to access additional features.', CAPTION, 'Login');" onmouseout="return nd();">
      Login
    </s:a><br/>
<%
	}
%>
  </span>
</div>
