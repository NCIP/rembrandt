<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Pathways</div>
<!-- <html:form action="/geneexpression"> -->

<html:textarea property="pathways">
			</html:textarea>
			<html:errors property="pathways"/>
<!--
<select size="4" multiple style="width:150px;" name="pathways">
	<option>&nbsp;&nbsp;</option>
</select>
-->
&nbsp;&nbsp;
<input class="sbutton" type="button" style="width:150px" value="Pathway Browser...">

<!-- </html:form> -->