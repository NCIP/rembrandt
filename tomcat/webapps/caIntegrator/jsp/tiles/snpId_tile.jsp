<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" > -->

<div class="title">SNP Id</div>
<b class="message">(Paste comma separated SNP list, or upload file using Browse button)</b>
<br>



<html:radio property="snpId" value="specify" styleClass="radio" />
 <html:select property="snpList">
   <html:optionsCollection property="snpTypes" />
    </html:select>

<!--- <html:select property="snpList">
	<option>&nbsp;</option>
	<option>TSC Id</option>
	<option>dBSNP Id</option>
	<option>Probe Set Id</option>
</html:select> --->

&nbsp;
<html:text property="snpListSpecify"/>
&nbsp;&nbsp;
<html:radio property="snpId" value="upload" styleClass="radio" />
<html:file property="snpListFile"/><br>
Validated SNPs:&nbsp;&nbsp;

<html:radio property="validatedSNP" value="all"  styleClass="radio"/>&nbsp;&nbsp;All
<html:radio property="validatedSNP" value="excluded"  styleClass="radio"/>&nbsp;&nbsp;Excluded
<html:radio property="validatedSNP" value="included"  styleClass="radio"/>&nbsp;&nbsp;Included
<html:radio property="validatedSNP" value="only"  styleClass="radio"/>&nbsp;&nbsp;Only
<br>
<html:errors property="snpId"/>

<!-- </html:form> -->
