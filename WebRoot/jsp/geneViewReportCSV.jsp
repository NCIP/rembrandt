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
gov.nih.nci.nautilus.ui.CSVGenerator,
 gov.nih.nci.nautilus.ui.helper.SessionQueryBag" %>

<%	
	//generate the CSV
	response.setContentType("application/csv");
	response.setHeader("Content-Disposition", "attachment; filename=report.csv");
	if(session.getAttribute("csv") != null)	{
	// request came from a transitional report
	ResultsContainer resultsContainer = (ResultsContainer) session.getAttribute("csv");
	String mode =  (String) session.getAttribute("mode");
	
	if(mode.equals("gene"))
		out.println(CSVGenerator.geneExprSampleView(resultsContainer));
	else if( mode.equals("copy"))
		out.println(CSVGenerator.copyNumberSampleView(resultsContainer));
	else if(mode.equals("ss"))
		out.println(CSVGenerator.clinicalSampleView(resultsContainer));
	else
		out.println("Error somewhere");
		
	session.removeAttribute("csv");
	session.removeAttribute("mode");
}
else	{

	//go the query Collection route
	SessionQueryBag queryCollection = null;
	if(request.getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY)==null)	{
		if(session.getAttribute("tmp") != null)	{
  			queryCollection = (SessionQueryBag)(session.getAttribute("tmp"));
			session.removeAttribute("tmp");
  		}
  		else	{
    		queryCollection = (SessionQueryBag) (session.getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY));
    	}
  	}
  	else	{
	    queryCollection = (SessionQueryBag)(request.getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY));
	}
 
//	CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();

	if(queryCollection != null)	{
		out.println(CSVGenerator.displayReport(queryCollection, false));
	}
	else
		out.println("QueryCollection is NULL");
}
%>