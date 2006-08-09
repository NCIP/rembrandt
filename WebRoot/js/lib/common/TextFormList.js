   var TextFormList =	{
    	'processTextForm' : function ()	{
    		//optional args:  commaListOfIds, listName, listType, statusSpanId
    		
    		var typeListIdsLabel = arguments.length > 0 ? arguments[0] : "typeListIds";
  			var listNameLabel = arguments.length > 1 ? arguments[1] : "listName";
  			var typeSelectorLabel = arguments.length > 2 ? arguments[2] : "typeSelector";
  			var uploadStatusLabel = arguments.length >3 ? arguments[3] : "uploadStatus";
  			
    		var typeListIds = $(typeListIdsLabel) && $(typeListIdsLabel).value ? $(typeListIdsLabel).value : "";
    		var listName = $(listNameLabel) && $(listNameLabel).value ? $(listNameLabel).value : "";
    		var uploadStatus = $(uploadStatusLabel) ? $(uploadStatusLabel) : "";   		
     		var typeSelector = $(typeSelectorLabel) &&  $(typeSelectorLabel).options ? $(typeSelectorLabel).options[document.getElementById(typeSelectorLabel).selectedIndex].value : "noType";
    		    		
		     if(typeListIds != "" && listName != "")	{
		     	if(uploadStatus.innerHTML)	{
			     	uploadStatus.style.display = "";
			     }
		     	//construct array
		     	var ids = Array();
		     	//ids = typeListIds.split(",");
		     	ids = typeListIds.split("\n");
		     	//clean on the sside
		     	//ajax call
		     	try	{
			    	DynamicListHelper.createGenericList(typeSelector, ids, listName, TextFormList.processTextForm_cb);
			    }
			    catch(err)	{
				    if(uploadStatus != "")
				     	uploadStatus.style.display = "";
			    }
			    finally	{
			    	setTimeout( function()	{
			    	if(typeListIds != "")	{
					    typeListIds = "";
					    $(typeListIdsLabel).value = "";
					} 
					if(listName != "")	{
			    		listName = "";
			    		$(listNameLabel).value = "";
			    	}
			    	/*
			    	if(uploadStatus != ""){
			    		uploadStatus.style.display = "none";
			    	}
			    	*/
			    	}, 200);
			    } 
		     }
		     else	{
		     	alert("Please fill in all fields");
		     } 
	    },
	    'processTextForm_cb' : function(res)	{
	    	//clear form, refresh lists
	    	var r = res.split(",");
	    	if(r[0] != "pass")	{
	    		alert("List did not save correctly, please try again.");
			}	
			else	{
				//StatusMessage.showStatus("List Saved...");
			}    		
			if( $('uploadStatus') && $('uploadStatus').style.display != 'none'){
				$('uploadStatus').style.display = "none";
			}
				
	    	ManageListHelper.generic_cb("none");
	    }
	};
