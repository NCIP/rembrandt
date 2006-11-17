<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script type='text/javascript' src='js/lib/scriptaculous/effects.js'></script>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/RegHelper.js'></script>
<script type='text/javascript' src='js/lib/Reg.js'></script>
<style>
	#loginTable input	{
		width:150px;
	}
	#loginTable div	{
		padding-bottom:10px;
	}
	#loginTable label	{
		float: left;
		width: 120px;
	}
	#loginTable fieldset	{
		height:450px;
	}
	input#userName, input#password	{
		width:140px;
	}
</style>

<table id="loginTable">
	<tr>
	<td width="40%">
	<br clear="both"/>
		<html:form action="alogin.do">
		<fieldset>
		<legend>Login with your user account</legend>
		<br clear="both"/>
		<b class="msg">Login with your current credentials</b><br/><br/><br/><br/>

			<div><label>Username:</label> <input type="text" name="userName" id="userName"/></div>
			<div><label>Password:</label> <input type="password" name="password" id="password"/></div>
			<div style="text-align:center">
				<input style="" type="submit" value="login"/>
			</div>
			<br/><br/>
			<div id="loginMsg"></div>
		</fieldset>
		</html:form>
	</td>
	<td>
	<br clear="both"/>
	<form id="regForm">
	<fieldset>
	<legend>Request a new Account</legend>
	<br clear="both"/>
	<div id="regErr" class="mmsg">All fields required</div><br/><br/>
	<div><label>First name:</label>  <input type="text" id="firstName"/></div>
	<div><label>Last name:</label>  <input type="text" id="lastName"/></div>
	<div><label>Email:</label>  <input type="text" id="email"/></div>
	<div><label>-<br/><br/></label>Your account information will be sent to this address</div>
	<div><label>Institution:</label> <input type="text" id="institution"/></div>
	<div><label>Image:</label><img src="Captcha.jpg"/></div>
	<div><label>-<br/><br/></label>Please type the text below, displayed in the image above</div>
	<div><label>&nbsp;</label><input type="text" id="cap"/></div>
	
	<div style="text-align:center" id="regButtons">
		<input type="reset" value="Reset"/>
		<input type="button" value="Register" onclick="Reg.pReg();"/>
	</div>
	<div id="regStatus" style="text-align:center;display:none;">
		Processing...<br/><img src="images/ajax-bar-loader.gif"/>
	</div>
	</fieldset>
	</form>
<%

//out.println( session.getAttribute(nl.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY));

%>
	</td>
	</tr>
</table>