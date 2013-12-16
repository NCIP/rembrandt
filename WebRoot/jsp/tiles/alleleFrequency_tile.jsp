<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
String act = request.getParameter("act") + "_Allele_tooltip";
%>
	
<fieldset class="gray">
<legend class="red">Allele Frequency
<app:cshelp topic="<%=act%>" text="[?]"/>   
</legend><br />
&nbsp;&nbsp;Population Type:

 <html:select property="alleleFrequency" disabled="true">
   <html:optionsCollection property="alleleTypes" />
    </html:select><br />
	

<html:errors property="alleleFrequency"/>
</fieldset>


