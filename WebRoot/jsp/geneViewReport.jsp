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
	Document reportXML = (Document)request.getAttribute(NautilusConstants.REPORT_XML);
	ReportGeneratorHelper.renderReport(request, reportXML,"report.xsl",out);
%>

