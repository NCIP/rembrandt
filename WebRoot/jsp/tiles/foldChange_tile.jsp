<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Fold_tooltip";

%>
<fieldset class="gray">
<legend class="red">Fold Change
<!-- <app:help help="Specify the threshold of differential regulation."/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>   
</legend>


	
<br />
<html:errors property="regulationStatusAllGenes" /><br />

<html:radio styleId="regulationStatus1" property="regulationStatus" value="up" styleClass="radio" /> <label for="regulationStatus1">Up-regulation</label> &ge;
				<html:text styleId="foldChangeValueUp" property="foldChangeValueUp" size="3" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].regulationStatus[0]);" /><label for="foldChangeValueUp">&nbsp;fold(s)</label></br>				
<html:radio styleId="regulationStatus2" property="regulationStatus" value="down" styleClass="radio" /> <label for="regulationStatus2">Down Regulation</label> &ge;
<html:text styleId="foldChangeValueDown" property="foldChangeValueDown" size="3" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].regulationStatus[1]);" /><label for="foldChangeValueDown">&nbsp;fold(s)</label></br>
<html:radio styleId="regulationStatus3" property="regulationStatus" value="updown" styleClass="radio" /> <label for="regulationStatus3">Up or Down&nbsp;</label>
<blockquote>
Up-regulation &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &ge;
 <html:text styleId="foldChangeValueUDUp" property="foldChangeValueUDUp" size="3" onfocus="javascript:radioFold(this);" /><label for="foldChangeValueUDUp">&nbsp;fold(s)</label>
&nbsp;
<Br>
Down-regulation &nbsp;&nbsp; &ge;
<html:text styleId="foldChangeValueUDDown" property="foldChangeValueUDDown" size="3" onfocus="javascript:radioFold(this);" /><label for="foldChangeValueUDDown">&nbsp;fold(s)</label>
&nbsp;
</blockquote>
<html:radio styleId="regulationStatus4" property="regulationStatus" value="unchange" styleClass="radio" /><label for="regulationStatus4">Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<html:text styleId="foldChangeValueUnchangeFrom" property="foldChangeValueUnchangeFrom" size="3" onfocus="javascript:radioFold(this);" /><label for="foldChangeValueUnchangeFrom">-to-</label>
				<html:text styleId="foldChangeValueUnchangeTo" property="foldChangeValueUnchangeTo" size="3" onfocus="javascript:radioFold(this);" /><label for="foldChangeValueUnchangeTo">&nbsp;fold(s)</label>
<html:errors property="foldChangeValueUnchangeFrom"/>
<html:errors property="foldChangeValueUnchangeTo"/>
<html:errors property="regulationStatus"/>
<html:errors property="foldChangeValueUp"/>
<html:errors property="foldChangeValueDown"/>
<html:errors property="foldChangeValueUDUp"/>
<html:errors property="foldChangeValueUDDown"/>
</fieldset>

