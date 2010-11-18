<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
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

