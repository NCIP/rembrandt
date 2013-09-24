<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts.form.*" %> 
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	String act = request.getParameter("act") + "_Copynum_tooltip";

%>
<fieldset class="gray">
<legend class="red">Computed Copy Number
<!-- <app:help help="Specify the threshold for the copy number."/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>    

</legend>

<html:errors property="copyNumberAllGenesAmp"/></br>
<html:errors property="copyNumberAllGenesDel"/></br>
	
<html:radio styleId="copyNumber1" property="copyNumber" value="amplified" styleClass="radio"/> <label for="copyNumber1">Amplified</label> &ge;
				<html:text styleId="cnAmplified" property="cnAmplified" onfocus="javascript:radioFold(this);" /><label for="cnAmplified">&nbsp;copies</label></br>
<html:errors property="cnAmplified"/>
<html:radio styleId="copyNumber2" property="copyNumber" value="deleted" styleClass="radio"/> <label for="copyNumber2">Deleted</label> &nbsp;&nbsp;&le;
				<html:text styleId="cnDeleted" property="cnDeleted" onfocus="javascript:radioFold(this);" /><label for="cnDeleted">&nbsp;copies</label></br>
<html:errors property="cnDeleted"/>			
<html:radio styleId="copyNumber3" property="copyNumber" value="ampdel" styleClass="radio"/> <label for="copyNumber3">Amplified or Deleted</label> &nbsp;

<blockquote>
Amplified&nbsp;&nbsp;&nbsp; &ge;
 <html:text styleId="cnADAmplified" property="cnADAmplified" onfocus="javascript:radioFold(this);" /><label for="cnADAmplified">&nbsp;copies</label>
&nbsp;
<html:errors property="cnADAmplified"/>	
<Br>
Deleted&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &le;
<html:text styleId="cnADDeleted" property="cnADDeleted" onfocus="javascript:radioFold(this);" /><label for="cnADDeleted">&nbsp;copies</label>
&nbsp;
<html:errors property="cnADDeleted"/>	
</blockquote>
<html:radio styleId="copyNumber4" property="copyNumber" value="unchange" styleClass="radio"/><label for="copyNumber4">Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<html:text styleId="cnUnchangeFrom" property="cnUnchangeFrom" onfocus="javascript:radioFold(this);" /><label for="cnUnchangeFrom">-to-</label>
				<html:text styleId="cnUnchangeTo" property="cnUnchangeTo" onfocus="javascript:radioFold(this);" /><label for="cnUnchangeTo">&nbsp;copies</label>
<html:errors property="cnUnchangeFrom"/>	
<html:errors property="cnUnchangeTo"/>	
<html:errors property="cnerror"/></br>

</fieldset>


