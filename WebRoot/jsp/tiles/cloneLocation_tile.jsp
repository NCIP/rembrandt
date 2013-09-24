<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<%
	String act = request.getParameter("act") + "_Clone_tooltip";
%>
<legend class="red">Clone Location
	<!-- <app:help help="Future implementation"/>-->
    <app:cshelp topic="<%=act%>" text="[?]"/>

</legend>

	

5' UTR <input class="radio" type="radio" name="5p" selected checked disabled="true">Included&nbsp;&nbsp;
<input class="radio" type="radio" name="5p" disabled="true">Excluded<br>
3' UTR <input class="radio" type="radio" name="3p" selected checked disabled="true">Included&nbsp;&nbsp;
<input class="radio" type="radio" name="3p" disabled="true">Excluded<br>
</fieldset>
