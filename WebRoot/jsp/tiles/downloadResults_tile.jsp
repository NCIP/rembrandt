<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>
<%@ page import="gov.nih.nci.rembrandt.download.caarray.RembrandtCaArrayFileDownloadManager"%>
<%
	Object  caArrayInstance = RembrandtCaArrayFileDownloadManager.getInstance();
	Boolean disableDownload = Boolean.FALSE;
	if(caArrayInstance == null){
		disableDownload = Boolean.TRUE;
		pageContext.setAttribute("disableDownload", disableDownload);
	}
	else{
		pageContext.removeAttribute("disableDownload");
	}
 %>
<!-- 
<script type='text/javascript' src='dwr/interface/Inbox.js'></script>
 -->
<script type='text/javascript' src='js/lib/prototype-1.6.0.2.js'></script> 
<script type='text/javascript' src='js/DownloadInboxWidget.js'></script>
 
<script type="text/javascript">
		function getDL(sel)	{
			var dl = sel.value;
			//why does this need an whole sep action and mapping?  bad...
			//var lnk = "fileDownloadforDownloadpage.do?method=brbFileDownload&fileId=";
			var lnk = "fileDownload.do?method=brbFileDownload&fileId=";
			window.location.href=lnk+dl;
		}
</script>
<script type="text/javascript">Help.insertHelp("download_data_help", " align='right'", "padding:2px;");</script>
<fieldset>
	<legend>
		Download Results <a href="javascript: Help.popHelp('results_download_tooltip');">[?]</a>
	</legend>
	<br />
	<div id="downloadStatusContainer"></div>
	<script type="text/javascript">
		Event.observe(window, 'load', function() {
	 		DownloadInboxWidgetController.start("downloadStatusContainer", 10);
		});
	</script>
	<!-- <br clear="all"/><a href="#" onclick="DownloadInboxWidgetController.stop();return false;">stop updating</a> -->
</fieldset>
<br/><br/>
<html:form action="/download.do?method=caarray">
<fieldset>
	<legend>caArray <a href="javascript: Help.popHelp('caarray_download_tooltip');">[?]</a></legend>
	 	<fieldset class="gray">
			<legend class="red">Step 1:Choose the saved List:</legend></br> 
			<html:select property="groupNameCompare" styleId="groupNameCompare" style="width:200px;" disabled="false" onchange="">	         	
	 			<html:optionsCollection name="sampleGroupsList" />
	 		</html:select>
		</fieldset>
		<fieldset class="gray">
			<legend class="red">Step 2:Choose Array Platform: </legend><br/>
			<select name="arrayPlatform"> 		
		    	<!-- <option value="<%=Constants.ALL_PLATFROM%>">All</option>  -->
				<option selected="true" value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Oligo (Affymetrix U133 Plus 2.0)</option>
				<option value="<%=Constants.AFFY_100K_SNP_ARRAY%>">Affymetrix 100K SNP Array</option> -->
			</select>	
		</fieldset>
	    <fieldset class="gray">
			<legend class="red">Step 3:Select file type to download:</legend><br/>
			<select name="fileType">
				<option>CEL</option>
				<option>CHP</option>
				<!-- <option>ALL</option>  -->
				<!-- <option>OTHER</option> -->
			</select>
		</fieldset><br/>
			<logic:present name="disableDownload">
				<legend class="red">caArray File Download is unavailable at this time <br>as we are unable to successfully connect with caArray server,<br> Please try again later</legend><br/>
	       		<input type="submit" value="download" style="width:70px" disabled/>	
			</logic:present>
			<logic:notPresent name="disableDownload">
	       		<input type="submit" value="download" style="width:70px"/>	 
			</logic:notPresent>
     
	
</fieldset>


<br/><br/>
<fieldset>
	<legend>File Downloads <a href="javascript: Help.popHelp('Brb_download_tooltip');">[?]</a></legend>
	
	   <logic:notEmpty name="downloadFileList">
        	<html:select property="downloadFile" onchange="DynamicListHelper.getFileNamesForFileType(this.value, function(fileId) { DWRUtil.removeAllOptions('idfile'); DWRUtil.addOptions('idfile', fileId);})">
        		<option value=" "></option>
				<option value="BRB">BRB Format</option>
        		<option value="GENE_EXP">Gene Expression Analysis</option>
        	</html:select>
        	<html:select styleId="idfile" property="idfile" style="width:300px">
					<option value="" />
						<html:optionsCollection property="fileId" label="fileId"
							value="fileName" />
			</html:select>
 	        <input type="button" onclick="getDL($('idfile'))" value="download" style="width:70px"/>
     	</logic:notEmpty>
	    <logic:empty name="downloadFileList">
		    <strong>There are no files to download at this time.</strong>
		    <br /><br />
	    </logic:empty>
     	<br/><br/>
	    &nbsp;&nbsp;&nbsp;
	    <a href="http://linus.nci.nih.gov/BRB-ArrayTools.html" target="_blank"><span style="font-size:.8em;text-align:left;"> BRB-Array Tools </span></a>
	  	<br /><br />
</fieldset>
</html:form>