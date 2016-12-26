<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<!-- saved from url=(0075)http://www.sdutacm.org/onlinejudge2/index.php/Home/User/info/uid/21636.html -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta http-equiv="X-UA-Compatible" content="IE=9">

<title>Userinfo</title>

<jsp:include page="Header.jsp"></jsp:include>

<script language="javascript" type="text/javascript" src="../js/show_userInfo.js"></script>
</head>
<body>
<div class="everything">

	<div class="main">
	
<div class="container">
    <div class="block block-info" style="padding-top:40px;"></div>
    <div class="row">
            <div class="col-md-12">
                <div class="col-md-4">
                    <img src="img/1.jpeg">
                </div>
                    <div class="col-md-4" id="show-userInfo-1">
                    <h4 class="text-warning">Nick name:&nbsp;&nbsp;<small id="nickname"></small></h4>
                    <h4 class="text-primary">Blog:&nbsp;&nbsp;<small id="blog"></small></h4> 
                    <h4 class="text-success">Email:&nbsp;&nbsp;<small id="email"></small></h4>
                    <h4 class="text-primary">HduUser:&nbsp;&nbsp;<small id="hduuser"></small></h4>
                    <h4 class="text-info">PojUser:&nbsp;&nbsp;<small id="pojuser"></small></h4>
                    <h4 class="text-success">VjudgeUser:&nbsp;&nbsp;<small id="vjudgeuser"></small></h4>
                    </div>
                    <div class="col-md-4" id="show-userInfo-2">
                    <h4 class="text-info">Rank:&nbsp;&nbsp;<small id="rank"></small></h4>
                    <h4 class="text-warning">Rating:&nbsp;&nbsp;<small id="rating"></small></h4>
                    <h4 class="text-danger">Solved:&nbsp;&nbsp;<small id="solved"></small></h4>
                    <h4 class="text-primary">Submissions:&nbsp;&nbsp;<small id="submissions"></small></h4>
                    <h4 class="text-muted">AC Ratio:&nbsp;&nbsp;<small id="acRatio"></small></h4>
                    </div>
            </div>
    </div>

    <div class="row">
        <div class="col-md-12 text-right" style="padding-top: 10px;padding-bottom: 10px;">
            <a class="btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php/Home/User/updateinfo/uid/21636">修改个人信息</a>
            <a class="btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php/Home/User/updatepasswd/uid/21636">修改密码</a>
        </div>
    </div>    <div class="row">
        <div class="block block-danger">
            <div class="block-content">
                <div class="heading">User's <span class="ce-text bold">submissions</span></div>
                <div class="inner problem_list">
                    <a href="http://www.sdutacm.org/onlinejudge2/index.php/Home/Solution/status/username/llwwlql/uid/21636.html">Submissions</a>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="block block-success">
            <div class="block-content">
                <div class="heading">List of <span class="accept-text bold">solved</span> problems</div>
                <div class="inner problem_list">
                    <a href="http://www.sdutacm.org/onlinejudge2/index.php/Home/Index/problemdetail/pid/2603.html">2603</a><a href="http://www.sdutacm.org/onlinejudge2/index.php/Home/Index/problemdetail/pid/2624.html">2624</a>                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="block block-warning">
            <div class="block-content">
                <div class="heading">List of <span class="wrong-text bold">unsolved</span> problems</div>
                <div class="inner problem_list">
                    <a href="http://www.sdutacm.org/onlinejudge2/index.php/Home/Index/problemdetail/pid/2610.html">2610</a>                </div>
            </div>
        </div>
    </div>
</div>

	</div>

	<div class="footer">
		<div class="container">
		        <div class="row">
		            <div class="col-sm-11 text-center">
		                <a href="http://www.sdutacm.org/onlinejudge2/index.php/Home/User/info/uid/21636.html#">SDUTACM运维技术中心</a>
		            </div>
		        </div>
		        <div class="row">
		            <div class="col-sm-11 text-center">
		                <span>Copyright © 2013-2014 SDUTACM Team. All Rights Reserved.</span>
		            </div>
		        </div>
		</div>
	</div>
</div>

</body></html>