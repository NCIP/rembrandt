<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<div class="title">Assay Platform</div>
<html:select property="assayPlatform"/>
	<option>&nbsp;</option>
	<option>All</option>
	<option>100K SNP Array</option>
	<option>Array CGH </option>
</html:select>
<html:errors property="assayPlatform"/>