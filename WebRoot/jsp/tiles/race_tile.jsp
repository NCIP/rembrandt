<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Race

</legend>
<%
	String act = request.getParameter("act");
%>
	
<input type="checkbox" name="caucasion" class="radio" >Caucasion 
<input type="checkbox" name="africanAmerican" class="radio" >African American 
<input type="checkbox" name="latino" class="radio"  >Latino 
<input type="checkbox" name="asianAmerican" class="radio"  >Asian American 
<input type="checkbox" name="nativeAmerican" class="radio"  >Native American 
&nbsp;&nbsp;
	

</ fieldset>