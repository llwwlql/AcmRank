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
<title>Welcome to Ludong University ACM Training System!</title>
<jsp:include page="Header.jsp"></jsp:include>
<body>
	<div class="everything">
		<div class="main">
			<div class="container">
			<div class="row">
                    <h1>Welcome to LDU ACM Training System!</h1>
                    <p>Please <a href="register.jsp">register</a> or <a href="login.jsp">log in</a> to practice programming skills or participate in a <a href="Contests.jsp">contest</a>.<br>
                    Anonymous visitors (without logging in ) can only check Problems,Standings, and Status</a>.<br>
                    As a registered user, you can submit your source code and see if your solution is correct. Should you have any questions, please <a href="">contact</a> us.<br>
                    Please use Chrome, Firefox, IE (above IE9) or other browsers supporting Bootstrap 3.
                    </p>
                    <br>
             </div>
             <div class="text-center">
             	<img src="img/body_1.jpg" class="img-rounded" style="width: 80%; height: 500px;">
             </div>
			 </div>		
		</div>
		<div style="padding-top: 30px;"></div>
		<jsp:include page="foot.jsp"></jsp:include>
	</div>
</body>
</html>