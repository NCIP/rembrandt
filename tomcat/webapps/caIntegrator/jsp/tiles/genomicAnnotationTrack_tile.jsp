<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
String act = request.getParameter("act");
%>

<!-- <html:form action="<%=act%>"> -->
<div class="title">Genomic Annotation Track
<a href="javascript:void(0);" onmouseover="return overlib('Via DAS.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</div>
<html:text property="genomicTrack"/>&nbsp;<input type="button" class="sbutton" value="Genomic Browser..." style="width:150px;">

<!-- </html:form> -->