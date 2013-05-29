<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<link rel="stylesheet" type="text/css" href="components/treeManager/css/tree.css" />
<script type="text/javascript" src="components/treeManager/Tree-optimized.js"></script>
<script type='text/javascript' src='dwr/interface/WorkspaceHelper.js'></script>
<script type="text/javascript" src="components/treeManager/treeFactory.js"></script>
<script type="text/javascript">
Event.observe(window, 'load', function()	{
	TreeUtils.initializeTreeForExport();
	TreeUtils.initializeQueryTreeForExport();
});


</script>

<br clear="both"/>
<fieldset id="organizeFS">
<legend>Export Workspace:</legend>
<app:cshelp topic="export_list_help" /><br clear="left"/>

<table border="0" cellpadding="10" cellspacing="3">
	<tbody>
		<tr>
			<td>
				Export your Queries and Lists by clicking on either the folder or 
				the file and save them as XML files in your local hard-drive. 
				<br />
				<div id="oQueryTree"></div>
				<br /><br clear="all" /><br />
				<div id="oListTree"></div>
			</td>
		</tr>
	</tbody>
</table>
</fieldset>