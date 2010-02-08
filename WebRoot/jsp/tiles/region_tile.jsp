<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts.form.*" %> 
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<fieldset class="gray">
<%
	String act = request.getParameter("act");

%>
<legend class="red">Region
<a href="javascript: Help.popHelp('<%=act%>_Region_tooltip');">[?]</a> 
<!--<app:help help="Specify the chromosomal region of interest.  " />-->
</legend>


	
<br />	&nbsp;&nbsp;Chromosome Number&nbsp;
	<html:select property="chromosomeNumber" onchange="javascript:cytobandChange()">
		    <option value=""/>
		    <html:optionsCollection property="chromosomes"/>
		</html:select>	<br />
	<html:errors property="chromosomeNumber"/>

	&nbsp;<br>
	<blockquote>
	<html:radio property="region" value="cytoband" styleClass="radio" />
			Cytoband&nbsp;
			 <html:select property="cytobandRegionStart" onclick="javascript:radioFold(this);">
             	<option value=""/>
             	<html:optionsCollection property="cytobands" label="cytoband" value="cytoband"/>	
             </html:select> &nbsp;-to-&nbsp;
             <html:select property="cytobandRegionEnd" onclick="javascript:radioFold(this);">
          	<option value=""/>
          	<html:optionsCollection property="cytobands" label="cytoband" value="cytoband"/>	
          </html:select>
			<!--  <input type="button" class="sbutton" value="MAP Browser..." disabled="true"> -->
			<html:errors property="cytobandRegion"/>
			<br />
			
	<html:radio property="region" value="basePairPosition" styleClass="radio" />
	        Base Pair Position (kb)&nbsp; 
	        <p style="margin-left:30px">
	        <html:text property="basePairStart" onclick="javascript:radioFold(this);" />
 			&nbsp;-to-&nbsp;
 			<html:text property="basePairEnd" onclick="javascript:radioFold(this);"/>
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


