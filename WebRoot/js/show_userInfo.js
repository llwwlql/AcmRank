(function($) {
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
})(jQuery);

function load() {
	var user_id = $.getUrlParam('user_id');
	$.ajax({
		data : {
			user_id : user_id,
			type : 1
		},
		type : 'Get',
		// async: false,
		url : '../servlet/UserInfoServlet',
		contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		success : function(data) {
			var json = $.parseJSON(data);
			var $showUser1 = $('#show-userInfo-1');
			var $showUser2 = $('#show-userInfo-2');
			var hduType = json.hduType;
			var pojType = json.pojType;
			var vjudgeType = json.vjudgeType;
			$showUser1.find('#nickname').text(json.nickName);
			$showUser1.find('#blog').html(
					"<a href =" + json.userBlog + ">" + json.userBlog
							+ "</a>");
			$showUser1.find('#email').text(json.email);
			$showUser1.find('#hduuser').text(json.hduuser);
			$showUser1.find('#pojuser').text(json.pojuser);
			$showUser1.find('#vjudgeuser').text(json.vjudgeuser);

			$showUser2.find('#rank').text(json.rank);
			$showUser2.find('#rating').text(json.rating);
			$showUser2.find('#solved').text(json.solved);
			$showUser2.find('#submissions').text(json.submissions);
			$showUser2.find('#contest-rating').text(json.contestRating);

			if(hduType==0)
				show-waring("hduWarning");
			if(pojType==0)
				show-waring("pojWarning");
			if(vjudgeType==0)
				show-waring("vjudgeWarning");
			
			console.log("ajax success");
		},
		error : function(err) {
			console.log("ajax error");
		}
	});
}
window.onload = load();

require.config({
	paths:{
		echarts:'http://echarts.baidu.com/build/dist'
	}
});

require(
	[
		'echarts',
		'echarts/chart/line'
	],
	function(ec){
		var json;
		var user_id = $.getUrlParam('user_id');
		$.ajax({
			data : {
				user_id : user_id,
				type : 2
			},
			type : 'Get',
			async: false,
			url : '../servlet/UserInfoServlet',
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			success : function(data) {
				json = eval(data);
				console.log("ajax success");
			},
			error : function(err) {
				console.log("ajax error");
			}
		});
		var myChart = ec.init(document.getElementById("line"));		
		var option = {
			    tooltip : {
			        trigger: 'item',
			        position:function(p){   //其中p为当前鼠标的位置
			            return [p[0] - 95, p[1] - 64];
			        },
			        formatter : function (params) {
			            var date = new Date(params.value[0]);
			            var origin = params.value[3];
			            var originStr;
			            switch(origin)
			            {
				            case 3:
				            	originStr="HDU Solved Problem";break;
				            case 4:
				            	originStr="POJ Solved Problem";break;
				            case 5:
				            	originStr="HDU Contest Rank";break;
				            case 6:
				            	originStr="VJudge Contest Rank";break;
				            case 7:
				            	originStr="VJudge Solved Problem";break;
				            default:
				            	originStr="";break;
			            }
			            data = date.getFullYear() + '-'
			                   + (date.getMonth() + 1) + '-'
			                   + date.getDate() + ' '
			            return data + '<br/>'
			                   + params.value[1] 
			            	   + "( +"+params.value[2]+")" 
			                   + "</br>"
			                   +originStr;
			        }
			    },
			    dataZoom: {
			        show: true,
			        start : 0
			    },
			    legend : {
			        data : ['Rating']
			    },
			    grid: {
			        y2: 80
			    },
			    xAxis : [
			        {
			            type : 'time',
			            splitNumber:10
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name: 'Rating',
			            type: 'line',
			            itemStyle : {
							normal : {  color:'#4D6FF9',
								lineStyle:{
									color:'#4D6FF9'
								}
							}
						},
			            showAllSymbol: true,
			            symbolSize: function (value){
			                return 3.5;
			            },
			            data: (function () {
			                var d = [];
			                var value = 0;
			                $.each(json, function (index, element) {
			                	var time = json[index].time;
			                	var origin = json[index].origin;
			                	var score = json[index].score;
			                	value = value + score;
			                	d.push([
				                    new Date(time),
				                    value,
				                    score,
				                    origin
				                ]);
			                });
			                return d;
			            })()
			        }
			    ]
			};
		myChart.setOption(option);
	}
);

