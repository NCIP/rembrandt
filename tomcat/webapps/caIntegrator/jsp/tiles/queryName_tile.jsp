<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<fieldset class="gray">
<legend class="red">
<bean:message key="queryName.label"/>
<b class="req">*</b>
<a href="javascript:void(0);" onmouseover="return overlib('<bean:message key="queryName.help"/>', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</legend>
<%
String act = request.getParameter("act");
%>
<br>
	<!-- <html:form action="<%=act%>"> -->
<html:text property="queryName" size="50" /> (should be unique)
<br /><html:errors property="queryName"/>
	<!-- </html:form> -->
</fieldset>