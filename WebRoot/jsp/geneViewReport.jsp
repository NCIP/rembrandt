<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="
gov.nih.nci.nautilus.ui.helper.ReportGeneratorHelper,
gov.nih.nci.nautilus.ui.bean.ReportBean,
gov.nih.nci.nautilus.ui.bean.SessionQueryBag,
gov.nih.nci.nautilus.constants.NautilusConstants"
%>

<%
	ReportBean reportBean = (ReportBean)request.getAttribute(NautilusConstants.REPORT_BEAN);
	ReportGeneratorHelper.renderReport(reportBean,null,out);
%>