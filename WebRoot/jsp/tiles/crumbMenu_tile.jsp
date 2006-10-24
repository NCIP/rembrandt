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
<a style="font-size:.8em" href="menu.do">home</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="javascript:Help.popHelp('Welcome');">help</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">support</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="tutorials.jsp">tutorials</a>&nbsp;&nbsp;&nbsp;
<a style="font-size:.8em" href="docs/rembrandt1.0_ug_12_20_05_nsn.pdf">user guide</a>&nbsp;&nbsp;&nbsp;
</span>
  <span style="text-align:right;font-size:.85em;">
    Welcome, &nbsp;
    <% out.println(session.getAttribute("name")); %>
    &nbsp;|&nbsp;
    <a style="font-size:.85em;" href="logoutPage.do">
      Logout
    </a>
  </span>
</div>
