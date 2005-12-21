<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*"%>

<script language="javascript">
	function setUserDefaults(analysis){
		var variance = document.getElementById("variancePercentile").value;
		var geneSetName = document.getElementById("geneList").value;
		var reporterSetName = document.getElementById("reporterList").value;
		
		if(analysis=="pca"){
			UserPreferences.setPCAVariancePercentile(variance,getPCAVariance);
		}
		else if(analysis=="hc"){
			UserPreferences.setHCVariancePercentile(variance,getHCVariance);
		}			
		UserPreferences.setGeneSetName(geneSetName,getGeneSet);		
		UserPreferences.setReporterSetName(reporterSetName,getReporterSet);
		
	}
	function getPCAVariance(data){
		UserPreferences.getPCAVariancePercentile(putVariance);
	}
	function getHCVariance(data){
		UserPreferences.getHCVariancePercentile(putVariance);
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
<app:help help="Select either Default or Advanced by clicking the pertinent radio button. If you choose Advanced, click the + to access the following options:  1.Constrain reporters by variance (Gene Vector) percentile: The reporters whose variances of the log ratio (or Log2 signals) across all experiments were among the top percentile of variance of all reporters were identified. Select the check box and enter the percentage into the text box. If you enter 70% into the text box, it means that you choose reporters with top 30 (100 - 70) percentile of variance of all reporters.  2.Use differentially expressed genes: Choose saved differentially expressed genes identified by class comparison. Choose an option (gene list1 or gene list2) from the drop-down list or click the Upload link to upload a file.  3.Use differentially expressed reporters: Choose saved differentially expressed reporters identified by class comparison. Choose an option (reporter list1 or reporter list2) from the drop-down list or click the Upload link to upload a file.  4.If desired, click the Set These Filters As Default button." />
</legend>
<span id="confirm"></span>

<html:radio styleClass="radio" property="filterType" value="default" />Default<br /><br />

<html:radio styleClass="radio" property="filterType" value="advanced" />Advanced
&nbsp;&nbsp;<a href='#' id="pm" class="exp" onclick="javascript:toggleSDiv('advFilter','pm');return false;">&nbsp;+&nbsp;</a>

	<div id="advFilter" class="divHide">
		<br>
			<logic:present name="principalComponentForm"> 
			<html:checkbox styleClass="radio" property="constraintVariance" value="constraintVariance" />Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="<jsp:getProperty name="userPreferences" property="pcaVariancePercentile"/>" />&nbsp;&nbsp;%
			</logic:present>
			<logic:present name="hierarchicalClusteringForm"> 
			<html:checkbox styleClass="radio" property="constraintVariance" value="constraintVariance" />Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="<jsp:getProperty name="userPreferences" property="hcVariancePercentile"/>" />&nbsp;&nbsp;%
			</logic:present>
			
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
			<logic:present name="principalComponentForm"> 
			<center><html:button property="method" value="SET THESE FILTERS AS DEFAULT" onmousedown="setUserDefaults('pca')" /></center>
			</logic:present>
			<logic:present name="hierarchicalClusteringForm"> 
			<center><html:button property="method" value="SET THESE FILTERS AS DEFAULT" onmousedown="setUserDefaults('hc')" /></center>
			</logic:present>
						
		</div>	  
	  
	