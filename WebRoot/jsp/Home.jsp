<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title>Contests</title>
<jsp:include page="Header.jsp"></jsp:include>
<body>
	<div class="everything">
		<div class="main">
			<div class="container">
			<div class="row">
                    <h1>Welcome to LDU OnlineJudge!</h1>
                    <p>Please <a href="../jsp/register.jsp">register</a> or <a href="../jsp/login.jsp">log in</a> to practice programming skills or participate in a <a href="/onlinejudge2/index.php/Home/Contest/contestlist">contest</a>.<br>
                    Anonymous visitors (without logging in ) can only check Problems,Standings, and Status</a>.<br>
                    As a registered user, you can submit your source code and see if your solution is correct. Should you have any questions, please <a href="">contact</a> us.<br>
                    Please use Chrome, Firefox, IE (above IE9) or other browsers supporting Bootstrap 3.
                    </p>
                    <br>
             </div>
             <div class="text-center">
             	<img src="../img/body_1.jpg" class="img-rounded" style="width: 80%; height: 500px;">
             </div>
			 </div>		
		</div>
		<div style="padding-top: 30px;"></div>
		<jsp:include page="../jsp/foot.jsp"></jsp:include>
	</div>
</body>
</html>