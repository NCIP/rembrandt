<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<app:checkLogin name="logged" page="/login.jsp" />
<!-- Shan comment out for now -->
<!-- jsp:forward page="menu" / --> 

<!-- META HTTP-EQUIV="Refresh" CONTENT="1;URL=Login.action" -->
<META HTTP-EQUIV="Refresh" CONTENT="0;URL=menu.action">
