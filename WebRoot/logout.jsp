<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld"  prefix="app" %>
<script language="javascript" src="js/lib/scriptaculous/scriptaculous.js"></script>
<app:cshelp topic="Logging_out" />
 <s:form action="logout" theme="simple">
	<fieldset class="gray">
		<legend class="red">
		Thank you for visiting the REMBRANDT application
		</legend>
		<br clear="both"/>
		You cannot save the current session if you are logged in as a guest user (RBTuser).
	<br /><br />
		<input type="radio" name="form.procedure" class="radio" value="logoutSave" />
		Save my current session and logout. 
		<app:help help="This will save all queries and preferences from your current browser session in addition to queries from your previous session(s)." />
		<br />
		
		<input type="radio" name="form.procedure" class="radio" value="logoutNoSave" />
		Do not save my current session and logout. 
		<app:help help="This will not save any queries or preferences from your current browser session." />
		<br/>
		
		<input type="radio" name="form.procedure" class="radio" value="dontLogout" />		
		Continue working in the application and do not logout. 
		
		<br /><br />
		<div id="surveyHeader" style="text-align:center; background-color:silver">
			<br/><a href="#" onclick="Effect.toggle('survey');return false;">Click Here to take our quick feedback survey...</a><br/><br/>
		</div>
		<div id="survey" style="display:none;border:2px dotted silver;border-top:1px solid silver;">
			<div style="margin:10px;">
			The feature I used/liked the most this session: 
			<select id="usedMost" name="form.usedMost">
				<option value="">N/A</option>
			<select>
			<br/><br/>
			The feature I used/liked the least this session: 
			<select id="usedLeast" name="form.usedLeast">
				<option value="">N/A</option>
			<select>
			<br/><br/>
			General Feedback:<br/>
			<textarea style="width:90%" name="form.generalFeedback"></textarea>
			<br/>
			We appreciate your input.
			</div>
		</div>
		<br/>
		<div align="center">
		<s:submit styleClass="xbutton" />
		</div>
   </fieldset>
</s:form>

<script language="javascript">
	
	var popFeats = function(fs)	{
		var feats = eval('(' + fs + ')');
		if(feats.length>1)	{
			for(var f=0; f<feats.length; f++)	{
				$("usedMost").options[f+1] = new Option(feats[f],feats[f]);
				$("usedLeast").options[f+1] = new Option(feats[f],feats[f]);
			}
		}
	}
	DynamicListHelper.getRBTFeatures(popFeats);

</script>


