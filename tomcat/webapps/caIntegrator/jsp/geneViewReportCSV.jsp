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
gov.nih.nci.nautilus.ui.CSVGenerator" %>

<%
	//generate the CSV
	response.setContentType("application/csv");
	response.setHeader("Content-Disposition", "attachment; filename=report.csv");

//	QueryCollection queryCollection = (QueryCollection) (session.getAttribute(NautilusConstants.QUERY_KEY));

if(session.getAttribute("csv") != null)	{
	// request came from a transitional report
	ResultsContainer resultsContainer = (ResultsContainer) session.getAttribute("csv");
	String mode =  (String) session.getAttribute("mode");
	
	if(mode.equals("gene"))
		out.println(CSVGenerator.geneExprSampleView(resultsContainer));
	else if( mode.equals("copy"))
		out.println(CSVGenerator.copyNumberSampleView(resultsContainer));
	else
		out.println("Error somewhere");
		
	session.removeAttribute("csv");
	session.removeAttribute("mode");
}
else	{
System.out.println("no resultscontainer in request");
	//go the query Collection route
	QueryCollection queryCollection = null;
	if(request.getAttribute(NautilusConstants.QUERY_KEY)==null){
		System.out.println("queryCollection is not in request");
    	queryCollection = (QueryCollection) (session.getAttribute(NautilusConstants.QUERY_KEY));
  	}else{
    	queryCollection = (QueryCollection)(request.getAttribute(NautilusConstants.QUERY_KEY));
  	}
	CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();


	if(queryCollection != null)	{
		out.println(CSVGenerator.displayReport(queryCollection, false));
	}
	else
		out.println("QueryCollection is NULL");
}	
%>