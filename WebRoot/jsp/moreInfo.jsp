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

<title>moreInfo</title>
<jsp:include page="Header.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="../js/register.js"></script>
</head>
<body>
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
                                </div>
                            </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Poj UserName:</label>
                                            <div class="col-md-10">
                                            <input type="text" name="pojUsername" placeholder="请输入Peking Online Judge的用户名" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Vjudge UserName:</label>
                                            <div class="col-md-10">
                                            <input type="text" name="VjudgeUsername" placeholder="请输入Virtual Judge的用户名" class="form-control" data-toggle="tooltip" data-placement="bottom" title="用户名格式不正确,字母数字组合 2-16个字符">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Blog:</label>
                                            <div class="col-md-10">
                                            <input type="text" name="blog" class="form-control" placeholder="请输入博客地址（不需要加'http://'）">
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
	<div class="footer">
		<div class="container">
		        <div class="row">
		            <div class="col-sm-11 text-center">
		                <a href="http://www.sdutacm.org/onlinejudge2/index.php/Home/User/register#">SDUTACM运维技术中心</a>
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