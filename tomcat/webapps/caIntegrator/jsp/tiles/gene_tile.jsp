<% 
/*
Gene Tile -
used in: GeneExpression form, 

*/
%>
<div class="title">Gene
<a href="javascript:void(0);" onmouseover="return overlib('Paste a comma separated Gene list, or upload file using Browse button', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</div>
<br>
<input type="radio" class="radio" name="geneGroup" selected checked value="Specify">
<select name="geneType">
	<option>All Genes</option>
	<option>Name/Symbol</option>
	<option>Locus Link Id</option>
	<option>GenBank AccNo.</option>
	<!-- <option>Unigene</option> -->
	<!-- <option>Affy Probe Set Id</option> -->
</select>
&nbsp;
<input type="text" name="geneList">&nbsp;
<a href="javascript:void(0);" onmouseover="return overlib('Selected Criteria on this form applies to all genes specified in this list.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
			
&nbsp;-or-&nbsp;
<input type="radio" class="radio" name="geneGroup" value="Upload">
<input type="file" name="geneFile">
