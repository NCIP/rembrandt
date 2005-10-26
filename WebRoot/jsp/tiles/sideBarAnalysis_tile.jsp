<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%> 

<div width="100%">

	<logic:notPresent name="classComparisonForm">
	<h3>Current Filter Settings</h3>
	settings to be placed here
	</logic:notPresent>
	
</div>
