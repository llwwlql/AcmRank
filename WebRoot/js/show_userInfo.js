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
			user_id : user_id
		},
		type : 'Get',
		// async: false,
		url : '../servlet/UserInfoServlet',
		contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		success : function(data) {
			var json = $.parseJSON(data);
			var $showUser1 = $('#show-userInfo-1');
			var $showUser2 = $('#show-userInfo-2');
			console.log(json);
			$showUser1.find('#nickname').text(json.nickName);
			$showUser1.find('#blog').html(
					"<a href = http://" + json.userBlog + ">" + json.userBlog
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
			// $showUser.find('#acRatio').text(json.pojuser);

			console.log("ajax ok");
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
//สนำร
require(
	[
		'echarts',
		'echarts/chart/line'
	],
	function(ec){
		var myChart = ec.init(document.getElementById("line"));		
		var option = {
			    tooltip : {
			        trigger: 'item',
			        formatter : function (params) {
			            var date = new Date(params.value[0]);
			            data = date.getFullYear() + '-'
			                   + (date.getMonth() + 1) + '-'
			                   + date.getDate() + ' '
			            return data + '<br/>'
			                   + params.value[1]
			        }
			    },
			    dataZoom: {
			        show: true,
			        start : 70
			    },
			    legend : {
			        data : ['series1']
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
			            name: 'series1',
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
			                return 3;
			            },
			            data: (function () {
			                var d = [];
			                var len = 0;
			                var now = new Date();
			                var value;
			                while (len++ < 200) {
			                    d.push([
			                        new Date(2014, 9, 1, 0, len*1440),
			                        (Math.random()*30).toFixed(2) - 0,
			                        (Math.random()*100).toFixed(2) - 0
			                    ]);
			                }
			                return d;
			            })()
			        }
			    ]
			};
		myChart.setOption(option);
	}
);