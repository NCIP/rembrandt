<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<Table>
<TR>
	<TD>
		<DIV class="title">	Survival Range</DIV>
		<%
	     String act = request.getParameter("act");
	     System.out.println(act);
        %>
	<!-- <html:form action="<%=act%>" method="get"> -->
	
		
		lower:&nbsp;
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
	<TD>
		<DIV class="title">	Age at Dx</DIV>
		
		lower:&nbsp;
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
	</TD>
	<TD align="left">
		<DIV class="title">	Gender</DIV>
			<!--- <select property="genderType">
				<option>all</option>
				<option>male</option>
				<OPTION>female</OPTION>
				<OPTION>other</OPTION>
			</select>&nbsp; --->
			
			<html:select property="genderType">
	        <html:optionsCollection property="genderTypeColl" />
            </html:select><html:errors property="genderType"/>
	</TD>
</TR>
<!-- </html:form> -->
</table>
