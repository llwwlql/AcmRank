function load() {
	var page = $.getUrlParam('page');
	if(page==null)
		page=1;
	$.ajax({
    	data:{
    		page : page,
    		type : 1
    	},
    	type: 'Get',
    	url: 'servlet/ContestServlet',
    	success: function(data) {

	    	var json = $.parseJSON(data);
    		var list = json.list;
    		count = json.count;
    		var showNum = 50;
    		pageDiv(page,count,showNum,"Contests.jsp");
            $.each(list, function (index, element) {  
                var id = list[index].id;
                var contestName = list[index].contestName;  
                var startTime = list[index].startTime;  
                var endTime = list[index].endTime;
                var origin = list[index].origin;
                var peopleNum = list[index].peopleNum;
                var strOrigin;
                var originUrl;
                var contestRank = "contestRank.jsp?contest_id=" + id;
                if(origin ==1)
                {
                    strOrigin = "HDU Contest";
                    originUrl = "http://acm.hdu.edu.cn/diy/contest_login.php?action=login&cid=" + list[index].orginId;
                }
                else if(origin ==2)
                {
                	strOrigin = "Vjudge Contest";
                    originUrl = "https://vjudge.net/contest/" + list[index].orginId;
                }
                
                $("#tbody").html($("#tbody").html() + "<tr><td>" + id +"</td><td><a href="+originUrl + " target=_blank>"+contestName + "</a></td><td><img src='img/people1.png' style='weight:80%'/>&nbsp&nbspx "+peopleNum +"</td><td>" + startTime+"</td><td>" + endTime +"</td><td>" + strOrigin +"</td>" + "<td><a href="+contestRank+">show</a></td></tr>");
            });
    	},
    	error: function(err) {
    		console.log("ajax error");
    	}
    });
}
$(function(){
	$('#ser-contest').click(function() {
		var cid = $('#cid').val();
		var contestname = $('#contestname').val();
		var date = $('#date').val();
		if(contestname!="" || date!="")
		{
			$.ajax({
		    	data:{
		    		contestname : contestname,
		    		date : date,
		    		type : 2
		    	},
		    	type: 'Get',
		    	url: 'servlet/ContestServlet',
		    	contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		    	success: function(data) {
		    		var json = eval(data);
		    		$("#tbody").html("");
		            $.each(json, function (index, element) {  
		                var id = json[index].id;
		                var contestName = json[index].contestName;  
		                var startTime = json[index].startTime;  
		                var endTime = json[index].endTime;
		                var origin = json[index].origin;
		                var peopleNum = json[index].peopleNum;
		                var strOrigin;
		                var originUrl;
		                var contestRank = "contestRank.jsp?contest_id=" + id;
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
		                
		                $("#tbody").html($("#tbody").html() + "<tr><td>" + id +"</td><td><a href="+originUrl + " target=_blank>"+contestName + "</a></td><td><img src='../img/people1.png' style='weight:80%'/>&nbsp&nbspx "+peopleNum +"</td><td>" + startTime+"</td><td>" + endTime +"</td><td>" + strOrigin +"</td>" + "<td><a href="+contestRank+">show</a></td></tr>");
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
$("body").keydown(function() {
	if (event.keyCode == "13") {// keyCode=13是回车键
		$('#ser-contest').click();
	}
});
window.onload = load();