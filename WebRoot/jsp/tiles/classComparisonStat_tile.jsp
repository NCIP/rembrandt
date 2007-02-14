<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<fieldset class="gray">
<legend class="red">Step 2: Select Statistic

<app:help help="Leave as Default, or select Advanced and click + for more options." />
</legend>
<input type="radio" class="radio" name="statistic" value="default" onclick="tdiv(this,'advStatistic','cc');" checked />Default<br /><br />

<input type="radio" class="radio" name="statistic" value="advanced" />Advanced
&nbsp;&nbsp;<a href='#' id="pm" class="exp" onclick="javascript:toggleSDiv('advStatistic','pm');return false;">&nbsp;+&nbsp;</a>

  <script language="javascript">

	function clearGroupBox(statmethods)	{

	var toBox = document.getElementById('selectedGroups');
	var fromBox = document.getElementById('nonselectedGroups');
	var thestatmethod = document.getElementById('statMethod');
	
	if (toBox.length == 0)
	{
		return;
	}
	
	var fromBoxLength = fromBox.length;
	
	if (toBox.length < 3)
	{
		for (var i = 0; i < thestatmethod.length; i++)
		{
			var tests = thestatmethod[i];
			if (tests.selected == true && tests.value == "FTest")
			{
				var optionText = toBox[toBox.length - 1].text;
				var baselineIndex = optionText.indexOf("baseline");
				if (baselineIndex != -1 && baselineIndex > 0)
				{
					toBox[toBox.length - 1].text = optionText.substring(0, baselineIndex - 1);
				}
			}
			else if (tests.selected == true && (tests.value == "TTest" ||
				 tests.value == "Wilcoxin"))
			{
				var optionText = toBox[toBox.length - 1].text;
				var baselineIndex = optionText.indexOf("baseline");
				if (baselineIndex == -1 )
				{
					toBox[toBox.length - 1].text = optionText + " (baseline)";
				}
			}
		}
		return;
	}
	
	for(i = 0; i < toBox.length; i++) {
		var newOption = new Option();

		newOption.value = toBox[i].value;
		
		if (i == (toBox.length - 1))
		{
			var optionText = toBox[i].text;
			var baselineIndex = optionText.indexOf("baseline");

			if (baselineIndex != -1 && baselineIndex > 0)
			{
				newOption.text = optionText.substring(0, baselineIndex - 1);
			}
			else
			{
				newOption.text = optionText;
			}
		}
		else			
			newOption.text = toBox[i].text;

		fromBox[fromBoxLength + i] = newOption;
	}
	
		toBox.length = 0;
	}
	
</script>

<div id="advStatistic" class="divHide">

<fieldset class="gray">
<legend class="red">a)</legend>
 Statistical Method
	<html:select styleId="statMethod" property="statisticalMethod" onchange="clearGroupBox();">
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
	<html:text property="foldChangeManual" size="14" onblur="absForce(this);" onkeyup="absForce(this);" disabled="false" />
	<script type="text/javascript">
		function absForce(el)	{
			if(el.value < 0)	{
				el.style.border="2px solid red";
				el.value = !isNaN(Math.abs(el.value)) ? Math.abs(el.value) : "";
				alert("Fold Change must be a positive number.  Please verify your entry.");
				return false;
			}
			return true;
		}
	</script>
	<br /><br />
	
	<span id="pfill">
	&nbsp;p-Value&nbsp;&le;
		<html:text property="statisticalSignificance" size="10" />
	</span>
	
</fieldset> 
</fieldset>  