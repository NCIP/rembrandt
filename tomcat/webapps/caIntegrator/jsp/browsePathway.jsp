<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %> 
<%@ page import="gov.nih.nci.caBIO.search.*,
	 gov.nih.nci.caBIO.bean.*,
	 org.apache.log4j.Logger,
	 gov.nih.nci.nautilus.constants.NautilusConstants" %> 


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

 
 <div><b>Browse Pathway - Name</b></div>

<html:form method="post" action="geneexpression.do">

 <p>
  <input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" / >&nbsp;
  
 </p>
 
 <% 
 Logger logger = Logger.getLogger(NautilusConstants.LOGGER);
 try {
 
      PathwaySearchCriteria pathwaySearch = new PathwaySearchCriteria();
      pathwaySearch.setTaxonId(new Long(5));  //human species is 5 and mouse 6
      Pathway pathway = new Pathway();
	  Pathway[] myPathways;
 	  SearchResult result = pathway.search(pathwaySearch);
	        
		  if (result != null)	  
		  
		  {  logger.debug("result.getCount():"+	result.getCount());	  
		  %>
	
 <div >
  Your search returned <b><%=result.getCount()%> </b> pathways.   

 </div>	
 <div style="width:700px;height:400px;overflow:auto">
 
	 <table border="1" cellpadding="5" cellspacing="0" style="border">
	 
	 <tr style="background-color:#D5E0E9">
	  <td><b>No</b></td>
	  <td><b>Pathway Name</b></td>
	  <td><b>Pathway Title</b></td>	  
	  <!--<td><b>Genes & Pathway Description </b> </td>-->
	  </tr>

	
		
      
		  <%    myPathways = (Pathway[]) result.getResultSet();	   
		        int k=0;    
				for (int i = 0; i < myPathways.length; i++) 
				{  
				  k++;
				  
				  String pathwayName = myPathways[i].getName();
				  if(pathwayName != null){
				    pathwayName = pathwayName.trim();												   
				    pathwayName = pathwayName.substring(2);
				      }
				     
				  
					%>
				<tr>
		          <TD><%=k%></TD>
		          <TD><html:checkbox property="pathwayName" value="<%=pathwayName%>"/> <a href="geneResults.jsp?id=<%=myPathways[i].getId()%>" target="_blank"><%=pathwayName%></a></TD>
		          <TD><%=myPathways[i].getDisplayValue()%></TD>				
				  <!--<TD><a href="geneResults.jsp?id=<%=myPathways[i].getId()%>" target="_blank"> Genes & Pathway Description</a></TD>-->
				</tr>
					<%}
		      }
		   }
		
		catch(Exception ex){
		 logger.error(ex);
		}%>
	
		
 </table>
 </div>
 <p>
  <div></div><input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" / >&nbsp;
  
 </p>


 </html:form>
 
