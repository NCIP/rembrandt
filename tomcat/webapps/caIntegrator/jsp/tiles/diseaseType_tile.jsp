<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">Disease Type <b class="req">*</b></legend>
<br>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
	 &nbsp;&nbsp;&nbsp;<html:select property="tumorType">
   <html:optionsCollection property="diseaseType" />
</html:select><html:errors property="tumorType"/>

&nbsp;
Grade:&nbsp;
<html:select property="tumorGrade" disabled="true">
				<html:option value="all">All</html:option>
				<html:option value="one">I</html:option>
				<html:option value="two">II</html:option>
				<html:option value="three">III</html:option><br>
				<html:option value="four">IV</html:option>
</html:select><html:errors property="tumorGrade"/>

<!-- </html:form> -->


<br>
&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onmouseover="return selectToolTip(document.forms[0].tumorType);" onmouseout="return nd();">[sub-types]</a>
</fieldset>
					