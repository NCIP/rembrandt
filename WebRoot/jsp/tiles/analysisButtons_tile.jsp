<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@page contentType="text/html"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<%
long randomness = System.currentTimeMillis(); //prevent image caching
%>
<script language="javascript">
document.forms[0].target = "_self";
</script>


<s:reset id="clearButton" cssClass="xbutton" value="Clear" theme="simple"/>&nbsp;&nbsp;
<s:submit type="input" cssClass="xbutton" name="method" value="Cancel" onclick="javascript:alertUser('analysisHome');" theme="simple"/>&nbsp;&nbsp;

<s:if test="hierarchicalClusteringForm == null">
	<s:if test="gpIntegrationForm != null">
		<s:if test="igvIntegrationForm == null">
			<s:submit id="submittalButton" cssClass="subButton" name="method" onclick="javascript: saveMe(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups')); return checkNull(document.forms[0].analysisResultName, 'false');" value="%{getText('buttons_tile.submittalButton')}" theme="simple">
			</s:submit>
		</s:if>
	</s:if>
	<s:if test="igvIntegrationForm != null">
		<s:submit id="submittalButton" cssClass="subButton" name="method" onclick="javascript: saveMe(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups')); javascript: saveMe(document.getElementById('selectedAnnotationList'),document.getElementById('annotationList')); return checkNull(document.forms[0].analysisResultName, 'false');" value="%{getText('buttons_tile.submittalButton')}" theme="simple">
		</s:submit>
	</s:if>
	<s:if test="gpIntegrationForm == null">
		<s:if test="igvIntegrationForm == null">
			<s:submit id="submittalButton" cssClass="subButton" name="method" onclick="javascript:saveMe(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'));return checkNull(document.forms[0].analysisResultName, 'true')" value="%{getText('buttons_tile.submittalButton')}" theme="simple">
			</s:submit>
		</s:if>
	</s:if>
</s:if>

<s:if test="hierarchicalClusteringForm != null">
<s:submit id="submittalButton" cssClass="subButton" name="method" onclick="javascript: document.forms[0].target='_self'; return checkNull(document.forms[0].analysisResultName, 'true')" value="%{getText('buttons_tile.submittalButton')}" theme="simple">
</s:submit>
</s:if>




