<%@ page language="java" %>
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
	
	</style>
	<script language="javascript" >
		function hideLoadingMessage(){
			document.getElementById('spnLoading').style.display = "none" ;
		}
	</script>
	
</head>
<body>
<div style="background-color: #ffffff"><img src="images/smallHead.jpg"></div>
<span id="spnLoading"  style="display:inline; width:500; text-align:center;" >
	<br><Br>
	<img src="images/statusBar2.gif">
	<br>Loading...please wait<br>
</span>


<Br>
<a name="top"></a>
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


if(mode == null)	{
  System.out.println("do a regular report");
  QueryCollection queryCollection = null;
  if(request.getAttribute(NautilusConstants.QUERY_KEY)==null)	{
	links = "<a href=\"jsp/geneViewReportCSV.jsp\">[Download this report for Excel]</a> | <a href=\"javascript:void(window.print())\">[Print Report]</a> | <a href=\"#queryInfo\">[Query Info]</a> | <a href=\"menu.do\">[Back to Menu]</a>\n";	
    queryCollection = (QueryCollection) (session.getAttribute(NautilusConstants.QUERY_KEY));
  }
  else	{
    queryCollection = (QueryCollection)(request.getAttribute(NautilusConstants.QUERY_KEY));
    links = "<a href=\"jsp/geneViewReportCSV.jsp\">[Download this report for Excel]</a> | <a href=\"javascript:window.close()\">[Close Window]</a> | <a href=\"javascript:void(window.print())\">[Print Report]</a> | <a href=\"#queryInfo\">[Query Info]</a>\n";
    if(queryCollection != null)
	    session.setAttribute(NautilusConstants.QUERY_KEY, queryCollection);
  }

	CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();

	if(queryCollection != null)	{
		out.println(ReportGenerator.displayReport(queryCollection, theColors, false, request, links));
		request.setAttribute(NautilusConstants.QUERY_KEY,queryCollection);
	}
	else
		out.println("QueryCollection is NULL");
		
 //   out.println("<Br><Br><Br><a name=\"queryInfo\"></a>\n");	
 

/*
	if(!myCompoundQuery.toString().equals(""))	{
		out.println("<B>Compound Query:</b> " + myCompoundQuery.toString() + "<br><br>");
	}
	else	{
		out.println("<B>Single Query:</b> " + queryCollection.getQueryNames() + "<br><br>");
	}

 	String  query = "";	
	int j = 0;	
	String queryKey = null;
	
	if(queryCollection != null){
			  
			//  out.println("<span class=\"queriesList\">\n");   
			      Collection queryColl = queryCollection.getQueries();
				  Collection queryKeys = queryCollection.getQueryNames();
				  
				  Iterator i = queryColl.iterator();
				  
				  out.println("<b>Queries:</b><br>\n");
				  while (i.hasNext()) { 
				     j++;
				     query =i.next().toString();
					 	
					 Iterator iter = queryKeys.iterator();
				     while(iter.hasNext()){
				        queryKey = (String)iter.next();
						String queryName = queryCollection.getQuery(queryKey).toString();
						if(query.equalsIgnoreCase(queryName)){					   
						   break;
						  }
					   }
					   out.println("<fieldset class=\"q\">");
					   out.println(query);
					   out.println("</fieldset>\n");
			       }	
			       out.println("<Br><Br>\n");
	}
	out.println("<a href=\"#top\">top</a>\n");
*/
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