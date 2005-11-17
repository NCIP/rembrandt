function A_saveTmpReporter(reporter)	{
	var rep = reporter.value;
	
	if(reporter.checked == true)	{
		DynamicReport.saveTmpReporter(rep, A_saveTmpReporter_cb);
		//alert("Adding " + rep);
	}
	else	{
		DynamicReport.removeTmpReporter(rep, A_saveTmpReporter_cb);
		//alert("removing " + rep);
	}
}

var savedHeader = "Selected Reporters:\n<br/>";
var currentTmpReporters = "";
var currentTmpReportersCount = 0;

function A_saveTmpReporter_cb(txt)	{
	//look9ing for txt["count"] and txt["reporters"]
	//reporter has been added to the list,
	//show how many we've saved,
	if(txt["count"] > -1) {
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
	DynamicReport.saveTmpReporter("", A_saveTmpReporter_cb);
}

function A_clearTmpReporters()	{
	DynamicReport.clearTmpReporters(A_clearTmpReporters_cb);
}

function A_clearTmpReporters_cb(txt)	{
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
		
}