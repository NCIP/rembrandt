<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<%
String act = request.getParameter("act") + "_GeoAnno_tooltip";
%>


<fieldset class="gray">
<legend class="red">Genomic Annotation Track &nbsp;
<!-- <app:help help="Future implementation"/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>


</legend>
<br />
<html:text property="genomicTrack" disabled="true" />&nbsp;<input type="button" class="sbutton" value="Genomic Browser..." style="width:150px;" disabled="true">
<br /></fieldset>
