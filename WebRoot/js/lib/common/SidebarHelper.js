//Dependencies:  prototype.js, DynamicListHelper.js, images/indicator.gif
var SidebarHelper = {
	'delay' : 250,
	'loadingImg' : "<img src=\"images/indicator.gif\"/>",
	'loadSidebar' : function()	{
		DynamicListHelper.getAllLists(SidebarHelper.loadSidebar_cb);
	},
	'loadSidebar_cb' : function(txt)	{
			try	{
				var listContainerArray = eval('(' + txt + ')');
				for(var i=0; i<listContainerArray.length; i++)	{
					var listContainer = listContainerArray[i];
					var listType = listContainer.listType ? listContainer.listType : "none";
					if(listType == "none") {
						continue;
					}
					
					var lists = listContainer.listItems;
					if(lists.length == 0)	{
						if($('sidebar'+listType+'UL'))	{
							$('sidebar'+listType+'UL').innerHTML = "<br/>No lists currently saved";
					    }
						continue;
					}
	
					if($('sidebar'+listType+'UL'))
						$('sidebar'+listType+'UL').innerHTML = ""; //wipe it first
					
					var tst = "";
					tst = "<ul>";
					for(var t=0; t<lists.length; t++)	{
					
					//	var status = "<span id=\""+lists[t].listName+"status\" style=\"display:none\"><img src=\"images/indicator.gif\"/></span>";
						var shortName = lists[t].listName.length>25 ? lists[t].listName.substring(0,23) + "..." : lists[t].listName;
						var theName = lists[t].listName;
						var listItems = lists[t].listItems ? lists[t].listItems : "none";
						var listSubType = lists[t].listSubType;
						var lstyle = lists[t].highlightType;			
						//generate the LIs
						tst += "<li style=\""+lstyle+"\" id=\""+theName+"\" title=\"" + theName + ":<br/>" +listItems+"\">" + shortName + "</li>\n";
					}
					tst += "</ul>";
					
					if($('sidebar'+listType+'UL'))
						$('sidebar'+listType+'UL').innerHTML += tst;
						
					//create the onclicks
					SidebarHelper.createOnClicks('sidebar'+listType+'UL');
				}
			}
			catch(err)	{
				alert("ERR: " + err);
			}	
	
	},
/*
	'loadGenericUL' : function(listType)	{
		if($('sidebar'+listType+'UL'))	{
			$('sidebar'+listType+'UL').innerHTML = this.loadingImg;
			setTimeout( function()	{
				DynamicListHelper.getGenericListAsList(listType, SidebarHelper.loadGenericUL_cb);
			},SidebarHelper.delay);
		}
	},
	'loadGenericUL_cb' : function(txt)	{
		//accepts 1 list only, make so it will accept an array
		var lists = eval('(' + txt + ')');
		//expecting an HTML element w/ id = "sidebar"+listType+"UL"
		if($('sidebar'+lists.listType+'UL'))	{
			if(lists.LIs != "")	{
				$('sidebar'+lists.listType+'UL').innerHTML = "<ul>" + lists.LIs + "</ul>";
				SidebarHelper.createOnClicks('sidebar'+lists.listType+'UL');
			}
			else	{
				$('sidebar'+lists.listType+'UL').innerHTML = "<br/>No Lists Available";
			}
		}
	},
*/
	'createOnClicks' : function(theId)	{
		var lis = $(theId).getElementsByTagName("li");

		var tmpp = new Array();

		var eurl = arguments.length > 1 ? arguments[1] : "listExport.jsp";
		
		for(var i=0; i<lis.length; i++)	{
			lis[i].ondblclick = function() { 
				var eid = encodeURIComponent(this.id);
				var url = eurl+"?list="+eid;
				location.href=url;
				//this = the li we want
				//alert(this.innerHTML); 
			};
			lis[i].onmouseover = function() { 
				tmpp[this.id] = this.title;
				this.title = "";
				return overlib(tmpp[this.id].split(",").join("<br/>"), CAPTION, this.id + " Elements:");
			};
			lis[i].onmouseout = function() { 
				this.title = tmpp[this.id];
				return nd();
			};
			
			//lis[i].id = lis[i].innerHTML;
			
			lis[i].style.cursor = "pointer";
			//trim the HTML length
			if(lis[i].innerHTML.length > 15)
				lis[i].innerHTML = lis[i].innerHTML.substring(0,14) + "...";
		}
	}
};