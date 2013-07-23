<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" omit-xml-declaration="yes" /> 

<xsl:param name="p_highlight">0</xsl:param>
<xsl:param name="p_highlight_op">gt</xsl:param>

<xsl:param name="p_page">0</xsl:param>
<xsl:param name="p_step">25</xsl:param>

<xsl:param name="key"></xsl:param>

<xsl:param name="p_pval_filter_mode">lt</xsl:param>
<xsl:param name="p_pval_filter_value"></xsl:param>
<xsl:param name="p_pval_filter_op"></xsl:param>

<xsl:param name="p_sort_element">1</xsl:param>
<xsl:param name="p_sort_method"></xsl:param>
<xsl:param name="p_sort_dtype">number</xsl:param>

<xsl:param name="filter_value5"></xsl:param>
<xsl:param name="filter_value6"></xsl:param>

<xsl:param name="showSampleSelect"></xsl:param>

<xsl:param name="allowShowAllValues">true</xsl:param>
<xsl:param name="queryDetails">N/A</xsl:param>
<xsl:param name="statusMsg"></xsl:param>
<xsl:param name="showAllValues">false</xsl:param>


<xsl:template match="/">

<span>
<xsl:comment>
<xsl:variable name="dtype">
	<xsl:choose>
		<xsl:when test="$p_sort_element != '1' and $p_sort_element != '5'">number</xsl:when>
		<xsl:otherwise>text</xsl:otherwise>		
	</xsl:choose>
</xsl:variable>
</xsl:comment>

<xsl:variable name="dtype">
		<xsl:choose>
			<xsl:when test="boolean(number(Report[1]/Row[2]/Cell[number($p_sort_element)]/Data)) = true()">number</xsl:when>
			<xsl:otherwise>text</xsl:otherwise>
		</xsl:choose>
</xsl:variable>
<!-- 
	Sorting by: <xsl:value-of select="$dtype"/>
