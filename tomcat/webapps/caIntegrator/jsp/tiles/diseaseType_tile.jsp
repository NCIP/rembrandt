<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<div class="title">Disease Type <b class="req">*</b></div>

	<!-- <html:form action="/geneexpression"> -->
<html:select property="tumorType">
	<html:optionsCollection property="diseaseType" />
</html:select><html:errors property="tumorType"/>

<!--
<select name="tumorType">
<option>All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
<option>Astrocytic</option>
<option>Oligodendroglial</option>
<option>Ependymal cell </option>
<option>Mixed gliomas </option>
<option>Neuroepithelial</option>
<option>Choroid Plexus </option>
<option>Neuronal and mixed neuronal-glial</option>
<option>Pineal Parenchyma </option>
<option>Embryonal</option>
<option>Glioblastoma</option>
</select>
-->
&nbsp;
Grade:&nbsp;
<html:select property="tumorGrade">
				<html:option value="all">All</html:option>
				<html:option value="one">I</html:option>
				<html:option value="two">II</html:option>
				<html:option value="three">III</html:option><br>
				<html:option value="four">IV</html:option>
</html:select><html:errors property="tumorGrade"/>

<!-- </html:form> -->

<!--
<select name="tumorGrade" multiple size="4" class="radio" style="width:60px; vertical-align:middle">
<option selected>All</option>
<option>I</option>
<option>II</option>
<option>III</option>
<option>IV</option>
</select>
-->
<br>
<a href="javascript:void(0);" onmouseover="return selectToolTip(document.forms[0].tumorType);" onmouseout="return nd();">[sub-types]</a>
					