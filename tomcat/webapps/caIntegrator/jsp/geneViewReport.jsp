<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="
gov.nih.nci.nautilus.criteria.*,
gov.nih.nci.nautilus.de.*,
gov.nih.nci.nautilus.query.*,
gov.nih.nci.nautilus.view.*,
gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr,
gov.nih.nci.nautilus.resultset.*,
gov.nih.nci.nautilus.resultset.copynumber.*,
gov.nih.nci.nautilus.resultset.gene.*,
gov.nih.nci.nautilus.resultset.sample.*,
gov.nih.nci.nautilus.constants.NautilusConstants,
java.text.DecimalFormat,
java.util.*,
gov.nih.nci.nautilus.ui.ReportGenerator" %>

<% /* dont need all these imports really */ %>

<html>
<head>
	<title>Generated Report</title>
	<style>
	body { font-family:arial; font-size: 11px; margin-top: 0px}
	TABLE	{border: 0px; padding:0px; font-size:12px;}
	Td {
	
		font-size: 12px;
		background: #F2F2F2;
		padding: 4px; 
		border: 1px solid white;
		color: #000000;
		white-space:nowrap;
	
		}

	.geneSpacerStyle { 	height: 1px; 
						font-size: 0px; 
						border-top: 1px solid black; 
						border-left: 2px solid black;
						border-right: 2px solid black;
						border-bottom: 2px solid black;  
						padding: 0px; 
						/* background-color: #ffffff; */
					}
	 #header { font-weight: bold;
	 			font-size: 12px;
	 		}
	 		
	 b.otherBold	{ font-size: 11px; font-weight:normal; }
	
	.queriesList { width: 600px; padding: 5px; }
	.q { width:200px; height: 200px; overflow:auto; border: 1px solid black; padding: 3px; background: #F2F2F2;}
	.title { font-size: 16px; font-weight: bold; padding-bottom: 10px; font-family: tahoma }
	.rowCount { font-size:11px; padding:2px;}
	
	.fontClass { font-size: 10px; 
				font-family: verdana;
				color:#000000; 
				padding:1px; 
				border-top:0px; border-bottom:0px; border-right:0px; border-left:0px;
				white-space:normal;
				background-color:#e9e9e9; 
				margin: 0px;
				}
  	.capfontClass { font-size: 10px; 
  					font-family: verdana;
  					color:#ffffff; 
  					padding:1px; 
  					border-top:0px; border-bottom:0px; border-right:0px; border-left:0px;
  					white-space:normal;
  					background-color: #AB0303; 
  					margin: 0px;
  					}
  	.fgClass {font-size: 10px; 
  			font-family: verdana;
  			padding:1px; 
  			border-top:1px solid #AB0303;border-bottom:1px solid #AB0303;border-left:1px solid #AB0303;border-right:1px solid #AB0303;
  			margin: 0px;
  			white-space:normal;
  			background-color:;
  			}
  	.bgClass {font-size: 10px; 
  			font-family: verdana;
  			padding:1px; 
  			border-top:0px; border-bottom:0px; border-right:0px; border-left:0px; 
  			margin: 0px;
  			white-space:normal;
  			background-color:;
  			}
  	
	</style>
	<script language="javascript" >
	
	window.focus();
	
		function hideLoadingMessage(){
			document.getElementById('spnLoading').style.display = "none" ;
		}
		
		
		var message="Function Disabled!";
		
		///////////////////////////////////
		function clickIE4(){
			if (event.button==2){
				alert(message);
				return false;
			}
		}
		
		function clickNS4(e){
			if (document.layers||document.getElementById&&!document.all){
				if (e.which==2||e.which==3){
					alert(message);
					return false;
				}
			}
		}
		
		if (document.layers){
			document.captureEvents(Event.MOUSEDOWN);
			document.onmousedown=clickNS4;
		}
		else if (document.all&&!document.getElementById){
				document.onmousedown=clickIE4;
		}
		
		document.oncontextmenu=new Function("alert(message);return false")

	</script>
	<script type="text/javascript" src="js/caIntScript.js"></script>
	<script type="text/javascript" src="js/overlib.js"></script>
	<script type="text/javascript" src="js/overlib_hideform.js"></script>
</head>
<body onload="window.focus()">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div style="background-color: #ffffff"><img src="images/smallHead.jpg"></div>
<span id="spnLoading"  style="display:inline; width:500; text-align:center;" >
	<br><Br>
	<img src="images/statusBar2.gif">
	<br>Loading...please wait<br>
</span>


<Br>
<a name="top"></a>
<!--
<a href="javascript: spawn('help.jsp', 350, 500);">
<img src="images/helpIcon.jpg" align="right" border="0" onmouseover="return overlib('Click here for additional information about this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();">
</a>
-->
<%
response.flushBuffer();

boolean debug = false;
if(debug)	{
	System.out.println("session========================");
 	for (Enumeration e = session.getAttributeNames() ; e.hasMoreElements() ;) {
         System.out.println(e.nextElement());
     }
	System.out.println("request========================");
 	for (Enumeration e = request.getAttributeNames() ; e.hasMoreElements() ;) {
         System.out.println(e.nextElement());
     }
}
     
     
System.out.println("sample we want: " + request.getParameter("s"));

String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };

