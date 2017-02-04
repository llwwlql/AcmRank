<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<!-- saved from url=(0075)http://www.sdutacm.org/onlinejudge2/index.php/Home/User/info/uid/21636.html -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta http-equiv="X-UA-Compatible" content="IE=9">

<title>Userinfo</title>

<jsp:include page="Header.jsp"></jsp:include>
<script src="http://echarts.baidu.com/build/dist/echarts.js"
	charset="UTF-8"></script>
<script language="javascript" type="text/javascript"
	src="js/show_userInfo.js"></script>
</head>
<body>
	<div class="everything">
		<div class="main">
			<div class="container">
				<div id="warning">
				</div>
				<div class="block block-info" style="padding-top:40px;"></div>
				<div class="row">
					<div class="col-md-12">
						<div class="col-md-4">
							<img src="img/1.jpeg">
						</div>
						<div class="col-md-4" id="show-userInfo-1">
							<h4 class="text-warning">
								Nick name:&nbsp;&nbsp;<small id="nickname"></small>
							</h4>
							<h4 class="text-muted">
								Blog:&nbsp;&nbsp;<small id="blog"></small>
							</h4>
							<h4 class="text-success">
								Email:&nbsp;&nbsp;<small id="email"></small>
							</h4>
							<h4 class="text-primary">
								HduUser:&nbsp;&nbsp;<small id="hduuser"></small>
							</h4>
							<h4 class="text-info">
								PojUser:&nbsp;&nbsp;<small id="pojuser"></small>
							</h4>
							<h4 class="text-success">
								VjudgeUser:&nbsp;&nbsp;<small id="vjudgeuser"></small>
							</h4>
						</div>
						<div class="col-md-4" id="show-userInfo-2">
							<h4 class="text-success">
								Rank:&nbsp;&nbsp;<small id="rank"></small>
							</h4>
							<h4 class="text-warning">
								Rating:&nbsp;&nbsp;<small id="rating"></small>
							</h4>
							<h4 class="text-danger">
								Solved:&nbsp;&nbsp;<small id="solved"></small>
							</h4>
							<h4 class="text-primary">
								Submissions:&nbsp;&nbsp;<small id="submissions"></small>
							</h4>
							<h4 class="text-info">
								Contest Rating:&nbsp;&nbsp;<small id="contest-rating"></small>
							</h4>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12 text-right"
						style="padding-top: 10px;padding-bottom: 10px;" id="update-btn">
						<a class="btn btn-default" id="updateInfo" onclick="alterInfo()">修改个人信息</a>
					</div>
				</div>
				<div class="row">
					<div class="block block-danger">
						<div class="block-content">
							<div class="heading">
								User's <span class="ce-text bold">Rating</span>
							</div>
							<div id="line" style="height:500px"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="foot.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
	$(function() {
		var user_id = '<%=session.getAttribute("user_id")%>';
		var userInfo_id = '<%=request.getParameter("user_id")%>';
			if (user_id != "null" && user_id == userInfo_id) {
				$('#updateInfo').show();
				$('#warning').show();
			} else {
				$('#updateInfo').hide();
				$('#warning').hide();
			}
		});
	</script>
</body>
</html>