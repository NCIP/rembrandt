<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Result View <b class="req">*</b>
<a href="javascript:void(0);" onmouseover="return overlib('Description of Gene View, Description of Sample View', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</div>
	<!-- <html:form action="/geneexpression"> -->
<html:select property="resultView">
				<html:option value="sample">Sample Centric</html:option>
				<html:option value="gene">Gene Centric</html:option>
</html:select><html:errors property="resultView"/>
	<!-- </html:form> -->
<!--
<Select name="resultView">
	<option>&nbsp;</option>
	<option>Sample Centric</option>
	<option>Gene Centric</option>
</select>
-->
&nbsp;<a href="#" class="message">[sample centric example]</a>&nbsp;<a href="#" class="message">[gene centric example]</a>
					