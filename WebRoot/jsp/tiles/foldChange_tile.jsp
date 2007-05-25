<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act");

%>
<fieldset class="gray">
<legend class="red">Fold Change
<!-- <app:help help="Specify the threshold of differential regulation."/>-->
<a href="javascript: Help.popHelp('<%=act%>_Fold_tooltip');">[?]</a>    
</legend>


	
<br />
<html:errors property="regulationStatusAllGenes" /><br />

<html:radio property="regulationStatus" value="up" styleClass="radio" /> Up-regulation &ge;
				<html:text property="foldChangeValueUp" size="3" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].regulationStatus[0]);" />&nbsp;fold(s)</br>				
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
<html:errors property="foldChangeValueUnchangeFrom"/>
<html:errors property="foldChangeValueUnchangeTo"/>
<html:errors property="regulationStatus"/>
<html:errors property="foldChangeValueUp"/>
<html:errors property="foldChangeValueDown"/>
<html:errors property="foldChangeValueUDUp"/>
<html:errors property="foldChangeValueUDDown"/>
</fieldset>

