/****************
SAVE REPORTERS
*****************/

var savedHeader = "Selected Reporters:\n<br/>";
var currentTmpReporters = "";
var currentTmpReportersCount = 0;

var allReporters = Array();
var allHighlightedReporters = Array();

function A_saveTmpReporter(reporter)	{
	var rep = reporter.value;
	
	if(reporter.checked == true)	{ // && currentTmpReporters.indexOf(reporter.value)==-1
		DynamicReport.saveTmpReporter(rep, A_saveTmpReporter_cb);
		//alert("Adding " + rep);
	}
	else	{
		DynamicReport.removeTmpReporter(rep, A_saveTmpReporter_cb);
		//alert("removing " + rep);
	}
}

function A_saveTmpReporter_cb(txt)	{
	//look9ing for txt["count"] and txt["reporters"]..txt["reporters"] is a <br/> delim string
	//reporter has been added to the list,
	//show how many we've saved,
	if(txt["count"] > -1) {
		if($("reporterCount"))
			$("reporterCount").innerHTML = txt["count"] + " reporters selected";
		currentTmpReportersCount = txt["count"];
		
		//update the running tab for overlib if this is not an init call
		currentTmpReporters = txt["elements"];
		
		//highlight box in red with the tempReporterName if we have some waiting to be saved
		if($("tmp_prb_queryName") && txt["count"] > 0)
			$("tmp_prb_queryName").style.border = "1px solid red";
	}
	else	{
		A_clearTmpReporters_cb("");
	}
}

function A_initSaveReporter()	{
	DynamicReport.saveTmpReporter("", A_initTmpReporter_cb);
}

function A_initTmpReporter_cb(txt)	{
	A_saveTmpReporter_cb(txt);
	//now, check the ones if theyve been previously selected
	
	if(!document.getElementsByName('tmpReporter').length)	{
		return;
	}
	var field = document.getElementsByName('tmpReporter');
	if(field.length >= 1 && (currentTmpReporters != "" || allHighlightedReporters.length>0))	{
		for (i = 0; i < field.length; i++)	{
			if(currentTmpReporters.indexOf(field[i].value) != -1 || allHighlightedReporters.inArray(field[i].value))
				field[i].checked = true ;
		}
	}
	else	{
		if(currentTmpReporters.indexOf(field.value) != -1  || allHighlightedReporters.inArray(field.value))
			field.checked = true;
	}
}

function A_clearTmpReporters()	{
	DynamicReport.clearTmpReporters(A_clearTmpReporters_cb);
}

function A_clearTmpReporters_cb(txt)	{
		if($("reporterCount"))
			$("reporterCount").innerHTML = "";
		currentTmpReportersCount = 0;
		currentTmpReporters = "";
		if($("tmp_prb_queryName"))
			$("tmp_prb_queryName").style.border = "1px solid";

		A_uncheckAll(document.getElementsByName('tmpReporter'));
}

function A_checkAll(field)	{
		if(field.length >= 1)	{
			for (i = 0; i < field.length; i++)	{
				field[i].checked = true ;
				A_saveTmpReporter(field[i]);
			}
		}
		else
			field.checked = true;
}

function A_checkAllOnAll(box)	{
	//clear the tmp ones weve already checked
	//get all the items on all pages and savethem
	//alert(allReporters.length);
	//update the UI to show which ones are checked and precheck all the boxes
	if(box.checked && allReporters.length && allReporters.length >= 1)	{
	//	SaveGenes.A_checkAll(allGenes);
		if(allReporters.length >= 1)	{
			DynamicReport.saveTmpGenericFromArray("tmpReporterList",allReporters,A_saveTmpReporter_cb);
			
			setTimeout(function() { A_initSaveReporter(''); }, 500);
		}
	}
	else if(!box.checked)	{
		A_clearTmpReporters();
	}
}

function A_uncheckAll(field)	{

	if(field.length > 1)	{
		for (i = 0; i < field.length; i++)	{
			field[i].checked = false ;
			A_saveTmpReporter(field[i]);
		}
	}
	else
		field.checked = false;
		
	$("checkAll").checked = false;
		
}

function manageCheckAll(box)	{
	if(box.checked)	{
		A_checkAll(document.getElementsByName('tmpReporter'));
	}
	else	{
		A_uncheckAll(document.getElementsByName('tmpReporter'));
	}
}

