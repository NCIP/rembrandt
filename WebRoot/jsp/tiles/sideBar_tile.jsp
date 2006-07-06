<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*,
				 gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
				 gov.nih.nci.rembrandt.util.RembrandtConstants,
	 			 gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,
	 			 gov.nih.nci.rembrandt.web.factory.ApplicationFactory,
	 			 gov.nih.nci.caintegrator.application.lists.ListType" %> 

<!--  lists related -->
<script type='text/javascript' src='dwr/engine.js'></script>	 			 
<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
<script type='text/javascript' src='js/lib/common/SidebarHelper.js'/></script>
<div id="manageListLinkDiv" style="text-align:center; margin-top:20px;">
	<fieldset style="text-align:center">
		<a href="manageLists.do">Manage Lists</a>
	</fieldset>
</div>
<style>
	#sidebar div b {
		border-bottom: 1px solid #A90101;
	}
</style>
<!--  end lists related -->

<div width="100%">
<h3>Queries</h3>
<html:form action ="delete_Query.do">
<script type="text/javascript">
	var method;
	var queryKey; 
	function setMode(method, queryKey){   
		document.deleteQueryForm.method.value = method;
		document.deleteQueryForm.queryKey.value = queryKey;  
	}
</script>
				 
<%
RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
String  query = "";	
int j = 0;	
String queryKey = null;
	
String pageStr = (String)request.getSession().getAttribute("currentPage");					   
String pageStr2 = (String)request.getSession().getAttribute("currentPage2");  

String sessionId = request.getSession().getId();
SessionQueryBag queryCollection = presentationTierCache.getSessionQueryBag(sessionId);

if(queryCollection != null)	{

	Collection queryColl = queryCollection.getQueries();
	Collection queryKeys = queryCollection.getQueryNames();
	Iterator i = queryColl.iterator();
	while (i.hasNext()) { 
	     j++;
	     query =i.next().toString();
		 	
		 Iterator iter = queryKeys.iterator();
	     while(iter.hasNext())	{
	     	queryKey = (String)iter.next();
			String queryName = queryCollection.getQuery(queryKey).toString();
			if(query.equalsIgnoreCase(queryName))	{
				break;
			}
		}
%>
		<table border="0" style="font-size:.9em">
			<tr>
				<td><%=query%>
<%
			if(pageStr != null && (pageStr2 ==null ||(pageStr2 != null && pageStr2.equals("1"))) )	{%>
			     <input type="submit" class="sbutton" style="width:50px" value="delete" onclick="setMode('deleteQuery', '<%=queryKey%>')">
			     <input type="submit" class="sbutton" style="width:50px" value="edit" onclick="setMode('editQuery', '<%=queryKey%>')">
			     <input type="submit" class="sbutton" style="width:50px" value="copy" onclick="setMode('copyQuery', '<%=queryKey%>')">	
<%
			}
%>
				</td>
			</tr>
		</table>
<%
	}		
}	

if(j !=0 && j>=2)	{
	if(pageStr != null && (pageStr2 ==null ||(pageStr2 != null && pageStr2.equals("1"))))	{
%>
		<html:submit styleClass="xbutton" value="delete all queries" onclick="setMode('deleteAll', 'all')"/>
<%
	}
}
%>
	<html:hidden property="method"/>
	<html:hidden property="queryKey" />
	</html:form>
	<br/>
</div>

<!------------------ lists -------------------->
<div id="sidebar">
<%
	ListType[] lts = ListType.values();
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<div style="text-align:left; margin-top:20px;">
		<b><%=label%> Lists:</b>
		<div id="sidebar<%=label%>UL">
			<img src="images/indicator.gif"/>
		</div>	
	</div>
<%
	}
%>
	<br/><br/>
	<b style="color:#A90101; font-size:10px;">Items in Red are "custom" lists</b>
</div>
<script language="javascript">
	SidebarHelper.loadSidebar();
</script>