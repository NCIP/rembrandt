<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
<label for="platSelect">
<s:if test="geneexpressionForm != null">
Array Platform
</s:if>
<s:if test="gpIntegrationForm != null">
Step 2: Select Array Platform
</s:if>
<s:if test="principalComponentForm != null">
Step 3: Select Array Platform
</s:if>
<s:if test="classComparisonForm != null">
Step 3: Select Array Platform
</s:if>
<s:if test="hierarchicalClusteringForm != null">
Step 4: Select Array Platform
</s:if>
<s:if test="igvIntegrationForm != null">
Step 2: Select Array Platform (Select at least one)
</s:if>
</label>
<%
	String act = request.getParameter("act") + "_Array_tooltip";
	String act1 = request.getParameter("act");
%>
<app:cshelp topic="<%=act%>" text="[?]"/> 
<!-- <app:help help="Select an array platform"/>-->
</legend>

	
<br/>	

<!-- s:if test="param.act eq 'igvintegration'" -->
<% if (act1.equals("igvintegration")) { %>
	<s:checkbox name="form.snpArrayPlatform" onclick="javascript:checkArrayPlatform(this);"/>100K SNP Array &nbsp;&nbsp;&nbsp;&nbsp;
	<s:checkbox name="form.arrayPlatform"/>Affymetrix HG_U133 Plus 2.0
<% } else {%>	
<!-- /s:if -->
<!-- s:else -->
	&nbsp;&nbsp;<select name="form.arrayPlatform" id="platSelect"   
		<s:if test="geneexpressionForm != null">
			onchange="this.options[1].selected=true;"
		</s:if>	
		>
	
	    <!-- s:if test="param.act eq 'gpintegration'" -->
	    <% if (act1.equals("gpintegration")) { %>
			<option selected="selected" value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Affymetrix HG_U133 Plus 2.0</option>
		<% } else {%>	
		<!-- /s:if -->
		<!-- s:else -->
						<option value="<%=Constants.ALL_PLATFROM%>">All</option>
						<option selected="selected" value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Affymetrix HG_U133 Plus 2.0</option>
						<option value="<%=Constants.CDNA_ARRAY_PLATFORM%>">cDNA</option>
		<% } %>
		<!-- /s:else -->
	
	</select>
<% } %>
<!-- /s:else -->

			
<s:actionerror name="arrayPlatform"/>
</fieldset>
<s:if test="geneexpressionForm != null">
<script type="text/javascript">
	document.getElementById("platSelect").selectedIndex = 0;
</script>
</s:if>

 <script language="javascript">
 function checkArrayPlatform(formElement){
     
   //selected index of the selected
	var element = formElement.checked;

	 if(element.toString() == "false"){
	 	document.getElementById("snpAnalysisSelect").disabled = true;
	 	document.getElementById("snpAnalysisSelect").selectedIndex = 0;
	 }
	 else {
	 	document.getElementById("snpAnalysisSelect").disabled = false;
	 	document.getElementById("snpAnalysisSelect").selectedIndex = 1;
	 }
}
</script>	



