<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="
gov.nih.nci.nautilus.ui.helper.ReportGeneratorHelper,
gov.nih.nci.nautilus.ui.bean.ReportBean,
gov.nih.nci.nautilus.ui.bean.SessionQueryBag,
gov.nih.nci.nautilus.constants.NautilusConstants"
%>
<span id="spnLoading"  style="display:inline; width:500; text-align:center;" >
	<br><Br>
	<img src="images/statusBar2.gif">
	<br>Loading...please wait<br>
</span>
<%
response.flushBuffer();
	ReportBean reportBean = (ReportBean)request.getAttribute(NautilusConstants.REPORT_BEAN);
	ReportGeneratorHelper.renderReport(reportBean,null,out);
//if spnLoading is still visible at this point, an error has occured i think, show it
%>

