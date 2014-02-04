<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>


<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<script type='text/javascript' src='js/lib/scriptaculous/effects.js'></script>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/RegHelper.js'></script>
<script type='text/javascript' src='js/lib/Reg.js'></script>
<style>
	#loginTable input	{
		width:150px;
	}
	#loginTable input#join_listServe, input#leave_listServe, input#listServe	{
		width: 40px;
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
	input#userName, input#password   {
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
	
	#loginTable div.label_checkbox_pair {
	border-left: 2px solid #42659C;
	border-right: 2px solid #42659C;
	clear: both;
	float: none;
	padding-bottom:10px;
	position: relative;

	}
	#loginTable div.label_checkbox_pair input {
	left: 120px;
	position: absolute;
	top: 1px;
	}
	#loginTable div.label_checkbox_pair label {
	display: block;
	margin-left: 5px;
	width: 200px;
	}
</style>

<table id="loginTable" summary="This table is used to format page content">
	<tr>
		<th></th><th></th>
	</tr>
	<tr>
	<td width="40%" style="border-right:1px dashed gray;">
	<app:cshelp topic="Logging_in" style="float:right;padding:_8px;cursor:pointer;" />
	<br clear="both"/>
		<fieldset>
		<s:form action="alogin">		
		<legend>Existing Users</legend>
		<br clear="both"/>
		
		<b class="msg">Login with your current credentials</b>
		<br/><br/>
		<s:fielderror fieldName="invalidLogin" />
		<br/><br/>
			<div class="h">Login:</div>
			<div class="r"><label for="userName">Username:</label> <input type="text" name="loginForm.userName" id="userName"/></div>
			<div class="r"><label for="password">Password:</label> <input type="password" name="loginForm.password" id="password" AUTOCOMPLETE = "off"/></div>
			<div class="rb" style="text-align:center">
				<input style="" type="submit" value="login"/>
			</div>
			Trouble logging in? <a href="http://ncicb.nci.nih.gov/NCICB/support" target="_blank">Contact support</a>
			<br/><br/>
			<!--  div id="loginMsg"></div> -->
			<s:token />
		</s:form>
		<form id="listServeForm">
			<div class="h">Rembrandt User List Serve:</div>
			<div class="r" style="text-align:left">The list serve periodically informs Rembrandt users on application and data updates.</div>
			
			<div class="r"><label for="listemail">Email*:</label> <input type="text" name="listemail" id="listemail"/></div>
			<div class="r"><label for="join_listServe">Join the List:</label><input  checked type="radio" name="listserve_radio" id="join_listServe" value="JOIN" /></div>
			<div class="r"></div>
			<div class="r"><label for="leave_listServe">Leave the List:</label><input  type="radio" name="listserve_radio" id="leave_listServe" value="LEAVE" /></div>
			<div class="r"></div>
			<div class="rb" style="text-align:center" id="listButtons">
				<input type="button" value="Submit" onclick="Reg.pListServe();"/>
			</div>	
		</form>
		</fieldset>
	</td>
	<td>
	<app:cshelp topic="Registering" style="padding:_8px;cursor:pointer;"/>
	<br clear="both"/>
	<form id="regForm">
	<fieldset>
	<legend>New Users</legend>
	<br clear="both"/>
	
	<b class="msg">Register for an account to be able to save your queries and organize your workspace</b>
		<br><br>
	
	<div id="regErr" class="mmsg"></div><br/>
	<div><label for="Registration">&nbsp;</label>* required field<input type="hidden" id="registration" value=""/></div>
	<div class="h">Name:</div>
	<div class="r"><label for="firstName">First name*:</label>  <input type="text" id="firstName"/></div>
	<div class="r"><label for="lastName">Last name*:</label>  <input type="text" id="lastName"/></div>
	<div class="h">Contact Information:<input type="hidden" id="contact_info" value=""/></div>
	<div class="r"><label for="email">Email*:</label>  <input type="text" id="email"/></div>
	<div class="r"><label for="contact_info">-<br/><br/></label>Your account information will be sent to this address</div>
	<div class="r"><label for="phone">Phone*:</label><input type="text" id="phone"/></div>
	<div class="r"><label for="institution">Institution*:</label> <input type="text" id="institution"/></div>
	<div class="r"><label for="dept">Department:</label><input type="text" id="dept"/></div>
<!-- 	<div class="r"><label for="listServe">Join Rembrandt User's List Serve?</label><input  type="checkbox" id="listServe"/><br/><br/></div>
	<div class="r"><label>-<br/><br/></br></label>By joining the Rembrandt User's List Serve, you will periodically receive information on application and data updates </div>
-->
	<div class="h">Verification:</div>
	<div class="r"><label for="captcha_image">Image*:</label><img alt="Captcha Image" src="Captcha.jpg"/><input type="hidden" id="captcha_image" value=""/></div>
	<div class="r"><label for="type_text_below">-<br/><br/></label>Please type the text below, displayed in the image above<input type="hidden" id="type_text_below" value=""/></div>
	<div class="rb"><label for="cap">&nbsp;</label><input type="text" id="cap"/></div>

	<div style="text-align:center" id="regButtons">
		<input type="reset" value="Reset"/>
		<input type="button" value="Register" onclick="Reg.pReg();"/>
	</div>
	<div id="regStatus" style="text-align:center;display:none;">
		Processing...<br/><img alt="Processing..." src="images/ajax-bar-loader.gif"/>
	</div>
	</fieldset>
	</form>
<%

//out.println( session.getAttribute(nl.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY));

%>
	</td>
	</tr>
</table>