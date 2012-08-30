<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
<label for="platSelect">
<logic:present name="geneexpressionForm">
Array Platform
</logic:present>
<logic:present name="gpIntegrationForm">
Step 2: Select Array Platform
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
<logic:present name="igvIntegrationForm">
Step 2: Select Array Platform (Select at least one)
</logic:present>
</label>
<%
	String act = request.getParameter("act");
%>
<a href="javascript: Help.popHelp('<%=act%>_Array_tooltip');">[?]</a>    
<!-- <app:help help="Select an array platform"/>-->
</legend>

	
<br/>	
<c:choose>
	<c:when test="${param.act eq 'igvintegration'}">
		<html:checkbox property="snpArrayPlatform" onclick="javascript:checkArrayPlatform(this);"/>100K SNP Array &nbsp;&nbsp;&nbsp;&nbsp;
		<html:checkbox property="arrayPlatform"/>Oligo (Affymetrix U133 Plus 2.0)
	</c:when>
	<c:otherwise>
		&nbsp;&nbsp;<html:select property="arrayPlatform"  styleId="platSelect">   
		<logic:notPresent name="geneexpressionForm">
			onchange="this.options[1].selected=true;"
		</logic:notPresent>	
		>
		<c:choose>
		    <c:when test="${param.act eq 'gpintegration'}">
				<option selected="true" value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Oligo (Affymetrix U133 Plus 2.0)</option>
			</c:when>
			<c:otherwise>
							<option value="<%=Constants.ALL_PLATFROM%>">All</option>
							<option selected="true" value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Oligo (Affymetrix U133 Plus 2.0)</option>
							<option value="<%=Constants.CDNA_ARRAY_PLATFORM%>">cDNA</option>
			</c:otherwise>
		</c:choose>
		</html:select>
	</c:otherwise>
</c:choose>
			
<html:errors property="arrayPlatform"/>
</fieldset>
<logic:present name="geneexpressionForm">
<script type="text/javascript">
	document.getElementById("platSelect").selectedIndex = 0;
</script>
</logic:present>

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



