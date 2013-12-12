<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_SNPID_tooltip";
%>
	
<fieldset class="gray">
<legend class="red">SNP Id
<!-- <app:help help="Enter comma-delimited IDs or select a saved list." />-->
<app:cshelp topic="<%=act%>" text="[?]"/> 

</legend>
<!-- <b class="message">(Paste comma separated SNP list, or upload file using Browse button)</b>-->
<br /><br />

<html:radio styleId="snpId" property="snpId" value="specify" styleClass="radio" onfocus="javascript:onRadio(this,0);" />
<label for="snpId">Type SNP's:&nbsp;&nbsp;</label>
<html:select styleId="snpList" property="snpList" disabled="false">
	<html:optionsCollection property="snpTypes" />
</html:select><label for="snpList">&nbsp;</label>
<html:text styleId="snpListSpecify" property="snpListSpecify" disabled="false" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].snpId[0]);"/><label for="snpListSpecify">&nbsp;</label>
<br/>

<html:radio property="snpId" value="snpList" styleClass="radio" styleId="snpIdSnpList" onfocus="javascript:onRadio(this,1);" />
<label for="snpIdSnpList">Choose a saved SNP list:&nbsp;&nbsp;</label>
<html:select property="snpListFile" disabled="false" styleId="snpListFileDD">
	<html:optionsCollection property="savedSnpList" />
</html:select><label for="snpListFileDD">&nbsp;</label>
<!-- 
<html:file property="snpListFile" disabled="true" onblur="javascript:cRadio(this, document.forms[0].snpId[1]);" onfocus="javascript:document.forms[0].snpId[1].checked = true;" />
-->

<br/>
&nbsp;&nbsp;Validated SNPs:&nbsp;&nbsp;
<html:radio styleId="validatedSNP1" property="validatedSNP" value="all"  disabled="true" styleClass="radio"/><label for="validatedSNP1">&nbsp;&nbsp;All</label>
<html:radio styleId="validatedSNP2" property="validatedSNP" value="excluded"  disabled="true" styleClass="radio"/><label for="validatedSNP2">&nbsp;&nbsp;Excluded</label>
<html:radio styleId="validatedSNP3" property="validatedSNP" value="included" disabled="true" styleClass="radio"/><label for="validatedSNP3">&nbsp;&nbsp;Included</label>
<html:radio styleId="validatedSNP4" property="validatedSNP" value="only" disabled="true" styleClass="radio"/><label for="validatedSNP4">&nbsp;&nbsp;Only</label>
<br/>
<html:errors property="snpId"/>
</fieldset>

<script type='text/javascript'>
if($("snpListFileDD").options.length<1)	{
	//alert('theres no gene lists to choose from');
	try	{
		$("snpIdSnpList").checked = $("snpIdSnpList").selected = false;
		$("snpIdSnpList").disabled = true;
		$("snpListFileDD").disabled = true;
	}
	catch(err){}
}

</script>
