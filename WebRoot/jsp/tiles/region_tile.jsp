<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*, 
	gov.nih.nci.rembrandt.web.struts2.form.*" %> 
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<fieldset class="gray">
<%
	String act = request.getParameter("act") + "_Region_tooltip";

%>
<legend class="red">Region
<app:cshelp topic="<%=act%>" text="[?]"/> 
<!--<app:help help="Specify the chromosomal region of interest.  " />-->
</legend>

<br />	
<label for="chromosomeNumber">&nbsp;&nbsp;Chromosome Number&nbsp;</label>
	<s:select id="chromosomeNumber" name="geneExpressionForm.chromosomeNumber" list="geneExpressionForm.chromosomes" listKey="value" listValue="label"
	onchange="javascript:cytobandChange()" />  
		<br />
<s:actionerror/>

	&nbsp;<br>
	<blockquote>
	
	<input type="radio" name="geneExpressionForm.region" class="radio" id="cytoband" value="cytoband" >
	    <label for="cytoband">Cytoband&nbsp;</label>
			 
	<s:select id="cytobandRegionStart" name="geneExpressionForm.cytobandRegionStart" onclick="javascript:radioFold(this);"
		list="geneExpressionForm.cytobands" listKey="cytoband" listValue="cytoband"> 
	</s:select>	 
		<label for="cytobandRegionStart">&nbsp;-to-&nbsp;</label>
             
    <s:select id="cytobandRegionEnd" name="geneExpressionForm.cytobandRegionEnd" onclick="javascript:radioFold(this);"
		list="geneExpressionForm.cytobands" listKey="cytoband" listValue="cytoband"> 
	</s:select>	         
    <label for="cytobandRegionEnd">&nbsp;</label>
			
	<s:actionerror/>
			
			<br />
	
	<input type="radio" name="geneExpressionForm.region" class="radio" id="basePairPosition" value="basePairPosition" >
	    <label for="basePairPosition">Base Pair Position (kb)&nbsp; </label>
	    
	<p style="margin-left:30px">
	        
	<s:textfield id="basePairStart" name="geneExpressionForm.basePairStart" onclick="javascript:radioFold(this);" />
 		<label for="basePairStart">&nbsp;-to-&nbsp;</label>
 			
 	<s:textfield id="basePairEnd" name="geneExpressionForm.basePairEnd" onclick="javascript:radioFold(this);"/>
 		<label for="basePairEnd">&nbsp;</label>
 			</p>
	<s:actionerror/>
	</blockquote>	

<s:actionerror/>
				

				</fieldset>
				
<SCRIPT language="Javascript">
function cytobandChange(){
  //document.forms[0].multiUseButton.value="GetCytobands";
  //.forms[0].multiUseButton.click();
  
  //javascript:cytobandChange()
  
  document.forms[0].action="getCytobands.action";
  document.forms[0].submit();
}
</SCRIPT>


