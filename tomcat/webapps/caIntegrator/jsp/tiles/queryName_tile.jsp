<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<div class="title">
<bean:message key="queryName.label"/>
<b class="req">*</b>
<a href="javascript:void(0);" onmouseover="return overlib('<bean:message key="queryName.help"/>', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</div>
<%
String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>"> -->
<html:text property="queryName"/>

<html:errors property="queryName"/>
	<!-- </html:form> -->
