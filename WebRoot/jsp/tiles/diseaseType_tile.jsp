<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Disease Type 
<app:help help="Select the type(s) of disease. Mouse over a name to display tumor sub-types. "/>
</legend>
<br/>

	
	 &nbsp;&nbsp;&nbsp;
<html:select multiple="true" styleId="dSelect" property="tumorType" onchange="javascript:onRadio(this, this.value);">
   <html:optionsCollection property="diseaseType" />
</html:select><html:errors property="tumorType"/>

&nbsp;
Grade:&nbsp;

<html:select property="tumorGrade" disabled="false">
				<html:option value="all">All</html:option>
				<html:option value="one">I</html:option>
				<html:option value="two">II</html:option>
				<html:option value="three">III</html:option>
				<html:option value="four">IV</html:option>
				
</html:select>
<b><app:help help="This criteria will be implemented in the upcoming release "/></b>
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
					