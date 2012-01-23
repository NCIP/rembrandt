<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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
<table border="0" cellspacing="4" cellpadding="3">
  <tr><th></th></tr>	
  <tr><td>
  
  <fieldset style="width:500px;">
		<legend>Add to query:</legend>
		<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
		
		  <script type="text/javascript">Help.insertHelp("Advanced_overview", " align='right'", "padding:2px;");</script><br clear="left"/>
		
		
			<table border="0" cellpadding="3" cellspacing="3">
			<tr><th></th></tr>
			<tr><td>
			    <html:form action="/geneexpression">
			       <html:submit styleClass="xbutton" property="method" style="width:200px;margin-bottom: 5px;">
			          <bean:message key="GeneExpressionAction.setupButton"/>
			        </html:submit>
			        &nbsp; <b class="message">- (<% out.write(geQueryString); %>) Gene Exp. Analysis Queries</b></td></tr>
			     </html:form>
				
			  <tr><td> 
			     <html:form action="/comparitivegenomic">
			       <html:submit styleClass="xbutton" property="method" style="width:200px;margin-bottom: 5px;">
			          <bean:message key="ComparativeGenomicAction.setupButton"/>
			       </html:submit>
			        &nbsp; <b class="message">- (<% out.write(cpQueryString); %>) Copy Number Data Queries</b></td></tr>
			     </html:form>
					
			<tr><td>
				<html:form action="/clinical">
			       <html:submit styleClass="xbutton" property="method" style="width:200px;margin-bottom: 5px;">
			          <bean:message key="ClinicalDataAction.setupButton"/>
			       </html:submit>
				&nbsp;<b class="message">- (<% out.write(cghQueryString); %>) Clinical Study Analysis Queries</b></td></tr>
				</html:form>
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
			<input type="button" class="xbutton" value="Finalize Query" onclick="location.href='refinecheck.do'">
		</blockquote>
	 <%}%>
	</td></tr>
	<tr><td>
		&nbsp;
	</td></tr>
</table>


