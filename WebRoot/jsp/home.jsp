<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
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
	        
	        <h5>Gene Expression Graph</h5>
	        <input type="radio" name="plot" class="radio" value="geneExpPlot" checked="true" onclick="javascript:onRadio(this,0);">
	        Gene Expression plot&nbsp;<br />
	        
	        <input type="radio" name="plot" class="radio" value="kapMaiPlotGE" onclick="javascript:onRadio(this,1);">
	        Kaplan-Meier survival plot for Gene Expression Data&nbsp;<br />
	        
	        <h5>Copy Number Graph</h5>
	        <input type="radio" name="plot" class="radio" value="kapMaiPlotCN" onclick="javascript:onRadio(this,2);">
	        Kaplan-Meier survival plot for Copy Number Data&nbsp;
	                
	        <br />
	        <br>
	        
	         <hr width=100% color="#002185" size="1px" />
	        
	        <br />
	        <select name="quickSearchType">
	        <option>Gene Symbol</option>
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
    
    	
    
    
    
    
    
    
    
    
    
    <!--
    <form action="menu.do" method="POST">
      <fieldset>
        <legend>
          Advanced Search
        </legend>
        <br />
        <table border="0" cellspacing="4" width="100%">
          <tr>
            <td>
              <input type="radio" class="radio" name="menu" value="0" checked selected onclick="javascript:document.forms[0].rpt.disabled=true;">
              <b>
                Create a New Query
              </b>
              &nbsp;
              <app:help help="Select this option if you do not have a saved query to upload.  You will be able to build a query based on the Advanced Search Areas listed below."/>

            </td>
            <td>
              <input type="Radio" name="menu" class="radio" value="1" onclick="javascript:document.forms[0].rpt;" disabled="true">
              <b>
                Upload a saved Query
              </b>
              &nbsp;
              <app:help help="You may choose to start from an existing query.  Please upload that saved query here."/>
            </td>
          </tr>
          <tr>
            <td>
              Study Data Set
              <br>
              <select name="dataSet" onchange="javascript:changeList(this);">
                <option>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </option>
                <option>
                  GMDI
                </option>
                <option>
                  Other
                </option>
              </select>
            </td>
            <td>
              <br>
              <input type="file" name="rpt" disabled="true">
            </td>
          </tr>
          <tr>
            <td colspan="2">
              Generating Institution
              <br>
              <select name="generatingInstitution">
                <option>
                  All
                </option>
                <option>
                  NCI
                </option>
                <option>
                  Johns Hopkins University
                </option>
                <option>
                  UCSF
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="center">
              <input type="submit" class="xbutton" style="width:100px;" value="continue">
            </td>
          </tr>
        </table>
        
        <div class="message">
          Note: Please move your mouse over the
          <app:help help="Help messages will appear here."/>
          links for help throughout the application.
        </div>
      </fieldset>
      -->
    </form>
    <div class="message">
          Note: Please move your mouse over the
          <app:help help="Help messages will appear here."/>
          links for help throughout the application.
     </div><Br><br>
  </td>
</tr>
