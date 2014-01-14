<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Go_tooltip";
%>
<fieldset class="gray">
<legend class="red">Gene Ontology (GO) Classifications
<!-- <app:help help="Enter or browse for GO IDs."/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>
</legend>

<script>
	function pickGo(g)	{
		document.getElementById("goClassification").value += (g + "\n");
	}
</script>
<br>
<b class="message"><label for="goClassification">(Type GO format as 'GO:XXXXXXX' where number is XXXXXXX)</label></b><br>
<s:textarea id="goClassification" name="form.goClassification" rows="5" cols="40"></s:textarea>
<s:actionerror/>
<!-- 
<input type="button" class="sbutton" value="GO Browser..." onclick="javascript:spawn('GOTreeServlet/?go', 580, 500);">
-->
<input type="button" class="sbutton" value="GO Browser..." onclick="javascript:spawn('http://www.godatabase.org/cgi-bin/amigo/go.cgi', 780, 500);">

<br/>
<!--<html:checkbox property="goMolecularFunction" styleClass="radio" /> Molecular Function
			<html:checkbox property="goBiologicalProcess" styleClass="radio" /> Biological Process
			<html:checkbox property="goCellularComp" styleClass="radio" /> Cellular Component</br>-->
			

<s:actionerror/>

</fieldset>


