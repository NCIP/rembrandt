<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<div class="title">Query Name <b class="req">*</b>
<a href="javascript:void(0);" onmouseover="return overlib('Please give a title/name to this query.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</div>
	<!-- <html:form action="/geneexpression"> -->
<html:text property="queryName"/>

<html:errors property="queryName"/>
	<!-- </html:form> -->
<!--
<input type="text" name="queryName" size="50">
-->