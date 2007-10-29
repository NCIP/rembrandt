<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.TissueType"%>
<%
	String act = request.getParameter("act");
%>



<fieldset class="gray">
<legend class="red">Tissue Type
<a href="javascript: Help.popHelp('<%=act%>_TissueType_tooltip');">[?]</a>    
<!-- <app:help help="Indicate the tissue type that was used for the comparative genomic study."/>-->
</legend><br />
&nbsp;&nbsp;<html:select property="tissueType" onchange="">
	<html:option value="">&nbsp;</html:option>
    <html:option value="<%=TissueType.BLOOD.toString()%>">Blood</html:option>
    <html:option value="<%=TissueType.TISSUE.toString()%>">Tissue</html:option>
</html:select>
<html:errors property="tissueType"/>
</fieldset>

