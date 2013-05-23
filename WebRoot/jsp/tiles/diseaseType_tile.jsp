<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Disease_tooltip";
%>
<fieldset class="gray">
<legend class="red"><label for="dSelect">Disease Type</label> 
<!-- <app:help help="Select the type(s) of disease. Mouse over a name to display tumor sub-types. "/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>
</legend>
<br/>

	
	 &nbsp;&nbsp;&nbsp;
<html:select multiple="true" styleId="dSelect" property="tumorType" onchange="javascript:onRadio(this, this.value);">
   <html:optionsCollection property="diseaseType" />
</html:select><html:errors property="tumorType"/>

&nbsp;
<label for="tumorGrade">Grade:&nbsp;</label>

<html:select styleId="tumorGrade" property="tumorGrade" disabled="true">
				<html:option value="all">All</html:option>
				<html:option value="one">I</html:option>
				<html:option value="two">II</html:option>
				<html:option value="three">III</html:option>
				<html:option value="four">IV</html:option>
				
</html:select>
<b>   </b>

<!-- <b><app:help help="This criteria will be implemented in the upcoming release "/></b>-->
<html:errors property="tumorGrade"/>

<script type="text/javascript">
var ops = $('dSelect').options;

var subhtm = "";
for(var i=0; i<ops.length;i++)	{

	if(ie)	{
		if( selectToolTip( $('dSelect').options[i], "y" ) != "")	{
			subhtm += "<a href=\"#\" onmouseout=\"return nd();\" onmouseover=\"return selectToolTip(\'"+$('dSelect').options[i].text + "\');\">"+$('dSelect').options[i].text+"</a> ";
		}
	}
	else	{
		$('dSelect').options[i].onmouseover = function()	{ return selectToolTip(this);};
		$('dSelect').options[i].onmouseout = function() { return nd();};
	}
	
	if($('dSelect').options[i].text == "ALL")	{
		$('dSelect').options[i].text = "ALL GLIOMA";
	}
}
</script>


<br/>
<b class="message">Mouseover disease types and any relevant sub-type will be displayed</b>
<br/><span id="ieStinks"></span>
<script language="javascript">
if(subhtm!="")	{
	$('ieStinks').innerHTML = subhtm;
}
</script>
<!-- 
&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onmouseover="return selectToolTip(document.forms[0].tumorType);" onmouseout="return nd();">[sub-types]</a>
-->
</fieldset>
					