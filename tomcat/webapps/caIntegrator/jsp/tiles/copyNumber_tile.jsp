<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">CGH Copy Number</div>

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" > -->

<html:radio property="copyNumber" value="amplified" styleClass="radio"/> Amplified
				<html:text property="cnAmplified"/></br>

<html:radio property="copyNumber" value="deleted" styleClass="radio"/> Deleted
				<html:text property="cnDeleted"/></br>
<html:radio property="copyNumber" value="ampdel" styleClass="radio"/> Amplified or Deleted &nbsp;
<blockquote>
Amplified&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <html:text property="cnADAmplified"/>
&nbsp;
<Br>
Deleted
<html:text property="cnADDeleted"/>
&nbsp;

</blockquote>
<html:radio property="copyNumber" value="unchange" styleClass="radio"/>Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="cnUnchangeFrom"/>-to-
				<html:text property="cnUnchangeTo"/>

<html:errors property="cnerror"/></br>


<!-- </html:form> -->

