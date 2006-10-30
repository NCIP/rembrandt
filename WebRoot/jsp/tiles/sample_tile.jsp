<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script language="javascript">
	function updateG(){
    	UserListHelper.getGenericListNamesFromStringWithSubs("PatientDID", "Custom",createSampleList);
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
<legend class="red">Sample Identifier
	<app:help help="Manually enter Sample Ids or select a sample list that you've previously saved." />
</legend>
<br/>	
&nbsp;&nbsp;

<html:radio property="sampleGroup" value="Specify" styleClass="radio" onfocus="javascript:onRadio(this,0);"/>
<html:text property="sampleList" disabled="false" onfocus="javascript:radioFold(this);" onblur="javascript:cRadio(this, document.forms[0].sampleGroup[0]);" />
&nbsp;-or-&nbsp;
<html:radio property="sampleGroup" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);"/>
<html:select property="sampleFile" styleId="sampleFile" disabled="false"  onblur="javascript:cRadio(this, document.forms[0].sampleGroup[1]);" onfocus="javascript:document.forms[0].sampleGroup[1].checked = true; updateG()">
 	<html:optionsCollection property="savedSampleList" />
</html:select>
<script language="javascript">
		updateG();
</script>
<br/>
<html:errors property="sampleFile"/>
<html:errors property="sampleGroup"/>
<html:errors property="sampleList"/>

</fieldset>
