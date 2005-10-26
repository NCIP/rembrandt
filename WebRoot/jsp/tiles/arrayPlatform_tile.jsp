<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
<logic:notPresent name="geneexpressionForm">
Step 3: Array Platform
</logic:notPresent>
<logic:present name="geneexpressionForm">
Array Platform
</logic:present>


<app:help help="Select array platform (Affymetrix Oligo arrays or cDNA array platform) from the pick list"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<br>	
&nbsp;&nbsp;<html:select property="arrayPlatform">
					<html:option value="<%=Constants.ALL_PLATFROM%>">All</html:option>
					<html:option value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Oligo (Affymetrix)</html:option>
					<html:option value="<%=Constants.CDNA_ARRAY_PLATFORM%>">cDNA</html:option>
			</html:select>
			<html:errors property="arrayPlatform"/>
</fieldset>
