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

	
<input type="radio" name="comparativeGenomicForm.segmentMean" value="amplified" class="radio"/> Mean &ge;
<s:textfield name="comparativeGenomicForm.smAmplified" onfocus="javascript:radioFold(this);" />&nbsp;<br>
<s:actionerror name="smAmplified"/>
<input type="radio" name="comparativeGenomicForm.segmentMean" value="deleted" class="radio"/> Mean &le;
				<s:textfield name="comparativeGenomicForm.smDeleted" onfocus="javascript:radioFold(this);" />&nbsp;<br>
<s:actionerror name="smDeleted"/>			

<input type="radio" name="comparativeGenomicForm.segmentMean" value="unchange" class="radio"/>Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:textfield name="comparativeGenomicForm.smUnchangeFrom" onfocus="javascript:radioFold(this);" />-to-
				<s:textfield name="comparativeGenomicForm.smUnchangeTo" onfocus="javascript:radioFold(this);" />&nbsp;mean
<s:actionerror name="smUnchangeFrom"/>	
<s:actionerror name="smUnchangeTo"/>	
<s:actionerror name="cnerror"/><br>

</fieldset>


