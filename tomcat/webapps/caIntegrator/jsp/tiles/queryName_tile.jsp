<%@ page import="java.util.*, java.text.*" %>
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

 String format = "H:mm:ss";
 Date today = new Date();
 SimpleDateFormat formatter = new SimpleDateFormat(format);
 String datenewformat = formatter.format(today);
%>
<br>
	<!-- <html:form action="<%=act%>"> -->
<!-- <html:text property="queryName" size="50" />  -->
<input type="text" name="queryName" size="50" value="<%=act%>_query_<%=datenewformat%>"> (should be unique)
<br /><html:errors property="queryName"/>
	<!-- </html:form> -->
</fieldset>