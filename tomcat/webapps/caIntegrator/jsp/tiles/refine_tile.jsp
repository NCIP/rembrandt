<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<!-- <html:form action="validatequery.do">-->


<div class="queryRows">
			<table border="1" width="100%" cellpadding="2" cellspacing="2" id="rosso">
				<tr><td colspan="6" class="message">Group Your Queries<BR><html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
				</td></tr>
				<tr>
					<td class="message" width="30%">Query Name</td>
					<td class="message" width="10%">(</td>
					<td class="message" width="30%">Query Description</td>
					<td class="message" width="10%">)</td>
					<td class="message" width="10%">and/or</td>
				</tr>
				<tr class="setQuery" id="test">
					<Td>
						<html:select property="queryName1" onchange="javascript:showName(this, document.getElementById('query1'));">
						    <html:optionsCollection property="queryNameColl" />
						</html:select><html:errors property="queryName1"/>
						
					</td>
					<td>
						<html:select property="leftParen1">
							<html:option value="">&nbsp;</html:option>
							<html:option value="(">&nbsp;(&nbsp;</html:option>
						</html:select>
					</td>
					<td id="query1">
						&nbsp;
					</td>
					<td>
						<html:select property="rightParen1">
							<html:option value="">&nbsp;</html:option>
							<html:option value=")">&nbsp;)&nbsp;</html:option>
						</html:select>
					</td>
					<Td>
						<html:select property="operatorType1">
							<html:option value="">&nbsp;</html:option>
							<html:option value="AND">and</html:option>
							<html:option value="OR">or</html:option>
							<html:option value="NOT">not</html:option>
							<html:option value="PRB">project from results</html:option>
						</html:select><html:errors property="operatorType1"/>
					</td>
				</tr>
				<tr class="setQuery">
					<Td>
						<html:select property="queryName2" onchange="javascript:showName(this, document.getElementById('query2'));">
						    <html:optionsCollection property="queryNameColl" />
						</html:select><html:errors property="queryName2"/>
					</td>
					<td>
						<html:select property="leftParen2">
							<html:option value="">&nbsp;</html:option>
							<html:option value="(">&nbsp;(&nbsp;</html:option>
						</html:select>
					</td>
					<tD id="query2">
						&nbsp;
					</td>
					<td>
						<html:select property="rightParen2">
							<html:option value="">&nbsp;</html:option>
							<html:option value=")">&nbsp;)&nbsp;</html:option>
						</html:select>
					</td>
					<Td>
						<html:select property="operatorType2">
							<html:option value="">&nbsp;</html:option>
							<html:option value="AND">and</html:option>
							<html:option value="OR">or</html:option>
							<html:option value="NOT">not</html:option>
							<html:option value="PRB">project from results</html:option>
						</html:select><html:errors property="operatorType2"/>
					</td>
				</tr>
				<tr class="setQuery">
					<Td>
						<html:select property="queryName3" onchange="javascript:showName(this, document.getElementById('query3'));">
						    <html:optionsCollection property="queryNameColl" />
						</html:select>
					</td>
					<td>
						<html:select property="leftParen3">
							<html:option value="">&nbsp;</html:option>
							<html:option value="(">&nbsp;(&nbsp;</html:option>
						</html:select>
					</td>
					<tD id="query3">
						&nbsp;
					</td>
					<td>
						<html:select property="rightParen3">
							<html:option value="">&nbsp;</html:option>
							<html:option value=")">&nbsp;)&nbsp;</html:option>
						</html:select>
					</td>
					<Td>&nbsp;
					</td>
				</tr>
			</table>
			<b class="message">[add more rows]</b>
		</div>
<!--Display buttons here -->		
		<div>
			<table width="100%" align="centeR" cellpadding="2" cellspacing="2">
				<tr>
					<Td align="center"><input type="reset" value="reset query" class="sbutton">
					&nbsp;&nbsp;<input type="submit" class="sbutton" value="validate query" onmouseover="return overlib('Based on your selections above, your query will appear in the box below.  Please verify its accuracy.  Click \'Reset\' to clear the query.', CAPTION, 'Help');" onmouseout="return nd();">
					&nbsp;&nbsp;<input type="button" class="sbutton" value="Save Query As...">
					</td>
				</tr>
				<tr>
					<td align="center">
						<html:textarea property="queryText" style="width:700px; height:75px;"></html:textarea>
					</td>
				</tr>
				<tr align="center">
					<td>
					<input type="button" class="xbutton" value="back" onclick="javascript:history.back();">&nbsp;&nbsp;
					<input type="button" class="xbutton" value="continue" onclick="javascript:location.href='compoundcheck.do';">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
<!-- </html:form> -->
</form>
		
		