function load() {
	$.ajax({
    	data:{
    		user_id : 5
    	},
    	type: 'Get',
    	//async: false,
    	url: '../servlet/UserInfoServlet',
    	success: function(data) {
    		var json = $.parseJSON(data);
    		var $showUser1 = $('#show-userInfo-1');
    		var $showUser2 = $('#show-userInfo-2');
    		console.log(json);
    		$showUser1.find('#nickname').text(json.nickName);
    		$showUser1.find('#blog').html("<a href = http://" + json.userBlog +">" +json.userBlog + "</a>");
    		$showUser1.find('#email').text(json.email);
    		$showUser1.find('#hduuser').text(json.hduuser);
    		$showUser1.find('#pojuser').text(json.pojuser);
    		$showUser1.find('#vjudgeuser').text(json.vjudgeuser);
    		
    		$showUser2.find('#rank').text(json.rank);
    		$showUser2.find('#rating').text(json.rating);
    		$showUser2.find('#solved').text(json.solved);
    		$showUser2.find('#submissions').text(json.submissions);
    		//$showUser.find('#acRatio').text(json.pojuser);
    		
            console.log("ajax ok");
    	},
    	error: function(err) {
    		console.log("ajax error");
    	}
    });
}
window.onload = load();