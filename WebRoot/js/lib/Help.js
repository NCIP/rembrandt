var Help = {
	url : "helpDocs/REMBRANDT_Online_Help/index.html?single=true&context=REMBRANDT_Online_Help&topic=",
	popHelp: function(topic) {
		window.open (Help.url+topic, "Help", "status,scrollbars,resizable,width=800,height=500");  
		//use the below if you want the "always on top" feature, most dont like it
		//window.open (Help.url+topic, "Help", "alwaysRaised,dependent,status,scrollbars,resizable,width=800,height=500");  
		//alert(Help.url+topic);
	},
	insertHelp: function(topic)	{
		var ex = arguments[1] ? arguments[1] : "";
		var exst = arguments[2] ? arguments[2] : "";
		var pth = arguments[3] ? arguments[3] : "";
		var htm = "<img "+ ex + " style=\"cursor:pointer;border:0px;"+ exst + "\" src=\""+pth+"images/help.png\" alt=\"help\" onclick=\"Help.popHelp(\'"+topic+"\');\" />";
		document.write(htm);
	},
	popHelpMain: function(topic) {
		//var _url = "helpDocs/REMBRANDT_Online_Help/index.html?single=false&context=REMBRANDT_Online_Help&topic=";
		var _url = "helpDocs/REMBRANDT_Online_Help/WelcometoREMBRANDTOnlineHelp.1.1.html";
		window.open (_url+topic, "Help", "status,scrollbars,resizable,width=800,height=500");  
		//use the below if you want the "always on top" feature, most dont like it		
		window.open (_url+topic, "Help", "alwaysRaised,dependent,status,scrollbars,resizable,width=800,height=500");  
		//alert(Help.url+topic);
	},
	convertHelp: function(topic)	{
		var ex = arguments[1] ? arguments[1] : "";
		var exst = arguments[2] ? arguments[2] : "";
		var ta = "";
		switch(topic)	{
			case "geneexpression":
				ta = "Advanced_gene_expression";
				break;
			case "comparitivegenomic":
				ta = "Advanced_copy_number";
				break;
				
			case "clinical":
				ta = "Advanced_clinical_data";
				break;
			case "classcomparison":
				ta = "Class_comparison";
				break;
			case "principalcomponent":
				ta = "PCA_analysis";
				break;
			case "hierarchicalclustering":
				ta = "HCA_analysis";
				break;
			default:
				break;
		
		}
		Help.insertHelp(ta, ex, exst);
	
	}
}