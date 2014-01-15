<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
		<%
	     String act = request.getParameter("act");
	     String sHelp = request.getParameter("act") + "_Survival_tooltip";
	     String aHelp = request.getParameter("act") + "_AgeatDX_tooltip";
	     String gHelp = request.getParameter("act") + "_Gender_tooltip";
     %>
	<fieldset class="gray">
	<legend class="red">Survival Range
	<app:cshelp topic="<%=sHelp%>" text="[?]"/>  
	<!--  	<app:help help="Specify limits for filtering the clinical data based on the age at diagnosis."/>-->
	</legend><br />

	<label for="survivalLower">&nbsp;&nbsp;&nbsp;lower:&nbsp;</label>
		
		<s:select id="survivalLower" name="form.survivalLower" 
		list="form.survivalLowerColl" listKey="value" listValue="label" />
	    <s:actionerror name="survivalLower"/>
		
		
		<label for="survivalUpper">upper:&nbsp;</label>
		
		<s:select id="survivalUpper" name="form.survivalUpper" 
		 list="form.survivalUpperColl" listKey="value" listValue="label" />
	    <s:actionerror name="survivalUpper"/>
		&nbsp;<b class="message">(months)</b>
	 </fieldset>
	 <br /><br />
	<fieldset class="gray">
	<legend class="red">Age at Dx
	<!-- 	<app:help help="Specify limits for filtering the clinical data based on the age at diagnosis."/>-->
	<app:cshelp topic="<%=aHelp%>" text="[?]"/>   
	</legend><br />
		
	<label for="ageLower">&nbsp;&nbsp;&nbsp;lower:&nbsp;</label>
		
		<s:select id="ageLower" name="form.ageLower" 
		 list="form.ageLowerColl" listKey="value" listValue="label" />
	    <s:actionerror name="ageLower"/>
		<label for="ageUpper">upper:&nbsp;</label>
		
		<s:select id="ageUpper" name="form.ageUpper" 
		 list="form.ageUpperColl" listKey="value" listValue="label" />
	    <s:actionerror name="ageUpper"/>
		&nbsp;<b class="message">(years)</b>
		</fieldset>
		<br /><br />
		
	<fieldset class="gray">
	<legend class="red"><label for="genderType">Gender</label>
	<!-- <app:help help="Select the gender of the patient."/>-->
	<app:cshelp topic="<%=gHelp%>" text="[?]"/> 
	
	</legend><br />
			
			
			&nbsp;&nbsp;<s:select id="genderType" name="form.genderType" 
			 list="form.genderTypeColl" listKey="value" listValue="label" />
	        <s:actionerror name="genderType"/>
      </fieldset>
      <br />


