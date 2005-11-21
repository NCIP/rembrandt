<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" omit-xml-declaration="yes" /> 

<xsl:param name="p_highlight"></xsl:param>
<xsl:param name="p_highlight_op">gt</xsl:param>

<xsl:param name="p_page">0</xsl:param>
<xsl:param name="p_step">25</xsl:param>

<xsl:param name="key"></xsl:param>

<xsl:param name="filter_value5"></xsl:param>
<xsl:param name="filter_value6"></xsl:param>

<xsl:param name="showSampleSelect"></xsl:param>

<xsl:param name="allowShowAllValues">true</xsl:param>
<xsl:param name="queryDetails">N/A</xsl:param>
<xsl:param name="statusMsg"></xsl:param>
<xsl:param name="showAllValues">false</xsl:param>
<xsl:template match="/">

<span>

  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;">Help</div>

  <div style="background-color: #ffffff"><img src="images/smallHead.jpg" /></div>
  <p align="center" style="background:red; color:#ffffff; font-size:12px; font-weight:bold;"><xsl:value-of select="$statusMsg" /></p>
 
   <xsl:for-each select="Report">

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

		<form action="testReport.do?key={$key}" name="paginate" method="post">
			<input type="hidden" name="queryName" value="{$qName}" />
			<input type="hidden" name="p_page" value="{$p_page}" />
			<input type="hidden" name="p_step" value="{$p_step}" />
			<input type="hidden" name="p_highlight" value="{$p_highlight}"/>
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
							  
		<div class="filterForm">
			<form style="margin-bottom:0;" action="testReport.do?key={$key}" method="post" name="highlight_form">
				<b><span class="lb">Highlight:</span></b> 
				<xsl:text>&#160;</xsl:text>
				highlight values 
				<select name="p_highlight_op">
					<option value="gt">&gt;</option>
					<option value="lt">&lt;</option>
					<option value="eq">=</option>
					<option value="lte">&lt;=</option>
					<option value="gte">&gt;=</option>
				</select>
				<input type="text" name="p_highlight" size="4" value="{$p_highlight}" />
				<input type="hidden" name="queryName" value="{$qName}"/>
				<input type="hidden" name="p_page" value="{$p_page}"/>
				<input type="hidden" name="p_step" value="{$p_step}"/>
				<input type="submit" name="filter_submit" value="Highlight" />
				<input type="hidden" name="showAllValues" value="{$showAllValues}"/>
				<input type="submit" name="filter_submit" value="Clear Highlighting" onclick="javascript:document.highlight_form.p_highlight.value='';" />
			</form>
		</div>
	  
		<xsl:if test="$showSampleSelect != 'false' and contains($qName, 'previewResults') = false">
			<div class="filterForm">
				<b><span class="lb">Select Reporters:</span></b> 
				<xsl:text>&#160;</xsl:text>
				<input type="text" size="30" id="tmp_prb_queryName" name="tmp_prb_queryName" value="{$key}" />
				<input type="button" name="filter_submit" value="Save Reporters" onclick="javascript:A_saveReporters();" />
			
				<xsl:text>&#160;</xsl:text>
				<span id="checkAllBlock"><input type="checkbox" name="checkAll" id="checkAll" class="checkorradio" onclick="javascript:manageCheckAll(this);"/> All on page</span>
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
		</xsl:if>
	 
	  </xsl:if>
	  
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
	  <select name="changeStep" onchange="javascript: goPageChangeStep('{$p_page}', this.value);">
	  	<option value=""><xsl:value-of select="$p_step"/> per page</option>
	  	<option value="1">1</option>
	  	<option value="5">5</option>
	  	<option value="10">10</option>
	  	<option value="25">25</option>
	  	<option value="50">50</option>
	  	<option value="100">100</option>
	  	<option value="1000">1000</option>
	  </select>
	 
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  <xsl:text>&#160;</xsl:text>
	  </div>
	  
	 </div>
	</div>
</div>
    <table cellpadding="0" cellspacing="0" id="dataTable">
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
		
		<!-- get each data row only -->
		<!--  should be going filtering here, also copy to record count -->
		<xsl:for-each select="Row[(@name='dataRow')] ">
			<xsl:if test="$p_step + ($p_step * $p_page)>=position() and position() > ($p_page * $p_step)">	
					<xsl:variable name="pvalue" select="Cell[3]/Data"/>
					<tr id="{$pvalue}" name="{$pvalue}">
		  				<xsl:for-each select="Cell[@class != 'csv']">
		  	  			<xsl:variable name="class" select="@group" />
		  	  			<xsl:variable name="styleclass" select="@class" />
		  	  			<xsl:variable name="theData" select="Data"/>
		      			<td class="{$class}">

		      			<xsl:choose>
		      				<xsl:when test="$styleclass = 'gene' and $theData != '-'">
		      					<a href="#" onclick="javascript:spawnAnnot('gene', this); return false;"><xsl:value-of select="Data"/></a>
		      				</xsl:when>
		      				<xsl:when test="($styleclass = 'reporter' or $styleclass = 'cytoband') and $theData != '-'">
		      						<input type="checkbox" class="checkorradio" id="tmpReporter" name="tmpReporter" value="{$theData}" onclick="javascript:A_saveTmpReporter(this);" /><a href="#" onclick="javascript:spawnAnnot('reporter',this); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
			      			<xsl:when test="$class = 'sample'">
			      				<xsl:variable name="sample" select="Data"  />
			      				<xsl:value-of select="Data" />
			      				<!-- <input class="checkorradio" type="checkbox" name="samples" value="{$sample}"/> -->
							</xsl:when>
			      			<xsl:when test="$p_highlight != 000 and $p_highlight_op != ''">
			      				<xsl:choose>
			      					<xsl:when test="$p_highlight_op = 'gt' and Data > $p_highlight">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$p_highlight_op = 'lt' and $p_highlight > Data">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$p_highlight_op = 'eq' and $p_highlight = Data">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$p_highlight_op = 'lte' and $p_highlight >= Data">
					      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      					</xsl:when>
			      					<xsl:when test="$p_highlight_op = 'gte' and Data >= $p_highlight">
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
		<xsl:comment>
				<xsl:if test="/Report[@reportType != 'Clinical'] and ./Cell[1]/Data[1]/text() != following::Cell[1]/Data[1]/text() and following::Cell[1]/Data[1]/text() != ''">
					<tr>
		      			<td colspan="{$colCount}" class="geneSpacerStyle">--</td>
		    		</tr>
				</xsl:if>
		</xsl:comment>
			</xsl:if>

		</xsl:for-each>
	</form> <!--  close PRB samples form -->
  	</table>
  	<div>
  	<script language="javascript">
  		goQueryDetails("<xsl:copy-of select="$queryDetails" />");
  	</script>
  	</div>
  	</xsl:if><!-- no records -->
  	<xsl:if test="$recordCount = 0">
  		<h3 style="text-align:center; margin-top:200px;">There was an error generating your report.  Please try again later. <br/><a href="javascript:window.close()">Close</a></h3>
  	</xsl:if>
  </xsl:for-each>

  <script language="javascript">
  if(document.highlight_form){
 	selectHOperand(document.highlight_form.p_highlight_op, '<xsl:value-of select="$p_highlight_op"/>');
 	}
  </script>

  </span>
</xsl:template>

</xsl:stylesheet>