<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">

<legend class="red">Clone Id/Probe Set Id</legend>
<b class="message">(Paste comma separated Clone Id list, or upload file using Browse button)</b>
<br><br />
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
	
<html:radio property="cloneId" value="list" styleClass="radio" onclick="javascript:onRadio(this,0);" />
	<html:select property="cloneList">
	<html:optionsCollection property="cloneTypeColl" />
	</html:select>
				<!--- <html:select property="cloneList">
					<html:option value="image">IMAGE Id</html:option>
					<html:option value="bac">BAC Id</html:option>
				</html:select> --->
&nbsp;<html:text property="cloneListSpecify"/>&nbsp;-or-&nbsp;
<html:radio property="cloneId" value="Upload" styleClass="radio" onclick="javascript:onRadio(this,1);" />
			<html:file property="cloneListFile" disabled="true" /></br>
			<html:errors property="cloneId"/></br>
</fieldset>
<!-- </html:form> -->
