<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.nautilus.ui.bean.SessionQueryBag,gov.nih.nci.nautilus.constants.NautilusConstants" %> 

<fieldset class="gray">
<legend class="red">
<bean:message key="queryName.label"/>
<b class="req">*</b>
<!-- <a href="javascript:void(0);" onmouseover="return overlib('<bean:message key="queryName.help"/>', CAPTION, 'Help');" onmouseout="return nd();">[?]</a> -->
<app:help help="Please give a title/name for this query. This name must be unique among all your queries in this session." />
</legend>
<%
String act = request.getParameter("act");

 String format = "H:mm:ss";
 Date today = new Date();
 SimpleDateFormat formatter = new SimpleDateFormat(format);
 String datenewformat = formatter.format(today);
%>
<br>
	<!-- <html:form action="<%=act%>"> -->
<html:text property="queryName" size="50" /> (should be unique)
<!-- <input type="text" name="queryName" size="50" >  -->
<br /><html:errors property="queryName"/>
	<!-- </html:form> -->
</fieldset>

<%

		SessionQueryBag queryCollection = (SessionQueryBag) request.getSession().getAttribute(NautilusConstants.SESSION_QUERY_BAG_KEY);
 		String returnQueryNames = "";
		
		if (queryCollection != null) {
			
			Collection queryKeys = queryCollection.getQueryNames();
				  
			   Iterator iter = queryKeys.iterator();
			   
			   while(iter.hasNext()){
				  String queryKey = (String) iter.next();
				  String queryName = queryCollection.getQuery(queryKey).getQueryName();

				  if (returnQueryNames.length() > 0) returnQueryNames += ",";

				  if (queryName != null && queryName.trim().length() > 0){
					returnQueryNames += '"'+queryName+'"';
					
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