<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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
&nbsp;&nbsp;<html:select styleId="distanceMatrix" property="distanceMatrix">
					<html:optionsCollection property="distanceMatrixCollection" /> 
			</html:select>
			&nbsp;&nbsp;
<label for="linkageMethod">Linkage Method:</label>
&nbsp;&nbsp;<html:select styleId="linkageMethod" property="linkageMethod">
					<html:optionsCollection property="linkageMethodCollection" /> 
			</html:select>
			
			
</fieldset>
