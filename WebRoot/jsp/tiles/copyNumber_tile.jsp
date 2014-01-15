<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<%@ page import="java.util.*, 
	gov.nih.nci.rembrandt.web.struts2.form.*" %> 

<%
	String act = request.getParameter("act") + "_Copynum_tooltip";

%>
<fieldset class="gray">
<legend class="red">Computed Copy Number
<!-- <app:help help="Specify the threshold for the copy number."/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>    

</legend>

<s:actionerror name="copyNumberAllGenesAmp"/></br>
<s:actionerror name="copyNumberAllGenesDel"/></br>
	
<input type="radio" id="copyNumber1" name="form.copyNumber" class="radio" value="amplified" />

<label for="copyNumber1">Amplified</label> &ge;
<s:textfield id="cnAmplified" name="form.cnAmplified" onfocus="javascript:radioFold(this);" />
<label for="cnAmplified">&nbsp;copies</label></br>

<s:actionerror name="cnAmplified"/>
<input type="radio" id="copyNumber2" name="form.copyNumber" value="deleted" class="radio"/> 
<label for="copyNumber2">Deleted</label> &nbsp;&nbsp;&le;
				
<s:textfield id="cnDeleted" name="form.cnDeleted" onfocus="javascript:radioFold(this);" />
<label for="cnDeleted">&nbsp;copies</label></br>
<s:actionerror name="cnDeleted"/>			

<input type="radio" id="copyNumber3" name="form.copyNumber" value="ampdel" class="radio"/> 
<label for="copyNumber3">Amplified or Deleted</label> &nbsp;

<blockquote>
Amplified&nbsp;&nbsp;&nbsp; &ge;
 <s:textfield id="cnADAmplified" name="form.cnADAmplified" onfocus="javascript:radioFold(this);" />
 <label for="cnADAmplified">&nbsp;copies</label>
&nbsp;
<html:errors property="cnADAmplified"/>	
<Br>
Deleted&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &le;
<html:text styleId="cnADDeleted" property="cnADDeleted" onfocus="javascript:radioFold(this);" /><label for="cnADDeleted">&nbsp;copies</label>
&nbsp;
<s:actionerror name="cnADDeleted"/>	
</blockquote>

<input type="radio" id="copyNumber4" name="form.copyNumber" value="unchange" class="radio"/>
<label for="copyNumber4">Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				
<s:textfield id="cnUnchangeFrom" name="form.cnUnchangeFrom" onfocus="javascript:radioFold(this);" />
<label for="cnUnchangeFrom">-to-</label>

<s:textfield id="cnUnchangeTo" name="form.cnUnchangeTo" onfocus="javascript:radioFold(this);" />
<label for="cnUnchangeTo">&nbsp;copies</label>
<s:actionerror name="cnUnchangeFrom"/>	
<s:actionerror name="cnUnchangeTo"/>	
<s:actionerror name="cnerror"/></br>

</fieldset>


