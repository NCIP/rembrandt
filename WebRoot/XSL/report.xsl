<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" omit-xml-declaration="yes" /> 

<xsl:param name="filter_value"></xsl:param>
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
	</style>
	</head>
  <body>
  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;">Help</div>
  <div style="background-color: #ffffff"><img src="images/smallHead.jpg" /></div>
  

  <xsl:for-each select="Report">
    <h2 class="title"><xsl:value-of select="@reportType" /></h2> 
    
	<xsl:variable name="helpLink" select="@reportType" />
	<xsl:variable name="colCount" select="count(Row[2]/Cell)" />

	<div class="filterForm">
	<form action="/refineQuery.do" method="post" name="filter_form">
	Filter: highlight values greater than <input type="text" name="filter_value" size="4" />
	<input type="submit" name="filter_submit" value="Filter" />
	<input type="hidden" name="action" value="filter" />
	</form>
	</div>
	<div class="rowCount">
	  <a href="javascript: spawn('help.jsp?sect={$helpLink}', 350, 500);"><img align="right" src="images/helpIcon.jpg" border="0" onmouseover="return overlib('Click here for additional information about this report.', CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);" onmouseout="return nd();" /></a>
	  <br clear="all" /><xsl:value-of select="count(Row[@name='dataRow'])" /> records returned. 
	  <xsl:if test="/Report[@reportType != 'Gene Expression Disease'] and /Report[@reportType != 'Clinical']" >
	  <xsl:value-of select="count(Row[@name='sampleRow']/Cell)-2" /> samples returned.
	  </xsl:if>
	  <a href="#" onclick="javascript:return false;">[Download this report for Excel]</a> | 
	  <a href="javascript:window.close()">[Close Window]</a> | 
	  <a href="javascript:void(window.print())">[Print Report]</a> | 
	  <a href="#queryInfo">[Query Info]</a>
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
						<td colspan="{$colspan}" class="{$currentGroup}"><xsl:value-of select="Data" /></td>
					</xsl:otherwise>
			</xsl:choose>
		    </xsl:for-each>
		    </tr>
		</xsl:for-each>
		<xsl:for-each select="Row[@name='sampleRow']">
			<tr class="sampleRow">
		  	<xsl:for-each select="Cell">
			  <xsl:variable name="currentGroup" select="@group" />
			  <xsl:variable name="sample" select="Data" />
		      <td class="{$currentGroup}"><a href="#?s={$sample}"><xsl:value-of select="Data" /></a></td>
		    </xsl:for-each>
		    </tr>
		</xsl:for-each>
		<!-- get each data row only -->
		<xsl:for-each select="Row[@name='dataRow']">
		
					<tr>
		  				<xsl:for-each select="Cell">
		  	  			<xsl:variable name="class" select="@group" />
		      			<td class="{$class}"><xsl:value-of select="Data" disable-output-escaping="yes" /></td>
		    			</xsl:for-each>
		    		</tr>

				<xsl:if test="/Report[@reportType != 'Clinical'] and ./Cell[1]/Data[1]/text() != following::Cell[1]/Data[1]/text()">
					<tr>
		      			<td colspan="{$colCount}" class="geneSpacerStyle">--</td>
		    		</tr>
				</xsl:if>

		</xsl:for-each>
  	</table>
  </xsl:for-each>
 <script language="javascript">
	<![CDATA[ hideLoadingMessage();]]>
</script>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>