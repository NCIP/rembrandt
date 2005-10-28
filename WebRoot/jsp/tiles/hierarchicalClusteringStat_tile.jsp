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
<!--change so options come from database-->	
&nbsp;&nbsp;<html:select property="distanceMatrix">
					<html:option value="correlation">Correlation</html:option>
					<html:option value="euclidean distance">Euclidean distance</html:option>
			</html:select>
&nbsp;&nbsp;&nbsp;<html:select property="linkageMethod">
					<html:option value="average">Average</html:option>
					<html:option value="single">Single</html:option>
					<html:option value="complete">Complete</html:option>
			</html:select>
			
			
</fieldset>
