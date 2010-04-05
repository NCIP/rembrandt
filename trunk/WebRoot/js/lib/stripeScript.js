 var Stripe = {
 
  // this function is needed to work around 
  // a bug in IE related to element attributes
  hasClass : function (obj) {
     var result = false;
     if (obj.getAttributeNode("class") != null) {
         result = obj.getAttributeNode("class").value;
     }
     return result;
  },   

 stripe : function(id) {

    // the flag we'll use to keep track of 
    // whether the current row is odd or even
    var even = false;
  
    // if arguments are provided to specify the colours
    // of the even & odd rows, then use the them;
    // otherwise use the following defaults:
    var evenColor = arguments[1] ? arguments[1] : "#fff";
    var oddColor = arguments[2] ? arguments[2] : "#eee";
  
    // obtain a reference to the desired table
    // if no such table exists, abort
    var table = document.getElementById(id);
    if (! table) { return; }
    
    // by definition, tables can have more than one tbody
    // element, so we'll have to get the list of child
    // &lt;tbody&gt;s 
	var tbodies = table.getElementsByTagName("tbody");

	//what if theres no tbody's?.. RCL
	if(tbodies.length < 1)	{
		tbodies[1] = table;
	}
	
    // and iterate through them...
    for (var h = 0; h < tbodies.length; h++) {
    
     // find all the &lt;tr&gt; elements... 
      var trs = tbodies[h].getElementsByTagName("tr");
      
      // ... and iterate through them
      for (var i = 0; i < trs.length; i++) {
 
        // avoid rows that have a class attribute
        // or backgroundColor style
//        if (! this.hasClass(trs[i]) && ! trs[i].style.backgroundColor) {
 		if (! trs[i].style.backgroundColor) {
          // get all the cells in this row...
          var tds = trs[i].getElementsByTagName("td");
        
        //add the mouseovers and outs... RCL
			if(i>0)	{
				trs[i].setAttribute("onmouseover", "Stripe.highlight(this);");
				trs[i].setAttribute("onmouseout", "Stripe.unhighlight(this);");
			}
            
          // and iterate through them...
          for (var j = 0; j < tds.length; j++) {
        
            var mytd = tds[j];

            // avoid cells that have a class attribute
            // or backgroundColor style
            if (! this.hasClass(mytd) && ! mytd.style.backgroundColor) {
        
              mytd.style.backgroundColor = even ? evenColor : oddColor;
            
            }
          }
        }
        // flip from odd to even, or vice-versa
        even =  ! even;
      }
    }
  },
  
  highlight : function(item)	{
	item.style.color = "red";
  },
  
  unhighlight : function(item)	{
	item.style.color  = "";
  }
  
  };