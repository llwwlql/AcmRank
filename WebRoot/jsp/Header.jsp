<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<!-- saved from url=(0070)http://www.sdutacm.org/onlinejudge2/index.php/Home/Contest/contestlist -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta http-equiv="X-UA-Compatible" content="IE=9">


<link type="text/css" rel="stylesheet" href="../css/bootstrap.min.css">
<script language="javascript" type="text/javascript"
	src="../js/jquery-3.0.0.min.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/bootstrap.min.js"></script>
<!--IE -->
<script language="javascript" type="text/javascript"
	src="../js/html5shiv.min.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/respond.min.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/page_div.js"></script>	
<!--IE-->
<link type="text/css" rel="stylesheet" href="../css/home.css">
<link type="text/css" rel="stylesheet" href="../css/base.css">
</style>
</head>
<body>
	<script type="text/javascript">	
		$(function() {
			var user_id = '<%=session.getAttribute("user_id")%>';
			if(user_id!="null")
			{
				var nickname = '<%=session.getAttribute("nickName")%>';
				var userUrl = "../jsp/userInfo.jsp?user_id=" + user_id;
				$('#unlogin').hide();
				$('#show-login').show();
				$('#show-nickname').html(
						"<a href=" + userUrl +">" + nickname + "</a>");d
			} else {
				$('#unlogin').show();
				$('#show-login').hide();
			}
		});

		$(function() {
			$('#logout').click(function() {
				$.ajax({
					type : 'Get',
					url : '../servlet/LogoutServlet',
					success : function(resp) {
						console.log("用户登出成功！");
						window.location.reload();
					},
					error : function(err) {
						console.log("ajax请求错误");
					}
				});
			});
		});
	</script>
	<div class="banner">
		<div class="container"></div>
	</div>

	<nav class="navbar navbar-default" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="../jsp/Home.jsp">Home</a>
			</div>

			<div class="collapse navbar-collapse" id="navbar-collapse">
				<ul class="nav navbar-nav text-primary">
					<li><a href="../jsp/Contests.jsp">Contests</a></li>
					<li><a href="../jsp/Ranklist.jsp">Ranklist</a></li>
					<li><a href="#">Discuss</a></li>
					<li><a href="#">News</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right" id="unlogin">
					<li><a href="../jsp/login.jsp">Login</a></li>
					<li><a href="../jsp/register.jsp">Register</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right" id="show-login">
					<li id="show-nickname"></li>
					<li id="logout"><a href="#">Logout</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</body>
</html>
