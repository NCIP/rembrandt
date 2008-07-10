//Inbox.js (from DWR) is required
var downloadStatuses; 		//global to hold the JSON status data for each job
var downloadContainer; 		//global to store the id of the container to inject the content into
var checker; 				//global PeriodicExecuter handle, so we can start/stop easily

var DownloadInboxWidget = Class.create({
  initialize: function(container) {
    downloadContainer = container;
    this.update();
  },
  checkAllStatus : function()	{
  	//check status of all avail dls
  	//returns array of objects (JSON):  id, status
  	Inbox.checkAllDownloadStatus(this.checkAllStatus_cb);
  },
  checkAllStatus_cb : function(returnedJSON)	{
  	//get returned JSON
  	downloadStatuses = returnedJSON;
  	DownloadInboxWidgetUI.draw();
  },
  update : function()	{
  	//$(downloadContainer).update("Updating.....");
  	//on demand update
  	this.checkAllStatus();
  }
});

var DownloadInboxWidgetController = {
 	start : function(container, interv)	{
		//$(container).update("updating...");
		new DownloadInboxWidget(container);
	  	//start checking every interv seconds
	  	checker = new PeriodicalExecuter(function() { new DownloadInboxWidget(container)}, interv);
	  },
	stop : function(){
		//stop periodic checking
		checker.stop();
	} 
};

var DownloadInboxWidgetUI = {
	draw : function()	{
		//
		if(downloadStatuses == "[]")	{
			$(downloadContainer).update("No Current Downloads in progress");
		}
		else	{
			$(downloadContainer).update("");
		  	//injects UI HTML into the container div
		  	downloadStatuses.evalJSON().each(function(e)	{
		  		$(downloadContainer).insert("<div style='height:25px;'><span style='float:left'><b>" + e.name + "</b></span> <span style='float:right;'>" + e.status + "... <img src='images/indicator.gif'/></span></div>");
		  	});
	  	}
	}
}
