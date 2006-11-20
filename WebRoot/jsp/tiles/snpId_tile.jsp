<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act");
%>
	
<fieldset class="gray">
<legend class="red">SNP Id
<app:help help="Enter comma-delimited IDs or select a saved list." />
</legend>
<!-- <b class="message">(Paste comma separated SNP list, or upload file using Browse button)</b>-->
<br /><br />

<html:radio property="snpId" value="specify" styleClass="radio" onfocus="javascript:onRadio(this,0);" />
Type SNP's:&nbsp;&nbsp;
<html:select property="snpList" disabled="false">
	<html:optionsCollection property="snpTypes" />
</html:select>
&nbsp;
<html:text property="snpListSpecify" disabled="false" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].snpId[0]);"/>
<br/>

<html:radio property="snpId" value="snpList" styleClass="radio" styleId="snpIdSnpList" onfocus="javascript:onRadio(this,1);" />
Choose s saved SNP list:&nbsp;&nbsp;
<html:select property="snpListFile" disabled="false" styleId="snpListFileDD">
	<html:optionsCollection property="savedSnpList" />
</html:select>
<!-- 
<html:file property="snpListFile" disabled="true" onblur="javascript:cRadio(this, document.forms[0].snpId[1]);" onfocus="javascript:document.forms[0].snpId[1].checked = true;" />
-->

<br/>
&nbsp;&nbsp;Validated SNPs:&nbsp;&nbsp;
<html:radio property="validatedSNP" value="all"  disabled="true" styleClass="radio"/>&nbsp;&nbsp;All
<html:radio property="validatedSNP" value="excluded"  disabled="true" styleClass="radio"/>&nbsp;&nbsp;Excluded
<html:radio property="validatedSNP" value="included" disabled="true" styleClass="radio"/>&nbsp;&nbsp;Included
<html:radio property="validatedSNP" value="only" disabled="true" styleClass="radio"/>&nbsp;&nbsp;Only
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
