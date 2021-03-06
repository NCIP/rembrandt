<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Disease_tooltip";
%>
<fieldset class="gray">
<legend class="red"><label for="dSelect">Disease Type</label> 
<app:cshelp topic="<%=act%>" text="[?]"/>
</legend>
<br/>
&nbsp;&nbsp;&nbsp;
 
<s:select name="form.tumorType" multiple="true" id="dSelect" disabled="false" 
	list="form.diseaseType" listKey="value" listValue="label" theme="simple" onchange="javascript:onRadio(this, this.value);">
	</s:select>
<s:fielderror fieldName="tumorGrade" />

&nbsp;

<label for="tumorGrade">Grade:&nbsp;</label>
<s:select id="tumorGrade" name="form.tumorGrade" list="form.tumorGrades" disabled="true" theme="simple" />

<b>   </b>

<!-- <b><app:help help="This criteria will be implemented in the upcoming release "/></b>-->

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
					