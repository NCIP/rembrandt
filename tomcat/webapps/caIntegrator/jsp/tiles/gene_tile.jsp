<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
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
<a href="javascript:void(0);" onmouseover="return overlib('Paste a comma separated Gene list, or upload file using Browse button', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</legend>

<br>
<html:radio property="geneGroup" value="Specify" styleClass="radio" onclick="javascript:onRadio(this,0);"/>
			<html:select property="geneType" disabled="false">
			    <html:optionsCollection property="geneTypeColl" />
			</html:select>
<html:text property="geneList" disabled="false" value=""/>
<a href="javascript:void(0);" onmouseover="return overlib('Selected Criteria on this form applies to all genes specified in this list.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
&nbsp;-or-&nbsp;
			<html:radio property="geneGroup" value="Upload" styleClass="radio" onclick="javascript:onRadio(this,1);"/>
			<html:file property="geneFile" disabled="true" />
			<Br>
			<html:errors property="geneFile"/>
			<html:errors property="geneGroup"/>
			<html:errors property="geneList"/>
			<html:errors property="geneType"/></br>
	</fieldset>		
			

<!-- </html:form> -->
