<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%
	String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>" > -->
<fieldset class="gray">
<legend class="red">SNP Id
<app:help help="Choose one type of SNP identifiers (dbSNP ID or Affymetrix SNP Probeset ID) from the pick list. Then enter the corresponding comma delimited IDs for SNPs to be searched in the text box. Another option to load a list of genes is to upload a tab-delimited file containing SNP ID list by click the browse button."/>
</legend>
<b class="message">(Paste comma separated SNP list, or upload file using Browse button)</b>
<br /><br />


 <html:select property="snpList" disabled="false">
   <html:optionsCollection property="snpTypes" />
    </html:select>



&nbsp;

<html:radio property="snpId" value="specify" styleClass="radio" onfocus="javascript:onRadio(this,0);" />

<html:text property="snpListSpecify" disabled="false" onfocus="javascript:radioFold(this);" value=""/>
&nbsp;&nbsp;
<html:radio property="snpId" value="upload" styleClass="radio" onfocus="javascript:onRadio(this,1);" />
<html:file property="snpListFile" disabled="true" /><br /><br />
&nbsp;&nbsp;Validated SNPs:&nbsp;&nbsp;

<html:radio property="validatedSNP" value="all"  disabled="true" styleClass="radio"/>&nbsp;&nbsp;All
<html:radio property="validatedSNP" value="excluded"  disabled="true" styleClass="radio"/>&nbsp;&nbsp;Excluded
<html:radio property="validatedSNP" value="included" disabled="true" styleClass="radio"/>&nbsp;&nbsp;Included
<html:radio property="validatedSNP" value="only" disabled="true" styleClass="radio"/>&nbsp;&nbsp;Only
<br>
<html:errors property="snpId"/>
</fieldset>
<!-- </html:form> -->
