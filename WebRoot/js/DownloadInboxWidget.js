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
		
		var myTemplate = new Template("<div style='height:25px;'><span style='float:left'><b>#{name}</b></span> <span style='float:right;'>#{status} <img src='#{img}'/></span></div>");
		var show;	//to be applied to the template above, based on a scenario/status
		var areAnyRunning = false; //flag to stop checking it all are complete
		
		if(downloadStatuses == "[]")	{
			$(downloadContainer).update("No Current Downloads in progress");
			DownloadInboxWidgetController.stop();
		}
		else	{
			$(downloadContainer).update("");
		  	//injects UI HTML into the container div
		  	downloadStatuses.evalJSON().each(function(e)	{
		  		if(e.status.toLowerCase().startsWith("comp"))	{
		  			//done, show check img and activate link....could also show file size and duration here.  to do so, mod the template
		  			show = {name: "<a href='"+e.url+"'>"+e.name+"</a>  "+e.size, status: e.status, img: 'images/check.png' };
		  		}
		  		else if(e.status.toLowerCase().startsWith("err"))	{
		  			//done, show check img and activate link....could also show file size and duration here.  to do so, mod the template
		  			show = {name: e.name, status: e.status, img: 'images/error.png' };
		  		}
		  		else if(e.status.toLowerCase().startsWith("nofiles"))	{
		  			//done, show check img and activate link....could also show file size and duration here.  to do so, mod the template
		  			show = {name: e.name, status: e.status, img: 'images/error.png' };
		  		}
		  		else	{
		  			show = {name: e.name, status: e.status, img: 'images/indicator.gif' };
		  			areAnyRunning = true;
		  		}
		  		$(downloadContainer).insert(myTemplate.evaluate(show));
		  		//$(downloadContainer).insert("<div style='height:25px;'><span style='float:left'><b>" + e.name + "</b></span> <span style='float:right;'>" + e.status + "... <img src='images/indicator.gif'/></span></div>");
		  	});
		  	if(areAnyRunning == false)	{
	  			//stop checking since none are still running
	  			//console.log("stopping...");
	  			//note: this get triggered twice.  once on inital call, once on initial start interval
	  			DownloadInboxWidgetController.stop();
		  	}
	  	}
	}
}
