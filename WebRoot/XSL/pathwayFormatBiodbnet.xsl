<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xlink="http://www.w3.org/1999/xlink" exclude-result-prefixes="xlink" version="2.0">
	<xsl:output method="html"/>
	<xsl:variable name="role">simple</xsl:variable>
	<xsl:variable name="recordCount" select="count(/response/item)"/>
	<xsl:variable name="pageSize" select="200" />
	<xsl:param name="page" />	
	
	<xsl:template match="/">
	
	
	 <xsl:variable name="start">
      <xsl:choose>
        <xsl:when test="$page = ''">
          
          <xsl:value-of select="0"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$page * $pageSize"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    
    <xsl:variable name="end">
    <xsl:choose>
        <xsl:when test="$recordCount &lt; ($start + $pageSize)">
          
          <xsl:value-of select="$recordCount"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$start + $pageSize"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>     
     
     <xsl:variable name="currPage">
     	<xsl:choose>
        <xsl:when test="$page = ''">
          
          <xsl:value-of select="0"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$page"/>
        </xsl:otherwise>
      </xsl:choose>
     </xsl:variable>
     
     
	
	<html>
		<head>  
		   <link rel="stylesheet" type="text/css" href="/caintegrator/styleSheet.css"/>		  
		</head>  

		<body>
		
			<font color="#737CA1" size="4" />
			<xsl:call-template name="topPanel">
					<xsl:with-param name="start" select="$start"/>
					<xsl:with-param name="end" select="$end"/>
					<xsl:with-param name="count" select="$recordCount"/>
			</xsl:call-template>
			
			<br>
			<xsl:call-template name="pageLink">
				<xsl:with-param name="totalRecords" select="$recordCount"/>
				<xsl:with-param name="pageSize" select="$pageSize"/>
				<xsl:with-param name="currPage" select="$currPage"/>
			</xsl:call-template>
				
			
		 
			</br>
			
			<xsl:apply-templates name="response" select="/response" mode="res">
				<xsl:with-param name="start" select="$start"/>
					<xsl:with-param name="end" select="$end"/>
					</xsl:apply-templates>
					
			<br>
			
				<table cellpadding="3" border="2">
					<tr>
						
					</tr>
				</table>
				
			</br>

		</body>
		</html>
	</xsl:template>
	
	<xsl:template name="topPanel">
		<xsl:param name="start"/>
		<xsl:param name="end"/>
		<xsl:param name="count"/>
		
		<font color="#000033" size="3">
			<br>
				<xsl:choose>
					<xsl:when test="$recordCount > 0">
					Your search returned <b> <xsl:value-of select="$recordCount"/> </b>  pathways
						(<xsl:value-of select="$start + 1"/> - <xsl:value-of select="$end"/> records).
					</xsl:when>
					<xsl:otherwise>
						<b><u>Results:  zero records found</u>
						</b>
					</xsl:otherwise>
				</xsl:choose>
			</br>
		
		</font>
	</xsl:template>
	
	<xsl:template name="response" match="/response" mode="res">
		<xsl:param name="start"/>
		<xsl:param name="end"/>
		
		<table border="2" style="table-layout:fixed" cellpadding="0" cellspacing="0" bgcolor="#FAF8CC">

			<tbody>
			
			<tr bgcolor="#D5E0E9">
				<th width="40">No</th>	
				<th width="200">Pathway Title</th>								
				<th width="500">Pathway Name</th>
			</tr>
			<xsl:for-each select="item">
			
			<xsl:variable name="pos" select="position()"/>
			<xsl:if test="$pos &gt; $start and $pos &lt;= $end">
				
				<tr bgcolor="#FFFFFF" align="left" valign="top">
				<td>
					<xsl:value-of select="$pos"/>
					
				</td>
				
				<xsl:variable name="dbname" select="./Source_Database"/>
				<xsl:variable name="pathname" select="./Name"/>
				<xsl:variable name="displayname" select="./Title"/>
    			
				<xsl:variable name="pathnameEncoded">
     				<xsl:call-template name="replace3Chars">
						<xsl:with-param name="source" select="$pathname"/>
					</xsl:call-template>
    			</xsl:variable>
    			
    			<xsl:variable name="displaynameEncoded">
     				<xsl:call-template name="replace3Chars">
						<xsl:with-param name="source" select="$displayname"/>
					</xsl:call-template>
    			</xsl:variable>
				
				<td width="200" nowrap="off">
					<xsl:value-of select="$displayname"/>
				</td>
				<td width="500" nowrap="off">
			
					<input type="checkbox" value="{$pathname}" name="pathwayName"/>
					
						<a href="geneResults.jsp?db={$dbname}&amp;name={$pathnameEncoded}&amp;displayname={$displaynameEncoded}" target="_blank">	
						<xsl:value-of select="$pathname" />							  						
						</a>
				</td>
			
			</tr>
			</xsl:if>
			</xsl:for-each>
			
			</tbody>
		</table>
	
	</xsl:template>
	
	<xsl:template name="pageLink">
		<xsl:param name="totalRecords"/>
		<xsl:param name="pageSize"/>
		<xsl:param name="currPage"/>
		
		<xsl:variable name="numPage" select="ceiling($totalRecords div $pageSize)"/>		
		
		<table cellpadding="3" border="2">
					<tr>
					<td bgcolor="#E0FFFF">
						<font color="#25587E">
						<xsl:choose>
    						<xsl:when test="$currPage &gt; 0">
								<a href="browsePathway.jsp?page={$currPage - 1}">Previous </a>
    						</xsl:when>
    					<xsl:otherwise>
							Previous
    					</xsl:otherwise>
						</xsl:choose>
					
						</font>
					</td>
					
					<td bgcolor="#E0FFFF">
						<font color="#25587E">

						<xsl:choose>
    						<xsl:when test="$currPage &lt; ($numPage -1)">
								<a href="browsePathway.jsp?page={$currPage +  1}">Next </a>
    						</xsl:when>
    					<xsl:otherwise>
							Next
    					</xsl:otherwise>
						</xsl:choose>					
						
						</font>
					</td>
						
					</tr>
		
			
		</table>
		
	</xsl:template>
	
	<xsl:template name="replace3Chars">
		<xsl:param name="source"/>
		
		<xsl:variable name="cleanPlus">
			<xsl:call-template name="string-replace-all">
					<xsl:with-param name="text" select="$source"/>
						<xsl:with-param name="replace" select="'+'" />
						<xsl:with-param name="by" select="'%2b'" />
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="cleanPound">
			<xsl:call-template name="string-replace-all">
					<xsl:with-param name="text" select="$cleanPlus"/>
						<xsl:with-param name="replace" select="'#'" />
						<xsl:with-param name="by" select="'%23'" />
			</xsl:call-template>
		</xsl:variable>
		
		
			<xsl:call-template name="string-replace-all">
					<xsl:with-param name="text" select="$cleanPound"/>
						<xsl:with-param name="replace" select="'&amp;'" />
						<xsl:with-param name="by" select="'%26'" />
			</xsl:call-template>
		
		
	</xsl:template>
	
	<xsl:template name="string-replace-all">
  		<xsl:param name="text" />
  		<xsl:param name="replace" />
  		<xsl:param name="by" />
  		<xsl:choose>
    			<xsl:when test="contains($text, $replace)">
      				<xsl:value-of select="substring-before($text,$replace)" />
      				<xsl:value-of select="$by" />
      				<xsl:call-template name="string-replace-all">
        				<xsl:with-param name="text"
       							 select="substring-after($text,$replace)" />
        				<xsl:with-param name="replace" select="$replace" />
        				<xsl:with-param name="by" select="$by" />
      				</xsl:call-template>
    			</xsl:when>
    			<xsl:otherwise>
    				<xsl:value-of select="$text" />
    			</xsl:otherwise>
  			</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>
	
