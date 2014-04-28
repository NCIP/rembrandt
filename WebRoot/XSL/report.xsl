<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output /> 

<xsl:param name="filter_value1"></xsl:param>

<xsl:param name="filter_value2">0</xsl:param>
<xsl:param name="filter_value3">25</xsl:param>

<xsl:param name="filter_value4">gt</xsl:param>

<xsl:param name="filter_value5"></xsl:param>
<xsl:param name="filter_value6"></xsl:param>

<xsl:param name="showSampleSelect"></xsl:param>

<xsl:param name="allowShowAllValues">true</xsl:param>
<xsl:param name="queryDetails">N/A</xsl:param>
<xsl:param name="statusMsg"></xsl:param>
<xsl:param name="showAllValues">false</xsl:param>

<xsl:variable name="showSaveSamples">
	<xsl:choose>
		<xsl:when test="$statusMsg != '' and contains($statusMsg, 'aved')">false</xsl:when>
		<xsl:otherwise>true</xsl:otherwise>		
	</xsl:choose>
</xsl:variable>

<xsl:template match="/">
  
  <body onload="javascript:A_clearTmpSamples();return false;">
  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;">Help</div>

  <div style="background-color: #ffffff"><img alt="Rembrandt" src="images/smallHead.jpg" /></div>
  <p align="center" style="background:red; color:#ffffff; font-size:12px; font-weight:bold;"><xsl:value-of select="$statusMsg" /></p>

   <xsl:for-each select="Report">

	<xsl:variable name="reportMsg" select="@msg" />
	<xsl:variable name="helpLink" select="@helpLink" />
	<xsl:variable name="colCount" select="count(Row[2]/Cell)" />
	<xsl:variable name="recordCount" select="count(Row[@name='dataRow'])" />
	
	<xsl:if test="$recordCount > 0">
	
	<xsl:variable name="qName" select="@queryName" />
	<xsl:variable name="rType" select="@reportType" />
	
    <span style="z-index:900; float:right;position:absolute;top:10px;right:10px;">
	  <!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	  
	  <a href="#" onclick="javascript:window.close();" title="Close this report."><img alt="Close" align="right" src="images/close.png" border="0" /> </a> 
	  <a href="javascript:openHelpWindow('{$helpLink}');" title="Click here for additional information about this report."><img alt="Help" align="right" src="images/help.png" border="0"  /></a>
	  <a href="#" onclick="javascript:stupidXSLEscape('{$qName}', '{$rType}')" title="Download for Excel."><img align="right" src="images/excel.png" border="0" alt="download for excel" /></a>
	  <a href="#" onclick="javascript:window.print();" title="Print this report."><img alt="Print" align="right" src="images/print.png" border="0" /> </a> 
	  <a href="#queryInfo" title="View Query Information."><img alt="View Query Info" align="right" src="images/text.png" border="0" /></a>
	  <a href="#" onclick="javascript:toggleDiv('hideme');return false;" title="Show or Hide Report Tools."><img alt="Show or Hide Report Tools" align="right" src="images/tools.png" border="0" /></a>
   
   	</span>
   	
   	<form action="runGeneViewReport" name="paginate" method="post">
	<input type="hidden" id="paginate_queryName" name="reportGeneratorForm.queryName" value="{$qName}" />
	<input type="hidden" id="paginate_filter_value2" name="reportGeneratorForm.filter_value2" value="{$filter_value2}" />
	<input type="hidden" id="paginate_filter_value3" name="reportGeneratorForm.filter_value3" value="{$filter_value3}" />
	<input type="hidden" name="reportGeneratorForm.filter_value1" value="{$filter_value1}"/>
	<input type="hidden" name="reportGeneratorForm.showAllValues" value="{$showAllValues}"/>
	</form>
	
	<form action="runSwitchViews" method="post" target="_blank" name="switchViewsForm" id="switchViewsForm">
	     <input type="hidden" id="reportView" name= "reportGeneratorForm.reportView" value="" />
	     <input type="hidden" name="reportGeneratorForm.queryName" value="{$qName}" />
	     <input type="hidden" name="reportGeneratorForm.samples" value="" />			
	</form>
	 
	<div class="rptHeader">	
	<div class="rowCount">
	 <div style="background-color:#ffffff; margin-bottom:5px; padding-bottom:5px; border-left:1px solid black; border-right:1px solid black;">
		<span style="float:right; top:0px;">
			  <script language="javascript">
	  			checkForBack();
	 		  </script>
			<a style="margin-left:10px" href="#" onclick="javascript:toggleDiv('hideme');return false;">[Show/Hide Form Tools]</a>
			<xsl:text>&#160;</xsl:text><xsl:text>&#160;</xsl:text> 
 		</span>
		<b class="title" style="display:block; padding:3px;">
			<xsl:value-of select="@reportType" />		
			<span style="font-weight:normal; font-size:12px">
				(Query Name:<xsl:value-of select="@queryName" />)
			</span>
		
			<xsl:if test="($rType = 'Gene Expression Sample' or $rType = 'Copy Number') and contains($qName, 'previewResults') = false">
				<br/><a href="#" id="switchToClinical" onclick="prepQuickClinical();return false;" style="font-size:10px">[View Clinical Report for all samples]</a>
				<form id="quickClinicalWrapper"></form>
			</xsl:if>
			
			<xsl:if test="$rType = 'Clinical'">
			<span style="margin-left:15px; ">
				 <a style="text-decoration:none;margin-left:2px;font-weight:normal; font-size:11px;" href="#" onclick="javascript:spawnx('clinicalPlots.action?taskId={$qName}',900,600,'clinicalPlots');">Show Clinical Plots for these samples
				 <img alt="Clinical Plot" src="images/plotSmall.png" style="padding:1px;border:1px solid red; vertical-align:middle; margin-left:1px;margin-right:5px; height:13px; width: 28px;" />
				 </a>
				 <br/>
				 <a style="text-decoration:none;margin-left:2px;font-weight:normal; font-size:11px;" href="#" onclick="javascript:clinical2km();">[view KM plot: all samples in report vs.  rest of samples]</a>
			 </span>
			</xsl:if>
		</b>
 	</div>
 	
 	<div id="hideme">
	  <xsl:if test="@reportType != 'Gene Expression Disease' and @reportType != 'Clinical'" >
 
	  <div class="filterForm">
	  <form style="margin-bottom:0;" action="runGeneViewReport" method="post" name="filter_form">
		<b><span class="lb">Filter:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<span id="showOnlyLabel"><input type="radio" class="checkorradio" name="reportGeneratorForm.filter_type" id="showOnly_radio" value="show" /><label for="showOnly_radio">Show Only</label></span>
		<span id="hideLabel"><input type="radio" class="checkorradio" name="reportGeneratorForm.filter_type" id="hide_radio" checked="true" value="hide"/><label for="hide_radio">Hide</label></span>		
		<select id="filter_element" name="reportGeneratorForm.filter_element" onchange="javascript: showCNumberFilter(this.value, 'cNumberFilter')">
			<xsl:if test="$rType = 'Gene Expression Sample' or $rType = 'Gene Expression Disease'">
			<option value="gene">Gene(s)</option>
			</xsl:if>
			<xsl:if test="$rType = 'Copy Number'">
			<option value="cytoband">Cytoband(s)</option>
			<option value="copy number">Copy Number</option>
			</xsl:if>
			<option value="reporter">Reporters</option>
		</select><label for="filter_element">&#160;</label>
		<span id="fb">
		<input type="text" name="reportGeneratorForm.filter_string"/>
		<input type="hidden" name="reportGeneratorForm.queryName" value="{$qName}"/>
		<input type="submit" id="filter_submit1" name="filter_submit" value="Filter" onclick="javascript:return checkElement(filter_form.filter_string);"/><label for="filter_submit1">&#160;</label>
		
		<input type="button" name="filter_submit" onclick="javascript:doShowAllValues('{$qName}', false);" value="Reset (show all)" />
	<!--	<input type="button" name="filter_submit" onclick="javascript:clearFilterForm(document.forms['filter_form']);" value="Reset (show all)" /> -->
		
		<input type="hidden" name="showAllValues" value="{$showAllValues}"/>
		</span>
	  </form>
	  </div>
	  
	  <xsl:if test="$rType = 'Copy Number'">
	  <div class="filterForm" id="cNumberFilter" style="display:none">
	  <form style="margin-bottom:0;margin:0;" action="runFilterCopyNumber" method="post" name="cfilter_form">
		<b><span class="lb">Filter Options:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<label for="filter_value5">No. of Consecutive SNPs:</label>
		<select if="filter_value5" name="reportGeneratorForm.filter_value5">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
		</select>
		<xsl:text>&#160;</xsl:text>
		<input type="checkbox" class="checkorradio" name="reportGeneratorForm.filter_value4" value="and" />And (Or by default)
		<xsl:text>&#160;</xsl:text>
		<label for="filter_value6">% SNPs that match criteria</label>
		<select name="reportGeneratorForm.filter_value6">
			<option value="0"></option>
			<option value="10">10%</option>
			<option value="20">20%</option>
			<option value="30">30%</option>
			<option value="40">40%</option>
			<option value="50">50%</option>
			<option value="60">60%</option>
			<option value="70">70%</option>
			<option value="80">80%</option>
			<option value="90">90%</option>
			<option value="100">100%</option>
		</select>
		<xsl:text>&#160;</xsl:text>
		<input type="hidden" name="reportGeneratorForm.queryName" value="{$qName}"/>
		<input type="submit" name="filter_submit" value="Submit" />
		<input type="hidden" name="filter_element" value="copy_number"/>
		<xsl:text>&#160;</xsl:text>
	  </form>
	  </div>
	  <!-- Added for WebGenome Testing -->
	  <div class="filterForm" style="height:auto">
	  <form action="null" method="post" name="reportGeneratorForm" style="padding:0px; margin:0px;">
	    <b><span>Integrative Genomics Viewer (IGV):</span></b> <input type="button" name="webGenome" onclick="javascript:igvEscape('{$qName}'); return false;" value="Download Data to View in IGV" />
	  </form>
      </div>

      
      
      <script type="text/javascript">var totalSamples = '<xsl:value-of select="count(Row[@name='sampleRow']/Cell[@class != 'csv' and @class != 'header'])" />';</script>
	  <script type="text/javascript">checkWGThresh(totalSamples, wgThresh);</script>
	  </xsl:if>	  
	  
			
	  <div class="filterForm">
	  <form style="margin-bottom:0;" action="runGeneViewReport" method="post" name="highlight_form">
		<b><span class="lb"><label for="filter_value1">Highlight:</label></span></b> 
		<xsl:text>&#160;</xsl:text>
		<label for="filter_value4">highlight values</label> 
		<select id="filter_value4" name="reportGeneratorForm.filter_value4">
			<option value="gt">&gt;</option>
			<option value="lt">&lt;</option>
			<option value="eq">=</option>
			<option value="lte">&lt;=</option>
			<option value="gte">&gt;=</option>
		</select>
		<input type="text" id="filter_value1" name="reportGeneratorForm.filter_value1" size="4" value="{$filter_value1}" />
		<input type="hidden" name="reportGeneratorForm.queryName" value="{$qName}"/>
		<input type="hidden" name="reportGeneratorForm.filter_value2" value="{$filter_value2}"/>
		<input type="hidden" name="reportGeneratorForm.filter_value3" value="{$filter_value3}"/>
		<input type="submit" id="filter_submit" name="filter_submit" value="Highlight" /><label for="filter_submit">&#160;</label>
		<input type="hidden" name="reportGeneratorForm.showAllValues" value="{$showAllValues}"/>
		<input type="submit" name="filter_submit" value="Clear Highlighting" onclick="javascript:document.highlight_form.filter_value1.value='';" />
	  </form>
	  </div>
	  
	  <xsl:if test="$showSampleSelect != 'false' and contains($qName, 'previewResults') = false">
	  <div class="filterForm">
		<b><span class="lb"><label for="tmp_prb_queryName">Select Samples:</label></span></b> 
		<xsl:text>&#160;</xsl:text>
		<input type="text" size="30" id="tmp_prb_queryName" name="tmp_prb_queryName" value="{$qName}" />
		<input type="button" name="filter_submit" value="Save Samples" onclick="javascript:saveSamples();" />
		<xsl:text>&#160;</xsl:text>
		<a href="#" onclick="javascript:checkAllSamples(document.prbSamples.samples);return false;">[Check All]</a>
		<xsl:text>&#160;</xsl:text>
		<a href="#" onclick="javascript:uncheckAllSamples();return false;">[Uncheck All]</a>
	 	<xsl:text>&#160;</xsl:text>
	  </div>
	 </xsl:if>
	 
	 <xsl:if test="$allowShowAllValues != 'false'"> 
  	 <div class="filterForm">
		<b><span class="lb">Show all Values:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<input type="button" name="filter_submit" value="Show all values on this report" onclick="javascript:location.href='runShowAllValuesQuery.action?queryName={$qName}';" />
		<xsl:text>&#160;</xsl:text>
		<input type="button" name="filter_submit" value="View Previous Report" onclick="javascript:doShowAllValues('{$qName}', false);" />
		<xsl:text>&#160;</xsl:text>
		
	  </div>
	  </xsl:if>
	  
	 <div class="filterForm">
		<b><span class="lb">Hide Diseases:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<xsl:for-each select="Row[@name='headerRow']">
		  	<xsl:for-each select="Cell">
			  	<xsl:if test="@group!='header'">
						<xsl:variable name="currentGroup" select="@group" />
						<input class="checkorradio" id="{$currentGroup}" type="checkbox" onclick="javascript:goFilterColumnMg(this, '{$currentGroup}')"/>
						<xsl:value-of select="$currentGroup"/>
						<xsl:text>&#160;</xsl:text><label for="{$currentGroup}">&#160;</label>
				</xsl:if>
			</xsl:for-each>
		</xsl:for-each>
						
		<xsl:text>&#160;</xsl:text>
		<xsl:text>&#160;</xsl:text>
	  </div>
	  </xsl:if>
	  
	  <xsl:if test="$rType = 'Clinical' and $showSaveSamples != 'false'">
	  <div class="filterForm">
	  	<input type="text" name="sampleGroupName" id="sampleGroupName" value="{$qName}_samples"/><label for="sampleGroupName">&#160;</label>
	  	<xsl:text>&#160;</xsl:text>
	  	<input type="button" value="save selected samples" onclick="javascript:A_saveSamples();" />
	  	<xsl:text>&#160;</xsl:text>
	  	<span id="checkAllBlock"><input type="checkbox" name="checkAll" id="checkAll" class="checkorradio" onclick="javascript:manageCheckAll(this);"/> <label for="checkAll">All</label></span>
	  	<xsl:text>&#160;</xsl:text>
	  	<a href="#" onclick="javascript:return false;" onmouseover="javascript:return showHelp(savedHeader + currentTmpSamples);" onmouseout="return nd();" id="sampleCount"></a> 	
	  	<xsl:text>&#160;</xsl:text>
	  	<a href="#" onclick="javascript:A_clearTmpSamples(); return false;" onmouseover="javascript:return showHelp('Clear these samples');" onmouseout="return nd();">[clear samples]</a>
		<xsl:text>&#160;</xsl:text>	 	
		<script language="javascript">A_initSaveSample();</script>
	  </div>
	  </xsl:if>
	  
	  <div class="pageControl" style="padding-bottom:1px;margin-bottom:0px;">
	  <!-- <xsl:value-of select="$recordCount" /> records returned. -->
	  <b><span class="lb">Displaying:</span></b> 
	  <xsl:text>&#160;</xsl:text>
	  <xsl:value-of select="$filter_value3 * $filter_value2+1" /> - 
	  <xsl:choose>
	  	<xsl:when test="$recordCount > ($filter_value3 * ($filter_value2+1))">
		  <xsl:value-of select="$filter_value3 * ($filter_value2+1)" />
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$recordCount" />
		</xsl:otherwise>
	  </xsl:choose>
	   of <xsl:value-of select="$recordCount" /> records
	   <!-- if theres a next show it-->
	  <xsl:if test="$filter_value2>0">
	  	<xsl:variable name="ppage" select="$filter_value2 - 1"/> 
	   <a href="javascript:goPage('{$ppage}');">&lt;&lt;Prev</a>
	  </xsl:if>
	  <xsl:if test = "$recordCount > ($filter_value3 * ($filter_value2 +1))">
	   <xsl:variable name="npage" select="$filter_value2 + 1" />
	   <a href="javascript:goPage('{$npage}');">Next&gt;&gt;</a>
	  </xsl:if>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:if test="ceiling($recordCount div $filter_value3) > 1">
		  <b><xsl:value-of select="ceiling($recordCount div $filter_value3)" /> page(s):</b>
		  <script language="javascript">
		  	var records = <xsl:value-of select="ceiling($recordCount div $filter_value3)"/>;
		  	var cPage = <xsl:value-of select="$filter_value2"/>;
		  	<![CDATA[	
		  		for(i=0;records!=i; i++)	{
		  			stupidXSL(i, cPage, records);
		  		}
		  	]]>
		  </script>
	  </xsl:if>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  <select id="changeStep" name="reportGeneratorForm.filter_value3" onchange="javascript:goPageChangeStep('{$filter_value2}', this.value);">
	  	<option value=""><xsl:value-of select="$filter_value3"/> per page</option>
	  	<option value="1">1</option>
	  	<option value="5">5</option>
	  	<option value="10">10</option>
	  	<option value="25">25</option>
	  	<option value="50">50</option>
	  	<option value="100">100</option>
	  </select>
	  <label for="changeStep">&#160;</label>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:if test="/Report[@reportType != 'Gene Expression Disease'] and /Report[@reportType != 'Clinical']" >
	  	<xsl:value-of select="count(Row[@name='sampleRow']/Cell[@class != 'csv' and @class != 'header'])" /> samples returned.
	  </xsl:if>
	  </div>
	  
	 </div>
	</div>
