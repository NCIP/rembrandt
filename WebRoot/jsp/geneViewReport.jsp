<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="
gov.nih.nci.nautilus.ui.helper.ReportGeneratorHelper,
gov.nih.nci.nautilus.ui.bean.SessionQueryBag,
gov.nih.nci.nautilus.constants.NautilusConstants,
org.dom4j.Document"
%>
<span id="spnLoading"  style="display:inline; width:500; text-align:center;" >
	<br><Br>
	<img src="images/statusBar2.gif">
	<br>Loading...please wait<br>
</span>
<%
	response.flushBuffer();	
	try	{
	Document reportXML = (Document)request.getAttribute(NautilusConstants.REPORT_XML);
	ReportGeneratorHelper.renderReport(request, reportXML,(String)request.getAttribute(NautilusConstants.XSLT_FILE_NAME),out);
	}
	catch(Exception e)	{
	// add some crude error checking here, make this message better later
%>
<LINK href="css/bigStyle.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script>
<script language="javascript">
	if(document.getElementById('spnLoading') != null &&	document.getElementById('spnLoading').style.display != "none")	{
		hideLoadingMessage();
		document.write("<h3 style=\"text-align:center; margin-top:200px;\">There was an error generating your report.  Please try again later. <br><a href=\"javascript:window.close()\">Close</a></h3>");
	}
</script>
<% } %>
