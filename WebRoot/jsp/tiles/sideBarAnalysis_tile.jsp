<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%> 

<jsp:useBean id="userPreferences" class="gov.nih.nci.rembrandt.web.bean.UserPreferencesBean" scope="session"/>
<div width="100%">

	<s:if test="classComparisonForm == null">
	<h3>Current Filter Settings</h3>
	<!--check which type of analysis it is, as to set the settings-->
		<s:if test="hierarchicalClusteringForm != null">
			<b>Variance:</b> <span id="varianceSetting"><jsp:getProperty name="userPreferences" property="hcVariancePercentile"/></span><br />
	    </s:if>
	    <s:if test="principalComponentForm != null">
			<b>Variance:</b> <span id="varianceSetting"><jsp:getProperty name="userPreferences" property="pcaVariancePercentile"/></span><br />
	    </s:if>
    <b>Gene Set Name:</b> <span id="geneSetSetting"><jsp:getProperty name="userPreferences" property="geneSetName"/></span><br />
    <b>Reporter Set Name:</b> <span id="reporterSetSetting"><jsp:getProperty name="userPreferences" property="reporterSetName"/></span><br />
	</s:if>
	
</div>

<tiles:insertTemplate template="/jsp/tiles/sideBar_tile.jsp" flush="false" />