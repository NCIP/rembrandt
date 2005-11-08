
function A_checkFindingStatus(taskId, sessionId)	{

	Inbox.checkStatus(taskId, sessionId, A_checkFindingStatus_cb);
}

function A_checkFindingStatus_cb(tasks)	{
	//looking for an assoc array arr[taskId]=status
	for(var i=0; i<tasks.length; i++)	{
		if(tasks[i] == 'completed')	{
			//its done, see if the innerhtml already says done
			var curEl = document.getElementById(tasks[i]+"_status");
			var curElImg = document.getElementById(tasks[i]+"_image");
			if(curEl.innerHtml != "completed")	{
				curEl.innerHtml = "completed";
				curElImg.src = "images/check.png";
			}
		}
		else	{
			//its running or errored
		}
	}

}

function testMap(k)	{
	Inbox.mapTest(k, testMap_cb);
}
function testMap_cb(map)	{
	alert(map["firstKey"]);
	alert(map["secondKey"]);
}