
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	     String act = request.getParameter("act");
%>

<fieldset class="gray">
<legend class="red">Prior Therapy
<a href="javascript: Help.popHelp('clinical_Prior_tooltip');">[?]</a>    
<!-- <app:help help="Specify the therapy received before enrollment in the current study"/>-->
</legend><br />	
<html:checkbox styleClass="radio" property="radiation" value="Specify"/>Radiation&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;- radiation type:&nbsp;
<html:select property="radiationType" >
<html:optionsCollection property="radiationTypeColl" />
</html:select><html:errors property="radiationType"/>

<br>
<html:checkbox styleClass="radio" property="chemo"  value="Specify"/>Chemo&nbsp;&nbsp;
- agent:&nbsp;
<!--- <select property="chemoType" name="chemo" disabled="true">
	<option>any</option>
	<option></option>
</select> --->

<html:select property="chemoType" >
<html:optionsCollection property="chemoAgentTypeColl" />
</html:select><html:errors property="chemoType"/>

<Br>
<html:checkbox styleClass="radio" property="surgery"  value="Specify"/>Surgery&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;- title:&nbsp;
<html:select property="surgeryTitle" >
<html:optionsCollection property="surgeryTitleColl" />
</html:select><html:errors property="surgeryTitle"/>

<Br>
&nbsp;&nbsp;&nbsp;&nbsp;- Outcome:&nbsp;
<html:select property="surgeryOutcome" >
<html:optionsCollection property="surgeryOutcomeColl" />
</html:select><html:errors property="surgeryOutcome"/>


</fieldset>