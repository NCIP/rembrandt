<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>
<%
	String act = request.getParameter("act") + "_Statistic_tooltip";
%>
<fieldset class="gray">
<legend class="red">
Step 2: Select Statistic
<!-- <app:help help="Specify statistical options."/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>
</legend>

	
<br>
<label for="distanceMatrix">Distance Matrix:</label>
&nbsp;&nbsp;<s:select id="distanceMatrix" name="distanceMatrix" list="form.distanceMatrixCollection" listKey="value" listValue="label"> 
			</s:select>
			&nbsp;&nbsp;
<label for="linkageMethod">Linkage Method:</label>
&nbsp;&nbsp;<s:select id="linkageMethod" name="linkageMethod" list="form.linkageMethodCollection" listKey="value" listValue="label"> 
			</s:select>
			
			
</fieldset>
