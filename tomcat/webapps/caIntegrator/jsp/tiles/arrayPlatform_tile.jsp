<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="gov.nih.nci.nautilus.criteria.Constants"%>

<fieldset class="gray">
<legend class="red">Array Platform</legend>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<br>	
&nbsp;&nbsp;<html:select property="arrayPlatform">
					<html:option value="<%=Constants.ALL_PLATFROM%>">All</html:option>
					<html:option value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Oligo (Affymetrix)</html:option>
					<html:option value="<%=Constants.CDNA_ARRAY_PLATFORM%>">cDNA</html:option>
			</html:select>
			<html:errors property="arrayPlatform"/>
</fieldset>
<!-- </html:form> -->