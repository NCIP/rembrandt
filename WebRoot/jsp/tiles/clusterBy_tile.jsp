<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>
<%
	String act = request.getParameter("act");
%>
<fieldset class="gray">
<legend class="red">
Step 3: Cluster By
<!-- <app:help help="Cluster by Samples or Genes."/>-->
<a href="javascript: Help.popHelp('<%=act%>_Clusterby_tooltip');">[?]</a>    

</legend>
	
<br>

			<html:radio styleId="SamplesCluster" styleClass="radio" property="clusterBy" value="Samples"/>
			&nbsp;<label for="SamplesCluster">Samples</label><br />
			<html:radio styleId="GenesCluster" styleClass="radio" property="clusterBy" value="Genes"/>
			&nbsp;<label for="GenesCluster">Genes</label><br />			
			
</fieldset>
