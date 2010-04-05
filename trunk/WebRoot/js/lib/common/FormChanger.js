	var FormChanger = {
	//this is somewhat specific to the manageList.jsp page, as the onclicks are specific
		'upload2type' : function()	{
			var uploadRow = arguments.length > 0 ? arguments[0] : "uploadRow";
			var textRow = arguments.length > 1 ? arguments[1] : "textRow";
			var uploadButton = arguments.length > 2 ? arguments[2] : "uploadButton";
			
			//hide the upload field and show a text area
			$(uploadRow).style.display = "none";
			$(textRow).style.display = "";
			$(uploadButton).onclick = function() { TextFormList.processTextForm(); };
		},
		'type2upload' : function()	{
			var uploadRow = arguments.length > 0 ? arguments[0] : "uploadRow";
			var textRow = arguments.length > 1 ? arguments[1] : "textRow";
			var uploadButton = arguments.length > 2 ? arguments[2] : "uploadButton";
		
			//hide the textarea field and show a upload
			$(uploadRow).style.display = "";
			$(textRow).style.display = "none";
			
			$(uploadButton).onclick = function()	{ ManageListHelper.validateForm(); };
		}
	};
