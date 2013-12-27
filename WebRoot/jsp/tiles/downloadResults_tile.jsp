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
			var lnk = "fileDownload.do?method=brbFileDownload&fileId=";
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
	<!-- <br clear="all"/><a href="#" onclick="DownloadInboxWidgetController.stop();return false;">stop updating</a> -->
</fieldset>
<br/><br/>
<html:form action="/download.do?method=caarray">
<fieldset>
	<legend>caArray <app:cshelp topic="caarray_download_tooltip" text="[?]"/></legend>
	 	<fieldset class="gray">
			<legend class="red"><label for="groupNameCompare">Step 1:Choose the saved List:</label></legend></br> 
			<html:select property="groupNameCompare" styleId="groupNameCompare" style="width:200px;" disabled="false" onchange="">	         	
	 			<html:optionsCollection name="sampleGroupsList" />
	 		</html:select>
		</fieldset>
		<fieldset class="gray">
			<legend class="red"><label for="arrayPlatform">Step 2:Choose Array Platform: </label></legend><br/>
			<select id="arrayPlatform" name="arrayPlatform"> 		
		    	<!-- <option value="<%=Constants.ALL_PLATFROM%>">All</option>  -->
				<option selected="true" value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Oligo (Affymetrix U133 Plus 2.0)</option>
				<option value="<%=Constants.AFFY_100K_SNP_ARRAY%>">Affymetrix 100K SNP Array</option> -->
			</select>	
		</fieldset>
	    <fieldset class="gray">
			<legend class="red"><label for="fileType">Step 3:Select file type to download:</label></legend><br/>
			<select id="fileType" name="fileType">
				<option>CEL</option>
				<option>CHP</option>
				<!-- <option>ALL</option>  -->
				<!-- <option>OTHER</option> -->
			</select>
		</fieldset><br/>
			<logic:present name="disableDownload">
				<legend class="red">caArray File Download is unavailable at this time <br>as we are unable to successfully connect with caArray server,<br> Please visit http://array.nci.nih.gov to download the data directly.</legend><br/>
	       		<input type="submit" value="download" style="width:70px" disabled/>	
			</logic:present>
			<logic:notPresent name="disableDownload">
	       		<input type="submit" value="download" style="width:70px"/>	 
			</logic:notPresent>
     
	
</fieldset>
</html:form>

<br/><br/>
<fieldset>
	<legend><label for="brbFormat">BRB File Downloads </label><app:cshelp topic="Brb_download_tooltip" text="[?]"/></legend>
    <logic:notEmpty name="downloadFileList">
        	<select id="brbFormat">
        		<option>BRB Format</option>
        	</select>
        	<select id="idfile" style="width:300px">
		        <logic:iterate name="downloadFileList" id="downloadFile">
			       <option value="<bean:write name="downloadFile" property="fileName"/>"><bean:write name="downloadFile" property="fileName"/></option>
		        </logic:iterate>
	        </select><label for="idfile">&#160;</label>
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