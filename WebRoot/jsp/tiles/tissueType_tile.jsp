<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.SpecimenType"%>
<%
	String act = request.getParameter("act");
%>



<fieldset class="gray">
<legend class="red">Specimen Type
<a href="javascript: Help.popHelp('<%=act%>_specimenType_tooltip');">[?]</a>   

<!-- <app:help help="Indicate the tissue type that was used for the comparative genomic study."/>-->
</legend><br />
&nbsp;&nbsp;<html:select property="specimenType" onchange="">
	<html:option value="">&nbsp;</html:option>
    <html:option value="<%=SpecimenType.BLOOD.toString()%>"><%=SpecimenType.BLOOD.toString()%></html:option>
    <html:option value="<%=SpecimenType.TISSUE_BRAIN.toString()%>"><%=SpecimenType.TISSUE_BRAIN.toString()%></html:option>
</html:select>
<html:errors property="specimenType"/>
</fieldset>

