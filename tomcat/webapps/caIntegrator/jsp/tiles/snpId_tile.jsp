<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" > -->
<fieldset class="gray">
<legend class="red">SNP Id</legend>
<b class="message">(Paste comma separated SNP list, or upload file using Browse button)</b>
<br /><br />



<html:radio property="snpId" value="specify" styleClass="radio" onfocus="javascript:onRadio(this,0);" />
 <html:select property="snpList" onchange="javascript:depChange(this);">
   <html:optionsCollection property="snpTypes" />
    </html:select>

<!--- <html:select property="snpList">
	<option>&nbsp;</option>
	<option>TSC Id</option>
	<option>dBSNP Id</option>
	<option>Probe Set Id</option>
</html:select> --->

&nbsp;
<html:text property="snpListSpecify" onfocus="javascript:radioFold(this);" />
&nbsp;&nbsp;
<html:radio property="snpId" value="upload" styleClass="radio" onfocus="javascript:onRadio(this,1);" />
<html:file property="snpListFile"/><br /><br />
&nbsp;&nbsp;Validated SNPs:&nbsp;&nbsp;

<html:radio property="validatedSNP" value="all"  disabled="true" styleClass="radio"/>&nbsp;&nbsp;All
<html:radio property="validatedSNP" value="excluded"  disabled="true" styleClass="radio"/>&nbsp;&nbsp;Excluded
<html:radio property="validatedSNP" value="included" disabled="true" styleClass="radio"/>&nbsp;&nbsp;Included
<html:radio property="validatedSNP" value="only" disabled="true" styleClass="radio"/>&nbsp;&nbsp;Only
<br>
<html:errors property="snpId"/>
</fieldset>
<!-- </html:form> -->
