<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<%
	String act = request.getParameter("act") + "_Clone_tooltip";
%>
<fieldset class="gray">
<legend class="red">Clone Id/Probe Set Id
<% if(act.equalsIgnoreCase("geneexpression"))	{ %>
<app:cshelp topic="<%=act%>" text="[?]"/>
<!-- <app:help help="Enter comma-delimited IDs or select a saved list." />-->
<% } else { %>
<!-- <app:help help="Future implementation"/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>

<% } %>
</legend>
<!-- <b class="message">(Paste comma separated Clone Id list, or upload file using Browse button)</b> -->
<br />


<html:radio styleId="cloneId" property="cloneId" value="list" styleClass="radio" onfocus="javascript:onRadio(this,0);" />
<label for="cloneId">Type Reporters:&nbsp;&nbsp;</label>
<html:select styleId="cloneList" property="cloneList" disabled="false">
	<html:optionsCollection property="cloneTypeColl" />
</html:select>
<label for="cloneList">&nbsp;</label>
<html:text styleId="cloneListSpecify" property="cloneListSpecify" disabled="false" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].cloneId[0]);" /><label for="cloneListSpecify">&nbsp;</label>
	
<br/>

<html:radio property="cloneId" value="cloneList" styleId="cloneIdCloneList" styleClass="radio" onfocus="javascript:onRadio(this,1);" />
<label for="cloneIdCloneList">Choose a saved Reporter List:&nbsp;&nbsp;</label>
<html:select property="cloneListFile" disabled="false" styleId="cloneListFileDD">
	<html:optionsCollection property="savedCloneList" />
</html:select><label for="cloneListFileDD">&nbsp;</label>
<br/>

<!--  <html:file property="cloneListFile" disabled="true"  onblur="javascript:cRadio(this, document.forms[0].cloneId[1]);" onfocus="javascript:document.forms[0].cloneId[1].checked = true;" /> -->
<!--<app:help help="Only files of type \"*.txt\" with each entry in a new line are accepted. Upper limit for this option is 500 entries in the txt file." /></br>-->
<html:errors property="cloneId"/><br/>
</fieldset>

<script type='text/javascript'>
if($("cloneListFileDD").options.length<1)	{
	//alert('theres no gene lists to choose from');
	try	{
		$("cloneIdCloneList").checked = $("cloneIdCloneList").selected = false;
		$("cloneIdCloneList").disabled = true;
		$("cloneListFileDD").disabled = true;
	}
	catch(err){}
}

</script>
