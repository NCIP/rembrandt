<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*, gov.nih.nci.nautilus.constants.Constants" %> 

<%

	/*
	*
	*	generates the sidebar where all the current queries are listed
	*
	*/

%>
		
		<div>
		<h3>Queries</h3>
		<Table>
		
			<%
				HashMap thisQueryMap = (HashMap) request.getSession().getAttribute(Constants.QUERY_KEY);
				if (thisQueryMap != null) {
					Set keys = thisQueryMap.keySet();
					Iterator i = keys.iterator();
					while (i.hasNext()) {
						Object key = i.next();
			%><tr><td><%=thisQueryMap.get(key).toString()%></td></tr>
			<%		}
				}
			%>

		</div>