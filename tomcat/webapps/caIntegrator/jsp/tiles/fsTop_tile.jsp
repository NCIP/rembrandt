<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>
<%
//below, we need to set the form action to the name cooresponding bean/action -
//get if from the request, and make lower to match the struts-config
String act = request.getParameter("s").toLowerCase();
String strIncFile = "/jsp/tiles/diseaseType_tile.jsp?act="+act;
String strIncFile2 = "";

if(act.equalsIgnoreCase("geneExpression"))	{
	strIncFile2 = "/jsp/tiles/diseaseType_tile.jsp?act="+act;
} 
else	{
strIncFile2 = "/jsp/tiles/copyNumber_tile.jsp?act="+act;
}
%>

<fieldset style="padding:5px;">
<legend align="right">AND</legend>
<tiles:insert page="<%= strIncFile %>" flush="false" />
<tiles:insert page="<%= strIncFile2 %>" flush="false" />
<Br>
</fieldset>
