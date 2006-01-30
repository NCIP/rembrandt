function checkNull(text)
{
	if(text.value == "")	{
		scroll(0,0);
		text.focus();
		text.style.border = "2px solid red";
		alert("Please Fill in a Unique Query Name");
		return false;
	}
	else	{
		return checkQueryName();
	}
}

function checkAnalysisNull(Nametext,Grouptext)
{
	if(Grouptext.options.length == 0){
		alert("Please select 2 groups");
		return false;
	}
	if(Nametext.value == "")	{
		scroll(0,0);
		alert("Please name Analysis Result");
		return false;
	}
	
	else	{
		//return checkQueryName();
	}
}

function cRadio(field, radio)
{
	
	if(field.value == "")
		radio.checked = false;
}

function resetVal(formElement)
{
    var element = formElement.name;
    if ((element == "operatorType1") || (element == "operatorType2")){
	document.getElementById("queryText").value = "";
	}
	if (element == "pathways"){
	document.forms[0].pathways.value = "";	
	}
}

function formNewTargetSimple(windowName, winw, winh)
{
	var d = new Date();
	var stamp = "";
	stamp = d.getDate().toString()+(d.getMonth()+1).toString()+d.getFullYear().toString()+d.getHours().toString()+d.getMinutes().toString()+d.getSeconds().toString();

	//this line enables multi view
	windowName += stamp;

	spawnx("", winw, winh, windowName);
	document.forms[0].target = windowName;
	return true;
}

