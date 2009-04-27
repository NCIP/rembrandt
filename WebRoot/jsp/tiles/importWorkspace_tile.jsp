<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<link rel="stylesheet" type="text/css" href="components/treeManager/css/tree.css" />
<script type="text/javascript" src="components/treeManager/Tree-optimized.js"></script>
<script type='text/javascript' src='dwr/interface/WorkspaceHelper.js'></script>
<script type="text/javascript" src="components/treeManager/treeFactory.js"></script>
<script type="text/javascript">
Event.observe(window, 'load', function()	{
	TreeUtils.initializeTreeForImport();
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
				<div id="oListTree"></div>
			</td>
		</tr>
		<tr>
			<td>
				<html:form action="importWorkspaceFile.do" enctype="multipart/form-data">
					<html:errors/>
					<html:file property="workspaceFile" /> <br /><br />
					<html:submit styleClass="xbutton" value="Import" />
				</html:form>			
			</td>
		</tr>	
	</tbody>
</table>
</fieldset>