<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*, gov.nih.nci.nautilus.query.QueryCollection,gov.nih.nci.nautilus.constants.Constants" %> 
<bean:write name="geneexpressionForm" property="getQueryCollection"/>

<%

	/*
	*
	*	generates the sidebar where all teh current queries are listed
	*
	*/

%>
		
		<div>
		<h3>Queries</h3>
		<Table>
		
			<%
			   QueryCollection queryCollection = (QueryCollection) request.getSession().getAttribute(Constants.QUERY_KEY);
			   if(queryCollection != null){
			      Collection queryColl = queryCollection.getQueries();
				  Iterator i = queryColl.iterator();
				  while (i.hasNext()) { %>
				    <tr><td><%=i.next().toString()%></td></tr>
				    <%}
			     }	
				
				//HashMap thisQueryMap = (HashMap) request.getSession().getAttribute(Constants.QUERY_KEY);
				
				/*if (thisQueryMap != null) {
					Set keys = thisQueryMap.keySet();
					Iterator i = keys.iterator();
					while (i.hasNext()) {
						Object key = i.next();*/
			%><tr><td><%//=thisQueryMap.get(key).toString()%></td></tr>
			<%		//}
				//}
			%>

		</Table>
		</div>
