<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*, gov.nih.nci.nautilus.query.*,gov.nih.nci.nautilus.constants.Constants" %> 


<%
int geQueryNum = 0;
String geQueryString = "0";
int cpQueryNum = 0;
String cpQueryString = "0";
int cghQueryNum = 0;
String cghQueryString = "0";

QueryCollection queryCollection = (QueryCollection) request.getSession().getAttribute(Constants.QUERY_KEY);
 if(queryCollection == null){
   System.out.println("its null");
   }
 else{
 	Collection queryColl = queryCollection.getQueries();
 	Iterator i = queryColl.iterator();
 	
 	while (i.hasNext()){
 		Query query = (Query)i.next();
 		if(query instanceof GeneExpressionQuery){
		   System.out.println("this is a gene expression query in the collection");
 		   geQueryNum++;
 		   geQueryString = Integer.toString(geQueryNum);
 		}
 		if(query instanceof ComparativeGenomicQuery){
 		   System.out.println("this is a Comparative Genomic query in the collection");
 	 	   cpQueryNum++;
 	 	   cpQueryString = Integer.toString(cpQueryNum);
 	 	}
 		if(query instanceof ClinicalDataQuery){
 			System.out.println("this is a Clinical Data query in the collection");
 	 		cghQueryNum++;
 	 		cghQueryString = Integer.toString(cghQueryNum);
 	 		}
 	}
 }
%>

<tr class="report"><td>
<table border="0" cellspacing="4" cellpadding="3">
<form>
	<tr>
		<td>
		<fieldset>
		<legend>Add to query:</legend><br>
		<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
		<table border="0" cellpadding="3" cellspacing="3">
		<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Gene Expression Analysis" onclick="javascript:location.href='geneExpression.do';">
			&nbsp; <b class="message">- (<% out.write(geQueryString); %>) Gene Exp. Analysis Queries</b></td></tr>
		<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Comparitive Genomic Analysis" onclick="javascript:location.href='comparitiveGenomic.do';">
			&nbsp; <b class="message">- (<% out.write(cpQueryString); %>) Comp. Genomic Analysis Queries</b></td></tr>
		<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Clinical Study Analysis" onclick="javascript:location.href='clinicalData.do';">
			&nbsp;<b class="message">- (<% out.write(cghQueryString); %>) Clinical Study Analysis Queries</b></td></tr>
		</table>
		
		</fieldset>
	</td>
	</tr>
	
	<tr><td>
	<%
	if(queryCollection == null){
	    out.println("&nbsp");
	    }
	  else{
		out.println("<strong>I am satisfied with my query and would like to finalize:</strong><br /><blockquote><input type='button' class='xbutton' value='Finalize query' onclick='location.href=\"refinecheck.do\"'></blockquote>");
	  }
	%>
	</td></tr>
				
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
</form>

</td></tr>
