<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*"%>
<%
	String act = request.getParameter("act") + "_Filter_tooltip";

%>
<script language="javascript">
	window.onload = function() {
		updateG();
		updateR();
	}

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
    	UserListHelper.getGenericListNamesFromString("Gene",createGeneList);
	}
	function createGeneList(data){   	
    	DWRUtil.removeAllOptions("geneList", data);
    	DWRUtil.addOptions("geneList", ['none']) 
    	DWRUtil.addOptions("geneList", data);
	}
	function updateR(){
    	UserListHelper.getGenericListNamesFromString("Reporter",createRList);
	}
	function createRList(data){
    	DWRUtil.removeAllOptions("reporterList", data);
    	DWRUtil.addOptions("reporterList", ['none']) 
    	DWRUtil.addOptions("reporterList", data);
	}
	
</script>
<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
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
	<app:cshelp topic="<%=act%>" text="[?]"/>   
	
<!--  <app:help help="Leave as Default, or select Advanced and click + for more options." />-->
</legend>
<span id="confirm"></span>

<!--<html:radio styleClass="radio" property="filterType" value="default" />Default<br /><br />-->

<!--<html:radio styleClass="radio" property="filterType" value="advanced" />-->&nbsp;&nbsp;View Filter Settings
&nbsp;&nbsp;<a href='#' id="pm" class="exp" onclick="javascript:toggleSDiv('advFilter','pm');return false;">&nbsp;+&nbsp;</a>

	<div id="advFilter" class="divHide">
		<br>
			<logic:present name="principalComponentForm"> 
			Constrain reporters by variance (Gene Vector) percentile:&nbsp;&nbsp;&ge;			
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="<jsp:getProperty name="userPreferences" property="pcaVariancePercentile"/>" />&nbsp;&nbsp;%
			</logic:present>
			<logic:present name="hierarchicalClusteringForm"> 
			Constrain reporters by selecting top variance percentile:&nbsp;&nbsp;&le;
				<input type="text" name="variancePercentile" id="variancePercentile" size="4" value="<jsp:getProperty name="userPreferences" property="hcVariancePercentile"/>" />&nbsp;&nbsp;%
			</logic:present>
			
		<br><br>	
		  Use differentially expressed genes &nbsp;&nbsp;
		      <html:select property="geneSetName" styleId="geneList" disabled="false" onfocus="javascript:updateG()">
				 <option value="<jsp:getProperty name="userPreferences" property="geneSetName"/>"><jsp:getProperty name="userPreferences" property="geneSetName"/></option>
			  </html:select>
			 <!--or <a href="#" onclick="javascript:spawnx('uploadGeneSet.do', 'upload', 'screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=380,height=230,scrollbars=yes,resizable=no');return false;">Upload</a>-->
						
		<br><br>	  
		  Use differentially expressed reporters &nbsp;&nbsp;
		      <html:select property="reporterSetName" styleId="reporterList" disabled="false" onfocus="javascript:updateR()">
					 <option value="<jsp:getProperty name="userPreferences" property="reporterSetName"/>"><jsp:getProperty name="userPreferences" property="reporterSetName"/></option>
			  </html:select>
			  <!--or <a href="#" onclick="javascript:spawnx('uploadReporterSet.do', 'upload2', 'screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=380,height=230,scrollbars=yes,resizable=no');return false;">Upload</a>-->
		<br><br>	
			<logic:present name="principalComponentForm"> 
			<center><html:button property="method" value="SET THESE FILTERS AS DEFAULT" onmousedown="setUserDefaults('pca')" /></center>
			</logic:present>
			<logic:present name="hierarchicalClusteringForm"> 
			<center><html:button property="method" value="SET THESE FILTERS AS DEFAULT" onmousedown="setUserDefaults('hc')" /></center>
			</logic:present>
						
		</div>	  
	  
	