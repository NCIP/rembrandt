<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ page import="java.util.*, java.lang.*, java.io.*,java.net.*,javax.xml.parsers.*" %> 
 <%@ page import="
gov.nih.nci.rembrandt.web.helper.ReportGeneratorHelper,
gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
gov.nih.nci.rembrandt.util.RembrandtConstants,org.xml.sax.*,org.xml.sax.helpers.DefaultHandler,
org.dom4j.Document,org.dom4j.io.SAXReader,org.dom4j.io.XMLWriter,org.dom4j.io.OutputFormat"
%>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
	<script type="text/javascript" src="js/overlib.js"></script>
	<script type="text/javascript" src="js/overlib_hideform.js"></script>
	<script type="text/javascript" src="js/lib/common/KeggPathwayHelper.js"></script>
	<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'> </script>
<script type='text/javascript' src='dwr/util.js'> </script>
 <script language="javascript">
 
 function closeData(){
    if (window.opener && !window.opener.closed)
	{
		//reference the textarea field in the opener/parent and fill it in
		
		
		for(i = 0; i < document.geneexpression.pathwayName.length; i++)
		{
			if(document.geneexpression.pathwayName[i].checked)
			{
				window.opener.document.getElementById("pathways").value += document.geneexpression.pathwayName[i].value + "\n";
				
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
		<td width="283" height="37" align="left"><a href="http://www.cancer.gov"><img src="images/logotype.gif" width="283" height="37" border="0"></a></td>
		<td>&nbsp;</td>
		<td width="295" height="37" align="right"><a href="http://www.cancer.gov"><img src="images/tagline.gif" width="295" height="37" border="0"></a></td>

</tr>
</table>
<!--header REMBRANDT image map-->
<div align="center" width="765px">

<div style="width:765px; border-bottom: 1px solid #000000; margin:0px;">
<map name="headerMap">
<area alt="REMBRANDT website" coords="7,8,272,50" href="http://rembrandt.nci.nih.gov">
</map>
<img src="images/header.jpg" width="765" height="65" alt="REMBRANDT application logo" border="0" usemap="#headerMap">
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

<s:form action="geneexpression" method="post" theme="simple">

 <p>
  <input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" />&nbsp;
  
 </p>
 
  <div>
  Your search returned <b>
  <s:property value="pathwaySize" />
  </b> pathways.   

 </div>	
	
 
 
  <div style="width:700px;height:400px;overflow:auto">
    
 
	 <table border="1" cellpadding="5" cellspacing="0" style="border">
	 
	 <tr style="background-color:#D5E0E9">
		<td><b>No</b></td>
	  <td><b>Select Pathway</b></td>
	  <td><b>Pathway Name</b></td>
	  <td><b>Number of Genes</b></td>	  
	  </tr>
	<% int i = 1; %>
	
	<s:iterator value="keggPathways">
		<tr>
			<TD><%= i %></TD>
			<TD>
			<input name="pathwayName" value="<s:property value='pathwayName'/>" type="checkbox"></input>
			<s:property value='pathwayName'/>
			</TD>
			<TD>
				<a href="http://cgap.nci.nih.gov/Pathways/Kegg/<s:property value='pathwayName'/>" target="_blank">
					<s:property value='pathwayDesc'/>
				</a>
			</TD>
			<TD>
			<div id="<s:property value='pathwayName'/>">
					<a href="javascript:void(0);" 
						onmouseover="return KeggPathwayHelper.getPathwayGeneSymbols('<s:property value='pathwayName'/>');"
						onmouseout="return nd();"><s:property value='listSize'/></a>
				</div>				
			</TD>
						
		</tr>		
		<% i++;%>
	</s:iterator>
	</table>
	
	<p>
  <input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" />&nbsp;
  
 </p>
 
 </div>
 </s:form>
 
 
