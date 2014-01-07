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

<s:checkbox id="caucasion" class="radio" name="clinicalDataForm.caucasion" value="Specify" />
<label for="caucasion"><%= RaceType.WHITE.toString()%></label><!-- Caucasion -->
<s:checkbox id="africanAmerican" class="radio" name="clinicalDataForm.africanAmerican" value="Specify" />
<label for="africanAmerican"><%= RaceType.BLACK_OR_AFRICAN_AMERICAN.toString()%></label> <!-- African American -->
<s:checkbox id="nativeHawaiian" class="radio" name="clinicalDataForm.nativeHawaiian"  value="Specify" />
<label for="nativeHawaiian"><%=RaceType.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER.toString()%></label> <!--  Native Hawaiian -->
<br/>
<s:checkbox id="asian" class="radio" name="clinicalDataForm.asian" value="Specify" />
<label for="asian"><%=RaceType.ASIAN.toString()%></label> <!--  Asian -->
<s:checkbox id="other" class="radio" name="clinicalDataForm.other" value="Specify" />
<label for="other"><%=RaceType.OTHER.toString()%></label> <!--  Other -->
<s:checkbox id="unknown" class="radio" name="clinicalDataForm.unknown" value="Specify"  />
<label for="unknown"><%=RaceType.UNKNOWN.toString()%></label> <!--  Unknown -->
&nbsp;&nbsp;
	

</fieldset>