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
gov.nih.nci.nautilus.constants.Constants,
java.text.DecimalFormat,
java.util.*,
gov.nih.nci.nautilus.ui.ReportGenerator" %>

<% /* dont need all these imports really */ %>

<html>
<head>
	<title>Generated Report</title>
	<style>
	body { font-family:arial; }
	Td {
		font-size: 10px;
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
	
	</style>
</head>
<body>
<%

//	String theColors[] = {"5C73B7", "B1BCDD" };
	
//	 String theColors[] = {"5C73B7", "B1BCDD", "DDD2B1", "8697CA", "B7A15C", "CFD4E6", "404F80", "5C71B5" };

//	 String theColors[] = {"CFD4E6", "E6DCCF", "E5E6CF", "DFCFE6" };
	 
	 String theColors[] = {"8E95C2", "F2F3F8", "9DA8CD", "E1E4EF", "AEB7D5", "BFC6DE", "D0D5E7"  };
	

	QueryCollection queryCollection = (QueryCollection) (session.getAttribute(Constants.QUERY_KEY));

// out.println("Query: " + queryCollection.toString() + "<br><br>");

	if(queryCollection != null)
		out.println(ReportGenerator.displayReport(queryCollection, theColors, false));
	else
		out.println("QueryCollection is NULL");
%>
</body>
</html>