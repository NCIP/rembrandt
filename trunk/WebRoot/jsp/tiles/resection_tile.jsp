<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act");
%>



<fieldset class="gray">
<legend class="red">Re-Resection Sets
<a href="javascript: Help.popHelp('<%=act%>_excludeResections_tooltip');">[?]</a>   

<!-- <app:help help="Indicate the tissue type that was used for the comparative genomic study."/>-->
</legend><br />

&nbsp;&nbsp;
<input type="checkbox" name="excludeResections" class="radio" >Exclude Re-Resection Sets
&nbsp;&nbsp;
<html:errors property="excludeResections"/>
</fieldset>

