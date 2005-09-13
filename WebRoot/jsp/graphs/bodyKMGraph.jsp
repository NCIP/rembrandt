<%@page contentType="text/html"%>
<%@taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
 String helpLink = "<a href=\"javascript: spawn('help.jsp";
 String helpLinkClose = "', 350, 500);\">"+
				"<img align=\"right\" src=\"images/help.png\" border=\"0\" "+
				"onmouseover=\"return overlib('Click here for additional information about this report.', CAPTION, 'Help', OFFSETX, -50);\" onmouseout=\"return nd();\">"+
				"</a><br clear=\"all\">";
%>
<div>
<html:errors/>
<%=helpLink%>?sect=kmplotGE<%=helpLinkClose%>
</div>
<html:form action="/kmGraph.do?method=redrawKaplanMeierGeneExpressionPlot">
<!-- User Input Panel for selecting the values
	 and redrawing the graph -->
<div>
    <b>
      Up Regulated <!-- Upregulated/Amplified  -->
      &nbsp;&ge;&nbsp;
      <html:select property="upFold">
        <html:options property="folds"/>
      </html:select>
      &nbsp;
      Fold <!--Fold/Copies -->
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      Down Regulated <!-- Downregulated/Deleted -->
      &nbsp;&ge;&nbsp;
      <html:select property="downFold">
        <html:options property="folds"/>
      </html:select>
      &nbsp;
      Fold <!--Fold/Copies -->
      &nbsp;&nbsp;Reporters
      <html:select property="selectedReporter">
        <html:options property="reporters"/>
      </html:select>
      &nbsp;
      &nbsp;&nbsp;
    </b>
    <html:submit value="Redraw Graph"/>
  </div>
  <hr>
   <b>
    <font size="+1">
      <bean:write name="kmDataSetForm" property="chartTitle"/>
    </font>
    <p>
    <!-- INSERT CHART HERE -->
    <graphing:KaplanMeierPlot/>
    <p>
</html:form>