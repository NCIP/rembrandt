<%@ page import="gov.nih.nci.rembrandt.web.ajax.WorkspaceHelper"%>
<link rel="stylesheet" type="text/css" href="components/treeManager/css/tree.css" />
<script type="text/javascript" src="components/treeManager/Tree-optimized.js"></script>
<script type='text/javascript' src='dwr/interface/WorkspaceHelper.js'></script>
<script type="text/javascript" src="components/treeManager/treeFactory.js"></script>
<script type="text/javascript">
Event.observe(window, 'load', function()	{
	TreeUtils.initializeListTree();
	TreeUtils.initializeQueryTree();
});


window.onbeforeunload = confirmExit;

function confirmExit()
{
	TreeUtils.checkTreeStructs();
	if ( needToConfirm )	
 		return ("There were changes made in 'Organize Workspace' or you have added/deleted new lists/queries that have not been saved.");
} 

<%
	boolean disableSAVE = false;
	if(WorkspaceHelper.isGuestUser(request.getSession())){
		disableSAVE = true;		
	}	
 %>
</script>

<br clear="both"/>
<fieldset id="organizeFS">
<legend>Organize Workspace:</legend>
			<script type="text/javascript">Help.insertHelp("organize_lists_help", " align='right'", "padding:2px;");</script><br clear="left"/>

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
				<div id="oQueryTree"></div>
				<a href="#" onclick="TreeUtils.newFolder(oQueryTree);return false;">
					<img src="components/treeManager/images/folder_add.png" border="0" /> new folder</a><br />
				<br clear="all" />
			
				<div id="oListTree"></div>
				<a href="#" onclick="TreeUtils.newFolder(oListTree);return false;">
					<img src="components/treeManager/images/folder_add.png" border="0" /> new folder</a><br />
				<br />
				<div align="center">
					 <!-- <button onclick="TreeUtils.saveTreeStructs();">Save</button>  -->  
					
					<%if(disableSAVE){ %>
	       				<button type="button" disabled="disabled">Save</button> 
						<p><i>Save is disabled as you are logged in as a guest user (RBTuser)</i></p>					
					<%}else { %>					
	       				<button onclick="TreeUtils.saveTreeStructs();">Save</button> 
					<%} %>
				</div>
			</td>
		</tr>
	</tbody>
</table>
</fieldset>