<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">

<legend class="red">Region</legend>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" > -->
<br />	&nbsp;&nbsp;Chromosome Number&nbsp;
	
	<html:select property="chrosomeNumber" onchange="javascript:changeList(this);">
		<html:option value="">&nbsp;</html:option>
		<html:option value="1">1</html:option>
		<html:option value="2">2</html:option>
		<html:option value="3">3</html:option>
		<html:option value="4">4</html:option>
		<html:option value="5">5</html:option>
		<html:option value="6">6</html:option>
		<html:option value="7">7</html:option>
		<html:option value="8">8</html:option>
		<html:option value="9">9</html:option>
		<html:option value="10">10</html:option>
		<html:option value="11">11</html:option>
		<html:option value="12">12</html:option>
		<html:option value="13">13</html:option>
		<html:option value="14">14</html:option>
		<html:option value="15">15</html:option>
		<html:option value="16">16</html:option>
		<html:option value="17">17</html:option>
		<html:option value="18">18</html:option>
		<html:option value="19">19</html:option>
		<html:option value="20">20</html:option>
		<html:option value="21">21</html:option>
		<html:option value="22">22</html:option>
		<html:option value="X">X</html:option>
		<html:option value="Y">Y</html:option>
	</html:select>
	<html:errors property="chrosomeNumber"/>

	&nbsp;<br>
	<blockquote>
	<html:radio property="region" value="cytoband" styleClass="radio" />
			Cytoband&nbsp; <html:select property="cytobandRegion" >
				               <html:option value=""></html:option>						   
                           </html:select>
			<input type="button" class="sbutton" value="MAP Browser..." disabled="true"><br />
			<html:errors property="cytobandRegion"/>
			
	<html:radio property="region" value="basePairPosition" styleClass="radio" />
	        Base Pair Position (kb)&nbsp; <html:text property="basePairStart"/>
 	&nbsp;-to-&nbsp;<html:text property="basePairEnd"/>
				<html:errors property="basePairEnd"/>
	</blockquote>	
<!--
<input type="radio" class="radio" name="region" value="cytoband" checked>&nbsp;
Cytoband &nbsp;<input type="text" name="cytobandRegion">
-->


				<html:errors property="region"/>

				</fieldset>
				
				
				<!--
<input type="radio" class="radio" name="region" value="chnum">&nbsp;
Chromosome Number
<blockquote>
	<input type="text" name="chromosomeNumber">&nbsp;
	Base Pair Position (kb)&nbsp;
	<input type="text" size="10" name="basePairStart">&nbsp;start&nbsp;<input type="text" size="10" name="basePairEnd">&nbsp;end
</blockquote>
-->

<!-- </html:form> -->