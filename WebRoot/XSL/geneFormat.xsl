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
			
			
			
			



		</body>
		</html>
	</xsl:template>
	<xsl:template name="request" match="/xlink:httpQuery/queryRequest" mode="req">
		<!--  -#003333 #99C68E #FDEEF4 -->
		<font color="#000033" size="3">
			<xsl:for-each select="/xlink:httpQuery/queryRequest">
				
			
				<xsl:choose>
					<xsl:when test="$recordCounter > 0">					
							Your search returned <b> <xsl:value-of select="$recordCounter"/> </b>        genes.						
					</xsl:when>
					<xsl:otherwise>
						<b>
							<u>Results:  zero records found</u>
						</b>
					</xsl:otherwise>
				</xsl:choose>
				<br/>
				
			</xsl:for-each>
		</font>
	</xsl:template>
	<xsl:template name="response" match="/xlink:httpQuery/queryResponse/class" mode="res">
	
		<table border="2" style="table-layout:fixed" cellpadding="0" cellspacing="0" bgcolor="#FAF8CC">
<!--
<table class="dataTable" border="1">
-->

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
					<xsl:if test="@recordNumber = $start">					
						
						<tr bgcolor="#D5E0E9">
							<xsl:for-each select="field">	
							    <xsl:choose>       
									
									 <xsl:when test="position() = 2">									 
									   <th width="100">
										Gene Symbol		
									    </th>
									 </xsl:when>
									 
									 <xsl:when test="position() = 4">
									 
									   <th width="700">
										Gene Name		
									    </th>
									 </xsl:when>         
								
									
							        <xsl:otherwise>					
			 		                </xsl:otherwise>
		    	               </xsl:choose>		
						 </xsl:for-each>
						</tr>
					</xsl:if>
					<tr bgcolor="#FFFFFF" align="left" valign="top">
					
						<xsl:for-each select="field">						
								
								<xsl:choose>
								   <xsl:when test="position() = 2">
								       <td width="100" nowrap="off">
								         <xsl:value-of select="../field[position()=4]"/>								    
								       </td>										
								  </xsl:when>
								
								 <xsl:when test="position() = 4">
								       <td width="700" nowrap="off">
								         <xsl:value-of select="../field[position()=2]"/>								    
								       </td>										
								 </xsl:when>								
							
							    <xsl:otherwise>					
			 		            </xsl:otherwise>
		    	             </xsl:choose>	
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
