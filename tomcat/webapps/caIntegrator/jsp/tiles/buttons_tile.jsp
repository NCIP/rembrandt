<%@page contentType="text/html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<html:reset styleClass="xbutton" value="clear"/>&nbsp;&nbsp;
<html:button styleClass="xbutton" property="method" value="cancel" onclick="javascript:alertUser();"/>&nbsp;&nbsp;
<html:submit styleClass="xbutton" property="method" onclick="javascript: return formNewTarget('_report', 770, 550);" value="run report"/>&nbsp;&nbsp;
<html:submit styleClass="subButton" property="method" value="submit"/>

