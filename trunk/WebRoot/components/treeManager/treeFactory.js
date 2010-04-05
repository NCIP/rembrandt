//Dependencies:  prototype.js, Tree-optimized.js (tafeltree), tree.css and related images

var oListTree = '';
var oQueryTree = '';
var needToConfirm = false; 
var TreeFactory = Class.create({
  initialize: function()  {
    //store the trees as name->JSON pair
    this.treeArray = new Hash();
  },
  generateTree: function(o)  {
      /*
        accepts object of options o as: {k:v, k:v}
        treeName (string unique name, must match div id of container)
        struct  (JSON TafelTree Structure)
        dnd (bool for drag-n-drop)
        showTrash (bool)
        showCheckbox (bool)
        isEditable (bool, only folders are editable - not root and not trash)
      */
      //returns a Tree this is typically called TafelTreeInit() or once the page has loaded
      // div id=treeName must exist, otherwise we could inject it, but where?
      
      var opts = {
      'generate' : true,
      'imgBase' : 'components/treeManager/imgs/',
      'width' : '100%', // default value : 100%
      'openAtLoad' : true,
      'cookies' : false,
      'checkboxesThreeState' : o.showCheckBox,
      'defaultImg' : 'page.gif',
      'defaultImgOpen' : 'folderopen.gif',
      'defaultImgClose' : 'folder.gif'
      };
      if(o.dnd) {
        opts.onDrop = function(m,d,f,c)  { return true;};
      }
      if(o.isEditable) {
        opts.onEdit = TreeUtils.editFolderName;
      }

	  if(o.isExport) {	
      	opts.onClick = TreeUtils.exportFile;
	  	opts.onMouseOver = TreeUtils.addCursorHand;
      }
      
      //console.log(o);
      
      var tree = new TafelTree(o.treeName, o.struct, opts);
      
      if(o.showTrash === false)  {
          tree.removeBranch('trash');
      }
      
      this.treeArray.set(o.treeName, tree);
      return tree;
  }


});


/*
Usage:
TafelTreeInit() {
  //most likely here we will fetch the tree structs from the session/db via AJAX
  var tf = new TreeFactory();
  var myTree = tf.generateTree({'treeName': 'exportListTree', 'struct': struct, 'dnd': false, 'showTrash':false, 'showCheckBox':true, 'isEditable':false});
}
*/

var TreeUtils = {
  copyStruct : function(structToCopy) {
    //deep copy via slow serialization
    var tmp = ""; var struct = null; 
    tmp = Object.toJSON(structToCopy); 
    struct = tmp.evalJSON();
    return struct;
  },
  editFolderName: function(b, n, o)  {
    //clean the names here
    return n;
  },
  exportFile: function(branch)  {
  	var root = branch.getAncestor();
    window.location.replace( "/rembrandt/exportWorkspaceFile.do?node_name=" + branch.getText() + "&node_type=" + root.getText() );
  },
  addCursorHand: function(branch)  {
    branch.addClass("tafelTreedrag");
  },
  newFolder: function(t) {     
    //make sure trash stays at bottom, assuming it starts at bottom
    var br = t.getBranchById("root");
    var lbr = br.getLastBranch();
    var nb = lbr.insertBefore({"id":"newFolder","img":"folder.gif","txt": "New Folder","onedit": TreeUtils.editFolderName});
  },
  initializeListTree : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchListTreeStructures(TreeUtils.initializeListTree_cb);
  },
  initializeListTree_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oListStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oListTree = tf.generateTree({'treeName': 'oListTree', 'struct': oListStruct, 'dnd': true, 'showTrash':true, 'showcheckBox':false, 'isEditable':true, 'isExport':false });
 
  }, 
  initializeQueryTree : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchQueryTreeStructures(TreeUtils.initializeQueryTree_cb);
  },
  initializeQueryTree_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oQueryStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oQueryTree = tf.generateTree({'treeName': 'oQueryTree', 'struct': oQueryStruct, 'dnd': true, 'showTrash':true, 'showcheckBox':false, 'isEditable':true, 'isExport':false });
 
  }, 
  initializeQueryTreeForExport : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchQueryTreeStructures(TreeUtils.initializeQueryTreeForExport_cb);
  },
  initializeQueryTreeForExport_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oQueryStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oQueryTree = tf.generateTree({'treeName': 'oQueryTree', 'struct': oQueryStruct, 'dnd': false, 'showTrash':false, 'showcheckBox':false, 'isEditable':false, 'isExport':true });
 
  }, 
  initializeTreeForExport : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchListTreeStructures(TreeUtils.initializeTreeForExport_cb);
  },
  initializeTreeForExport_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oListStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oListTree = tf.generateTree({'treeName': 'oListTree', 'struct': oListStruct, 'dnd': false, 'showTrash':false, 'showcheckBox':false, 'isEditable':false, 'isExport':true });
  }, 
  initializeTreeForImport : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchListTreeStructures(TreeUtils.initializeTreeForImport_cb);
  },
  initializeTreeForImport_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oListStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oListTree = tf.generateTree({'treeName': 'oListTree', 'struct': oListStruct, 'dnd': false, 'showTrash':false, 'showcheckBox':false, 'isEditable':false, 'isExport':false });
  },
  initializeQueryTreeForImport : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchQueryTreeStructures(TreeUtils.initializeQueryTreeForExport_cb);
  },
  initializeQueryTreeForImport_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oQueryStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oQueryTree = tf.generateTree({'treeName': 'oQueryTree', 'struct': oQueryStruct, 'dnd': false, 'showTrash':false, 'showcheckBox':false, 'isEditable':false, 'isExport':false });
 
  }, 
  saveTreeStructs : function()	{
  	//grab the local copies and persist
  	var listTreeString = oListStruct.toJSON();
  	var queryTreeString = oQueryStruct.toJSON();
  	WorkspaceHelper.saveTreeStructures(listTreeString, queryTreeString, TreeUtils.saveTreeStructs_cb);
  },
  saveTreeStructs_cb : function(r)	{
  	if(r == "pass")	{
  		alert("Save Successful");
  		$('oListTree').innerHTML='';
  		$('oQueryTree').innerHTML='';
  		TreeUtils.initializeListTree();
  		TreeUtils.initializeQueryTree();
  		SidebarHelper.loadSidebar();
  	}else {
  		alert("Save successful within the current session only because you are logged in as a guest user (RBTuser).");
  		$('oListTree').innerHTML='';
  		$('oQueryTree').innerHTML='';
  		TreeUtils.initializeListTree();
  		TreeUtils.initializeQueryTree();
  		SidebarHelper.loadSidebar();
  	}
  },
  checkTreeStructs : function()	{
  	//grab the local copies 
  	var listTreeString = oListStruct.toJSON();
  	var queryTreeString = oQueryStruct.toJSON();
  	WorkspaceHelper.checkTreeStructures(listTreeString, queryTreeString, { 
        async: false,
        callback: TreeUtils.checkTreeStructs_cb } );
  },
  checkTreeStructs_cb : function(r)	{
  	if ( r == "true" ) {
  		needToConfirm = true;
  		}
  	else
  		needToConfirm = false;
  }
}