<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

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

<fieldset>
	<legend>
		Download Results
	</legend>
	<br />
	<div id="downloadStatusContainer"></div>
	<script type="text/javascript">
		Event.observe(window, 'load', function() {
	 		DownloadInboxWidgetController.start("downloadStatusContainer", 10);
		});
	</script>
	<br clear="all"/><a href="#" onclick="DownloadInboxWidgetController.stop();return false;">stop updating</a>
</fieldset>
<br/><br/>
<html:form action="/download.do?method=caarray">
<fieldset>
	<legend>caArray</legend>
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
				<!-- <option value="<%=Constants.CDNA_ARRAY_PLATFORM%>">cDNA</option> -->
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
		<input type="submit" value="download" style="width:70px"/>	        
	
</fieldset>
</html:form>

<br/><br/>
<fieldset>
	<legend>BRB File Downloads</legend>
    <logic:notEmpty name="downloadFileList">
        	<select>
        		<option>BRB Format</option>
        	</select>
        	<select id="idfile" style="width:300px">
		        <logic:iterate name="downloadFileList" id="downloadFile">
			       <option value="<bean:write name="downloadFile" property="fileId"/>"><bean:write name="downloadFile" property="fileName"/></option>
		        </logic:iterate>
	        </select>
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