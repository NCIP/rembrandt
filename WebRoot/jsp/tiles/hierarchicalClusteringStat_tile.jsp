<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
Step 2: Select Statistic
<app:help help="Select the following options. 1.Distance Matrix: Select an option (Correlation or Euclidean    distance) from the drop-down list. Pearson correlation measures the relative shape of the gene regulations rather than the absolute levels. This is a natural choice because it is widely used to measure gene correlations. Euclidean distance is the most common distance measure. It measures the absolute level of gene regulation. 2. Linkage Method: Select an option (Average, Single or Complete)    from the drop-down list. Different linkage methods affect the shape of the resulting clusters. Average linkage: The linking distance is the aver­age of all pair-wise distances between members of the two clus­ters. Selecting Hierarchical Clustering criteria. Single linkage: The linking distance is the minimum distance between two clusters. Com­plete linkage: The linking distance is the maximum distance between two clusters."/>
</legend>
<%
	String act = request.getParameter("act");
%>
	
<br>
Distance Matrix:
&nbsp;&nbsp;<html:select property="distanceMatrix">
					<html:optionsCollection property="distanceMatrixCollection" /> 
			</html:select>
			&nbsp;&nbsp;
Linkage Method:
&nbsp;&nbsp;<html:select property="linkageMethod">
					<html:optionsCollection property="linkageMethodCollection" /> 
			</html:select>
			
			
</fieldset>
