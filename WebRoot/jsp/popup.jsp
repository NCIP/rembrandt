<%@ page import="gov.nih.nci.nautilus.constants.NautilusConstants" %>
	<%
		Object q = (Object) request.getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
		if(q != null)	{
			session.setAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY+"_tmp", q);
		}
		else
			request.setAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY, null);
	%>
		<script type="text/javascript">
			spawnx("report.do", 770, 550, "_report");
		</script>
	<%
//	}
	%>