<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" omit-xml-declaration="yes" /> 

<xsl:param name="filter_value1"></xsl:param>

<xsl:param name="filter_value2">0</xsl:param>
<xsl:param name="filter_value3">25</xsl:param>

<xsl:param name="filter_type">greater</xsl:param>

<xsl:template match="/">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  	<head>
	<title>My Report</title>
	<script language="JavaScript" type="text/javascript" src="js/overlib.js">return false;
	</script>
	<script language="JavaScript" type="text/javascript" src="js/overlib_hideform.js">return false;
	</script>
	<script language="JavaScript" type="text/javascript" src="js/caIntScript.js">return false;
	</script> 
	<script language="JavaScript" type="text/javascript" src="XSL/js.js">return false;
	</script> 
	<style>
		body { font-family:arial; font-size: 11px; margin-top: 0px}
		TABLE	{border: 0px; padding:0px; font-size:12px;}
		Td {
		
			font-size: 12px;
			background: #F2F2F2;
			padding: 4px; 
			border: 1px solid white;
			color: #000000;
			white-space:nowrap;
		
			}
		#header { font-weight: bold;
		 			font-size: 12px;
		 		}
		
		a, a:visited {color:blue}
		
		td.ASTROCYTOMA { background-color: #B6C5F2; }
		td.GBM { background-color: #F2E3B5; }
		td.MIXED { background-color: #DAE1F9; }
		td.OLIG { background-color: #C4F2B5; }
		
		.sampleRow, .headerRow { font-weight: bold; }
		.rowCount { font-size:11px; padding:2px;}

		.title { font-size: 16px; font-weight: bold; padding-bottom: 10px; font-family: tahoma }
		
		.geneSpacerStyle { 	
		height: 3px; 
						font-size: 1px; 
						border-top: 1px solid black; 
						border-left: 2px solid black;
						border-right: 2px solid black;
						border-bottom: 2px solid black;  
						padding: 0px; 
						background-color: #ffffff;
					}
		
			.fontClass { font-size: 10px; 
				font-family: verdana;
				color:#000000; 
				padding:1px; 
				border-top:0px; border-bottom:0px; border-right:0px; border-left:0px;
				white-space:normal;
				background-color:#e9e9e9; 
				margin: 0px;
				}
  	.capfontClass { font-size: 10px; 
  					font-family: verdana;
  					color:#ffffff; 
  					padding:1px; 
  					border-top:0px; border-bottom:0px; border-right:0px; border-left:0px;
  					white-space:normal;
  					background-color: #AB0303; 
  					margin: 0px;
  					}
  	.fgClass {font-size: 10px; 
  			font-family: verdana;
  			padding:1px; 
  			border-top:1px solid #AB0303;border-bottom:1px solid #AB0303;border-left:1px solid #AB0303;border-right:1px solid #AB0303;
  			margin: 0px;
  			white-space:normal;
  			background-color:;
  			}
  	.bgClass {font-size: 10px; 
  			font-family: verdana;
  			padding:1px; 
  			border-top:0px; border-bottom:0px; border-right:0px; border-left:0px; 
  			margin: 0px;
  			white-space:normal;
  			background-color:;
  			}
  	.pageControl, .filterForm { 
  					/*background-color:#F2F2F2; */
  					background-color: #ffffff;
  					background-image: url(images/buttonbg.png);
  					background-repeat: repeat-x;
  					padding:3px;
  					padding-left:8px; 
  					border-left:1px dotted black; 
  					border-right:1px dotted black; 
  					height: 21px;
  					overflow: none;
  					margin-bottom:5px;
  				}
	.rptHeader	{
		background-color:#F1F1F1;
		/* background-color: #ffffff; */
		padding:5px;
		padding-bottom:1px;
	}
  	INPUT, SELECT { 
		/*color: #002185;*/
		font-family: arial; 
		font-size:10px; 
		padding: 1px; 
		border-style: solid; 
		border-width: 1px; 
		border-color: #000000; 
		}
		.checkorradio	{
			border: 0px;
		}
		.lb	{
			display:block;
			float:left;
			width:100px;
			text-align:left;
		}
	</style>
	</head>
  <body>
  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;">Help</div>
  <div style="background-color: #ffffff"><img src="images/smallHead.jpg" /></div>
  

  <xsl:for-each select="Report">
    
    <!--
    <fieldset>
    <legend>DEBUG USE ONLY, PLEASE IGNORE THIS</legend>
    <h3>Test: <xsl:value-of select="$filter_value1"/>,
    <xsl:value-of select="$filter_value2"/>,
    <xsl:value-of select="$filter_value3"/>
    </h3>
	</fieldset><br/>
	-->
	<xsl:variable name="helpLink" select="@reportType" />
	<xsl:variable name="colCount" select="count(Row[2]/Cell)" />
	<xsl:variable name="recordCount" select="count(Row[@name='dataRow'])" />
	<xsl:variable name="qName" select="@queryName" />
	<xsl:variable name="rType" select="@reportType" />

<form action="runReport.do?method=runGeneViewReport" name="paginate" method="post">
<input type="hidden" name="queryName" value="{$qName}" />
<input type="hidden" name="filter_value2" value="{$filter_value2}" />
<input type="hidden" name="filter_value3" value="{$filter_value3}" />
<input type="hidden" name="filter_value1" value="{$filter_value1}"/>
</form>
<div class="rptHeader">	
	<div class="rowCount">
	 <div style="background-color:#ffffff; margin-bottom:5px; padding-bottom:5px; border-left:1px solid black; border-right:1px solid black;">
	  <!-- navigation icons courtesy of:  Anthony J. Brutico, D.O. -->
	  <a href="#" onclick="javascript:window.close();"><img align="right" src="images/close.png" border="0" onmouseover="return overlib('Close this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/> </a> 
	  <a href="javascript: spawn('help.jsp?sect={$helpLink}', 350, 500);"><img align="right" src="images/help.png" border="0" onmouseover="return overlib('Click here for additional information about this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();" /></a>
	  <a href="#" onclick="javascript:return false;"><img align="right" src="images/excel.png" border="0" alt="download for excel" onmouseover="return overlib('Download for Excel.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/></a>
	  <a href="#" onclick="javascript:window.print();"><img align="right" src="images/print.png" border="0" onmouseover="return overlib('Print this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/> </a> 
<!--	  <a href="#queryInfo"><img align="right" src="images/text.png" border="0" onmouseover="return overlib('View Query Information.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/></a> -->
	  <a href="#" onclick="javascript:toggleDiv('hideme');return false;"><img align="right" src="images/tools.png" border="0" onmouseover="return overlib('Show or Hide Report Tools.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();"/></a>

		<b class="title" style="display:block; padding:3px;">
		<xsl:value-of select="@reportType" />		
		<span style="font-weight:normal; font-size:12px">(Query Name:<xsl:value-of select="@queryName" />)</span>
		</b>
		<br/><a style="margin-left:10px" href="#" onclick="javascript:toggleDiv('hideme');return false;">[Show/Hide Form Tools]</a>
		<xsl:text>&#160;</xsl:text><xsl:text>&#160;</xsl:text> 
 	</div>
 	<div id="hideme">
	  <xsl:if test="@reportType != 'Gene Expression Disease' and @reportType != 'Clinical'" >
 
	  <div class="filterForm">
	  <form style="margin-bottom:0;" action="runReport.do?method=runGeneViewReport" method="post" name="filter_form">
		<b><span class="lb">Filter:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<input type="radio" class="checkorradio" name="filter_type" value="show" />Show Only
		<input type="radio" class="checkorradio" name="filter_type" checked="true" value="hide"/>Hide		
		<select name="filter_element">
			<xsl:if test="$rType = 'Gene Expression Sample' or $rType = 'Gene Expression Disease'">
			<option value="gene">Gene(s)</option>
			</xsl:if>
			<xsl:if test="$rType = 'Copy Number'">
			<option value="cytoband">Cytoband(s)</option>
			</xsl:if>
			<option value="reporter">Reporters</option>
		</select>
		<input type="text" name="filter_string"/>
		<input type="hidden" name="queryName" value="{$qName}"/>
		<input type="submit" name="filter_submit" value="Filter" />
		<input type="button" name="filter_submit" onclick="javascript:clearFilterForm(document.forms['filter_form']);" value="Reset (show all)" />
	  </form>
	  </div>
	  
	  <div class="filterForm">
	  <form style="margin-bottom:0;" action="runReport.do?method=runGeneViewReport" method="post" name="filter_form">
		<b><span class="lb">Highlight:</span></b> 
		<xsl:text>&#160;</xsl:text>
		highlight values greater than <input type="text" name="filter_value1" size="4" value="{$filter_value1}" />
		<input type="hidden" name="queryName" value="{$qName}"/>
		<input type="hidden" name="filter_value2" value="{$filter_value2}"/>
		<input type="hidden" name="filter_value3" value="{$filter_value3}"/>
		<input type="submit" name="filter_submit" value="Highlight" />
	  </form>
	  </div>
	  
	  
	  <div class="filterForm">
		<b><span class="lb">Select Samples:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<input type="text" size="30" id="tmp_prb_queryName" name="tmp_prb_queryName" value="{$qName}" />
		<input type="button" name="filter_submit" value="Save Samples" onclick="javascript:saveSamples();" />
		<xsl:text>&#160;</xsl:text>
		<a href="#" onclick="javascript:checkAll(document.prbSamples.samples);return false;">[Check All]</a>
		<xsl:text>&#160;</xsl:text>
		<a href="#" onclick="javascript:uncheckAll(document.prbSamples.samples);return false;">[Uncheck All]</a>
	  </div>
	  
  	<div class="filterForm">
		<b><span class="lb">Show all Values:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<input type="button" name="filter_submit" value="Show all values on this report" onclick="javascript:location.href='runReport.do?method=runShowAllValuesQuery&amp;queryName={$qName}';" />
		<xsl:text>&#160;</xsl:text>
		<a href="#" onclick="javascript:return false;" onmouseover="javascript:return showHelp('show all values');" onmouseout="return nd();">[?]</a>
	  </div>
	  
	 <div class="filterForm">
		<b><span class="lb">Hide Diseases:</span></b> 
		<xsl:text>&#160;</xsl:text>
		<xsl:for-each select="Row[@name='headerRow']">
		  	<xsl:for-each select="Cell">
			  	<xsl:if test="@group!='header'">
						<xsl:variable name="currentGroup" select="@group" />
						<input class="checkorradio" type="checkbox" onclick="javascript:goFilterColumnMg(this, '{$currentGroup}')"/>
						<xsl:value-of select="$currentGroup"/>
						<!--
						[<a href="#" onclick="javascript:goFilterColumn(true, '{$currentGroup}')">Hide <xsl:value-of select="$currentGroup"/></a> | 
						<a href="#" onclick="javascript:goFilterColumn(false, '{$currentGroup}')">Show <xsl:value-of select="$currentGroup"/></a> ]
						-->
						<xsl:text>&#160;</xsl:text>
				</xsl:if>
			</xsl:for-each>
		</xsl:for-each>
						
		<xsl:text>&#160;</xsl:text>
		<xsl:text>&#160;</xsl:text>
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
	  	<xsl:value-of select="count(Row[@name='sampleRow']/Cell)-2" /> samples returned.
	  </xsl:if>
	  </div>
	  
	 </div>
	</div>
</div>
    <table cellpadding="0" cellspacing="0">
		<xsl:for-each select="Row[@name='headerRow']">
			<tr class="headerRow">
		  	<xsl:for-each select="Cell">
			<xsl:choose>
					<xsl:when test="@group='header'">
						<td style="color:red"><xsl:value-of select="Data" /></td>
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="currentGroup" select="@group" />
						<xsl:variable name="colspan" select="count(/Report/Row[@name='sampleRow']/Cell[@group=$currentGroup])"/>
						<td colspan="{$colspan}" class="{$currentGroup}">
							<xsl:value-of select="Data" />
							<xsl:if test="/Report[@reportType != 'Gene Expression Disease'] and /Report[@reportType != 'Clinical']" >
								<input id="grpcheck" class="checkorradio" type="checkbox" onclick="javascript:groupCheck(document.prbSamples.samples, '{$currentGroup}', this.checked)" />
							<!--
								<a href="#" onclick="javascript:checkById(document.prbSamples.samples, '{$currentGroup}');return false;">[all]</a>
								<a href="#" onclick="javascript:uncheckById(document.prbSamples.samples, '{$currentGroup}');return false;">[none]</a>
								<a href="#" onclick="javascript:toggleCheckById(document.prbSamples.samples, '{$currentGroup}');return false;">[toggle]</a>
							-->
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
		  	<xsl:for-each select="Cell">
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
		      	<xsl:if test="$sample != '' and $sample != ' '">
		      		<input id ="{$currentGroup}" class="checkorradio" type="checkbox" name="samples" value="{$sample}"/>
		      	</xsl:if>
		      		<a href="#?s={$sample}"><xsl:value-of select="Data" /></a>
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
		  				<xsl:for-each select="Cell">
		  	  			<xsl:variable name="class" select="@group" />
		  	  			<xsl:variable name="styleclass" select="@class" />
		  	  			<xsl:variable name="theData" select="Data"/>
		      			<td class="{$class}">

		      			<xsl:choose>
		      				<xsl:when test="$styleclass = 'gene' and $theData != '-'">
		      					<a href="#" onclick="javascript:spawnAnnot('gene', '{$theData}'); return false;"><xsl:value-of select="Data"/></a>
		      				</xsl:when>
		      				<xsl:when test="$styleclass = 'reporter' and $theData != '-'">
		      						<a href="#" onclick="javascript:spawnAnnot('reporter','{$theData}'); return false;"><xsl:value-of select="Data"/></a>	
		      				</xsl:when>
			      			<xsl:when test="$class = 'sample'">
			      				<xsl:variable name="sample" select="Data"  />
			      				<xsl:value-of select="Data" />
			      				<!-- <input class="checkorradio" type="checkbox" name="samples" value="{$sample}"/> -->
							</xsl:when>
			      			<xsl:when test="$filter_value1 != 000 and Data > $filter_value1">
			      				<span style="background-color:yellow"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      			</xsl:when>
			      			<xsl:otherwise>
			      				<xsl:if test="@class = 'highlighted'">
			      					<span class="missing" style="color:gray;"><xsl:value-of select="Data" disable-output-escaping="yes" /></span>
			      				</xsl:if>
			      				<xsl:if test="@class != 'highlighted'">
			      					<xsl:value-of select="Data" disable-output-escaping="yes" />
			      				</xsl:if>
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

<!-- the pagination form -->
<xsl:variable name="nextpage" select = "$filter_value2 + 1" />
<xsl:variable name="prevpage" select = "$filter_value2 - 1" />

		</xsl:for-each>
	</form>
  	</table>
  </xsl:for-each>
 <script language="javascript">
 <![CDATA[hideLoadingMessage();]]>
 </script>
<script language="javascript">
<![CDATA[
function goPage(p)	{
	 	document.forms['paginate'].filter_value2.value = p;
	 	document.forms['paginate'].submit();
	 }
function goPageChangeStep(p, s)	{
	if(s != '')	{
	 	document.forms['paginate'].filter_value2.value = 0; 
	 	document.forms['paginate'].filter_value3.value = s; 
	 	document.forms['paginate'].submit(); 
	}
	/*alert(p);
	alert(s); */
	 }
	 ]]>
</script>

  </body>
  </html>
</xsl:template>

</xsl:stylesheet>