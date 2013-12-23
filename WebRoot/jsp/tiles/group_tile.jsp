<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts2.form.*,gov.nih.nci.rembrandt.web.bean.*" %> 
<%
	String act = request.getParameter("act");
	String gHelp = request.getParameter("act") + "_Group_tooltip";
	String bHelp = request.getParameter("act") + "_Baseline_tooltip";
%>

<fieldset class="gray">


<s:if test="principalComponentForm != null">
<legend class="red">Step 1: Select Group<b class="req">*</b>
<!-- <app:help help="Search on All Samples, or specify at least two Existing Groups." />-->
<app:cshelp topic="<%=gHelp%>" text="[?]"/>  
</legend>
<input type="radio" name="form.groupsOption" value="allSamples" checked="checked" id="allSamplesRadio" class="radio"><label for="allSamplesRadio">Show all samples</label><br /><br />

<input type="radio" name="form.groupsOption" value="variousSamples" id="variousSamplesRadio" class="radio"><label for="variousSamplesRadio">Select samples</label><br />
<table align="center" border="0" summary="This table is used to format page content">
    <tr style="vertical-align:top">
      <td><label for="nonselectedGroups">Existing Groups</label>
        <br/>
        <s:select id="nonselectedGroups" size="5" multiple="true" style="width:200px" name="form.existingGroups" onclick="radioFold(this);" list="principalComponentForm.existingGroupsList" listKey="value" listValue="label" ondblclick="move(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'));" theme="simple">
		</s:select>
      </td>
      <td style="vertical-align:middle">
        <input id="button1" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'))" value="<<" type="button"  /><br />
        <input id="button2" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'))" value=">>" type="button"  />
      </td>
      <td><label for="selectedGroups">Selected Groups</label>
        <br/>

        <s:select id="selectedGroups" size="5" multiple="true" style="width:200px" name="form.selectedGroups" ondblclick="move(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'));" list="#{}" theme="simple">
        	
		</s:select>
      </td>
    </tr>
    
  </table>
</s:if>

<!-- s:if test="%{#parameters.act[0] == 'gpintegration' ||#parameters.act[0] == 'igvintegration' }" -->
<% if (act.equals("gpintegration") || act.equals("igvintegration")) { %>
<legend class="red">Step 1: Select Group<b class="req">*</b>
<!-- <app:help help="Search on All Samples, or specify at least two Existing Groups." />-->
<app:cshelp topic="<%=gHelp%>" text="[?]"/>   
</legend>


 <!-- s:if test="%{#parameters.act[0] == 'gpintegration'}" -->
 <% if (act.equals("gpintegration")) { %>
 	<s:actionerror name="selectedGroups"/>Select 2 or More Groups <br />
 <% } %> 	
 <!-- /s:if -->
  <!-- s:if test="%{#parameters.act[0] == 'igvintegration'}" -->
 <% if (act.equals("igvintegration")) { %>
 	<s:actionerror name="selectedGroups"/>Select 1 or More Groups <br />
 <% } %> 	
 <!-- /s:if -->

<table align="center" border="0" summary="This table is used to format page content">
    <tr style="vertical-align:top">
      <td><label for="nonselectedGroups">Existing Groups</label>
        <br/>
        <s:select id="nonselectedGroups" size="5" multiple="true" style="width:200px" name="form.existingGroups" list="form.existingGroupsList" listKey="value" listValue="label" onclick="radioFold(this);" ondblclick="move(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'));" theme="simple">
		</s:select>
      </td>
      <td style="vertical-align:middle">
      		<input onclick="move($('selectedGroups'),$('nonselectedGroups'));initBaseline();" value="&lt;&lt;" type="button"/><br />
			<input onclick="move($('nonselectedGroups'),$('selectedGroups'));initBaseline();" value=">>" type="button"/>
	
       <!--
       <input id="button1" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'))" value="<<" type="button"  /><br />
        <input id="button2" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'))" value=">>" type="button"  />
     
     -->
      </td>
      <td><label for="selectedGroups">Selected Groups</label>
        <br/>

        <s:select id="selectedGroups" size="5" multiple="true" style="width:200px" name="form.selectedGroups" list="#{}" ondblclick="move(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'));" theme="simple">
        	
		</s:select>
      </td>
    </tr>
    
  </table>
  <!-- /s:if -->
  <% } %>



