(function($) {
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
})(jQuery);
function pageDiv(page,count,showNum,url)
{
	var pageNum = parseInt(count/showNum);
	if(count%showNum!=0)
		pageNum++;
	var $bootpage = $('#page-div');
	var aStr = "<a href="+ url +"?page=";
	var spanStr = "<span>"+page+"</span>"
	var nextPage = (parseInt(page)+1);
	var j = 0;
	for(var i=1;i<page;i++)
		$bootpage.html($bootpage.html() + aStr + i + ">"+i+"</a>");
	$bootpage.html($bootpage.html() + spanStr);
	for(var i = nextPage,j=0;i<pageNum && j <10;i++,j++)
		$bootpage.html($bootpage.html() + aStr + i + ">"+i+"</a>");
	//next
	if(page!=pageNum)
	{
		$bootpage.html($bootpage.html() + aStr + nextPage + " class=next>下一页</a>");
		$bootpage.html($bootpage.html() + aStr + pageNum + ">" + pageNum+"</a>");
	}
	$(".bootpage div").addClass("btn-group btn-group-sm");
	$(".bootpage a").addClass("btn btn-default");
	$(".bootpage span").addClass("btn btn-primary");
}