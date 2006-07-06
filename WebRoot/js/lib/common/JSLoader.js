//must be a seperate JS file...not included inline
var JSLoader = {
	require: function(libraryName) {
		// borrowed from Script.acu.lous
		// inserting via DOM fails in Safari 2.0, so brute force approach
		document.write('<script type="text/javascript" src="'+libraryName+'"></script>');
	},
	include: function(libraryName)	{
		JSLoader.require(libraryName);
	}
};