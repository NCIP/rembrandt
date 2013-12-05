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
   
String strIncFile = "/jsp/tiles/gene_tile.jsp?act="+act;
String strIncFile2 = "/jsp/tiles/region_tile.jsp?act="+act;
%>

<fieldset style="padding:5px;">
<legend class="right" align="right">Gene/Region</legend><br clear="both"/><br clear="both"/>

<html:radio property="geneRegionView" value="geneView" styleClass="radio" onclick="javascript:toggleGeneRegionView('geneView');" /> Gene View &nbsp;&nbsp;&nbsp;
<html:radio property="geneRegionView" value="regionView" styleClass="radio" onclick="javascript:toggleGeneRegionView('regionView');" /> Region View 
<br/>
<br/>

<div id="geneView">
	<tiles:insertAttribute page="<%= strIncFile %>" flush="false" />
</div>
<div id="regionView">
	<tiles:insertAttribute page="<%= strIncFile2 %>" flush="false" />
</div>

<br/>
</fieldset>
