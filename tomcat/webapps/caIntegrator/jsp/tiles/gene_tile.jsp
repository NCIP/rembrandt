<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<% 
/*
Gene Tile -
used in: GeneExpression form, CGH form
*/

String act = request.getParameter("act");
%>
	<!-- <html:form action="<%=act%>"> -->
<fieldset class="gray">
<legend class="red">Gene
<app:help help="Choose one type of Gene identifiers (Genbank ID, LocusLink ID or Gene symbol) from the pick list. Then enter the corresponding comma delimited value or IDs for the genes to be searched in the text box. Another option is to load a list of genes is to upload a file using the Browse button, file must be of type *.txt with each entry in a new line. Upper limit for this option is 500 entries in the txt file." />
</legend>

<br>

			&nbsp;&nbsp;<html:select property="geneType" disabled="false">
			    <html:optionsCollection property="geneTypeColl" />
			</html:select>
<html:radio property="geneGroup" value="Specify" styleClass="radio" onfocus="javascript:onRadio(this,0);"/>
<html:text property="geneList" disabled="false" onfocus="javascript:radioFold(this);" value=""/>
<!-- <a href="javascript:void(0);" onmouseover="return overlib('Selected Criteria on this form applies to all genes specified in this list.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>-->
&nbsp;-or-&nbsp;
			<html:radio property="geneGroup" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);"/>
			<html:file property="geneFile" disabled="true" />
			<Br>
			<html:errors property="geneFile"/>
			<html:errors property="geneGroup"/>
			<html:errors property="geneList"/>
			<html:errors property="geneType"/></br>
	</fieldset>		
			

<!-- </html:form> -->
