<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<div class="title">Allele Frequency</div>
Population Type:
<html:select property="alleleFrequency"/>
	<option>&nbsp;</option>
	<option selected> ALL </option>
	<option> CENTRAL ASIA </option>
	<option> CENTRAL/SOUTH AFRICA </option>
	<option> CENTRAL/SOUTH AMERICA </option>
	<option> EAST ASIA </option>
	<option> EUROPE </option>
	<option> MULTI-NATIONAL </option>
	<option> NORTH AMERICA </option>
	<option> NORTH/EAST AFRICA  MIDDLE EAST </option>
	<option> NOT SPECIFIED </option>
	<option> PACIFIC </option>
	<option> UNKNOWN </option>
	<option> WEST AFRICA </option>
</html:select>
<html:errors property="alleleFrequency"/>