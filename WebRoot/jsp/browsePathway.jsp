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
 
 function closeData(){
    if (window.opener && !window.opener.closed)
	{
		//reference the textarea field in the opener/parent and fill it in
		
		
		for(i = 0; i < document.geneexpressionForm.pathwayName.length; i++)
		{
			if(document.geneexpressionForm.pathwayName[i].checked)
			{
				window.opener.document.geneexpressionForm.pathways.value += document.geneexpressionForm.pathwayName[i].value + "\n";
			}	
		}	
		// close the child
		window.close();
		
	}	
 
   
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
<%response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
response.setHeader("Pragma","no-cache"); //HTTP 1.0 
//response.setDateHeader ("Expires", 0); 
response.setDateHeader ("Expires", -1);
//prevents caching 
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
%>
 
 <div><b>Browse Pathway - Name</b></div>

<html:form method="post" action="geneexpression.do">

 <p>
  <input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" / >&nbsp;
  
 </p>
 
 
	
 <div >
 
  <div style="width:700px;height:400px;overflow:auto">
    
  <%
  response.flushBuffer();	
  try{  
     URL url = new URL("http://cabio.nci.nih.gov/cacore31/GetXML?query=Pathway&Taxon[@abbreviation=Hs]");  
     
     String urlLink = (String)request.getParameter("url");      
     if(urlLink != null && urlLink.equals("2")){
        url = new URL("http://cabio.nci.nih.gov/cacore31/GetXML?query=Pathway&Taxon[@abbreviation=Hs]&pageNumber=2&resultCounter=1000&startIndex=0"); 
      }
   
    SAXReader reader = new SAXReader();    
    Document reportXML = reader.read(url);    
    ReportGeneratorHelper.renderReport(request,reportXML,RembrandtConstants.DEFAULT_PATHWAY_XSLT_FILENAME,out);
    }
    catch(Exception e){}  	
 	
  	%>
 
 </div>	

 <p>
  <input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" / >&nbsp;  
 </p>
 

 </html:form>
 
