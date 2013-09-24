<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ page import="gov.nih.nci.rembrandt.util.*" %>


<% 
	String bottomPage = "rbtFramesBottom.jsp";
	String param = request.getParameter("p");
	if (param != null) {
		param = MoreStringUtils.checkWhiteListBeforeCleaningJavascriptAndSpecialChars(MoreStringUtils.specialCharacters, param);
		bottomPage = param;
	}
	
%>

<HTML>
<HEAD>
<TITLE>Rembrandt Report Annotation</TITLE>
</HEAD>

  <FRAMESET rows="70, *" border="0">
      <FRAME src="rbtFramesTop.jsp" scrolling="no">
      <FRAME src="<%=bottomPage%>">
  </FRAMESET>

 
</HTML>