<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %>
<tr class="report">
  <td>
    <br />
    <html:form action="/quickSearch.do?method=quickSearch">
      <html:hidden property="dave" value="test"/>
      <fieldset>
        <legend>
          Quick Search
        </legend>
        <html:errors/>
        <br />
        <strong>
          Select graph format:
        </strong>
        <br />
        <input type="radio" name="plot" class="radio" value="geneExpPlot" checked="true" onclick="javascript:onRadio(this,0);">
        Gene Expression plot&nbsp;<br />
        <input type="radio" name="plot" class="radio" value="kapMaiPlotGE" onclick="javascript:onRadio(this,1);">
        Kaplan-Meier survival plot for Gene Expression Data&nbsp;<br />
        <input type="radio" name="plot" class="radio" value="kapMaiPlotCN" onclick="javascript:onRadio(this,2);">
        Kaplan-Meier survival plot for Copy Number Data&nbsp;<br />
        <br />
        <br>
        <strong>
          Enter a gene symbol
        </strong>
        <br />
        <select name="quickSearchType">
        <option>Gene Symbol</option>
        </select>
        <input type="text" name="quickSearchName" size="40">
        &nbsp;
        <html:submit styleClass="xbutton" style="width:50px;" value="Go" />
        <app:help help="Enter a HUGO gene symbol (such as EGFR) to plot either a gene expression profile or a Kaplan-Meier survival plot for the gene" />
        <br />
        
      </fieldset>
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
