<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act");
%>
<fieldset class="gray">
<legend class="red">Gene Ontology (GO) Classifications
<!-- <app:help help="Enter or browse for GO IDs."/>-->
<a href="javascript: Help.popHelp('<%=act%>_Go_tooltip');">[?]</a>  
</legend>

<script>
	function pickGo(g)	{
		document.getElementById("goClassification").value += (g + "\n");
	}
</script>
<br>
<b class="message">(Type GO format as 'GO:XXXXXXX' where number is XXXXXXX)</b><br>
<html:textarea styleId="goClassification" property="goClassification" rows="5" cols="40"></html:textarea>
<html:errors property="goClassification"/>
<!-- 
<input type="button" class="sbutton" value="GO Browser..." onclick="javascript:spawn('GOTreeServlet/?go', 580, 500);">
-->
<input type="button" class="sbutton" value="GO Browser..." onclick="javascript:spawn('http://www.godatabase.org/cgi-bin/amigo/go.cgi', 780, 500);">

<br/>
<!--<html:checkbox property="goMolecularFunction" styleClass="radio" /> Molecular Function
			<html:checkbox property="goBiologicalProcess" styleClass="radio" /> Biological Process
			<html:checkbox property="goCellularComp" styleClass="radio" /> Cellular Component</br>-->
			<html:errors property="goCellularComp"/>&nbsp;&nbsp;
			<html:errors property="goBiologicalProcess"/>&nbsp;&nbsp;
			<html:errors property="goMolecularFunction"/>

</fieldset>


