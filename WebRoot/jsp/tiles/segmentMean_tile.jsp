<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts.form.*" %> 
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	String act = request.getParameter("act");

%>
<fieldset class="gray">
<legend class="red">Segment Mean
<!-- <app:help help="Specify the threshold for the copy number."/>-->
<a href="javascript: Help.popHelp('<%=act%>_Copynum_tooltip');">[?]</a>    

</legend>

Sample Type:&nbsp;&nbsp;
<html:select property="sampleType" styleId="geneType" disabled="false">
	<html:optionsCollection property="sampleTypeColl" />
</html:select>
<br/><br/>

	
<html:radio property="segmentMean" value="amplified" styleClass="radio"/> Amplified &ge;
				<html:text property="smAmplified" onfocus="javascript:radioFold(this);" />&nbsp;mean<br>
<html:errors property="smAmplified"/>
<html:radio property="segmentMean" value="deleted" styleClass="radio"/> Deleted &nbsp;&nbsp;&le;
				<html:text property="smDeleted" onfocus="javascript:radioFold(this);" />&nbsp;mean<br>
<html:errors property="smDeleted"/>			

<html:radio property="segmentMean" value="unchange" styleClass="radio"/>Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="smUnchangeFrom" onfocus="javascript:radioFold(this);" />-to-
				<html:text property="smUnchangeTo" onfocus="javascript:radioFold(this);" />&nbsp;mean
<html:errors property="smUnchangeFrom"/>	
<html:errors property="smUnchangeTo"/>	
<html:errors property="cnerror"/><br>

</fieldset>


