	<%@ page import="gov.nih.nci.nautilus.constants.NautilusConstants" %>
<!-- Start Footer Table -->
<div class="content">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td valign="top"><div align="center">
			<a href="http://www.cancer.gov/"><img src="images/footer_nci.gif" width="63" height="31" alt="National Cancer Institute" border="0"></a>
			<a href="http://www.dhhs.gov/"><img src="images/footer_hhs.gif" width="39" height="31" alt="Department of Health and Human Services" border="0"></a>
			<a href="http://www.nih.gov/"><img src="images/footer_nih.gif" width="46" height="31" alt="National Institutes of Health" border="0"></a>
			<a href="http://www.firstgov.gov/"><img src="images/footer_firstgov.gif" width="91" height="31" alt="FirstGov.gov" border="0"></a></div>
		</td>
	</tr>
</table>
<div>
<!-- End Footer table -->
</div>

	<%
	String preview = (String) request.getAttribute("preview");
	if(preview != null && preview.equals("yes"))
	{
		System.out.println("preview");
		// if we have a report to preview
		Object q = (Object) request.getAttribute(NautilusConstants.QUERY_KEY);
		if(q != null)	{
			session.setAttribute(NautilusConstants.QUERY_KEY+"_tmp", q);
			System.out.println("Set prev in req");
		}
		else
			request.setAttribute(NautilusConstants.QUERY_KEY, null);
		%>
		<form name="prev" action="report.do" target="_preview">
			<input type="hidden" name="preview" value="yes">
		</form>
		
		<script type="text/javascript">
			spawnx("report.do", 770, 550, "_report");
		//document.prev.submit();
		</script>
		
		<form
		
	<%
	}
	%>