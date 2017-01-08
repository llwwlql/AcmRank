$(function(){
	var contest_id = GetQueryString("contest_id");
	$.ajax({
    	data:{
    		contest_id: contest_id,
    		type: 1
    	},
    	type: 'Post',
    	url: '../servlet/ContestRankServlet',
    	contentType:"application/x-www-form-urlencoded;charset=UTF-8",
    	success: function(resp) {
    		var json = eval(resp);
    		var len = json[0].contestproblems.length;
			var half = 75/len;
			if(half>10)
				half=10;
    		for(var i=0;i<len;i++)
    		{
    			var problemId = i+65;
    			$('#table-head').html($('#table-head').html()+"<th style='width:"+half+"%;text-align:center;'>"+String.fromCharCode(problemId) +"</th>");
    		};
    		
    		$.each(json, function (index, element) {    			
                //循环获取数据    
                var rank = json[index].rank;
                var solved = json[index].solved;  
                var penalty = json[index].penalty;  
                var contestproblems = json[index].contestproblems;
                var userName = json[index].userName;
                var user_id = json[index].userId;
                var contestRank = "#";
                var originUrl;
                var str= $("#tbody").html();
                if(user_id == 0)
                	originUrl = "#";
                else
                	originUrl = "../jsp/userInfo.jsp?user_id=" + user_id;
                console.log(user_id);
                str = str + "<tr><td>" + rank +"</td><td><a href="+originUrl + ">"+userName + "</a></td><td>" + solved+"</td><td>" + penalty +"</td>";
                for(var i=0;i<len;i++)
                {
                	var submissions = parseInt(contestproblems[i].submissions);
                	var penalty = contestproblems[i].penalty;
                	var firstAc = contestproblems[i].firstAc;
                	var acOr = contestproblems[i].acOr;
                	var style="";
                	if(firstAc=="1")
                		style="first-ac";
                	else if(acOr=="1")
                		style="ac";
                	else if(submissions!=0)
                		style="wrong";
                	if(style=="wrong")
                		str = str + "<td class="+style+"><br>( -"+(submissions) +" )</td>";
                	else if(submissions>1)
                		str = str + "<td class="+style+">"+penalty+"<br>( -"+(submissions-1) +" )</td>";
                	else if(submissions==1)
                		str = str + "<td class="+style+">"+penalty+"<br>&nbsp</td>";
                	else
                		str = str + "<td><br>&nbsp</td>";
                }
                $("#tbody").html(str);
            });
    		console.log("ajax success");
    	},
    	error: function(err) {
    		console.log("ajax error");
    	}
   });
	
});
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}