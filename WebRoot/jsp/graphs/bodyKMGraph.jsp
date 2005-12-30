<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>

<%
 String helpLink = "<a href=\"javascript: spawn('help.jsp";
 String helpLinkClose = "', 350, 500);\">"+
				"<img align=\"right\" src=\"images/help.png\" border=\"0\" "+
				"onmouseover=\"return overlib('Click here for additional information about this report.', CAPTION, 'Help', OFFSETX, -50);\" onmouseout=\"return nd();\">"+
				"</a><br clear=\"all\">";
%>
<div><html:errors /> <%
   String km = "kmplotGE";
   if(  ((request.getParameter("plot") != null) && 
         (request.getParameter("plot").equalsIgnoreCase("kapMaiPlotGE")))
           || 
        ((request.getParameter("plot") == null) && 
         (request.getParameter("plotType").equalsIgnoreCase("GE_KM_PLOT"))) ){
         km = "kmplotGE";
   }
   if(  ((request.getParameter("plot") != null) && 
         (request.getParameter("plot").equalsIgnoreCase("kapMaiPlotCN")))
           || 
        ((request.getParameter("plot") == null) && 
         (request.getParameter("plotType").equalsIgnoreCase("COPY_NUM_KM_PLOT"))) ){
        km = "kmplotCN";
   }
   
	
%> <%=helpLink%>?sect=<%=km%><%=helpLinkClose%></div>

