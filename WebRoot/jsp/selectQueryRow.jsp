<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<tr class="setQuery">
	<td>
		<s:select id="leftParen" name="refineQueryForm.leftParen" list="refineQueryForm.leftParenOptions" />
	</td>
	<td>
		<s:select id="queryName"name="refineQueryForm.queryName" 
			list="refineQueryForm.nonAllGenesQueries" listKey="queryName" listValue="queryName" />
	</td>
	<td>
		<s:select id="rightParen" name="refineQueryForm.rightParen" list="refineQueryForm.rightParenOptions" />
	</td>
	<Td>
		<s:select id="operand" name="refineQueryForm.operand" list="refineQueryForm.operands" onchange="operandChange()" />
			
		<s:fielderror fieldName="operand"/>
	</td>
</tr>