-->

  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;">Help</div>

  <div style="background-color: #ffffff"><img id="Rembrandt" src="images/smallHead.jpg" /></div>
  <p align="center" style="background:red; color:#ffffff; font-size:12px; font-weight:bold;"><xsl:value-of select="$statusMsg" /></p>
 
   <xsl:for-each select="Report">

	<xsl:variable name="helpLink" select="@reportType" />
	<xsl:variable name="colCount" select="count(Row[2]/Cell)" />
	

	<xsl:variable name="filterNone" select = "Row[@name='dataRow']" />
	<xsl:variable name="filterRecordLT" select="Row[@name='dataRow']/Cell[position() = 2 and $p_pval_filter_value > number(Data)]" />
	<xsl:variable name="filterRecordLTE" select="Row[@name='dataRow']/Cell[position() = 2 and $p_pval_filter_value >= number(Data)]" />
	<xsl:variable name="filterRecordGT" select="Row[@name='dataRow']/Cell[position() = 2 and number(Data) > $p_pval_filter_value]" />
	<xsl:variable name="filterRecordGTE" select="Row[@name='dataRow']/Cell[position() = 2 and number(Data) >= $p_pval_filter_value]" />
	<xsl:variable name="filterRecordEQ" select="Row[@name='dataRow']/Cell[position() = 2 and number(Data) = $p_pval_filter_value]" />
	
	
	<xsl:variable name="recordCount">
		<xsl:choose>
			<xsl:when test="$p_pval_filter_value != '' and $p_pval_filter_mode = 'lt'"><xsl:value-of select="count($filterRecordLT)"/></xsl:when>
			<xsl:when test="$p_pval_filter_value != '' and $p_pval_filter_mode = 'lte'"><xsl:value-of select="count($filterRecordLTE)"/></xsl:when>
			<xsl:when test="$p_pval_filter_value != '' and $p_pval_filter_mode = 'gt'"><xsl:value-of select="count($filterRecordGT)"/></xsl:when>
			<xsl:when test="$p_pval_filter_value != '' and $p_pval_filter_mode = 'gte'"><xsl:value-of select="count($filterRecordGTE)"/></xsl:when>
			<xsl:when test="$p_pval_filter_value != '' and $p_pval_filter_mode = 'eq'"><xsl:value-of select="count($filterRecordEQ)"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="count($filterNone)"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:if test="$recordCount > 0">
	
		<xsl:variable name="qName" select="@queryName" />
		<xsl:variable name="rType" select="@reportType" />
		
	    <span style="z-index:900; float:right;position:absolute;top:10px;right:10px;">
		  <!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
		  <a href="#" onclick="javascript:window.close();" title="Close this report."><img alt="Close" align="right" src="images/close.png" border="0" /> </a> 
		  <a href="https://wiki.nci.nih.gov/display/icrportals/5+Viewing+REMBRANDT+Results+v1.5.8#id-5ViewingREMBRANDTResultsv158-ClassComparisonReport" title="Click here for additional information about this report."><img alt="Help" align="right" src="images/help.png" border="0" /></a>
		  <a href="#" onclick="javascript:runFindingCSV('{$key}')" title="Download for Excel."><img align="right" src="images/excel.png" border="0" alt="download for excel" /></a>
		  <a href="#" onclick="javascript:window.print();" title="Print this report."><img align="right" alt="Print" src="images/print.png" border="0" /> </a> 
		  <a href="#queryInfo" title="View Query Information."><img align="right" alt="View Query Information" src="images/text.png" border="0" /></a>
		  <a href="#" onclick="javascript:toggleDiv('hideme');return false;" title="Show or Hide Report Tools"><img align="right" alt="Show or Hide Report Tools" src="images/tools.png" border="0" /></a>
	  	</span>
	  	
		<form action="testReport.do?key={$key}" name="paginate" method="post">
			<input type="hidden" name="queryName" value="{$qName}" />
			<input type="hidden" name="p_page" value="{$p_page}" />
			<input type="hidden" name="p_step" value="{$p_step}" />
			<input type="hidden" name="p_highlight" value="{$p_highlight}"/>
			<input type="hidden" name="p_pval_filter_mode" value="{$p_pval_filter_mode}"/>
			<input type="hidden" name="p_pval_filter_value" value="{$p_pval_filter_value}"/>
			<input type="hidden" name="p_pval_filter_op" value="{$p_pval_filter_op}"/>
			<input type="hidden" name="p_sort_element" value="{$p_sort_element}"/>
			<input type="hidden" name="p_sort_method" value="{$p_sort_method}"/>
			<input type="hidden" name="showAllValues" value="{$showAllValues}"/>
		</form>
	
		<form action="runReport.do?method=switchViews" method="post" name="switchViewsForm">
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
					</b>
			 	</div>
 	
 				<div id="hideme">

					<div class="filterForm">
						<form style="margin-bottom:0;" action="testReport.do?key={$key}" method="post" name="pval_filter_form">
							<b><span class="lb"><label for="p_pval_filter_mode">Filter p-value:</label></span></b> 
							<xsl:text>&#160;</xsl:text>
							Show values:
							<select id="p_pval_filter_mode" name="p_pval_filter_mode">
								<option value="gt">&gt;</option>
								<option value="lt">&lt;</option>
								<option value="eq">=</option>
								<option value="lte">&lt;=</option>
								<option value="gte">&gt;=</option>
							</select>
							<!-- 
							<span id="showOnlyLabel"><input type="radio" class="checkorradio" name="p_pval_filter_mode" id="showOnly_radio" value="show" />Show Only</span>
							<span id="hideLabel"><input type="radio" class="checkorradio" name="p_pval_filter_mode" id="hide_radio" checked="true" value="hide"/>Hide</span>		
							-->
							<xsl:text>&#160;</xsl:text>
							<xsl:text>&#160;</xsl:text>
							<!--  P-Value  -->
							<input type="text" id="p_pval_filter_value" name="p_pval_filter_value" size="25" value="{$p_pval_filter_value}" /><label for="p_pval_filter_value">&#160;</label>
							<input type="hidden" name="p_page" value="0"/>
							<input type="hidden" name="p_step" value="25"/>
							<input type="hidden" name="p_sort_element" value="{$p_sort_element}"/>
							<input type="hidden" name="p_sort_method" value="{$p_sort_method}"/>
							<input type="button" onclick="processFilterForm('{$key}', 'filter');" name="filter_submit" value="Filter" />
							<input type="button" name="filter_submit" value="Reset (Show All)" onclick="processFilterForm('{$key}', 'reset');" />
						</form>
					</div>
		
						
							  
		<div class="filterForm">
			<form style="margin-bottom:0;" action="testReport.do?key={$key}" method="post" name="highlight_form">
				<b><span class="lb">Highlight:</span></b> 
				<xsl:text>&#160;</xsl:text>
				<label for="p_highlight_op">highlight values</label> 
				<select id="p_highlight_op" name="p_highlight_op">
					<option value="gt">&gt;</option>
					<option value="lt">&lt;</option>
					<option value="eq">=</option>
					<option value="lte">&lt;=</option>
					<option value="gte">&gt;=</option>
				</select>
				<input type="text" id="p_highlight" name="p_highlight" size="4" value="{$p_highlight}" /><label for="p_highlight">&#160;</label>
				<input type="hidden" name="queryName" value="{$qName}"/>
				<input type="hidden" name="p_page" value="{$p_page}"/>
				<input type="hidden" name="p_step" value="{$p_step}"/>
				<input type="hidden" name="p_sort_element" value="{$p_sort_element}"/>
				<input type="hidden" name="p_sort_method" value="{$p_sort_method}"/>
				<input type="button" onclick="processHighlightForm('{$key}', 'highlight');" name="filter_submit" value="Highlight" />
				<input type="hidden" name="showAllValues" value="{$showAllValues}"/>
				<input type="button" name="filter_submit" value="Clear Highlighting" onclick="processHighlightForm('{$key}', 'unhighlight');" />
			</form>
		</div>
	  
			<div class="filterForm" style="height:auto">
				<b><span class="lb"><label for="tmp_prb_queryName">Select Reporters:</label></span></b> 
				<xsl:text>&#160;</xsl:text>
				<input type="text" size="30" id="tmp_prb_queryName" name="tmp_prb_queryName" value="{$key}" />
				<label for="repSubType">Type:</label><select id="repSubType" name="repSubType"></select>
				<script type="text/javascript">populateReporterTypeDD();</script>
				<input type="button" name="filter_submit" value="Save Reporters" onclick="javascript:A_saveReporters();" />
				<xsl:text>&#160;</xsl:text>
				<br/>
				<span style="margin-left:100px" id="checkAllBlock"><input type="checkbox" name="checkAll" id="checkAll" class="checkorradio" onclick="javascript:A_checkAllOnAll(this);"/> <label for="checkAll">All on all pages</label></span>
				<span style="margin-left:100px" id="checkAllBlock"><input type="checkbox" name="checkAll" id="checkAll" class="checkorradio" onclick="javascript:A_checkAll(document.getElementsByName('tmpReporter'));"/> All on all this page</span>
				
				<!-- 
				<xsl:text>&#160;</xsl:text>
				<a href="#" onclick="javascript:A_checkAll(document.getElementsByName('tmpReporter'));return false;">[Check All]</a>
				<xsl:text>&#160;</xsl:text>
				<a href="#" onclick="javascript:A_uncheckAll(document.getElementsByName('tmpReporter'));return false;">[Uncheck All]</a>
				-->
			 	<!-- 
			 	<xsl:text>&#160;</xsl:text>
			 	<b><a href="#" onclick="javascript:return false;" onmouseover="javascript:return showHelp('You can select the samples of interest by clicking on each individual sample or a group and saving them with a unique name. This allows you to select this sample set to apply your future queries to.');" onmouseout="return nd();">[?]</a></b>
			 	-->
			 	<xsl:text>&#160;</xsl:text>
			 	<a href="#" onclick="javascript:return false;" onmouseover="javascript:return showHelp(savedHeader + currentTmpReporters);" onmouseout="return nd();" id="reporterCount"></a>
			 	
			 	<xsl:text>&#160;</xsl:text>
			 	<a href="#" onclick="javascript:A_clearTmpReporters(); return false;" onmouseover="javascript:return showHelp('Clear these reporters');" onmouseout="return nd();">[clear reporters]</a>
		  	</div>
	 
	  
	  <div class="pageControl" style="padding-bottom:1px;margin-bottom:0px;">
	  <!-- <xsl:value-of select="$recordCount" /> records returned. -->
	  <b><span class="lb">Displaying:</span></b> 
	  <xsl:text>&#160;</xsl:text>
	  <xsl:value-of select="$p_step * $p_page+1" /> - 
	  <xsl:choose>
	  	<xsl:when test="$recordCount > ($p_step * ($p_page+1))">
		  <xsl:value-of select="$p_step * ($p_page+1)" />
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$recordCount" />
		</xsl:otherwise>
	  </xsl:choose>
	  of <xsl:value-of select="$recordCount" /> records
	  <xsl:text>&#160;</xsl:text>
	  <!-- if theres a next show it-->
	  <xsl:if test="$p_page>0">
	  	<xsl:variable name="ppage" select="$p_page - 1"/> 
	   	<a href="javascript:goPage('{$ppage}');">&lt;&lt;Prev</a>
		<xsl:text>&#160;</xsl:text>
	  </xsl:if>
	  <xsl:if test = "$recordCount > ($p_step * ($p_page +1))">
	   <xsl:variable name="npage" select="$p_page + 1" />
	   <a href="javascript:goPage('{$npage}');">Next&gt;&gt;</a>
	  </xsl:if>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:if test="ceiling($recordCount div $p_step) > 1">
		  <b><xsl:value-of select="ceiling($recordCount div $p_step)" /> page(s):</b>
		  <script language="javascript">
		  	var records = <xsl:value-of select="ceiling($recordCount div $p_step)"/>;
		  	var cPage = <xsl:value-of select="$p_page"/>;
		  	<![CDATA[	
		  		for(i=0;records!=i; i++)	{
		  			stupidXSL(i, cPage, records);
		  		}
		  	]]>
		  </script>
	  </xsl:if>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  <select id="changeStep" name="changeStep" onchange="javascript: goPageChangeStep('{$p_page}', this.value);">
	  	<option value=""><xsl:value-of select="$p_step"/> per page</option>
	  	<option value="1">1</option>
	  	<option value="5">5</option>
	  	<option value="10">10</option>
	  	<option value="25">25</option>
	  	<option value="50">50</option>
	  	<option value="100">100</option>
	  	<!-- <option value="1000">1000</option> -->
	  </select><label for="changeStep">&#160;</label>
	 
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  </div>
	  
	 </div>
	</div>
