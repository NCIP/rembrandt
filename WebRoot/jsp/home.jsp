<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="java.util.*, java.lang.*, java.io.*" %>


<tr class="report">
  <td>
    <br />
    <html:form action="/quickSearch.do?method=quickSearch">
      
      
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
	        
	        <h5>Gene Expression-based Graphs&nbsp;&nbsp;&nbsp;&nbsp;
	        <app:help help="Enter a HUGO gene symbol (such as EGFR,WT1) to plot either a gene expression profile or a Kaplan-Meier survival plot based on the expression of your gene of interest." /></h5>
	        <input type="radio" name="plot" class="radio" value="geneExpPlot" checked="true" onclick="javascript:onRadio(this,0);">
	        Gene Expression plot&nbsp;<br />
	        
	        <input type="radio" name="plot" class="radio" value="kapMaiPlotGE" onclick="javascript:onRadio(this,1);">
	        Kaplan-Meier survival plot for Gene Expression Data&nbsp;<br />
	        
	        <h5>Copy Number-based Graph&nbsp;&nbsp;&nbsp;&nbsp;
	        <app:help help="Enter a HUGO gene symbol (such as EGFR,WT1) or an Affymetrix 100K SNP Probeset ID (reporter) to plot a Kaplan-Meier survival plot based on the Gene copy number or the SNP reporter respectively." /></h5>
	        <input type="radio" name="plot" class="radio" value="kapMaiPlotCN" onclick="javascript:onRadio(this,2);">
	        Kaplan-Meier survival plot for Copy Number Data&nbsp;
	                
	        <br />
	        <br>
	        
	         <hr width=100% color="#002185" size="1px" />
	        
	        <br />
	        <select name="quickSearchType">
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
        <html:text property="quickSearchName" size="40" />
        &nbsp;
        </logic:empty>
        
        <html:submit styleClass="xbutton" style="width:50px;" value="Go" />
        
        <logic:notEmpty name="quickSearchForm" property="allGeneAlias">
        <html:button styleClass="xbutton" property="method" style="width:75px;" value="Cancel" onclick="javascript:location.href='home.do';" />
        </logic:notEmpty>
        
        <app:help help="Enter a HUGO gene symbol (such as EGFR) to plot either a gene expression profile or a Kaplan-Meier survival plot for the gene" />
        <br />
        
      </fieldset>
      
      <html:hidden property="plot" />
      <html:hidden property="quickSearchName" />
      
      
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