String links = "";

String mode = request.getParameter("report");

//clear this here
if(session.getAttribute("tmp") != null)
	session.removeAttribute("tmp");



if(mode == null)	{
  System.out.println("do a regular report");
  QueryCollection queryCollection = null;
  if(session.getAttribute(NautilusConstants.QUERY_KEY+"_tmp")==null)	{
	links = "<a href=\"jsp/geneViewReportCSV.jsp\">[Download this report for Excel]</a> | <a href=\"javascript:window.close()\">[Close Window]</a> | <a href=\"javascript:void(window.print())\">[Print Report]</a> | <a href=\"#queryInfo\">[Query Info]</a>\n";	
    queryCollection = (QueryCollection) (session.getAttribute(NautilusConstants.QUERY_KEY));
  }
  else	{
    queryCollection = (QueryCollection)(session.getAttribute(NautilusConstants.QUERY_KEY+"_tmp"));
    session.removeAttribute(NautilusConstants.QUERY_KEY+"_tmp");
    links = "<a href=\"jsp/geneViewReportCSV.jsp\">[Download this report for Excel]</a> | <a href=\"javascript:window.close()\">[Close Window]</a> | <a href=\"javascript:void(window.print())\">[Print Report]</a> | <a href=\"#queryInfo\">[Query Info]</a>\n";
    if(queryCollection != null)
	    session.setAttribute("tmp", queryCollection);
  }

	CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();

	if(queryCollection != null)	{
		out.println(ReportGenerator.displayReport(queryCollection, theColors, false, request, links));
		request.setAttribute(NautilusConstants.QUERY_KEY,queryCollection);
	}
	else
		out.println("QueryCollection is NULL");

	session.removeAttribute("csv");
	session.removeAttribute("mode");
}

else	{
 
	System.out.println("do a trans report");
	//we have a mode, process the transitional report
	ResultsContainer scv = null;
	DimensionalViewContainer dv = null;
	String s = (String) request.getParameter("s");
	dv = (DimensionalViewContainer) session.getAttribute("_dv");

	if(mode.equals("gene"))	{
		scv = null;
		if(dv!=null)	{
			//get the sample ID, snip of the _gene
			System.out.println(s.substring(0, s.length()-5));
			scv = dv.getGeneExprSingleViewResultsContainerForSample(s.substring(0, s.length()-5));

			if(scv!=null)	{
				out.println(ReportGenerator.geneExprSampleView(scv, theColors, request));
			}
			else
				out.println("gene scv null");
		}
		else
			out.println("gene Dv null");
	}
	else if( mode.equals("copy"))	{
		scv = null;
		if(dv!=null)	{
			//get the sample ID, snip of the _copy
			scv = dv.getCopyNumberSingleViewContainerForSample(s.substring(0, s.length()-5));

			if(scv!=null)	{
				out.println(ReportGenerator.copyNumberSampleView(scv, theColors, request));
			}
			else
				out.println("copy scv null");
		}
		else
			out.println("copy Dv null");
	}
	else if(mode.equals("ss"))	{
		scv = null;
		if(dv!=null)	{
			scv = dv.getSampleViewResultsContainerForSample(s);
			if(scv!=null)	{
				out.println(ReportGenerator.clinicalSampleView(scv, theColors, request));
			}
			else
				out.println("scv null");
		}
		else
			out.println("Dv null");
	}
	else
		out.println("Error with your report.  Please try again.");
		
	session.setAttribute("csv", scv);
	session.setAttribute("mode", mode);
	
	
//	session.removeAttribute("resultsContainer");
//	session.removeAttribute("report");

}


%>
<script language="javascript">
	hideLoadingMessage();
</script>
</body>
</html>