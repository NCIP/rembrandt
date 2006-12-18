<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.text.ParseException" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.Locale" %>
<%@ page import = "gov.nih.nci.rembrandt.util.RembrandtConstants" %>
<%@ page import = "gov.nih.nci.rembrandt.dto.lookup.*" %>
<%@taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%

String filename="";
String graphURL= "";

%>
<!-- 
<script type="text/javascript" src="js/overlib.js"></script>
<script type="text/javascript" src="js/overlib_hideform.js"></script>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
-->

	<script type='text/javascript' src='/rembrandt/dwr/interface/DynamicReport.js'></script>
	<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>

	<script language="javascript" src="js/lib/json.js"></script>
	<script language="javascript" src="XSL/js.js"></script>
	

<div style="background-color:#ffffff">
	<div>
<%
 if(request.getParameter("reporter")!=null)	{
%>
       <script type="text/javascript">Help.insertHelp("Coin_plot", " align='right'", "padding:2px;");</script>
<% } else { %>
	   <script type="text/javascript">Help.insertHelp("Simple_gene_expression_plot", " align='right'", "padding:2px;");</script><br clear="all"/>
<% } %>   
	</div>

	<!-- 
	<img src="<%= graphURL %>" border=0 usemap="#<%= filename %>">
	-->

<%
//String geneSymbol = request.getAttribute("geneSymbol")!=null ? (String) request.getAttribute("geneSymbol") : "N/A";
String alg = request.getParameter("alg")!=null ? (String) request.getParameter("alg") : RembrandtConstants.REPORTER_SELECTION_AFFY; 

%>
<div align="center">
	<div>
	<graphing:GenePlot algorithm="<%=alg%>" />
	<br/>
	<br clear="both"/>
	</div>
	<fieldset style="width:300px; text-align:left; padding:3px">
		<legend>Abbreviations of Group Names</legend>
		<%
		out.println("<table>\n");
		DiseaseTypeLookup[] diseaseTypes = LookupManager.getDiseaseType();
		if(diseaseTypes != null)	{
			for (int i = 0; i< diseaseTypes.length ; i++) {
				String diseaseType = diseaseTypes[i].getDiseaseType()!=null ? diseaseTypes[i].getDiseaseType() : "N/A";
				String diseaseDesc = diseaseTypes[i].getDiseaseDesc() != null ? diseaseTypes[i].getDiseaseDesc() : "N/A";
				if(diseaseType.equalsIgnoreCase(gov.nih.nci.rembrandt.util.RembrandtConstants.ASTRO))	{
			    	diseaseType = diseaseType.substring(0,6);
			    }	
			 	out.println("<tr><td>"+diseaseType+":</td><td>" + diseaseDesc + "</td></tr>\n" );
			}
			out.println("</table>\n");
		}
		else	{
		%>
			<pre>
				GBM:	Glioblastoma Multiforme
				OLIG:	Oligodendroglioma
				ASTRO:	Astrocytoma
				MIXED:	Mixed
				GLIOMA:	Glioma (Includes All)
				NON-TUMOR: Normal
			</pre>
		<%
		}
		%>
	</fieldset>
<br/>
<!-- 
<a href="javascript:void(window.print())">[Print this graph]</a><br/><br/>
-->
</div>
	
</div>