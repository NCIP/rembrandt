<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %> 
<%@ page import="gov.nih.nci.caBIO.search.*, gov.nih.nci.caBIO.bean.*" %> 
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

	
  <%
 try {
      String id = request.getParameter("id");   
	  
      PathwaySearchCriteria pathwaySearch = new PathwaySearchCriteria();
      pathwaySearch.setId(new Long(id));  //human species is 5 and mouse 6
      Pathway pathway = new Pathway();
	  Pathway[] myPathways;
	  String pathwayName = null;
 	  SearchResult pathwayResult = pathway.search(pathwaySearch);
	        
		  if (pathwayResult != null)	  
		  
		  {  %>
		  <p>
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
		  
		    <table align="center" width="85%" cellpadding="5" cellspacing="0" border="1"> 
		    
		 
		  <% myPathways = (Pathway[]) pathwayResult.getResultSet();	 
		       for (int i = 0; i < myPathways.length; i++) 
				{
				 pathwayName = myPathways[i].getName();
				  if(pathwayName != null){
				    pathwayName = pathwayName.trim();												   
				    pathwayName = pathwayName.substring(2);
				      }
				%>
				  <tr>
			        <td style="background-color:#D5E0E9"><b>Pathway Description for "<%=pathwayName%>"</b> </td>
			      </tr>
				  <tr>
				     <TD><%=myPathways[i].getDescription()%></TD>
				   </tr>
				 </table>
		 <% }
	  }%>
	  <p>
	  <div><b>Gene Information for "<%=pathwayName%>"</b></div>  
	  <p>
	  
	 <% GeneSearchCriteria geneSearch = new GeneSearchCriteria();
      geneSearch.setPathwayId(new Long(id));  
      Gene gene = new Gene();
	  Gene[] myGenes;
 	  SearchResult result = gene.search(geneSearch);
	        
	  if (result != null)	{ %>
	     Your search returned <b><%=result.getCount()%> </b> genes. 
		 <br>
		 <br>
		  <table align="center" width="85%" cellpadding="5" cellspacing="0" border="1">
	 
	 <tr>	 
	  <td style="background-color:#D5E0E9"><b>Gene Symbol</b></td>
	  <td style="background-color:#D5E0E9"><b>Gene Name</b></td>	 
	  </tr>
		<%  myGenes = (Gene[]) result.getResultSet();	  		           
		  for (int i = 0; i < myGenes.length; i++) { 		
			
				%>
				<tr>
		          <TD><%=myGenes[i].getName()%></TD>					         
		          <TD><%=myGenes[i].getTitle()%></TD>				  
				 </tr>
					<%}
			}
	  
        
    
	  }// end of try
	 catch(Exception ex){
	   ex.printStackTrace();
	  }       
		
		  %>	
		
 </table>
 
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
				
  
