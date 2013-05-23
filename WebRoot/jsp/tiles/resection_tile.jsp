<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_excludeResections_tooltip";
%>



<fieldset class="gray">
<legend class="red">Re-Resection Tumor Samples:

<app:cshelp topic="<%=act%>" text="[?]"/>

</legend><br />

&nbsp;&nbsp;
<input id="excludeResections" type="checkbox" name="excludeResections" class="radio" ><label for="excludeResections">Exclude Re-Resection Tumor Samples</label>
&nbsp;&nbsp;
<html:errors property="excludeResections"/>
</fieldset>

