function load() {
	$.ajax({
    	data:{
    		page : 1
    	},
    	type: 'Get',
    	//async: false,
    	url: '../servlet/RankListServlet',
    	success: function(data) {
    		var json = eval(data); 
            $.each(json, function (index, element) {  
                //循环获取数据    
            	
            	var id = json[index].id;
                var rank = json[index].rank;
                var nickName = json[index].nickName;  
                var motto = json[index].motto;  
                var solved = json[index].solved;
                var submissions = json[index].submissions;
                var rating = json[index].rating;
                var userUrl = "../jsp/userInfo.jsp?userid=" + id;
                $("#tbody").html($("#tbody").html() + "<tr><td>" + rank +"</td><td><a href="+userUrl + " target=_blank>"+ nickName + "</a></td><td>" + motto+"</td><td>" + solved +"</td><td>" + submissions +"</td>" + "<td>" + rating +"</td></tr>");
            });
            console.log("ajax ok");
    	},
    	error: function(err) {
    		console.log("ajax error");
    	}
    });
}
window.onload = load();