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
				el.innerHTML += "<img id='statusImg' src='images/circle.gif' border='0'/>";
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
<script type='text/javascript' src='/rembrandt/dwr/interface/Inbox.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/util.js'></script>

<span id="statusMsg"><br/></span>

<% 
	//default settings for tabs
	String simple = "";
	String adv = "";
	String viewResults = "";
	String analysis = "";
	String secondary = "";
	String advSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"menu.do\">Advanced Search Home</a></li>\n" +
							//"<li><a href=\"menu.do\">Build Query</a></li>\n" +
							"<li><a href=\"refinecheck.do\">Refine Query</a></li>\n" +
							"</ul>\n";
	String resultsSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"viewResults.do\">View Findings</a></li>\n" +
							"<li><a href=\"#\">Managed Saved Lists</a></li>\n" +
							//"<li><a href=\"#\">Upload Lists</a></li>\n" +
							"</ul>\n";				
	String simpleSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"home.do\">Simple Search Home</a></li>\n" +
							"</ul>\n";
	String analysisSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"analysisHome.do\">Analysis Home</a></li>\n" +
							"</ul>\n";
							
	String s = (String) request.getParameter("s");
	if(s != null)	{
		int sect = Integer.parseInt(s);	
		switch(sect)	{
			case 1:
				//1 is simple search
				simple = "<span>Simple Search</span>\n" + simpleSecondary;
				adv = "<a href=\"menu.do\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				break;
			case 2:
				//2 is adv
				simple = "<a href=\"home.do\">Simple Search</a>";
				adv = "<span>Advanced Search</span>\n" + advSecondary;
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				break;
			case 3:
				//3 is view results
				simple = "<a href=\"home.do\">Simple Search</a>";
				adv = "<a href=\"menu.do\">Advanced Search</a>";
				viewResults = "<span id=\"inboxStatus\">View Results&nbsp;&nbsp;</span>\n";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				break;
			case 4:
				//4 is high order analysis
				simple = "<a href=\"home.do\">Simple Search</a>";
				adv = "<a href=\"menu.do\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<span>High Order Analysis</span>\n" + analysisSecondary;
				break;
			default:
				simple = "<span>Simple Search</span>\n" + simpleSecondary;
				adv = "<a href=\"menu.do\">Advanced Search</a>";
				viewResults = "<a id=\"inboxStatus\" href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				break;
		}
	}
%>
<div id="header">
	<ul id="primary">
		<li><%= simple %></li>
		<li><%= adv %></li>
		<li><%= viewResults %></li>
		<li><%= analysis %></li>
	</ul>
</div>
