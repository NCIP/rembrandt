<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_excludeResections_tooltip";
%>



<fieldset class="gray">
<legend class="red">Re-Resection Tumor Samples:

<app:cshelp topic="<%=act%>" text="[?]"/>

</legend><br />

&nbsp;&nbsp;
<input id="excludeResections" type="checkbox" name="form.excludeResections" class="radio" >
<label for="excludeResections">Exclude Re-Resection Tumor Samples</label>
&nbsp;&nbsp;
<s:fielderror fieldName="excludeResections" />
</fieldset>

