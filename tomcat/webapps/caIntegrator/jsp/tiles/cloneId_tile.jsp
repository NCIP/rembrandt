<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Clone Id/Probe Set Id</div>
<b class="message">(Paste comma separated Clone Id list, or upload file using Browse button)</b>
<br>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
	
<html:radio property="cloneId" value="list" styleClass="radio" />
	<html:select property="cloneList">
	<html:optionsCollection property="cloneTypeColl" />
	</html:select>
				<!--- <html:select property="cloneList">
					<html:option value="image">IMAGE Id</html:option>
					<html:option value="bac">BAC Id</html:option>
				</html:select> --->
&nbsp;<html:text property="cloneListSpecify"/>&nbsp;-or-&nbsp;
<html:radio property="cloneId" value="file" styleClass="radio" disabled="true" />
				<html:file property="cloneListFile" disabled="true" /></br>
			<html:errors property="cloneId"/></br>
<!--
<input type="radio" class="radio" name="cloneId" value="list" checked selected>
<select name="cloneList">
	<option>&nbsp;</option>
	<option>IMAGE Id</option>
	<option>BAC Id</option>
	<option>Probe Set Id</option>
</select>
&nbsp;
<input type="text" name="cloneListSpecify">
&nbsp;&nbsp;

<input type="radio" class="radio" name="cloneId" value="file">
<input type="file" name="cloneListFile">
-->
<!-- </html:form> -->
