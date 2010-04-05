function setBackToDefault(formType){
		if(formType == "pca"){
			document.getElementById("variancePercentile").value = "70.0";
		}
		else {document.getElementById("variancePercentile").value = "95.0";}
		document.getElementById("geneList").value = "none";
			
	}
	

function checkNull(text, checkName){
	if(text.value == "")	{
		scroll(0,0);
		text.focus();
		text.style.border = "2px solid red";
		alert("Please Fill in a Unique Query Name");
		return false;
	}
	if (!checkIsURLSafe(text)){
	
		alert("Please enter a query name without any special characters such as !@#$%^&*()+=[]\';,./{}|\":<>?");
		return false;
	}
	if (checkName == "true")
		return checkQueryName();
	else
		return true;
}

function checkIsURLSafe(textField){
	var iChars = "!@#$%^&*()+=[]\';,./{}|\":<>?";
	var text = textField.value;
	
	for (var i = 0; i < text.length; i++) {
		if (iChars.indexOf(text.charAt(i)) != -1) 
		{
			return false;
		}	
	}
	return true;
}

function clearSpecialCharacters(textField) {
	var text = textField.value;

	re = /\$|,|@|#|~|`|\%|\*|\^|\&|\(|\)|\+|\=|\[|\-|\_|\]|\[|\}|\{|\;|\:|\'|\"|\<|\>|\?|\||\\|\!|\$|\./g;
          // remove special characters like "$" and "," etc...
	text = text.replace(re, "");
	textField.value = text;
}

