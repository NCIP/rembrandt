<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts.form.*,gov.nih.nci.rembrandt.web.bean.*" %> 


<fieldset class="gray">
<legend class="red">Step 1: Select Group<b class="req">*</b><br />
</legend>
<logic:present name="principalComponentForm">
<html:radio property="groupsOption" styleClass="radio" value="allSamples" />Show all samples<br /><br />

<html:radio property="groupsOption" styleClass="radio" value="variousSamples" />Select samples<br />
<table align="center" border="0">
    <tr style="vertical-align:top">
      <td>Existing Groups
        <br/>
        <html:select styleId="nonselectedGroups" size="5" multiple="true" style="width:200px" property="existingGroups">
           <html:optionsCollection property="existingGroupsList"/>
		</html:select>
      </td>
      <td style="vertical-align:middle">
        <input onclick="move(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'))" value="<<" type="button"/><br />
        <input onclick="move(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'))" value=">>" type="button"/>
      </td>
      <td>Selected Groups
        <br/>

        <html:select styleId="selectedGroups" size="5" multiple="true" style="width:200px" property="selectedGroups">
        	
		</html:select>
      </td>
    </tr>
    
  </table>
</logic:present>


<logic:present name="classComparisonForm">
<a href="javascript:void(0);" onmouseover="return overlib('Select ONLY two groups for class comparison.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>
</legend><br>
	<em>choose 2 groups</em>
	<html:errors property="selectedGroups"/>
	<table align="center" border="0">
    <tr style="vertical-align:top">
      <td>Existing Groups
        <br/>
        <html:select styleId="nonselectedGroups" size="5" multiple="true" style="width:200px" property="existingGroups">
           <html:optionsCollection property="existingGroupsList"/>
		</html:select>
      </td>
      <td style="vertical-align:middle">
        <input onclick="move(document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'))" value="<<" type="button"/><br />
        <input onclick="move(document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'))" value=">>" type="button"/>
      </td>
      <td>Selected Groups
        <br/>

        <html:select styleId="selectedGroups" size="5" multiple="true" style="width:200px" property="selectedGroups">
        	
		</html:select>
      </td>
    </tr>
    
  </table>
</logic:present>

</fieldset>
