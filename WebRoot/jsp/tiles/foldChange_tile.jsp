<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Fold Change
<app:help help="Specify the threshold for the differential regulation by indicating the “up”, “down” or “unchanged” criteria. This will bring back differential expression ratios between tumor and non-tumor samples for a particular reporter"/>
</legend>

<%
	String act = request.getParameter("act");

%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<br />
<html:radio property="regulationStatus" value="up" styleClass="radio" /> Up-regulation &ge;
				<html:text property="foldChangeValueUp" size="3" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].regulationStatus[0]);" />&nbsp;fold(s)</br>

				<html:errors property="isAllGenes" />
				
<html:radio property="regulationStatus" value="down" styleClass="radio" /> Down Regulation &ge;
<html:text property="foldChangeValueDown" size="3" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].regulationStatus[1]);" />&nbsp;fold(s)</br>
<html:radio property="regulationStatus" value="updown" styleClass="radio" /> Up or Down&nbsp;
<blockquote>
Up-regulation &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &ge;
 <html:text property="foldChangeValueUDUp" size="3" onfocus="javascript:radioFold(this);" />&nbsp;fold(s)
&nbsp;
<Br>
Down-regulation &nbsp;&nbsp; &ge;
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
