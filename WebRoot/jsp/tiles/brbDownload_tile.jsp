<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, java.net.URLEncoder " %>
<%@ page import="gov.nih.nci.rembrandt.web.factory.ApplicationFactory" %>
<%@ page import="gov.nih.nci.caintegrator.application.cache.BusinessTierCache" %>
<%@ page import="gov.nih.nci.caintegrator.service.findings.*" %>
<%@ page import="gov.nih.nci.caintegrator.enumeration.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<!--  BEGIN BULK DOWNLOAD SECTION -->
	<script type="text/javascript">
		function getDL(sel)	{
			var dl = sel.value;
			var lnk = "fileDownloadforDownloadpage.do?method=brbFileDownload&fileId=";
			window.location.href=lnk+dl;
		}
	</script>

      <fieldset style="background:transparent;background-color:#fff;">
        <legend>
          Bulk Downloads
        </legend>
        <br/>
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
	  <br/><br/>
