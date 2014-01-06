<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*, java.lang.*, java.io.*,java.net.*,javax.xml.parsers.*" %> 
 <%@ page import="
gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper,
gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
gov.nih.nci.rembrandt.util.RembrandtConstants,org.xml.sax.*,org.xml.sax.helpers.DefaultHandler,
org.dom4j.Document,org.dom4j.io.SAXReader,org.dom4j.io.XMLWriter,org.dom4j.io.OutputFormat"
%>
	 

<html>
<head>
	<!--  script type="text/javascript" src="/rembrandt/js/rembrandtScript.js"></script>-->
	<script language="JavaScript" type="text/javascript" src="js/rembrandtScript.js"></script>
 <script language="javascript">
 	//overwrite this
 	Help.url = "../"+Help.url;
 
 function closeData(){
    if (window.opener && !window.opener.closed)
	{
		try	{
		for(i = 0; i < document.geneexpression.pathwayName.length; i++)
		{
			if(document.geneexpression.pathwayName[i].checked)
			{
				//alert(document.geneexpression.pathwayName[i].value);
				window.opener.document.getElementById("pathways").value += document.geneexpression.pathwayName[i].value + "\n";
			}	
		}	
		}
		catch(err){}
		// close the child
		window.close();
		
	}	
 
  }
  
  //This is to replace app:cshelp, which doesn't work for this page. Reason unknown
  function openHelp(){
  	window.open("https://wiki.nci.nih.gov/display/icrportals/3+Conducting+Advanced+Searches+v1.5.8#id-3ConductingAdvancedSearchesv158-SelectingaPathway", "Help",  "status,scrollbars,resizable,width=800,height=500");
  }
</script>
</head>
<body>
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
<%response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
response.setHeader("Pragma","no-cache"); //HTTP 1.0 
//response.setDateHeader ("Expires", 0); 
response.setDateHeader ("Expires", -1);
//prevents caching 
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
%>
 
 <div>
 	<b>Browse Pathway - Name</b>
 	<!-- This doesn't work for some reason: app:cshelp topic="Browse_pathway" />-->
 	<img align="right" onclick="javascript:openHelp()" name="helpIcon" id="helpIcon" alt="help" title="help" src="/rembrandt/images/help.png" style="cursor:pointer;border:0px;padding:2px;">
 </div>

<s:form action="geneexpression" method="post" theme="simple">

 <p>
  <input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" />&nbsp;
  
 </p>
 
 
	
 <div >
 
  <div style="width:700px;height:400px;overflow:auto">
    
  <%
  response.flushBuffer();	
  try{  
  		
  	String s = System.getProperty("gov.nih.nci.rembrandt.cacore.url")!=null ? (String)System.getProperty("gov.nih.nci.rembrandt.cacore.url") : "http://biodbnet.abcc.ncifcrf.gov/webServices/rest.php";
    URL url = new URL(s+"/biodbnetRestApi.xml?pathways=biocarta,ncipid,reactome&taxonId=9606");
    String pageNum = (String)request.getParameter("page"); 
    Document reportXML = null;
    SAXReader reader = new SAXReader();
    if (pageNum == null || pageNum.length() == 0) {
    	reportXML = reader.read(url);  
    	request.getSession().setAttribute(RembrandtConstants.SESSION_ATTR_PATHWAY_XML, reportXML);
    	pageNum = "0";
    }
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("page", pageNum);
    ReportGeneratorHelper.renderReportWithParams(request, null, RembrandtConstants.DEFAULT_PATHWAY_XSLT_FILENAME,out, params);
                          
    }
    catch(Exception e){
    	out.println("The Pathway Service is currently Unavailable.  Please try again later. ");
    }  	
 	
  	%>
 
 </div>	

 <p>
  <input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" />&nbsp;  
 </p>
 

 </s:form>
 </body>
 </html>
