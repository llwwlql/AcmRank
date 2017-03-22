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

<title>MoreInfo Welcome to Ludong University ACM Training System!</title>
<jsp:include page="Header.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="js/register.js"></script>
</head>
<body>
<div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
           注册成功！已完成基本信息注册，请完善您的详细信息。注意，详细信息将影响Rating的值。也可跳过填写，直接提交。
</div>
<div class="everything">
		<div class="main">
			<div style="padding-top: 10px;"></div>
			<h1 class="text-center" style="padding-bottom: 30px;">Detailed Information</h1>
			<div class="container">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">

						<form class="form-horizontal">
							<div class="form-group">
								<label class="col-md-2 control-label">Hdu UserName:</label>
								<div class="col-md-10">
									<input type="text" name="hduUsername" placeholder="请输入Hdu Online Judge的用户名" class="form-control" data-toggle="tooltip" data-placement="bottom" title="用户名格式不正确,字母数字组合 6-16个字符">
									<a class="navbar-right" href="http://acm.hdu.edu.cn/userloginex.php?action=login" target=_blank style="padding-right: 25px;"><small>忘记请点我</small></a>
                                </div>
                            </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Poj UserName:</label>
                                            <div class="col-md-10">
                                            <input type="text" name="pojUsername" placeholder="请输入Peking Online Judge的用户名" class="form-control">
                                            <a class="navbar-right" href="http://poj.org/" target=_blank style="padding-right: 25px;"><small>忘记请点我</small></a>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Vjudge UserName:</label>
                                            <div class="col-md-10">
                                            <input type="text" name="VjudgeUsername" placeholder="请输入Virtual Judge的用户名" class="form-control" data-toggle="tooltip" data-placement="bottom" title="用户名格式不正确,字母数字组合 2-16个字符">
                                            <a class="navbar-right" href="https://vjudge.net/" target=_blank style="padding-right: 25px;"><small>忘记请点我</small></a>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Blog:</label>
                                            <div class="col-md-10">
                                            <input type="text" name="blog" class="form-control" placeholder="请输入博客地址（输入完整地址）">
                                            </div>
                                        </div>
                                        <div class="form-group" style="margin-top: 27px;">
                                            <label class="col-md-2 control-label">Motto:</label>
                                            <div class="col-md-10">
                                            <textarea class="form-control" name="motto" placeholder="请输入座右铭" style="height: 100px;" data-toggle="tooltip" data-placement="bottom" title="座右铭太长,小于255个英文或汉字"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-10 col-md-offset-2">    
                                            <button type="button" id="btn-submit" class="btn btn-primary btn-bg" id="btn-register">Submit</button>
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