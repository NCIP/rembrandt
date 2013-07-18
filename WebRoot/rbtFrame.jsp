<%@ page language="java" %>
<%@ page import="gov.nih.nci.rembrandt.util.*" %>


<% 
	String bottomPage = "rbtFramesBottom.jsp";
	String param = request.getParameter("p");
	if (param != null) {
		param = MoreStringUtils.cleanJavascriptAndSpecialChars(MoreStringUtils.specialCharacters, param);
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