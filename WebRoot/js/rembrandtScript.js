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

function checkForm(){
 if(document.forms[0].queryName != null){
 document.forms[0].queryName.focus();
 }
 if(document.forms[0].tumorType != null){ 
 onRadio("tumorType",(document.forms[0].tumorType.options.value));
 }
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

function changeList(formElement)	{	

	//selected index of the selected
	var element = formElement.name;
		//alert(element);
		 
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
	 if(element == "existingGroups"){	 	
	  	  document.forms[0].variousSamplesRadio.checked = true;	 
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
 
 
 function toggleGenePlot(a, b)	{
	if(document.getElementById("geneChart"))	{
		var chart = document.getElementById("geneChart");
		//alert(chart.src + " = " + a);
		var imgURL = chart.src.split("filename=");
		if(arguments[1])	{
			if(imgURL[1] == a)	{
				chart.src = imgURL[0] + "filename=" + b;
				chart.useMap = "#"+b;
			}
			else if(imgURL[1] == b)	{
				chart.src = imgURL[0] + "filename=" + a;
				chart.useMap = "#"+a;
			}
			else	{
				chart.src = imgURL[0] + "filename=" + a;
				chart.useMap = "#"+a;
			}
		}
		else	{
			chart.src = imgURL[0] + "filename=" + a;
			chart.useMap = "#"+a;
		}
	} 
}