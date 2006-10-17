//Dependencies:  Inbox.js
var QueryDetailHelper = {
	'delay' : 250,
	'getQueryDetails' : function(queryName)	{
		Inbox.getQueryDetailFromCompoundQuery(QueryDetailHelper.getQueryDetails_cb, queryName);
	},
	'getQueryDetails_cb' : function(txt){
		return overlib(txt, CAPTION,  "Query");
	}
};