<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Fold Change</div>

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->

<html:radio property="regulationStatus" value="up" styleClass="radio" /> Up-regulation
				<html:text property="foldChangeValueUp"/></br>

<html:radio property="regulationStatus" value="down" styleClass="radio" /> Down Regulation
				<html:text property="foldChangeValueDown"/></br>
<html:radio property="regulationStatus" value="updown" styleClass="radio" /> Up or Down&nbsp;
<blockquote>
Up-regulation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <html:text property="foldChangeValueUDUp"/>
&nbsp;
<Br>
Down-regulation
<html:text property="foldChangeValueUDDown"/>
&nbsp;
</blockquote>
<html:radio property="regulationStatus" value="unchange" styleClass="radio" />Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="foldChangeValueUnchangeFrom"/>-to-
				<html:text property="foldChangeValueUnchangeTo"/>
<html:errors property="foldChangeValueUnchange"/>
<html:errors property="regulationStatus"/>
<html:errors property="foldChangeValueUp"/>
<html:errors property="foldChangeValueDown"/>
<html:errors property="foldChangeValueUDUp"/>
<html:errors property="foldChangeValueUDDown"/>
<!-- </html:form> -->
