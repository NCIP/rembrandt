<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, gov.nih.nci.caintegrator.util.CaIntegratorConstants" %>


<tr class="report">
  <td>
    <br />
    <html:form action="/quickSearch.do?method=quickSearch" styleId="qsForm">
      
      
      <fieldset>
        <legend>
          Quick Search
        </legend>
        <html:errors/>
        <br />
        
        <logic:empty name="quickSearchForm" property="allGeneAlias">
	        <strong>
	          Select graph format:
	        </strong>    
	        <br />
	        
	        <h5>Gene Expression-based  and Copy Number-based Graphs&nbsp;&nbsp;&nbsp;&nbsp;
	        <app:help help="Enter a HUGO gene symbol (such as EGFR,WT1) to plot either a gene expression profile or a Kaplan-Meier survival plot based on the expression of your gene of interest." /></h5>
	        <input type="radio" checked="checked" name="plot" class="radio" value="geneExpPlot" onclick="javascript:onRadio(this,0);">
	        Gene Expression plot&nbsp;<br />
	        
	        <input type="radio" name="plot" class="radio" value="kapMaiPlotGE" onclick="javascript:onRadio(this,1);">
	        Kaplan-Meier survival plot for Gene Expression Data&nbsp;<br />
	        
	        <!-- <h5>Copy Number-based Graph&nbsp;&nbsp;&nbsp;&nbsp;
	        <app:help help="Enter a HUGO gene symbol (such as EGFR,WT1) or an Affymetrix 100K SNP Probeset ID (reporter) to plot a Kaplan-Meier survival plot based on the Gene copy number or the SNP reporter respectively." /></h5>
	        -->
	        <input type="radio" name="plot" class="radio" value="kapMaiPlotCN" onclick="javascript:onRadio(this,2);">
	        Kaplan-Meier survival plot for Copy Number Data<br/>
	           
	     <!--     <hr width=100% color="#002185" size="1px" /> -->
	        
	        <br/>
	        <select name="quickSearchType" style="width:140px">
	       		<option>Gene Keyword</option>
	        </select>

        </logic:empty>
        
        <logic:notEmpty name="quickSearchForm" property="allGeneAlias">
	        <select name="quickSearchName">
	        <logic:iterate name="quickSearchForm" property="allGeneAlias" id="test">
	          <option>
	            <bean:write name='test' property='approvedSymbol' filter='true' />:
	            <bean:write name='test' property='approvedName' filter='true' />            
	          </option>
	            </logic:iterate>
	        </select>
        </logic:notEmpty>
        
        <logic:empty name="quickSearchForm" property="allGeneAlias">
        	<input type="text" name="quickSearchName" id="quickSearchName" value="" size="40"/>&nbsp;
        	<br/>Restrict to sample group: 
        	 <html:select property="baselineGroup" styleId="baselineGroupName" disabled="true">
			 	<html:optionsCollection property="sampleGroupsList" />
			</html:select>
        </logic:empty>
        
        <br/><br/>
        <!--  sample based plots -->
        <h5>Sample-based Graph&nbsp;&nbsp;&nbsp;&nbsp;</h5>
        <input type="radio" name="plot" class="radio" value="<%=CaIntegratorConstants.SAMPLE_KMPLOT%>" onclick="javascript:onRadio(this,3);">
        Kaplan-Meier survival plot for Sample Data&nbsp;
        <br/><br/>
        <html:select property="groupName" style="margin-left:20px;" styleId="groupName" disabled="false">
		 	<html:optionsCollection property="sampleGroupsList" />
		</html:select>
		
		<!-- 
        <input type="text" id="groupName" name="groupName" style="margin-left:25px"/> vs.
        <input type="text" id="groupNameCompare" name="groupNameCompare"/>
        -->
         vs. 
         <html:select property="groupNameCompare" styleId="groupNameCompare" disabled="false">
		 	<html:optionsCollection property="sampleGroupsList" />
		</html:select>
        <br/>
        <script language="javascript">
        	try	{
	        	document.getElementById("groupName").options[0] = null;
    	    	document.getElementById("groupNameCompare").options[0].text = "Rest of the Samples";
    	    }
    	    catch(err){}
    	    
        </script>
	        
        <br/><br/>
        <div style="text-align:center">
	        <html:submit styleClass="xbutton" style="width:50px;" value="Go" />
		        
	        <logic:notEmpty name="quickSearchForm" property="allGeneAlias">
	       		<html:button styleClass="xbutton" property="method" style="width:75px;" value="Cancel" onclick="javascript:location.href='home.do';" />
	        </logic:notEmpty>
        
        	<app:help help="Select either the Gene Keyword or SNP Probe set ID option, as applicable, from the drop-down list and enter the keyword or ID in the text box.The SNP Probe set ID option is available only for the Copy Number-based Graph format." />
        </div>
        <br />
        
      </fieldset>
      <!-- 
      <html:hidden property="plot" />
      <html:hidden property="quickSearchName" />
      -->
    </html:form>
    <br>
 </form>
    <div class="message">
          Note: Please move your mouse over the
          <app:help help="Help messages will appear here."/>
          links for help throughout the application.
     </div><Br><br>
  </td>
</tr>

<script type="text/javascript">
	window.onload = function()	{ $('qsForm').reset()};
</script>
