<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*,
				 gov.nih.nci.nautilus.query.*,
				 gov.nih.nci.nautilus.ui.bean.SessionQueryBag,
				 gov.nih.nci.nautilus.constants.NautilusConstants" %>
<%@ page import="org.apache.log4j.Logger;" %>
<div class="crumb">
  <span style="text-align:right;font-size:.85em;">
    Welcome, &nbsp;
    <% out.println(session.getAttribute("name")); %>
    &nbsp;|&nbsp;
    <a style="font-size:.85em;" href="logout.jsp">
      Logout
    </a>
  </span>
</div>
