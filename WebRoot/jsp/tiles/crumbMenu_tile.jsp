<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*,
				 gov.nih.nci.rembrandt.dto.query.*,
				 gov.nih.nci.caintegrator.dto.query.*,
				 gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
				 gov.nih.nci.rembrandt.util.RembrandtConstants" %>
<%@ page import="org.apache.log4j.Logger;" %>
<div class="crumb">
  <span style="text-align:right;font-size:.85em;">
    Welcome, &nbsp;
    <% out.println(session.getAttribute("name")); %>
    &nbsp;|&nbsp;
    <a style="font-size:.85em;" href="logoutPage.do">
      Logout
    </a>
  </span>
</div>
