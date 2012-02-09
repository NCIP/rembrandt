<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
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
<a style="font-size:.8em" href="javascript:Help.popHelp('Welcome');">help</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="tutorials.jsp">tutorials</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="javascript:spawnNewPage('userGuide.html',640,480);">user guide</a>
<!-- a style="font-size:.8em" target="_new" href="docs/REMBRANDT_1.5_Users_Guide.pdf">user guide</a -->&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="javascript:Help.popHelp('cite_data');">cite data</a>&nbsp;&nbsp;&nbsp;
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
	<a style="font-size:.85em;" href="registration.do">
      Login
    </a><br/>
<%
	}
%>
  </span>
</div>
