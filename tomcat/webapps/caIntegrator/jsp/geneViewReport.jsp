<%@ page language="java" %>
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
</head>
<body>
<div style="background-color: #ffffff"><img src="images/smallHead.jpg"></div>
<Br>
<a name="top"></a>
<%
	System.out.println("sample we want: " + request.getParameter("s"));

	String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };

String links = "";
String sample = request.getParameter("s");

//get the results container from the session with sample as key
if(session.getAttribute(sample) == null)
{
  System.out.println("not transitional report");	
  QueryCollection queryCollection = null;
  if(request.getAttribute(NautilusConstants.QUERY_KEY)==null)	{
	links = "<a href=\"jsp/geneViewReportCSV.jsp\">[Download this report for Excel]</a> | <a href=\"javascript:void(window.print())\">[Print Report]</a> | <a href=\"menu.do\">[Back to Menu]</a>\n";	
    queryCollection = (QueryCollection) (session.getAttribute(NautilusConstants.QUERY_KEY));
  }
  else	{
    queryCollection = (QueryCollection)(request.getAttribute(NautilusConstants.QUERY_KEY));
    links = "<a href=\"javascript:window.close()\">[Close Window]</a> | <a href=\"javascript:void(window.print())\">[Print Report]</a>\n";
  }

	CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();

	if(queryCollection != null)	{
		out.println(ReportGenerator.displayReport(queryCollection, theColors, false, request, links));
		request.setAttribute(NautilusConstants.QUERY_KEY,queryCollection);
	}
	else
		out.println("QueryCollection is NULL");
		

    out.println("<Br><Br><Br><a name=\"queryInfo\"></a>\n");	
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
	System.out.println("Doing transitional");
	//process the transitional report
	ResultsContainer resultsContainer = (ResultsContainer) session.getAttribute(sample);
	String mode =  (String) request.getParameter("report");
	if(mode.equals("gene"))
		out.println(ReportGenerator.geneExprSampleView(resultsContainer, theColors));
	else if( mode.equals("copy"))
		out.println(ReportGenerator.copyNumberSampleView(resultsContainer, theColors));
	else
		out.println("Error somewhere");
		
	session.setAttribute("csv", resultsContainer);
	session.setAttribute("mode", mode);
	System.out.println("set things in session");
	
	//session.removeAttribute("resultsContainer");
	session.removeAttribute("report");
}


%>
</body>
</html>