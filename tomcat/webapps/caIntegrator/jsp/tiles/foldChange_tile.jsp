<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">Fold Change</legend>

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<br />
<html:radio property="regulationStatus" value="up" styleClass="radio" /> Up-regulation &gt;=
				<html:text property="foldChangeValueUp" size="3" onfocus="javascript:radioFold(this);" />&nbsp;fold(s)</br>

<html:radio property="regulationStatus" value="down" styleClass="radio" /> Down Regulation &lt;=
<html:text property="foldChangeValueDown" size="3" onfocus="javascript:radioFold(this);" />&nbsp;fold(s)</br>
<html:radio property="regulationStatus" value="updown" styleClass="radio" /> Up or Down&nbsp;
<blockquote>
Up-regulation &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &gt;=
 <html:text property="foldChangeValueUDUp" size="3" onfocus="javascript:radioFold(this);" />&nbsp;fold(s)
&nbsp;
<Br>
Down-regulation &nbsp;&nbsp; &lt;=
<html:text property="foldChangeValueUDDown" size="3" onfocus="javascript:radioFold(this);" />&nbsp;fold(s)
&nbsp;
</blockquote>
<html:radio property="regulationStatus" value="unchange" styleClass="radio" />Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="foldChangeValueUnchangeFrom" size="3" onfocus="javascript:radioFold(this);" />-to-
				<html:text property="foldChangeValueUnchangeTo" size="3" onfocus="javascript:radioFold(this);" />&nbsp;fold(s)
<html:errors property="foldChangeValueUnchange"/>
<html:errors property="regulationStatus"/>
<html:errors property="foldChangeValueUp"/>
<html:errors property="foldChangeValueDown"/>
<html:errors property="foldChangeValueUDUp"/>
<html:errors property="foldChangeValueUDDown"/>
</fieldset>
<!-- </html:form> -->
