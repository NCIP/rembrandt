<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">Array Platform</legend>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<br>	
<html:select property="arrayPlatform">
					<html:option value="all">All</html:option>
					<html:option value="oligo">Oligo (Affymetrix)</html:option>
					<html:option value="cDNA">cDNA</html:option>
			</html:select>
			<html:errors property="arrayPlatform"/>
</fieldset>
<!-- </html:form> -->