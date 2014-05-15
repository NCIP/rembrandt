<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" omit-xml-declaration="yes" /> 
<xsl:template match="/">
<xsl:for-each select="Report">
<span>
	 <xsl:if test="@reportType != 'Copy Number' and @reportType != 'Clinical'" >
 
		<xsl:value-of select="' , '"/>
		<xsl:for-each select="Row[(@name='dataRow')]">
	  		<xsl:value-of select="Cell[1]/Data" />
	  		<xsl:if test="position() != last()">
				<xsl:text>&#x9;</xsl:text>
			</xsl:if>
			<xsl:if test="position() = last()">
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
  		</xsl:for-each>
  		
  		<xsl:value-of select="' , '"/>
  		<xsl:for-each select="Row[(@name='dataRow')]">
	  		<xsl:value-of select="Cell[2]/Data" />
	  		<xsl:if test="position() != last()">
				<xsl:text>&#x9;</xsl:text>
			</xsl:if>
			<xsl:if test="position() = last()">
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
  		</xsl:for-each>
		
		<xsl:for-each select="Row[@name='sampleRow']">
			<xsl:for-each select="Cell">
			  	<xsl:if test="Data != ' '">
			  		<xsl:variable name="pos" select="position()" />
			  		<xsl:value-of select="Data" />
			  		<xsl:text>&#x9;</xsl:text>
			  		<xsl:for-each select="//Row[(@name='dataRow')]/Cell[position() = $pos]">
				  		<xsl:value-of select="Data" />
				  		<xsl:if test="position() != last()">
							<xsl:text>&#x9;</xsl:text>
						</xsl:if>
			  		</xsl:for-each>
					<xsl:text>&#10;</xsl:text>
				</xsl:if>
		    </xsl:for-each>
		</xsl:for-each>
	</xsl:if>	
	<xsl:if test="@reportType = 'Copy Number' or @reportType = 'Clinical'" >
 		<xsl:for-each select="Row[@name='headerRow']">
			<xsl:for-each select="Cell[@group = 'header']">
				<xsl:value-of select="Data"/>
				<xsl:if test="position() != last()">
					<xsl:text>&#x9;</xsl:text>
				</xsl:if>
				<xsl:if test="position() = last()">
					<xsl:text>&#10;</xsl:text>
				</xsl:if>	
			</xsl:for-each>
		</xsl:for-each>
	
		<xsl:for-each select="Row[@name='sampleRow']">
			<xsl:for-each select="Cell">
			  <xsl:choose>
			  	<xsl:when test="Data = ' '"> 
			  		<xsl:if test="position() != last()">
						<xsl:text>&#x9;</xsl:text>
					</xsl:if>
					<xsl:if test="position() = last()">
						<xsl:text>&#10;</xsl:text>
					</xsl:if>
				</xsl:when>
			  	<xsl:otherwise>
			  		<xsl:value-of select="Data" />
			  		<xsl:if test="position() != last()">
						<xsl:text>&#x9;</xsl:text>
					</xsl:if>
					<xsl:if test="position() = last()">
						<xsl:text>&#10;</xsl:text>
					</xsl:if>
				</xsl:otherwise>
		      </xsl:choose>
		    </xsl:for-each>
		</xsl:for-each>
		<xsl:for-each select="Row[(@name='dataRow')] ">
		
			<xsl:for-each select="Cell[@group = 'header']">
				<xsl:value-of select="Data" />
				<xsl:text>&#x9;</xsl:text>
				<xsl:if test="position() = last()">
					<xsl:text>&#x9;</xsl:text>
					<xsl:text>&#x9;</xsl:text>
					<xsl:text>&#x9;</xsl:text>
					<xsl:text>&#x9;</xsl:text>
				</xsl:if>
			</xsl:for-each>
		
			<xsl:for-each select="Cell[@group != 'header']">
				<xsl:value-of select="Data" />
				<xsl:if test="position() != last()">
					<xsl:text>&#x9;</xsl:text>
				</xsl:if>
				<xsl:if test="position() = last()">
					<xsl:text>&#10;</xsl:text>
				</xsl:if>
				
			</xsl:for-each>
		</xsl:for-each>
		
	</xsl:if>
</span>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>