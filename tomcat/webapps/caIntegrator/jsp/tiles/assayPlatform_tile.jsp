<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>

<!-- <html:form action="<%=act%>" > -->

<fieldset class="gray">
<legend class="red">Assay Platform</legend><br />
&nbsp;&nbsp;<html:select property="assayPlatform" onchange="javascript:depChange(this);">
<!--	<option>&nbsp;</option> -->
    <option value="Affymetrix 100K SNP Arrays">100K SNP Array</option>
	<!--<option>Array CGH </option> -->
	<option>All</option>
</html:select>
<html:errors property="assayPlatform"/>
</fieldset>
<!-- </html:form> -->
