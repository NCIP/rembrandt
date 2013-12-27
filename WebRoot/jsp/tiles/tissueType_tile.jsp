<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.SpecimenType"%>
<%
	String act = request.getParameter("act") + "_specimenType_tooltip";
%>



<fieldset class="gray">
<legend class="red"><label for="specimenType">Specimen Type</label>
<app:cshelp topic="<%=act%>" text="[?]"/>  

<!-- <app:help help="Indicate the tissue type that was used for the comparative genomic study."/>-->
</legend><br />
&nbsp;&nbsp;<html:select styleId="specimenType" property="specimenType" onchange="">
	<html:option value="">&nbsp;</html:option>
    <html:option value="<%=SpecimenType.BLOOD.toString()%>"><%=SpecimenType.BLOOD.toString()%></html:option>
    <html:option value="<%=SpecimenType.TISSUE_BRAIN.toString()%>"><%=SpecimenType.TISSUE_BRAIN.toString()%></html:option>
</html:select>
<html:errors property="specimenType"/>
</fieldset>

