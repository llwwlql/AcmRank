<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!-- saved from url=(0070)http://www.sdutacm.org/onlinejudge2/index.php/Home/Contest/contestlist -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta http-equiv="X-UA-Compatible" content="IE=9">
	<title>Ranklist Welcome to Ludong University ACM Training System!</title>
<jsp:include page="Header.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="js/show_ranklist.js"></script>
</head>
<body>
<div class="everything">
	<div class="main">
	
	<div class="container">
		<div class="row block block-info">
			<div class="form-inline">
				<div class="pull-right pad"> 
					<form>
					<div class="input-group input-group-sm">
						<span class="input-group-addon">Name</span>
						<input class="form-control" id="nickName" type="text" placeholder="UserName" name="contestname" value="">
					</div>
					<button class="btn btn-default btn-sm" type="button" id="ser-user">GO</button> 
					</form>
				</div>
			</div>
		</div>
		<div class="row">
				<table class="table table-bordered table-hover text-center">
				    <thead>
				        <tr>
				            <th style="width:3%;text-align:center;">Rank</th>
				            <th style="width:10%;text-align:center;">Nickname</th>
				            <th style="width:31%;text-align:center;">Motto</th>
				            <th style="width:6%;text-align:center;">Solved</th>
				            <th style="width:6%;text-align:center;">Submitted</th>
				            <th style="width:8%;text-align:center;">Contest Rating</th>
				            <th style="width:6%;text-align:center;">Rating</th>
				        </tr>
				    </thead>
				    <tbody id="tbody">
				    </tbody>
				    <tfoot>					
					    <tr>
					    	<td colspan="7">
					    	<div class="bootpage">
								<div class="btn-group btn-group-sm" id="page-div">
								</div>
							</div>
					    	</td>
					    </tr>
				    </tfoot>
				</table>
		</div>
	</div>

	</div>

	<jsp:include page="foot.jsp"></jsp:include>
</div>


</body></html>