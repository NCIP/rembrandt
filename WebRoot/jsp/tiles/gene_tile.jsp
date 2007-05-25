<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<% 
/*
 * Gene Tile - used in: GeneExpression form, CGH form
 */

String act = request.getParameter("act");
%>
	
<fieldset class="gray">
<legend class="red">Gene
    <a href="javascript: Help.popHelp('<%=act%>_Gene_tooltip');">[?]</a>    
	<!-- <app:help help="Search on a gene identifier, a gene list, or All Genes. " />-->
</legend>

<br/>
<html:radio property="geneOption" styleClass="radio" value="standard" onclick="submitStandardQuery();"/>
  
	Type Genes:&nbsp;&nbsp;
	<html:select property="geneType" disabled="false">
		<html:optionsCollection property="geneTypeColl" />
	</html:select>
 	
			<html:text property="geneList" disabled="false" onfocus="" onblur="" />
			<br/><br/>
			
			<html:radio property="geneOption" styleClass="radio" value="geneList" onclick="submitStandardQuery();" styleId="geneOptionGeneList"/>
		
			 Choose a saved Gene List:&nbsp;&nbsp;
			<html:select property="geneFile" disabled="false" styleId="geneFileDD">
				<html:optionsCollection property="savedGeneList" />
			</html:select>
			<br/>
		   		
			<html:errors property="geneFile"/>
			<html:errors property="geneGroup"/>
			<html:errors property="geneList"/>
			<html:errors property="geneType"/>
	
	<!-- 	
	<logic:present name="comparitivegenomicForm">
		<logic:equal name="comparitivegenomicForm" property="geneOption" scope="request" value="standard">
			<p style="margin-left:30px">
			<html:radio property="geneGroup" value="Specify" styleClass="radio" onfocus="javascript:onRadio(this,0);"/>
			<html:text property="geneList" disabled="false" onfocus="javascript:radioFold(this);" onblur="javascript:cRadio(this, document.forms[0].geneGroup[0]);" />
			&nbsp;-or-&nbsp;
			<html:radio property="geneGroup" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);"/>
			<html:file property="geneFile" disabled="true"  onblur="javascript:cRadio(this, document.forms[0].geneGroup[1]);" onfocus="javascript:document.forms[0].geneGroup[1].checked = true;" />
			<br/>
			</p>		
			<html:errors property="geneFile"/>
			<html:errors property="geneGroup"/>
			<html:errors property="geneList"/>
			<html:errors property="geneType"/>
		</logic:equal>
	</logic:present>
-->
<br/>
<html:radio property="geneOption" styleClass="radio" value="allGenes" onclick="submitAllGenesQuery();" />All Genes Query
		
</fieldset>		
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
		document.forms[0].multiUseButton.value="AllGenes";
		document.forms[0].multiUseButton.click();
//	}
}
	    
function submitStandardQuery(){
	//if(document.forms[0].multiUseButton.value!="Standard")	{
		document.forms[0].multiUseButton.value="Standard";
		document.forms[0].multiUseButton.click();
	//}
}
</script>	