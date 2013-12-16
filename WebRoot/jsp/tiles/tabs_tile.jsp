<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<script language="javascript">

	//start the checking
	//var checker = setInterval("Inbox.checkStatus(checkStatus_cb)", 5000);
	var count = 0;
	var totalRuns = 0;
	var DEBUG = false;
		
	function checkStatus_cb(txt)	{
		if(DEBUG)
			setStatus("i am checking..."+totalRuns);
		
		totalRuns++;		
		var el = document.getElementById("inboxStatus");
		//see whats coming back
		
		if(txt == "false")	{
			if(el.innerHTML.indexOf("circle.gif") == -1 && count<1)
				el.innerHTML += "<img id='statusImg' alt='loading' src='images/circle.gif' border='0'/>";
		}
		else if(txt == 'true')	{
			//hide the circle
			if(document.getElementById('statusImg'))
				document.getElementById('statusImg').style.display = "none";
			
			setStatus("<b>New Query Finished!  Click 'View Results' tab for details.</b>");
			count++;
			
			if(document.getElementById("newQ"))
				document.getElementById("newQ").innerHTML = "("+count+")";
			else
				el.innerHTML += "<b id='newQ'>("+count+")</b>";
		}
		
		if(totalRuns > 5) 	{ //only run 5 times to avoid infinte loops
			clearInterval(checker);
			if(DEBUG)
				setStatus("[stopped checking]");
		}
	}
	
	function setStatus(txt)	{
		document.getElementById("statusMsg").innerHTML = txt;
		setTimeout("clearStatus()", 2000);
	}
	function clearStatus()	{
		document.getElementById("statusMsg").innerHTML = "<br/>";
	}
</script>
<script type='text/javascript' src='dwr/interface/Inbox.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<span id="statusMsg"></span>

