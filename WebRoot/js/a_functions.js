var vr_count = 0;
var vr_totalRuns = 0;
var vr_alldone = true;

var vr_slow_checker;
var slow_check_flag = false;
var sid = "";

var times_to_check = 10;	
			
function A_checkAllFindingStatus(sessionId)	{
	if(sid=="") sid = sessionId;
	showLoading(true);
	vr_totalRuns++;
	Inbox.checkAllStatus(sessionId, A_checkFindingStatus_cb);
	if(vr_totalRuns > times_to_check) 	{ //avoid infinte loops
		clearInterval(vr_checker);
		
		//lets slow the interval for checking...1 minute
		if(!slow_check_flag)	{
			slow_check_flag = true;
			//alert("slow it down");
			vr_slow_checker = setInterval("A_checkAllFindingStatus(sid)", 60000);
		}
		
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
			else if(tasks[key]["status"] == 'error')	{
				//its done, see if the innerhtml already says done	
				if(curEl.innerHTML != "error")	{
					curEl.innerHTML = "error";
					curElImg.src = "images/error.png";
					//curElLink.onclick = "";
					//curElLink.removeAttribute("onclick");
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

function A_nukeFinding(key)	{
	//Inbox.deleteFinding(key, A_nukeFinding_cb);
}
function A_nukeFinding_cb(txt)	{
	//its been nuked...refresh the page
	location.replace(location.href);
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