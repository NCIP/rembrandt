<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">CGH Copy Number
<app:help help="Specify the threshold for the copy number by indicating the “amplified”, “deleted” or “unchanged” criteria."/>
</legend><br />

<%
	String act = request.getParameter("act");

%>
	<!-- <html:form action="<%=act%>" > -->

<html:radio property="copyNumber" value="amplified" styleClass="radio"/> Amplified &ge;
				<html:text property="cnAmplified" onfocus="javascript:radioFold(this);" />&nbsp;copies</br>

<html:radio property="copyNumber" value="deleted" styleClass="radio"/> Deleted &nbsp;&nbsp;&ge;
				<html:text property="cnDeleted" onfocus="javascript:radioFold(this);" />&nbsp;copies</br>
<html:radio property="copyNumber" value="ampdel" styleClass="radio"/> Amplified or Deleted &nbsp;
<blockquote>
Amplified&nbsp;&nbsp;&nbsp; &ge;
 <html:text property="cnADAmplified" onfocus="javascript:radioFold(this);" />&nbsp;copies
&nbsp;
<Br>
Deleted&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &ge;
<html:text property="cnADDeleted" onfocus="javascript:radioFold(this);" />&nbsp;copies
&nbsp;

</blockquote>
<html:radio property="copyNumber" value="unchange" styleClass="radio"/>Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="cnUnchangeFrom" onfocus="javascript:radioFold(this);" />-to-
				<html:text property="cnUnchangeTo" onfocus="javascript:radioFold(this);" />&nbsp;copies

<html:errors property="cnerror"/></br>

</fieldset>
<!-- </html:form> -->

