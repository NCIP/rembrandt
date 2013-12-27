<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
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

<input type="radio" name="geneExpressionForm.cloneId" class="radio" id="cloneId" value="list" onfocus="javascript:onRadio(this,0);">
<label for="cloneId">Type Reporters:&nbsp;&nbsp;</label>


<s:select id="cloneList" name="cloneList" list="geneExpressionForm.cloneTypeColl" listKey="value" listValue="label" disabled="false" theme="simple" />
<label for="cloneList">&nbsp;</label>


<s:textfield id="cloneListSpecify" name="geneExpressionForm.cloneListSpecify" disabled="false" 
onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].cloneId[0]);"  theme="simple"/>
<label for="cloneListSpecify">&nbsp;</label>
	
<br/>

<input type="radio" name="geneExpressionForm.cloneId" class="radio" id="cloneIdCloneList" value="geneExpressionForm.cloneList" 
onfocus="javascript:onRadio(this,1);">
<label for="cloneIdCloneList">Choose a saved Reporter List:&nbsp;&nbsp;</label>

<s:select name="geneExpressionForm.cloneListFile" disabled="false" list="geneExpressionForm.savedCloneList" 
	id="cloneListFileDD" theme="simple" />
	<label for="cloneListFileDD">&nbsp;</label>
<br/>

<s:actionerror/>
<br/>
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
