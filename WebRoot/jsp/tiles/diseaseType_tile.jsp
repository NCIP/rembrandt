<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Disease Type <b class="req">*</b>
<app:help help="Enter the type of diseases using the pick list. “Grade” will be implemented in a future version."/>
</legend>
<br>
<%
	String act = request.getParameter("act");

%>
	<!-- <html:form action="<%=act%>" method="get"> -->
	 &nbsp;&nbsp;&nbsp;<html:select property="tumorType">
   <html:optionsCollection property="diseaseType" />
</html:select><html:errors property="tumorType"/>

&nbsp;
Grade:&nbsp;

<html:select property="tumorGrade" disabled="false">
				<html:option value="all">All</html:option>
				<html:option value="one">I</html:option>
				<html:option value="two">II</html:option>
				<html:option value="three">III</html:option><br>
				<html:option value="four">IV</html:option>
</html:select>
<b><app:help help="This criteria will be implemented in the upcoming release "/></b>
<html:errors property="tumorGrade"/>

<!-- </html:form> -->


<br>
&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onmouseover="return selectToolTip(document.forms[0].tumorType);" onmouseout="return nd();">[sub-types]</a>
</fieldset>
					