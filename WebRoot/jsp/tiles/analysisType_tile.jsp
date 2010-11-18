<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.SpecimenType"%>
<%
	String act = request.getParameter("act");
%>



<fieldset class="gray">
<legend class="red">Analysis Type
<a href="javascript: Help.popHelp('<%=act%>_analysisType_tooltip');">[?]</a>   
</legend><br />
&nbsp;&nbsp;
<html:select property="analysisType" disabled="false">
	<html:optionsCollection property="analysisTypeColl" />
</html:select>
<br/><br/>
<html:errors property="analysisType"/>
</fieldset>

