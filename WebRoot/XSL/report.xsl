<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  	<head>
	<title>My Report</title>
	
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
		
		
	</style>
	</head>
  <body>
  <xsl:for-each select="Report">
    <h2><xsl:value-of select="@reportType" /></h2> 

    <table border="1" cellpadding="0" cellspacing="0">
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
		      <td class="{$class}"><xsl:value-of select="Data" /></td>
		    </xsl:for-each>
		    </tr>
		</xsl:for-each>
  	</table>
  </xsl:for-each>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>