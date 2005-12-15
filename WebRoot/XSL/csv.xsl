<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" omit-xml-declaration="yes" /> 
<xsl:template match="/">
<xsl:for-each select="Report">
<span>
	<xsl:for-each select="Row[@name='headerRow']">
			<xsl:for-each select="Cell[@group = 'header']">
				<xsl:value-of select="Data"/>
				<xsl:if test="position() != last()">
					<xsl:value-of select="', '"/>
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
					<xsl:value-of select="', '"/>
				</xsl:if>
				<xsl:if test="position() = last()">
					<xsl:text>&#10;</xsl:text>
				</xsl:if>
			</xsl:when>
		  	<xsl:otherwise>
		  		<xsl:value-of select="Data" />
		  		<xsl:if test="position() != last()">
					<xsl:value-of select="', '"/>
				</xsl:if>
				<xsl:if test="position() = last()">
					<xsl:text>&#10;</xsl:text>
				</xsl:if>
			</xsl:otherwise>
	      </xsl:choose>
	    </xsl:for-each>
	</xsl:for-each>
	<xsl:for-each select="Row[(@name='dataRow')] ">
		<xsl:for-each select="Cell">
			<xsl:value-of select="Data" />
			<xsl:if test="position() != last()">
				<xsl:value-of select="', '"/>
			</xsl:if>
			<xsl:if test="position() = last()">
				<xsl:text>&#10;</xsl:text>
			</xsl:if>
			</xsl:for-each>
	</xsl:for-each>
</span>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>