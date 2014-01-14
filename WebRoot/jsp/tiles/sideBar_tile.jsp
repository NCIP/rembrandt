<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*,
				 gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
				 gov.nih.nci.rembrandt.util.RembrandtConstants,
				 gov.nih.nci.rembrandt.dto.query.CompoundQuery,
	 			 gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,
	 			 gov.nih.nci.rembrandt.web.factory.ApplicationFactory,
	 			 gov.nih.nci.caintegrator.application.lists.ListType" %> 

<!--  lists related -->




<style>
	#sidebar div b {
		border-bottom: 1px solid #A90101;
	}
</style>
<!--  end lists related -->

<div width="100%">
<table width="100%" summary="This table is used to format page content">
<tr><th></th><th></th></tr>
<tr><td style="vertical-align:bottom;">
<h3 style="vertical-align:bottom;">About</h3>
</td><td width="40">
<app:cshelp topic="Blue_panel" />
</td></tr></table>
<div style="font-size:0.8em;padding: 0px 0px 0px 0px;">
	<span>Application Release: <b><%=System.getProperty("rembrandt.application.version")!=null ? System.getProperty("rembrandt.application.version") : "1.5"%></b>
	<br/>
	Data Release Date: <b><%=System.getProperty("rembrandt.data.releaseDate")!=null ? System.getProperty("rembrandt.data.releaseDate") : "2009"%></b>
	</span> 
</div>

<h3>Queries</h3>
<s:form action ="delete_Query" theme="simple">
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
String cquery = "";
int j = 0;	
String queryKey = null;
	
String pageStr = (String)request.getSession().getAttribute("currentPage");					   
String pageStr2 = (String)request.getSession().getAttribute("currentPage2");  

String sessionId = request.getSession().getId();
SessionQueryBag queryCollection = presentationTierCache.getSessionQueryBag(sessionId);

if(queryCollection != null)	{

	Collection queryColl = queryCollection.getQueries();
	Collection queryKeys = queryCollection.getQueryNames();
	Collection compoundQueryKeys = queryCollection.getCompoundQueryNames();
	Map cqs = queryCollection.getCompoundQueryMap();
	
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
		<table border="0" style="font-size:.9em" summary="This table is used to format page content">
			<tr><th></th></tr>
			<tr>
				<td><%=query%>
<%
			if(pageStr != null && (pageStr2 ==null ||(pageStr2 != null && pageStr2.equals("1"))) )	{
%>			
			     <input type="submit" class="sbutton" style="width:50px" value="Delete" onclick="setMode('deleteQuery', '<%=queryKey%>')">
			     <input type="submit" class="sbutton" style="width:50px" value="Edit" onclick="setMode('editQuery', '<%=queryKey%>')">
			     <input type="submit" class="sbutton" style="width:50px" value="Copy" onclick="setMode('copyQuery', '<%=queryKey%>')">	
<%
			}
%>
				</td>
			</tr>
		</table>
<%
	}
	i = compoundQueryKeys.iterator();
	while (i.hasNext()) { 
		query = (String)i.next();
		CompoundQuery cq = (CompoundQuery)cqs.get(query);
		cquery = cq.toStringForSideBar();
		j++;
		
%>
		<table border="0" style="font-size:.9em" summary="This table is used to format page content">
			<tr><th></th></tr>
			<tr>
				<td><%=cquery%>

<%
			if(pageStr != null && (pageStr2 ==null ||(pageStr2 != null && pageStr2.equals("1"))) )	{
%>
			     <input type="submit" class="sbutton" style="width:50px" value="Delete" onclick="setMode('deleteCompoundQuery', '<%=query%>')">	
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
	if(pageStr != null && (pageStr2 ==null ||(pageStr2 != null && pageStr2.equals("1"))) )	{
%>
		<s:submit styleClass="xbutton" value="Delete All Queries" onclick="setMode('deleteAll', 'all')"/>
<%
	}
}
%>
	<s:hidden property="method"/>
	<s:hidden property="queryKey" />
	</s:form>
	<br/>
</div>

<!------------------ lists -------------------->
<div id="sidebar">
<h3>Lists</h3>
<%
	ListType[] lts = ListType.values();
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<div style="text-align:left; margin-top:10px;">
		<b><%=label%> Lists:</b>
		<div id="sidebar<%=label%>UL">
			<img alt="indicator" src="images/indicator.gif"/>
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