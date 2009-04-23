//Dependencies:  prototype.js, Tree-optimized.js (tafeltree), tree.css and related images


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
      }
      if(o.dnd) {
        opts.onDrop = function(m,d,f,c)  { return true;};
      }
      if(o.isEditable) {
        opts.onEdit = TreeUtils.editFolderName;
      }

	  if(o.isExport) {	
      	opts.onClick = TreeUtils.exportFile;
      }

      
      //console.log(o);
      
      tree = new TafelTree(o.treeName, o.struct, opts);
      
      if(o.showTrash == false)  {
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
    window.location.replace( WorkspaceHelper.getWorkspaceListDownloadUrl() + "?listId=" + branch.getText() );
  },
  newFolder: function(t) {     
    //make sure trash stays at bottom, assuming it starts at bottom
    var br = t.getBranchById("root");
    var lbr = br.getLastBranch();
    var nb = lbr.insertBefore({"id":"newFolder","img":"folder.gif","txt": "New Folder","onedit": TreeUtils.editFolderName});
  },
  initializeTree : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchTreeStructures(TreeUtils.initializeTree_cb);
  },
  initializeTree_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oListStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oListTree = tf.generateTree({'treeName': 'oListTree', 'struct': oListStruct, 'dnd': true, 'showTrash':true, 'showcheckBox':false, 'isEditable':true, 'isExport':false });
  	
  }, 
  initializeTreeForExport : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchTreeStructures(TreeUtils.initializeTreeForExport_cb);
  },
  initializeTreeForExport_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oListStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oListTree = tf.generateTree({'treeName': 'oListTree', 'struct': oListStruct, 'dnd': true, 'showTrash':false, 'showcheckBox':false, 'isEditable':false, 'isExport':true });
  }, 
  initializeTreeForImport : function()	{
  	//make an ajax call to get the JSON, returned as a Hash of JSON objects (should be 2)
  	//call callback
  	WorkspaceHelper.fetchTreeStructures(TreeUtils.initializeTreeForImport_cb);
  },
  initializeTreeForImport_cb : function(r)	{
  	//r = string hash of JSON objects, convert the string to JSON obj w/ prototype
  	//convention keys are : oListStruct, eListStruct
  	var rJSON = r.evalJSON();
  	var tf = new TreeFactory();
  	//required: div container w/ id=treeName defined per TafelTree API
  	oListStruct = rJSON; //.oListStruct; //local copy, will be updated when the tree is manipulated, then persisted
  	oListTree = tf.generateTree({'treeName': 'oListTree', 'struct': oListStruct, 'dnd': true, 'showTrash':false, 'showcheckBox':false, 'isEditable':false, 'isExport':false });
  }, 
  saveTreeStructs : function()	{
  	//grab the local copies and persist
  	var treeString = oListStruct.toJSON();
  	WorkspaceHelper.saveTreeStructures(treeString, TreeUtils.saveTreeStructs_cb);
  },
  saveTreeStructs_cb : function(r)	{
  	if(r == "pass")	{
  		alert("Save Successful");
  		//should rebuild the tree for fun
  		//TreeUtils.initializeTree();
  	}
  }

}