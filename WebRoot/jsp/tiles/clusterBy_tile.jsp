<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
Step 3: Cluster By
<app:help help="Leave the default to cluster on samples or change the radio button to cluster by genes."/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<br>

			<html:radio styleClass="radio" property="clusterBy" value="Samples"/>
			&nbsp;Samples<br />
			<html:radio styleClass="radio" property="clusterBy" value="Genes"/>
			&nbsp;Genes<br />			
			
</fieldset>
