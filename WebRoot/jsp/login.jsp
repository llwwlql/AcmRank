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
<jsp:include page="Header.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="../js/login.js"></script>
</head>
<body>
	<div class="everything">
	<div class="main">
	
<div style="padding-top: 80px;"></div>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <h1 class="text-center">OnlineJudge Login</h1>
        	<form  action="" method="post" class="form-horizontal ">
                    <div class="form-group">
                        <label class="col-md-2 control-label">UserName:</label>
                        <div class="col-md-10">
                        <input type="text" name="user_name" id="user_name" class="form-control" data-toggle="tooltip" data-placement="bottom" title="用户名不存在，请重新输入"></input>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">Password:</label>
                        <div class="col-md-10">
                        <input type="password" name="password" id="password" class="form-control" data-toggle="tooltip" data-placement="bottom" title="输入密码有误，请重新输入"></input>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-10 col-md-offset-2">    
                            <button type="button" id="btn-login" class="btn btn-primary btn-bg">&nbsp;Login&nbsp;</button>
                        </div>
                    </div>
            </form>
        </div>
    </div>
</div>
<div style="padding-top: 80px;"></div>
</div>
	<jsp:include page="../jsp/foot.jsp"></jsp:include>
</div>
</body>
</html>
