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
<!--   html:radio property="copyNumberView" value="calculatedCN" styleClass="radio" onclick="javascript:toggleCopyNumberView('calculatedCN');" /> Calculated Copy Number &nbsp;&nbsp;&nbsp;-->
<input type="radio" name="form.copyNumberView" class="radio" id="calculatedCN" value="calculatedCN" 
		onclick="javascript:toggleCopyNumberView('calculatedCN');" />
	    <label for="calculatedCN">Calculated Copy Numberr &nbsp;&nbsp;&nbsp;</label>

<!--  html:radio property="copyNumberView" value="segmentMean" styleClass="radio" onclick="javascript:toggleCopyNumberView('segmentMean');" /> Segment Mean  -->
<input type="radio" name="form.copyNumberView" class="radio" id="segmentMean" value="segmentMean" 
		onclick="javascript:toggleCopyNumberView('segmentMean');" />
	    <label for="segmentMean">Segment Mean</label>

<br/>
<br/>


</fieldset>

