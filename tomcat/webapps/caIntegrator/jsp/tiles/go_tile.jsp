<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">Gene Ontology (GO) Classifications</legend>
<%
	String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<br>
<html:textarea property="goClassification" rows="5" cols="40"></html:textarea>
<html:errors property="goClassification"/>
<input type="button" class="sbutton" value="GO Browser..." disabled="true" onclick="javascript:spawn('http://cgap.nci.nih.gov/Genes/GOBrowser', 780, 500);">
<br>
<!--<html:checkbox property="goMolecularFunction" styleClass="radio" /> Molecular Function
			<html:checkbox property="goBiologicalProcess" styleClass="radio" /> Biological Process
			<html:checkbox property="goCellularComp" styleClass="radio" /> Cellular Component</br>-->
			<html:errors property="goCellularComp"/>&nbsp;&nbsp;
			<html:errors property="goBiologicalProcess"/>&nbsp;&nbsp;
			<html:errors property="goMolecularFunction"/>

</fieldset>

<!-- </html:form> -->