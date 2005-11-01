<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<fieldset class="gray">
<legend class="red">Step 2: Select Statistic

<app:help help="Select a statistic" />
</legend>
<input type="radio" class="radio" name="statistic" value="default" checked />Default<br /><br />

<input type="radio" class="radio" name="statistic" value="advanced" />Advanced
&nbsp;&nbsp;<a href='#' id="pm" class="exp" onclick="javascript:toggleSDiv('advStatistic','pm');return false;">&nbsp;+&nbsp;</a>


<div id="advStatistic" class="divHide">

<fieldset class="gray">
<legend class="red">a)</legend>
 Statistical Method
	<html:select property="statisticalMethod">
		<html:optionsCollection property="statisticalMethodCollection"/>
	</html:select><br /><br />
	
Multiple Comparison Adjustment
	<html:select property="comparisonAdjustment">
		
		<html:optionsCollection property="comparisonAdjustmentCollection"/>
	</html:select> <br /><br />
	</fieldset>
	
	<fieldset class="gray">
<legend class="red">b) Select Constraint
<a href="javascript:void(0);" onmouseover="return overlib('Future implementation', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</legend>

	<html:radio property="foldChange" value="list" styleClass="radio"/>
	&nbsp;&nbsp;Fold Change&nbsp;&ge;
	<html:select property="foldChangeAuto">
		<html:option value="0">&nbsp;</html:option>		
		<html:optionsCollection property="foldChangeAutoList" />
	</html:select>
	
	&nbsp;&nbsp;-OR-&nbsp;&nbsp;
	
	<html:radio property="foldChange" value="specify" styleClass="radio"/>
	&nbsp;&nbsp;Fold Change&nbsp;&ge;	
	<html:text property="foldChangeManual" size ="14" disabled="false" />
	
	<br /><br />
	
	<span id="pfill">
	&nbsp;p-Value&nbsp;&le;
		<html:text property="statisticalSignificance" size="10" />
	</span>
	
</fieldset> 
</fieldset>  