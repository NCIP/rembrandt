<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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
strIncFile5 = "/jsp/tiles/tissueType_tile.jsp?act="+act;
strIncFile6 = "/jsp/tiles/segmentMean_tile.jsp?act="+act;
}
%>

<fieldset style="padding:5px;">
<legend class="right" align="right">AND</legend><br clear="both"/><br clear="both"/>

<tiles:insert page="<%= strIncFile %>" flush="false" />
<tiles:insert page="<%= strIncFile4 %>" flush="false" />
<tiles:insert page="<%= strIncFile5 %>" flush="false" />

<html:radio property="copyNumberView" value="calculatedCN" styleClass="radio" onclick="javascript:toggleCopyNumberView('calculatedCN');" /> Calculated Copy Number &nbsp;&nbsp;&nbsp;
<html:radio property="copyNumberView" value="segmentMean" styleClass="radio" onclick="javascript:toggleCopyNumberView('segmentMean');" /> Segment Mean 
<br/>
<br/>

<script type="text/javascript">
	//run this onload
	document.getElementById("segmentMean").style.display = "none";
	
	function toggleCopyNumberView(selectedView) {
		if ( selectedView == "calculatedCN" ) {
			$('calculatedCN').show();
			$('segmentMean').hide();
		} else {
			$('calculatedCN').hide();
			$('segmentMean').show();
		}
	}
</script>

<div id="calculatedCN">
	<tiles:insert page="<%= strIncFile2 %>" flush="false" />
</div>
<div id="segmentMean">
	<tiles:insert page="<%= strIncFile6 %>" flush="false" />
</div>
<tiles:insert page="<%= strIncFile3 %>" flush="false" />

<Br>
</fieldset>
