<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
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
<html:textarea styleId="pathways" property="pathways" rows="5" cols="40" readonly="true">
			</html:textarea><label for="pathways">&nbsp;</label>
			<html:errors property="pathways"/>

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



