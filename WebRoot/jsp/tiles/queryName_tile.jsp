<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page import="java.util.*, java.text.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
				 gov.nih.nci.rembrandt.util.RembrandtConstants,
	 			 gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,
	 			 gov.nih.nci.rembrandt.web.factory.ApplicationFactory,
	 			 gov.nih.nci.rembrandt.dto.query.Query" %> 	 

<fieldset class="gray">
<legend class="red">
<label for="queryName">Query Name</label>
<b class="req">*</b>
<%
 String act = request.getParameter("act") + "_Query_tooltip";
 RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
 String format = "H:mm:ss";
 Date today = new Date();
 SimpleDateFormat formatter = new SimpleDateFormat(format);
 String datenewformat = formatter.format(today);
%>
<!-- <a href="javascript:void(0);" onmouseover="return overlib('<bean:message key="queryName.help"/>', CAPTION, 'Help');" onmouseout="return nd();">[?]</a> -->
<!-- <app:help help="Enter a unique name for the query." />-->
<app:cshelp topic="<%=act%>" text="[?]"/>
</legend>

<br>
	
<s:actionerror />
<s:textfield id="queryName" name="form.queryName" size="50" theme="simple"/> (should be unique)
<br />
<s:fielderror fieldName="queryName" />
	
</fieldset>

<%

		String sessionId = request.getSession().getId();
  		SessionQueryBag queryCollection = presentationTierCache.getSessionQueryBag(sessionId);
 		String returnQueryNames = "";
		if (queryCollection != null) {
			Collection queryKeys = queryCollection.getQueryNames();
			   Iterator iter = queryKeys.iterator();
			   while(iter.hasNext()){
				  String queryKey = (String) iter.next();
				  Query query = queryCollection.getQuery(queryKey);
				  if(query!=null){
				    String queryName = query.getQueryName();
				    if (returnQueryNames.length() > 0){
				    	returnQueryNames += ",";
				    }
				    if (queryName != null && queryName.trim().length() > 0){
						returnQueryNames += '"'+queryName+'"';
					}
				 }
			 }
		}

%>
<SCRIPT>
function checkQueryName(){

	var thisQueryName = document.forms[0].queryName.value;
	
	<%
			out.println("\t\t\tvar queryNameArray = new Array("+returnQueryNames+");");
	%>
	var found = false;
	if (!(thisQueryName == null || thisQueryName == "")) {
		for(var t=0;t<queryNameArray.length; t++)	{
		  if (thisQueryName == queryNameArray[t]) found = true;
		}
		if (found) {
			  if (confirm("Query Name exists  in system.  This action will overwrite existing query")) {
		  		return true;
			  }
	 	}else {return true;}
	 }
	 	
	 	return false;
 }
</SCRIPT>