//Dependencies:  Inbox.js
var QueryDetailHelper = {
	'delay' : 250,
	'getQueryDetails' : function(queryName)	{
		Inbox.getQueryDetailFromCompoundQuery(queryName, QueryDetailHelper.getQueryDetails_cb);
	},
	'getQueryDetails_cb' : function(txt){
		return overlib(txt, CAPTION,  "Query");
	}
};