<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%
	String act = request.getParameter("act") + "_SNP_tooltip";
%>

<fieldset class="gray">
<legend class="red">
<label for="platSelect">
Step 3: Select SNP Analysis Method
</label>
<app:cshelp topic="<%=act%>" text="[?]"/> 
</legend>
<br/>
<html:select property="snpAnalysis" styleId="snpAnalysisSelect" disabled="true">
		<option selected="true" value="none"> </option>
		<option value="paired">Paired Tissue Samples</option>
		<option value="unpaired">Unpaired Tissue Samples</option>
		<option value="blood">Blood Samples</option>
</html:select>&nbsp;&nbsp;  
	
</fieldset>

 