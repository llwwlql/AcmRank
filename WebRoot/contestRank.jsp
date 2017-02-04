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
<title>Ranklist</title>
<jsp:include page="Header.jsp"></jsp:include>
<script language="javascript" type="text/javascript"
	src="js/show_contestRank.js"></script>
</head>

<body>
	<div class="everything">
		<div class="main">
			<div class="container">
				<div class="row block block-info">
				</div>
				<div class="row">
					<table class="table table-bordered table-hover text-center">
						<thead>
							<tr id="table-head">
								<th style="width:3%;text-align:center;">Rank</th>
								<th style="width:10%;text-align:center;">Nickname</th>
								<th style="width:3%;text-align:center;">Solved</th>
								<th style="width:8%;text-align:center;">Penalty</th>
							</tr>
						</thead>
						<tbody id="tbody">
						</tbody>
					</table>
				</div>
			</div>

		</div>
		<jsp:include page="foot.jsp"></jsp:include>
	</div>

</body>
</html>
