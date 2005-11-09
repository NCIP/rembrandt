<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts.form.*" %> 
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<fieldset class="gray">
<legend class="red">CGH Copy Number
<app:help help="Specify the threshold for the copy number by indicating the “amplified”, “deleted” or “unchanged” criteria. If you are creating an “All Genes” query, you must select an amplification threshold > 10 or a deletion threshold < 1."/>
</legend><br />

<%
	String act = request.getParameter("act");

%>
	
	
<html:errors property="copyNumberAllGenesAmp"/></br>
<html:errors property="copyNumberAllGenesDel"/></br>
	
<html:radio property="copyNumber" value="amplified" styleClass="radio"/> Amplified &ge;
				<html:text property="cnAmplified" onfocus="javascript:radioFold(this);" />&nbsp;copies</br>
<html:errors property="cnAmplified"/>
<html:radio property="copyNumber" value="deleted" styleClass="radio"/> Deleted &nbsp;&nbsp;&le;
				<html:text property="cnDeleted" onfocus="javascript:radioFold(this);" />&nbsp;copies</br>
<html:errors property="cnDeleted"/>			
<html:radio property="copyNumber" value="ampdel" styleClass="radio"/> Amplified or Deleted &nbsp;

<blockquote>
Amplified&nbsp;&nbsp;&nbsp; &ge;
 <html:text property="cnADAmplified" onfocus="javascript:radioFold(this);" />&nbsp;copies
&nbsp;
<html:errors property="cnADAmplified"/>	
<Br>
Deleted&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &le;
<html:text property="cnADDeleted" onfocus="javascript:radioFold(this);" />&nbsp;copies
&nbsp;
<html:errors property="cnADDeleted"/>	
</blockquote>
<html:radio property="copyNumber" value="unchange" styleClass="radio"/>Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:text property="cnUnchangeFrom" onfocus="javascript:radioFold(this);" />-to-
				<html:text property="cnUnchangeTo" onfocus="javascript:radioFold(this);" />&nbsp;copies
<html:errors property="cnUnchangeFrom"/>	
<html:errors property="cnUnchangeTo"/>	
<html:errors property="cnerror"/></br>

</fieldset>


