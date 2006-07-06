<%@ page import="gov.nih.nci.caintegrator.application.lists.ListType,
				gov.nih.nci.caintegrator.application.lists.UserList,
				gov.nih.nci.caintegrator.application.lists.UserListBean,
				gov.nih.nci.caintegrator.application.lists.ListManager,
				gov.nih.nci.caintegrator.application.lists.UserListBeanHelper,
				org.apache.struts.upload.FormFile,
				java.io.File,
				java.util.Map,
				java.util.HashMap,
				java.util.List,org.
				dom4j.Document"%>				
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<script type='text/javascript' src='js/lib/scriptaculous/effects.js'></script>

<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>

<script type='text/javascript' src='js/lib/common/ManageListHelper.js'></script>
<script type='text/javascript' src='js/lib/common/TextFormList.js'></script>
<script type='text/javascript' src='js/lib/common/FormChanger.js'></script>
<script type='text/javascript' src='js/lib/common/StatusMessage.js'></script>

<script type="text/javascript">
	function handleResponse(msg)	{
		ManageListHelper.handleResponse(msg);
	}
	
	//http://simon.incutio.com/archive/2004/05/26/addLoadEvent
function addLoadEvent(func) {
	var oldonload = window.onload;
  	if (typeof window.onload != 'function') {
    	window.onload = func;
  	} 
  	else {
	    window.onload = function() {
	    	if (oldonload) {
	        	oldonload();
	      	}
			func();
	    }
 	}
}
</script>
<style>	
	.status {
		color:#A90101;
		font-weight:bold;
	}
	.groupList li	{
		margin-left:20px;
		list-type:none;
		list-style-type: none;
	}
</style>
<iframe id="RSIFrame" name="RSIFrame" style="width:0px; height:0px; border: 0px" src="blank.jsp"></iframe>

<span id="info">&nbsp;</span>

<div style="text-align:center">
<%
	ListType[] lts = ListType.values();
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<a href="#<%=label%>Lists"><%=label%> Lists</a> | 
<% } %>
	<a href="#addList">Add List</a>
</div>
<br/><br/>

<%
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<a name="<%=label%>Lists"></a>
	<fieldset class="groupList" id="<%=label%>ListsFS">
		<legend onclick="new Effect.toggle('<%=label%>Container')">
			<%=label%> Lists
		</legend>
		<div id="<%=label%>Container">
			<br/>
			<div id="<%=label%>ListDiv"></div>	
			
			<div id="<%=label%>UniteDiv" />
				New List Name:<input type="text" id="<%=label%>GroupName"/>
				<b><input type="button" onclick="ManageListHelper.groupSelectedLists('<%=label%>','<%=label%>ListsFS', $('<%=label%>GroupName').value,'join')" value="Join Selected"/></b>	
				<b><input type="button" onclick="ManageListHelper.groupSelectedLists('<%=label%>','<%=label%>ListsFS', $('<%=label%>GroupName').value,'intersect')" value="Intersect Selected"/></b>	
				<span class="status" id="<%=label%>GroupStatus"></span>
			</div>
			
		</div>
	</fieldset>
	<div style="text-align:right; margin:10px;">
		<a href="#" onclick="javascript:scroll(0,0);return false;">[top]</a>
	</div>
<%
	}
%>

<script type="text/javascript">
		if(!saf)	{
			addLoadEvent(ManageListHelper.generic_cb); 
		}
		else	{
			ManageListHelper.generic_cb("init");
		}
		
		//StatusMessage.showStatus("asdf");
</script>

<a name="addList"></a>
<fieldset class="listForm" id="listForm">
	<legend class="listLegend">
		<a onclick="FormChanger.type2upload();return false;" href="#">Upload List</a> -or- <a href="#" onclick="FormChanger.upload2type();return false;">Manually type List</a>
	</legend>
	<br/>
	<div id="uploadListDiv">
	<form id="uploadForm" method="post" action="upload.jsp" enctype="multipart/form-data" target="RSIFrame" onSubmit="return false;">
		<table border="0" cellspacing="2" cellpadding="2">
			<tr>
				<td>
					Choose the list type:
				</td>
				<td colspan="2">
					<select id="typeSelector" name="type">
<%
					for(int i=0; i<lts.length; i++)	{
						String label = lts[i].toString();			
%>
						<option value="<%=label%>"><%=label%></option>
<%
					}					
%>
					</select>
					<app:help help="There must only be one entry (id) per line." />
					
				</td>
			</tr>
			<tr id="uploadRow">
				<td>
					Upload file:
				</td>
				<td colspan="2">
					<input type="file" id="upload" name="upload" size="25">
				</td>
			</tr>
			<tr id="textRow" style="display:none">
				<td>
					Type Ids:<br/> (comma separated)
				</td>
				<td colspan="2">
					<textarea id="typeListIds" style="width:300px"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					Name list:
				</td>
				<td colspan="2">
					<input type="text" id="listName" name="listName" size="30">
					<input type="button" value="add list" onclick="ManageListHelper.validateForm()" id="uploadButton">
					<span id="uploadStatus" style="display:none"><img src="images/indicator.gif"/>&nbsp; saving...</span>
				</td>
			</tr>

		</table>
	</form>
	</div>
	
</fieldset>

<div style="text-align:right; margin:10px;">
<a href="#" onclick="javascript:scroll(0,0);return false;">[top]</a>
</div>
