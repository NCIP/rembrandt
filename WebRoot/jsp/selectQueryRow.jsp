<tr class="setQuery">
	<td>
		<nested:select property="leftParen">
			<html:option value="">&nbsp;</html:option>
			<html:option value="(">&nbsp;(&nbsp;</html:option>
		</nested:select>
	</td>
	<td>
		<nested:select property="queryName">
		    <html:optionsCollection property="queryNameColl" />
		</nested:select>
	</td>
	<td>
		<nested:select property="rightParen">
			<html:option value="">&nbsp;</html:option>
			<html:option value=")">&nbsp;)&nbsp;</html:option>
		</nested:select>
	</td>
	<Td>
		<nested:select property="operand" onchange="operandChange()">
			<html:option value="">&nbsp;</html:option>
			<html:option value="AND">and</html:option>
			<html:option value="OR">or</html:option>
		</nested:select>
		<html:errors property="operand"/>
	</td>
</tr>