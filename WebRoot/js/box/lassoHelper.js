/*
//include other JS files
function js_include(url)	{
	var t = '<scr'+ 'ipt language="javascript" src="'+ url +'" type="text\/javascript"><\/scr'+ 'ipt>';
	document.write(t);
    return 1;
} 
function js_import(url)	{
	js_include(url);
}
*/

/*
//some globals for the marker
var marker, markersrc, markersize;
var coordx   = new Array();
var coordy   = new Array();

if (ie) {
	markersrc = "images/marker.gif";
} else {
	markersrc = "images/marker.png";
}

markersize = 32;
*/

//saved sample management - incorporate AJAX here
//hold our pending samples
var pendingSamples = new Array();
		
function addToPending(sample)	{
	if(!pendingSamples.inArray(sample))	{
		pendingSamples[pendingSamples.length] = sample;
	}
}		
function clearPending()	{
	pendingSamples = new Array();
	writePendings();
}		
function writePendings()	{
	var html = "";	
	document.getElementById("pending_samples").innerHTML = "";
	if(pendingSamples.length>0)	{
		for(var j=0; j<pendingSamples.length; j++)	{
				html += "<a href=\"#\" onmouseover=\"mapshow('"+pendingSamples[j]+"');return overlib('Some cool annotations about:<br>\\n "+pendingSamples[j]+"');\" onmouseout=\"maphide();return nd();\">"+ pendingSamples[j] + "</a><br/>\n";
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

//Create the dBox object (initialize a few things)
var main = new dBox("geneChart");
main.box = true;
main.color = "yellow";
main.verbose = true;
		


//implement some methods	
function setbox_handler(name, minx, miny, maxx, maxy) {
	// document.mapserv.imgbox.value = minx + " " + miny + " " + maxx + " " + maxy;
	// document.mapserv.imgxy.value = minx + " " + miny;
	// document.mapserv.submit();

	//if(minx != maxx && miny != maxy)
		//alert("Your box: " + minx +", "+miny + ", "+maxx+", "+maxy);
   		//alert(name); //which lasso, since we now have 3
  
	didSelectAnything(name, minx, miny, maxx, maxy);
}

function seterror_handler(message) {
   alert(message);
}

function reset_handler(name, minx, miny, maxx, maxy) { 
	//some thing goes here      
}

/*
//kickoff onload
function startup() {
	alert("yesss");
   // Step 4: Initialize the dBox object
   main.initialize();
   //main2.initialize(); //for second lasso
}
*/
  
//some basic debugging 
var DEBUG = true;
function debug(txt)	{
	if(DEBUG)
		alert(txt);
}

//did we snag anything?
function didSelectAnything(name, minx, miny, maxx, maxy)	{

	var gotem = "";
	
	//get the map
	var theMap = document.getElementsByTagName("map");
	
	//alert(mapMap[name]);
	
	//get the areas
	//var theAreas = theMap[mapMap[name]].getElementsByTagName("area");
	var theAreas = theMap[0].getElementsByTagName("area");
	
	//look at each area
	for(var i=0; i<theAreas.length; i++)	{
		//parse the coords
		var _minx, _miny, _maxx, _maxy;
		var s = theAreas[i].coords.replace(" ", "").split(",");
		//so there could be N coords, unless we force Jfreechart to use a rect..which we do
		
		if(s[0] >= minx && s[1] >= miny && s[2] <= maxx && s[3] <= maxy)	{
			//we have lassoed this point
			gotem += theAreas[i].title + "\n";
			//alert("lasso has: " + theAreas[i].title);			
			//write this to the pending list
			addToPending(theAreas[i].title);
		}
	}
	if(gotem != "")	{
		alert("lasso has: \n" + gotem);
		writePendings();
	}
}
	

/*	
//getting our map names, for 1+ maps per page
var mapNames = new Array();
var mapMap = new Object();

function getSetMapNames()	{
	var theMap = document.getElementsByTagName("map");
	for(var i=0; i<theMap.length; i++)	{
		mapNames[i] = theMap[i].name;
	}
	
	var imgs = document.getElementsByTagName("img");
	for(var i=0; i<imgs.length; i++)	{
		alert(imgs[i].useMap);
		if(imgs[i].useMap != '')	{
			mapMap[imgs[i].name] = imgs[i].useMap.substring(1, imgs[i].useMap.length);
		}
	}
}
*/  

//functions for marker display ... assumes exactly 1 map per page
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
		
		//convert to ints for comparisons
		for(var eachs = 0; eachs<s.length; eachs++)	{
			s[eachs] = parseInt(s[eachs]);
		}
		
		//get the center point
		coordx[theAreas[i].title] = Math.ceil(s[0] + ((s[2]-s[0])/2));
		coordy[theAreas[i].title] = Math.ceil(s[1] + ((s[3]-s[1])/2));
	}
}

//init the marker points...call this onload
//initMarkerPoints();

//note:  must use "the_marker"...hard coded in bdox.js when i put this part in
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
