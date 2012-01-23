<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
		<%
	     String act = request.getParameter("act");
     %>
	<fieldset class="gray">
	<legend class="red">Survival Range
	<a href="javascript: Help.popHelp('<%=act%>_Survival_tooltip');">[?]</a>    
	<!--  	<app:help help="Specify limits for filtering the clinical data based on the age at diagnosis."/>-->
	</legend><br />

	
	
		
	<label for="survivalLower">&nbsp;&nbsp;&nbsp;lower:&nbsp;</label>
		<!--- <select property="survivalLower">
			<option>0</option>
			<option>10</option>
			<option>20</option>
			<option>30</option>
			<option>40</option>
			<option>50</option>
			<option>60</option>
			<option>70</option>
			<option>80</option>
			<option>90</option>
		</select>&nbsp;&nbsp; --->
		
		<html:select styleId="survivalLower" property="survivalLower">
	    <html:optionsCollection property="survivalLowerColl" />
        </html:select><html:errors property="survivalLower"/>
		
		
		<label for="survivalUpper">upper:&nbsp;</label>
		
		<!--- <select property="survivalUpper">
			<option>0</option>
			<option>10</option>
			<option>20</option>
			<option>30</option>
			<option>40</option>
			<option>50</option>
			<option>60</option>
			<option>70</option>
			<option>80</option>
			<option>90</option>
			<option>90+</option>
		</select> --->
		
		<html:select styleId="survivalUpper" property="survivalUpper">
	    <html:optionsCollection property="survivalUpperColl" />
        </html:select><html:errors property="survivalUpper"/>
		&nbsp;<b class="message">(months)</b>
	 </fieldset>
	 <br /><br />
	<fieldset class="gray">
	<legend class="red">Age at Dx
	<!-- 	<app:help help="Specify limits for filtering the clinical data based on the age at diagnosis."/>-->
	<a href="javascript: Help.popHelp('<%=act%>_AgeatDX_tooltip');">[?]</a>    
	</legend><br />
		
	<label for="ageLower">&nbsp;&nbsp;&nbsp;lower:&nbsp;</label>
		<!--- <select property="ageLower">
			<option>0</option>
			<option>10</option>
			<option>20</option>
			<option>30</option>
			<option>40</option>
			<option>50</option>
			<option>60</option>
			<option>70</option>
			<option>80</option>
			<option>90</option>
		</select>&nbsp;&nbsp; --->
		
		<html:select styleId="ageLower" property="ageLower">
	    <html:optionsCollection property="ageLowerColl" />
        </html:select><html:errors property="ageLower"/>
		<label for="ageUpper">upper:&nbsp;</label>
		
		<!--- <select property="ageUpper">
			<option>0</option>
			<option>10</option>
			<option>20</option>
			<option>30</option>
			<option>40</option>
			<option>50</option>
			<option>60</option>
			<option>70</option>
			<option>80</option>
			<option>90</option>
			<option>90+</option>
		</select> --->
		
		<html:select styleId="ageUpper" property="ageUpper">
	    <html:optionsCollection property="ageUpperColl" />
        </html:select><html:errors property="ageUpper"/>
		&nbsp;<b class="message">(years)</b>
		</fieldset>
		<br /><br />
		
	<fieldset class="gray">
	<legend class="red"><label for="genderType">Gender</label>
	<!-- <app:help help="Select the gender of the patient."/>-->
	<a href="javascript: Help.popHelp('<%=act%>_Gender_tooltip');">[?]</a>    
	
	</legend><br />
			<!--- <select property="genderType">
				<option>all</option>
				<option>male</option>
				<OPTION>female</OPTION>
				<OPTION>other</OPTION>
			</select>&nbsp; --->
			
			&nbsp;&nbsp;<html:select styleId="genderType" property="genderType">
	        <html:optionsCollection property="genderTypeColl" />
            </html:select><html:errors property="genderType"/>
      </fieldset>
      <br />


