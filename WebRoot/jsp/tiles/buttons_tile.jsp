<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@page contentType="text/html"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%
long randomness = System.currentTimeMillis(); //prevent image caching
%>
<script language="javascript">
document.forms[0].target = "_self";

var sURL = unescape(window.location.pathname);
var s = sURL.replace('/rembrandt/','');
function refresh()	{
    window.location.href = s;
}
</script>


<s:if test="deleteQueryForm == null">
<input type="button" id="clearButton" class="xbutton" value="Clear" onclick="refresh();"/>
</s:if>

&nbsp;&nbsp;

<!--  html:button styleClass="xbutton" property="method" value="Cancel" onclick="javascript:alertUser('menu');" /> -->
 <input type="button" id="submitButton" onclick="" class="xbutton" value="Cancel" onclick="javascript:alertUser('menu');"/>
&nbsp;&nbsp;

<s:if test="comparativeGenomicForm != null">
	<s:if test="comparativeGenomicForm.geneOption == null || geneExpressionForm.geneOption.length() == 0" >
			
		<input type="button" id="previewButton" class="xbutton" value="Preview-CG" 
			onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
		&nbsp;&nbsp;
 	</s:if>	
	
	<s:if test="comparativeGenomicForm.geneOption.equals('standard')"> 
		<input type="button" id="previewButton" class="xbutton" value="Preview-CG2" 
			onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
	&nbsp;&nbsp;
 	</s:if>
	
	<s:if test="comparativeGenomicForm.geneOption.equals('geneList')">
	<input type="button" id="previewButton" class="xbutton" value="Preview-CG3" 
		onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
		&nbsp;&nbsp;
 	</s:if>
</s:if>

<s:elseif test="geneExpressionForm != null">
	<!--  logic:empty name="geneexpressionForm" property="geneOption" scope="request">-->
	<s:if test="geneExpressionForm.geneOption == null || geneExpressionForm.geneOption.length() == 0" >
			
		<input type="button" id="previewButton" class="xbutton" value="Preview-GE" 
			onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
		&nbsp;&nbsp;
 	</s:if>
	
	<s:if test="geneExpressionForm.geneOption.equals('standard')"> 
		<input type="button" id="previewButton" class="xbutton" value="Preview-GE2" 
			onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
	&nbsp;&nbsp;
 	</s:if>
 	
	<s:if test="geneExpressionForm.geneOption.equals('geneList')">
	<input type="button" id="previewButton" class="xbutton" value="Preview-GE3" 
		onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
		&nbsp;&nbsp;
 	</s:if>
</s:elseif>
<s:else></s:else>

<!--  
<logic:present name="clinicaldataForm">
	<html:submit styleId="previewButton" styleClass="xbutton" property="method">
		<bean:message key="buttons_tile.previewButton" />
	</html:submit>&nbsp;&nbsp;
</logic:present>
-->

<input type="button" id="submittalButton" class="subButton" value="Submit" onclick="return gecnSubmit();"/>

<!--  input type="submit" id="multiUseButton" class="subButtonInv" value="MultiUse"/> -->
<!--  s:submit type="submit" action="getCytobands" id="multiUseButton" class="subButtonInv" value="MultiUse" theme="simple"/>-->