function formNewTarget(windowName, winw, winh)
{
	if(document.forms[0].queryName.value == "")	{
		scroll(0,0);
		document.forms[0].queryName.focus();
		document.forms[0].queryName.style.border = "2px solid red";
		alert("Please Fill in a Unique Query Name");
		return false;
	}
	else	{
		spawnx("", winw, winh, windowName);
		document.forms[0].target = windowName;
		return true;
	}
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

function checkForm(){
 if(document.forms[0].queryName != null){
 document.forms[0].queryName.focus();
 }
 if(document.forms[0].tumorType != null){ 
 onRadio("tumorType",(document.forms[0].tumorType.options.value));
 }
}

function setQuery(txt)	{
	document.forms[0].query.value = txt;
}

function setDispMethod(txt)	{
	
	document.forms[0].method.value = txt;

	if(txt == "displayresult")
		formNewTargetSimple('_report', 770, 550);
	else
		document.forms[0].target = "_self";

    document.forms[0].submit();
}

function selAndSubmit(txt)	{
	// Select the values in the sampleListTo combobox
	var numOptions = document.forms[0].listTo.options.length;
	
	if (numOptions > 0) {
		for(var w=0; w<numOptions; w++)	{
			document.forms[0].listTo.options[w].selected = true;
		}
		document.forms[0].method.value = txt;
    	document.forms[0].submit();
	}else {
		alert("Please select at least one column that you would like to see in your report !!");
	}
}


function moveEm()	{
	 // alert(document.forms[0].category.selectedIndex);
	  var category = document.forms[0].category.selectedIndex;
 	
	/*
	for(var w=0; w<document.forms[0].delement.options.length; w++)	{
	document.forms[0].delement.options[w] = null;
	}
	*/
		document.forms[0].delement.options.length = 1;
		//document.forms[0].operator.options.length = 1;
	
	if(category == "1")	{
		//gene ex  
		var geneEx = new Array("Gene Name", "Fold Change", "Clone ID", "GO", "Pathway", "Assay Platform", "Clone Location 5\' UTR", "Clone Location 3\' UTR"); 
		for(var t=0;t<geneEx.length; t++)	{
		  myOption = new Option();
		  myOption.text = geneEx[t];
		  document.forms[0].delement.options[document.forms[0].delement.options.length] = myOption;
		}
		
	}
	if(category == "2")	{
	
		var genomic = new Array("Gene Name", "Chrom. Aber. Status", "Chrom. No.", "Chrom. Name", "Cytoband", "Clone ID", "Chrom. Features", "SNP ID", "Assay Platform", "Allele Freq.");
		for(var z=0; z<genomic.length; z++)	{
		 genOption = new Option();
		   genOption.text = genomic[z];
		   document.forms[0].delement.options[document.forms[0].delement.options.length] = genOption;
		}
		
	}
	  
}
	  
function moveEmOp()	{
	
	var category = document.forms[0].category.selectedIndex;
	var de = document.forms[0].delement.selectedIndex;
	
	document.forms[0].operator.options.length = 1;
		
	if(category == "1" && de == "1")	{
		//gene Ex and Gene Name
		var geneExOp = new Array("=", "contains", "!=");
		for(var a=0; a<geneExOp.length; a++)	{
			myOpOption = new Option();
			myOpOption.text = geneExOp[a];
			document.forms[0].operator.options[document.forms[0].operator.options.length] = myOpOption;
		}
	
	}
	
	else if(category == "1" && de == "2")	{
		//gene Ex and Fold Change
		var geneExOp = new Array("=", "<=", ">=");
		for(var a=0; a<geneExOp.length; a++)	{
			myOpOption = new Option();
			myOpOption.text = geneExOp[a];
			document.forms[0].operator.options[document.forms[0].operator.options.length] = myOpOption;
		}
	
	}
	
	else if(category == "2" && de == "5")	{
		//gene Ex and Fold Change
		var geneExOp = new Array("=", "!=");
		for(var a=0; a<geneExOp.length; a++)	{
			myOpOption = new Option();
			myOpOption.text = geneExOp[a];
			document.forms[0].operator.options[document.forms[0].operator.options.length] = myOpOption;
		}
	
	}
	
	else if(category == "2" && de == "2")	{
		//gene Ex and Fold Change
		var geneExOp = new Array("is", "is not");
		for(var a=0; a<geneExOp.length; a++)	{
			myOpOption = new Option();
			myOpOption.text = geneExOp[a];
			document.forms[0].operator.options[document.forms[0].operator.options.length] = myOpOption;
		}
	
	}

}
	 
	 
function changeBackground(num, obj) {
   tableRow = document.getElementById("row" + num);
   tableRow.style.backgroundColor = (obj.checked) ? "#A8A8A8" : "#E6E6E6";
}



function moveEm(formElement)	{	

	//selected index of the selected
	var element = formElement.name;
		//alert(element);
	
//	var tt = document.forms[0].tumorType.selectedIndex;
	var ds = document.forms[0].dataSet.selectedIndex;
	var gi = document.forms[0].generatingInstitution.selectedIndex;
			
/*
	if(element == "tumorType")	{
		//reset the dropdown for dataset	
		document.forms[0].dataSet.options.length = 1;
		//tumor type was moved, switch on tt
		switch(tt)	{
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				var dataSet = new Array("Rembrandt (GMDI)", "other");
			break;
			case 10:
				var dataSet = new Array("I-SPY", "other");
			break;
			default:
				var dataSet = new Array("");
		}
		//tumor type box has changed, so update data type
		for(var a=0; a<dataSet.length; a++)	{
			myOption = new Option();
			myOption.text = dataSet[a];
			myOption.value = dataSet[a];
			document.forms[0].dataSet.options[document.forms[0].dataSet.options.length] = myOption;
		}
		//reset generating instit as well since tumor type has changed
		document.forms[0].generatingInstitution.options.length=1;
		
	}
*/
	if(element == "dataSet")	{
		//reset the dropdown for gen inst	
		document.forms[0].generatingInstitution.options.length = 1;
		//data set was moved, switch on ds
		switch(ds)	{
			//case 1:
			/*
				switch(tt)	{
					case 1: //brain tt
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
						var genInst = new Array("all", "UCSF", "NCI", "Johns Hopkins", "MDACC", "other");
						break;
					case 10: //breast tt
						var genInst = new Array("all", "UNC", "NCI", "other");
						break;
					default:
						var genInst = new Array("all", "NCI","other");
				}
			*/
				case 1:
						var genInst = new Array("All", "UCSF", "NCI", "Johns Hopkins", "MDACC", "other");
						break;
				case 2:
						var genInst = new Array("All", "UNC", "NCI", "other");
						break;
			default:
				var genInst = new Array("All", "NCI","other");
		}
		//data set box has changed, so update gen inst
		for(var a=0; a<genInst.length; a++)	{
			myOption = new Option();
			myOption.text = genInst[a];
			myOption.value = genInst[a];
			document.forms[0].generatingInstitution.options[document.forms[0].generatingInstitution.options.length] = myOption;
		}
		
		//default to all
		document.forms[0].generatingInstitution.selectedIndex = 1;
	}
	
}


function showName(item, box)	{
/*
	var t = item.selectedIndex;
	var r =	item.options[t].value;

	box.value=r;
*/
/*
	var t = item.selectedIndex;
	var r = item.options[t].value;
	box.innerHTML = "<b class='message'>"+r+"</b>";
*/
}

function selectToolTip(type)	{
			var theText = "Select a tumor type";
			var el = type.selectedIndex;
			var txt = type.options[type.selectedIndex].text;
			//alert(document.forms[0].tumorType.options[document.forms[0].tumorType.selectedIndex].text);
			switch(el)
			{
				case 1:
					theText = "<ol><li>Astrocytoma (WHO grade II)" +
							"<ol><li>Variants: protoplasmic, gemistocytic, fibrillary, mixed </ol> " +
							"<li>Anaplastic (malignant) astrocytoma (WHO grade III)" +
							"<li>Glioblastoma multiforme (WHO grade IV)" +
							"<li>Pilocytic astrocytoma (non-invasive, WHO grade I)" +
							"<li>Subependymal giant cell astrocytoma (non-invasive, WHO grade I)" +
							"<li>Pleomorphic xanthoastrocytoma (non-invasive, WHO grade I)</ol> ";

					break;
				case 2:
					theText = "<ol><li>Oligodendroglioma (WHO grade II) " +
							"<li>Anaplastic (malignant) oligodendroglioma (WHO grade III) </ol>";
					break;
				case 3:
					theText = "<ol><li>Ependymoma (WHO grade II) " +
						"<li>Anaplastic ependymoma (WHO grade III) " +
						"<li>Myxopapillary ependymoma " +
						"<li>Subependymoma (WHO grade I) </ol>";
					break;
				case 4:
					theText = "<ol><li>Mixed oligoastrocytoma (WHO grade II) " +
							"<li>Anaplastic (malignant) oligoastrocytoma (WHO grade III) " +
							"<li>Others (e.g. ependymo-astrocytomas) </ol>";
					break;
				case 5:
					theText = "<ol><li>Polar spongioblastoma (WHO grade IV) " +
						"<li>Astroblastoma (WHO grade IV) " +
						"<li>Gliomatosis cerebri (WHO grade IV) </ol>";
					break;
				case 6:
					theText = "<ol><li>Choroid plexus papilloma " +
						"<li>Choroid plexus carcinoma (anaplastic choroid plexus papilloma) </ol>";
					break;
				case 7:
					theText = "<ol><li>Gangliocytoma " +
						"<li>Dysplastic gangliocytoma of cerebellum (Lhermitte-Duclos)  " +
						"<li>Ganglioglioma  " +
						"<li>Anaplastic (malignant) ganglioglioma  " +
						"<li>Desmoplastic infantile ganglioglioma  " +
						"<li>Central neurocytoma  " +
						"<li>Dysembryoplastic neuroepithelial tumor  " +
						"<li>Olfactory neuroblastoma (esthesioneuroblastoma) </ol>";
				break;
				case 8:
					theText = "<ol><li>Pineocytoma "+
						"<li>Pineoblastoma " +
						"<li>Mixed pineocytoma/pineoblastoma </ol>";
				break;
				case 9:
					theText = "<ol><li>Medulloepithelioma "+
						"<li>Primitive neuroectodermal tumors with multipotent differentiation"+ 
						"<li>Neuroblastoma "+
						"<li>Retinoblastoma "+
						"<li>Ependymoblastoma </ol>";
				break;
				case 10:
					theText = "Glioblastoma";
				break;

				default:
					theText = "Select a tumor type to see its sub-types";
					break;
			
			
			}
			return overlib(theText, CAPTION, txt+' Tumor Sub-types', WIDTH, 300);
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
 
 
function showQueryDetail(selectName){
     //get id from current select box
	 var currentSelect = document.getElementById(selectName);
	 //find the current selected index of the select box
	 var currentQuery = currentSelect.selectedIndex;
	 //depending on selected index, perform switch to show query detail pages
	switch(currentQuery){
	  case 1:
	    spawn("egfr_up.html",600,300);
		break;
	  case 2:
	    spawn("egfr_amp.html",600,300);
		break;
	  case 3:
	   spawn("other.html",600,300);
		break;
	 default:
	    alert('please choose a query');
		break;
	 }
 }
 
 function addRow(queryNum){
  var thisTR = document.all.rosso.insertRow();
  var getTRIndex = thisTR.rowIndex;
  /*var createTD1 = document.all.rosso.rows[getTRIndex].insertCell();
  var createTD2 = document.all.rosso.rows[getTRIndex].insertCell();
  var createTD3 = document.all.rosso.rows[getTRIndex].insertCell();
  var createTD4 = document.all.rosso.rows[getTRIndex].insertCell();
  var createTD5 = document.all.rosso.rows[getTRIndex].insertCell();
  var createTD6 = document.all.rosso.rows[getTRIndex].insertCell();*/  
  var findTR = document.getElementById(queryNum);
  var CloneNode = findTR.cloneNode(true);
  var newTR = thisTR.insertBefore(CloneNode);
  /*var getTRIndex = newTR.rowIndex;
  newTR.setAttribute('id','test2'); 
  alert(newTR.rowIndex);
  alert(newTR.getAttribute('id'));
  alert(newTR.hasChildNodes());
  alert(newTR.childNodes.length);
  newTR.childNodes(3).setAttribute('id','q4');
  alert(newTR.childNodes(3).getAttribute('id')); */
  return newTR;
    
  }
  
  function showName2(queryNum)	{
    
    addRow(queryNum);
	alert(newTR);
	
    //var t = item3.selectedIndex;
	//var r = item3.options[t].value;
	//var r = "hello";
	
	//queryNum.childNodes(3).innerHTML = "<b class='message'>"+r+"</b>";
    }
  
  function changeList(formElement)	{	

	//selected index of the selected
	var element = formElement.name;
		//alert(element);
	
	  	
	if(element == "chrosomeNumber")	{
	    var cn = document.forms[0].chrosomeNumber.selectedIndex;
	    //var cr = document.forms[0].cytobandRegion.selectedIndex;
		//reset the dropdown for cytobandRegion	
		document.forms[0].cytobandRegion.options.length = 1;
		//tumor type was moved, switch on cr
		switch(cn)	{
			
			case 1:
			    
				var cytoRegion = new Array("p36.33","p36.32","p36.31","p36.23",
				                           "p36.22","p36.21","p36.13","p36.12",
				                           "p36.11","p35.3","p35.2","p35.1",
				                           "p34.3","p34.2","p34.1","p33",
				                           "p32.3","p32.2","p32.1",
				                           "p31.3", "p31.2","p31.1","p22.3",
				                           "p22.2","p22.2","p22.1","p21.3",
				                           "p21.2","p21.1","p13.3","p13.2",
				                           "p13.1","p12","p11.2","p11.1",
				                           "q11","q12","q21.3","q21.2",
				                            "q21.1","q22","q23.1","q23.2",
				                            "q23.3","q24.1","q24.2","q24.3",
				                            "q25.1","q25.2","q25.3","q31.1",
				                            "q31.2","q31.3","q32.1","q32.2",
				                           "q32.3","q41","q42.11","q42.12",
				                            "q42.13","q42.2","q42.3","q43",
				                            "q44");
			   break;
			case 2:
			    var cytoRegion = new Array("p25.3","p25.2","p25.1");
			    break;
			case 3:
			    var cytoRegion = new Array("p26.3","p26.2","p26.1");
			    break;
			case 4:
			    var cytoRegion = new Array("p16.3","p16.2","p16.1");
			    break;
			case 5:
			    var cytoRegion = new Array("p15.33","p15.32","p15.31");
			    break;
			case 6:
			    var cytoRegion = new Array("p25.3","p25.2","p25.1");
			    break;
			case 7:
			    var cytoRegion = new Array("p22.3","p22.2","p22.1");
			    break;    
			case 8:
			    var cytoRegion = new Array("p23.3","p23.2","p23.1");
			    break;
			case 9:
			    var cytoRegion = new Array("p24.3","p24.2","p24.1");
			    break;
			case 10:
			    var cytoRegion = new Array("p15.3","p15.2","p15.1");
			    break;
			case 11:
			    var cytoRegion = new Array("p15.5","p15.4","p15.3");
			    break;
			case 12:
			    var cytoRegion = new Array("p13.33","p13.32","p13.31");
			    break;
			case 13:
			    var cytoRegion = new Array("p13","p12","p11.2");
			    break;
			case 14:
			    var cytoRegion = new Array("p13","p12","p11.2");
			    break;  
			case 15:
			    var cytoRegion = new Array("p13","p12","p11.2");
			    break; 
		    case 16:
			    var cytoRegion = new Array("p13.13","p13.12","p13.11");
			    break; 
			case 17:
			    var cytoRegion = new Array("p13.3","p13.2","p13.1");
			    break; 
			case 18:
			    var cytoRegion = new Array("p11.23","p11.22","p11.21");
			    break; 
			case 19:
			    var cytoRegion = new Array("p13.13","p13.12","p13.11");
			    break; 
			case 20:
			    var cytoRegion = new Array("p12.3","p12.2","p12.1");
			    break; 
			case 21:
			    var cytoRegion = new Array("p13","p12","p11.2");
			    break; 			    
			case 22:
			    var cytoRegion = new Array("p13","p12","p11.2");
			    break; 
			case 23:
			    var cytoRegion = new Array("p22.33","p22.32","p22.31");
			    break; 
			case 24:
			    var cytoRegion = new Array("p11.32","p11.31","p11.2");
			    break; 
			    
			default:
				var cytoRegion = new Array("");
		}
		//chrosomeNumber box has changed, so update cytoband Region
		for(var i=0; i<cytoRegion.length; i++)	{
			myOption = new Option();
			myOption.text = cytoRegion[i];
			myOption.value = cytoRegion[i];
			document.forms[0].cytobandRegion.options[document.forms[0].cytobandRegion.options.length] = myOption;
		}
	 }
	 
	 if(element == "dataSet"){
	 //alert("you got here");
	 var ds = document.forms[1].dataSet.selectedIndex;
	 
	 document.forms[1].generatingInstitution.options.length = 1;
	 //alert("you got here");
	    switch(ds)	{
			
			case 1: var genInst = new Array("NCI", "Johns Hopkins University", "UCSF");
			      break;
			case 2: var genInst = new Array("");
			      break;
			default:
				var genInst = new Array("NCI", "Johns Hopkins University", "UCSF");
		}
	 //dataSet box has changed, so update generating Inst.
		for(var i=0; i<genInst.length; i++)	{
			myOption = new Option();
			myOption.text = genInst[i];
			myOption.value = genInst[i];
			document.forms[1].generatingInstitution.options[document.forms[1].generatingInstitution.options.length] = myOption;
		}	
		
	}
  }
 
 function onRadio(formElement,i){
     
   //selected index of the selected
	var element = formElement.name;
	
	  if(element == "groupsOption"){
	  	if (i == "allSamples"){
	  		document.getElementById("button1").disabled = true;
	  		document.getElementById("button2").disabled = true; 		
	  	}
	  	else if(i == "variousSamples"){
	  		document.getElementById("button1").disabled = false;
	  		document.getElementById("button2").disabled = false; 	
	  	}
	  }
	  
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
	  
	     if (i == 2){
	      myOption = new Option();
	      myOption2 = new Option();
	      myOption.text = "Gene Keyword";
		  myOption2.text = "SNP Probe set ID";
	      document.forms[0].quickSearchType.options[0] = myOption;
	      document.forms[0].quickSearchType.options[1] = myOption2;
		  }	
	     else if(i != 2){
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
	      document.forms[0].cloneListFile.focus();
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
			document.forms[0].snpListFile.focus();
	      }
	   }
	   
  }
	  
 function radioFold(formElement){
       var element = formElement.name;
       
   if (element == "cytobandRegionStart" || element == "cytobandRegionEnd"){
        document.forms[0].region[0].checked = true;
        document.forms[0].region[1].checked = false;
        document.forms[0].basePairStart.value = "";
        document.forms[0].basePairEnd.value = "";        
       } 
   
   if (element == "basePairStart" || element == "basePairEnd"){
        document.forms[0].region[1].checked = true;
        document.forms[0].region[0].checked = false;
        document.forms[0].cytobandRegionStart.value = "";
        document.forms[0].cytobandRegionEnd.value = "";     
       }      
   
   if (element == "sampleList"){
         document.forms[0].sampleGroup[0].checked = true;
        }
	
   if (element == "geneList"){ 
	      document.forms[0].geneGroup[0].checked = true;
	      }	
   if (element == "cloneListSpecify"){ 
	      document.forms[0].cloneId[0].checked = true;
	      }
   if (element == "snpListSpecify"){
          document.forms[0].snpId[0].checked = true;
          }
   if (element == "foldChangeValueUp"){ 
	      document.forms[0].regulationStatus[0].checked = true;
	      document.forms[0].regulationStatus[1].checked = false;
	      document.forms[0].regulationStatus[2].checked = false;
	      document.forms[0].regulationStatus[3].checked = false;
	      }
    if (element == "foldChangeValueDown"){
	      document.forms[0].regulationStatus[0].checked = false;
	      document.forms[0].regulationStatus[1].checked = true;
	      document.forms[0].regulationStatus[2].checked = false;
	      document.forms[0].regulationStatus[3].checked = false;
	      }
    if ((element == "foldChangeValueUDUp") || (element == "foldChangeValueUDDown")){
	      document.forms[0].regulationStatus[0].checked = false;
	      document.forms[0].regulationStatus[1].checked = false;
	      document.forms[0].regulationStatus[2].checked = true;
	      document.forms[0].regulationStatus[3].checked = false;
	      }
	if ((element == "foldChangeValueUnchangeFrom") || (element == "foldChangeValueUnchangeTo")){      
	      document.forms[0].regulationStatus[0].checked = false;
	      document.forms[0].regulationStatus[1].checked = false;
	      document.forms[0].regulationStatus[2].checked = false;
	      document.forms[0].regulationStatus[3].checked = true;
	      }
	if (element == "cnAmplified"){ 
	      
	      document.forms[0].copyNumber[0].checked = true;
	      document.forms[0].copyNumber[1].checked = false;
	      document.forms[0].copyNumber[2].checked = false;
	      document.forms[0].copyNumber[3].checked = false;
	      }
	if (element == "cnDeleted"){ 
	      
	      document.forms[0].copyNumber[0].checked = false;
	      document.forms[0].copyNumber[1].checked = true;
	      document.forms[0].copyNumber[2].checked = false;
	      document.forms[0].copyNumber[3].checked = false;
	      }
	 if ((element == "cnADAmplified") || (element == "cnADDeleted")){      
	      
	      document.forms[0].copyNumber[0].checked = false;
	      document.forms[0].copyNumber[1].checked = false;
	      document.forms[0].copyNumber[2].checked = true;
	      document.forms[0].copyNumber[3].checked = false;
	      }  
	 if ((element == "cnUnchangeFrom") || (element == "cnUnchangeTo")){      
	      document.forms[0].cnDeleted.value = " ";
	      document.forms[0].cnAmplified.value = " ";
	      document.forms[0].cnADAmplified.value = " ";
	      document.forms[0].cnADDeleted.value = " ";
	      document.forms[0].copyNumber[0].checked = false;
	      document.forms[0].copyNumber[1].checked = false;
	      document.forms[0].copyNumber[2].checked = false;
	      document.forms[0].copyNumber[3].checked = true;
	      }  
 }
 
 function depChange(formElement){
    var element = formElement.name;
    
    if(element == "snpList"){
      var sl = document.forms[0].snpList.selectedIndex;
      if(sl == 2){
      alert("Sorry, 'TSC Id' is not currently being supported");
      document.forms[0].snpList.selectedIndex = 0;
      }
    }
    if(element == "assayPlatform"){
      var ap = document.forms[0].assayPlatform.selectedIndex;
      if(ap == 1){
      alert("Sorry, 'Array CGH' is not currently being supported");
      document.forms[0].assayPlatform.selectedIndex = 0;
      }
      if(ap == 2){
      alert("Sorry, 'All' is not currently being supported");
      document.forms[0].assayPlatform.selectedIndex = 0;
      }
    }
 }
 
 function alertUser(){
  
if(confirm("This will eliminate all data currently entered in the form and will not add a query"))
{
location.href='menu.do';
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
 
function toggleGenePlot(a, b)	{
	if(document.getElementById("geneChart"))	{
		var chart = document.getElementById("geneChart");
		//alert(chart.src + " = " + a);
		var imgURL = chart.src.split("filename=");
		
		if(imgURL[1] == a)	{
			chart.src = imgURL[0] + "filename=" + b;
		}
		else if(imgURL[1] == b)	{
			chart.src = imgURL[0] + "filename=" + a;
		}
	} 
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
if (strValues.length == 0) {
//alert("You have not made any selections");
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

function switchStatistic(element){
	
	switch (element){
	case 0:
		document.getElementById("pfill").innerHTML = "&nbsp;&nbsp;p-Value&nbsp;&nbsp;&le;<input type='text' name='pValueNum' size='10' value='.05' /><br />";
		break;
	case 1:
		document.getElementById("pfill").innerHTML = "&nbsp;&nbsp; Adjusted p-Value&nbsp;&nbsp;&le;<input type='text' name='pValueNum' size='10' value='.05' /><br />";
		break;
	case 2:
		document.getElementById("pfill").innerHTML = "&nbsp;&nbsp; Adjusted p-Value&nbsp;&nbsp;&le;<input type='text' name='pValueNum' size='10' value='.05' /><br />";
		break;
	default: 
		document.getElementById("pfill").innerHTML = "&nbsp;&nbsp;p-Value&nbsp;&nbsp;&le;<input type='text' name='pValueNum' size='10' value='.05' /><br />";
		break;
	}
	
	
}
	   
