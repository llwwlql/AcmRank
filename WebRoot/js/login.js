function check() {
	$('#btn-submit').attr({"disabled":"disabled"});
	var username = $("input[name=user_name]").val();
	var passwd = $("input[name=password]").val();
	$.ajax({
		data : {
			userName : username,
			password : passwd,
			
			type : 1
		},
		type : 'Post',
		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		url : '../servlet/LoginServlet',
		success : function(resp) {
			var json = $.parseJSON(resp);
			if (json.result == 1) {
				console.log("用户登录成功！");
				window.location.href = "../jsp/Contests.jsp";
			} else if(json.result == 2){
				$('#btn-register').removeAttr("disabled");
				$('#user_name').tooltip('show');
				$('#password').tooltip('hide');
			} else if(json.result == 3){
				$('#password').tooltip('show');
				$('#user_name').tooltip('hide');
			}
		},
		error : function(err) {
			$('#btn-register').removeAttr("disabled");
			console.log("用户登录失败！");
		}
	});
}

$(function() {
	$("[data-toggle='tooltip']").tooltip({
		trigger : 'manual',
	});
});

$(function() {
	$('#btn-login').click(function() {
		check();
	});
});
