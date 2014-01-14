<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script language="javascript">
	function updateG(){
    	UserListHelper.getGenericListNamesFromString("PatientDID", createSampleList);
	}
	function createSampleList(data){   
		var t = $('sampleFile').value;	
		
    	DWRUtil.removeAllOptions("sampleFile", data);
    	DWRUtil.addOptions("sampleFile", ['none']) 
    	DWRUtil.addOptions("sampleFile", data);
    	
    	$('sampleFile').value = t;
    			
    	if($('sampleFile').options.length<2)	{
    		$('sampleFile').disabled = true;
    	}
    	
	}
</script>
<fieldset class="gray">
<%
	String act = request.getParameter("act") + "_Sample_tooltip";

%>
<legend class="red"><label for="sampleGroup1">Sample Identifier</label>
	<!-- <app:help help="Enter comma-delimited IDs or select a saved list." />-->
<app:cshelp topic="<%=act%>" text="[?]"/>   
	
</legend>
<br/>	
&nbsp;&nbsp;
  
<input type="radio" name="form.sampleGroup" class="radio" id="sampleGroup1" value="Specify" onfocus="javascript:onRadio(this,0);" />
<s:textfield id="sampleList" name="form.sampleList" disabled="false" onfocus="javascript:radioFold(this);" 
	onblur="javascript:cRadio(this, document.forms[0].sampleGroup[0]);" />


<label for="sampleGroup2">&nbsp;-or-&nbsp;</label>
<input type="radio" name="form.sampleGroup" class="radio" id="sampleGroup2" value="Upload" onfocus="javascript:onRadio(this,1);" />

<s:select name="form.sampleFile" id="sampleFile" disabled="false" list="form.savedSampleList"
	onblur="javascript:cRadio(this, document.forms[0].sampleGroup[1]);" onfocus="javascript:document.forms[0].sampleGroup[1].checked = true; updateG()" />
<label for="sampleList">&nbsp;</label><label for="sampleFile">&nbsp;</label>

<script language="javascript">
		updateG();
</script>
<br/>

<s:actionerror />
</fieldset>
