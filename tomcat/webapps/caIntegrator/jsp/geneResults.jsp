<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %> 
<%@ page import="gov.nih.nci.caBIO.search.*, gov.nih.nci.caBIO.bean.*" %> 


 

 <div><b>Gene Information</b></div>
  
 <p>
  
 </p>
 </div>	  
   
	 <table align="center" width="85%" cellpadding="5" cellspacing="5" border="1">
	 
	 <tr>
	  <td class="label">ID</td>		 
	  <td class="label">Title</td>
	  <td class="label">Name/Symbol</td>
	 
	  </tr>
  <%
 try {
      String id = request.getParameter("id");  
	  
	  GeneSearchCriteria geneSearch = new GeneSearchCriteria();
      geneSearch.setPathwayId(new Long(id));  
      Gene gene = new Gene();
	  Gene[] myGenes;
 	  SearchResult result = gene.search(geneSearch);
	        
	  if (result != null)	{ %>
	     Your search returned <b><%=result.getCount()%> </b> genes. 
		 <br>
		 <br>
		<%  myGenes = (Gene[]) result.getResultSet();	  		           
		  for (int i = 0; i < myGenes.length; i++) { 		
			
				%>
				<tr>
		          <TD><%=myGenes[i].getId()%></TD>							         
		          <TD><%=myGenes[i].getTitle()%></TD>
				  <TD><%=myGenes[i].getName()%></TD>
				 </tr>
					<%}
			}
	  
        
    
	  }// end of try
	 catch(Exception ex){
	   ex.printStackTrace();
	  }       
		
		  %>
	



 
 

	
		
      
		
		
		
 </table>
