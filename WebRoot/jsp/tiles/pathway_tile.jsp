<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act") + "_Pathway_tooltip";

%>
<fieldset class="gray">
<legend class="red">Pathways
<!--  <app:help help="Enter or browse for a pathway of interest." />  -->
<app:cshelp topic="<%=act%>" text="[?]"/>

</legend>

	
<Br>
<s:textarea id="pathways" name="geneExpressionForm.pathways" rows="5" cols="40" readonly="true">
			</s:textarea>
			<label for="pathways">&nbsp;</label>
<s:actionerror/>

&nbsp;&nbsp;

<!--- <input class="sbutton" type="button" style="width:150px" value="Pathway Browser..." disabled="true"> --->
<script language="javascript">

 function browseData(){ 	
     window.open( 'jsp/browsePathway.jsp', 'page2', 'status,resizable,scrollbars,width=1000px,height=500px,screenX=50,screenY=100');
   }
   
 function browseKegg(){ 	
     window.open( 'browseKeggPathway.do', 'page2', 'status,resizable,scrollbars,width=1000px,height=500px,screenX=50,screenY=100');
   }
</script>

 <a href="javascript:browseKegg()"><img alt="KEGG" src="images/kegg.gif" border="0" /></a>&nbsp;

 <a href="javascript:browseData()"><img alt="Browse BioDBNet" src="images/btnBiodbnet.gif" border="0" /></a>

     <br />
    <input type="button" class="xbutton" onclick="javascript:resetVal(pathways)" value="Clear Text Area" />



