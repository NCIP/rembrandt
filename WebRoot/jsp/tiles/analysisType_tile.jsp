<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
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
<s:select name="comparativeGenomicForm.analysisType" disabled="false" list="comparativeGenomicForm.analysisTypeColl">
</s:select>
<br/><br/>
<s:actionerror name="analysisType"/>
</fieldset>

