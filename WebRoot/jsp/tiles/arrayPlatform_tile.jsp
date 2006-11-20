<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">

<logic:present name="geneexpressionForm">
Array Platform
</logic:present>
<logic:present name="principalComponentForm">
Step 3: Select Array Platform
</logic:present>
<logic:present name="classComparisonForm">
Step 3: Select Array Platform
</logic:present>
<logic:present name="hierarchicalClusteringForm">
Step 4: Select Array Platform
</logic:present>


<app:help help="Select an array platform"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<br/>	
&nbsp;&nbsp;<select name="arrayPlatform" 
<logic:notPresent name="geneexpressionForm">
	onchange="this.options[1].selected=true;"
</logic:notPresent>	
>
					<option value="<%=Constants.ALL_PLATFROM%>">All</option>
					<option selected="true" value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Oligo (Affymetrix U133 Plus 2.0)</option>
					<option value="<%=Constants.CDNA_ARRAY_PLATFORM%>">cDNA</option>
			</select>
			<html:errors property="arrayPlatform"/>
</fieldset>