<html:form action="/kmGraph.do?method=redrawKMPlot">
	<html:hidden property="geneOrCytoband" />
	<html:hidden property="plotType" />
	<div>
		<table style="border:1px solid silver" cellpadding="4" cellspacing="4">
			<tr>			
			<td>
			<!-- Upregulated/Amplified  -->
			<span style="font-size:.9em"><bean:write name="kmDataSetForm" property="upOrAmplified" /></span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ge;&nbsp; 
			<html:select property="upFold">
				<html:options property="folds" />
			</html:select>
			<span style="font-size:.9em"><bean:write name="kmDataSetForm" property="changeType" /></span>
			</td>
			
			<!--Reporters-->
			<td>
			<span style="font-size:.9em;margin-left:10px">Reporters</span>
			<html:select property="selectedReporter">
				<html:options property="reporters" />
			</html:select></td>
			</tr>
			
			<tr>
			<td>
			<!-- Downregulated/Deleted -->
			<span style="font-size:.9em"><bean:write name="kmDataSetForm" property="downOrDeleted" /></span>			
			
			<!--check to see if it is copy number km plot or GE km plot. if it is, change the deleted fold change values-->
			<logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">						
			&nbsp;&ge;&nbsp; 
			<html:select property="downFold">
				<html:options property="folds" />
			</html:select>
			</logic:equal>
			<logic:equal name="kmDataSetForm" property="plotType" value="COPY_NUM_KM_PLOT">						
			&nbsp;&le;&nbsp; 
			<html:select property="downFold">
				<html:options property="copyNumberDownFolds" />
			</html:select>
			</logic:equal>
			<!--end after deleted fold change values have been determined-->
			 			
			<span style="font-size:.9em"><bean:write name="kmDataSetForm" property="changeType" /></span> 
			</td>
			<td>			
			<!--make sure plot is GE and not Copy # before giving option of algorithm to use-->
			<logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">						
			<!--Unified or regular algorithm-->
			<span style="font-size:.9em;margin-left:10px">Algorithm</span>			
			<html:select property="algorithm">
				<html:options property="algorithms" />
			</html:select> &nbsp; 	
			</logic:equal>		
			</td>
			</tr>
			</table>
			</div>
			<html:submit value="Redraw Graph" />
	<div>
	<logic:equal name="kmDataSetForm" property="plotVisible" value="true">
		<hr>
		<b> <font size="+1"> 
		<bean:write name="kmDataSetForm" property="chartTitle" /> </font>
		<br />
		
		<p><!-- INSERT CHART HERE --> 
		<graphing:KaplanMeierPlot bean="kmDataSetForm" dataset="selectedDataset" /></p>
		<p>
		<br>
		
		View Clinical Reports<br />
		<!--check what type of plot it is as to display the correct link text-->
		<logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">		
			<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=up',700,500,'clinicalPlots');"/>Upregulating Samples</a>
		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=down',700,500,'clinicalPlots');"/>Downregulating samples</a>
		</logic:equal>
		<logic:equal name="kmDataSetForm" property="plotType" value="COPY_NUM_KM_PLOT">		
			<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=up',700,500,'clinicalPlots');"/>Samples with Amplification</a>
		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=down',700,500,'clinicalPlots');"/>Samples with Deletion</a>
		</logic:equal>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.do?dataName=KAPLAN&sampleGroup=inter',700,500,'clinicalPlots');"/>Intermediate Samples</a>
		
		
		<!-- Statistical Data  STARTS HERE --></p>
		<fieldset class="gray" style="text-align:left">
		<legend class="red">Statistical	Report:</legend>
		<table class="graphTable" border="0" cellpadding="2" cellspacing="0">
			<logic:present name="kmDataSetForm" property="geneOrCytoband">
				<tr>
					<td colspan="2" id="reportBold">
					<bean:write name="kmDataSetForm" property="geneOrCytoband" /></td>
				</tr>
			</logic:present>
			<logic:present name="kmDataSetForm" property="selectedReporter">
				<!--Show the selected Reporter -->
				<tr>
					<td colspan="2" id="reportBold">Reporter: <bean:write
						name="kmDataSetForm" property="selectedReporter" /></td>
				</tr>
			</logic:present>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
				<!--Show the number of samples in each category-->
			<tr>
				<td colspan="2" id="reportBold">Number of samples in group:</td>
			</tr>
    		<logic:greaterThan name="kmDataSetForm" property="storedData.upSampleCount"
				value="0">
				<tr>
					<td><bean:write name="kmDataSetForm" property="upOrAmplified" /></td>
					<td><bean:write name="kmDataSetForm" property="storedData.upSampleCount" /></td>
				</tr>
			</logic:greaterThan>
			<logic:greaterThan name="kmDataSetForm" property="storedData.downSampleCount"
				value="0">
				<tr>
					<td><bean:write name="kmDataSetForm" property="downOrDeleted" /></td>
					<td><bean:write name="kmDataSetForm" property="storedData.downSampleCount" /></td>
				</tr>
			</logic:greaterThan>
			<logic:greaterThan name="kmDataSetForm" property="storedData.intSampleCount"
				value="0">
				<tr>
					<td>Intermediate:</td>
					<td><bean:write name="kmDataSetForm" property="storedData.intSampleCount" /></td>
				</tr>
			</logic:greaterThan>
			<tr>
				<td colspan="2">
				<hr width="100%" size="1" color="black" />
				</td>
			</tr>

			<tr>
				<td id="reportBold" colspan="3">Log-rank p-value(for significance of
				difference of survival between group of samples)</td>
			</tr>
			<logic:greaterThan name="kmDataSetForm" property="storedData.upVsIntPvalue"
				value="-100">
				<td><bean:write name="kmDataSetForm" property="upOrAmplified" />
				vs. Intermediate:</td>
				<td><bean:write name="kmDataSetForm" property="storedData.upVsIntPvalue" /></td>
				<tr></tr>
			</logic:greaterThan>

			<logic:greaterThan name="kmDataSetForm" property="storedData.upVsDownPvalue"
				value="-100">
				<tr>
					<td><bean:write name="kmDataSetForm" property="upOrAmplified" />
					vs.<bean:write name="kmDataSetForm" property="downOrDeleted" /></td>
					<td><bean:write name="kmDataSetForm" property="storedData.upVsDownPvalue" /></td>
				</tr>
			</logic:greaterThan>

			<logic:greaterThan name="kmDataSetForm" property="storedData.downVsIntPvalue"
				value="-100">
				<tr>
					<td><bean:write name="kmDataSetForm" property="downOrDeleted" />
					vs. Intermediate:</td>
					<td><bean:write name="kmDataSetForm" property="storedData.downVsIntPvalue" /></td>
				</tr>
			</logic:greaterThan>
			<logic:greaterThan name="kmDataSetForm" property="storedData.numberOfPlots"
				value="2">
				<tr>
					<td colspan="2">
					<hr width="100%" size="1" color="black" />
					</td>
				</tr>
				<logic:greaterThan name="kmDataSetForm" property="storedData.upVsRestPvalue"
					value="-100">
					<tr>
						<td><bean:write name="kmDataSetForm" property="upOrAmplified" />
						vs. all other samples:</td>
						<td><bean:write name="kmDataSetForm" property="storedData.upVsRestPvalue" /></td>
					</tr>
				</logic:greaterThan>
				<logic:greaterThan name="kmDataSetForm" property="storedData.downVsRestPvalue"
					value="-100">
					<tr>
						<td><bean:write name="kmDataSetForm" property="downOrDeleted" />
						vs. all other samples:</td>
						<td><bean:write name="kmDataSetForm" property="storedData.downVsRestPvalue" /></td>
					</tr>
				</logic:greaterThan>
				<logic:greaterThan name="kmDataSetForm" property="storedData.intVsRestPvalue"
					value="-100">
					<tr>
						<td>Intermediate vs. all other samples:</td>
						<td><bean:write name="kmDataSetForm" property="storedData.intVsRestPvalue" /></td>
					</tr>
				</logic:greaterThan>
			</logic:greaterThan>
			<logic:lessEqual name="kmDataSetForm" property="storedData.numberOfPlots"
				value="1">
				<td>N/A ( Not Applicable )</td>
			</logic:lessEqual>
		</table>
		</fieldset>
		</b>
	</logic:equal> <!-- TAG CREATION WOULD NEED TO CONTAIN THE ABOVE --> 
	<logic:equal name="kmDataSetForm" property="plotVisible" value="false">
		<p>To display graph, Please select a Reporter for the Gene: 
		<bean:write	name="kmDataSetForm" property="geneOrCytoband" /> and select "Redraw Graph"
		</p>
	</logic:equal>
</div>
</html:form>
