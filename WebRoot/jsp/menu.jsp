<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*,
	 gov.nih.nci.nautilus.query.*,
	 gov.nih.nci.nautilus.constants.NautilusConstants,
	 org.apache.log4j.Logger,
	  gov.nih.nci.nautilus.ui.helper.SessionQueryBag" %> 


<%
Logger logger = Logger.getLogger(NautilusConstants.JSP_LOGGER);
int geQueryNum = 0;
String geQueryString = "0";
int cpQueryNum = 0;
String cpQueryString = "0";
int cghQueryNum = 0;
String cghQueryString = "0";

 SessionQueryBag queryCollection = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
 if(queryCollection == null){
   logger.debug("its null");
   }
 else{
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
<table border="0" cellspacing="4" cellpadding="3">
  <tr><td>
  <fieldset>
		<legend>Add to query:</legend><br>
		<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
			<table border="0" cellpadding="3" cellspacing="3">
			<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Gene Expression Analysis" onclick="javascript:location.href='geneExpression.do';">
				&nbsp; <b class="message">- (<% out.write(geQueryString); %>) Gene Exp. Analysis Queries</b></td></tr>
			<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Comparative Genomic Analysis" onclick="javascript:location.href='comparitiveGenomic.do';">
				&nbsp; <b class="message">- (<% out.write(cpQueryString); %>) Comp. Genomic Analysis Queries</b></td></tr>
			<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Clinical Study Analysis" onclick="javascript:location.href='clinicalData.do';">
				&nbsp;<b class="message">- (<% out.write(cghQueryString); %>) Clinical Study Analysis Queries</b></td></tr>
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


