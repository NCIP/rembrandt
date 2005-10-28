<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
Step 3: Cluster By
<app:help help="Select to cluster by samples or cluster by genes"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<br>

			<html:radio styleClass="radio" property="clusterBy" value="samples"/>
			&nbsp;samples<br />
			<html:radio styleClass="radio" property="clusterBy" value="genes"/>
			&nbsp;genes<br />			
			
</fieldset>
