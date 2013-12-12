<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%
	/*
	* HTML head specifying the JS and CSS req'd files
	*/

%>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1">

	<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
	
	<LINK href="<%=request.getContextPath()%>/css/bigStyle.css" rel="stylesheet" type="text/css">
	<script language="javascript" src="<%=request.getContextPath()%>/js/caIntScript.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/js/rembrandtScript.js"></script>
	<!-- JB Begin: GF#19875 Gene Alias validation - without clicking the 'check alias' -->
	<script language="JavaScript" src="<%=request.getContextPath()%>/js/geneexpression.js"></script>
	<!-- JB End: GF#19875 Gene Alias validation - without clicking the 'check alias' -->
		
	<script language="javascript" src="<%=request.getContextPath()%>/js/box/browserSniff.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/js/lib/prototype-1.6.0.2.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/js/lib/Help.js"></script>
	
	<script language="javascript" src="<%=request.getContextPath()%>/js/lib/common/JSLoader.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/js/lib/window.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/js/lib/common/fat.js"></script>
	
	<script language="javascript" src="<%=request.getContextPath()%>/js/lib/scriptaculous/scriptaculous.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/overlib.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/overlib_hideform.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/menuSwapper.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/moveUpDown.js"></script>
	
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/DynamicListHelper.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Inbox.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/js/lib/common/SidebarHelper.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/js/lib/common/QueryDetailHelper.js'></script>
	
	<style type="text/css" media="screen">@import "<%=request.getContextPath()%>/css/tabs.css";</style>
	
