<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, gov.nih.nci.nautilus.ui.bean.SessionQueryBag,gov.nih.nci.nautilus.constants.NautilusConstants" %> 
<div width="100%">
<h3>Queries</h3>
<html:form action ="delete_Query.do">
<script>
 var method;
 var queryKey; 
 function setMode(method, queryKey){   
   document.deleteQueryForm.method.value = method;
   document.deleteQueryForm.queryKey.value = queryKey;  
   }
</script>
		
		 
			<%
			   String  query = "";	
			   int j = 0;	
			   String queryKey = null;
				
			   String pageStr = (String)request.getSession().getAttribute("currentPage");					   
			   String pageStr2 = (String)request.getSession().getAttribute("currentPage2");  
			   
			   SessionQueryBag queryCollection = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
			   if(queryCollection != null){
			     
			      Collection queryColl = queryCollection.getQueries();
				  Collection queryKeys = queryCollection.getQueryNames();
				  
				  Iterator i = queryColl.iterator();
				  
				  while (i.hasNext()) { 
				     j++;
				     query =i.next().toString();
					 	
					 Iterator iter = queryKeys.iterator();
				     while(iter.hasNext()){
				        queryKey = (String)iter.next();
						String queryName = queryCollection.getQuery(queryKey).toString();
						if(query.equalsIgnoreCase(queryName)){					   
						   break;
						  }
					   }
				     				 
					%>
					<Table border="0" style="font-size:.9em">
				    <tr><td><%=query%>
					<%if(pageStr != null && (pageStr2 ==null ||(pageStr2 != null && pageStr2.equals("1"))) ){%>
					     <input type="submit" class="sbutton" value="delete" onclick="setMode('deleteQuery', '<%=queryKey%>')"></td>			
					<%}%>
					</tr>&nbsp;&nbsp;
				    <%}		
			       }	
				%>
		</Table>
		<%if(j !=0 && j>=2){
		   if(pageStr != null && (pageStr2 ==null ||(pageStr2 != null && pageStr2.equals("1")))){%>
		<html:submit styleClass="xbutton" value="delete all queries" onclick="setMode('deleteAll', 'all')"/>
		    <%}
		  }%>
		<html:hidden property="method"/>
		<html:hidden property="queryKey" />
		</html:form>
		<br>
	</div>
