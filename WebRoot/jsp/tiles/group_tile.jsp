<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.struts.form.*,gov.nih.nci.rembrandt.web.bean.*" %> 


<fieldset class="gray">
<legend class="red">Step 1: Select Group<b class="req">*</b>

<logic:present name="principalComponentForm">
<app:help help="Select either Show all samples or Select samples by clicking the pertinent radio button. If you chose Select samples, choose one or more groups from the Existing Groups box and click the right-arrow button to move your selection(s) to the Selected Groups box." />
</legend>
<html:radio property="groupsOption" styleClass="radio" value="allSamples" onclick="onRadio(this,'allSamples');" />Show all samples<br /><br />

<html:radio property="groupsOption" styleId="variousSamplesRadio" styleClass="radio" value="variousSamples" onclick="onRadio(this,'variousSamples');" />Select samples<br />
<table align="center" border="0">
    <tr style="vertical-align:top">
      <td>Existing Groups
        <br/>
        <html:select styleId="nonselectedGroups" size="5" multiple="true" style="width:200px" property="existingGroups">
           <html:optionsCollection property="existingGroupsList"/>
		</html:select>
      </td>
      <td style="vertical-align:middle">
        <input id="button1" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'))" value="<<" type="button" disabled="true" /><br />
        <input id="button2" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'))" value=">>" type="button" disabled="true" />
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
<app:help help="Choose two groups from the Existing Groups box and click the right-arrow button to move your selection(s) to the Selected Groups box." />
</legend>
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
