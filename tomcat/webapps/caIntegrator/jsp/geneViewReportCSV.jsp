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
gov.nih.nci.nautilus.ui.CSVGenerator" %>

<%
	//generate the CSV
	response.setContentType("application/csv");
	response.setHeader("Content-Disposition", "attachment; filename=report.csv");
	
	String theColors[] = { "B6C5F2","F2E3B5","DAE1F9","C4F2B5","819BE9", "E9CF81" };

	QueryCollection queryCollection = (QueryCollection) (session.getAttribute(Constants.QUERY_KEY));

	CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();


	if(queryCollection != null)	{
		out.println(CSVGenerator.displayReport(queryCollection, theColors, false));
	}
	else
		out.println("QueryCollection is NULL");
		
%>