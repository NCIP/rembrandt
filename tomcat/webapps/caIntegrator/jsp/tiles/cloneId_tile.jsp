<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">

<legend class="red">Clone Id/Probe Set Id</legend>
<b class="message">(Paste comma separated Clone Id list, or upload file using Browse button)</b>
<br><br />
<%
	String act = request.getParameter("act");
%>
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
