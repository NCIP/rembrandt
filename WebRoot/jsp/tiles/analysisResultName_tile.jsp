<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="gov.nih.nci.caintegrator.dto.query.QueryDTO,
				 gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache,
				 gov.nih.nci.rembrandt.web.bean.SessionQueryBag,
				 gov.nih.nci.rembrandt.web.factory.ApplicationFactory"%> 

<fieldset class="gray">
<legend class="red">

		<logic:present name="principalComponentForm">
		Step 4: 
		</logic:present>
		<logic:present name="classComparisonForm">
		Step 4: 
		</logic:present>
		<logic:present name="hierarchicalClusteringForm">
		Step 5: 
		</logic:present>
		Name Analysis Result
<b class="req">*</b>
<app:help help="Please give a title/name for this analysis result. This name must be unique among all your analysis results in this session." />
</legend>
<br>
	<html:errors property="analysisResultName"/>
<html:text styleId="analysisResultName" property="analysisResultName" size="50" /> (should be unique)
<br />
<html:errors property="queryName"/><br />
</fieldset>
<%
		RembrandtPresentationTierCache presentationTierCache = ApplicationFactory.getPresentationTierCache();
		String returnQueryNames = "";
		if(presentationTierCache!=null){
			String sessionId = request.getSession().getId();
	  		SessionQueryBag queryCollection = presentationTierCache.getSessionQueryBag(sessionId);
			if (queryCollection != null) {
				   Collection queryDTOKeys = queryCollection.getQueryDTONames();
				   if(queryDTOKeys != null){
				   Iterator iter = queryDTOKeys.iterator();
				   while(iter.hasNext()){
					  String queryDTOKey = (String) iter.next();
					  QueryDTO queryDTO = queryCollection.getQueryDTO(queryDTOKey);
					  if(queryDTO!=null){
					  	String queryName = queryDTO.getQueryName();
					  	if (returnQueryNames.length() > 0){
					  	 returnQueryNames += ",";
					  	}
						if (queryName != null && queryName.trim().length() > 0){
							returnQueryNames += '"'+queryName+'"';
						}
					  }
				}
			}
		}
	}

%>

<SCRIPT>
function checkQueryName(){

	var thisQueryName = document.forms[0].analysisResultName.value;
	
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

