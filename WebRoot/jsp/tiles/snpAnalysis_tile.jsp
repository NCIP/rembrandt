<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
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
<select name="form.snpAnalysis" id="snpAnalysisSelect" disabled="disabled">
		<option selected="selected" value="none"> </option>
		<option value="paired">Paired Tissue Samples</option>
		<option value="unpaired">Unpaired Tissue Samples</option>
		<option value="blood">Blood Samples</option>
</select>&nbsp;&nbsp;  
	
</fieldset>

 