</div>

<!--  process samples -->
<script language="javascript">
	<xsl:for-each select="Row/Cell[@group='sample']">
		<xsl:if test="Data != ''">
			allSamplesFromClinical.push('<xsl:value-of select="Data"/>');
		</xsl:if>
	</xsl:for-each>
	var anyHighlighted = false;
	var hcells = Array();
	<xsl:for-each select="Row[position()>2]/Cell[position()>3]">
		<xsl:if test="($filter_value4 = 'gt' and Data > $filter_value1) or ($filter_value4 = 'lt' and $filter_value1 > Data) or ($filter_value4 = 'eq' and $filter_value1 >= Data and Data >= $filter_value1) or ($filter_value4 = 'lte' and $filter_value1 >= Data) or ($filter_value4 = 'gte' and Data >= $filter_value1)">
			hcells.push('<xsl:value-of select="Data"/>');
			anyHighlighted = true;
		</xsl:if>
	</xsl:for-each>
</script>

    <table cellpadding="0" cellspacing="0">
		<xsl:for-each select="Row[@name='headerRow']">
			<tr class="headerRow">
		  	<xsl:for-each select="Cell[@class != 'csv']">
			<xsl:choose>
					<xsl:when test="@group='header'">
						<td style="color:red"><xsl:value-of select="Data" /></td>
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="currentGroup" select="@group" />
						<xsl:variable name="colspan" select="count(/Report/Row[@name='sampleRow']/Cell[@group=$currentGroup])"/>
						<td colspan="{$colspan}" class="{$currentGroup}">
							<xsl:value-of select="Data" />
							<xsl:if test="/Report[@reportType != 'Gene Expression Disease'] and /Report[@reportType != 'Clinical'] and $showSampleSelect != 'false' and contains($qName, 'previewResults')=false" >
								<input id="grpcheck" class="checkorradio" type="checkbox" onclick="javascript:groupCheckSamples('{$currentGroup}', this.checked)" />
							</xsl:if>
						</td>
					</xsl:otherwise>
			</xsl:choose>
		    </xsl:for-each>
		    <label for="grpcheck">&#160;</label>
		    </tr>
		</xsl:for-each>
		
		<form action="runSubmitSpecimens" method="post" name="prbSamples" id="prbSamples">
		<input type="hidden" name="queryName" value="{$qName}"/>
		<input type="hidden" name="reportType" value="{$rType}" />
		<xsl:for-each select="Row[@name='sampleRow']">
			<tr class="sampleRow">
		  	<xsl:for-each select="Cell[@class != 'csv']">
			  <xsl:variable name="currentGroup" select="@group" />
			  <xsl:variable name="specimen" select="Data" />			  
			  <xsl:variable name="sample" select="@sampleId" />
			  <xsl:choose>
			  <xsl:when test="Data = ' '">
			  	
			  	<xsl:if test="preceding::Cell[1]/Data[1]/text() != ' ' and $currentGroup = 'header'">
			  	<td colspan="2" align="center">
				  <input type="hidden" name="reportGeneratorForm.prbQueryName" value="{$qName}" size="10" />
				  <!-- <input type="submit" name="prb_submitSamples" value="Save" style="width:40px" /> -->
				 </td>
			  	</xsl:if>
			  	<xsl:if test="preceding::Cell[1]/Data[1]/text() = ' ' and following::Cell[1]/Data[1]/text() != ' ' and $currentGroup = 'header'">
				  
			  	</xsl:if>
			  </xsl:when>
			  <xsl:otherwise>
		      	<td class="{$currentGroup}">
		      	<xsl:if test="$specimen != '' and $specimen != ' ' and $showSampleSelect != 'false' and contains($qName,'previewResults') = false">
		      		<input id ="{$currentGroup}" class="checkorradio" type="checkbox" name="reportGeneratorForm.samples" value="{$specimen}"/>
		      	</xsl:if>
		      	<!--
		      		<a href="runReport.do?method=switchViews&amp;queryName={$qName}"><xsl:value-of select="Data" /></a>
		      	-->
		      		<a href="javascript:switchViews('Cl', '{$specimen}')"><xsl:value-of select="$sample" /></a>
		      		<xsl:if test="$specimen != ''and $specimen != $sample"> 
		      		(<xsl:value-of select="$specimen" />)
		      		</xsl:if>
		      	</td>
		      </xsl:otherwise>
		      </xsl:choose>
		    </xsl:for-each>
		    </tr>
		</xsl:for-each>
		
		<!-- get each data row only -->
		<xsl:for-each select="Row[(@name='dataRow')] ">
			<xsl:if test="$filter_value3 + ($filter_value3 * $filter_value2)>=position() and position() > ($filter_value2 * $filter_value3)">	
					<tr>
		  				<xsl:for-each select="Cell[@class != 'csv']">
		  	  			<xsl:variable name="class" select="@group" />
		  	  			<xsl:variable name="styleclass" select="@class" />
		  	  			<xsl:variable name="theData" select="Data"/>
		  	  			<xsl:variable name="tooltip" select="Data/@datainfo"/>
		  	  			
		  	  			<xsl:variable name="highlightThisCell">
							<xsl:choose>
								<xsl:when test="($filter_value4 = 'gt' and Data > $filter_value1) or ($filter_value4 = 'lt' and $filter_value1 > Data) or ($filter_value4 = 'eq' and $filter_value1 >= Data and Data >= $filter_value1) or ($filter_value4 = 'lte' and $filter_value1 >= Data) or ($filter_value4 = 'gte' and Data >= $filter_value1)">yes</xsl:when>
								<xsl:otherwise>no</xsl:otherwise>		
							</xsl:choose>
						</xsl:variable>
						
		      			<td class="{$class}">

		      			<xsl:choose>
		      				<xsl:when test="$styleclass = 'gene' and $theData != '-'">
		      					<a href="#" onclick="javascript:spawnAnnot('gene', this); return false;"><xsl:value-of select="Data"/></a>
		      				</xsl:when>
		      				<xsl:when test="$styleclass = 'reporter' and $theData != '-' and $rType != 'Copy Number'">
		      						<a href="#" onclick="javascript:spawnAnnot('reporterFromGene',this); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
		      				<xsl:when test="$styleclass = 'reporter' and $theData != '-' and $rType = 'Copy Number'">
		      						<a href="#" onclick="javascript:spawnAnnot('reporterFromCopy',this); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
		      				<xsl:when test="$styleclass = 'cytoband' and $theData != '-'">
		      						<a href="#" onclick="javascript:spawnAnnot('cytoband',this); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
			      			<xsl:when test="$class = 'sample'">
			      				<xsl:variable name="sample" select="Data"  />
			      				<xsl:if test="$showSaveSamples != 'false'">
				      				<input type="checkbox" class="checkorradio" id="{$sample}" name="reportGeneratorForm.samples" value="{$sample}" onclick="javascript:A_saveTmpSample(this);" /><label for="{$sample}">&#160;</label>
				      			</xsl:if>
			      				<xsl:value-of select="Data"/>
			   
							</xsl:when>
			      			<xsl:when test="$filter_value1 != 000 and $filter_value4 != ''">
			      				<xsl:choose>
			      					<xsl:when test="$highlightThisCell = 'yes'">
						      			<span class="highlighted" style="background-color:yellow" id="{$theData}"><xsl:value-of select="Data"/></span>		      				
			      					</xsl:when>
			      					<xsl:when test="$theData = 'G' or $theData = 'C'">
			      						<xsl:variable name="currentSample" select="@sampleId"/>
			      						<a href="javascript:switchViews('{$theData}', '{$currentSample}')"><xsl:value-of select="$currentSample"/></a>
			
			<!--      						<a href="runReport.do?method=switchViews&amp;queryName={$qName}&amp;reportView={$theData}"><xsl:value-of select="$theData"/></a> -->
			      					</xsl:when>
									<xsl:when test="$theData = '-' and $showAllValues = 'true'">
										<span class="missing" style="color:gray;">null</span>
									</xsl:when>
			      					<xsl:otherwise>
			      						<xsl:if test="$styleclass = 'highlighted'">
			      						    <!-- JB Begin: Add for GForge # 17783 - Advance Query Reports - Make the report more user friendly (Provide tool tip) -->
			      						    <!-- <span class="missing" style="color:gray;"><xsl:value-of select="Data" disable-output-escaping="yes" /></span> -->
			      				            <xsl:if test="$rType = 'Clinical'">
			      								<span class="missing" style="color:gray;"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      							</xsl:if>
			      				            <xsl:if test="$rType != 'Clinical'">
			      								<a href="javascript:void(0);" onmouseover="return overlib('{$tooltip}', WIDTH, 500, HEIGHT, 300, CAPTION, 'Data Details', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);');" onmouseout="return nd();"><span class="missing" style="color:gray;"><xsl:value-of select="Data" disable-output-escaping="yes" /></span></a>
			      							</xsl:if>
											<!-- JB End: Add for GForge # 17783 - Advance Query Reports - Make the report more user friendly (Provide tool tip) -->
			      						</xsl:if>
				      					<xsl:if test="$styleclass != 'highlighted'">
			      						    <!-- JB Begin: Add for GForge # 17783 - Advance Query Reports - Make the report more user friendly (Provide tool tip) -->
			      						    <!-- <xsl:value-of select="Data" disable-output-escaping="yes" /> -->
			      				            <xsl:if test="$rType = 'Clinical'">
				      							<xsl:value-of select="Data" disable-output-escaping="yes" />
				      						</xsl:if>
			      				            <xsl:if test="$rType != 'Clinical'">
				      							<a href="javascript:void(0);" onmouseover="return overlib('{$tooltip}',  WIDTH, 500, HEIGHT, 300, CAPTION, 'Data Details', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"><xsl:value-of select="Data" disable-output-escaping="yes" /></a>
				      						</xsl:if>
											<!-- JB End: Add for GForge # 17783 - Advance Query Reports - Make the report more user friendly (Provide tool tip) -->
				      					</xsl:if>
			      					</xsl:otherwise>
			      				</xsl:choose>
			      			</xsl:when>
			      			<xsl:otherwise>
			      				
			      			</xsl:otherwise>
		      			</xsl:choose>
		      			</td>
		    			</xsl:for-each>
		    		</tr>

				<xsl:if test="/Report[@reportType != 'Clinical'] and ./Cell[1]/Data[1]/text() != following::Cell[1]/Data[1]/text() and following::Cell[1]/Data[1]/text() != ''">
					<tr>
		      			<td colspan="{$colCount}" class="geneSpacerStyle">--</td>
		    		</tr>
				</xsl:if>

			</xsl:if>

		</xsl:for-each>
	</form>
  	</table>
  	<div>
  	<script language="javascript">
  		goQueryDetails("<xsl:copy-of select="$queryDetails" />");
  	</script>
  	</div>
  	
 	<script language="javascript">
 	if(document.highlight_form){
 		selectHOperand(document.highlight_form.filter_value4, '<xsl:value-of select="$filter_value4"/>');
 	}
  	</script>
 	<script language="javascript">A_initSaveSample();</script>
 	<script language="javascript">if(anyHighlighted)	{autoCheckHighlightedSamples(hcells);}</script>
  	
  	</xsl:if><!-- no records -->
  	<xsl:if test="$recordCount = 0">
  		<h3 style="text-align:center; margin-top:200px;">
  		<xsl:choose>
		  <xsl:when test="$reportMsg = 'over limit'">
		   	  	Your query contains too many results for display.  Please add additional disease or sample filters to your query and re-submit it.
 		  </xsl:when>
		  <xsl:otherwise>
 				No Records Returned.
		  </xsl:otherwise>
		</xsl:choose>
		 <!--  		 
		 <br/><a style="margin-right:10px" href="javascript:history.back()">Back </a><a href="javascript:window.close()">Close</a></h3>
		 -->
		 <br/><a href="javascript:window.close()">Close</a></h3>
   	
   	
   	</xsl:if>
  </xsl:for-each>
	
		 
  </body>

</xsl:template>
</xsl:stylesheet>