<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>

<%
	String act = request.getParameter("act");
%>
<fieldset class="gray">
<legend class="red">Clone Id/Probe Set Id
<% if(act.equalsIgnoreCase("geneexpression"))	{ %>
<app:help help="Choose one type of Reporter identifiers either (IMAGE ID for Clones or Affymetrix Probeset ID) from the pick list. Then enter the corresponding comma delimited IDs to be searched in the text box. Another option to load a list of IDs is to upload a file using the Browse button, file must be of type *.txt with each entry in a new line. Upper limit for this option is 500 entries in the txt file." />
<% } else { %>
<app:help help="Future implementation"/>
<% } %>
</legend>
<!-- <b class="message">(Paste comma separated Clone Id list, or upload file using Browse button)</b> -->
<br />

	<!-- <html:form action="<%=act%>" method="get"> -->
	
	
	
	<html:select property="cloneList" disabled="false">
	<html:optionsCollection property="cloneTypeColl" />
	</html:select>
				
&nbsp;
<html:radio property="cloneId" value="list" styleClass="radio" onfocus="javascript:onRadio(this,0);" />
	<html:text property="cloneListSpecify" disabled="false" onfocus="javascript:radioFold(this);"  onblur="javascript:cRadio(this, document.forms[0].cloneId[0]);" value="" />
	&nbsp;-or-&nbsp;
<html:radio property="cloneId" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);" />
			<html:file property="cloneListFile" disabled="true"  onblur="javascript:cRadio(this, document.forms[0].cloneId[1]);" onfocus="javascript:document.forms[0].cloneId[1].checked = true;" />
			<!--<app:help help="Only files of type \"*.txt\" with each entry in a new line are accepted. Upper limit for this option is 500 entries in the txt file." /></br>-->
			<html:errors property="cloneId"/></br>
</fieldset>
<!-- </html:form> -->
