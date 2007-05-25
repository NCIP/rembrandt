<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%
	String act = request.getParameter("act");

%>
<fieldset class="gray">
<legend class="red">Pathways
<!--  <app:help help="Enter or browse for a pathway of interest." />  -->
<a href="javascript: Help.popHelp('<%=act%>_Pathway_tooltip');">[?]</a>    

</legend>

	
<Br>
<html:textarea property="pathways" rows="5" cols="40" readonly="true">
			</html:textarea>
			<html:errors property="pathways"/>

&nbsp;&nbsp;

<!--- <input class="sbutton" type="button" style="width:150px" value="Pathway Browser..." disabled="true"> --->
<script language="javascript">

 function browseData(){ 	
     window.open( 'jsp/browsePathway.jsp', 'page2', 'status,resizable,scrollbars,width=765px,height=500px,screenX=100,screenY=100');
   }
   
 function browseKegg(){ 	
     window.open( 'browseKeggPathway.do', 'page2', 'status,resizable,scrollbars,width=765px,height=500px,screenX=100,screenY=100');
   }
</script>

 <a href="javascript:browseKegg()"><img src="images/kegg.gif" border="0" /></a>&nbsp;

 <a href="javascript:browseData()"><img src="images/btnBrowse2.gif" border="0" /></a>

     <br />
    <input type="button" class="xbutton" onclick="javascript:resetVal(pathways)" value="clear text area" />



