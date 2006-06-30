<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xlink="http://www.w3.org/1999/xlink" exclude-result-prefixes="xlink" version="2.0">
	<xsl:output method="html"/>
	<xsl:variable name="role">simple</xsl:variable>
	<xsl:variable name="recordCounter" select="/xlink:httpQuery/queryResponse/recordCounter"/>
	<xsl:variable name="start" select="/xlink:httpQuery/queryResponse/start"/>
	<xsl:variable name="end" select="/xlink:httpQuery/queryResponse/end"/>
	<xsl:template match="/">
	
	<html>
		<head>  
		   <link rel="stylesheet" type="text/css" href="/caintegrator/styleSheet.css"/>		  
		</head>  

		<body>
	
	

		
			<font color="#737CA1" size="4">
			
			
			</font>
			<xsl:apply-templates select="/xlink:httpQuery/queryRequest" mode="req"/>
			<xsl:apply-templates select="/xlink:httpQuery/queryResponse/class" mode="res"/>
			<br>
			
			
				
			</br>



		</body>
		</html>
	</xsl:template>
	<xsl:template name="request" match="/xlink:httpQuery/queryRequest" mode="req">
		
	</xsl:template>
	<xsl:template name="response" match="/xlink:httpQuery/queryResponse/class" mode="res">
	
		<table border="0" style="table-layout:fixed" cellpadding="0" cellspacing="0" bgcolor="#FAF8CC">

			<tbody>
				<xsl:call-template name="cacore">
					<xsl:with-param name="counter" select="@recordNumber"/>
					<xsl:with-param name="rec" select="@recordNumber"/>
				</xsl:call-template>
			</tbody>
		</table>
		
		
	</xsl:template>
	
	
	
	
	<xsl:template name="cacore">
		<xsl:param name="counter"/>
		<xsl:param name="rec"/>	
		
		<xsl:choose>
			<xsl:when test="$counter = $rec">
				<xsl:if test="$counter = @recordNumber">
					<table border="1" style="table-layout:fixed" cellpadding="0" cellspacing="0" bgcolor="#FAF8CC">
				
					   <xsl:if test="@recordNumber = $start">									
						
						  <tr bgcolor="#D5E0E9">
							   <xsl:for-each select="field">										
									<xsl:if test="@name = 'description'">
									    <th align ="left" width="800">
										Pathway Description for "<xsl:value-of select="substring(../field[position()=4],3)"/>"
										<!--
										   <xsl:value-of select="@name = 'name"/></xls>
										 -->
									    </th>
									</xsl:if>
							   </xsl:for-each>
						 </tr>
					   </xsl:if>
					
					   <tr bgcolor="#FFFFFF" align="left" valign="top">
					
						   <xsl:for-each select="field">								
								<xsl:if test ="@name = 'description'" >
									<td width="800" nowrap="off">
									<xsl:value-of select="."/>
									</td>
								</xsl:if>
						  </xsl:for-each>
					   </tr>				
						
				  </table>
				  <tr bgcolor="#FFFFFF" align="left" valign="top">
						 <th align ="left" width="300">
							<br></br>
						 </th>
				  </tr>
				  <tr bgcolor="#FFFFFF" align="left" valign="top">					
					 <xsl:for-each select="field">								
							<xsl:if test="@name = 'description'">
								<th align ="middle" width="800">
								    <b>Gene Information for "<xsl:value-of select="substring(../field[position()=4],3)"/>"</b>									
								</th>
							</xsl:if>
					</xsl:for-each>
				</tr>
			</xsl:if>
			<xsl:choose>
					<xsl:when test="$counter > @recordNumber">
			         </xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="cacore">
							<xsl:with-param name="counter" select="$counter + 1"/>
							<xsl:with-param name="rec" select="following::node()/@recordNumber"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	
	
</xsl:stylesheet>
