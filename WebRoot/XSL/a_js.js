function saveReporter(reporter)	{
	var rep = reporter.value;
	
	if(reporter.checked = true)	{
		A_SessionManager.saveTmpReporter(reporter, A_saveTmpReporter_cb);
		alert("Adding");
	}
	else	{
		A_SessionManager.removeTmpReporter(reporter, A_saveTmpReporter_cb);
		alert("removing");
	
	}
}

var savedHeader = "Selected Samples:\n<br/>";
var currentTmpSamples = "";
var currentTmpSamplesCount = 0;

function A_saveTmpReporter_cb(txt)	{
	//reporter has been added to the list,
	//show how many we've saved,
	//update the running tab for overlib 
	//highlight box in red with the tempReporterName if we have some waiting to be saved
}

function initSaveReporter()	{
	A_SessionManager.saveTmpReporter("", A_saveTmpReporter_cb);
}