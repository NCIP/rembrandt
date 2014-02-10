<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<fieldset class="gray">
<legend class="red">Result View <b class="req">*</b>
<a href="javascript:void(0);" title="Description of Gene View, Description of Sample View">[?]</a>
</legend>
<%
	String act = request.getParameter("act");
%>
<br>
	<!-- <html:form action="<%=act%>" method="get"> -->
<s:select name="form.resultView" list="form.resultViewColl" />
				
<s:fielderror fieldName="resultView"/>

&nbsp;<a href="#" class="message">[sample centric example]</a>&nbsp;<a href="#" class="message">[gene centric example]</a>
</fieldset>
					