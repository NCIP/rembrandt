<%@ page language="java" %><%@ taglib uri="/WEB-INF/app.tld" prefix="app" %><%@ page buffer="none" %><%@ page import="
gov.nih.nci.nautilus.ui.helper.ReportGeneratorHelper,
gov.nih.nci.nautilus.ui.bean.SessionQueryBag,
gov.nih.nci.nautilus.constants.NautilusConstants,
org.dom4j.Document,org.dom4j.io.XMLWriter,org.dom4j.io.OutputFormat"
%><%
String csv = "false";
if(request.getParameter("csv")!=null)
	csv = (String) request.getParameter("csv");

if(csv.equals("true"))	{
	//generate the CSV
	response.setContentType("application/csv");
	response.setHeader("Content-Disposition", "attachment; filename=report.csv");
	try{	
	Document reportXML = (Document)request.getAttribute(NautilusConstants.REPORT_XML);
	if(reportXML != null)
		ReportGeneratorHelper.renderReport(request, reportXML,"csv.xsl",out);
	else
		out.println("No Records Available for this query");
	}
	catch(Exception e)	{
		out.println("Error Generating the report");
	}
}
else	{ %>
	<span id="spnLoading"  style="display:inline; width:500; text-align:center; width:100%" >
		<br><p align="center" style="font: 14px arial bold">
		<img src="images/statusBar2.gif">
		<br>Loading...please wait<br>
		</p>
	</span>
<%
	response.flushBuffer();	
	try	{
		Document reportXML = (Document)request.getAttribute(NautilusConstants.REPORT_XML);
		
		if(reportXML!=null)
			ReportGeneratorHelper.renderReport(request, reportXML,(String)request.getAttribute(NautilusConstants.XSLT_FILE_NAME),out);
		else	{ 
		//we still need to know if this is due to no records in the result, or system failure
		//from here on down is not-good-code, will be cleaned up later
%>
			<LINK href="css/bigStyle.css" rel="stylesheet" type="text/css">
			<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script>
			<script language="javascript">
				if(document.getElementById('spnLoading') != null &&	document.getElementById('spnLoading').style.display != "none")	{
					hideLoadingMessage();
					document.write("<h3 style=\"text-align:center; margin-top:200px;\">No Records Returned.  Please modify the query. <br><a href=\"javascript:window.close()\">Close</a></h3>");
				}
			</script>
<%
		}
	}
	catch(Exception e)	{
	//maybe put this in a finally?
%>
		<LINK href="css/bigStyle.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script>
		<script language="javascript">
			if(document.getElementById('spnLoading') != null &&	document.getElementById('spnLoading').style.display != "none")	{
				hideLoadingMessage();
				document.write("<h3 style=\"text-align:center; margin-top:200px;\">There was an error generating your report.  Please try again later. <br><a href=\"javascript:window.close()\">Close</a></h3>");
			}
		</script>
<% 
	} 
}
%>
