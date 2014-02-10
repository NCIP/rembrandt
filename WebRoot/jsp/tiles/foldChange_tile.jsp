<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Fold_tooltip";

%>
<fieldset class="gray">
<legend class="red">Fold Change
<!-- <app:help help="Specify the threshold of differential regulation."/>-->
<app:cshelp topic="<%=act%>" text="[?]"/>   
</legend>


	
<br />
<s:fielderror fieldName="regulationStatusAllGenes" />
<br />

<input type="radio" name="form.regulationStatus" class="radio" id="regulationStatus1" value="up" />
<label for="regulationStatus1">Up-regulation</label> &ge;
				
<s:textfield id="foldChangeValueUp" name="form.foldChangeValueUp" size="3" 
	onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].regulationStatus[0]);" />
<label for="foldChangeValueUp">&nbsp;fold(s)</label></br>				

<label for="regulationStatus2">Down Regulation</label> &ge;
<input type="radio" name="form.regulationStatus" class="radio" id="regulationStatus2" value="down" />

<s:textfield id="foldChangeValueDown" name="form.foldChangeValueDown" size="3" 
	onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].regulationStatus[1]);" />
	<label for="foldChangeValueDown">&nbsp;fold(s)</label></br>

<input type="radio" name="form.regulationStatus" class="radio" id="regulationStatus3" value="updown" />
<label for="regulationStatus3">Up or Down&nbsp;</label>

<blockquote>
Up-regulation &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &ge;
 <s:textfield id="foldChangeValueUDUp" name="form.foldChangeValueUDUp" size="3" 
 	onfocus="javascript:radioFold(this);" />
 	<label for="foldChangeValueUDUp">&nbsp;fold(s)</label>
&nbsp;
<Br>
Down-regulation &nbsp;&nbsp; &ge;
<s:textfield id="foldChangeValueUDDown" name="form.foldChangeValueUDDown" size="3" 
	onfocus="javascript:radioFold(this);" />
	<label for="foldChangeValueUDDown">&nbsp;fold(s)</label>
&nbsp;
</blockquote>

<input type="radio" name="form.regulationStatus" class="radio" id="regulationStatus4" value="unchange" />
<label for="regulationStatus4">Unchanged&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				
<s:textfield id="foldChangeValueUnchangeFrom" name="form.foldChangeValueUnchangeFrom" size="3" 
	onfocus="javascript:radioFold(this);" />
	<label for="foldChangeValueUnchangeFrom">-to-</label>
				
<s:textfield id="foldChangeValueUnchangeTo" name="form.foldChangeValueUnchangeTo" size="3" onfocus="javascript:radioFold(this);" />
<label for="foldChangeValueUnchangeTo">&nbsp;fold(s)</label>

<s:fielderror fieldName="foldChangeValueUnchangeFrom" />
<s:fielderror fieldName="foldChangeValueUnchangeTo" />
<s:fielderror fieldName="regulationStatus" />
<s:fielderror fieldName="foldChangeValueUp" />
<s:fielderror fieldName="foldChangeValueDown" />
<s:fielderror fieldName="foldChangeValueUDUp" />
<s:fielderror fieldName="foldChangeValueUDDown" />
</fieldset>

