/****************
SAVE REPORTERS
*****************/

var savedHeader = "Selected Reporters:\n<br/>";
var currentTmpReporters = "";
var currentTmpReportersCount = 0;

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
		currentTmpReporters = txt["reporters"];
		
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
	var field = document.getElementsByName('tmpReporter');
	if(field.length > 1 && currentTmpReporters != "")	{
		for (i = 0; i < field.length; i++)	{
			if(currentTmpReporters.indexOf(field[i].value) != -1 )
				field[i].checked = true ;
		}
	}
	else	{
		if(currentTmpReporters.indexOf(field.value) != -1 )
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
		if(field.length > 1)	{
			for (i = 0; i < field.length; i++)	{
				field[i].checked = true ;
				A_saveTmpReporter(field[i]);
			}
		}
		else
			field.checked = true;
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
	if(name != "")	{
		//convert the overlib list to a comma seperated list
		if(currentTmpReporters != "")	{
			var replaceme = "<br/>";
			var commaSepList = currentTmpReporters.replace(/<br\/>/g, ",");
			if(commaSepList.charAt(commaSepList.length-1) == ",")
				commaSepList = commaSepList.substring(0, commaSepList.length-1);
			//alert("="+commaSepList+"=");
			DynamicReport.saveReporters(commaSepList, name, A_saveReporters_cb);
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
	var val = document.forms.paginate.p_step.value;
	if(val > "500" || val > 500)	{
		//alert('too many');
		$("checkAllBlock").innerHTML = "<i>reduce size to check all</i>";
	}
}

function initPagination()	{
	var my_table = document.getElementById("dataTable");
	var my_rows = my_table.rows;
	//alert(my_rows.length-1);
}

function initSortArrows()	{

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