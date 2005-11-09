<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.*"%> 

<jsp:useBean id="userPreferences" class="gov.nih.nci.rembrandt.web.bean.UserPreferencesBean" scope="session"/>
<div width="100%">

	<logic:notPresent name="classComparisonForm">
	<h3>Current Filter Settings</h3>
	<b>Variance:</b> <span id="varianceSetting"><jsp:getProperty name="userPreferences" property="variancePercentile"/></span><br />
    <b>Gene Set Name:</b> <span id="geneSetSetting"><jsp:getProperty name="userPreferences" property="geneSetName"/></span><br />
    <b>Reporter Set Name:</b> <span id="reporterSetSetting"><jsp:getProperty name="userPreferences" property="reporterSetName"/></span><br />
	</logic:notPresent>
	
</div>
