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
		padding: 5px;
		}
	</style>
</head>
<body>
<%
	QueryCollection queryCollection = (QueryCollection) (session.getAttribute(Constants.QUERY_KEY));

	if(queryCollection != null)
		out.println(ReportGenerator.displayReport(queryCollection, false));
	else
		out.println("QueryCollection is NULL");
%>
</body>
</html>