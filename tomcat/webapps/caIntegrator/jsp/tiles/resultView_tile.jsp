<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">Result View <b class="req">*</b>
<a href="javascript:void(0);" onmouseover="return overlib('Description of Gene View, Description of Sample View', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</legend>
<%
	String act = request.getParameter("act");
%>
<br>
	<!-- <html:form action="<%=act%>" method="get"> -->
<html:select property="resultView">
				<html:option value="sample">Sample Centric</html:option>
				<html:option value="gene">Gene Centric</html:option>
</html:select><html:errors property="resultView"/>
	<!-- </html:form> -->

&nbsp;<a href="#" class="message">[sample centric example]</a>&nbsp;<a href="#" class="message">[gene centric example]</a>
</fieldset>
					