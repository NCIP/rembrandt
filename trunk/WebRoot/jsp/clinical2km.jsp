<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, gov.nih.nci.caintegrator.util.CaIntegratorConstants" %>

<html>
<body onload="document.getElementById('qsForm').submit();">
<html:form action="/quickSearch.do?method=quickSearch" styleId="qsForm">  
<input type="hidden" name="plot" value="<%=CaIntegratorConstants.SAMPLE_KMPLOT%>" />
<input type="hidden" name="groupName" value="SamplesFromClinicalReport"/>
<input type="hidden" name="groupNameCompare" value=""/>
</html:form>
</body>
</html>