<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Array Platform</div>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
	
<html:select property="arrayPlatform">
					<html:option value="all">All</html:option>
					<html:option value="oligo">Oligo (Affymetrix)</html:option>
					<html:option value="cDNA">cDNA</html:option>
			</html:select>
			<html:errors property="arrayPlatform"/>
<!--
<select name="arrayPlatform">
	<option>&nbsp;</option>
	<option>All</option>
	<option>Oligo (Affymetrix)</option>
	<option>cDNA</option>
</select>
-->
<!-- </html:form> -->