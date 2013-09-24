<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts.form.*" %> 
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<fieldset class="gray">
<%
	String act = request.getParameter("act") + "_Region_tooltip";

%>
<legend class="red">Region
<app:cshelp topic="<%=act%>" text="[?]"/> 
<!--<app:help help="Specify the chromosomal region of interest.  " />-->
</legend>


	
<br />	<label for="chromosomeNumber">&nbsp;&nbsp;Chromosome Number&nbsp;</label>
	<html:select styleId="chromosomeNumber" property="chromosomeNumber" onchange="javascript:cytobandChange()">
		    <option value=""/>
		    <html:optionsCollection property="chromosomes"/>
		</html:select>	<br />
	<html:errors property="chromosomeNumber"/>

	&nbsp;<br>
	<blockquote>
	<html:radio styleId="cytoband" property="region" value="cytoband" styleClass="radio" />
			<label for="cytoband">Cytoband&nbsp;</label>
			 <html:select styleId="cytobandRegionStart" property="cytobandRegionStart" onclick="javascript:radioFold(this);">
             	<option value=""/>
             	<html:optionsCollection property="cytobands" label="cytoband" value="cytoband"/>	
             </html:select> <label for="cytobandRegionStart">&nbsp;-to-&nbsp;</label>
             <html:select styleId="cytobandRegionEnd" property="cytobandRegionEnd" onclick="javascript:radioFold(this);">
          	<option value=""/>
          	<html:optionsCollection property="cytobands" label="cytoband" value="cytoband"/>	
          </html:select><label for="cytobandRegionEnd">&nbsp;</label>
			<!--  <input type="button" class="sbutton" value="MAP Browser..." disabled="true"> -->
			<html:errors property="cytobandRegion"/>
			<br />
			
	<html:radio styleId="basePairPosition" property="region" value="basePairPosition" styleClass="radio" />
	        <label for="basePairPosition">Base Pair Position (kb)&nbsp; </label>
	        <p style="margin-left:30px">
	        <html:text styleId="basePairStart" property="basePairStart" onclick="javascript:radioFold(this);" />
 			<label for="basePairStart">&nbsp;-to-&nbsp;</label>
 			<html:text styleId="basePairEnd" property="basePairEnd" onclick="javascript:radioFold(this);"/><label for="basePairEnd">&nbsp;</label>
 			</p>
				<html:errors property="basePairEnd" />
	</blockquote>	
<!--
<input type="radio" class="radio" name="region" value="cytoband" checked>&nbsp;
Cytoband &nbsp;<input type="text" name="cytobandRegion">
-->


				<html:errors property="region"/>

				</fieldset>
				
				
				<!--
<input type="radio" class="radio" name="region" value="chnum">&nbsp;
Chromosome Number
<blockquote>
	<input type="text" name="chromosomeNumber">&nbsp;
	Base Pair Position (kb)&nbsp;
	<input type="text" size="10" name="basePairStart">&nbsp;start&nbsp;<input type="text" size="10" name="basePairEnd">&nbsp;end
</blockquote>
-->
<SCRIPT language="Javascript">
function cytobandChange(){
  document.forms[0].multiUseButton.value="GetCytobands";
  document.forms[0].multiUseButton.click();
}
</SCRIPT>


