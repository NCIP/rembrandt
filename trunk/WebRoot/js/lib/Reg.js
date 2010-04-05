var Reg = {
'pListServe' : function()	{
 	var msg = "Please fill in the email";
 	var e = "";
 	var radio = "";
	try	{
		if($('listemail').value == "")	{
			$('listemail').style.border="1px solid red";
			e=msg;
		}
		if($('listemail').value != "" && !Reg.isValidEmail($('listemail').value))	{
			$('listemail').style.border="1px solid red";
			e += "Invalid Email";
		}
		if($('join_listServe').checked)
			radio = $('join_listServe').value;
		else
			radio = $('leave_listServe').value;
		
		if(e!="")	{
			throw(e);
		}
		
		//alert(radio);
		RegHelper.pListServe( $('listemail').value, radio, Reg.pListServe_cb);
		
	}
	catch(err)	{
		$('regListServeErr').innerHTML = err;
		Fat.fade_element('regListServeErr');
	}
},
'pListServe_cb' : function(txt)	{
	//eval the txt and look at .status, .un, and .ps, .msg
	//alert("Hello CB");
	var res = eval('(' + txt + ')');
	if(res.status == "pass")	{
		//$('listServeForm').reset();
		//$('email').value = "";

		
		$('loginMsg').innerHTML = "A confirmation request email regarding your request to join or leave the REMBRANDT_USER_L list serve is being sent under a separate cover.";
		Fat.fade_element('loginMsg');
	}
},
'pReg' : function()	{
 	var msg = "Please fill in all required fields";
 	var e = "";
	try	{
		if($('lastName').value == "")	{
			$('lastName').style.border="1px solid red";
			e=msg;
		}
		if($('firstName').value == "")	{
			$('firstName').style.border="1px solid red";
			e=msg;
		}
		if($('phone').value == "")	{
			$('phone').style.border="1px solid red";
			e=msg;
		}
		if($('email').value == "")	{
			$('email').style.border="1px solid red";
			e=msg;
		}
		
		if($('institution').value == "")	{
			$('institution').style.border="1px solid red";
			e=msg;
		}
		if($('cap').value == "")	{
			$('cap').style.border="1px solid red";
			e=msg;
		}
		if($('email').value != "" && !Reg.isValidEmail($('email').value))	{
			$('email').style.border="1px solid red";
			e += "Invalid Email";
		}
		
		if(e!="")	{
			throw(e);
		}
		
		//alert("form is okay");
		//hide the submit button, so they dont submit again.
		$('regButtons').style.display = 'none';
		$('regStatus').style.display = '';
		RegHelper.pReg($('lastName').value, $('firstName').value, $('email').value,$('institution').value, $('cap').value, $('phone').value, $('dept').value, Reg.pReg_cb);
	}
	catch(err)	{
		$('regErr').innerHTML = err;
		Fat.fade_element('regErr');
	}
},
'pReg_cb' : function(txt)	{
	//eval the txt and look at .status, .un, and .ps, .msg
	var res = eval('(' + txt + ')');
	if(res.status == "pass")	{
		$('regForm').reset();
		$('regStatus').style.display = 'none';
		$('regButtons').innerHTML = "Please login to the left";
		$('regButtons').style.display = '';
		$('userName').value = res.un;
		$('password').value = res.ps;
		
		$('regErr').innerHTML = "Thanks for registering";
		$('loginMsg').innerHTML = "Please click 'login' to login using a temporary account.  This username and password was sent to the email account you registered.  Your full account details will be emailed to you shortly.";
		Fat.fade_element('loginMsg');
	}
	else	{
		//alert(res.msg);
		var t = "";
		if(res.msg == "BAD_CAPTCHA")	{
			t= "Please enter the text displayed in the image again.  Refresh the page if you would like another image or email ncicb@pop.nci.nih.gov for assistance";
		}
		if(t=="")	{
			t = "An error occured when processing your registration.  Please try again later or email ncicb@pop.nci.nih.gov for assistance";
		}
		
		$('regErr').innerHTML = t;
		
		setTimeout(function()	{
		$('regButtons').style.display = '';
		$('regStatus').style.display = 'none';
		}, 500);
		
		Fat.fade_element('regErr');

	}
},
'isValidEmail' : function(str)	{
   return (str.indexOf(".") > 1) && (str.indexOf("@") > 0);
}

}