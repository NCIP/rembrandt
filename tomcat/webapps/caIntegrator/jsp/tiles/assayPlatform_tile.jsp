<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>

<!-- <html:form action="<%=act%>" > -->

<fieldset class="gray">
<legend class="red">Assay Platform</legend>
<html:select property="assayPlatform">
<!--	<option>&nbsp;</option> -->
	<option>All</option>
	<option value="Affymetrix 100K SNP Arrays">100K SNP Array</option>
	<option>Array CGH </option> 
</html:select>
<html:errors property="assayPlatform"/>
</fieldset>
<!-- </html:form> -->
