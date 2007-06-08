<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<%
String act = request.getParameter("act");
%>


<fieldset class="gray">
<legend class="red">Genomic Annotation Track &nbsp;
<!-- <app:help help="Future implementation"/>-->
<a href="javascript: Help.popHelp('<%=act%>_GeoAnno_tooltip');">[?]</a>


</legend>
<br />
<html:text property="genomicTrack" disabled="true" />&nbsp;<input type="button" class="sbutton" value="Genomic Browser..." style="width:150px;" disabled="true">
<br /></fieldset>
