<div class="title">Fold Change</div>
<input type="radio" class="radio" name="regulationStatus" value="up">Up-regulation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!--
<input type="radio" class="radio" name="fold" value="yes" checked selected>
<select>
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;
<input type="radio" class="radio" name="fold" value="no">&nbsp;
-->
<input type="text" value="other" name="foldChangeValueUp"><Br>
<input type="radio" class="radio" name="regulationStatus" value="down">Down-regulation&nbsp;
<!--
<input type="radio" class="radio" name="fold2" value="yes" checked selected>
<select>
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;<input type="radio" class="radio" name="fold2" value="no">&nbsp;
-->
<input type="text" value="other" name="foldChangeValueDown"><Br>
<input type="radio" class="radio" name="regulationStatus" value="updown">Up OR Down&nbsp;
<blockquote>
Up-regulation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!--
<input type="radio" class="radio" name="fold3" value="yes" checked selected>
<select>
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;<input type="radio" class="radio" name="fold3" value="no">&nbsp;
-->
<input type="text" value="other" name="foldChangeValueUDUp">
&nbsp;
<Br>
Down-regulation
<!--
<input type="radio" class="radio" name="fold3" value="yes" checked selected>

<select>
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;<input type="radio" class="radio" name="fold3" value="no">&nbsp;
-->
<input type="text" value="other" name="foldChangeValueUDDown">
&nbsp;
</blockquote>
<input type="radio" class="radio" name="regulationStatus" onclick="javascript:document.forms[0].f4.disabled=(!(document.forms[0].f4.disabled));document.forms[0].f4_2.disabled=(!(document.forms[0].f4_2.disabled));" value="unchange">
Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!--
<input type="radio" class="radio" name="fold4" value="yes" checked selected>
<select name="f4" disabled="true">
	<option>&nbsp;</option>
	<option>0.5x</option>
	<option>1.5x</option>
	<option>2x</option>
	<option>3x</option>
	<option>4x</option>
</select>&nbsp;-or-&nbsp;
<input type="radio" class="radio" name="fold4" value="no">&nbsp;
-->
<input type="text" disabled="true" name="foldChangeValueUnchangeFrom" value="other">&nbsp;-to-&nbsp;
<input type="text" disabled="true" name="foldChangeValueUnchangeTo" value="other">
