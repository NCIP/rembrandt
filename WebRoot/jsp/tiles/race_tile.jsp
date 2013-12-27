<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.rembrandt.util.RaceType" %>
<%
	String act = request.getParameter("act") + "_Race_tooltip";
%>
<fieldset class="gray">
<legend class="red">Race
	<app:cshelp topic="<%=act%>" text="[?]"/>

</legend>

<html:checkbox styleId="caucasion" styleClass="radio" property="caucasion" value="Specify" />
<label for="caucasion"><%= RaceType.WHITE.toString()%></label><!-- Caucasion -->
<html:checkbox styleId="africanAmerican" styleClass="radio" property="africanAmerican" value="Specify" />
<label for="africanAmerican"><%= RaceType.BLACK_OR_AFRICAN_AMERICAN.toString()%></label> <!-- African American -->
<html:checkbox styleId="nativeHawaiian" styleClass="radio" property="nativeHawaiian"  value="Specify" />
<label for="nativeHawaiian"><%=RaceType.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER.toString()%></label> <!--  Native Hawaiian -->
<br/>
<html:checkbox styleId="asian" styleClass="radio" property="asian" value="Specify" />
<label for="asian"><%=RaceType.ASIAN.toString()%></label> <!--  Asian -->
<html:checkbox styleId="other" styleClass="radio" property="other" value="Specify" />
<label for="other"><%=RaceType.OTHER.toString()%></label> <!--  Other -->
<html:checkbox styleId="unknown" styleClass="radio" property="unknown" value="Specify"  />
<label for="unknown"><%=RaceType.UNKNOWN.toString()%></label> <!--  Unknown -->
&nbsp;&nbsp;
	

</fieldset>