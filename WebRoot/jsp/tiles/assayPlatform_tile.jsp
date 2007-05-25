<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act");
%>



<fieldset class="gray">
<legend class="red">Assay Platform
<a href="javascript: Help.popHelp('<%=act%>_Array_tooltip');">[?]</a>    
<!-- <app:help help="Indicate the platform that was used for the comparative genomic study."/>-->
</legend><br />
&nbsp;&nbsp;<html:select property="assayPlatform" onchange="">
<!--	<option>&nbsp;</option> -->
    <option value="Affymetrix 100K SNP Arrays">100K SNP Array</option>
	<!--<option>Array CGH </option> -->
	<!--  <option>All</option> -->
</html:select>
<html:errors property="assayPlatform"/>
</fieldset>

