<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*, java.lang.*, java.io.*, gov.nih.nci.caintegrator.util.CaIntegratorConstants" %>

<html>
<body onload="document.getElementById('qsForm').submit();">

<s:form action="clinical2KmSearch" method="post" namespace="/" id="qsForm" theme="simple">
<input type="hidden" name="quickSearchForm.plot" value="<%=CaIntegratorConstants.SAMPLE_KMPLOT%>" />
<input type="hidden" name="quickSearchForm.groupName" value="SamplesFromClinicalReport"/>
<input type="hidden" name="quickSearchForm.groupNameCompare" value=""/>
</s:form>
</body>
</html>