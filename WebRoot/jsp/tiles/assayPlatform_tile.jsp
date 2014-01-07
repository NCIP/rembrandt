<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Array_tooltip";
%>



<fieldset class="gray">
<legend class="red"><label for="assayPlatform">Assay Platform</label>
<app:cshelp topic="<%=act%>" text="[?]"/>
<!-- <app:help help="Indicate the platform that was used for the comparative genomic study."/>-->
</legend><br />
&nbsp;&nbsp;
<s:select id="assayPlatform" name="comparativeGenomicForm.assayPlatform" list="comparativeGenomicForm.assayPlatforms" 
  listKey="key" listValue="value" onchange="">
</s:select>

<s:actionerror name="assayPlatform"/>
</fieldset>

