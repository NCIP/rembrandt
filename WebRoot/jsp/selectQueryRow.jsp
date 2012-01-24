<tr class="setQuery">
	<td>
		<nested:select styleId="leftParen" property="leftParen">
			<html:option value="">&nbsp;</html:option>
			<html:option value="(">&nbsp;(&nbsp;</html:option>
		</nested:select>
	</td>
	<td>
		<nested:select styleId="queryName" property="queryName">
		    <option/>
		    <html:optionsCollection property="nonAllGenesQueries" label="queryName" value="queryName" />
		</nested:select>
	</td>
	<td>
		<nested:select styleId="rightParen" property="rightParen">
			<html:option value="">&nbsp;</html:option>
			<html:option value=")">&nbsp;)&nbsp;</html:option>
		</nested:select>
	</td>
	<Td>
		<nested:select styleId="operand" property="operand" onchange="operandChange()">
			<html:option value="">&nbsp;</html:option>
			<html:option value="AND">and</html:option>
			<html:option value="OR">or</html:option>
		</nested:select>
		<html:errors property="operand"/>
	</td>
</tr>