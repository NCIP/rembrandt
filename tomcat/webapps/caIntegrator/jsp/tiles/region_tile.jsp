<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Region</div>

	<!-- <html:form action="/geneexpression"> -->
	<html:radio property="region" value="cytoband"/>
				Cytoband&nbsp; <html:text property="cytobandRegion"/>
<!--
<input type="radio" class="radio" name="region" value="cytoband" checked>&nbsp;
Cytoband &nbsp;<input type="text" name="cytobandRegion">
-->
&nbsp;
<input type="button" class="sbutton" value="MAP Browser..."><br>
<br>
<html:radio property="region" value="chnum"/>&nbsp;
				Chromosome Number</br>
				<blockquote>
					<html:text property="chrosomeNumber"/>&nbsp;
					Base Pair Position (kb)&nbsp; <html:text property="basePairStart"/>
				 	&nbsp;start&nbsp;<html:text property="basePairEnd"/>
					&nbsp;end&nbsp;
				</blockquote>	
				<html:errors property="region"/>
<!--
<input type="radio" class="radio" name="region" value="chnum">&nbsp;
Chromosome Number
<blockquote>
	<input type="text" name="chromosomeNumber">&nbsp;
	Base Pair Position (kb)&nbsp;
	<input type="text" size="10" name="basePairStart">&nbsp;start&nbsp;<input type="text" size="10" name="basePairEnd">&nbsp;end
</blockquote>
-->

<!-- </html:form> -->