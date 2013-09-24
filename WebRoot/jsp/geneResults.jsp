<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*,java.net.*,javax.xml.parsers.*" %> 
<%@ page import="
gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper,
gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
gov.nih.nci.rembrandt.util.RembrandtConstants,org.xml.sax.*,org.xml.sax.helpers.DefaultHandler,
org.dom4j.Document,org.dom4j.io.SAXReader,org.dom4j.io.XMLWriter,org.dom4j.io.OutputFormat"
%>
  <script language="javascript">
 
 function closePage(){
   window.close();
 }
 </script>
 
 <!--header NCI-->
 <table align="center" width="765" border="0" cellspacing="0" cellpadding="0" bgcolor="#A90101">
 <tr bgcolor="#A90101">
 		<td width="283" height="37" align="left"><a href="http://www.cancer.gov"><img src="../images/logotype.gif" width="283" height="37" border="0"></a></td>
 		<td>&nbsp;</td>
 		<td width="295" height="37" align="right"><a href="http://www.cancer.gov"><img src="../images/tagline.gif" width="295" height="37" border="0"></a></td>

 </tr>
 </table>
 <!--header REMBRANDT image map-->
 <div align="center" width="765px">
 <div style="width:765px; border-bottom: 1px solid #000000; margin:0px;">
 <map name="headerMap">
 <area alt="REMBRANDT website" coords="7,8,272,50" href="http://rembrandt.nci.nih.gov">
 </map>
 <img src="../images/header.jpg" width="765" height="65" alt="REMBRANDT application logo" border="0" usemap="#headerMap">
 </div>
 <!--end all headers-->

	
	<table width="95%" border="0"  align="center">
    			 
    <tr>				
	  <td align="right">
	     <input type="button" value="Close"  onclick="javascript:closePage();" / >	
	  </td>	
	</tr>
	<tr>
	 <td>&nbsp;&nbsp;&nbsp;</td>
	</tr>	
	</table>	
	 
		
	<%
  response.flushBuffer();	
  try{
    String db = (String)request.getParameter("db");  
    String pathwayName = (String)request.getParameter("name");  
    String pathwayDisplayName = (String)request.getParameter("displayname"); 
    String s = System.getProperty("gov.nih.nci.rembrandt.cacore.url")!=null ? (String)System.getProperty("gov.nih.nci.rembrandt.cacore.url") : "http://biodbnet.abcc.ncifcrf.gov/webServices/rest.php";   
    
    String urlStr = ReportGeneratorHelper.composeGeneReportUrl(s, db, pathwayName);    
    URL url = new URL(urlStr);
    SAXReader reader = new SAXReader();    
    Document reportXML = reader.read(url);
    
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("displayName", pathwayDisplayName);
    ReportGeneratorHelper.renderReportWithParams(request,reportXML,RembrandtConstants.DEFAULT_GENE_XSLT_FILENAME,out, params);
    
   
    }
    catch(Exception e){
     
    }			
  	
 	
  	%>
 
 
   
 
 <table width="95%" border="0"  align="center">
    <tr>
	 <td>&nbsp;&nbsp;&nbsp;</td>
	</tr>				 
    <tr>				
	  <td align="right">
	     <input type="button" value="Close"  onclick="javascript:closePage();" / >	
	  </td>	
	</tr>
	</table>				
				
  
