var marker, markersrc, markersize;
var coordx   = new Array();
var coordy   = new Array();

if (ie) {
	markersrc = "images/marker.gif";
} else if(saf) {
	markersrc = "images/blank.gif";
} else {
	markersrc = "images/marker.png";
}


markersize = 32;

//includes
//dwr (3)
//a_saveSamples
//x_core, x_event, x_dom, x_drag
//wz_jsgraphics
//dbox
//overlib


//hold our pending samples
var pendingSamples = new Array();
		
function addToPending(sample)	{
	if((saf && !safInArray(pendingSamples, sample)) || !pendingSamples.inArray(sample))	{
		//add this to the JS array
		pendingSamples[pendingSamples.length] = sample;
		//add to array list
		A_saveTmpSample(sample);
	}
}		
function clearPending()	{
	//clear the JS array
	pendingSamples = new Array();
	//clear the array list and a_js arrays
	A_clearTmpSamples();
	writePendings();
}		

function writePendings()	{
	var html = "";	
	document.getElementById("pending_samples").innerHTML = "";
	if(pendingSamples.length>0)	{
		for(var j=0; j<pendingSamples.length; j++)	{
				html += "<span style=\"margin-left:5px;\">\n";
				html += "<a href=\"#\" onmouseover=\"mapshow('"+pendingSamples[j]+"');return overlib('Sample:<br>\\n "+pendingSamples[j]+"');\" onmouseout=\"maphide();return nd();\">"+ pendingSamples[j] + "</a><br/>\n";
				html += "</span>";
		}
	}
	//html = pendingSamples;	
	document.getElementById("pending_samples").innerHTML = html;
}
		
		
/* http://www.embimedia.com/resources/labs/js-inarray.html */
Array.prototype.inArray = function (value)	{
	// Returns true if the passed value is found in the
	// array.  Returns false if it is not.
	var i;
	for (i=0; i < this.length; i++) {
		// Matches identical (===), not just similar (==).
		if (this[i] == value) {
			return true;
		}
	}
	return false;
};

//saf doesnt like the above inArray
function safInArray(ar, value)	{
	var i;
	for (i=0; i < ar.length; i++) {
		// Matches identical (===), not just similar (==).
		if (ar[i] == value) {
			return true;
		}
	}
	return false;
}

//Create the dBox object (initialize a few things)
var main = new dBox("geneChart");
main.box = true;
main.color = "red";
main.verbose = true;
//main.thickness = Stroke.DOTTED;
		
//implement some methods	
function setbox_handler(name, minx, miny, maxx, maxy) {  
	didSelectAnything(name, minx, miny, maxx, maxy);
}

function seterror_handler(message) {
   alert(message);
}

function reset_handler(name, minx, miny, maxx, maxy) { 
	//some thing goes here      
}

function startup() {
	//Initialize the dBox object
	main.initialize();
}



	var DEBUG = true;
	
	function debug(txt)	{
		if(DEBUG)
			alert(txt);
	}
	
	function didSelectAnything(name, minx, miny, maxx, maxy)	{
	
		var gotem = "";
		
		//get the map
		var theMap = document.getElementsByTagName("map");
		
		//get the areas
		var theAreas = theMap[0].getElementsByTagName("area");

		//look at each area
		for(var i=0; i<theAreas.length; i++)	{
			//parse the coords
			var _minx, _miny, _maxx, _maxy;
			var s = theAreas[i].coords.replace(" ", "").split(",");
			//so there could be N coords, unless we force Jfreechart to use a square
			
			if(s[0] >= minx && s[1] >= miny && s[2] <= maxx && s[3] <= maxy)	{
				//we have lassoed this point
				gotem += theAreas[i].title + "\n";
				
				//RCL - write this to the pending list
				//addToPending(theAreas[i].title);
				addToPending(theAreas[i].id);
			}
			//alert(theAreas[i].coords);
		}
		if(gotem != "")	{
			//alert("lasso has: \n" + gotem);
			writePendings();
		}
	
	}
	
	var mapNames = new Array();
	var mapMap = new Object();
	
	function getSetMapNames()	{
		var theMap = document.getElementsByTagName("map");
		for(var i=0; i<theMap.length; i++)	{
			mapNames[i] = theMap[i].name;
		}
		
		var imgs = document.getElementsByTagName("img");
		for(var i=0; i<imgs.length; i++)	{
			if(imgs[i].useMap != '')	{
				mapMap[imgs[i].name] = imgs[i].useMap.substring(1, imgs[i].useMap.length);
			}
		}		
	}
	
	

function initMarkerPoints()	{
	//get the map
	var theMap = document.getElementsByTagName("map");
	
	//get the areas
	var theAreas = theMap[0].getElementsByTagName("area");
	//look at each area
	for(var i=0; i<theAreas.length; i++)	{
		//parse the coords
		var _minx, _miny, _maxx, _maxy;
		var s = theAreas[i].coords.replace(" ", "").split(",");
		//so there could be N coords, unless we force Jfreechart to use a square
		
		//convert to ints for comparisons
		for(var eachs = 0; eachs<s.length; eachs++)	{
			s[eachs] = parseInt(s[eachs]);
		}
		
		//get the center point...based on id now not title
		//coordx[theAreas[i].title] = Math.ceil(s[0] + ((s[2]-s[0])/2));
		//coordy[theAreas[i].title] = Math.ceil(s[1] + ((s[3]-s[1])/2));
		coordx[theAreas[i].id] = Math.ceil(s[0] + ((s[2]-s[0])/2));
		coordy[theAreas[i].id] = Math.ceil(s[1] + ((s[3]-s[1])/2));
	}
}

initMarkerPoints();

function maphide () {
	var _marker = document.getElementById("the_marker");
    _marker.style.display = "none";
  }
  
function mapshow (city) {
	main.reset();
    var offset = 0 - (markersize/2);
    var x = coordx[city] + offset;
    var y = coordy[city] + offset;
    var _marker = document.getElementById("the_marker");
    _marker.style.left = x + "px";
    _marker.style.top = y + "px";
    _marker.style.display = "block";
}
  

startup();
A_initSaveSample();


