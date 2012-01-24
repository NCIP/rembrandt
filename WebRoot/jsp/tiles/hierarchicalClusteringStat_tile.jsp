<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>
<%
	String act = request.getParameter("act");
%>
<fieldset class="gray">
<legend class="red">
Step 2: Select Statistic
<!-- <app:help help="Specify statistical options."/>-->
<a href="javascript: Help.popHelp('<%=act%>_Statistic_tooltip');">[?]</a>    
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
