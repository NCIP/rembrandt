<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld"  prefix="app" %>
<%@ page import="java.util.*,
				 gov.nih.nci.rembrandt.dto.query.*,
				 gov.nih.nci.caintegrator.dto.query.*,
				 gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
				 gov.nih.nci.rembrandt.util.RembrandtConstants" %>
<%@ page import="org.apache.log4j.Logger;" %>
<div class="crumb">
<span style="float:left">
<a href="#main_content"><img src="../../images/skipnav.gif" alt="Skip Navigation Link" name="skipnav" width="1" height="1" border="0" id="skipnav"></a>
<a style="font-size:.8em" href="menu.do">home</a>&nbsp;&nbsp;&nbsp;
<app:cshelp topic="Welcome" style="font-size:.8em" text="Help"/>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="tutorials.jsp">tutorials</a>&nbsp;&nbsp;&nbsp;
<app:cshelp topic="cite_data" style="font-size:.8em" text="cite data"/>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="html/disclaimer.html" target="new">Disclaimer</a>&nbsp;&nbsp;&nbsp;
</span>
  <span style="text-align:right;font-size:.85em;">
<%
if(session.getAttribute("name") != null)	{
%>
    Welcome, &nbsp;
    <% out.println(session.getAttribute("name")); %>
    &nbsp;|&nbsp;
    <a style="font-size:.85em;" href="logoutPage.do">
      Logout
    </a>
<% } 
	else	{
%>
	<a style="font-size:1.5em;" href="registration.do" onmouseover="return overlib('Please login to access additional features.', CAPTION, 'Login');" onmouseout="return nd();">
      Login
    </a><br/>
<%
	}
%>
  </span>
</div>
