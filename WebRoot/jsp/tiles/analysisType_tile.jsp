<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.SpecimenType"%>
<%
	String act = request.getParameter("act") + "_analysisType_tooltip";
%>



<fieldset class="gray">
<legend class="red">Analysis Type
<app:cshelp topic="<%=act%>" text="[?]"/>   
</legend><br />
&nbsp;&nbsp;
<html:select property="analysisType" disabled="false">
	<html:optionsCollection property="analysisTypeColl" />
</html:select>
<br/><br/>
<html:errors property="analysisType"/>
</fieldset>

