<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>

<html>
<head><title>REMBRANDT - Repository for Molecular Brain Neoplasia Data</title>
<%@ include file="/jsp/tiles/htmlHead_tile.jsp" %>
</head>

<body>
<div style="padding:10px;width:320;height:180;background:#FFFAE1;border:1px dashed red;">
<h4 class="red">Upload Gene Set</h4>
<html:form action="uploadGeneSet.do" enctype="multipart/form-data">
&nbsp;&nbsp;<html:select property="geneType" disabled="false">
			<html:optionsCollection property="geneTypeColl" />
		  </html:select><br />
			
			
			Name the sample set that has been uploaded<br />
			<html:text property="geneSetName" size="35"/><br /><br /><br />
			<html:file property="geneSetFile" /> <br /><br />
			<span align="right">
			<html:reset styleClass="xbutton" value="clear"/>
			<html:submit styleClass="xbutton" value="add this gene set" />
			</span>

</html:form>

<center><a href="#" onclick="window.close(); return false;">Close Window</a></center>
</body>
</html>