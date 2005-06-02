<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>


<fieldset class="gray">
<legend class="red">Sample Identifier
<app:help help="Manually enter Sample Id(s) or Select Sample Id(s) from file using browse button. There must only be one entry per line and a return must be added at the end of the file." />
</legend>
<%
	String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<br>	
&nbsp;&nbsp;
<html:radio property="sampleGroup" value="Specify" styleClass="radio" onfocus="javascript:onRadio(this,0);"/>
<html:text property="sampleList" disabled="false" onfocus="javascript:radioFold(this);" onblur="javascript:cRadio(this, document.forms[0].sampleGroup[0]);" />
&nbsp;-or-&nbsp;
<html:radio property="sampleGroup" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);"/>
<html:file property="sampleFile" disabled="true"  onblur="javascript:cRadio(this, document.forms[0].sampleGroup[1]);" onfocus="javascript:document.forms[0].sampleGroup[1].checked = true;" />
<Br>
<html:errors property="sampleFile"/>
<html:errors property="sampleGroup"/>
<html:errors property="sampleList"/>

</fieldset>
<!-- </html:form> -->