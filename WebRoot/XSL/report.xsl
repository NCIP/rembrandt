<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" omit-xml-declaration="yes" /> 

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

<html xml:lang="en" lang="en">
  	<head>
		<title>My Report</title>
		<script language="JavaScript" type="text/javascript" src="js/overlib.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/caIntScript.js"></script> 
		<script language="JavaScript" type="text/javascript" src="XSL/js.js"></script> 
		<script language="JavaScript" type="text/javascript" src="XSL/a_saveSamples.js"></script>
		
		<script type='text/javascript' src='/rembrandt/dwr/interface/DynamicReport.js'></script>
		<script type='text/javascript' src='/rembrandt/dwr/engine.js'></script>
		<script type='text/javascript' src='/rembrandt/dwr/util.js'></script>
		
		<LINK href="XSL/css.css" rel="stylesheet" type="text/css" />
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
		<META HTTP-EQUIV="Expires" CONTENT="-1" />
	</head>
  <body>
  
  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;">Help</div>

  <div style="background-color: #ffffff"><img src="images/smallHead.jpg" /></div>
  <p align="center" style="background:red; color:#ffffff; font-size:12px; font-weight:bold;"><xsl:value-of select="$statusMsg" /></p>
 
   <xsl:for-each select="Report">

    <!--
    <fieldset>
    <legend>DEBUG USE ONLY, PLEASE IGNORE THIS</legend>
    <h3>Test: <xsl:value-of select="$showSaveSamples"/>,
    <xsl:value-of select="$filter_value2"/>,
    <xsl:value-of select="$filter_value3"/>
    </h3>
	</fieldset><br/>
	-->
	<xsl:variable name="helpLink" select="@reportType" />
	<xsl:variable name="colCount" select="count(Row[2]/Cell)" />
	<xsl:variable name="recordCount" select="count(Row[@name='dataRow'])" />
	
	<xsl:if test="$recordCount > 0">
	
	<xsl:variable name="qName" select="@queryName" />
	<xsl:variable name="rType" select="@reportType" />
	
    <span style="z-index:900; float:right;position:absolute;top:10px;right:10px;">
	  <!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	  <a href="#" onclick="javascript:window.close();"><img align="right" src="images/close.png" border="0" onmouseover="return overlib('Close this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/> </a> 
	  <a href="javascript: spawn('help.jsp?sect={$helpLink}', 350, 500);"><img align="right" src="images/help.png" border="0" onmouseover="return overlib('Click here for additional information about this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();" /></a>
	  <a href="#" onclick="javascript:stupidXSLEscape('{$qName}')"><img align="right" src="images/excel.png" border="0" alt="download for excel" onmouseover="return overlib('Download for Excel.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/></a>
	  <a href="#" onclick="javascript:window.print();"><img align="right" src="images/print.png" border="0" onmouseover="return overlib('Print this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/> </a> 
	  <a href="#queryInfo"><img align="right" src="images/text.png" border="0" onmouseover="return overlib('View Query Information.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/></a>
	  <a href="#" onclick="javascript:toggleDiv('hideme');return false;"><img align="right" src="images/tools.png" border="0" onmouseover="return overlib('Show or Hide Report Tools.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/></a>
   	</span>

	<form action="runReport.do?method=runGeneViewReport" name="paginate" method="post">
	<input type="hidden" name="queryName" value="{$qName}" />
	<input type="hidden" name="filter_value2" value="{$filter_value2}" />
	<input type="hidden" name="filter_value3" value="{$filter_value3}" />
	<input type="hidden" name="filter_value1" value="{$filter_value1}"/>
	<input type="hidden" name="showAllValues" value="{$showAllValues}"/>
	</form>
	
	<form action="runReport.do?method=switchViews" method="post" target="_blank" name="switchViewsForm">
	     <input type="hidden" name= "reportView" value="" />
	     <input type="hidden" name="queryName" value="{$qName}" />
	     <input type="hidden" name="samples" value="" />			
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
		
			<xsl:if test="$rType = 'Clinical'">
			<span style="margin-left:15px; ">
				 <a style="text-decoration:none;margin-left:2px;font-weight:normal; font-size:11px;" href="#" onclick="javascript:spawnx('clinicalPlots.do?taskId={$qName}',900,600,'clinicalPlots');">Show Clinical Plots for these samples
				 <img src="images/plotSmall.png" style="padding:1px;border:1px solid red; vertical-align:middle; margin-left:1px;margin-right:5px; height:13px; width: 28px;" />
				 </a>
			 </span>
			</xsl:if>
		</b>
 	</div>
 	
 	<div id="hideme">
	  <xsl:if test="@reportType != 'Gene Expression Disease' and @reportType != 'Clinical'" >
 
	  <div class="filterForm">
	  <form style="margin-bottom:0;" action="runReport.do?method=runGeneViewReport" method="post" name="filter_form">
		<b><span class="lb">Filter:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<span id="showOnlyLabel"><input type="radio" class="checkorradio" name="filter_type" id="showOnly_radio" value="show" />Show Only</span>
		<span id="hideLabel"><input type="radio" class="checkorradio" name="filter_type" id="hide_radio" checked="true" value="hide"/>Hide</span>		
		<select name="filter_element" onchange="javascript: showCNumberFilter(this.value, 'cNumberFilter')">
			<xsl:if test="$rType = 'Gene Expression Sample' or $rType = 'Gene Expression Disease'">
			<option value="gene">Gene(s)</option>
			</xsl:if>
			<xsl:if test="$rType = 'Copy Number'">
			<option value="cytoband">Cytoband(s)</option>
			<option value="copy number">Copy Number</option>
			</xsl:if>
			<option value="reporter">Reporters</option>
		</select>
		<span id="fb">
		<input type="text" name="filter_string"/>
		<input type="hidden" name="queryName" value="{$qName}"/>
		<input type="submit" name="filter_submit" value="Filter" onclick="javascript:return checkElement(filter_form.filter_string);"/>
		
		<input type="button" name="filter_submit" onclick="javascript:doShowAllValues('{$qName}', false);" value="Reset (show all)" />
	<!--	<input type="button" name="filter_submit" onclick="javascript:clearFilterForm(document.forms['filter_form']);" value="Reset (show all)" /> -->
		
		<input type="hidden" name="showAllValues" value="{$showAllValues}"/>
		</span>
	  </form>
	  </div>
	  
	  <xsl:if test="$rType = 'Copy Number'">
	  <div class="filterForm" id="cNumberFilter" style="display:none">
	  <form style="margin-bottom:0;margin:0;" action="runReport.do?method=runFilterCopyNumber" method="post" name="cfilter_form">
		<b><span class="lb">Filter Options:</span></b> 
		<xsl:text>&#160;</xsl:text>
		No. of Consecutive SNPs:
		<select name="filter_value5">
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
		<input type="checkbox" class="checkorradio" name="filter_value4" value="and" />And (Or by default)
		<xsl:text>&#160;</xsl:text>
		% SNPs that match criteria
		<select name="filter_value6">
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
		<input type="hidden" name="queryName" value="{$qName}"/>
		<input type="submit" name="filter_submit" value="Submit" />
		<input type="hidden" name="filter_element" value="copy_number"/>
		<xsl:text>&#160;</xsl:text>
		<b><a href="#" onclick="javascript:return false;" onmouseover="javascript:return showHelp('The Copy Number Filter allows you to filter the copy number report based on additional criteria such as No. of Consecutive SNPs and Percent of SNPs that match criteria.  These criteria will determine which SNPs are displayed on the report. The filtered Version of our report will now reflect the filter parameters we have filled out. For example, selecting “3” for the first filter and “50%” in the second filter will displays only those samples that had atleast 3 consecutive SNPs and 50% of the SNPs that met the criteria specified.');" onmouseout="return nd();">[?]</a></b>
	  </form>
	  </div>
	  <!-- Added for WebGenome Testing -->
	  <div class="filterForm">
	  <form action="null" method="post" name="reportGeneratorForm">
	     <button type="button" name="webGenome" onclick="javascript:return window.open('runReport.do?method=webGenomeRequest&amp;queryName={$qName}')">View Plots (in WebGenome)</button>
      </form>
	   </div>
	  </xsl:if>	  
	  
			
	  <div class="filterForm">
	  <form style="margin-bottom:0;" action="runReport.do?method=runGeneViewReport" method="post" name="highlight_form">
		<b><span class="lb">Highlight:</span></b> 
		<xsl:text>&#160;</xsl:text>
		highlight values 
		<select name="filter_value4">
			<option value="gt">&gt;</option>
			<option value="lt">&lt;</option>
			<option value="eq">=</option>
			<option value="lte">&lt;=</option>
			<option value="gte">&gt;=</option>
		</select>
		<input type="text" name="filter_value1" size="4" value="{$filter_value1}" />
		<input type="hidden" name="queryName" value="{$qName}"/>
		<input type="hidden" name="filter_value2" value="{$filter_value2}"/>
		<input type="hidden" name="filter_value3" value="{$filter_value3}"/>
		<input type="submit" name="filter_submit" value="Highlight" />
		<input type="hidden" name="showAllValues" value="{$showAllValues}"/>
		<input type="submit" name="filter_submit" value="Clear Highlighting" onclick="javascript:document.highlight_form.filter_value1.value='';" />
	  </form>
	  </div>
	  
	  <xsl:if test="$showSampleSelect != 'false' and contains($qName, 'previewResults') = false">
	  <div class="filterForm">
		<b><span class="lb">Select Samples:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<input type="text" size="30" id="tmp_prb_queryName" name="tmp_prb_queryName" value="{$qName}" />
		<input type="button" name="filter_submit" value="Save Samples" onclick="javascript:saveSamples();" />
		<xsl:text>&#160;</xsl:text>
		<a href="#" onclick="javascript:checkAll(document.prbSamples.samples);return false;">[Check All]</a>
		<xsl:text>&#160;</xsl:text>
		<a href="#" onclick="javascript:uncheckAll(document.prbSamples.samples);return false;">[Uncheck All]</a>
	 	<xsl:text>&#160;</xsl:text>
	 	<b><a href="#" onclick="javascript:return false;" onmouseover="javascript:return showHelp('You can select the samples of interest by clicking on each individual sample or a group and saving them with a unique name. This allows you to select this sample set to apply your future queries to.');" onmouseout="return nd();">[?]</a></b>
	  </div>
	 </xsl:if>
	 
	 <xsl:if test="$allowShowAllValues != 'false'"> 
  	 <div class="filterForm">
		<b><span class="lb">Show all Values:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<input type="button" name="filter_submit" value="Show all values on this report" onclick="javascript:location.href='runReport.do?method=runShowAllValuesQuery&amp;queryName={$qName}';" />
		<xsl:text>&#160;</xsl:text>
		<input type="button" name="filter_submit" value="View Previous Report" onclick="javascript:doShowAllValues('{$qName}', false);" />
		<xsl:text>&#160;</xsl:text>
		
		<b><a href="#" onclick="javascript:return false;" onmouseover="javascript:return showHelp('Clicking on this button lets you view the gene expression fold changes or copy number values (depending on the type of report) for all the reporters in the report. This allows you to see those values that did not match your query criteria.');" onmouseout="return nd();">[?]</a></b>
	  </div>
	  </xsl:if>
	  
	 <div class="filterForm">
		<b><span class="lb">Hide Diseases:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<xsl:for-each select="Row[@name='headerRow']">
		  	<xsl:for-each select="Cell">
			  	<xsl:if test="@group!='header'">
						<xsl:variable name="currentGroup" select="@group" />
						<input class="checkorradio" type="checkbox" onclick="javascript:goFilterColumnMg(this, '{$currentGroup}')"/>
						<xsl:value-of select="$currentGroup"/>
						<xsl:text>&#160;</xsl:text>
				</xsl:if>
			</xsl:for-each>
		</xsl:for-each>
						
		<xsl:text>&#160;</xsl:text>
		<xsl:text>&#160;</xsl:text>
	  </div>
	  </xsl:if>
	  
	  <xsl:if test="$rType = 'Clinical' and $showSaveSamples != 'false'">
	  <div class="filterForm">
	  	<input type="text" name="sampleGroupName" id="sampleGroupName" value="{$qName}_samples"/>
	  	<xsl:text>&#160;</xsl:text>
	  	<input type="button" value="save selected samples" onclick="javascript:A_saveSamples();" />
	  	<xsl:text>&#160;</xsl:text>
	  	<span id="checkAllBlock"><input type="checkbox" name="checkAll" id="checkAll" class="checkorradio" onclick="javascript:manageCheckAll(this);"/> All on page</span>
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
	  <select name="changeStep" onchange="javascript: goPageChangeStep('{$filter_value2}', this.value);">
	  	<option value=""><xsl:value-of select="$filter_value3"/> per page</option>
	  	<option value="1">1</option>
	  	<option value="5">5</option>
	  	<option value="10">10</option>
	  	<option value="25">25</option>
	  	<option value="50">50</option>
	  	<option value="100">100</option>
	  </select>
	 
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
								<input id="grpcheck" class="checkorradio" type="checkbox" onclick="javascript:groupCheck(document.prbSamples.samples, '{$currentGroup}', this.checked)" />
							</xsl:if>
						</td>
					</xsl:otherwise>
			</xsl:choose>
		    </xsl:for-each>
		    </tr>
		</xsl:for-each>
		
		<form action="runReport.do?method=submitSamples" method="post" name="prbSamples">
		<input type="hidden" name="queryName" value="{$qName}"/>
		<xsl:for-each select="Row[@name='sampleRow']">
			<tr class="sampleRow">
		  	<xsl:for-each select="Cell[@class != 'csv']">
			  <xsl:variable name="currentGroup" select="@group" />
			  <xsl:variable name="sample" select="Data" />
			  <xsl:choose>
			  <xsl:when test="Data = ' '">
			  	
			  	<xsl:if test="preceding::Cell[1]/Data[1]/text() != ' ' and $currentGroup = 'header'">
			  	<td colspan="2" align="center">
				  <input type="hidden" name="prbQueryName" value="{$qName}" size="10" />
				  <!-- <input type="submit" name="prb_submitSamples" value="Save" style="width:40px" /> -->
				 </td>
			  	</xsl:if>
			  	<xsl:if test="preceding::Cell[1]/Data[1]/text() = ' ' and following::Cell[1]/Data[1]/text() != ' ' and $currentGroup = 'header'">
				  
			  	</xsl:if>
			  </xsl:when>
			  <xsl:otherwise>
		      	<td class="{$currentGroup}">
		      	<xsl:if test="$sample != '' and $sample != ' ' and $showSampleSelect != 'false' and contains($qName,'previewResults') = false">
		      		<input id ="{$currentGroup}" class="checkorradio" type="checkbox" name="samples" value="{$sample}"/>
		      	</xsl:if>
		      	<!--
		      		<a href="runReport.do?method=switchViews&amp;queryName={$qName}"><xsl:value-of select="Data" /></a>
		      	-->
		      		<a href="javascript:switchViews('Cl', '{$sample}')"><xsl:value-of select="Data" /></a>
		      	
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
		      			<td class="{$class}">

		      			<xsl:choose>
		      				<xsl:when test="$styleclass = 'gene' and $theData != '-'">
		      					<a href="#" onclick="javascript:spawnAnnot('gene', this); return false;"><xsl:value-of select="Data"/></a>
		      				</xsl:when>
		      				<xsl:when test="$styleclass = 'reporter' and $theData != '-' and $rType != 'Copy Number'">
		      						<a href="#" onclick="javascript:spawnAnnot('reporterFromGene',this); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
		      				<xsl:when test="$styleclass = 'reporter' and $theData != '-' and $rType = 'Copy Number'">
		      						<a href="#" onclick="javascript:spawnAnnot('reporter',this); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
		      				<xsl:when test="$styleclass = 'cytoband' and $theData != '-'">
		      						<a href="#" onclick="javascript:spawnAnnot('cytoband',this); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
			      			<xsl:when test="$class = 'sample'">
			      				<xsl:variable name="sample" select="Data"  />
			      				<xsl:if test="$showSaveSamples != 'false'">
				      				<input type="checkbox" class="checkorradio" id="samples" name="samples" value="{$sample}" onclick="javascript:A_saveTmpSample(this);" />
				      			</xsl:if>
			      				<xsl:value-of select="Data"/>
			   
							</xsl:when>
			      			<xsl:when test="$filter_value1 != 000 and $filter_value4 != ''">
			      				<xsl:choose>
			      					<xsl:when test="$filter_value4 = 'gt' and Data > $filter_value1">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$filter_value4 = 'lt' and $filter_value1 > Data">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$filter_value4 = 'eq' and $filter_value1 = Data">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$filter_value4 = 'lte' and $filter_value1 >= Data">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$filter_value4 = 'gte' and Data >= $filter_value1">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$theData = 'G' or $theData = 'C'">
			      					<xsl:variable name="currentSample" select="..//Cell[1]/Data"/>
			      						<a href="javascript:switchViews('{$theData}', '{$currentSample}')"><xsl:value-of select="$theData"/></a>
			
			<!--      						<a href="runReport.do?method=switchViews&amp;queryName={$qName}&amp;reportView={$theData}"><xsl:value-of select="$theData"/></a> -->
			      					</xsl:when>
									<xsl:when test="$theData = '-' and $showAllValues = 'true'">
										<span class="missing" style="color:gray;">null</span>
									</xsl:when>
			      					<xsl:otherwise>
			      						<xsl:if test="$styleclass = 'highlighted'">
			      							<span class="missing" style="color:gray;"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      						</xsl:if>
				      					<xsl:if test="$styleclass != 'highlighted'">
				      						<xsl:value-of select="Data" disable-output-escaping="yes" />
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
  	</xsl:if><!-- no records -->
  	<xsl:if test="$recordCount = 0">
  		<h3 style="text-align:center; margin-top:200px;">No Records Returned. <br/><a style="margin-right:10px" href="javascript:history.back()">Back </a><a href="javascript:window.close()">Close</a></h3>
  	</xsl:if>
  </xsl:for-each>

  <script language="javascript">
  if(document.highlight_form){
 	selectHOperand(document.highlight_form.filter_value4, '<xsl:value-of select="$filter_value4"/>');
 	}
  </script>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>