/**
*	//this object is dependent on the DynamicListHelper, included in the sidebar tile
*	// src='dwr/interface/DynamicListHelper.js'
*	// also dependent on browserSniff.js, SidebarHelper.js, and prototype.js 1.5.x
*/
	var ManageListHelper = {
		'getGenericLists' : function(listType)	{
			DynamicListHelper.getGenericLists(listType, ManageListHelper.getGenericLists_cb);
		},
		'getAllLists' : function()	{
			//assumes browserSniff.js has already been included and declared the browser specific vars
			DynamicListHelper.getAllLists(ManageListHelper.getGenericLists_cb);
		},
		'getGenericLists_cb' : function(txt)	{
			//accepts a JSON object	<- now accepts a json array
			// String listType : patient | gene | defaultPatient		
			// Array<Object> listItems : { listName, listDate, itemCount, invalidItems }
			try	{
				var listContainerArray = eval('(' + txt + ')');
				for(var i=0; i<listContainerArray.length; i++)	{
					var listContainer = listContainerArray[i];
					var listType = listContainer.listType ? listContainer.listType : "none";
					if(listType == "none") {
						continue;
					}
					
					var lists = listContainer.listItems;
					
					
					//Note:  $('..'+"ListDiv") needs be be defined in the HTML src
					// ^ now, should be auto generated 
					
					if(lists.length == 0)	{
						if($(listType+'ListDiv')){
							$(listType+'ListDiv').innerHTML = "<b>No "+ listType + " lists currently saved</b><br/><br/>";
					    }
					    if($(listType+'UniteDiv')){
					    	$(listType+'UniteDiv').style.display = "none";
					    }
						continue;
					}
	
					if($(listType+'ListDiv'))
						$(listType+'ListDiv').innerHTML = "";  //clear it, and repopulate
					
					var tst = "";
					for(var t=0; t<lists.length; t++)	{
					
						var status = "<span id=\""+lists[t].listName+"status\" style=\"display:none\"><img src=\"images/indicator.gif\"/></span>";
						var shortName = lists[t].listName.length>25 ? lists[t].listName.substring(0,23) + "..." : lists[t].listName;
						var theName = lists[t].listName;
						var itemCount = lists[t].itemCount;
						var author = lists[t].author;
						var date = lists[t].listDate;
						var notes = lists[t].notes;
						var lib = "Author: " + lists[t].author + "<br />" +
								  "Date Created: " + lists[t].listDate 	+ "<br />" +
								  "Notes: <br />"  + lists[t].notes;
						
						var listSubTypes = (lists[t].listSubTypes && lists[t].listSubTypes.length > 0) ? lists[t].listSubTypes.join(",") : "";
						var lstyle = listSubTypes.indexOf(listContainer.highlightType)!= -1 ? "color:#000000;" : "";			
						// += or =
						tst +=  "<div id='"
		                	+ theName
		                    + "' class='dlistListing'>" 
		                    + "<input type='checkbox' style='border:0px;' id='' name='" + listType + "' value='" +theName+ "'/>"
		                    + "<b style='"+lstyle+"' onmouseover=\"overlib('" + lib +"', CAPTION, '"+ theName+ "');\" onmouseout='return nd();'>"
		                    + shortName + "</b><br/><div style='margin-left:10px'> Type: " + listSubTypes + " - "
		                    + "<span id='"+theName+"_count'>" + itemCount + "</span> item(s)" 
		                    + "<div style='cursor:pointer;margin-left:20px;width:200px;display:inline;' onclick='ManageListHelper.getDetails(\""
		                    + theName
		                    + "\");return false;'>"
		                    + "<img src='images/arrowPane20.png' border='0' style='vertical-align:text-bottom'/>details" + status + "</div>"
		                    + "<div style='cursor:pointer;margin-left:20px;width:200px;display:inline;'  onclick='ManageListHelper.deleteList(\""
		                    + theName
		                    + "\");return false;'>"
		                    + "<img src='images/deleteCross20.png' border='0' style='vertical-align:text-bottom;'/>delete</div>"
		                    + "</div><br /><div id='"
		                    + theName
		                    + "details'></div>\n</div>\n";    
					}
					if($(listType+'ListDiv'))
						$(listType+'ListDiv').innerHTML = tst;
				}
			}
			catch(err)	{
				//alert("ERR: " + err);
			}
			 
		},
		'groupSelectedLists' : function(groupType, listGroup, groupName, action)	{
			//params:  String listType, DOM where the selected boxes are, new list name, action to perform
			var sLists = Array();
			try	{
				if(groupName == "")	{
					alert("Please Enter a name for the new group");
					throw("no name error");
				}
				var ls = $(listGroup) ? $(listGroup).getElementsByTagName('input') : Array();
				for(var i=0; i<ls.length; i++)	{
					if(ls[i].type=='checkbox' && (ls[i].selected || ls[i].checked))
						sLists.push(ls[i].value);
				}
				
				if(sLists.length < 1)	{
					alert("Please select some lists to " + action);
					throw("no lists selected");
				}
				if(sLists.length!=2 && action == "difference")	{
					alert("Please select only 2 groups");
					throw("incorrect amount of lists");
				}
								
				DynamicListHelper.uniteLists(sLists, groupName, groupType, action, ManageListHelper.groupSelectedLists_cb );
			}
			catch(err) {} 
		},
		'groupSelectedLists_cb' : function(txt)	{
			//var res = txt.split(",");
			//accepts a comma sep string, should/could be json!
			//String results : pass | fail
			//String action
			//String groupType
			try	{
				var res = eval('(' + txt + ')');
				
				if(res.results.indexOf("pass")!=-1)	{
					$(res.groupType+'GroupName').value = "";
					
					var st = res.groupType+"GroupStatus";
					$(st).innerHTML = "Group Saved";
					setTimeout(function()	{ $(st).innerHTML = ""; }, 2000);
				}
				else {
					if(res.action == "join"){
						alert("List did not save, please try again.");
					}
					else{
						alert("The selected lists do not have any common entries.");
					}
				}
					
				ManageListHelper.getAllLists();
			   	SidebarHelper.loadSidebar();
			}
			catch(err)	{
				//alert(err);
			}
		},
		// ************* upload related **************** //
		//this function is called by upload.jsp sitting in an iframe
		//it is given an associative array (much like a paramMap) from
		// the validated list. The groupString variable(below) is inserted into the page
		// at the appropriate place.-KR...dont need the main argument anymore
		
		'handleResponse' : function(msg) { 
			//optional arguments: 1-upload form id, 2-upload status id
			try	{
				ManageListHelper.getAllLists();
			   	SidebarHelper.loadSidebar();
			}
			catch(err)	{}
		   
		   var uform = arguments.length > 1 && arguments[1]!= "" ? arguments[0] : "uploadForm";
		   var ustat = arguments.length > 2 ? arguments[2] : "uploadStatus";
			//reset the form
			Form.reset($(uform));
			//hide the indicator
			setTimeout(function(){$(ustat).style.display = "none";}, 500);

		},
		
		// this functions as a toggle for the details link next to a 
		// list. If details are currently viewable, it removes the element
		// from the DOM. If the "list name + detailsDiv" element doesn't
		// exist, the AJAX call is made to retrieve the most current details
		// from the userListBean sitting in the session. -KR
		// requires name+"detailsDiv" and name+"status" elements(ids) to be in the HTML
		'getDetails' : function(name){   

			if(document.getElementById(name+ "detailsDiv")==null){	    
				if($(name+"status"))
					$(name+"status").style.display = "";
		    	UserListHelper.getDetailsFromList(name,ManageListHelper.putDetailsIHTML);	    
		   	}
		   	else	{
		    	Element.remove(name+"detailsDiv");	     
		    }
		},
		 //function to make an AJAX call to the userListBean in the session
		 // and delete the list under the name it passes as a param. Then finds
		 // the div with an id matching this name and removes it from the DOM. -KR
		'deleteList' : function(name){
			if(confirm("Delete this List?"))
				UserListHelper.removeListFromAjax(name, ManageListHelper.generic_cb);
		},
		'generic_cb' : function(name)	{
			try	{
				SidebarHelper.loadSidebar();
			}
			catch(err)	{}			
			ManageListHelper.getAllLists()
		},
		//function to make an AJAX call to the userListBean in the session
		// and delete the list item under the list name it passes as params. Then finds
		// the div with an id matching this item name and removes it from the DOM. -KR
		'deleteItem' : function (name, itemId){
			UserListHelper.removeItemFromList(name,itemId);
			Element.remove(name + itemId + "_div");	
			try	{
				SidebarHelper.loadSidebar();
				//update the count
				if($(name+"_count"))	{
					var tmp = $(name+"_count").innerHTML;
					$(name+"_count").innerHTML = parseInt(tmp) - 1;
				}
			}
			catch(err){} 	
		},
		//this is the callback function from the getDetails AJAX function (above). This
		//function recieves a Document object back from the getDetails call and parses
		// the Document object. As it parses the Document (which is a UserList), it 
		// dynamically creates DOM elements for each item in a particular list and adds
		// them to the page. The dynamically created nodes are created/deleted depending
		// on when the user toggles the "details" link next to a link. -KR
		
		// modded to use IHTML and JSON- rcl
		'putDetailsIHTML' : function(userList){
			//JSON: String listName
			// String listType
			// Array validItems
			// Array invalidItems
		 	try	{
				var list = eval('(' + userList + ')');
				listName = list.listName;
				listType = list.listType;
				listType = ""; //clear this for now
				
				var items = list.validItems ? list.validItems : Array();
				var invalidItems = list.invalidItems ? list.invalidItems : Array();
				
								
				var itemId;
				var itemRank = "";
				var itemNotes = "";
		 		var value;
		 		var dDIV = document.createElement("div");
		 		dDIV.setAttribute("id",listName + "detailsDiv");
		 		dDIV.setAttribute("class", "listItemsDiv");
		 		
		 		//rcl - create and append our container
		 		 document.getElementById(listName + "details").appendChild(dDIV);
		 		//setup a handle to the working container
		 		var wDiv = $(listName + "detailsDiv");
		 		wDiv.style.borderLeft = "1px dashed red";
		 		wDiv.style.marginLeft = "20px";
				wDiv.style.width="300px";
		 		if(items.length > 0)	{
		 			var tmp = "";		 				 			
					for(var i=0; i<items.length; i++)	{
						itemId = items[i].name;
						itemRank = "";
						itemNotes = "";	
							if(items[i].rank!=null){
								itemRank = " rank: " + items[i].rank;
							}
							if(items[i].notes!=null){
								itemNotes = " notes: " + items[i].notes;
							}						
						tmp += "<li id='"+listName + itemId + "_div"+"' class='detailsList'>"+(i+1) +") " +listType + " " + itemId + itemRank + itemNotes;						
						var oc = new Function("deleteItem('"+listName+ "','" + itemId + "');return false;");
						tmp += "<a href=\"#\" onclick=\"ManageListHelper.deleteItem('"+listName+ "','" + itemId + "');return false;\">[delete]</a></li>";
					}  
					wDiv.innerHTML += tmp;
					     
					var eid = encodeURIComponent(listName);
					wDiv.innerHTML += "<div onclick=\"location.href='listExport.jsp?list="+eid+"';\" style='margin:20px;cursor:pointer; width:90px;height:20px'><img src='images/downArrow20.png'/><u>export list</u></div>";
				}
				else{
			    	document.getElementById(listName + "details").appendChild(dDIV);
			    	wDiv.innerHTML = "<span>No valid items found</span><br />";
				}
			     
			    if(!invalidItems.length < 1)	{
					var intmp = "<div style='margin-left:20px;'><span id='invalid_span' style='color:gray; padding:3px;'><br/>Invalid or does not exist in the database:<br/> ";
					//wDiv.innerHTML += "<span id='invalid_span' style='color:gray; padding:3px'><br/>Invalid or does not exist in the database:<br/> ";
					for(var i=0; i<invalidItems.length; i++){
						invalidItemId = invalidItems[i];
						if((i+1) == invalidItems.length){
							//wDiv.innerHTML += invalidItemId;
							intmp += invalidItemId;
						}
						else{
							intmp += invalidItemId + ", ";
							//wDiv.innerHTML += invalidItemId + ", ";							
						}
					}
					wDiv.innerHTML +=intmp;		
					wDiv.innerHTML += "<br/></span>";
							
				}    
			}
			catch(err)	{
				if($('details'))	{
					$("details").innerHTML = err;
					$("details").style.display = "";
				}
			}
		 		
			if($(listName+"status"))	{
				setTimeout(function()	{$(listName+"status").style.display = "none";}, 500);
			}		
		},
		//all form validation is done client side. The name and file fields are checked for null
		// and list name collisions are checked. If there is a collision the user can either
		//overwrite the stored list with the current list or cancel the action and rename it. -KR

		//never gets called if you click <enter> on the keyboard, hence the onSubmit = return false; - RL
		//moved here from inline - RL
     	'validateForm' : function()	{
			//optional args: listName, upload, uploadStatus
			//loosely checking for presence
			var listName = arguments.length>0 ? arguments[0] : 'listName';
			var upload = arguments.length>1 ? arguments[1] : 'upload';
			var uploadStatus = arguments.length> 2 ? arguments[2] : 'uploadStatus';
			var uploadForm = arguments.length>3 ? arguments[3] : 'uploadForm';
			
			$(uploadStatus).style.display = "";
	     	var thisListName = $(listName).value;
			
			var errors = "";
	     	if(thisListName == ""){ 
	     		errors += "please enter a name for this list. \n";     	    
	     	}
	     	if($(upload).value==""){
	     		errors += "please enter a file for this list.";
			}  
	     	if(errors != ""){
	     	    alert(errors);
	     	    $(uploadStatus).style.display = "none";
	     	    return false;
	     	}
	     	else {
		    	$(uploadForm).submit();
			} 
	     }
	};
	