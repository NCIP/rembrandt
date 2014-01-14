<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<%@ page import="java.util.*,
	 gov.nih.nci.rembrandt.dto.query.*,
	 gov.nih.nci.caintegrator.dto.query.*,
	 gov.nih.nci.rembrandt.util.RembrandtConstants,
	 org.apache.log4j.Logger,
	 gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
	 gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,
	 gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %> 


<%
Logger logger = Logger.getLogger(RembrandtConstants.JSP_LOGGER);
RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
int geQueryNum = 0;
String geQueryString = "0";
int cpQueryNum = 0;
String cpQueryString = "0";
int cghQueryNum = 0;
String cghQueryString = "0";
String sessionId = request.getSession().getId();
SessionQueryBag queryCollection = presentationTierCache.getSessionQueryBag(sessionId);
request.getSession().setAttribute("currentPage", "0");
if(queryCollection == null){
   logger.debug("its null");
}else{
 	Collection queryColl = queryCollection.getQueries();
 	Iterator i = queryColl.iterator();
 	
 	while (i.hasNext()){
 		Query query = (Query)i.next();
 		if(query instanceof GeneExpressionQuery){
		   logger.debug("this is a gene expression query in the collection");
 		   geQueryNum++;
 		   geQueryString = Integer.toString(geQueryNum);
 		}
 		if(query instanceof ComparativeGenomicQuery){
 		   logger.debug("this is a Comparative Genomic query in the collection");
 	 	   cpQueryNum++;
 	 	   cpQueryString = Integer.toString(cpQueryNum);
 	 	}
 		if(query instanceof ClinicalDataQuery){
 			logger.debug("this is a Clinical Data query in the collection");
 	 		cghQueryNum++;
 	 		cghQueryString = Integer.toString(cghQueryNum);
 	 		}
 	}
 }
%>
<br clear="both"/>
<table border="0" cellspacing="4" cellpadding="3" summary="This table is used to format page content">
  <tr><th></th></tr>	
  <tr><td>
  
  <fieldset style="width:500px;">
		<legend>Add to query:</legend>
		<s:actionerror />
		
		  <app:cshelp topic="Advanced_overview" /><br clear="left"/>
		
		 
			<table border="0" cellpadding="3" cellspacing="3" summary="This table is used to format page content">
			<tr><th></th></tr>
			 
			<tr><td>
			<s:form action="geSetup" namespace="/" theme="simple"> 
			 <s:submit value="Gene Expression Analysis" style="width:200px;margin-bottom: 5px;" />
			  &nbsp; <b class="message">- (<% out.write(geQueryString); %>) Gene Exp. Analysis Queries</b>
			</s:form>
			</td></tr>
			    
			 <tr><td> 
			     <s:form action="comparitivegenomicSetup" method="post" namespace="/" theme="simple">
			     <s:submit value="Copy Number Data Analysis" style="width:200px;margin-bottom: 5px;"/>
			  		&nbsp; <b class="message">- (<% out.write(cpQueryString); %>) Copy Number Data Queries</b>
			       
			     </s:form>
			</td></tr>		
			<tr><td>
			<s:form action="clinicalSetup" method="post" namespace="/" theme="simple">
			     <s:submit value="Clinical Study Analysis" style="width:200px;margin-bottom: 5px;"/>
			  		&nbsp; <b class="message">- (<% out.write(cghQueryString); %>) Clinical Study Analysis Queries</b>
			       
			     </s:form>
			</td></tr>
					
			 
			</table>
			
	</fieldset>
	</td></tr>
	<tr><td>
	<%
	if(queryCollection == null){%>
	  &nbsp;
	<% }
	  else{%>
		<strong> I am satisfied with my query and would like to finalize:</strong><br/>
		<blockquote>
			<input type="button" class="xbutton" value="Finalize Query" onclick="location.href='refinecheck.action'">
		</blockquote>
	 <%}%>
	</td></tr>
	<tr><td>
		&nbsp;
	</td></tr>
</table>