<s:if test="classComparisonForm != null">
<legend class="red">Step 1: Select Group<b class="req">*</b>
<app:cshelp topic="<%=gHelp%>" text="[?]"/>  
<!-- <app:help help="Select two or more Existing Groups, and click >> to move them to Selected Groups. " />-->
</legend>
<br clear="both"/>
	<em>choose 2 or more groups</em>
	<s:actionerror name="selectedGroups"/>
	<table align="center" border="0" summary="This table is used to format page content">
    <tr style="vertical-align:top">
      <td><label for="nonselectedGroups">Existing Groups</label>
        <br/>
        <s:select id="nonselectedGroups" size="5" multiple="true" style="width:200px" name="form.existingGroups" list="classComparisonForm.existingGroupsList" listKey="value" listValue="label" ondblclick="move(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'));initBaseline();" theme="simple">
		</s:select>
      </td>
      <td style="vertical-align:middle">
        <input onclick="move(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'));initBaseline();" value="&lt;&lt;" type="button"/><br />
        <input onclick="move(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'));initBaseline();" value=">>" type="button"/>
      </td>
      <td><label for="selectedGroups">Selected Groups</label>
        <br/>

        <s:select id="selectedGroups" size="5" multiple="true" style="width:200px; overflow:none;" name="form.selectedGroups" list="#{}" ondblclick="move(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups')); initBaseline();" theme="simple">
        	
		</s:select>
	<br/>	
	<div style="padding-top:10px; padding-left:5px;">
	<span style="cursor:pointer; border:1px solid; padding-right:3px; padding-left:3px;" onclick="javascript:moveUpList(document.getElementById('selectedGroups'));initBaseline();">&uarr;</span>
	<span style="cursor:pointer; border:1px solid; padding-right:3px; padding-left:3px;" onclick="javascript:moveDownList(document.getElementById('selectedGroups'));initBaseline();">&darr;</span>
	<span style="font-size:10px; font-family:arial; padding:10px;">
		Baseline
		<app:cshelp topic="<%=bHelp%>" text="[?]"/>  
		<!-- <app:help help="Use up or down arrow to move the group to the last Selected Groups position. (baseline) appears.  " />-->
		: <span id="baseline">none</span>
		<input type="hidden" name="form.baselineGroup" id="baselineGroup"/>
	</span>
	</div>
      </td>
    </tr>
    
  </table>
  
  <script language="javascript">
	var lst = document.getElementById('selectedGroups');
	var nonlst = document.getElementById('nonselectedGroups');
	
	var bltag = " (baseline)";

	function initBaseline()	{
		
		//Only baseline a test that is not FTest.
		var statmethod = document.getElementById('statMethod');
		
		var thebaseline = true;

		for (var i = 0; i < statmethod.length; i++)
		{
			var tests = statmethod[i];
			if (tests.selected == true && tests.value == "FTest")
			{
				thebaseline = false;
			}
		}

		var baseline = lst[lst.length-1];
		
		//remove the tag from the other ones
		for(var i=0; i<lst.length - 1; i++)	{
			var currentBaseline = lst[i];
			
			if(currentBaseline.text.indexOf(bltag) != -1)	{
				currentBaseline.text = currentBaseline.text.substring(0, currentBaseline.text.indexOf(bltag));
				currentBaseline.style.color = '';
				currentBaseline.style.border = '';
			}
		}
		
		for(var i=0; i<nonlst.length; i++)	{
			var currentBaseline = nonlst[i];
			if(currentBaseline.text.indexOf(bltag) != -1)	{
				currentBaseline.text = currentBaseline.text.substring(0, currentBaseline.text.indexOf(bltag));
				currentBaseline.style.color = '';
				currentBaseline.style.border = '';
			}
		}		
		
		//If user selects three or more groups, make it FTest.
		if (lst.length > 2)
		{
			for (var i = 0; i < statmethod.length; i++)
			{
				var tests = statmethod[i];
				if (tests.value == "FTest")
				{
					tests.selected = true;
				}
			}
			return;
		}
		
		//add the tag to the new baseline
		if(baseline != null && baseline.text.indexOf(bltag) == -1 && thebaseline == true)	{
			var tmp = baseline.text;
			baseline.text += bltag;
			baseline.style.color="red";
			baseline.style.border="1px solid";
			document.getElementById("baseline").innerHTML = tmp.length > 10 ? tmp.substring(0, 10)+"..." : tmp ;
			document.getElementById("baselineGroup").value = baseline.value;
		}
		
		if(lst.length<1)	{
			document.getElementById("baseline").innerHTML = "None";
			document.getElementById("baselineGroup").value = "";
		}
	
	}
	
	//initBaseline();
</script>
</s:if>

</fieldset>
<br clear="all"/>
