function load() {
	var page = $.getUrlParam('page');
	if(page==null)
		page=1;
	console.log(page);
	$.ajax({
    	data:{
    		page : page,
    		type : 1
    	},
    	type: 'Get',
    	//async: false,
    	url: '../servlet/RankListServlet',
    	contentType:"application/x-www-form-urlencoded;charset=UTF-8",
    	success: function(data) {
    		var json = $.parseJSON(data);
    		var list = json.list;
    		var count = json.count;
    		var showNum = 50;
    		pageDiv(page,count,showNum,"Ranklist.jsp");
            $.each(list, function (index, element) {
                //循环获取数据
            	var id = list[index].id;
                var rank = list[index].rank;
                var nickName = list[index].nickName;  
                var motto = list[index].motto;  
                var solved = list[index].solved;
                var submissions = list[index].submissions;
                var rating = parseInt(list[index].rating);
                var userUrl = "../jsp/userInfo.jsp?user_id=" + id;
                var contestRating = parseInt(list[index].contestRating);
                var style = null;
                if(motto==null)
                	motto="";
                if(rating>=1000)
                	style="rating-1";
                else if(rating>=800)
                	style="rating-2";
                else if(rating>=600)
                	style="rating-3";
                else if(rating>=400)
                	style="rating-4";
                else if(rating>=200)
                	style="rating-5";
                else
                	style="rating-6";
                console.log(style);
                $("#tbody").html($("#tbody").html() + "<tr><td>" + rank +"</td><td><a class="+style+" href="+userUrl + " target=_blank>"+ nickName + "</a></td><td>" + motto+"</td><td>" + solved +"</td><td>" + submissions +"</td><td>" + contestRating +"</td><td>" + rating +"</td></tr>");
            });
            console.log("ajax ok");
    	},
    	error: function(err) {
    		console.log("ajax error");
    	}
    });
}

$(function(){
	$('#ser-user').click(function() {
		var nickName = $('#nickName').val();
		if(nickName!="")
		{
			$.ajax({
		    	data:{
		    		nickName : nickName,
		    		type : 2
		    	},
		    	type: 'Get',
		    	url: '../servlet/RankListServlet',
		    	contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		    	success: function(data) {
		    		var json = eval(data);
		    		$("#tbody").html("");
		            $.each(json, function (index, element) {
		                //循环获取数据
		            	var id = json[index].id;
		                var rank = json[index].rank;
		                var nickName = json[index].nickName;  
		                var motto = json[index].motto;  
		                var solved = json[index].solved;
		                var submissions = json[index].submissions;
		                var rating = parseInt(json[index].rating);
		                var userUrl = "../jsp/userInfo.jsp?user_id=" + id;
		                var contestRating = parseInt(json[index].contestRating);
		                var style = null;
		                if(motto==null)
		                	motto="";
		                if(rating>=1000)
		                	style="rating-1";
		                else if(rating>=800)
		                	style="rating-2";
		                else if(rating>=600)
		                	style="rating-3";
		                else if(rating>=400)
		                	style="rating-4";
		                else if(rating>=200)
		                	style="rating-5";
		                else
		                	style="rating-6";
		                $("#tbody").html($("#tbody").html() + "<tr><td>" + rank +"</td><td><a class="+style+" href="+userUrl + " target=_blank><strong>"+ nickName + "</strong></a></td><td>" + motto+"</td><td>" + solved +"</td><td>" + submissions +"</td><td>" + contestRating +"</td><td>" + rating +"</td></tr>");
		            });
		            console.log("ajax success");
		    	},
		    	error: function(err) {
		    		console.log("ajax error");
		    	}
		    });
		}
	});
});

window.onload = load();