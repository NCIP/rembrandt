<%@ page import="gov.nih.nci.nautilus.constants.NautilusConstants" %>
	<%
//	String preview = (String) request.getAttribute("preview");
//	if(preview != null && preview.equals("yes"))
//	{
		Object q = (Object) request.getAttribute(NautilusConstants.QUERY_KEY);
		if(q != null)	{
			session.setAttribute(NautilusConstants.QUERY_KEY+"_tmp", q);
		}
		else
			request.setAttribute(NautilusConstants.QUERY_KEY, null);
	%>
		<script type="text/javascript">
			spawnx("report.do", 770, 550, "_report");
		</script>
	<%
//	}
	%>