function spawnx(url,winw,winh, name) {

  var w = window.open(url, name,
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");
	
	//check for pop-up blocker
	if (w==null || typeof(w)=="undefined") {
		alert("You have pop-ups blocked.  Please click the highlighted link at the top of this page to view the report.  You may disable your pop-up blocker for this site to avoid doing this in the future.");
		/*
		if(document.all) {
			  document.all.popup.visible = "true"; 
			  document.all.popup.className = "pop";
		      document.all.popup.innerText = "You have pop-ups blocked.  Click <a href=\"javascript:spawnx('"+url+"',"+winw+","+winh+",'"+name+"');\">here</a> to view the report."; 
			  
		    } else { 
			  document.getElementById('popup').visible = "true";
			  document.getElementById('popup').className= "pop";
		      document.getElementById('popup').innerHTML = "You have pop-ups blocked.  Click <a href=\"javascript:spawnx('"+url+"',"+winw+","+winh+",'"+name+"');\">here</a> to view the report."; 	  
		} 
		*/
		document.write("<Br><Br><span class=\"pop\">You have pop-ups blocked.  Click <a href=\"javascript:spawnx('"+url+"',"+winw+","+winh+",'"+name+"');\">here</a> to view the report.</span>");
		//scroll(0, 8000);
	} else {
		w.focus();

	}
} 

function spawn(url,winw,winh) {
  var w = window.open(url, "_blank",
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");
} 



function deleteRow(queryNum){
//get elements body and table
 var mybody=document.getElementsByTagName("body").item(0);
 var mytable=mybody.getElementsByTagName("table").item(0);
 
 //show warning if user has deleted all but one row
 if (mytable.rows.length == 2){
 alert('Deleting any more rows will create an invalid query');
 }
 //delete rows by finding the row id and the row's index
 else {
  var thisTR = document.getElementById(queryNum);
  var thisTRIndex = thisTR.rowIndex;
  mytable.deleteRow(thisTRIndex);
   }
 }

  
 
 function onRadio(formElement,i){
     
   //selected index of the selected
	var element = formElement.name;
	
	 if(element == "tumorType" || formElement == "tumorType"){
	    document.forms[0].tumorGrade.options.length = 1;
	        myOption1 = new Option("I","one");
		    myOption2 = new Option("II","two");
		    myOption3 = new Option("III","three");
		    myOption4 = new Option("IV","four");
		    
		 
		if(i == "ALL"){
		 document.forms[0].tumorGrade.options[1] = myOption1;
	     document.forms[0].tumorGrade.options[2] = myOption2;
	     document.forms[0].tumorGrade.options[3] = myOption3;
	     document.forms[0].tumorGrade.options[4] = myOption4;
	     }    
	    if(i == "ASTROCYTOMA"){
	     document.forms[0].tumorGrade.options[1] = myOption2;
	     document.forms[0].tumorGrade.options[2] = myOption3;
	     }
	    if(i == "GBM"){
	     document.forms[0].tumorGrade.options[1] = myOption4;
	     }
	    if(i == "OLIG"){
	     document.forms[0].tumorGrade.options[1] = myOption2;
	     document.forms[0].tumorGrade.options[2] = myOption3;
	     }
	    if(i == "MIXED"){
	     document.forms[0].tumorGrade.options[1] = myOption1;
	     document.forms[0].tumorGrade.options[2] = myOption2;
	     document.forms[0].tumorGrade.options[3] = myOption3;
	     }
	  }
	
	  if(element == "isAllGenesQuery"){
	   if(i == 0){
	    document.forms[0].allGeneQuery.disabled = true;
	    checkToggle(formElement, "qrows");
	    }	   
	   if(i == 1){
	    document.forms[0].allGeneQuery.disabled = false;
	    checkToggle(formElement, "qrows");
	    }
	   }	
	  
	  if(element == "sampleGroup"){
	    if(i == 0){
	    document.forms[0].sampleFile.disabled = true;
	    document.forms[0].sampleList.disabled = false;
	    }
	    if(i == 1){
	    document.forms[0].sampleList.value = "";
	    document.forms[0].sampleList.disabled = true;
	    document.forms[0].sampleFile.disabled = false;
	    }
	  }
		
	  if(element == "plot"){
	  document.forms[0].quickSearchType.options.length = 1;
	  
	 	document.forms[0].quickSearchType.disabled = false;
	  	document.forms[0].quickSearchName.disabled = false;
	  	document.getElementById("quickSearchName").disabled = false;
	  	document.getElementById("baselineGroupName").disabled = false;
	  	
	  	if(i==0)	{
	  		document.getElementById("baselineGroupName").disabled = true;
	  	}
	  	
	  if(i==3)	{
	  	//disable the fields since this is a sample
	  	document.forms[0].quickSearchType.disabled = true;
	  	document.getElementById("quickSearchName").value = "";
	  	document.getElementById("quickSearchName").disabled = true;
	  	document.getElementById("baselineGroupName").disabled = true;
	  	//document.forms[0].quickSearchName.disabled = true;
	  }
	  else if (i == 2){
	      myOption = new Option();
	      myOption2 = new Option();
	      myOption.text = "Gene Keyword";
		  myOption2.text = "SNP Probe set ID";
	      document.forms[0].quickSearchType.options[0] = myOption;
	      document.forms[0].quickSearchType.options[1] = myOption2;
		  }	
	     else /*if(i != 2)*/ {
	      myOption = new Option();
		  myOption.text = "Gene Keyword";
	      document.forms[0].quickSearchType.options[0] = myOption;
	     }
	   }	
		
	  if(element == "geneGroup"){	
	      if (i == 0){
	      
		  document.forms[0].geneList.disabled = false;
		  document.forms[0].geneType.disabled = false;
		  document.forms[0].geneFile.disabled = true;
		  document.forms[0].geneList.focus();
		  }
	      if (i == 1) {
	      
	      document.forms[0].geneList.value = "";
	      document.forms[0].geneList.disabled = true;
		  document.forms[0].geneFile.disabled = false;
		  document.forms[0].geneFile.focus();
	      }
	   }
	   
	   if(element == "cloneId"){	
	      if (i == 0){
	      document.forms[0].cloneListSpecify.disabled = false;
		  document.forms[0].cloneList.disabled = false;	
		  document.forms[0].cloneListFile.disabled = true;
 		  document.forms[0].cloneListSpecify.focus();
		
		  }
	      if (i == 1) {
	      document.forms[0].cloneListSpecify.value = "";
		  document.forms[0].cloneListSpecify.disabled = true;	    
		  document.forms[0].cloneListFile.disabled = false;
	      //document.forms[0].cloneListFile.focus();
	      }
	   }
	   if(element == "snpId"){
	      if (i == 0){		  
	        document.forms[0].snpListSpecify.disabled = false;			
	        document.forms[0].snpList.disabled = false;
			document.forms[0].snpListSpecify.focus();
			document.forms[0].snpListFile.value = "";	
	        document.forms[0].snpListFile.disabled = true;
	      }
	      if (i == 1){
	        document.forms[0].snpListSpecify.value = "";			
	        document.forms[0].snpListSpecify.disabled = true;	        
	        document.forms[0].snpListFile.disabled = false;
			//document.forms[0].snpListFile.focus();
	      }
	   }
	   
  }

 function alertUser(where){
  
if(confirm("This will eliminate all data currently entered in the form and will not add a query"))
{
	if (where == 'menu')
		location.href='menu.do';
	if (where == 'analysisHome')
		location.href='analysisHome.do';
}

  
 }

function hideLoadingMessage(){
	if(document.getElementById('spnLoading') != null)
			document.getElementById('spnLoading').style.display = "none" ;
}
		

function checkToggle(box, id)	{
	if(box.value == 'false')
		toggleDiv(id, false);
	else
		toggleDiv(id, true);
}

function toggleDiv(id, state)	{
		if(state)
		    document.getElementById(id).style.display = "none";
		else
			document.getElementById(id).style.display = "block";	
}
 

function preMove(test, fbox, tbox)	{

	if(test)	{
		move(fbox, tbox);
	}
	else	{
		//alert("Invalid Selection");	
	}
}

function move(fbox, tbox) {

	var arrFbox = new Array();
	var arrTbox = new Array();
	var arrLookup = new Array();
	var i;
	for(i = 0; i < tbox.options.length; i++) {
		arrLookup[tbox.options[i].text] = tbox.options[i].value;
		arrTbox[i] = tbox.options[i].text;
	}
	var fLength = 0;
	var tLength = arrTbox.length;
	for(i = 0; i < fbox.options.length; i++) {
		arrLookup[fbox.options[i].text] = fbox.options[i].value;
		if (fbox.options[i].selected && fbox.options[i].value != "") {
			arrTbox[tLength] = fbox.options[i].text;
			tLength++;
		}
		else {
			arrFbox[fLength] = fbox.options[i].text;
			fLength++;
		}
	}
	//arrFbox.sort();
	//arrTbox.sort();
	fbox.length = 0;
	tbox.length = 0;
	var c;
	for(c = 0; c < arrFbox.length; c++) {
		var no = new Option();
		no.value = arrLookup[arrFbox[c]];
		no.text = arrFbox[c];
		fbox[c] = no;
	}
	for(c = 0; c < arrTbox.length; c++) {
		var no = new Option();
		no.value = arrLookup[arrTbox[c]];
		no.text = arrTbox[c];
		tbox[c] = no;
	}
}

function saveMe(tbox,fbox) {
	
	var strValues = "";
	
	//if(tbox == null || fbox == null || !tbox.length || !fbox.length){
	
	if(tbox == null || fbox == null || !tbox.length){
		return;
	}
		
	var boxLength = 0;
	if(tbox.length)	{ boxLength = tbox.length; }
	
	var fboxLength = 0;
	if(fbox.length) { fboxLength = fbox.length;}
	
	var count = 0;
	if (boxLength != 0) {
	
		for (i = 0; i < boxLength; i++) {
			if (count == 0) {
				strValues = tbox.options[i].value;
			}
			else {
				strValues = strValues + "," + tbox.options[i].value;
			}
			count++;
		}
	}
	
	var posOfAllSamples=strValues.indexOf("REST OF ALL SAMPLES")
	var posOfAllGliomas=strValues.indexOf("REST OF ALL GLIOMAS")
	
	if (strValues.length == 0) {
		alert("You have not made any selections");
	}
	else if (posOfAllSamples >= 0 && posOfAllGliomas >= 0) {
		alert("You cannot perform class comparison with overlapping groups.  Therefore you cannot select both 'REST OF ALL SAMPLES' AND 'REST OF ALL GLIOMAS'.  Please select one or the other.")
	}
	else {
	
		//alert("Here are your values you've selected:\r\n" + strValues);
		for(i=0; i < boxLength; i++)
		{
		    tbox.options[i].selected = true;
		    
		}
		
	}
}
 
function toggleSDiv(divId,aId){

			var divId = divId;
			var aId1 = aId;
			
			var div = document.getElementById(divId);
			var a = document.getElementById(aId);
			
			
			if(div.className == 'divHide'){
				div.className = 'divShow';
				a.innerHTML = '&nbsp;-&nbsp;';
				}
			else {
				div.className = 'divHide';
				a.innerHTML = '&nbsp;+&nbsp;';
				}
			}

function HelpWrapper(topic)	{
	var ta = "";
	switch(topic)	{
		case  "Clinical":
			ta = "Clinical_report";
			break;
		case "clinicalPlot":
			ta = "Clinical_plot";
			break;
		case "Gene Expression Sample":
			ta = "Gene_Expression_Sample";
			break;
		case "Gene Expression Disease":
			ta = "Gene_Expression_Disease";
			break;
		case "Copy Number":
			ta = "Copy_Number_Sample";
			break;
		default:
			ta= "Clinical";
			break;
	}
	
	try	{
		Help.popHelp(ta);
	}
	catch(err){alert(err);}
}
	   
