<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Gene Ontology (GO) Classifications</div>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
	
<!--
<select size="4" multiple style="width:150px;" name="goClassification">
<option>&nbsp;&nbsp;</option>
</select>
-->
<html:textarea property="goClassification">
			</html:textarea>
<html:errors property="goClassification"/>
<input class="sbutton" type="button" value="GO Browser..." onclick="javascript:spawn('http://cgap.nci.nih.gov/Genes/GOBrowser', 780, 500);">
<br>
<html:checkbox property="goMolecularFunction" styleClass="radio" /> Molecular Function
			<html:checkbox property="goBiologicalProcess" styleClass="radio" /> Biological Process
			<html:checkbox property="goCellularComp" styleClass="radio" /> Cellular Component</br>
			<html:errors property="goCellularComp"/></br>
			<html:errors property="goBiologicalProcess"/></br>
			<html:errors property="goMolecularFunction"/></br>
<!--
<input class="radio" type="checkbox" value="" name="goMolecularFunction">Molecular Function&nbsp;&nbsp;
<input class="radio" type="checkbox" value="" name="goBiologicalProcess">Biological Process&nbsp;&nbsp;
<input class="radio" type="checkbox" value="" name="goCellularComp">Cellular Component&nbsp;&nbsp;
-->

<!-- </html:form> -->