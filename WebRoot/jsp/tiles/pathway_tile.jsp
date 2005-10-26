<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<fieldset class="gray">
<legend class="red">Pathways
<app:help help="Enter pathways name in this box by using <img src=images/btnBrowse2.gif> browse caBIO button and select the pathway of interest from the pop up window." />
</legend>
<%
	String act = request.getParameter("act");

%>
	
<Br>
<html:textarea property="pathways" rows="5" cols="40" readonly="true">
			</html:textarea>
			<html:errors property="pathways"/>

&nbsp;&nbsp;

<!--- <input class="sbutton" type="button" style="width:150px" value="Pathway Browser..." disabled="true"> --->
<script language="javascript">

 function browseData(){
     window.open( 'jsp/browsePathway.jsp', 'page2', 'status,resizable,dependent,scrollbars,width=765px,height=500px,screenX=100,screenY=100');
   }
</script>

 <a href="javascript:browseData()">	   
	 <img src="images/btnBrowse2.gif" border="0" />
 </a>
     <br />
    <input type="button" class="xbutton" onclick="javascript:resetVal(pathways)" value="clear text area" />



