<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
Step 2: Select Statistic
<app:help help="Select distance matrix and linkage method"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<br>
Distance Matrix:
&nbsp;&nbsp;<html:select property="distanceMatrix">
					<html:optionsCollection property="distanceMatrixCollection" /> 
			</html:select>
			&nbsp;&nbsp;
Linkage Method:
&nbsp;&nbsp;<html:select property="linkageMethod">
					<html:optionsCollection property="linkageMethodCollection" /> 
			</html:select>
			
			
</fieldset>
