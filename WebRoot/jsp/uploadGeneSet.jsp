<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>

<html>
<head><title>Upload GeneSet</title>
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
</head>

<body>
<div style="padding:10px;width:320;height:180;background:#FFFAE1;border:1px dashed red;">
<h4 class="red">Upload Gene Set</h4>
<html:form action="uploadGeneSet.action" enctype="multipart/form-data">
&nbsp;&nbsp;<html:select property="geneType" disabled="false">
			<html:optionsCollection property="geneTypeColl" />
		  </html:select>
			<html:file property="geneSetFile" /> <br /><br />
			&nbsp;&nbsp;Name the gene set<br />
			&nbsp;&nbsp;<html:text property="geneSetName" size="35"/><br /><br /><br />
			<html:errors property="geneGroup"/>
			
			<span align="right">&nbsp;&nbsp;
			<html:reset styleClass="xbutton" value="clear"/>
			<html:submit styleClass="xbutton" value="add this gene set" />
			</span>

</html:form>
</div>
<center><a href="#" onclick="window.close(); return false;">Close Window</a></center>
</body>
</html>