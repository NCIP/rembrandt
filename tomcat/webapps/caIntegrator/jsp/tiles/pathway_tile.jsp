<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Pathways</div>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->

<html:textarea property="pathways">
			</html:textarea>
			<html:errors property="pathways"/>
<!--
<select size="4" multiple style="width:150px;" name="pathways">
	<option>&nbsp;&nbsp;</option>
</select>
-->
&nbsp;&nbsp;
<input class="sbutton" type="button" style="width:150px" value="Pathway Browser..." disabled="true">

<!-- </html:form> -->