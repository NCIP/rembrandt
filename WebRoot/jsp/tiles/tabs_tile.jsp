<% 
	//default settings for tabs
	String simple = "";
	String adv = "";
	String viewResults = "";
	String secondary = "";
	String advSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"menu.do\">Advanced Search Home</a></li>\n" +
							//"<li><a href=\"menu.do\">Build Query</a></li>\n" +
							"<li><a href=\"refinecheck.do\">Refine Query</a></li>\n" +
							"</ul>\n";
							
	String simpleSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"home.do\">Gene Search Home</a></li>\n" +
							"</ul>\n";
							
	String s = (String) request.getParameter("s");
	if(s != null)	{
		int sect = Integer.parseInt(s);	
		switch(sect)	{
			case 1:
				//1 is simple search
				simple = "<span>Simple Search</span>\n" + simpleSecondary;
				adv = "<a href=\"menu.do\">Advanced Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				break;
			case 2:
				//2 is adv
				simple = "<a href=\"home.do\">Simple Search</a>";
				adv = "<span>Advanced Search</span>\n" + advSecondary;
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				break;
			case 3:
				//3 is view results
				simple = "<a href=\"home.do\">Simple Search</a>";
				adv = "<a href=\"menu.do\">Advanced Search</a>";
				viewResults = "<span>View Results&nbsp;&nbsp;</span>";
				break;
			default:
				simple = "<span>Simple Search</span>\n" + simpleSecondary;
				adv = "<a href=\"menu.do\">Advanced Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				break;
		}
	}
%>
<div id="header">
	<ul id="primary">
		<li><%= simple %></li>
		<li><%= adv %></li>
		<li><%= viewResults %></a></li>
	</ul>
</div>