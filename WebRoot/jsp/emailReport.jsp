<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<p align="left"><bean:message key="label.email.results"/></p>
<p align="left"><bean:message key="label.email.message"/></p>
<html:form action="emailReport.do">
	<p align="left">
		<bean:message key="label.email"/>
		<html:text property="email" size="80"/>
	</p>
	<p align="left">
		<span class="er"><html:errors property="email"/></span>
	</p>
	<p align="left">
		<bean:message key="label.email.notify"/>
	</p>
	<input type="button" onclick="self.close()" class="btn" value="Cancel"/>
	<!-- <html:submit onclick="self.close()" styleClass="btn"/> -->
	<html:submit styleId="submittalButton" styleClass="subButton" property="method" onclick="self.close()">
		<bean:message key="buttons_tile.submittalButton" />
	</html:submit>
</html:form>
