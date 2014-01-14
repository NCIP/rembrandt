<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<br>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<% 
/*
 * Gene Tile - used in: GeneExpression form, CGH form
 */

String act = request.getParameter("act") + "_Gene_tooltip";
%>

<A NAME="geneTile">
<fieldset class="gray">
<legend class="red"><label for="geneOption1">Gene</label>
    <app:cshelp topic="<%=act%>" text="[?]"/>
</legend>

<br/>
	
	<!-- html:radio styleId="geneOption1" property="geneOption" styleClass="radio" value="standard" onclick="submitStandardQuery();"/>  -->
	
	<input type="radio" name="form.geneOption" id="geneOption1" class="radio" value="standard" />
	<label for="geneType">Type Genes:&nbsp;&nbsp;</label>
	<s:select name="form.geneType" id="geneType" disabled="false" 
		list="form.geneTypeColl" listKey="value" listValue="label" theme="simple" 
		onchange="checkStandardOption();GeneAlias.armAliasLink();" />
	<br/><br/>
	
 	<s:textarea name="form.geneList" id="geneList" cols="65" rows="5" disabled="false" 
 		onclick="checkStandardOption();" theme="simple"/>
	<span valign="middle">
		<a href="#" id="aliasLink" onclick="GeneAlias.checkAlias($('geneList').value);return false;">check aliases</a><label for="geneList">&nbsp;</label>
	</span>	
			
	<img alt="indicator" src="images/indicator.gif" id="indicator" style="display:none;"/>
	<br/><br/>
	<div id="gAliases" style="display:none; margin-left:20px;border:1px solid #AB0303;"></div>
	<br/><br/>
			
	<input type="radio" name="form.geneOption" class="radio" value="form.geneList" disabled="false" id="geneOptionGeneList"   
		onclick="submitStandardQuery();">
	<label for="geneOptionGeneList">&nbsp;</label>
		
	<label for="geneFileDD">Choose a saved Gene List:&nbsp;&nbsp;</label>
	
	<s:select name="form.geneFile" disabled="false" id="geneFileDD" list="form.savedGeneList" theme="simple" />
	
	<br/>
		   		
	<!-- html:errors property="geneFile"/> -->
	<!--  html:errors property="geneGroup"/>-->
	<!--  html:errors property="geneList"/>-->
	<!--  html:errors property="geneType"/>-->
	<s:actionerror name="geneFile"/>
	<s:actionerror name="geneGroup"/>
	<s:actionerror name="geneList"/>
	<s:actionerror name="geneType"/>
	
	
<br/>
		
</fieldset>	
</A>
	
<script language="javascript">
//run this onload
if($("geneFileDD").options.length<1)	{
	try	{
		$("geneOptionGeneList").checked = $("geneOptionGeneList").selected = false;
		$("geneOptionGeneList").disabled = true;
		$("geneFileDD").disabled = true;
	}
	catch(e){}
}

function submitAllGenesQuery(){
	//if(document.forms[0].multiUseButton.value!="AllGenes")	{
	document.forms[0].geneList.value = "";
	//document.forms[0].geneType.disabled = true;
		document.forms[0].multiUseButton.value="AllGenes";
		document.forms[0].multiUseButton.click();
//	}
}
	    
function submitStandardQuery(){
	//if(document.forms[0].multiUseButton.value!="Standard")	{
	//document.forms[0].geneList.disabled = true;
	document.forms[0].geneOption1.disabled = false;
	//document.forms[0].multiUseButton.value="Standard";
	//document.forms[0].multiUseButton.click();

	document.forms[0].action="submitStandard.action";
	document.forms[0].submit();
		
	//}
}
function checkStandardOption()	{
	document.forms[0].geneOption1.checked = true;
}

</script>	