<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">
<legend class="red">Pathways</legend>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" method="get"> -->
<Br>
<html:textarea property="pathways" rows="5" cols="40" disabled="true">
			</html:textarea>
			<html:errors property="pathways"/>

&nbsp;&nbsp;

<!--- <input class="sbutton" type="button" style="width:150px" value="Pathway Browser..." disabled="true"> --->
<script language="javascript">

 function browseData(){
     window.open( 'jsp/browsePathway.jsp', 'page2', 'status,resizable,dependent,scrollbars,width=700,height=500,screenX=100,screenY=100' );
   }
</script>

 <a href="javascript:browseData()">	   
	 <img src="images/btnBrowse.gif" border="0"/>
 </a>

<!-- </html:form> -->

