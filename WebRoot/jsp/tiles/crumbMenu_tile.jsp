<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*,
				 gov.nih.nci.nautilus.query.*,
				 gov.nih.nci.nautilus.ui.helper.SessionQueryBag,
				 gov.nih.nci.nautilus.constants.NautilusConstants" %>
<%@ page import="org.apache.log4j.Logger;" %>
<%
	/*
	* generates the crumb menu
	*
	*/
%>

<div class="crumb">
<!--
  <a class="possible" href="home.do">
    Search
  </a>
  <a class="possible" href="menu.do">
    Build Query
  </a>
-->
  <%
  /*
  Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
  SessionQueryBag queryCollection = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
  if(queryCollection == null){
    logger.debug("no query collection");
    out.println("<a class='notPossible'>Refine Query</a>");
    }
  else{
  logger.debug("there is a query collection");
  out.println("<A class='possible' href='refinecheck.do'>Refine Query</A>");
  }
  %>
  <!--select presentation to be implemented post-nautilus-->
  <%
  if(queryCollection != null){
      if(queryCollection.hasCompoundQuery()){
      logger.debug("has compound query");
      out.println("<A class='possible' href='compoundcheck.do'>Select Presentation</A>");
      }
      else{
          out.println("<A class='notPossible'>Select Presentation</A>");
          logger.debug("has no compound query");
      }
  }
  else{
          out.println("<A class='notPossible'>Select Presentation</A>");
          logger.debug("has no compound query");
  }
  */
  %>
 
  <span style="text-align:right;font-size:.85em;">
    Welcome, &nbsp;
    <% out.println(session.getAttribute("name")); %>
    &nbsp;|&nbsp;
    <a style="font-size:.85em;" href="logout.jsp">
      Logout
    </a>
  </span>
</div>
