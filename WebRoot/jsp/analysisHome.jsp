<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>
	 

<%
int ccAnalysisNum = 0;
String ccAnalysisString = "0";
int hcAnalysisNum = 0;
String hcAnalysisString = "0";
int pcaAnalysisNum = 0;
String pcaAnalysisString = "0";
String sessionId = request.getSession().getId();
%>

<table width="80%" border="0" cellspacing="4" cellpadding="3">
  <tr><td>
  <fieldset>
		<legend>High Order Analysis:</legend><br>
		<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
			<table border="0" cellpadding="3" cellspacing="3">
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Class Comparison Analysis" onclick="javascript:location.href='classcomparisonInit.do?method=setup';"></td></tr>
				
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Principal Component Analysis (PCA)" onclick="javascript:location.href='principalcomponent.do?method=setup';"></td></tr>
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Hierarchical Clustering Analysis" onclick="javascript:location.href='hierarchicalclustering.do?method=setup';"></td></tr>			
			</table>
	</fieldset>
	</td></tr>
	
</table>


