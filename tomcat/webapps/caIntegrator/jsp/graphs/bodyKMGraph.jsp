<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>

<html:form action="/kmGraph.do?method=redrawKMPlot">
<html:hidden property="geneSymbol"/>
<div>
    Upregulated
    <html:radio title="UPREGULATED" property="regulated" value="Up"/>
    &nbspDownregulated
    <html:radio title="DOWNREGULATED" property="regulated" value="Down"/>
    &nbspFolds
    <html:select property="fold">
       <html:options property="folds"/>
    </html:select>
      
    <html:submit value="Redraw Graph"/>
</div>
<div>
   <cewolf:chart id="xy"	
                  title="Kaplan-Meier Suvival Plot" 
                  type="xy" 
                  xaxislabel="Days of Study" 
                  yaxislabel="Percentage Survival">
			   <cewolf:data>
				    <cewolf:producer id="kmDataSetForm"/>
			   </cewolf:data>
		</cewolf:chart>
    <HR>
    <bean:write name="kmDataSetForm" property="chartHeader"/>
    <p>
     <cewolf:img chartid="xy" 
                  renderer="/cewolf" 
                  width="720" 
                  height="540"/>
		<P>
</div>
</html:form>