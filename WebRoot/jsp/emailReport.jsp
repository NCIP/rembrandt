<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld"  prefix="app" %>
<script language="javascript" src="js/lib/scriptaculous/scriptaculous.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<s:form action="emailReport.action">
<head><title>REMBRANDT - Repository for Molecular Brain Neoplasia Data</title>
<link rel="shortcut icon" href="images/favicon.ico" />
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
<script type='text/javascript' src='dwr/engine.js'></script>
<script language="javascript" src="js/caIntScript.js"></script>
</head>
<body>
<!--header NCI-->
<%@ include file="/jsp/header.jsp" %>
<!--end all headers-->
<%
String act = request.getParameter("act") + "_Allele_tooltip";
%>
	
<fieldset style="border: 1px solid #000066;width:700px">
<legend style="text-align:center;background-color:#ffffff">Notify Query Completion via Email
<app:cshelp topic="<%=act%>" text="[?]"/>   
</legend><br />
<div class="content">
<logic:empty name ="email">
<p align="left">Please provide your email address, if you would like to receive an email notification upon completion of this query</p>
</logic:empty>
<logic:notEmpty name ="email">
<p align="left">Please verify your email address, if you would like to receive an email notification upon completion of this query</p>
</logic:notEmpty>

	<p align="left">
		<bean:message key="label.email"/>
		<s:text name="email" />
	</p>
	<p align="left">
		<s:fielderror fieldName="email"/>
	</p>
	<p align="left">
		<bean:message key="label.email.notify"/>
	</p>
	<input type="button" class="xbutton" onclick="window.opener.location.reload(); self.close()" class="btn" value="Cancel"/>
	<!-- <html:submit onclick="self.close()" styleClass="btn"/> -->
	<s:submit id="submittalButton" class="subButton" onclick="window.opener.location.reload();" property="method" theme="simple">
		<bean:message key="buttons_tile.submittalButton" />
	</s:submit>
	</div>
	</fieldset>
	</body>
</s:form>
