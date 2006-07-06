//this class will render type of status messages, ex. a confirmation after an AJAX cb, or an error msg

//JSLoader.include("js/window.js");

var StatusMessage = {
	'showStatus' : function()	{
		//show the message in a fixed location - window overlay, alert, or fixed upper rt corner
		//params for how long to show it, and what type it is, etc
		
		var txt = arguments.length>0 && arguments[0]!="" ? arguments[0] : "Loading...";
		//StatusMessage.showLoading(txt);
		//setTimeout(function() { StatusMessage.hideLoading();}, 5000);
		
		StatusMessage.showInfo(txt);

	},
	'showAlert' : function()	{
		var txt = arguments.length>0 && arguments[0]!="" ? arguments[0] : "Processing...";
		Dialog.alert("<img src=\"images/indicator.gif\"   /> "+txt,  {windowParameters: {className: "alphacube", width:500}, okLabel: "Close"}); 	
	},
	'showInfo' : function()	{
		var txt = arguments.length>0 && arguments[0]!="" ? arguments[0] : "Processing...";
		Dialog.info("<img src=\"images/indicator.gif\"   /> "+txt,  {windowParameters: {className: "alphacube", width:500}, okLabel: "Close"}); 	
		setTimeout(function()	{ Dialog.closeInfo();}, 2000);
		
	},
	'showLoading' : function()	{
		var msg = arguments.length > 0 && arguments[0] != "" ? StatusMessage.messageHTML.replace("Loading...", arguments[0]) : StatusMessage.messageHTML;
		new Insertion.Top(document.getElementsByTagName("body")[0], msg);
	},
	'hideLoading' : function()	{
		Element.remove("StatusMessage_messageDiv");
	},
	'messageHTML' :
	"<div id=\"StatusMessage_messageDiv\" style=\"position:absolute; bottom:0px; background-color:yellow; border:1px solid black;right:0px;width:100px;padding:20px; z-index:999; vertical-align:center;text-align:center;opacity:.80;filter: alpha(opacity=80); -moz-opacity: 0.8;\"> "
	+ "Loading...<br/> "
	+ "<img src=\"images/indicator.gif\"   /> "
	+ "</div>"

};