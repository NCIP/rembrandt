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

<!--  
<logic:notPresent name="deleteQueryForm">
<input type="button" id="clearButton" class="xbutton" value="Clear" onclick="refresh();"/>
</logic:notPresent>
-->
&nbsp;&nbsp;

<!--  html:button styleClass="xbutton" property="method" value="Cancel" onclick="javascript:alertUser('menu');" /> -->
 <input type="submit" id="submitButton" onclick="" class="xbutton" value="Cancel" onclick="javascript:alertUser('menu');"/>
&nbsp;&nbsp;

<!--  logic:present name="geneexpressionForm">-->
<s:if test="geneExpressionForm != null">
	<!--  logic:empty name="geneexpressionForm" property="geneOption" scope="request">-->
	<s:if test="geneExpressionForm.geneOption == null || geneExpressionForm.geneOption.length() == 0" >
			
		<input type="submit" id="previewButton" class="xbutton" value="Preview" 
			onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
		&nbsp;&nbsp;
 	</s:if>
	
	<s:if test="geneExpressionForm.geneOption.equals('standard')"> 
		<input type="submit" id="previewButton" class="xbutton" value="PreviewShan" 
			onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
	&nbsp;&nbsp;
 	</s:if>
 	
	<s:if test="geneExpressionForm.geneOption.equals('geneList')">
	<input type="submit" id="previewButton" class="xbutton" value="PreviewYang" 
		onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');"/>
		&nbsp;&nbsp;
 	</s:if>
</s:if>
<!--  
<logic:present name="comparitivegenomicForm">
	<logic:empty name="comparitivegenomicForm" property="geneOption" scope="request">
		<html:submit styleId="previewButton" styleClass="xbutton" property="method" onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');">
			<bean:message key="buttons_tile.previewButton" />
		</html:submit>&nbsp;&nbsp;
	</logic:empty>
	<logic:equal name="comparitivegenomicForm" property="geneOption" scope="request" value="standard">
		<html:submit styleId="previewButton" styleClass="xbutton" property="method" onclick="return GeneAlias.validateAliases($('geneList').value, 'Preview');">
			<bean:message key="buttons_tile.previewButton" />
		</html:submit>&nbsp;&nbsp;
	</logic:equal>
	<logic:equal name="comparitivegenomicForm" property="geneOption" scope="request" value="geneList">
		<html:submit styleId="previewButton" styleClass="xbutton" property="method" onclick="return GeneAlias.validateAliases($('geneList').value 'Preview');">
			<bean:message key="buttons_tile.previewButton" />
		</html:submit>&nbsp;&nbsp;
 	</logic:equal>
</logic:present>
<logic:present name="clinicaldataForm">
	<html:submit styleId="previewButton" styleClass="xbutton" property="method">
		<bean:message key="buttons_tile.previewButton" />
	</html:submit>&nbsp;&nbsp;
</logic:present>
-->

<input type="submit" id="submittalButton" class="subButton" value="SubmitShan" onclick="return gecnSubmit();"/>

<input type="submit" id="multiUseButton" class="subButtonInv" value="MultiUse"/>
