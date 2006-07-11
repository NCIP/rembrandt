//Dependencies:  prototype.js, DynamicListHelper.js, images/indicator.gif
var SidebarHelper = {
	'delay' : 250,
	'loadingImg' : "<img src=\"images/indicator.gif\"/>",
	'loadSidebar' : function()	{
		SidebarHelper.loadPatientUL();
		SidebarHelper.loadGeneUL();
		SidebarHelper.loadGenericUL("Reporter");
		
	},
	'loadPatientUL' : function()	{
		if($('sidebarPatientDIDUL'))	{
			$('sidebarPatientDIDUL').innerHTML = this.loadingImg;
			setTimeout( function()	{
				DynamicListHelper.getGenericListAsList("PatientDID", SidebarHelper.loadGenericUL_cb);
				},SidebarHelper.delay);
		}
	},
	'loadGeneUL' : function()	{
		if($('sidebarGeneUL'))	{
			$('sidebarGeneUL').innerHTML = this.loadingImg;
			setTimeout( function()	{
				DynamicListHelper.getGenericListAsList("Gene", SidebarHelper.loadGenericUL_cb);
			},SidebarHelper.delay);
		}
	},
	'loadGenericUL' : function(listType)	{
		if($('sidebar'+listType+'UL'))	{
			$('sidebar'+listType+'UL').innerHTML = this.loadingImg;
			setTimeout( function()	{
				DynamicListHelper.getGenericListAsList(listType, SidebarHelper.loadGenericUL_cb);
			},SidebarHelper.delay);
		}
	},
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