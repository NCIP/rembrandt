<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">CGH Copy Number</legend><br />

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" > -->

<html:radio property="copyNumber" value="amplified" styleClass="radio"/> Amplified
				<html:text property="cnAmplified" onclick="javascript:radioFold(this);" />&nbsp;copies</br>

<html:radio property="copyNumber" value="deleted" styleClass="radio"/> Deleted&nbsp;&nbsp;
				<html:text property="cnDeleted" onclick="javascript:radioFold(this);" />&nbsp;copies</br>
<html:radio property="copyNumber" value="ampdel" styleClass="radio"/> Amplified or Deleted &nbsp;
<blockquote>
Amplified&nbsp;&nbsp;&nbsp;
 <html:text property="cnADAmplified" onclick="javascript:radioFold(this);" />&nbsp;copies
&nbsp;
<Br>
Deleted&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:text property="cnADDeleted" onclick="javascript:radioFold(this);" />&nbsp;copies
&nbsp;

</blockquote>
<html:radio property="copyNumber" value="unchange" styleClass="radio"/>Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="cnUnchangeFrom" onclick="javascript:radioFold(this);" />-to-
				<html:text property="cnUnchangeTo" onclick="javascript:radioFold(this);" />&nbsp;copies

<html:errors property="cnerror"/></br>

</fieldset>
<!-- </html:form> -->

