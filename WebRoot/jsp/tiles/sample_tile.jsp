<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
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

<html:radio styleId="sampleGroup1" property="sampleGroup" value="Specify" styleClass="radio" onfocus="javascript:onRadio(this,0);"/>
<html:text styleId="sampleList" property="sampleList" disabled="false" onfocus="javascript:radioFold(this);" onblur="javascript:cRadio(this, document.forms[0].sampleGroup[0]);" />
<label for="sampleGroup2">&nbsp;-or-&nbsp;</label>
<html:radio styleId="sampleGroup2" property="sampleGroup" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);"/>
<html:select property="sampleFile" styleId="sampleFile" disabled="false"  onblur="javascript:cRadio(this, document.forms[0].sampleGroup[1]);" onfocus="javascript:document.forms[0].sampleGroup[1].checked = true; updateG()">
 	<html:optionsCollection property="savedSampleList" />
</html:select><label for="sampleList">&nbsp;</label><label for="sampleFile">&nbsp;</label>
<script language="javascript">
		updateG();
</script>
<br/>
<html:errors property="sampleFile"/>
<html:errors property="sampleGroup"/>
<html:errors property="sampleList"/>

</fieldset>
