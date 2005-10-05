<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<app:checkLogin name="logged" page="/login.jsp" />
<jsp:forward page="/menu.do" /> 
