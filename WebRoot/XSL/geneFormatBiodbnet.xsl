<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xlink="http://www.w3.org/1999/xlink" exclude-result-prefixes="xlink" version="2.0">
	<xsl:output method="html"/>
	<xsl:variable name="role">simple</xsl:variable>
	<xsl:param name="displayName"/>
	
	<xsl:template match="/">
	
	<html>
		<head>  
		   <link rel="stylesheet" type="text/css" href="/caintegrator/styleSheet.css"/>		  
		</head>  

		<body>
		
			<font color="#737CA1" size="4"></font>
			<xsl:apply-templates name="response" select="/response/item" mode="res"/>
			<br>				
			</br>

		</body>
		</html>
	</xsl:template>
	
	<xsl:template name="response" match="/response/item" mode="res">
	
	<table border="0" style="table-layout:fixed" cellpadding="0" cellspacing="0" bgcolor="#FAF8CC">

	<tbody>
			<table border="1" style="table-layout:fixed" cellpadding="0" cellspacing="0" bgcolor="#FAF8CC">
			<tr bgcolor="#D5E0E9">
				<th align ="left" width="800">
					Pathway Description for <xsl:value-of select="./InputValue"/> (<xsl:value-of select="$displayName"/>)
										
				</th>
			</tr>
			 <tr bgcolor="#FFFFFF" align="left" valign="top">
					
				<td width="800" nowrap="off">
				
				<xsl:choose>
        		<xsl:when test="./outputs/BiocartaPathwayDescription">
          			<xsl:value-of select="./outputs/BiocartaPathwayDescription"/>
        		</xsl:when>
        		<xsl:when test="./outputs/ReactomePathwayDescription">
          			<xsl:value-of select="./outputs/ReactomePathwayDescription"/>
        		</xsl:when>
        		<xsl:otherwise>
          			<xsl:apply-templates select="./outputs/NCIPIDPathwayTitle"/>
        		</xsl:otherwise>
      			</xsl:choose> 
				
				</td>
								
			</tr>		
					
			</table>
			<table>
			 <tr bgcolor="#FFFFFF" align="left" valign="top">
				<th align ="left" width="300">
						<br></br>
				</th>
			</tr>
			<tr bgcolor="#FFFFFF" align="left" valign="top">					
				<th align ="middle" width="800">
					<b>Gene Information for <xsl:value-of select="./InputValue"/></b>
				</th>
			</tr>
			<tr align="middle" valign="top">
				<font color="#000033" size="3">
			
				<xsl:variable name="geneCount" select="count(outputs/GeneInfo)"/>
				<td>
					<xsl:choose>
						<xsl:when test="$geneCount > 0">					
							Your search returned <b> <xsl:value-of select="$geneCount"/> </b> genes.						
						</xsl:when>
						<xsl:otherwise>
							<b>
								<u>Results:  zero records found</u>
							</b>
						</xsl:otherwise>
					</xsl:choose>
				</td>
				</font>
				</tr>
				<xsl:apply-templates name="geneInfo" select="/response/item/outputs" mode="res"/>
				</table>
				
			</tbody>
		</table>
	</xsl:template>
	
	<xsl:template name="geneInfo" match="/response/item/outputs" mode="res">
	
	<table border="2" style="table-layout:fixed" cellpadding="0" cellspacing="0" bgcolor="#FAF8CC">
		<tbody>
			<tr bgcolor="#D5E0E9">
				<th width="150">Gene Symbol</th>
				<th width="500">Gene Description</th>
				<th width="150">Gene Entrez ID</th>
			</tr>
			<xsl:for-each select="GeneInfo">
			
			<xsl:variable name="fullText" select="." />
			<xsl:variable name="geneId" select="substring-before($fullText,' ')"  />
			<xsl:variable name="geneSymbol" select="substring-before(substring-after($fullText, 'Gene Symbol: '), ']')"  />
			<xsl:variable name="geneDesc" select="substring-before(substring-after($fullText, '[Description: '), ']')"  />
			
			<tr bgcolor="#FFFFFF" align="left" valign="top">
				<td width="150" nowrap="off">
					<xsl:value-of select="$geneSymbol"/>								    
				</td>										
				<td width="500" nowrap="off">
					<xsl:value-of select="$geneDesc"/>								    
				</td>				 
				<td width="150" nowrap="off">
					<a href="http://www.ncbi.nlm.nih.gov/gene/?term={$geneId}" target="_blank"><xsl:value-of select="$geneId"/>	</a>							    
				</td>										
									
			</tr>
			</xsl:for-each>
			</tbody>
		</table>
	
	</xsl:template>
	
	</xsl:stylesheet>
	
