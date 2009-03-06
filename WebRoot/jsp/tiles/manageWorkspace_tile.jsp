
<link rel="stylesheet" type="text/css" href="components/treeManager/css/tree.css" />
<script type="text/javascript" src="components/treeManager/Tree-optimized.js"></script>
<script type='text/javascript' src='dwr/interface/WorkspaceHelper.js'></script>
<script type="text/javascript" src="components/treeManager/treeFactory.js"></script>
<script type="text/javascript">
Event.observe(window, 'load', function()	{
	TreeUtils.initializeTree();
});


</script>

<br clear="both"/>
<fieldset id="organizeFS">
<legend>Organize Workspace:</legend>
<table border="0" cellpadding="10" cellspacing="3">
	<tbody>
		<tr>
			<td>
				Organize your Queries and Lists by dragging items and creating
				folders. Your current lists and queries will be saved for future
				use. Click save to save all your organized lists and queries so that
				they are available to you when you return to the application at a
				later date.
				<br />
				<!--  
				<div id="oQueryTree"></div>
				<a href="#" onclick="TreeUtils.newFolder(oQueryTree);return false;">
					<img src="images/folder_add.png" border="0" /> new folder</a><br />
				<br clear="all" />
				-->
				<div id="oListTree"></div>
				<a href="#" onclick="TreeUtils.newFolder(oListTree);return false;">
					<img src="components/treeManager/images/folder_add.png" border="0" /> new folder</a><br />
				<br />
				<div align="center">
					<button onclick="TreeUtils.saveTreeStructs();">Save</button>
				</div>
			</td>
		</tr>
	</tbody>
</table>
</fieldset>