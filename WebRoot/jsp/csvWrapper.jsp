<%@ page language="java" %><%@ page buffer="none" %><%@ page import="
gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper,
gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
gov.nih.nci.rembrandt.util.RembrandtConstants,
org.dom4j.Document,org.dom4j.io.XMLWriter,org.dom4j.io.OutputFormat,
gov.nih.nci.caintegrator.service.findings.*, gov.nih.nci.rembrandt.web.helper.*,
gov.nih.nci.rembrandt.web.factory.*, gov.nih.nci.rembrandt.web.bean.*, 
org.dom4j.Document, gov.nih.nci.rembrandt.util.*,
gov.nih.nci.rembrandt.web.factory.ApplicationFactory,
gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,gov.nih.nci.rembrandt.web.xml.*,
java.util.HashMap, java.util.ArrayList" %><%

String key = request.getParameter("key")!=null ? (String) request.getParameter("key") : null;
	if(key != null)	{
		RembrandtPresentationTierCache ptc = ApplicationFactory.getPresentationTierCache();
		FindingReportBean frb = (FindingReportBean) ptc.getNonPersistableObjectFromSessionCache(session.getId(), key);
		if(frb!=null && frb.getFinding()!=null)	{
			Document xmlDocument = null;
			if(frb.getXmlDocCSV()!=null)
				xmlDocument = (Document)frb.getXmlDocCSV();
			else	{
				//generate the XML for CSV (w/annotations) and cache
				if(frb.getFinding() instanceof ClassComparisonFinding)	{
					ArrayList reporterIds = (ArrayList)request.getSession().getAttribute("tmpReporterList");
					xmlDocument = ClassComparisonReport.getReportXML(frb.getFinding(), new HashMap(), true, reporterIds);
				}
				else if(frb.getFinding() instanceof FTestFinding)	{
					xmlDocument = FTestReport.getReportXML(frb.getFinding(), new HashMap(), true);
				}
				//put frb back in cache
//				we don't want to save it in cache because from now on each time
//				the user could export a different set of list.
//				so, saving the csv in the cache is useless.				
//				frb.setXmlDocCSV(xmlDocument);
//				ptc.addNonPersistableToSessionCache(frb.getFinding().getSessionId(),frb.getFinding().getTaskId(), frb);
			}
			if(xmlDocument!=null)	{
				//generate the CSV
				response.setContentType("application/csv");
				response.setHeader("Content-Disposition", "attachment; filename=report.csv");
				try{	
					ReportGeneratorHelper.renderReport(request, xmlDocument,"csv.xsl",out);
				}
				catch(Exception e)	{
					out.println("Error Generating the report");
				}
			}
			else	{
				out.println("No Records Available for this query");
			}
		}
		else	{
			out.println("No Records Available for this query");
		}
	}
	else	{
		out.println("Error Generating the CSV.");
	}
%>