function A_saveReporters()	{
	//get the name
	var name = $("tmp_prb_queryName").value;
	var st = $("repSubType").value;
	//alert(st);
	
	if(name != "")	{
		//convert the overlib list to a comma seperated list
		if(currentTmpReporters != "")	{
			var replaceme = "<br/>";
			var commaSepList = currentTmpReporters.replace(/<br\/>/g, ",");
			if(commaSepList.charAt(commaSepList.length-1) == ",")
				commaSepList = commaSepList.substring(0, commaSepList.length-1);
			//alert("="+commaSepList+"=");
			DynamicReport.saveReportersWithSub(commaSepList, name, st, A_saveReporters_cb);
		}
		else	{
			alert("Please select some reporters to save");
		}
	}
	else	{
		alert("Please enter a name for your reporter group");
	}
}

function A_saveReporters_cb(txt)	{
	var results = txt == "pass" ? "Reporter List Saved" : "There was a problem saving your reporter list";
	alert(results); //pass | fail
	if(txt != "fail")	{
		//erase the name
		$("tmp_prb_queryName").value = "";
		//clear the sample list
		A_clearTmpReporters();
	}
	//attempt to refresh the parents sideba
	if(!window.opener.closed)	{
		try	{
			window.opener.SidebarHelper.loadSidebar();
		}
		catch(err)	{
			//alert("cant update sidebar: " + err);
		}
	}
}

/*********************
END SAVE REPORTERS
***********************/

//not going to use this function for now
function filterRow(pvalue)	{
	pvalue = "0.0100";
	var my_table = document.getElementById("dataTable");
	var my_rows = my_table.rows;
	//var rows = document.getElementsByTagName("tr");
	for(i=1; i<my_rows.length; i++)	{ //dont look @ header
		if(my_rows[i].id > pvalue)	{
			//my_rows[i].style.display = "none";
			my_table.deleteRow(i);
		}
	}
}

function checkStep()	{
	try	{
		var val = document.forms.paginate.p_step.value;
		if(val > "500" || val > 500)	{
			//alert('too many');
			$("checkAllBlock").innerHTML = "<i>reduce size to check all</i>";
		}
	}
	catch(e){}
}

function initPagination()	{
	if(!document.getElementById("dataTable"))	{
		return false;
	}
	var my_table = document.getElementById("dataTable");
	var my_rows = my_table.rows;
	//alert(my_rows.length-1);
}

function initSortArrows()	{
	if(!document.forms["paginate"])	{
		return;
	}
	var element = document.forms['paginate'].p_sort_element.value;
	var method = document.forms['paginate'].p_sort_method.value;

	var imgsrc = "images/sort-none.png";
	var upimgsrc = "images/openUpArrow.png";
	var downimgsrc = "images/openDownArrow.png";
	
	if(method == "ascending")	{
		downimgsrc = "images/openDownArrow.png";
		upimgsrc = "images/closedUpArrow.png";
	}
	else if(method == "descending")	{
		downimgsrc = "images/closedDownArrow.png";
		upimgsrc = "images/openUpArrow.png";
	}
	if(document.getElementById(element+"_sort_img_up"))	{
		if(document.getElementById(element+"_sort_img_up").src != upimgsrc)	{
			document.getElementById(element+"_sort_img_up").src = upimgsrc;
		}
	}
	if(document.getElementById(element+"_sort_img_down"))	{
		if(document.getElementById(element+"_sort_img_down").src != downimgsrc)	{
			document.getElementById(element+"_sort_img_down").src = downimgsrc;
		}
	}
	
}	

function goSort(element, method, key)	{
	//reuse the paginate form
	if(document.forms['paginate'].p_sort_element)
		 document.forms['paginate'].p_sort_element.value = element;
	if(document.forms['paginate'].p_sort_method)
		 document.forms['paginate'].p_sort_method.value = method;
	//go back to the start
	if(document.forms['paginate'].p_page)
		 document.forms['paginate'].p_page.value = "0";
	if(document.forms['paginate'].p_step)
		 document.forms['paginate'].p_step.value = "25"; 

	document.forms['paginate'].submit();
}

function populateReporterTypeDD()	{
	ListFilter.getSubTypesForTypeFromString("Reporter", populateReporterTypeDD_cb);
}
function populateReporterTypeDD_cb(txt){
	DWRUtil.removeAllOptions("repSubType", txt);
    DWRUtil.addOptions("repSubType", txt);
}

/* http://www.embimedia.com/resources/labs/js-inarray.html */
Array.prototype.inArray = function (value)	{
	// Returns true if the passed value is found in the
	// array.  Returns false if it is not.
	var i;
	for (i=0; i < this.length; i++) {
		// Matches identical (===), not just similar (==).
		if (this[i] == value) {
			return true;
		}
	}
	return false;
};