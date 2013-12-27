<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.SpecimenType"%>
<%
	String act = request.getParameter("act");
%>



<fieldset class="gray">
<html:radio property="copyNumberView" value="calculatedCN" styleClass="radio" onclick="javascript:toggleCopyNumberView('calculatedCN');" /> Calculated Copy Number &nbsp;&nbsp;&nbsp;
<html:radio property="copyNumberView" value="segmentMean" styleClass="radio" onclick="javascript:toggleCopyNumberView('segmentMean');" /> Segment Mean 
<br/>
<br/>


</fieldset>

