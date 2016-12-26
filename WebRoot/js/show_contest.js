function load() {
	$.ajax({
    	data:{
    		page : 1
    	},
    	type: 'Get',
    	//async: false,
    	url: '../servlet/ContestServlet',
    	success: function(data) {
    		var json = eval(data); 
            $.each(json, function (index, element) {  
                //循环获取数据    
                var id = json[index].id;
                var contestName = json[index].contestName;  
                var startTime = json[index].startTime;  
                var endTime = json[index].endTime;
                var origin = json[index].origin;
                var strOrigin;
                var originUrl;
                var contestRank = "#";
                if(origin ==1)
                {
                    strOrigin = "HDU Contest";
                    originUrl = "http://acm.hdu.edu.cn/diy/contest_login.php?action=login&cid=" + json[index].orginId;
                }
                else if(origin ==2)
                {
                	strOrigin = "Vjudge Contest";
                    originUrl = "https://vjudge.net/contest/" + json[index].orginId;
                }
                $("#tbody").html($("#tbody").html() + "<tr><td>" + id +"</td><td><a href="+originUrl + " target=_blank>"+contestName + "</a></td><td>" + startTime+"</td><td>" + endTime +"</td><td>" + strOrigin +"</td>" + "<td><a href="+contestRank+">show</a></td></tr>");
            });
    	},
    	error: function(err) {
    		console.log("ajax error");
    	}
    });
}
window.onload = load();