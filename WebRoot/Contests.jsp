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
<title>Contests Welcome to Ludong University ACM Training System!</title>
<jsp:include page="Header.jsp"></jsp:include>
<script language="javascript" type="text/javascript"
	src="js/show_contest.js"></script>
<script language="javascript" type="text/javascript"
	src="js/bootstrap-datetimepicker.js" media="screen"></script>
<link type="text/css" rel="stylesheet"
	href="css/bootstrap-datetimepicker.css">
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
									<input
										class="form-control" type="text" placeholder="Contest Name"
										id="contestname" value="">
								</div>
								<div class="form-group input-group">
									<span class="input-group-addon">Date</span>
									<div class="input-group date form_date input-group-sm" data-date=""
										data-date-format="yyyy-mm-dd" data-link-field="dtp_input2"
										data-link-format="yyyy-mm-dd">
										<input id="date" class="form-control" placeholder="Start Time" type="text" value=""
											readonly> <span class="input-group-addon"><span
											class="glyphicon glyphicon-remove"></span>
										</span> <span class="input-group-addon"><span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
									<input type="hidden" id="dtp_input2" value="" /><br />
								</div>
								<button class="btn btn-default btn-sm" type="button" id="ser-contest">GO</button>
							</form>
						</div>
					</div>
				</div>
				<div class="row">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th style="width:3%">ID</th>
								<th style="width:37%">Contest name</th>
								<th style="width:6%"></th>
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
								<td colspan="7">
									<div class="bootpage">
										<div class="btn-group btn-group-sm" id="page-div"></div>
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
	<script type="text/javascript">
		$('.form_date').datetimepicker({
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0
		});
	</script>
</body>
</html>