<% 
	//default settings for tabs
	String simple = "";
	String adv = "";
	String viewResults = "";
	String myWorkspace = "";
	String analysis = "";
	String secondary = "";
	String list = "";
	String download="";
	String advSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"menu.action\">Advanced Search Home</a></li>\n" +
							//"<li><a href=\"menu.action\">Build Query</a></li>\n" +
							"<li><a href=\"refinecheck.action\">Refine Query</a></li>\n" +
							"</ul>\n";
	String resultsSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"viewResults.action\">View Findings</a></li>\n" +
							"<li><a href=\"#\">Managed Saved Lists</a></li>\n" +
							//"<li><a href=\"#\">Upload Lists</a></li>\n" +
							"</ul>\n";				
	String simpleSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"home.action\">Simple Search Home</a></li>\n" +
							"</ul>\n";
	String analysisSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"analysisHome.action\">Analysis Home</a></li>\n" +
							"</ul>\n";

	String resultSecondary = "<ul id=\"secondary\">\n" +
							//"<li><a href=\"graph.action?method=setup\">Search</a></li>\n" +
							"<li><a href=\"viewResults.action\">Report Results</a></li>\n" +
							"<li><a href=\"gpProcess.action?method=setup\">GenePattern Job Results</a></li>\n" +							
							"</ul>\n";
	
	String autoLogged  = (String)request.getSession().getAttribute("autoLogged");
	String myWorkspaceSecondary = "";
	if( autoLogged != null && autoLogged.equals("yes")) {
		myWorkspaceSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"manageLists.action\">Manage Lists</a></li>\n" +
							"<li><span style='color:#777777;text-decoration:underline;' onmouseover=\"return overlib('Please login to access additional features.', CAPTION, 'Login');\" onmouseout=\"return nd();\">Organize</span></li>\n" +
							"<li><span style='color:#777777;text-decoration:underline;' onmouseover=\"return overlib('Please login to access additional features.', CAPTION, 'Login');\" onmouseout=\"return nd();\">Import</span></li>\n" +
							"<li><span style='color:#777777;text-decoration:underline;' onmouseover=\"return overlib('Please login to access additional features.', CAPTION, 'Login');\" onmouseout=\"return nd();\">Export</span></li>\n" +							
							"</ul>\n";
	
	}
	else {								
	myWorkspaceSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"manageLists.action\">Manage Lists</a></li>\n" +
							"<li><a href=\"manageWorkspace.action\">Organize</a></li>\n" +
							"<li><a href=\"importWorkspace.action\">Import</a></li>\n" +
							"<li><a href=\"exportWorkspace.action\">Export</a></li>\n" +							
							"</ul>\n";
	}							
	String s = request.getParameter("s")!=null ? (String) request.getParameter("s") : null;
	if(s != null)	{
		int sect = Integer.parseInt(s);	
		switch(sect)	{
			case 1:
				//1 is simple search
				simple = "<span>Simple Search</span>\n" + simpleSecondary;
				adv = "<a href=\"menu.action\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.action\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.action\">High Order Analysis</a>";
				list = "<a href=\"manageLists.action\">Manage Lists</a>";
				myWorkspace = "<a href=\"manageLists.action\">My Workspace</a>";
				download="<a href=\"downloadInit.action?method=setup\">Download</a>";
				break;
			case 2:
				//2 is adv
				simple = "<a href=\"home.action\">Simple Search</a>";
				adv = "<span>Advanced Search</span>\n" + advSecondary;
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.action\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.action\">High Order Analysis</a>";
				list = "<a href=\"manageLists.action\">Manage Lists</a>";
				myWorkspace = "<a href=\"manageLists.action\">My Workspace</a>";
				download="<a href=\"downloadInit.action?method=setup\">Download</a>";
				break;
			case 3:
				//3 is view results
				simple = "<a href=\"home.action\">Simple Search</a>";
				adv = "<a href=\"menu.action\">Advanced Search</a>";
				viewResults = "<span id=\"inboxStatus\">View Results&nbsp;&nbsp;</span>\n" + resultSecondary;
				analysis = "<a href=\"analysisHome.action\">High Order Analysis</a>";
				list = "<a href=\"manageLists.action\">Manage Lists</a>";
				myWorkspace = "<a href=\"manageLists.action\">My Workspace</a>";
				download="<a href=\"downloadInit.action?method=setup\">Download</a>";
				break;
			case 4:
				//4 is high order analysis
				simple = "<a href=\"home.action\">Simple Search</a>";
				adv = "<a href=\"menu.action\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.action\">View Results&nbsp;&nbsp;</a>";
				analysis = "<span>High Order Analysis</span>\n" + analysisSecondary;
				list = "<a href=\"manageLists.action\">Manage Lists</a>";
				myWorkspace = "<a href=\"manageLists.action\">My Workspace</a>";
				download="<a href=\"downloadInit.action?method=setup\">Download</a>";
				break;
			case 5:
				//5 is list mgr
				simple = "<a href=\"home.action\">Simple Search</a>";
				adv = "<a href=\"menu.action\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.action\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.action\">High Order Analysis</a>";
				list = "<span>Manage Lists</span>\n";
				myWorkspace = "<a href=\"manageLists.action\">My Workspace</a>";
				download="<a href=\"downloadInit.action?method=setup\">Download</a>";
				break;
			case 6: //download
				simple = "<a href=\"home.action\">Simple Search</a>";
				adv = "<a href=\"menu.action\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.action\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.action\">High Order Analysis</a>";
				list = "<a href=\"manageLists.action\">Manage Lists</a>";
				myWorkspace = "<a href=\"manageLists.action\">My Workspace</a>";
				download="<span>Download</span>\n";
				break;
			case 7: //myworkspace
				simple = "<a href=\"home.action\">Simple Search</a>";
				adv = "<a href=\"menu.action\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.action\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.action\">High Order Analysis</a>";
				list = "<a href=\"manageLists.action\">Manage Lists</a>";
				myWorkspace="<span>My Workspace</span>\n" + myWorkspaceSecondary;
				download="<a href=\"downloadInit.action?method=setup\">Download</a>";
				break;
			case 0:
			default:
				simple = "<a href=\"home.action\">Simple Search</a>";
				adv = "<a href=\"menu.action\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.action\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.action\">High Order Analysis</a>";
				list = "<a href=\"manageLists.action\">Manage Lists</a>";
				myWorkspace = "<a href=\"manageLists.action\">My Workspace</a>";
				download="<a href=\"downloadInit.action?method=setup\">Download</a>";
				break;
		}
	
%>
<div id="header">
	<ul id="primary">
		<li><%= simple %></li>
		<li><%= adv %></li>
		<li><%= analysis %></li>
		<li><%= viewResults %></li>
		<li><%= myWorkspace %></li>
		<li><%= download %></li>
	</ul>
</div>
<%
}
>>>>>>> b0280c2492ec02403cca549090d5127dac3898cb
%>