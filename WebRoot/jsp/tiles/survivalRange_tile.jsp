<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
	<fieldset class="gray">
	<legend class="red">Survival Range
	<app:help help="The range of the patient survival length after the first diagnosis."/>
	</legend><br />
		<%
	     String act = request.getParameter("act");
     %>
	
	
		
	&nbsp;&nbsp;&nbsp;lower:&nbsp;
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
		
		<html:select property="survivalLower">
	    <html:optionsCollection property="survivalLowerColl" />
        </html:select><html:errors property="survivalLower"/>
		
		
		upper:&nbsp;
		
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
		
		<html:select property="survivalUpper">
	    <html:optionsCollection property="survivalUpperColl" />
        </html:select><html:errors property="survivalUpper"/>
		&nbsp;<b class="message">(months)</b>
	 </fieldset>
	 <br /><br />
	<fieldset class="gray">
	<legend class="red">Age at Dx
	<app:help help="Specify the upper and lower limits for filtering the clinical data based on the age at which a patient was diagnosed with the disease"/>
	</legend><br />
		
	&nbsp;&nbsp;&nbsp;lower:&nbsp;
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
		
		<html:select property="ageLower">
	    <html:optionsCollection property="ageLowerColl" />
        </html:select><html:errors property="ageLower"/>
		upper:&nbsp;
		
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
		
		<html:select property="ageUpper">
	    <html:optionsCollection property="ageUpperColl" />
        </html:select><html:errors property="ageUpper"/>
		&nbsp;<b class="message">(years)</b>
		</fieldset>
		<br /><br />
		
	<fieldset class="gray">
	<legend class="red">Gender</legend><br />
			<!--- <select property="genderType">
				<option>all</option>
				<option>male</option>
				<OPTION>female</OPTION>
				<OPTION>other</OPTION>
			</select>&nbsp; --->
			
			&nbsp;&nbsp;<html:select property="genderType">
	        <html:optionsCollection property="genderTypeColl" />
            </html:select><html:errors property="genderType"/>
      </fieldset>
      <br />


