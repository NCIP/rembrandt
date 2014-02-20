<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>
<%
//below, we need to set the form action to the name cooresponding bean/action -
//get if from the request, and make lower to match the struts-config
String actLong = request.getParameter("s").toLowerCase();
String act ="";
   if(actLong.indexOf(':') != -1){
    String[] actLongStrings = actLong.split(":");
    act = actLongStrings[0];
   }
   else
    act = actLong;
   
String strIncFile = "/jsp/tiles/diseaseType_tile.jsp?act="+act;
String strIncFile2 = "";
String strIncFile3 = "";
String strIncFile4 = "/jsp/tiles/sample_tile.jsp?act="+act;
String strIncFile5 = "/jsp/tiles/empty_tile.jsp";
String strIncFile6 = "";


boolean cgh = false;
if(act.equalsIgnoreCase("geneexpression"))	{
	strIncFile2 = "/jsp/tiles/foldChange_tile.jsp?act="+act;
	strIncFile3 = "/jsp/tiles/arrayPlatform_tile.jsp?act="+act;
	strIncFile5 = "/jsp/tiles/resection_tile.jsp?act="+act;
} 
else	{
cgh = true;
strIncFile2 = "/jsp/tiles/copyNumber_tile.jsp?act="+act;
strIncFile3 = "/jsp/tiles/assayPlatform_tile.jsp?act="+act;
strIncFile5 = "/jsp/tiles/analysisType_tile.jsp?act="+act;
strIncFile6 = "/jsp/tiles/segmentMean_tile.jsp?act="+act;
}
%>

<fieldset style="padding:5px;">
<legend class="right" align="right">AND</legend><br clear="both"/><br clear="both"/>

<tiles:insertTemplate template="<%= strIncFile %>" flush="false" />
<tiles:insertTemplate template="<%= strIncFile4 %>" flush="false" />
<tiles:insertTemplate template="<%= strIncFile5 %>" flush="false" />
<%
if(act.equalsIgnoreCase("comparitivegenomic"))	{
%>

<input type="radio" name="copyNumberView" class="radio" checked value="calculatedCN" onclick="javascript:toggleCopyNumberView('calculatedCN');"/>
 Calculated Copy Number &nbsp;&nbsp;&nbsp;
<input type="radio" name="copyNumberView" class="radio" value="segmentMean" onclick="javascript:toggleCopyNumberView('segmentMean');"/>Segment Mean

<br/>
<br/>

<div id="calculatedCN">
	<tiles:insertTemplate template="<%= strIncFile2 %>" flush="false" />
 </div>

<div id="segmentMean"> 
	<tiles:insertTemplate template="<%= strIncFile6 %>" flush="false" /> 
</div>  
<%
} else{%>
<tiles:insertTemplate template="<%= strIncFile2 %>" flush="false" />
<%
}
%>

<tiles:insertTemplate template="<%= strIncFile3 %>" flush="false" />



<Br>
</fieldset>
