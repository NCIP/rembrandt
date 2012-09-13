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
			<br>
			<table cellpadding="3" border="2">
								<tr>
									<xsl:apply-templates select="/xlink:httpQuery/queryResponse/previous" mode="previous"/>
									<xsl:apply-templates select="/xlink:httpQuery/queryResponse/next" mode="next"/>
									<xsl:apply-templates select="/xlink:httpQuery/queryResponse/pages" mode="page"/>
								</tr>
									
			
			</table>
			
		 
			</br>
			
			<xsl:apply-templates select="/xlink:httpQuery/queryResponse/class" mode="res"/>
			<br>
			
				<table cellpadding="3" border="2">
					<tr>
						<xsl:apply-templates select="/xlink:httpQuery/queryResponse/previous" mode="previous"/>
						<xsl:apply-templates select="/xlink:httpQuery/queryResponse/next" mode="next"/>
						<xsl:apply-templates select="/xlink:httpQuery/queryResponse/pages" mode="page"/>
					</tr>
				</table>
				
			</br>



		</body>
		</html>
	</xsl:template>
	<xsl:template name="request" match="/xlink:httpQuery/queryRequest" mode="req">
		
		<!--  -#003333 #99C68E #FDEEF4 -->
		<font color="#000033" size="3">
			<xsl:for-each select="/xlink:httpQuery/queryRequest">
				
			<br>
				<xsl:choose>
					<xsl:when test="$recordCounter > 0">
					
							Your search returned <b> <xsl:value-of select="$recordCounter"/> </b>  pathways
						
						 (<xsl:value-of select="$start"/> - <xsl:value-of select="$end"/> records).
					</xsl:when>
					<xsl:otherwise>
						<b>
							<u>Results:  zero records found</u>
						</b>
					</xsl:otherwise>
				</xsl:choose>
			</br>
				
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
							    <xsl:if test="@name = 'bigid'">    		  							    
								       	 <th width="40">
										No
									 </th>									
									</xsl:if>
							
							                 
									<xsl:if test="@name = 'name'">							 
									   
		  							    <th width="500">
										Pathway Name	
									    </th>
									   
									</xsl:if>
									
									<xsl:if test="@name = 'displayValue'">
									    <th width="200">
										Pathway Title
									    </th>
									</xsl:if>
							</xsl:for-each>
						</tr>
					</xsl:if>
					<tr bgcolor="#FFFFFF" align="left" valign="top">

 				        
						<xsl:for-each select="field">				
	
	 				      <xsl:variable name="pathId" select="../field[@name='bigid']"/>	
	 				      <xsl:variable name="pathName" select="substring(../field[@name='name'],3)" />
	 							 
	 							<xsl:if test ="@name = 'bigid'" >
								  
								   <td width="40" nowrap="off">
								   	<xsl:value-of select="$counter "/>
								   </td>
								</xsl:if>
								
								
								<xsl:if test ="@name = 'name'" >
									<td width="500" nowrap="off">							   
									 
								      <input type="checkbox" value="{$pathName}" name="pathwayName"/>
									   <a href="geneResults.jsp?id={$pathId}&amp;name={$pathName}" target="_blank">	
									     <xsl:value-of select="substring(.,3)"/>							  						
									   </a>
									
									   
									</td>
								</xsl:if>
								
								<xsl:if test ="@name = 'displayValue'" >
									<td width="200" nowrap="off">
									<xsl:value-of select="."/>
									</td>
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
	<xsl:template name="previous" match="/xlink:httpQuery/queryResponse/previous" mode="previous">
		<xsl:for-each select="/xlink:httpQuery/queryResponse/previous">
			<td bgcolor="#E0FFFF">
				<font color="#25587E">
					<a href="browsePathway.jsp?url=previous"> Previous </a>
					<!-- 
					<a href="{@xlink:href}">
						<xsl:value-of select="."/>
					</a>
					-->
				</font>
			</td>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="next" match="/xlink:httpQuery/queryResponse/next" mode="next">
		<xsl:for-each select="/xlink:httpQuery/queryResponse/next">
			<td bgcolor="#E0FFFF">
				<font color="#25587E">
					 <a href="browsePathway.jsp?url=next"> Next </a>
					<!-- 
					<a href="{@xlink:href}">
						<xsl:value-of select="."/>
					</a>
					-->
				</font>
			</td>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="page" match="/xlink:httpQuery/queryResponse/pages" mode="page">
		<xsl:choose>
			<xsl:when test="@count > 1">
				<xsl:for-each select="/xlink:httpQuery/queryResponse/pages/page">
					<td bgcolor="#E0FFFF">
						<font color="#25587E">						
						<xsl:choose>
		                   
		                     <xsl:when test="@xlink:href = 'http://cabioapi-qa.nci.nih.gov/cabio42/GetXML?query=Pathway&amp;Taxon[@abbreviation=Hs]&amp;pageNumber=1&amp;resultCounter=1000&amp;startIndex=0'">  
								 <a href="browsePathway.jsp?url=1">
								    <xsl:value-of select="."/>
							     </a>
		                    </xsl:when>
		
		                     <xsl:otherwise>
			                     <a href="browsePathway.jsp?url=2">
								     <xsl:value-of select="."/>
							    </a>
		                     </xsl:otherwise>
	                    </xsl:choose>
						
						</font>
					</td>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
