function spawn(url,winw,winh) {
  var w = window.open(url, "_blank",
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");
} 

function setQuery(txt)	{
	document.forms[0].query.value = txt;
}

function setDispMethod(txt)	{
	
	document.forms[0].method.value = txt;
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
	var t = item.selectedIndex;
	var r = item.options[t].value;
	box.innerHTML = "<b class='message'>"+r+"</b>";
 
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
	 alert("you got here");
	 var ds = document.forms[1].dataSet.selectedIndex;
	 
	 document.forms[1].generatingInstitution.options.length = 1;
	 alert("you got here");
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
		
		
	  if(element == "geneGroup"){	
	      if (i == 0){
	      
		  document.forms[0].geneList.disabled = false;
		  document.forms[0].geneType.disabled = false;
		  document.forms[0].geneFile.value = "";
		  document.forms[0].geneFile.disabled = true;
		
		  }
	      if (i == 1) {
	      
	      document.forms[0].geneList.value = "";
	      document.forms[0].geneList.disabled = true;
		  document.forms[0].geneType.disabled = true;
		  document.forms[0].geneFile.disabled = false;
	
	      }
	   }
	   
	   if(element == "cloneId"){	
	      if (i == 0){
	      
		  document.forms[0].cloneList.disabled = false;
		  document.forms[0].cloneListSpecify.disabled = false;
		  document.forms[0].cloneListFile.disabled = true;
		
		  }
	      if (i == 1) {
	      
	      document.forms[0].cloneListSpecify.value = "";
	      document.forms[0].cloneList.disabled = true;
		  document.forms[0].cloneListSpecify.disabled = true;
		  document.forms[0].cloneListFile.disabled = false;
	
	      }
	   }
	   
	   
  }
	  
 function radioFold(formElement){
       var element = formElement.name;
		
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
	    
 }
 
 
  