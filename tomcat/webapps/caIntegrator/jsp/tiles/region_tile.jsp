<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, gov.nih.nci.nautilus.struts.form.*" %> 

<fieldset class="gray">

<legend class="red">Region</legend>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" > -->
	
<br />	&nbsp;&nbsp;Chromosome Number&nbsp;
	
	<html:select property="chrosomeNumber" onchange="javascript:changeCytoBand();">
			    <html:optionsCollection property="chromosomeValue" />
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

<SCRIPT>

  function changeCytoBand()	{	

	    var cn = document.forms[0].chrosomeNumber.value;
		document.forms[0].cytobandRegion.options.length = 1;
		switch(cn)	{

		<%
			BaseForm thisForm = new BaseForm();
			HashMap cytoBandMap =  thisForm.getCytoBandForChr();

			if (cytoBandMap != null) {
			
				Set keys = cytoBandMap.keySet();
				Iterator i = keys.iterator();
				while (i.hasNext()) {
					Object key = i.next();
					out.println("\t\tcase '"+key+"':");
					out.println("\t\t\tvar cytoRegion = new Array("+cytoBandMap.get(key).toString()+");");
					out.println("\t\t\tbreak;");
				}
			}
		%>
			default:
				var cytoRegion = new Array("");
			}

		for(var i=0; i<cytoRegion.length; i++)	{
			myOption = new Option();
			myOption.text = cytoRegion[i];
			myOption.value = cytoRegion[i];
			document.forms[0].cytobandRegion.options[document.forms[0].cytobandRegion.options.length] = myOption;
		}
			
		}
		
</SCRIPT>

<!-- </html:form> -->