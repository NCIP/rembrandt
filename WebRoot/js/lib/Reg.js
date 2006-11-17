var Reg = {

'pReg' : function()	{
 	var msg = "Please fill in all fields";
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
		RegHelper.pReg($('lastName').value, $('firstName').value, $('email').value,$('institution').value, $('cap').value, Reg.pReg_cb);
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
		$('loginMsg').innerHTML = "Please click 'login' to login using a temporary account.  Your full account details will be mailed to the address you submitted.";
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
   return (str.indexOf(".") > 2) && (str.indexOf("@") > 0);
}

}