<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Clone Id/Probe Set Id</div>
<b class="message">(Paste comma separated Clone Id list, or upload file using Browse button)</b>
<br>
<!-- <html:form action="/geneexpression"> -->
<html:radio property="cloneId" value="list"/>
				<html:select property="cloneList">
					<html:option value="image">IMAGE Id</html:option>
					<html:option value="bac">BAC Id</html:option>
				</html:select>
&nbsp;<html:text property="cloneListSpecify"/>&nbsp;&nbsp;
<html:radio property="cloneId" value="file"/>
				<html:file property="cloneListFile"/></br>
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