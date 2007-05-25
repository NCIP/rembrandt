<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
String act = request.getParameter("act");
%>
	
<fieldset class="gray">
<legend class="red">Allele Frequency
<a href="javascript: Help.popHelp('<%=act%>_Allele_tooltip');">[?]</a>    
</legend><br />
&nbsp;&nbsp;Population Type:

 <html:select property="alleleFrequency" disabled="true">
   <html:optionsCollection property="alleleTypes" />
    </html:select><br />
	

<html:errors property="alleleFrequency"/>
</fieldset>


