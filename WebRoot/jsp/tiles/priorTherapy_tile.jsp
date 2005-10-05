
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	     String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<fieldset class="gray">
<legend class="red">Prior Therapy
<app:help help="Future implementation"/>
</legend><br />	
<input type="checkbox" name="radiation"  class="radio" disabled="true" onclick="javascript:document.forms[0].rad.disabled=(!(document.forms[0].rad.disabled));">Radiation&nbsp;&nbsp;
<!--- <select property="radiationType" name="rad" disabled="true">
	<option>any</option>
	<option>photon</option>
</select> --->

<html:select property="radiationType" disabled="true">
<html:optionsCollection property="radiationTypeColl" />
</html:select><html:errors property="radiationType"/>

<br>
<input name="chemo" type="checkbox" class="radio" disabled="true" onclick="javascript:document.forms[0].chemotherapy.disabled=(!(document.forms[0].chemotherapy.disabled));">Chemo&nbsp;&nbsp;
- agent:&nbsp;
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<html:select property="chemoType" disabled="true">
<html:optionsCollection property="chemoAgentTypeColl" />
</html:select><html:errors property="chemoType"/>

<Br>
<input name="sugery" type="checkbox" class="radio" disabled="true"
 onclick="javascript:document.forms[0].surgery.disabled=(!(document.forms[0].surgery.disabled));">Surgery&nbsp;&nbsp;
<!--- <select property="surgeryType" name="surgery" disabled="true">
	<option>any</option>
	<option>Complete Resection (CR)</option>
	<option>Partial Resection (PR)</option>
	<option>Biopsy Only (BX)</option>
</select> --->

<html:select property="surgeryType" disabled="true">
<html:optionsCollection property="surgeryTypeColl" />
</html:select><html:errors property="surgeryType"/>

<!-- </html:form> -->
</fieldset>