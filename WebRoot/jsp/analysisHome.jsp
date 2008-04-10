<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>
<%@ page import="gov.nih.nci.caintegrator.application.analysis.gp.GenePatternIntegrationHelper" %>
	 

<%
int ccAnalysisNum = 0;
String ccAnalysisString = "0";
int hcAnalysisNum = 0;
String hcAnalysisString = "0";
int pcaAnalysisNum = 0;
String pcaAnalysisString = "0";
String sessionId = request.getSession().getId();
String gpHomeURL = GenePatternIntegrationHelper.gpHomeURL(request);
gpHomeURL = gpHomeURL + "&target=new";
%>
<br clear="both"/>
<div>
  <fieldset>
		<legend>High Order Analysis:</legend>
		<br clear="both"/>
		<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
		<script type="text/javascript">Help.insertHelp("HOA_overview", " align='right'", "padding:2px;");</script>
		
			<table border="0" cellpadding="10" cellspacing="3">
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Class Comparison Analysis" onclick="javascript:location.href='classcomparisonInit.do?method=setup';"></td></tr>				
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Principal Component Analysis (PCA)" onclick="javascript:location.href='principalcomponentInit.do?method=setup';"></td></tr>				
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Hierarchical Clustering Analysis" onclick="javascript:location.href='hierarchicalclusteringInit.do?method=setup';"></td></tr>			
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Send the Data to GenePattern" onclick="javascript:location.href='gpintegrationInit.do?method=setup';"></td></tr>			
			    <tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Go to GenePattern" onclick="window.open( '<%= gpHomeURL %>');"></td></tr>			
		</table>
	</fieldset>
</div>
<br/><br/>