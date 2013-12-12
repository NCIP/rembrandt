<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Array_tooltip";
%>



<fieldset class="gray">
<legend class="red"><label for="assayPlatform">Assay Platform</label>
<app:cshelp topic="<%=act%>" text="[?]"/>
<!-- <app:help help="Indicate the platform that was used for the comparative genomic study."/>-->
</legend><br />
&nbsp;&nbsp;<html:select styleId="assayPlatform" property="assayPlatform" onchange="">
<!--	<option>&nbsp;</option> -->
    <option value="Affymetrix 100K SNP Arrays">100K SNP Array</option>
	<!--<option>Array CGH </option> -->
	<!--  <option>All</option> -->
</html:select>
<html:errors property="assayPlatform"/>
</fieldset>

