<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Clone Location
<app:help help="Future implementation"/>
</legend>
<%
	String act = request.getParameter("act");
%>
	

5' UTR <input class="radio" type="radio" name="5p" selected checked disabled="true">Included&nbsp;&nbsp;
<input class="radio" type="radio" name="5p" disabled="true">Excluded<br>
3' UTR <input class="radio" type="radio" name="3p" selected checked disabled="true">Included&nbsp;&nbsp;
<input class="radio" type="radio" name="3p" disabled="true">Excluded<br>
</fieldset>
