<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>

<%
 String helpLink = "<a href=\"javascript: spawn('help.jsp";
 String helpLinkClose = "', 350, 500);\">"+
				"<img align=\"right\" src=\"images/helpIcon.jpg\" border=\"0\" "+
				"onmouseover=\"return overlib('Click here for additional information about this report.', CAPTION, 'Help', OFFSETX, -50);\" onmouseout=\"return nd();\">"+
				"</a><br clear=\"all\">";
%>
<div>
<html:errors/>
<%=helpLink%>?sect=kmplot<%=helpLinkClose%>
</div>

<html:form action="/kmGraph.do?method=redrawKMPlot">
  <html:hidden property="geneSymbol"/>
  <div>
    <b>
      Upregulated
      &nbsp;&ge;&nbsp;
      <html:select property="upFold">
        <html:options property="folds"/>
      </html:select>
      &nbsp;
      Folds
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      Downregulated
      &nbsp;&ge;&nbsp;
      <html:select property="downFold">
        <html:options property="folds"/>
      </html:select>
      &nbsp;Folds
      &nbsp;&nbsp;
    </b>
    <html:submit value="Redraw Graph"/>
  </div>
  <div>
    <cewolf:overlaidchart id="kmChart"
        type="overlaidxy"
        xaxislabel="Days of Study"
        yaxislabel="Probability of Survival"
        xaxistype="number"
        yaxistype="number">
          <cewolf:plot type="xyline">
            <cewolf:data>
              <cewolf:producer id="kmDataSetForm">
                 <cewolf:param name="censusPlot" value='<%= new Boolean("false") %>'/>
               </cewolf:producer>
            </cewolf:data>
          </cewolf:plot>
          <cewolf:plot type="scatter">
            <cewolf:data>
               <cewolf:producer id="kmDataSetForm">
                 <cewolf:param name="censusPlot" value='<%= new Boolean("true") %>'/>
               </cewolf:producer>
            </cewolf:data>
          </cewolf:plot>
    </cewolf:overlaidchart>
    <hr>
    <b>
    <font size="+1">
      <bean:write name="kmDataSetForm" property="chartTitle"/>
    </font>
    <p>
    <cewolf:img chartid="kmChart"
    renderer="/cewolf"
    width="720"
    height="540"/>
    <p>
  </div>
</html:form>
