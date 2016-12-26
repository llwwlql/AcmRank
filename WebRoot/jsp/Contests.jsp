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

	<title>Contests</title>
<jsp:include page="Header.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="../js/show_contest.js"></script>
</head>
<body>

<div class="everything">
	
	<div class="main">
	
	<div class="container">
		<div class="row block block-info">
			<div class="form-inline">
				<div class="pull-right pad"> 
					<form name="" action="http://www.sdutacm.org/onlinejudge2/index.php/Home/Contest/contestlist" method="get">
					<div class="input-group input-group-sm">
						<span class="input-group-addon">Name</span>
						<input class="form-control" type="text" placeholder="Contest Name" name="contestname" value="">
					</div>
					<div class="input-group input-group-sm">
					 	<span class="input-group-addon">Type</span>
						 <select class="form-control" name="contesttype">
							 <option value="">All</option>
							 <option class="bold wrong-text" value="3">Public</option>
							 <option class="bold accept-text" value="1">Private</option>
							 <option class="bold pe-text" value="2">Register</option>
						 </select>
					 </div>  
					<div class="input-group input-group-sm">
						<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
						<input class="form-control" type="text" placeholder="Contest ID" name="cid" value="">
					</div> 
					<button class="btn btn-default btn-sm" type="submit">GO</button> 
					</form>
				</div>
			</div>
		</div>
		<div class="row">
				<table class="table table-bordered table-hover">
				    <thead>
				        <tr>
				            <th style="width:3%">ID</th>
				            <th style="width:43%">Contest name</th>
				            <th style="width:12%">Start Time</th>
				            <th style="width:12%">End Time</th>
				            <th style="width:12%">Origin</th>
				            <th style="width:6%">Rank</th>
				        </tr>
				    </thead>
				    <tbody id="tbody">
				    </tbody>
				    <tfoot>
					    <tr>
					    	<td colspan="6">
					    	<div class="bootpage ">
							<div class="btn-group btn-group-sm">  
							<!-- 
								<span class="current btn btn-primary">1</span>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=2">2</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=3">3</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=4">4</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=5">5</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=6">6</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=7">7</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=8">8</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=9">9</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=10">10</a>
								<a class="num btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=11">11</a>
								<a class="next btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=2">下一页</a>
								<a class="end btn btn-default" href="http://www.sdutacm.org/onlinejudge2/index.php?m=&amp;c=Contest&amp;a=contestlist&amp;p=14">14</a>
							-->
							</div>							
							<script type="text/javascript">
								$(".bootpage div").addClass("btn-group btn-group-sm");
								$(".bootpage a").addClass("btn btn-default");
								$(".bootpage span").addClass("btn btn-primary");
							</script>
						</div>
					    	</td>
					    </tr>
				    </tfoot>
				</table>
		</div>
	</div>

	</div>

	<div class="footer">
		<div class="container">
		        <div class="row">
		            <div class="col-sm-11 text-center">
		                <a href="http://www.sdutacm.org/onlinejudge2/index.php/Home/Contest/contestlist#">SDUTACM运维技术中心</a>
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