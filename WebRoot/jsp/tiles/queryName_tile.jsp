<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
				 gov.nih.nci.rembrandt.util.RembrandtConstants,
	 			 gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,
	 			 gov.nih.nci.rembrandt.web.factory.ApplicationFactory,
	 			 gov.nih.nci.rembrandt.dto.query.Query" %> 	 

<fieldset class="gray">
<legend class="red">
<bean:message key="queryName.label"/>
<b class="req">*</b>
<!-- <a href="javascript:void(0);" onmouseover="return overlib('<bean:message key="queryName.help"/>', CAPTION, 'Help');" onmouseout="return nd();">[?]</a> -->
<app:help help="Please give a title/name for this query. This name must be unique among all your queries in this session." />
</legend>
<%
 String act = request.getParameter("act");
 RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
 String format = "H:mm:ss";
 Date today = new Date();
 SimpleDateFormat formatter = new SimpleDateFormat(format);
 String datenewformat = formatter.format(today);
%>
<br>
	
<html:text property="queryName" size="50" /> (should be unique)
<!-- <input type="text" name="queryName" size="50" >  -->
<br /><html:errors property="queryName"/>
	
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