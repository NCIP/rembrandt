<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>

<html:form action="/kmGraph.do?method=redrawKMPlot">
<html:hidden property="geneSymbol"/>
<div>
     <B>
     Upregulated
     &nbsp 
    <html:select property="upFold">
       <html:options property="folds"/>
    </html:select>
    &nbsp
    Folds
    &nbsp&nbsp&nbsp&nbsp&nbsp
    Downregulated
    &nbsp
     <html:select property="downFold">
       <html:options property="folds"/>
    </html:select>
    &nbspFolds
    &nbsp&nbsp
    </B>
    <html:submit value="Redraw Graph"/>
</div>
<div>
   <cewolf:chart id="xy"	
                  title="Kaplan-Meier Survival Plot" 
                  type="xy" 
                  xaxislabel="Days of Study" 
                  yaxislabel="Probability of Survival">
			   <cewolf:data>
				    <cewolf:producer id="kmDataSetForm"/>
			   </cewolf:data>
		</cewolf:chart>
    <HR>
    
    <p>
     <cewolf:img chartid="xy" 
                  renderer="/cewolf" 
                  width="720" 
                  height="540"/>
		<P>
</div>
</html:form>