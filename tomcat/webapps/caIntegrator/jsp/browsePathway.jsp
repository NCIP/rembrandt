<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %> 
<%@ page import="gov.nih.nci.caBIO.search.*, gov.nih.nci.caBIO.bean.*" %> 


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

 
 <div><b>Browse Pathway - Name</b></div>

<html:form method="post" action="geneexpression.do">

 <p>
  <input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" / >&nbsp;
  
 </p>
 
 <% 
 try {
 
      PathwaySearchCriteria pathwaySearch = new PathwaySearchCriteria();
      pathwaySearch.setTaxonId(new Long(5));  //human species is 5 and mouse 6
      Pathway pathway = new Pathway();
	  Pathway[] myPathways;
 	  SearchResult result = pathway.search(pathwaySearch);
	        
		  if (result != null)	  
		  
		  {  System.out.println("result.getCount():"+	result.getCount());	  
		  %>
	
 <div >
  Your search returned <b><%=result.getCount()%> </b> pathways.   

 </div>	  
   <br>
	 <table align="center" width="85%" cellpadding="5" cellspacing="5" border="1">
	 
	 <tr>
	  <td class="label"><b>No</b></td>
	  <td class="label"><b>Pathway Name</b></td>
	  <td class="label"><b>Pathway Title</b></td>	  
	  <td class="label"><b>Genes & Pathway Description </b> </td>
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
		          <TD><html:checkbox property="pathwayName" value="<%=pathwayName%>"/> <%=pathwayName%></TD>
		          <TD><%=myPathways[i].getDisplayValue()%></TD>				
				  <TD><a href="geneResults.jsp?id=<%=myPathways[i].getId()%>" target="_blank"> Genes & Pathway Description</a></TD>
				</tr>
					<%}
		      }
		   }
		
		catch(Exception ex){
		 ex.printStackTrace();
		}%>
	
		
 </table>
 <p>
  <div></div><input type="button" name="pathwayNames" value="Done"  onclick="javascript:closeData();" / >&nbsp;
  
 </p>


 </html:form>
 
