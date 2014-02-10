<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts2.form.*" %> 

<%
	String act = request.getParameter("act") + "_SegmentMean_tooltip";

%>
<fieldset class="gray">
<legend class="red">Segment Mean
<!-- <app:help help="Specify the threshold for the copy number."/>-->
<app:cshelp topic="<%=act%>" text="[?]"/> 

</legend>

	
<input type="radio" name="form.segmentMean" value="amplified" class="radio"/> Mean &ge;
<s:textfield name="form.smAmplified" onfocus="javascript:radioFold(this);" />&nbsp;<br>
<s:fielderror fieldName="smAmplified"/>
<input type="radio" name="form.segmentMean" value="deleted" class="radio"/> Mean &le;
				<s:textfield name="form.smDeleted" onfocus="javascript:radioFold(this);" />&nbsp;<br>
<s:fielderror fieldName="smDeleted"/>			

<input type="radio" name="form.segmentMean" value="unchange" class="radio"/>Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:textfield name="form.smUnchangeFrom" onfocus="javascript:radioFold(this);" />-to-
				<s:textfield name="form.smUnchangeTo" onfocus="javascript:radioFold(this);" />&nbsp;mean
<s:fielderror fieldName="smUnchangeFrom"/>	
<s:fielderror fieldName="smUnchangeTo"/>	
<s:fielderror fieldName="cnerror"/><br>

</fieldset>


