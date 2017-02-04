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

<title>register</title>
<jsp:include page="Header.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="js/register.js"></script>
</head>
<body>
<div class="everything">
		<div class="main">
			<div style="padding-top: 10px;"></div>
			<h1 class="text-center" style="padding-bottom: 30px;">OnlineJudge
				Register</h1>
			<div class="container">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">

						<form class="form-horizontal">
							<div class="form-group">
								<label class="col-md-2 control-label">Username:</label>
								<div class="col-md-10">
									<input type="text" name="user_name" placeholder="字母数字组合6-16个字符" class="form-control" data-toggle="tooltip" data-placement="bottom" title="用户名格式不正确,字母数字组合 6-16个字符,或用户名已存在">
                                </div>
                            </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Nickname:</label>
                                            <div class="col-md-10">
                                            <input type="text" name="nick_name" placeholder="字母数字汉字组合2-18个字符" class="form-control" data-toggle="tooltip" data-placement="bottom" title="昵称格式不正确,字母数字汉字组合2-18个字符">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Email:</label>
                                            <div class="col-md-10">
                                            <input type="text" name="email" placeholder="请输入合法邮箱" class="form-control" data-toggle="tooltip" data-placement="bottom" title="邮箱格式不正确">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Password:</label>
                                            <div class="col-md-10">
                                            <input type="password" name="password" class="form-control" placeholder="任意6-16个字符" data-toggle="tooltip" data-placement="bottom" title="密码长度不满足">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Repeat Password:</label>
                                            <div class="col-md-10">
                                            <input type="password" name="repassword" class="form-control" data-toggle="tooltip" data-placement="bottom" title="输入密码不一致">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-10 col-md-offset-2">    
                                            <button type="button" id="btn-register" class="btn btn-primary btn-bg" id="btn-register">Register</button>
                                            </div>
                                        </div>                                        
                            </form>
                   </div>
        	</div>
	</div>
<div style="padding-top: 80px;"></div>

	</div>
	<jsp:include page="foot.jsp"></jsp:include>
</div>
</body></html>