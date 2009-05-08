<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

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
				<html:form action="importWorkspaceFile.do" enctype="multipart/form-data">
					<html:errors/>
					<table>
						<tr>
							<td>
								File Type : 
								<html:select property="fileType">
									<html:option value="1">Query</html:option>
									<html:option value="2">List</html:option>
								</html:select>	
							</td>
							<td style="padding-left:10px;">
								<html:file property="workspaceFile" /> 
							</td>
						</tr>
						<tr>
							<td  style="padding-top:10px;">
								<html:submit styleClass="xbutton" value="Import" />
							</td>
						</tr>
					</table>			
				</html:form>			
			</td>
		</tr>	
	</tbody>
</table>
</fieldset>