<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
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
			var lnk = "fileDownload?fileId=";
			window.location.href=lnk+dl;
		}
</script>
<app:cshelp topic="download_data_help" />
<fieldset>
	<legend>
		Download Results <app:cshelp topic="results_download_tooltip" text="[?]"/>
	</legend>
	<br />
	<div id="downloadStatusContainer"></div>
	<script type="text/javascript">
		Event.observe(window, 'load', function() {
	 		DownloadInboxWidgetController.start("downloadStatusContainer", 10);
		});
	</script>
	
</fieldset>
<br/><br/>
<s:form action="downloadcaArray" theme="simple">
<fieldset>
	<legend>caArray <app:cshelp topic="caarray_download_tooltip" text="[?]"/></legend>
	 	<fieldset class="gray">
			<legend class="red"><label for="groupNameCompare">Step 1:Choose the saved List:</label></legend></br> 
			
			<s:select name="downloadForm.groupNameCompare" id="groupNameCompare" style="width:200px;" disabled="false" 
				list="sampleGroupsList" listKey="value" listValue="label">	         	
	 		</s:select>
		</fieldset>
		<fieldset class="gray">
			<legend class="red"><label for="arrayPlatform">Step 2:Choose Array Platform: </label></legend><br/>
			<s:select id="arrayPlatform" name="downloadForm.arrayPlatform" list="arrayPlatformList" listKey="value" listValue="label" /> 		
			
		</fieldset>
	    <fieldset class="gray">
			<legend class="red"><label for="fileType">Step 3:Select file type to download:</label></legend><br/>
			<s:select id="fileType" name="downloadForm.fileType" list="fileTypeList" />
				
		</fieldset><br/>
			<s:if test="#disableDownload != null && #disableDownload.length() > 0">
				<legend class="red">caArray File Download is unavailable at this time <br>
				as we are unable to successfully connect with caArray server,<br> 
				Please visit http://array.nci.nih.gov to download the data directly.</legend><br/>
	       		<input type="submit" value="download" style="width:70px" disabled/>	
			</s:if>
			<s:else>
			
	       		<input type="submit" value="download" style="width:70px"/>	 
			</s:else>
     
	
</fieldset>
</s:form>

<br/><br/>
<fieldset>
	<legend><label for="brbFormat">BRB File Downloads </label><app:cshelp topic="Brb_download_tooltip" text="[?]"/></legend>
    <s:if test="downloadFileList != null && downloadFileList.size() > 0">
    <s:form action="fileDownload" theme="simple" >
        	<s:select id="brbFormat" list="brbFormatList" />
       
        	<s:select id="idfile" style="width:300px" name="fileId" list="downloadFileList" listKey="fileName" listValue="fileName" />
		        <label for="idfile">&#160;</label>
	        <s:submit type="button" style="width:70px" class="xbutton" theme="simple">download</s:submit> 
	        
	 </s:form>
     	</s:if>
     	<s:else>
		    <strong>There are no files to download at this time.</strong>
		    <br /><br />
	    </s:else>
     	<br/><br/>
	    &nbsp;&nbsp;&nbsp;
	    <a href="http://linus.nci.nih.gov/BRB-ArrayTools.html" target="_blank"><span style="font-size:.8em;text-align:left;"> BRB-Array Tools </span></a>
	  	<br /><br />
</fieldset>