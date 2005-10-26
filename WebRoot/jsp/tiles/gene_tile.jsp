<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<% 
/*
 * Gene Tile - used in: GeneExpression form, CGH form
 */

String act = request.getParameter("act");
%>
	
<fieldset class="gray">
<legend class="red">Gene
<app:help help="Choose one type of Gene identifiers (Genbank ID, LocusLink ID or Gene symbol) from the pick list. Then enter the corresponding comma delimited value or IDs for the genes to be searched in the text box. Optionally, you can upload a text file containing Gene identifiers by clicking the browse button. There must only be one entry per line and a return must be added at the end of the file. Choose “All Genes query” if you do not wish to specify a list of genes, but would like to see the data for all the genes analyzed. You must apply the “All Genes query” to a pre-existing result set. (see help on Refine query page for more details)" />
</legend>

<br>
  <html:radio property="geneOption" styleClass="radio" value="standard" onclick="submitStandardQuery()" />
  
      &nbsp;&nbsp;<html:select property="geneType" disabled="false">
		<html:optionsCollection property="geneTypeColl" />
	  </html:select>
	  
	  
	  <logic:present name="geneexpressionForm">
	  <logic:equal name="geneexpressionForm" property="geneOption" scope="request" value="standard">
	   <p style="margin-left:30px">
        <html:radio property="geneGroup" value="Specify" styleClass="radio" onfocus="javascript:onRadio(this,0);"/>
          <html:text property="geneList" disabled="false" onfocus="javascript:radioFold(this);" onblur="javascript:cRadio(this, document.forms[0].geneGroup[0]);" />
          <!-- <a href="javascript:void(0);" onmouseover="return overlib('Selected Criteria on this form applies to all genes specified in this list.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>-->
          &nbsp;-or-&nbsp;
          <html:radio property="geneGroup" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);"/>
			<html:file property="geneFile" disabled="true"  onblur="javascript:cRadio(this, document.forms[0].geneGroup[1]);" onfocus="javascript:document.forms[0].geneGroup[1].checked = true;" />
			<Br>
		    </p>		
		<html:errors property="geneFile"/>
		<html:errors property="geneGroup"/>
		<html:errors property="geneList"/>
		<html:errors property="geneType"/>
		</logic:equal>
		</logic:present>
		<logic:present name="comparitivegenomicForm">
		  <logic:equal name="comparitivegenomicForm" property="geneOption" scope="request" value="standard">
		   <p style="margin-left:30px">
	        <html:radio property="geneGroup" value="Specify" styleClass="radio" onfocus="javascript:onRadio(this,0);"/>
	          <html:text property="geneList" disabled="false" onfocus="javascript:radioFold(this);" onblur="javascript:cRadio(this, document.forms[0].geneGroup[0]);" />
	          <!-- <a href="javascript:void(0);" onmouseover="return overlib('Selected Criteria on this form applies to all genes specified in this list.', CAPTION, 'Help');" onmouseout="return nd();">[?]</a>-->
	          &nbsp;-or-&nbsp;
	          <html:radio property="geneGroup" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);"/>
				<html:file property="geneFile" disabled="true"  onblur="javascript:cRadio(this, document.forms[0].geneGroup[1]);" onfocus="javascript:document.forms[0].geneGroup[1].checked = true;" />
				<Br>
		        </p>		
			<html:errors property="geneFile"/>
			<html:errors property="geneGroup"/>
			<html:errors property="geneList"/>
			<html:errors property="geneType"/>
			</logic:equal>
			</logic:present>
			<br />
  <html:radio property="geneOption" styleClass="radio" value="allGenes" onclick="submitAllGenesQuery();" />All Genes Query
		
	</fieldset>		



<script language="JavaScript">
function submitAllGenesQuery(){
  document.forms[0].multiUseButton.value="AllGenes";
  document.forms[0].multiUseButton.click();
   }
	    
function submitStandardQuery(){
  document.forms[0].multiUseButton.value="Standard";
  document.forms[0].multiUseButton.click();
}

</script>	
