<div class="queryRows">
			<table border="0" width="100%" cellpadding="2" cellspacing="2" id="rosso">
				<tr><td colspan="6" class="message">Group Your Queries</td></tr>
				<tr>
					<td class="message" width="30%"">Query Name</td>
					<td class="message" width="10%">(</td>
					<Td class="message" width="10%">Not</td>
					<tD class="message" width="30%">Query Description</td>
					<td class="message" width="10%">)</td>
					<td class="message" width="10%">and/or</td>
				</tr>
				<tr class="setQuery" id="test">
					<Td>
						<select id="1" onchange="javascript:showName(this, document.getElementById('q1'));">
							<option>&nbsp;</option>
							<option>--- Gene Centric ---</option>
							<option value="Gene Centric: Fold change: 2x ; Gene: EGFR...">&nbsp;&nbsp;&nbsp;EGFR up-regulation</option>
							<option value="Gene Centric: Chromosome abberation status: Amplified  Gene: EGFR...">&nbsp;&nbsp;&nbsp;EGFR amplification</option>
							<option>&nbsp;</option>
							<option>--- Sample View ---</option>
							<option value="Sample Centric: Gene: * ;  Map Location: 7P11.2 ...">&nbsp;&nbsp;&nbsp;other genes in region</option>
							<option value="Sample Centric: Gene: * ;  Map Location: 7P11.2 ...">&nbsp;&nbsp;&nbsp;Sample Query</option>
						</select>
						
						&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:showQueryDetail('1');">Refine</a>&nbsp;<a href="#" onmouseover="return overlib('This feature allows you to further refine your query. The query details will appear in a new window.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a> 
					</td>
					<td>
						<select class="paren">
							<option>&nbsp;</option>
							<option>&nbsp;(&nbsp;</option>
						</select>
					</td>
					<Td>
						<input type="checkbox" class="radio" name="not">
					</td>
					<tD id="q1">
						&nbsp;
					</td>
					<td>
						<select class="paren">
							<option>&nbsp;</option>
							<option>&nbsp;)&nbsp;</option>
						</select>
					</td>
					<Td>
						<select>
							<option selected>and</option>
							<option>or</option>
							<option>project from results</option>
						</select>
					</td>
				</tr>
				<tr class="setQuery">
					<Td>
						<select id = "2" onchange="javascript:showName(this, document.getElementById('q2'));">
							<option>&nbsp;</option>
							<option>--- Gene Centric ---</option>
							<option value="Gene Centric: Fold change: 2x ; Gene: EGFR...">&nbsp;&nbsp;&nbsp;EGFR up-regulation</option>
							<option value="Gene Centric: Chromosome abberation status: Amplified  Gene: EGFR...">&nbsp;&nbsp;&nbsp;EGFR amplification</option>
							<option>&nbsp;</option>
							<option>--- Sample Centric ---</option>
							<option value="Sample Centric: Gene: * ;  Map Location: 7P11.2 ...">&nbsp;&nbsp;&nbsp;other genes in region</option>
							<option value="Sample Centric: Gene: * ;  Map Location: 7P11.2 ...">&nbsp;&nbsp;&nbsp;Sample Query</option>
						</select>
						
						&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:showQueryDetail('2');">Refine</a>&nbsp;<a href="#" onmouseover="return overlib('This feature allows you to further refine your query. The query details will appear in a new window.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
					</td>
					<td>
						<select class="paren">
							<option>&nbsp;</option>
							<option>&nbsp;(&nbsp;</option>
						</select>
					</td>
					<Td>
						<input type="checkbox" class="radio" name="not">
					</td>
					<tD id="q2">
						&nbsp;
					</td>
					<td>
						<select class="paren">
							<option>&nbsp;</option>
							<option>&nbsp;)&nbsp;</option>
						</select>
					</td>
					<Td>
						<select>
							<option selected>and</option>
							<option>or</option>
							<option>project from results</option>
						</select>
					</td>
				</tr>
				<tr class="setQuery">
					<Td>
						<select id ="3" onchange="javascript:showName(this, document.getElementById('q3'));">
							<option>&nbsp;</option>
							<option>--- Gene Centric ---</option>
							<option value="Gene Centric: Fold change: 2x ; Gene: EGFR...">&nbsp;&nbsp;&nbsp;EGFR up-regulation</option>
							<option value="Gene Centric: Chromosome abberation status: Amplified  Gene: EGFR...">&nbsp;&nbsp;&nbsp;EGFR amplification</option>
							<option>&nbsp;</option>
							<option>--- Sample Centric ---</option>
							<option value="Sample Centric: Gene: * ;  Map Location: 7P11.2 ...">&nbsp;&nbsp;&nbsp;other genes in region</option>
							<option value="Sample Centric: Gene: * ;  Map Location: 7P11.2 ...">&nbsp;&nbsp;&nbsp;Sample Query</option>
						</select>
						
						&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:showQueryDetail('3');">Refine</a>&nbsp;<a href="#" onmouseover="return overlib('This feature allows you to further refine your query. The query details will appear in a new window.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
					</td>
					<td>
						<select class="paren">
							<option>&nbsp;</option>
							<option>&nbsp;(&nbsp;</option>
						</select>
					</td>
					<Td>
						<input type="checkbox" class="radio" name="not">
					</td>
					<tD id="q3">
						&nbsp;
					</td>
					<td>
						<select class="paren">
							<option>&nbsp;</option>
							<option>&nbsp;)&nbsp;</option>
						</select>
					</td>
					<Td>
						<select>
							<option>N/A</option>
							<option>and</option>
							<option>or</option>
							<option>project from results</option>
						</select>
					</td>
				</tr>
			</table>
			<a href="#"><b class="message">[add more rows]</b></a>
		</div>