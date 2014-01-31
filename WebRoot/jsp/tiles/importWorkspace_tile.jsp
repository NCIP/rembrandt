<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<link rel="stylesheet" type="text/css" href="components/treeManager/css/tree.css" />
<script type="text/javascript" src="components/treeManager/Tree-optimized.js"></script>
<script type='text/javascript' src='dwr/interface/WorkspaceHelper.js'></script>
<script type="text/javascript" src="components/treeManager/treeFactory.js"></script>
<script type="text/javascript">
Event.observe(window, 'load', function()	{
	TreeUtils.initializeTreeForImport();
	TreeUtils.initializeQueryTreeForImport();
});


</script>

<br clear="both"/>
<fieldset id="organizeFS">
<legend>Import Workspace:</legend>
<app:cshelp topic="import_list_help" /><br clear="left"/>

<table border="0" cellpadding="10" cellspacing="3">
	<tbody>
		<tr>
			<td>
				Import your Queries and Lists by selecting an XML export file
				from your local hard-drive. 
				<br />
				<div id="oQueryTree"></div>
				<br /><br clear="all" /><br />
				<div id="oListTree"></div>
			</td>
		</tr>
		<tr>
			<td>
				<s:form action="importWorkspaceFile" enctype="multipart/form-data" method="post" theme="simple">
					<s:fielderror fieldName="Import File" />
					<table>
						<tr>
							<td>
							<label for="fileType">File Type :</label> 
							<s:select id="fileType" name="form.fileType" list="form.fileTypeList" theme="simple"/>
							
							</td>
							<td style="padding-left:10px;">
								<s:file id="workspaceFile" name="workspaceFile" />
								<label for="workspaceFile">&nbsp;</label> 
							</td>
						</tr>
						<tr>
							<td  style="padding-top:10px;">
								<s:submit styleClass="xbutton" value="Import" />
							</td>
						</tr>
					</table>			
				</s:form>			
			</td>
		</tr>	
	</tbody>
</table>
</fieldset>