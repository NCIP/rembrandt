<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">Pathways</legend>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<Br>
<html:textarea property="pathways">
			</html:textarea>
			<html:errors property="pathways"/>

&nbsp;&nbsp;
<input class="sbutton" type="button" style="width:150px" value="Pathway Browser..." disabled="true">
</fieldset>
<!-- </html:form> -->