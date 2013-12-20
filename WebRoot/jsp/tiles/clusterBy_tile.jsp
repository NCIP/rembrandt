<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>
<%
	String act = request.getParameter("act") + "_Clusterby_tooltip";
%>
<fieldset class="gray">
<legend class="red">
Step 3: Cluster By
<!-- <app:help help="Cluster by Samples or Genes."/>-->
<app:cshelp topic="<%=act%>" text="[?]"/> 

</legend>
	
<br>
			<input type="radio" name="form.clusterBy" value="Samples" checked="checked" id="SamplesCluster" class="radio">
			&nbsp;<label for="SamplesCluster">Samples</label><br />
			<input type="radio" name="form.clusterBy" value="Genes" id="GenesCluster" class="radio">
			&nbsp;<label for="GenesCluster">Genes</label><br />			
			
</fieldset>
