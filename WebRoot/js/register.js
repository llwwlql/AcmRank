function checkUsername()
{
    var username=$("input[name=user_name]").val();
    var patrn = /^[a-zA-Z0-9_]{5,15}$/;
    if(username.length==0)
    {
    	return false;
    }
    else if(!patrn.test(username))//
    {
      	return false;
    }
    else
    {
    	var flag;
    	$.ajax({
        	data:{
        		UserName: username,
        		type: 1
        	},
        	type: 'Post',
        	async: false,
        	url: '../servlet/RegisterServlet',
        	success: function(data) {
        		console.log(data);
        		var json = $.parseJSON(data);
        		if(json.isExist=="false")
        			flag=1;
        		else
        			flag=0;
        		console.log("ajax success");
        	},
        	error: function(err) {
        		console.log("ajax error");
        	}
       });
    }
   return flag;
}

function checkNickname(){
	var nickname = $("input[name=nick_name]").val();
	var patrn = /^[\\u4e00-\\u9fa5_a-zA-Z0-9\W]{1,18}$/;
	if(nickname.length==0)
	{
		return false;
	}
	else if (!patrn.test(nickname)) 
	{	
		return false;
	}
	return true;
}

function checkPassword()
{
	var passwd = $("input[name=password]").val();
	var patrn = /^[a-zA-Z0-9_]{6,20}$/;
	if(passwd.length==0)
		return false;
	else if (!patrn.test(passwd))
		return false;
	return true;
}

function checkPassword2()
{
     var password1 = $("input[name=password]").val();
     var password2 = $("input[name=repassword]").val();
     if(password1!=password2)
     {
        return false;
     }
     return true;    
}

function checkSchool(){
   	var Email = $("input[name=school]").val();
	return true;
}

function checkEmail()
{
   var Email = $("input[name=email]").val();
   var patrn = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    if(Email.length==0)
		return false;
	else if (!patrn.exec(Email)) 
		return false;
	return true;
}

function checkHduname()
{
	var username=$("input[name=hduUsername]").val();
    var patrn = /^[a-zA-Z0-9_]{5,15}$/;
    if(username.length==0)
    {
    	return true;
    }
    else if(!patrn.test(username))//
    {
      	return false;
    }
    return true;
}

function checkVJname()
{
	var username=$("input[name=VjudgeUsername]").val();
    var patrn = /^[a-zA-Z0-9_]{1,15}$/;
    if(username.length==0)
    {
    	return true;
    }
    else if(!patrn.test(username))//
    {
      	return false;
    }
    return true;
}

function checkMotto()
{
	var motto = $("textarea[name=motto]").val();
	if(motto.length>255)
		return false;
	return true;
}

function check(){
	$('#btn-register').attr({"disabled":"disabled"});
	if(checkUsername()==1 && checkPassword()==1 && checkPassword2()==1 && checkNickname()==1 && checkEmail()==1)
	{
		//alert(checkUsername()+"  " +checkPassword()+"  " +checkPassword2()+"   "+checkNickname()+"    "+checkEmail())
		var username = $("input[name=user_name]").val();
		var nickname = $("input[name=nick_name]").val();
	    var passwd = $("input[name=password]").val();
	    var email = $("input[name=email]").val();
    	$.ajax({
        	data:{
        		userName: username,
        		nickName: nickname,
        		password: passwd,
        		email: email,
        		type: 2
        	},
        	type: 'Post',
        	url: '../servlet/RegisterServlet',
        	success: function(resp) {
        		var json = $.parseJSON(resp);
        		if(json.result==1)
        		{
        			console.log("保存用户信息成功!");
        			window.location.href="../jsp/moreInfo.jsp";
        		}
        		else
        		{
        			$('#btn-register').removeAttr("disabled");
        			alert("保存用户信息失败！！");
        		}
        			
        	},
        	error: function(err) {
        		$('#btn-register').removeAttr("disabled");
        		console.log("保存用户信息失败!");
        	}
        });
	}
}

function check_submit()
{
	$('#btn-submit').attr({"disabled":"disabled"});
	if(checkHduname()==true && checkVJname()==true && checkMotto()==true)
	{
		var hduUsername = $("input[name=hduUsername]").val();
		var pojUsername = $("input[name=pojUsername]").val();
	    var VjudgeUsername = $("input[name=VjudgeUsername]").val();
	    var blog = $("input[name=blog]").val();
	    var motto = $("textarea[name=motto]").val();
    	$.ajax({
        	data:{
        		user_id: 4,
        		hduUsername: hduUsername,
        		pojUsername: pojUsername,
        		VjudgeUsername: VjudgeUsername,
        		blog: blog,
        		motto: motto,
        		type: 3
        	},
        	type: 'Post',
        	url: '../servlet/RegisterServlet',
        	success: function(resp) {
        		var json = $.parseJSON(resp);
        		if(json.result==1)
        		{
        			console.log("保存用户信息成功!");
        			window.location.href="../jsp/Contests.jsp";
        		}
        		else
        		{
        			alert("保存用户信息失败！！");
        		}
        	},
        	error: function(err) {
        		console.log("保存用户信息失败!");
        		$('#btn-submit').removeAttr("disabled");
        	}
        });		
	}
}


$(function () { 
	$("[data-toggle='tooltip']").tooltip({
		trigger: 'manual',
	})
	.on('focus', function() {
		// 获得焦点时隐藏	
		$(this).tooltip('hide');
	})
	.on('blur', function() {
		// 失去焦点时显示
		var name = $(this).attr("name");
		var check = 1;
		if(name==="user_name")
			check = checkUsername();
		else if(name==="nick_name")
			check = checkNickname();
		else if(name==="email")
			check = checkEmail();
		else if(name==="password")
			check = checkPassword();
		else if(name==="repassword")
			check = checkPassword2();
		else if(name=="hduUsername")
			check = checkHduname();
		else if(name=="VjudgeUsername")
			check = checkVJname();
		else if(name=="motto")
			check = checkMotto();
		console.log(check);
		if(check==0)
			$(this).tooltip('show');
		else if(check==1)
			$(this).tooltip('hide');
	});
});


$(function(){
	$('#btn-register').click(function() {
		check();
	});
})

$(function(){
	$('#btn-submit').click(function() {
		check_submit();
	});
})