</div>

<!--  process reps -->
<script language="javascript">
	<xsl:for-each select="Row/Cell[@class='reporter']">
		<xsl:if test="Data != ''">
			allReporters.push('<xsl:value-of select="Data"/>');
		</xsl:if>
	</xsl:for-each>
	<xsl:for-each select="Row/Cell">
		<xsl:if test="($p_highlight_op = 'gt' and Data > $p_highlight) or ($p_highlight_op = 'lt' and $p_highlight > Data) or ($p_highlight_op = 'eq' and $p_highlight >= Data and Data >= $p_highlight) or ($p_highlight_op = 'lte' and $p_highlight >= Data) or ($p_highlight_op = 'gte' and Data >= $p_highlight)">
			allHighlightedReporters.push('<xsl:value-of select="../Cell[1]/Data"/>');
			//DynamicReport.saveTmpReporter('<xsl:value-of select="../Cell[1]/Data"/>', A_saveTmpReporter_cb);
		</xsl:if>
	</xsl:for-each>
	DynamicReport.saveTmpGenericFromArray('tmpReporterList', allHighlightedReporters, A_saveTmpReporter_cb);
</script>

    <table cellpadding="0" cellspacing="0" id="dataTable">
		<xsl:for-each select="Row[@name='headerRow']">
			<tr class="headerRow">
		  	<xsl:for-each select="Cell[@class != 'csv']">
			<xsl:choose>
					<xsl:when test="@group='header'">
					<xsl:variable name="currentone" select="position()"/>
						<td style="color:red">
							<xsl:value-of select="Data" />
							<img id="{$currentone}_sort_img_up" alt="Up" style="margin-left:5px;" src="images/openUpArrow.png" onclick="javascript:goSort('{$currentone}','ascending', '{$key}');" />
							<img id="{$currentone}_sort_img_down" alt="Down" style="margin-left:0px;" src="images/openDownArrow.png" onclick="javascript:goSort('{$currentone}','descending', '{$key}');" />
						</td>
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
		    <label for="grpcheck">&#160;</label>
		    </tr>
		</xsl:for-each>
		
		<form action="runReport.do?method=submitSamples" method="post" name="prbSamples">
		<input type="hidden" name="queryName" value="{$qName}"/>
		
		
		
		<!-- get each data row only -->
		<!--  should be going filtering here, also copy to record count -->
		<xsl:for-each select="(Row[@name='dataRow']) [$p_pval_filter_value = ''] | (Row[@name='dataRow' and number($p_pval_filter_value) = number(Cell[2]/Data)]) [$p_pval_filter_value != '' and $p_pval_filter_mode = 'eq'] | (Row[@name='dataRow' and number($p_pval_filter_value) > number(Cell[2]/Data)]) [$p_pval_filter_value != '' and $p_pval_filter_mode = 'lt'] | (Row[@name='dataRow' and number($p_pval_filter_value) >= number(Cell[2]/Data)]) [$p_pval_filter_value != '' and $p_pval_filter_mode = 'lte'] | (Row[@name='dataRow' and number(Cell[2]/Data) > number($p_pval_filter_value)]) [$p_pval_filter_value != '' and $p_pval_filter_mode = 'gt'] |(Row[@name='dataRow' and number(Cell[2]/Data) >= number($p_pval_filter_value)]) [$p_pval_filter_value != '' and $p_pval_filter_mode = 'gte']">	
			<xsl:sort select="(Cell[number($p_sort_element)]/Data) [$p_sort_element != '']" order="{$p_sort_method}" data-type="{$dtype}" />
	
			<xsl:variable name="pvalue" select="Cell[2]/Data"/>
			<xsl:variable name="rep" select="Cell[1]/Data"/>

			<xsl:if test="$p_step + ($p_step * $p_page)>=position() and position() > ($p_page * $p_step)">	
				
					<tr id="{$rep}" name="{$rep}">
		  				<xsl:for-each select="Cell[@class != 'csv']">
		  	  			<xsl:variable name="class" select="@group" />
		  	  			<xsl:variable name="styleclass" select="@class" />
		  	  			<xsl:variable name="theData" select="Data"/>
		  	  			<xsl:variable name="theType" select="@type"/>
		  	  			<xsl:variable name="highlightThisCell">
							<xsl:choose>
								<xsl:when test="($p_highlight_op = 'gt' and Data > $p_highlight) or ($p_highlight_op = 'lt' and $p_highlight > Data) or ($p_highlight_op = 'eq' and $p_highlight >= Data and Data >= $p_highlight) or ($p_highlight_op = 'lte' and $p_highlight >= Data) or ($p_highlight_op = 'gte' and Data >= $p_highlight)">yes</xsl:when>
								<xsl:otherwise>no</xsl:otherwise>		
							</xsl:choose>
						</xsl:variable>
		  	  			
		      			<td class="{$class}" id="{$theType}" name="{$theType}">
						
		      			<xsl:choose>
		      				<xsl:when test="$styleclass = 'gene' and $theData != '--' and $theData != '-'">
		      					<a href="#" onclick="javascript:spawnAnnot('gene', this); return false;"><xsl:value-of select="Data"/></a>
		      				</xsl:when>
		      				<xsl:when test="($styleclass = 'reporter' or $styleclass = 'cytoband') and $theData != '-'">
		      						<input type="checkbox" class="checkorradio" id="tmpReporter" name="tmpReporter" value="{$theData}" onclick="javascript:A_saveTmpReporter(this);" /><a href="#" onclick="javascript:spawnAnnot('reporterFromCC',this); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
			      			<xsl:when test="$class = 'sample'">
			      				<xsl:variable name="sample" select="Data"  />
			      				<xsl:value-of select="Data" />
			      				<!-- <input class="checkorradio" type="checkbox" name="samples" value="{$sample}"/> -->
							</xsl:when>
			      			<xsl:when test="$p_highlight_op != ''">
			      				<xsl:choose>
			      					<xsl:when test="$highlightThisCell = 'yes'">
					      				<span style="background-color:yellow"><xsl:value-of select="Data"/></span>
					      				<script language="javascript">
					      					//autoCheckHighlighted('<xsl:value-of select="$rep"/>');
					      				</script>
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
			      							<span class="missing" style="color:gray;"><xsl:value-of select="Data"  /></span>
			      						</xsl:if>
				      					<xsl:if test="$styleclass != 'highlighted'">
				      						<xsl:value-of select="Data" />
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
		<xsl:comment>
				<xsl:if test="/Report[@reportType != 'Clinical'] and ./Cell[1]/Data[1]/text() != following::Cell[1]/Data[1]/text() and following::Cell[1]/Data[1]/text() != ''">
					<tr>
		      			<td colspan="{$colCount}" class="geneSpacerStyle">--</td>
		    		</tr>
				</xsl:if>
		</xsl:comment>
			</xsl:if>
