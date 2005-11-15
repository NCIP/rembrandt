<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*, gov.nih.nci.rembrandt.web.helper.*,
gov.nih.nci.rembrandt.web.factory.*, gov.nih.nci.rembrandt.web.bean.*, org.dom4j.Document, gov.nih.nci.rembrandt.util.*" %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.rembrandt.cache.*" %>

<html>
	<head>
		<script language="JavaScript" type="text/javascript" src="js/overlib.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
		<script language="JavaScript" type="text/javascript" src="XSL/js.js"></script> 
		<LINK href="XSL/css.css" rel="stylesheet" type="text/css" />
	</head>
<body>
	<div id="reportContainer" style="">&nbsp;</div>

<script type='text/javascript' src='/rembrandt/dwr/interface/DynamicReport.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>

<%

	String key = "frb_test";
	if(request.getParameter("key")!=null)
		key = (String) request.getParameter("key");
	
	String xhtml = "nada";
	if(session.getAttribute(key+"_xhtml")!=null)	{
		xhtml = (String) session.getAttribute(key+"_xhtml");
		out.println(xhtml);
		session.removeAttribute(key+"_xhtml");
	}
	else	{


	%>
	<div id="imgContainer" style="display:block">
		<center><img src="images/circleStatusGray200.gif" /></center>
	</div>
	
	
	<%
	PresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
	BusinessTierCache btc = ApplicationFactory.getBusinessTierCache();

/*	
	//only generate XML if its not already cached...leave off for debug
	//if(ptc.getObjectFromSessionCache(session.getId(), key) == null)	{
		Object o = btc.getObjectFromSessionCache(session.getId(), key);
		Finding finding = (Finding) o; 
		//generate the XML and cached it
		ReportGeneratorHelper.generateReportXML(finding);
	//}
	Object ob = ptc.getObjectFromSessionCache(session.getId(), key);
	if(ob != null && ob instanceof FindingReportBean)	{
		try	{
			FindingReportBean frb = (FindingReportBean) ob;
			Document reportXML = (Document) frb.getXmlDoc();
		
			ReportGeneratorHelper.renderReport(request, reportXML,RembrandtConstants.DEFAULT_XSLT_FILENAME,out);
		}
		catch(Exception e)	{
			out.println("no worky");
		}
	}
	else	{
		out.println("this no worky");
	}
*/
%>


<script language="javascript">

	var reportC = document.getElementById("reportContainer");
	var imgC = document.getElementById("imgContainer");
	
	function A_getReport(key)	{
		DynamicReport.generateDynamicReport(key, A_getReport_cb);
	}
	
	function A_getReport_cb(html)	{
		alert(html.length);
		imgC.style.display = "none";
		location.replace("/rembrandt/testReport.do?key=<%=key%>");
	}
	
	setTimeout("A_getReport('<%=key%>')", 0500);
	
</script>

<% } %>
</body>
</html>

