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

<s:if test="queryKey == null">
<input type="button" id="clearButton" class="xbutton" value="Clear" onclick="refresh();"/>
</s:if>

&nbsp;&nbsp;

<input type="button" id="submitButton" onclick="" class="xbutton" value="Cancel" onclick="javascript:alertUser('menu');"/>
&nbsp;&nbsp;

<!-- used in  cytobandChange script -->
<s:set var="submitAction" value="form.getSubmitActionName()" />
<s:hidden id="submitAction" value="%{submitAction}"  />

<s:set var="previewAction" value="form.getPreviewActionName()" />
<s:hidden id="previewAction" value="%{previewAction}"  />


	<s:if test="form.geneOption == null || form.geneOption.length() == 0" >
			
		<input type="button" id="previewButton" name="previewButton" class="xbutton" value="Preview" 
			onclick="handlePreviewButton();"/>
			
		&nbsp;&nbsp;
 	</s:if>
	
	<s:if test="form.geneOption.equals('standard')"> 
	<!--  
			<s:submit type="button" id="previewButton" action="gePreview" class="xbutton" theme="simple"
				onclick="handlePreviewButton();">Preview-GE2</s:submit>
				-->
			<input type="button" id="previewButton" name="previewButton" class="xbutton" value="Preview" 
			onclick="handlePreviewButton();"/>
	&nbsp;&nbsp;
 	</s:if>
 	
	<s:if test="form.geneOption.equals('geneList')">
	<input type="button" id="previewButton" name="previewButton" class="xbutton" value="Preview" 
		onclick="handlePreviewButton();"/>
		&nbsp;&nbsp;
 	</s:if>
 	
 	<s:if test="#previewAction.startsWith('cd')">
 		<input type="button" id="previewButton" name="previewButton" class="xbutton" value="Preview" 
		onclick="handlePreviewButton();"/>
		&nbsp;&nbsp;
 	</s:if>

<input type="button" id="submittalButton" class="subButton" value="Submit" onclick="handleSubmitButton();"/>

<!--  input type="submit" id="multiUseButton" class="subButtonInv" value="MultiUse"/> -->
<!--  s:submit type="submit" action="getCytobands" id="multiUseButton" class="subButtonInv" value="MultiUse" theme="simple"/>-->

<script language="javascript">
function handlePreviewButton()	{
	var actionName = document.getElementById("previewAction").value;
	//alert(actionName);
	if (actionName != 'cdPreview')
		var ret = GeneAlias.validateAliases($('geneList').value, 'Preview');
	
	document.forms[0].action = actionName + '.action';
	document.forms[0].submit();
	
}

function handleSubmitButton()	{
	
	var actionName = document.getElementById("submitAction").value;
	
	if (actionName != 'cdSubmit')
		var ret = gecnSubmitX();
	
	//alert(actionName);
	document.forms[0].action = actionName + '.action';
	document.forms[0].submit();
	
}

function gecnSubmitX() {
	
	var geneOptionVal = document.forms[0].geneOption1.value;
	
	if (document.forms[0].geneOption1.checked) {
		var geneOptionVal = document.forms[0].geneOption1.value;
		//alert(geneOptionVal);
		if ( geneOptionVal == "standard" ) {
			//alert("Checking geneList...");
		    var ret = GeneAlias.validateAliases($('geneList').value, 'Submit');
		    //if (ret == false)
		    //	alert("Not valid");
		    //else 
		    //	alert ("Valid");
		  }
	}
	
	//document.forms[0].target='_self';	
	//for (var i=0; i < document.forms[0].geneOption.length; i++) {
	//	var geneOptionVal = document.forms[0].geneOption[i].value;
	//	  if (document.forms[0].geneOption[i].checked) {
	//		  if ( geneOptionVal == "standard" ) {
	//		      return GeneAlias.validateAliases($('geneList').value, 'Submit');
	//		  }
	//	      break;
	//  	}
	//}
	//return;
}
</script>
