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
		height:600px;
		border:0px;
		border-top:1px solid #42659C;
		-moz-border-radius: 0px;
	}
	input#userName, input#password	{
		width:140px;
	}
	
	#loginTable div.r,#loginTable div.rb	{
		border-left: 2px solid #42659C;
		border-right: 2px solid #42659C;
		padding:5px;
	}
	
	#loginTable div.rb	{
		border-bottom: 2px solid #42659C;
		margin-bottom:10px;
	}
	#loginTable div.h, legend {
		color: #C6C7C6;
		background-color:#42659C; 
		font-weight:bold;
		padding:5px; 
	}
</style>

<table id="loginTable">
	<tr>
	<td width="40%" style="border-right:1px dashed gray;">
	<script type="text/javascript">Help.insertHelp("Logging_in", "", "float:right;padding:_8px;");</script>	
	<br clear="both"/>
		<html:form action="alogin.do">
		<fieldset>
		<legend>Existing Users</legend>
		<br clear="both"/>
		
		<b class="msg">Login with your current credentials</b>
		<br/><br/>
		<html:errors property="invalidLogin" />
		<br/><br/>
			<div class="h">Login:</div>
			<div class="r"><label>Username:</label> <input type="text" name="userName" id="userName"/></div>
			<div class="r"><label>Password:</label> <input type="password" name="password" id="password"/></div>
			<div class="rb" style="text-align:center">
				<input style="" type="submit" value="login"/>
			</div>
			<br/><br/>
			<div id="loginMsg"></div>
		</fieldset>
		</html:form>
	</td>
	<td>
	<script type="text/javascript">Help.insertHelp("Registering", "align='right'", "padding:_8px;");</script>
	<br clear="both"/>
	<form id="regForm">
	<fieldset>
	<legend>New Users</legend>
	<br clear="both"/>
	<b>Register for an account to gain instant access to public data</b><br/>
	<div id="regErr" class="mmsg"></div><br/>
	<div><label>&nbsp;</label>* required field</div>
	<div class="h">Name:</div>
	<div class="r"><label>First name*:</label>  <input type="text" id="firstName"/></div>
	<div class="r"><label>Last name*:</label>  <input type="text" id="lastName"/></div>
	<div class="h">Contact Information:</div>
	<div class="r"><label>Email*:</label>  <input type="text" id="email"/></div>
	<div class="r"><label>-<br/><br/></label>Your account information will be sent to this address</div>
	<div class="r"><label>Phone*:</label><input type="text" id="phone"/></div>
	<div class="r"><label>Institution*:</label> <input type="text" id="institution"/></div>
	<div class="r"><label>Department:</label><input type="text" id="dept"/></div>
	<div class="h">Verification:</div>
	<div class="r"><label>Image*:</label><img src="Captcha.jpg"/></div>
	<div class="r"><label>-<br/><br/></label>Please type the text below, displayed in the image above</div>
	<div class="rb"><label>&nbsp;</label><input type="text" id="cap"/></div>

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