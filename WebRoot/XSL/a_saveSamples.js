/****************
SAVE SAMPLES
*****************/

var savedHeader = "Selected Samples:\n<br/>";
var currentTmpSamples = "";
var currentTmpSamplesCount = 0;

function A_saveTmpSample(sample)	{
	var sam = sample.value;
	
	if(sample.checked == true)	{ // && currentTmpReporters.indexOf(reporter.value)==-1
		DynamicReport.saveTmpSamplesFromClinical(sam, A_saveTmpSample_cb);
		//alert("Adding " + sam);
	}
	else	{
		DynamicReport.removeTmpSampleFromClinical(sam, A_saveTmpSample_cb);
		//alert("removing " + sam);
	}
}

function A_saveTmpSample_cb(txt)	{
	//look9ing for txt["count"] and txt["elements"]..txt["elements"] is a <br/> delim string
	//sample has been added to the list,
	//show how many we've saved,
	if(txt["count"] > -1) {
		if($("sampleCount"))
			$("sampleCount").innerHTML = txt["count"] + " samples selected";
		currentTmpSamplesCount = txt["count"];
		
		//update the running tab for overlib if this is not an init call
		currentTmpSamples = txt["elements"];
		
		//highlight box in red with the tempReporterName if we have some waiting to be saved
		if($("sampleGroupName") && txt["count"] > 0)
			$("sampleGroupName").style.border = "1px solid red";
	}
	else	{
		A_clearTmpSamples_cb("");
	}
}

function A_initSaveSample()	{
	DynamicReport.saveTmpSamplesFromClinical("", A_initTmpSample_cb);
}

function A_initTmpSample_cb(txt)	{
	A_saveTmpSample_cb(txt);
	//now, check the ones if theyve been previously selected
	var field = document.getElementsByName('samples');
	if(field.length > 1 && currentTmpSamples != "")	{
		for (i = 0; i < field.length; i++)	{
			if(currentTmpSamples.indexOf(field[i].value) != -1 )
				field[i].checked = true ;
		}
	}
	else	{
		if(currentTmpSamples.indexOf(field.value) != -1 )
			field.checked = true;
	}
}

function A_clearTmpSamples()	{
	DynamicReport.clearTmpSamplesFromClinical(A_clearTmpSamples_cb);
}

function A_clearTmpSamples_cb(txt)	{
		if($("sampleCount"))
			$("sampleCount").innerHTML = "";
		currentTmpSamplesCount = 0;
		currentTmpSamples = "";
		if($("sampleGroupName"))
			$("sampleGroupName").style.border = "1px solid";

		A_uncheckAll(document.getElementsByName('samples'));
}

function A_checkAll(field)	{
		if(field.length > 1)	{
			for (i = 0; i < field.length; i++)	{
				field[i].checked = true ;
				A_saveTmpSample(field[i]);
			}
		}
		else
			field.checked = true;
}

function A_uncheckAll(field)	{
	if(field.length > 1)	{
		for (i = 0; i < field.length; i++)	{
			field[i].checked = false ;
			A_saveTmpSample(field[i]);
		}
	}
	else
		field.checked = false;
		
	$("checkAll").checked = false;
		
}

function manageCheckAll(box)	{
	if(box.checked)	{
		A_checkAll(document.getElementsByName('samples'));
	}
	else	{
		A_uncheckAll(document.getElementsByName('samples'));
	}
}

function A_saveSamples()	{
	//get the name
	var name = $("sampleGroupName").value;
	if(name != "")	{
		if(currentTmpSamples != "")	{
			//convert the overlib list to a comma seperated list
			var replaceme = "<br/>";
			var commaSepList = currentTmpSamples.replace(/<br\/>/g, ",");
			if(commaSepList.charAt(commaSepList.length-1) == ",")
				commaSepList = commaSepList.substring(0, commaSepList.length-1);
			//alert(name + " = '"+commaSepList+"' ");
			DynamicReport.saveSamples(commaSepList, name, A_saveSamples_cb);
		}
		else	{
			alert("Please select some samples to save");
		}
	}
	else	{
		alert("Please enter a name for your sample group");
	}
}

function A_saveSamples_cb(txt)	{
	var results = txt == "pass" ? "Sample List Saved" : "There was a problem saving your sample list";
	alert(results); //pass | fail
	if(txt != "fail")	{
		//erase the name
		$("sampleGroupName").value = "";
		//clear the sample list
		A_clearTmpSamples();
	}
	
}



/*********************
END SAVE SAMPLES
***********************/