<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>

<%
	String act = request.getParameter("act");
%>
<fieldset class="gray">
<legend class="red">Clone Id/Probe Set Id
<% if(act.equalsIgnoreCase("geneexpression"))	{ %>
<app:help help="Paste a comma delimited IMAGE Clone Id/Affymetrix probe set ID list, or upload a tab-delimited file containing the IDs using the browse button." />
<% } else { %>
<app:help help="Future implementation"/>
<% } %>
</legend>
<b class="message">(Paste comma separated Clone Id list, or upload file using Browse button)</b>
<br><br />

	<!-- <html:form action="<%=act%>" method="get"> -->
	
	
	
	<html:select property="cloneList" disabled="false">
	<html:optionsCollection property="cloneTypeColl" />
	</html:select>
				
&nbsp;
<html:radio property="cloneId" value="list" styleClass="radio" onfocus="javascript:onRadio(this,0);" />
	<html:text property="cloneListSpecify" disabled="false" onfocus="javascript:radioFold(this);"value="" />
	&nbsp;-or-&nbsp;
<html:radio property="cloneId" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);" />
			<html:file property="cloneListFile" disabled="true" /></br>
			<html:errors property="cloneId"/></br>
</fieldset>
<!-- </html:form> -->
