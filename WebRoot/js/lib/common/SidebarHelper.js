//Dependencies:  prototype.js, DynamicListHelper.js, images/indicator.gif
var SidebarHelper = {
	'delay' : 250,
	'loadingImg' : "<img src=\"images/indicator.gif\"/>",
	'loadSidebar' : function()	{
		SidebarHelper.loadPatientUL();
		//SidebarHelper.loadDefaultPatientUL();
		SidebarHelper.loadGeneUL();
	},
	'loadPatientUL' : function()	{
		if($('sidebarPatientDIDUL'))	{
			$('sidebarPatientDIDUL').innerHTML = this.loadingImg;
			setTimeout( function()	{
				DynamicListHelper.getPatientListAsList(SidebarHelper.loadGenericUL_cb);
				},SidebarHelper.delay);
		}
	},
	'loadDefaultPatientUL' : function()	{
		/*
		$('sidebarDefaultPatientDIDUL').innerHTML = this.loadingImg;
		setTimeout( function()	{
			DynamicListHelper.getDefaultPatientListAsList(SidebarHelper.loadGenericUL_cb);
			},SidebarHelper.delay);
		*/
		alert("not here 2");
	},
	/*
	'loadPatientUL_cb' : function(txt)	{
		if(txt != "")	{
			$('sidebarPatientUL').innerHTML = "<ul>" + txt + "</ul>";
			SidebarHelper.createOnClicks('sidebarPatientUL');
		}
		else	
			$('sidebarPatientUL').innerHTML = "<ul><li>No Lists Available</li></ul>";
	},
	'loadDefaultPatientUL_cb' : function(txt)	{
		if(txt != "")	{
			$('sidebarDPatientUL').innerHTML = "<ul>" + txt + "</ul>";
			SidebarHelper.createOnClicks('sidebarDPatientUL');
		}
		else	
			$('sidebarDPatientUL').innerHTML = "No Lists Available";
	},
	*/
	'loadGeneUL' : function()	{
		if($('sidebarGeneSymbolUL'))	{
			$('sidebarGeneSymbolUL').innerHTML = this.loadingImg;
			setTimeout( function()	{
				DynamicListHelper.getGeneListAsList(SidebarHelper.loadGenericUL_cb);
			},SidebarHelper.delay);
		}
	},
	/*
	'loadGeneUL_cb' : function(txt)	{
		if($('sidebarGeneUL'))	{
			if(txt != "")	{
				$('sidebarGeneUL').innerHTML = "<ul>" + txt + "</ul>";
				SidebarHelper.createOnClicks('sidebarGeneUL');
			}
			else	{
				$('sidebarGeneUL').innerHTML = "No Lists Available";
			}
		}
	},
	*/
	'loadGenericUL_cb' : function(txt)	{
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
				return overlib(tmpp[this.id].split(",").join("<br/>"), CAPTION, this.id.substring(0,10) + " Elements:");
			};
			lis[i].onmouseout = function() { 
				this.title = tmpp[this.id];
				return nd();
			};
			
			lis[i].id = lis[i].innerHTML;
			
			lis[i].style.cursor = "pointer";
			//trim the HTML length
			if(lis[i].innerHTML.length > 15)
				lis[i].innerHTML = lis[i].innerHTML.substring(0,14) + "...";
		}
	
	}

};