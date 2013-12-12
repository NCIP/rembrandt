<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page buffer="none" %>
<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*, gov.nih.nci.rembrandt.web.helper.*,gov.nih.nci.rembrandt.queryservice.QueryManager,gov.nih.nci.caintegrator.dto.query.QueryType,
gov.nih.nci.rembrandt.web.factory.*, gov.nih.nci.rembrandt.web.bean.*, org.dom4j.Document, gov.nih.nci.rembrandt.util.*,java.util.Collection,gov.nih.nci.rembrandt.dto.query.*" %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory, gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierStoredData,org.apache.commons.beanutils.BeanUtils" %>
<%@ page import="gov.nih.nci.rembrandt.web.bean.*,gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,java.util.*,java.lang.*,gov.nih.nci.caintegrator.dto.view.ViewFactory,gov.nih.nci.caintegrator.dto.view.ViewType,gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierSampleInfo" %>


	<%
	RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
	String sampleGroup = request.getParameter("sampleGroup");
	String dataName = request.getParameter("dataName");
	String sessionId = request.getSession().getId();
	Map params = new HashMap(); 
	params.put("showSampleSelect","false");
	
	//Retrieve the cache data about the plots
	KaplanMeierStoredData cacheData = (KaplanMeierStoredData)presentationTierCache.getSessionGraphingData(sessionId,dataName);
		
		//if the data is there grab the param from the request to see which sample Group they want to view: Up, Down or intermediate
		if(cacheData !=null){
			Collection samples = new ArrayList();
			if(sampleGroup.equals("up")){
			  samples = cacheData.getUpSamples();
			 }
			 else if (sampleGroup.equals("down")){
			  samples = cacheData.getDownSamples();
			 }
			 else if (sampleGroup.equals("inter")){
			  samples = cacheData.getIntSamples();
			 }
			 else if (sampleGroup.equals("list1")){
			  samples = cacheData.getSampleList1();
			 }
			 else if (sampleGroup.equals("list2")){
			  samples = cacheData.getSampleList2();
			 }
			Collection samplesList=new ArrayList();
			// if there are samples, iterate over the collection and cast them to info objects so
			// that we can get the sampleIDs, and add them into a new arraylist
			if(!samples.isEmpty()){
					for (Iterator it=samples.iterator(); it.hasNext(); ) {
       					 Object element = it.next();
   					     KaplanMeierSampleInfo kaplanMeierSampleInfo = (KaplanMeierSampleInfo)element;
   					     samplesList.add(kaplanMeierSampleInfo.getSampleID());
   					     
					}
					//create a compund query using an empty clinical query
					ClinicalDataQuery clinicalDataQuery = (ClinicalDataQuery) QueryManager.createQuery(QueryType.CLINICAL_DATA_QUERY_TYPE);
		    		clinicalDataQuery.setQueryName("clinical");
		    		CompoundQuery cquery = new CompoundQuery(clinicalDataQuery);		
					cquery.setAssociatedView(ViewFactory.newView(ViewType.CLINICAL_VIEW));
					cquery.setQueryName(presentationTierCache.getTempReportName(request.getSession().getId()));
					cquery.setSessionId(sessionId);		    
		    		String[] samplesArray = (String[]) samplesList.toArray(new String[0]);
					//generate the reportBean with the reportXML using the compQury and samples Array
					//JB: Changing this call to support the limitation to the number of samples allowed
					//for clinical reports.
					ReportGeneratorHelper reportGeneratorHelper = new ReportGeneratorHelper(cquery, samplesArray,false);
					//ReportGeneratorHelper reportGeneratorHelper = new ReportGeneratorHelper(cquery, samplesArray,false,true);
					ReportBean clincalReportBean = presentationTierCache.getReportBean(sessionId,cquery.getQueryName());
						if(clincalReportBean!=null){
							Document reportXML = clincalReportBean.getReportXML();
							String report = reportGeneratorHelper.renderReport(params,reportXML,"report.xsl");
							
							//JB:  This the extension of a previous hack (see ReportGeneratorActionrunGeneViewReport() 
							//by RCL).  We need to make sure the uery Deatils are available on the initial page of the
							//clinical report.
				    		StringBuffer sb = new StringBuffer();
				    		sb.append(report);
				    		if(cquery != null) {			
				    			String theQuery  =  cquery.toString();
				    	 		sb.append("<br><a name=\'queryInfo\'></a>Query: "+theQuery);
				    	 		sb.append("<table>");
				    	 		sb.append("<tr>");
				    	 		//Query[] queries = cquery.getAssociatiedQueries();
				    	 		Query[] queries = clincalReportBean.getAssociatedQuery().getAssociatiedQueries();
				    	 		for(int i = 0; i<queries.length; i++){
				    	 			sb.append("<td>");
				    	 			sb.append(queries[i]);
				    	 			sb.append("</td>");
				    	 		}
				    	 		sb.append("</tr>");
				    	 		sb.append("</table>");
				    		}
				    		
				    		/*
				    		String noHTMLString = sb.toString();
				    		//noHTMLString = noHTMLString.replaceAll("\\<.*?\\>","");
				    		noHTMLString = noHTMLString.replaceAll("<", "{");
				    		noHTMLString = noHTMLString.replaceAll(">", "}");
				    		noHTMLString = noHTMLString.replaceAll("&nbsp;", " ");
							*/
							
							//return the string buffer and print
							//out.write(report);
							out.write(sb.toString());
						}
						else{
						out.println("No Records Available for this query");
						}
			}
			else{
			out.println("No Samples Available for this query");
            }
			
		}
		
	%>		
	
	
	
	


