<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String actLong = request.getParameter("s").toLowerCase();
String act ="";
   if(actLong.indexOf(':') != -1){
   String[] actLongStrings = actLong.split(":");
    act = actLongStrings[0];
    
   }
   else
   act = actLong + "_Array_tooltip"; 
%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <body>
  <fieldset>
    <legend>Raw Data Files Download from CaArray</legend>
	<br />
	
		
			    <fieldset class="gray">
					<legend class="red">Step 1:Choose the saved List:</legend></br> 
					&nbsp;&nbsp;<html:select property="groupNameCompare" styleId="groupNameCompare" style="width:200px;" disabled="false" onchange="examineGroups(this);needGVal = false;">	         	
			 			<html:optionsCollection property="sampleGroupsList" />
			 		</html:select>
				</fieldset>
				
				<fieldset class="gray">
					<legend class="red">Step 2:Choose Array Platform: <app:cshelp topic="<%=act%>" text="[?]"/></legend><br/>
					&nbsp;&nbsp;<select name="arrayPlatform"> 		
				    	<option value="<%=Constants.ALL_PLATFROM%>">All</option>
						<option selected="true" value="<%=Constants.AFFY_OLIGO_PLATFORM%>">Oligo (Affymetrix U133 Plus 2.0)</option>
						<option value="<%=Constants.CDNA_ARRAY_PLATFORM%>">cDNA</option>
					</select>	
				</fieldset>
					
			    <fieldset class="gray">
				<legend class="red">Step 3:Select file type to download:</legend><br/>
				&nbsp;&nbsp;<select name="fileType">
					<option>CEL</option>
					<option>CHP</option>
					<option>ALL</option>
					<option>OTHER</option>
				</select>
				</fieldset><br/>
				
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" ALIGN="middle" value="download" style="width:70px"/>	        
	
	</fieldset>
  </body>
</html>
