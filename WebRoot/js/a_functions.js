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

function A_checkAllTaskResultsStatus(sessionId)	{
	if(sid=="") sid = sessionId;
	showLoading(true);
	vr_totalRuns++;
	Inbox.checkAllTaskResultsStatus(sessionId, A_checkAllTaskResultsStatus_cb);
	if(vr_totalRuns > times_to_check) 	{ //avoid infinte loops
		clearInterval(vr_checker1);
		
		//lets slow the interval for checking...1 minute
		if(!slow_check_flag)	{
			slow_check_flag = true;
			//alert("slow it down");
			vr_slow_checker = setInterval("A_checkAllTastResultsStatus(sid)", 60000);
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
		if(key == "GeneExpressionPlot")
			continue;
			
		var curElTime = document.getElementById(key+"_time");
		var curEl = document.getElementById(key+"_status");
		var curElImg = document.getElementById(key+"_image");
		var curElLink = document.getElementById(key+"_link");
		
			if(tasks[key]["status"] == 'Completed')	{
				
				//its done, see if the innerhtml already says done	
				if(curEl.innerHTML != "Completed")	{
					curEl.innerHTML = "Completed";
					curElImg.src = "images/check.png";
					curElLink.onclick = "";
					curElLink.removeAttribute("onclick");
					
					//try and play a sound
					/*
					try	{
						soundManager.play('beep');
					}
					catch(ex){ }
					*/
				}
			}
			else if(tasks[key]["status"] == 'Error')	{
				//its done, see if the innerhtml already says done	
				if(curEl.innerHTML.indexOf('Error') == -1)	{
					var comments = "Unspecified Error";
					
					if(tasks[key]["comments"] && tasks[key]["comments"] != "")	{
						comments = tasks[key]["comments"];
					}
					curEl.innerHTML = showErrorHelp(comments, "Error");
		
					curElImg.src = "images/error.png";
					//curElLink.onclick = "";
					//curElLink.removeAttribute("onclick");
				}
			}
			else if(tasks[key]["status"] == 'Running' && curEl.innerHTML.indexOf('Completed') != -1 )	{
				//handle overlapping AJAX calls...this ones already completed...dont reset to running
				//basically just ignore it
			}
			else	{
				//its running, need to continue
				vr_alldone = false;
			}
			curElTime.innerHTML = tasks[key]["time"];
	}
	
	if(vr_alldone)	{
		//stop checking, theyre all done
		clearInterval(vr_checker);
	}

}

function A_checkAllTaskResultsStatus_cb(tasks)	{
	//looking for an assoc array arr[taskId]=arr[status, time]
	var vr_alldone = true;
		for(key in tasks)	{
		var curElTime = document.getElementById(key+"_time");
		var curEl = document.getElementById(key+"_status");		
		var curElImg = document.getElementById(key+"_image");		
		var curElLink = document.getElementById(key+"_link");
		var curElEmail = document.getElementById(key+"_email");		
		var curElEmailLink = document.getElementById(key+"_email_link");
		//curElLink.onclick = "";
		if(curElEmailLink){
			curElEmailLink.innerHTML = "<img src='images/blank.gif' BORDER=0 />";
			//curElEmailLink.removeAttribute("href");
		    //curElEmailLink.onclick = function(){return false;};
		}

		
		var emailImage = "images/blank.gif";		
		var taskId = tasks[key]["task_id"];
		var cacheId = tasks[key]["cache_id"];			
	
			
			if( tasks[key]["email_timeout"]=='true'){
				var newhref= "emailReport.do?taskId=" + taskId + "&cacheId=" + cacheId ;
				curElEmailLink.innerHTML = "<img src='images/mail_icon.gif' alt='email results' BORDER=0 />";
				curElEmailLink.onclick = new Function( "return newWindow('" + newhref + "');" );
 			}
 			if(tasks[key]["status"] == 'Completed')	{
				//its done, see if the innerhtml already says done	
				if(curEl.innerHTML != "Completed")	{
					curEl.innerHTML = "Completed";
					curElImg.src = "images/check.png";
					curElLink.onclick = "";
					curElLink.removeAttribute("onclick");
					curElEmailLink.detachEvent("onclick");
					emailImage = "images/blank.gif";
					curElEmailLink.removeAttribute("href");
					//try and play a sound
					/*
					try	{
						soundManager.play('beep');
					}
					catch(ex){ }
					*/
				}
			}
			else if(tasks[key]["status"] == 'Error')	{
				//its done, see if the innerhtml already says done	
				if(curEl.innerHTML.indexOf('Error') == -1)	{
					var comments = "Unspecified Error";
					
					if(tasks[key]["comments"] && tasks[key]["comments"] != "")	{
						comments = tasks[key]["comments"];
					}
					curEl.innerHTML = showErrorHelp(comments, "Error");
					emailImage = "images/blank.gif";
					curElEmailLink.removeAttribute("href");
					curElEmailLink.detachEvent("onclick");
					curElImg.src = "images/error.png";
					//curElLink.onclick = "";
					//curElLink.removeAttribute("onclick");
				}
			}
			else if(tasks[key]["status"] == 'Email')	{
				//its done, see if the innerhtml already says done	
				if(curEl.innerHTML.indexOf('Email') == -1)	{
					var comments = "Report will be emailed";
					
					if(tasks[key]["comments"] && tasks[key]["comments"] != "")	{
						comments = tasks[key]["comments"];
					}
					curEl.innerHTML = showErrorHelp(comments, "Email");
					emailImage = "images/blank.gif";
					curElEmailLink.removeAttribute("href");
					curElEmailLink.detachEvent("onclick");
					curElImg.src = "images/mail_icon.gif";
					//curElLink.onclick = "";
					//curElLink.removeAttribute("onclick");
				}
			}
			else if(((tasks[key]["status"] == 'Running')|| (tasks[key]["status"] == 'Retrieving'))&& curEl.innerHTML.indexOf('Completed') != -1 )	{
				//handle overlapping AJAX calls...this ones already completed...dont reset to running
				//basically just ignore it
			}
			else	{
				//its running, need to continue
				vr_alldone = false;
			}

			curElTime.innerHTML = tasks[key]["time"];
			curElEmail.src = emailImage;
	}
	
	if(vr_alldone)	{
		//stop checking, theyre all done
		clearInterval(vr_checker1);
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

function showErrorHelp(txt, show)	{
	txt = txt.replace("\n", " ");
	txt = txt.replace("/n", " ");
	txt = txt.replace("\"", "\\\"");
	txt = txt.replace("\'", "\\\'");
	var html = "<a href=\"#\"  style=\"text-decoration:none; border-bottom: 1px dashed #AB0303;\" onmouseover=\"return overlibWrapper('"+escape(txt)+"');return false;\" onmouseout=\"return nd();\" ><strong>"+show+"</strong></a>";
	return html;
}

function overlibWrapper(txt)	{
	//just get the first sentence
	var err = "";
	//err = txt.indexOf(".") != -1 ? txt.split(".")[0] : txt;
	err = escape(txt);
	var t = err != "" ? txt  : "Unspecified Error.";
	t=unescape(t);
	return overlib(t, CAPTION, "Details");
}


function newWindow(newhref)
{ 
	window.open(newhref,'_blank','toolbar=no,width=850,height=400,top=0,left=0,toolbar=0, menubar=0, location=0, status=0, scrollbars=0, resizable=0'); 
}