<!--  close TR filter -->
			
		</xsl:for-each>
	</form> <!--  close PRB samples form -->
  	</table>
    <label for="tmpReporter">&#160;</label>
  	<div id="query_details">
  	<a name="queryInfo"></a>
  	<xsl:for-each select="Query_details">
  		<xsl:value-of select="Data" />
  	</xsl:for-each>
  	</div>
  	<div>
  	<script language="javascript">
  		//goQueryDetails("<xsl:copy-of select="$queryDetails" />");
  		convertSci();
  		fixQueryDetails();
  	</script>
  	</div>
  	</xsl:if><!-- no records -->
  	<xsl:if test="$recordCount = 0">
  		<h3 style="text-align:center; margin-top:200px;">No records were returned.  <br/><a href="javascript:window.close()">Close</a><br/><a href="javascript:history.back()">Back</a></h3>
  	</xsl:if>
  </xsl:for-each>

  <script language="javascript">
  if(document.highlight_form){
 	selectHOperand(document.pval_filter_form.p_pval_filter_value, '<xsl:value-of select="$p_pval_filter_value"/>');
 	}
  if(document.pval_filter_form){
 	selectHideShowOnly(document.pval_filter_form.p_pval_filter_mode, '<xsl:value-of select="$p_pval_filter_mode"/>');
 	}
  </script>

  </span>
</xsl:template>

</xsl:stylesheet>