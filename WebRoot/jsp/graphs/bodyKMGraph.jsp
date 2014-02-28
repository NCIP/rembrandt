<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@page contentType="text/html"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@taglib uri='/WEB-INF/caintegrator-graphing.tld' prefix='graphing' %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.nih.nci.rembrandt.util.*" %>

<div>
<s:actionerror />


<s:set var="ta" value="getHelpTopic()" />
<app:cshelp topic="${ta}" /><br clear="all"/>
   
</div>

<s:form action="redrawKMPlot" method="post" >

	<s:set var="baselineGroup" value="quickSearchForm.baselineGroup" />
	<s:hidden name="redrawInputSearchForm.baselineGroup" id="baselineGroup" value="%{baselineGroup}"  />
	<!--  input type="hidden" name="redrawInputSearchForm.baselineGroup" value="#quickSearchForm.baselineGroup"/> -->

	<s:set var="cyto" value="<s:property value='kmForm.geneOrCytoband' />" />
	
	<s:if test="!kmForm.plotType.equals('SAMPLE_KM_PLOT')">
	<div>
	
	<s:if test="quickSearchForm.baselineGroup.length() > 0">
	<b>Constrained to group: <s:property value="quickSearchForm.baselineGroup" /> </b><br/><br/>
	</s:if>
		<table style="border:1px solid silver" cellpadding="4" cellspacing="4" summary="This table is used to format page content">
			<tr>
				<th></th><th></th>
			</tr>
			<tr>			
				<td>
					<!-- Upregulated/Amplified  -->
					<span style="font-size:.9em">
					<label for="upFold">
					<s:property value="kmForm.upOrAmplified" />
					</label>
					</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ge;&nbsp;
				<!--check to see if it is copy number km plot or GE km plot. if it is, change the amplified fold change values-->
				<!--  logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">	-->
				<s:if test="kmForm.plotType.equals('GE_KM_PLOT')">
					<s:select id="upFold" name="redrawInputForm.upFold" list="kmForm.folds" value="kmForm.upFold" theme="simple">
					</s:select>
				</s:if>
				
				
				<s:if test="kmForm.plotType.equals('COPY_NUM_KM_PLOT')">					
					<s:select id="upFold" name="redrawInputForm.upFold" list="kmForm.copyNumberUpFolds" value="kmForm.upFold" theme="simple">
					</s:select>
				</s:if>
				<!--end after up fold change values have been determined-->
					
					<span style="font-size:.9em">
					<s:property value="kmForm.changeType" />
					</span>
				</td>
				
				<td>			
					<!--make sure plot is GE and not Copy # before giving option of algorithm to use-->	
					<s:if test="kmForm.plotType.equals('GE_KM_PLOT')">					
						<!--Unified or regular algorithm-->
						<span style="font-size:.9em;margin-left:10px"><label for="reporterType">Reporter Type</label></span>			
						<s:select id="reporterType" name="redrawInputForm.reporterSelection" list="kmForm.algorithms" value="kmForm.reporterSelection" theme="simple"
						onchange="$('redrawGraphButton').disabled = 'true';$('redrawGraphButton').style.color='gray'; document.forms[0].selectedReporter.selectedIndex=0;  document.forms[0].submit();">
							
						</s:select> &nbsp; 	
					</s:if>		
				</td>
				
			</tr>
			
			
			<tr>
				<td>
				<!-- Downregulated/Deleted -->
				<span style="font-size:.9em"><label for="downFold">
				<s:property value="kmForm.downOrDeleted" />
				</label></span>			
				
				<!--check to see if it is copy number km plot or GE km plot. if it is, change the deleted fold change values-->
				<s:if test="kmForm.plotType.equals('GE_KM_PLOT')">					
					&nbsp;&ge;&nbsp; 
					<s:select id="downFold" name="redrawInputForm.downFold" list="kmForm.folds" value="kmForm.downFold" theme="simple">
	
					</s:select>
				</s:if>
				<s:if test="kmForm.plotType.equals('COPY_NUM_KM_PLOT')">					
					&nbsp;&le;&nbsp; 
					<s:select id="downFold" name="redrawInputForm.downFold" list="kmForm.copyNumberDownFolds" value="kmForm.downFold" theme="simple">
						
					</s:select>
				</s:if>
				<!--end after deleted fold change values have been determined-->
				 			
				<span style="font-size:.9em">
				<s:property value="kmForm.changeType" />
				</span> 
				</td>
				
				<!--Reporters-->
				<td>
					<!--make sure plot is GE and not Copy # before giving option of algorithm to use-->
					<s:if test="kmForm.plotType.equals('GE_KM_PLOT')">
						<span style="font-size:.9em;margin-left:10px"><label for="selectedReporter">Reporters</label></span>
						
						<s:select id="selectedReporter" name="redrawInputForm.selectedReporter" list="kmForm.reporters" value="kmForm.selectedReporter" theme="simple">
						</s:select>
					</s:if>	
				</td>
			</tr>
			
			<tr>
				<td align="center" colspan="2" style="font-size:.9em;margin-left:10px">
					<p align="left">Select which plots should be visible in the redrawn graph:<label for="item">&#160;</label><br/></p>
					
					<s:iterator value="kmForm.items" var="item"> 
							<s:checkbox name="redrawInputForm.selectedItems" value="isItemSelected(#item)" fieldValue="%{item}" theme = "simple">
									<s:property value="#item" />
							</s:checkbox>
					</s:iterator>
				</td>
			</tr>
			
		</table>
	</div>
	<br/>
	<!--  s:submit value="Redraw Graph" id="redrawGraphButton" /> -->
	<s:submit type="button" id="redrawGraphButton" theme="simple">Redraw Graph</s:submit>
	</s:if>
		
	<div>
	<s:if test="kmForm.plotVisible == true">
	
		<hr/>
		<b> 
			<font size="+1"> 
				<s:property value="kmForm.chartTitle" />
			</font>
		</b>
		<br/>
		
		<!-- INSERT CHART HERE --> 
		<p>
			<graphing:KaplanMeierPlot bean="kmForm" dataset="selectedDataset" />
		</p>
		<p>
		<br/>
		
		<!--  BEGIN LEGEND: LINKS TO CLINICAL -->
		
		View Clinical Reports<br />
		
		<!-- bean:define id="selectedItemsId" name="kmDataSetForm" property="selectedItems" />  -->
		<s:set var="selectedItemsId" value="kmForm.getSelectedItemsInList()" />

		<!--check what type of plot it is as to display the correct link text-->
		<!--  logic:equal name="kmDataSetForm" property="plotType" value="GE_KM_PLOT">	-->
		<s:if test="kmForm.plotType.equals('GE_KM_PLOT')">	
			<s:if test="#selectedItemsId != null && #selectedItemsId.contains('Up-Regulated')">
				<a href="#" onclick="spawnx('clinicalViaKMReport.action?dataName=KAPLAN&sampleGroup=up',700,500,'clinicalPlots');">Upregulating Samples</a>
			</s:if>
			<s:if test="#selectedItemsId != null && #selectedItemsId.contains('Down-Regulated')">
		 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 		<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.action?dataName=KAPLAN&sampleGroup=down',700,500,'clinicalPlots');">Downregulating samples</a>
			</s:if>
			
		</s:if>
		<s:if test="kmForm.plotType.equals('COPY_NUM_KM_PLOT')">				
			
			<s:if test="#selectedItemsId != null && #selectedItemsId.contains('Up-Regulated')">
				<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.action?dataName=KAPLAN&sampleGroup=up',700,500,'clinicalPlots');">Samples with Amplification</a>
			</s:if>
			
			<s:if test="#selectedItemsId != null && #selectedItemsId.contains('Down-Regulated')">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.action?dataName=KAPLAN&sampleGroup=down',700,500,'clinicalPlots');">Samples with Deletion</a>
			</s:if>
			
		</s:if>
		
		<s:if test="!kmForm.plotType.equals('SAMPLE_KM_PLOT')">
			
			<s:if test="#selectedItemsId != null && #selectedItemsId.contains('Intermediate')">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.action?dataName=KAPLAN&sampleGroup=inter',700,500,'clinicalPlots');">Intermediate Samples</a>
			</s:if>
				
		</s:if>
		
		<s:if test="kmForm.plotType.equals('SAMPLE_KM_PLOT')">
			<a href="#" onclick="javascript:spawnx('clinicalViaKMReport.action?dataName=KAPLAN&sampleGroup=list1',700,500,'clinicalPlots');return false;">
			<s:property value="kmForm.storedData.samplePlot1Label" />
			</a>
			
			<s:if test="!kmForm.storedData.samplePlot2Label.equals('none')">
				<a style="margin-left:20px" href="#" onclick="javascript:spawnx('clinicalViaKMReport.action?dataName=KAPLAN&sampleGroup=list2',700,500,'clinicalPlots');return false;">
				<!-- bean:write name="kmDataSetForm" property="storedData.samplePlot2Label" />  -->
				<s:property value="kmForm.storedData.samplePlot2Label" />
				</a>
			</s:if>
		</s:if>
		
		<!-- Statistical Data  STARTS HERE --></p>
		<fieldset class="gray" style="text-align:left">
		<legend class="red">Statistical	Report:</legend>
		<table class="graphTable" border="0" cellpadding="2" cellspacing="0">
			<s:if test="kmForm.geneOrCytoband != null && kmForm.geneOrCytoband.length() > 0">
				<tr>
					<td colspan="2" id="reportBold">
						<s:property value="kmForm.geneOrCytoband" />
					</td>
				</tr>
			</s:if>
			<s:if test="kmForm.selectedReporter != null && kmForm.selectedReporter.length() > 0">
				<!--Show the selected Reporter -->
				<tr>
					<td colspan="2" id="reportBold">Reporter: <s:property value="kmForm.selectedReporter" />
						</td>
				</tr>
			</s:if>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
				<!--Show the number of samples in each category-->
			<tr>
				<td colspan="2" id="reportBold">Number of samples in group:</td>
			</tr>
    		<s:if test="kmForm.storedData.upSampleCount > 0">
				<tr>
					<td>
						<s:property value="kmForm.upOrAmplified" />:</td>
					<td>
						<s:property value="kmForm.storedData.upSampleCount" /></td>
				</tr>
			</s:if>
			<s:if test="kmForm.storedData.downSampleCount > 0">
				<tr>
					<td>
					<s:property value="kmForm.downOrDeleted" />:</td>
					<td>
					<s:property value="kmForm.storedData.downSampleCount" />
					</td>
				</tr>
			</s:if>
			<s:if test="kmForm.storedData.intSampleCount > 0">
				<tr>
					<td>Intermediate:</td>
					<td>
					<s:property value="kmForm.storedData.intSampleCount" />
					</td>
				</tr>
			</s:if>
			<s:if test="kmForm.storedData.sampleList1Count > 0">
				<tr>
					<td>
					<s:property value="kmForm.storedData.samplePlot1Label" />
					</td>
					<td>
					<s:property value="kmForm.storedData.sampleList1Count" />
					</td>
				</tr>
			</s:if>
			<s:if test="kmForm.storedData.sampleList2Count > 0">
				<tr>
					<td>
					<s:property value="kmForm.storedData.samplePlot2Label" />
					</td>
					<td>
					<s:property value="kmForm.storedData.sampleList2Count" />
					</td>
				</tr>
			</s:if>
			<tr>
				<td colspan="2">
				<hr width="100%" size="1" color="black" />
				</td>
			</tr>

			<tr>
				<td id="reportBold" colspan="3">Log-rank p-value(for significance of
				difference of survival between group of samples)</td>
			</tr>
			<s:if test="kmForm.storedData.upVsIntPvalue > -100">
				<tr>
					<td>
					<s:property value="kmForm.upOrAmplified" />
					vs. Intermediate:</td>
					<td>
					<s:property value="kmForm.storedData.upVsIntPvalue" />
					</td>
				</tr>
			</s:if>

			<s:if test="kmForm.storedData.upVsDownPvalue > -100">
				<tr>
					<td>
					<s:property value="kmForm.upOrAmplified" />
					 vs. <s:property value="kmForm.downOrDeleted" />:</td>
					<td>
					<s:property value="kmForm.storedData.upVsDownPvalue" />
					</td>
				</tr>
			</s:if>

			<s:if test="kmForm.storedData.downVsIntPvalue > -100">
				<tr>
					<td>
					<s:property value="kmForm.downOrDeleted" />
					vs. Intermediate:</td>
					<td>
					<s:property value="kmForm.storedData.downVsIntPvalue" />
					</td>
				</tr>
			</s:if>
			<s:if test="kmForm.storedData.numberOfPlots > 2">
				<tr>
					<td colspan="2">
					<hr width="100%" size="1" color="black" />
					</td>
				</tr>
				<s:if test="kmForm.storedData.upVsRestPvalue > -100">
					<tr>
						<td>
						<s:property value="kmForm.upOrAmplified" />
						vs. all other samples:</td>
						<td>
						<s:property value="kmForm.storedData.upVsRestPvalue" />
						</td>
					</tr>
				</s:if>
				<s:if test="kmForm.storedData.downVsRestPvalue > -100">
					<tr>
						<td>
						<s:property value="kmForm.downOrDeleted" />
						vs. all other samples:</td>
						<td>
						<s:property value="kmForm.storedData.downVsRestPvalue" />
						</td>
					</tr>
				</s:if>
				<s:if test="kmForm.storedData.intVsRestPvalue > -100">
					<tr>
						<td>Intermediate vs. all other samples:</td>
						<td>
						<s:property value="kmForm.storedData.intVsRestPvalue" />
						</td>
					</tr>
				</s:if>
			</s:if>
				<s:if test="kmForm.storedData.sampleList1VsSampleList2 > -100">
					<tr>
						<td>
						<s:property value="kmForm.storedData.samplePlot1Label" /> vs. <s:property value="kmForm.storedData.samplePlot2Label" />:</td>
						<td>
						<s:property value="kmForm.storedData.sampleList1VsSampleList2" />
						</td>
					</tr>
				</s:if>

			<s:if test="kmForm.storedData.numberOfPlots < 1">
				<td>N/A ( Not Applicable )</td>
			</s:if>
		</table>
		</fieldset>
		
	</s:if> <!-- TAG CREATION WOULD NEED TO CONTAIN THE ABOVE --> 
	
	<s:if test="kmForm.plotVisible == false">
		<p>To display graph, Please select a Reporter for the Gene: 
		<s:property value="kmForm.geneOrCytoband" /> and select "Redraw Graph"
		</p>
	</s:if>
</div>
</s:form>

