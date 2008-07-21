var vr_count = 0;
var vr_totalRuns = 0;
var vr_alldone = true;

var vr_slow_checker;
var slow_check_flag = false;
var sid = "";

var times_to_check = 10;
var add = "0";	
			
function A_checkGenePatternStatus(jobId, indicator){

	//alert("A_checkGenePatternStatus(jobId) called " + jobId);
	if (add == '0'){
		add = indicator;
	}
	if(sid=='') sid = jobId;
	showLoading(true);
	vr_totalRuns++;
	GenePatternHelper.checkGPStatus(sid, A_checkGenePatternStatus_cb);
	//alert("After GenePatternHelper.checkGPStatus... " + jobId);
	if(vr_totalRuns > times_to_check) 	{ //avoid infinte loops
		clearInterval(vr_checker);
		
		//lets slow the interval for checking...1 minute
		if(!slow_check_flag)	{
			slow_check_flag = true;
			//alert("slow it down");
			vr_slow_checker = setInterval("A_checkGenePatternStatus(sid)", 60000);
		}
	}
	//alert("A_checkGenePatternStatus(jobId) exisitng... " + jobId);
}

/*
function A_checkSingleFindingStatus(taskId, sessionId)	{
	Inbox.checkSingleStatus(sessionId, taskId, A_checkFindingStatus_cb);
}
*/


function A_checkGenePatternStatus_cb(message){
	//looking for an assoc array arr[taskId]=arr[status, time]
	//alert("A_checkGenePatternStatus_cb called " + message);
	var vr_alldone = true;

	var curEl = document.getElementById(sid+"_status");
	var curElImg = document.getElementById(sid+"_image");
	var curElLink = document.getElementById(sid+"_link");
	var curElVLink = document.getElementById(sid+"_v_link");
	var curElJobSelect = document.getElementById(sid+"_jobId");
	var curElProcessSelect = document.getElementById(sid+"_process");
	var curElSubmit = document.getElementById(sid+"_submit");
	if(message == 'completed')	{
		//its done, see if the innerhtml already says done	
		if(curEl.innerHTML != "completed")	{
			curEl.innerHTML = "completed";
			//alert("message == 'completed' " + message);
			curElJobSelect.disabled = false;
			curElProcessSelect.disabled = false;
			curElSubmit.disabled = false;
			curElImg.src = "images/check.png";

			//Add the new job id to job list list box if needed.
			if (add == "1"){
				var no = new Option();
				no.value = sid;
				no.text = sid;
				curElJobSelect.options[curElJobSelect.options.length] = no;
			}
			vr_alldone = true;
			curElLink.onclick = "";
			curElLink.removeAttribute("onclick");
			curElVLink.onclick = "";
			curElVLink.removeAttribute("onclick");
		}
	}
	else if(message == 'error')	{
		//its done, see if the innerhtml already says done	
		if(curEl.innerHTML.indexOf('error') == -1)	{
			var comments = "Unspecified Error";	
			curEl.innerHTML = showErrorHelp(comments, "error");
			curElImg.src = "images/error.png";
			//curElLink.onclick = "";
			//curElLink.removeAttribute("onclick");
			vr_alldone = true;
		}
	}
	else if(message == 'running' && curEl.innerHTML.indexOf('completed') != -1 )	{
		//handle overlapping AJAX calls...this ones already completed...dont reset to running
		//basically just ignore it
	}
	else{
		//its running, need to continue
		vr_alldone = false;
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
	err = txt.indexOf(".") != -1 ? txt.split(".")[0] : txt;
	var t = err != "" ? err +"." : "Unspecified Error.";
	t=unescape(t);
	return overlib(t, CAPTION, "Error Details");
}