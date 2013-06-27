<%@ page import="gov.nih.nci.rembrandt.util.RembrandtConstants,
					gov.nih.nci.caintegrator.security.UserCredentials" %>
					
<!--header NCI logo-->
<table width="765" align="center" border="0" cellspacing="0" cellpadding="0" bgcolor="#A90101" summary="This table is used to format page content">
<tr>
<th></th><th></th><th></th>
</tr>
<tr bgcolor="#A90101">
<td width="283" height="37" align="left"><a href="http://www.cancer.gov"><img alt="National Cancer Institute" src="images/logotype.gif" width="283" height="37" border="0"></a></td>
<td>&nbsp;</td>
<td width="295" height="37" align="right"><a href="http://www.cancer.gov"><img alt="National Institute of Health" src="images/tagline.gif" width="295" height="37" border="0"></a></td>
</tr>
</table>

<div align="center" style="padding:0px;">
<!--<p style="text-align:right; padding:0px">
Welcome&nbsp;
<B><% out.println(session.getAttribute("name")); %></b>&nbsp;|&nbsp;
<a href="logout.jsp">Logout</a>
</p>-->

<!--header REMBRANDT image map-->
<% 
String toPage = "menu.do";
UserCredentials credentials = (UserCredentials)session.getAttribute(RembrandtConstants.USER_CREDENTIALS);
if (credentials == null || (credentials.getUserName() != null && credentials.getUserName().equalsIgnoreCase("RBTuser"))) {
	toPage = "login.do";
} 
 %>
<div style="width:765px; border-bottom: 1px solid #000000; margin:0px;">
<map name="headerMap">
<area alt="REMBRANDT application logo" coords="7,8,272,50" href="<%= toPage%>">
</map>
<img src="images/header.jpg" width="765" height="65" alt="REMBRANDT application logo" border="0" usemap="#headerMap">
</div>
<!--end all headers-->