function alterInfo(){
	var $showUser1 = $('#show-userInfo-1');
	var nickname = $showUser1.find('#nickname').text();
	var blog = $showUser1.find('#blog').text();
	var email = $showUser1.find('#email').text();
	var hduuser = $showUser1.find('#hduuser').text();
	var pojuser = $showUser1.find('#pojuser').text();
	var vjudgeuser = $showUser1.find('#vjudgeuser').text();
	$showUser1.find('#nickname').html("<input class=form-control type=text value="+nickname+">");
	$showUser1.find('#blog').html("<input class=form-control type=text value="+blog+">");
	$showUser1.find('#email').html("<input class=form-control type=text value="+email+">");
	$showUser1.find('#hduuser').html("<input class=form-control type=text value="+hduuser+">");
	$showUser1.find('#pojuser').html("<input class=form-control type=text value="+pojuser+">");
	$showUser1.find('#vjudgeuser').html("<input class=form-control type=text value="+vjudgeuser+">");
	//update-info
	$('#update-btn').html("<a id='updateInfo' class='btn btn-default' onclick='updateInfo()'>确认修改</a>");
	
}
function updateInfo(){
	var $showUser1 = $('#show-userInfo-1');
	var nickname = $showUser1.find('#nickname').find('input').val();
	var blog = $showUser1.find('#blog').find('input').val();
	var email = $showUser1.find('#email').find('input').val();
	var hduuser = $showUser1.find('#hduuser').find('input').val();
	var pojuser = $showUser1.find('#pojuser').find('input').val();
	var vjudgeuser = $showUser1.find('#vjudgeuser').find('input').val();
	$showUser1.find('#nickname').text(nickname);
	$showUser1.find('#blog').html(
			"<a href =" + blog + ">" + blog
					+ "</a>");
	$showUser1.find('#email').text(email);
	$showUser1.find('#hduuser').text(hduuser);
	$showUser1.find('#pojuser').text(pojuser);
	$showUser1.find('#vjudgeuser').text(vjudgeuser);
	$('#update-btn').html("<a id='updateInfo' class='btn btn-default' onclick='alterInfo()'>修改个人信息</a>");
	var user_id = $.getUrlParam('user_id');
	$.ajax({
		data : {
			user_id : user_id,
			type : 3,
			nickName : nickname,
			blog : blog,
			email : email,
			hduUser : hduuser,
			pojUser : pojuser,
			vjudgeUser : vjudgeuser
		},
		type : 'Post',
		// async: false,
		url : '../servlet/UserInfoServlet',
		contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		success : function(data) {
			var json = $.parseJSON(data);
			var $showUser1 = $('#show-userInfo-1');
			var $showUser2 = $('#show-userInfo-2');
			$showUser1.find('#nickname').text(json.nickName);
			$showUser1.find('#blog').html(
					"<a href = " + json.userBlog + ">" + json.userBlog
							+ "</a>");
			$showUser1.find('#email').text(json.email);
			$showUser1.find('#hduuser').text(json.hduuser);
			$showUser1.find('#pojuser').text(json.pojuser);
			$showUser1.find('#vjudgeuser').text(json.vjudgeuser);

			$showUser2.find('#rank').text(json.rank);
			$showUser2.find('#rating').text(json.rating);
			$showUser2.find('#solved').text(json.solved);
			$showUser2.find('#submissions').text(json.submissions);
			$showUser2.find('#contest-rating').text(json.contestRating);

			console.log("ajax success");
		},
		error : function(err) {
			console.log("ajax error");
		}
	});
}
/*function show-warning(var imgId){
	$('#'+imgId);
}*/
