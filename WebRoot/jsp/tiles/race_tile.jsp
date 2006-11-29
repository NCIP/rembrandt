<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.rembrandt.util.RaceType" %>
<fieldset class="gray">
<legend class="red">Race</legend>
<%
	String act = request.getParameter("act");
%>
<html:checkbox styleClass="radio" property="caucasion" value="Specify" />
<%= RaceType.WHITE.toString()%><!-- Caucasion -->
<html:checkbox styleClass="radio" property="africanAmerican" value="Specify" />
<%= RaceType.BLACK_OR_AFRICAN_AMERICAN.toString()%> <!-- African American -->
<html:checkbox styleClass="radio" property="nativeHawaiian"  value="Specify" />
<%=RaceType.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER.toString()%> <!--  Native Hawaiian -->
<br/>
<html:checkbox styleClass="radio" property="asian" value="Specify" />
<%=RaceType.ASIAN.toString()%> <!--  Asian -->
<html:checkbox styleClass="radio" property="other" value="Specify" />
<%=RaceType.OTHER.toString()%> <!--  Other -->
<html:checkbox styleClass="radio" property="unknown" value="Specify"  />
<%=RaceType.UNKNOWN.toString()%> <!--  Unknown -->
&nbsp;&nbsp;
	

</fieldset>