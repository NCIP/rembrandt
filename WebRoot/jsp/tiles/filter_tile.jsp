<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*"%>
<% 
/*
 * Gene Tile - used in: GeneExpression form, CGH form
 */

String act = request.getParameter("act");
String sessionId = request.getSession().getId();  

%>

<script type='text/javascript' src='/rembrandt/dwr/interface/UserPreferences.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/util.js'></script>

<script language="javascript">
	function setUserDefaults(){
		var variance = document.getElementById("variancePercentile").value;
		var geneSetName = document.getElementById("geneList").value;
		var reporterSetName = document.getElementById("reporterList").value;
		
		UserPreferences.setVariancePercentile(variance,getVariance);			
		UserPreferences.setGeneSetName(geneSetName,getGeneSet);		
		UserPreferences.setReporterSetName(reporterSetName,getReporterSet);
		
	}
	function getVariance(data){
		UserPreferences.getVariancePercentile(putVariance);
	}
	function putVariance(data){	    
	    var varianceSetting = document.getElementById("varianceSetting");
	    varianceSetting.innerHTML = data;
	}
	function getGeneSet(data){
		UserPreferences.getGeneSetName(putGeneSet);
	}
	function putGeneSet(data){	    
	    var geneSetSetting = document.getElementById("geneSetSetting");
	    geneSetSetting.innerHTML = data;
	}
	function getReporterSet(data){
		UserPreferences.getReporterSetName(putReporterSet);
	}
	function putReporterSet(data){	    
	    var reporterSetName = data;
	    var reporterSetSetting = document.getElementById("reporterSetSetting");
	    reporterSetSetting.innerHTML = reporterSetName;
	}
	function updateG(){
    	UserPreferences.updateGeneSetList(createGList);
	}
	function createGList(data){    	
    	DWRUtil.removeAllOptions("geneList", data);
    	DWRUtil.addOptions("geneList", data);
	}
	function updateR(){
    	UserPreferences.updateReporterSetList(createRList);
	}
	function createRList(data){
    	DWRUtil.removeAllOptions("reporterList", data);
    	DWRUtil.addOptions("reporterList", data);
	}
	
</script>
<script type='text/javascript' src='/rembrandt/dwr/interface/UserPreferences.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>
<script type='text/javascript' src='/rembrandt/dwr/util.js'></script>
<jsp:useBean id="userPreferences" class="gov.nih.nci.rembrandt.web.bean.UserPreferencesBean" scope="session"/>
	
<fieldset class="gray">
<legend class="red">
	<logic:present name="hierarchicalClusteringForm">
	Step 1: 
	</logic:present>
	<logic:present name="principalComponentForm">
	Step 2: 
	</logic:present>
	Filter Genes/Reporters
<app:help help=" ...Optionally, you can upload a text file containing Gene identifiers by clicking the browse button. There must only be one entry per line and a return must be added at the end of the file. Choose “All Genes query” if you do not wish to specify a list of genes, but would like to see the data for all the genes analyzed. You must apply the “All Genes query” to a pre-existing result set. (see help on Refine query page for more details)" />
</legend>
<span id="confirm"></span>

<html:radio styleClass="radio" property="filterType" value="default" />Default<br /><br />

<html:radio styleClass="radio" property="filterType" value="advanced" />Advanced
&nbsp;&nbsp;<a href='#' id="pm" class="exp" onclick="javascript:toggleSDiv('advFilter','pm');return false;">&nbsp;+&nbsp;</a>

	<div id="advFilter" class="divHide">
		<br>
			<html:checkbox styleClass="radio" property="constraintVariance" value="constraintVariance" />Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="<jsp:getProperty name="userPreferences" property="variancePercentile"/>" />&nbsp;&nbsp;%
				
		<br><br>	
		  	<html:checkbox property="diffExpGenes" styleClass="radio" value="diffExpGenes" />
		  Use differentially expressed genes &nbsp;&nbsp;
		      <html:select property="geneSetName" styleId="geneList" disabled="false" onfocus="javascript:updateG()">
				 <option value="<jsp:getProperty name="userPreferences" property="geneSetName"/>"><jsp:getProperty name="userPreferences" property="geneSetName"/></option>
			  </html:select>
			  or <a href="#" onclick="javascript:spawnx('uploadGeneSet.do', 'upload', 'screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=380,height=230,scrollbars=yes,resizable=no');return false;">Upload</a>
						
		<br><br>	  
			<html:checkbox property="diffExpReporters" styleClass="radio" value="diffExpReporters" />
		  Use differentially expressed reporters &nbsp;&nbsp;
		      <html:select property="reporterSetName" styleId="reporterList" disabled="false" onfocus="javascript:updateR()">
					 <option value="<jsp:getProperty name="userPreferences" property="reporterSetName"/>"><jsp:getProperty name="userPreferences" property="reporterSetName"/></option>
			  </html:select>
			  or <a href="#" onclick="javascript:spawnx('uploadReporterSet.do', 'upload2', 'screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=380,height=230,scrollbars=yes,resizable=no');return false;">Upload</a>
		<br><br>	
			
			<center><html:button property="method" value="SET THESE FILTERS AS DEFAULT" onmousedown="setUserDefaults()" /></center>
						
		</div>	  
	  
	