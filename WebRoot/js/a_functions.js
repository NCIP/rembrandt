var vr_count = 0;
var vr_totalRuns = 0;
				
function A_checkAllFindingStatus(sessionId)	{
	showLoading(true);
	vr_totalRuns++;
	Inbox.checkAllStatus(sessionId, A_checkFindingStatus_cb);
	if(vr_totalRuns > 3) 	{ //only run 3 times to avoid infinte loops
		clearInterval(vr_checker);
	}
}

/*
function A_checkSingleFindingStatus(taskId, sessionId)	{
	Inbox.checkSingleStatus(sessionId, taskId, A_checkFindingStatus_cb);
}
*/

function A_checkFindingStatus_cb(tasks)	{
	//looking for an assoc array arr[taskId]=status
	for(key in tasks)	{
		if(tasks[key] == 'completed')	{
			//its done, see if the innerhtml already says done
			var curEl = document.getElementById(key+"_status");
			var curElImg = document.getElementById(key+"_image");
			if(curEl.innerHTML != "completed")	{
				curEl.innerHTML = "completed";
				curElImg.src = "images/check.png";
			}
		}
		else	{
			//its running or errored
		}
	}

}

function showLoading(showit)	{
	var msg = document.getElementById("loadingMsg");
	if(showit)	{
		msg.innerHTML = " <strong>Checking status...</strong>";
		setTimeout("showLoading(false)", 1000);
	}
	else 
		msg.innerHTML = "&nbsp;";
}


function testMap(k)	{
	Inbox.mapTest(k, testMap_cb);
}
function testMap_cb(map)	{
	alert(map["firstKey"]);
	alert(map["secondKey"]);
}