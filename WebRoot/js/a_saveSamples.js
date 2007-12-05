var DEBUG = true;


var savedHeader = "Selected Samples:\n<br/>";
var currentTmpSamples = "";
var currentTmpSamplesCount = 0;

function A_saveTmpSample(sample)	{
		DynamicReport.saveTmpSamples(sample, A_saveTmpSample_cb);
}

/*
function A_saveTmpSample(sample)	{
	var spl = sample.value;
	
	//based on a checkbox selection
	if(sample.checked == true)	{ 
		DynamicReport.saveTmpSample(spl, A_saveTmpSample_cb);
		if(DEBUG)
			alert("Adding " + spl);
	}
	else	{
		DynamicReport.removeTmpSample(spl, A_saveTmpSample_cb);
		if(DEBUG)
			alert("removing " + spl);
	}
}
*/

function A_saveTmpSample_cb(txt)	{
	//look9ing for txt["count"] and txt["elements"]..txt["elements"] is a <br/> delim string
	//reporter has been added to the list,
	//show how many we've saved,
	if(txt["count"] > 0) {
		if($("sampleCount"))
			$("sampleCount").innerHTML = txt["count"]-1 + " samples selected";
		currentTmpSamplesCount = txt["count"];
		
		//update the running tab for overlib if this is not an init call
		currentTmpSamples = txt["elements"];
		
		//highlight box in red with the sampleGroupName if we have some waiting to be saved
		if($("sampleGroupName") && txt["count"] > 0)
			$("sampleGroupName").style.border = "1px solid red";
	}
	else	{
		A_clearTmpSamples_cb("");
	}
}

function A_initSaveSample()	{
	DynamicReport.saveTmpSamples("", A_initTmpSample_cb);
}

function A_initTmpSample_cb(txt)	{
	A_saveTmpSample_cb(txt);
	//populate the local JS
	pendingSamples = currentTmpSamples.split("<br/>");
	writePendings();
	
	
	/*
	//now, check the ones if theyve been previously selected
	var field = document.getElementsByName('tmpSample');
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
	*/
}

function A_clearTmpSamples()	{
	DynamicReport.clearTmpSamples(A_clearTmpSamples_cb);
}

function A_clearTmpSamples_cb(txt)	{
		if($("sampleCount"))
			$("sampleCount").innerHTML = "";
			
		//reset JS vals
		currentTmpSamplesCount = 0;
		currentTmpSamples = "";
		
		if($("sampleGroupName"))
			$("sampleGroupName").style.border = "1px solid";

		//based on checkbox selection..not using
		//A_uncheckAll(document.getElementsByName('tmpSamples'));
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
			//alert("="+commaSepList+"=");
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
	//also call the clear on "local" js
	try	{
		clearPending();
	}
	catch(error)	{}
}
