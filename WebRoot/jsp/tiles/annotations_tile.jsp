<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<fieldset class="gray">
<legend class="red">
<label for="platSelect">
Step 4: Select Annotations
</label>
</legend>
<s:fielderror fieldName="selectedAnnotations" />Select 1 or More Annotations <br />
<br/>
<table align="center" border="0">
    <tr style="vertical-align:top">
      <td><label for="annotationList">Annotations</label>
        <br/>
		 <html:select styleId="annotationList" size="5" multiple="true" style="width:200px" property="annotations" ondblclick="move(document.getElementById('annotationList'),document.getElementById('selectedAnnotationList'));">
		    <html:optionsCollection property="annotationsList"/>
		</html:select>      
	  </td>
	  <td style="vertical-align:middle">
      		<input onclick="move($('selectedAnnotationList'),$('annotationList'));" value="&lt;&lt;" type="button"/><br />
			<input onclick="move($('annotationList'),$('selectedAnnotationList'));" value=">>" type="button"/>
	
       <!--
       <input id="button1" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('selectedGroups'),document.getElementById('nonselectedGroups'))" value="<<" type="button"  /><br />
        <input id="button2" onclick="preMove(document.getElementById('variousSamplesRadio').checked, document.getElementById('nonselectedGroups'),document.getElementById('selectedGroups'))" value=">>" type="button"  />
     
     -->
      </td>

      <td><label for="selectedAnnotationList">Selected Annotations</label>
        <br/>

        <html:select styleId="selectedAnnotationList" size="5" multiple="true" style="width:200px" property="selectedAnnotations" ondblclick="move(document.getElementById('selectedAnnotationList'),document.getElementById('annotationList'));">
        	
		</html:select>
      </td>
    </tr>
    
  </table> 
	
</fieldset>