<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act");
%>



<fieldset class="gray">
<legend class="red">Re-Resection Tumor Samples:

 <a href="javascript: Help.popHelp('<%=act%>_excludeResections_tooltip');">[?]</a>

</legend><br />

&nbsp;&nbsp;
<input type="checkbox" name="excludeResections" class="radio" >Exclude Re-Resection Tumor Samples
&nbsp;&nbsp;
<html:errors property="excludeResections"/>
</fieldset>
