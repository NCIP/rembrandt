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

	QueryCollection queryCollection = (QueryCollection) (session.getAttribute(NautilusConstants.QUERY_KEY));

	CompoundQuery myCompoundQuery = queryCollection.getCompoundQuery();


	if(queryCollection != null)	{
		out.println(CSVGenerator.displayReport(queryCollection, false));
	}
	else
		out.println("QueryCollection is NULL");
		
%>