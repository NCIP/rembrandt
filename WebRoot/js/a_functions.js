var vr_count = 0;
var vr_totalRuns = 0;
var vr_alldone = true;
	
var times_to_check = 10;	
			
function A_checkAllFindingStatus(sessionId)	{
	showLoading(true);
	vr_totalRuns++;
	Inbox.checkAllStatus(sessionId, A_checkFindingStatus_cb);
	if(vr_totalRuns > times_to_check) 	{ //avoid infinte loops
		clearInterval(vr_checker);
	}
}

/*
function A_checkSingleFindingStatus(taskId, sessionId)	{
	Inbox.checkSingleStatus(sessionId, taskId, A_checkFindingStatus_cb);
}
*/

function A_checkFindingStatus_cb(tasks)	{
	//looking for an assoc array arr[taskId]=arr[status, time]
	var vr_alldone = true;
	
	for(key in tasks)	{
		
		var curElTime = document.getElementById(key+"_time");
		var curEl = document.getElementById(key+"_status");
		var curElImg = document.getElementById(key+"_image");
		var curElLink = document.getElementById(key+"_link");
		
			if(tasks[key]["status"] == 'completed')	{
				//its done, see if the innerhtml already says done	
				if(curEl.innerHTML != "completed")	{
					curEl.innerHTML = "completed";
					curElImg.src = "images/check.png";
					curElLink.onclick = "";
					curElLink.removeAttribute("onclick");
				}
			}
			else	{
				//its running or errored, need to continue
				vr_alldone = false;
			}
			curElTime.innerHTML = tasks[key]["time"];
	}
	
	if(vr_alldone)	{
		//stop checking, theyre all done
		clearInterval(vr_checker);
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