<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Fold Change</div>

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->

<html:radio property="regulationStatus" value="up" styleClass="radio" /> Up-regulation
				<html:text property="foldChangeValueUp"/></br>

<!--			
<input type="radio" class="radio" name="regulationStatus" value="up">Up-regulation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
-->
<!--
<input type="radio" class="radio" name="fold" value="yes" checked selected>
<select>
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;
<input type="radio" class="radio" name="fold" value="no">&nbsp;
-->
<!--
<input type="text" value="other" name="foldChangeValueUp"><Br>
-->
<html:radio property="regulationStatus" value="down" styleClass="radio" /> Down Regulation
				<html:text property="foldChangeValueDown"/></br>
<!--
<input type="radio" class="radio" name="regulationStatus" value="down">Down-regulation&nbsp;
-->
<!--
<input type="radio" class="radio" name="fold2" value="yes" checked selected>
<select>
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;<input type="radio" class="radio" name="fold2" value="no">&nbsp;
-->
<!--
<input type="text" value="other" name="foldChangeValueDown"><Br>
-->
<html:radio property="regulationStatus" value="updown" styleClass="radio" /> Up or Down&nbsp;
<!--
<input type="radio" class="radio" name="regulationStatus" value="updown">Up OR Down&nbsp;
-->
<blockquote>
Up-regulation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!--
<input type="radio" class="radio" name="fold3" value="yes" checked selected>
<select>
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;<input type="radio" class="radio" name="fold3" value="no">&nbsp;
-->
 <html:text property="foldChangeValueUDUp"/>
<!--
<input type="text" value="other" name="foldChangeValueUDUp">
-->
&nbsp;
<Br>
Down-regulation
<!--
<input type="radio" class="radio" name="fold3" value="yes" checked selected>

<select>
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;<input type="radio" class="radio" name="fold3" value="no">&nbsp;
-->
<html:text property="foldChangeValueUDDown"/>
<!--
<input type="text" value="other" name="foldChangeValueUDDown">
-->
&nbsp;
</blockquote>
<html:radio property="regulationStatus" value="unchange" styleClass="radio" />Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="foldChangeValueUnchangeFrom"/>-to-
				<html:text property="foldChangeValueUnchangeTo"/>
<!--
<input type="radio" class="radio" name="regulationStatus" onclick="javascript:document.forms[0].f4.disabled=(!(document.forms[0].f4.disabled));document.forms[0].f4_2.disabled=(!(document.forms[0].f4_2.disabled));" value="unchange">
Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
-->
<!--
<input type="radio" class="radio" name="fold4" value="yes" checked selected>
<select name="f4" disabled="true">
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;
<input type="radio" class="radio" name="fold4" value="no">&nbsp;
-->
<!--
<input type="text" disabled="true" name="foldChangeValueUnchangeFrom" value="other">&nbsp;-to-&nbsp;
<input type="text" disabled="true" name="foldChangeValueUnchangeTo" value="other">
-->
<html:errors property="foldChangeValueUnchange"/>
<html:errors property="regulationStatus"/>
<html:errors property="foldChangeValueUp"/>
<html:errors property="foldChangeValueDown"/>
<html:errors property="foldChangeValueUDUp"/>
<html:errors property="foldChangeValueUDDown"/>
<!-- </html:form> -->