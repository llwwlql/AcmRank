package com.llwwlql.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.ContestRankInfo;
import com.llwwlql.bean.Contestproblem;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.CPComparator;
import com.llwwlql.tool.CUComparator;

public class ContestRankServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		int type = Integer.parseInt(request.getParameter("type"));
		if(type==1)
			this.contestRank(request, response);
		out.flush();
		out.close();
	}

	public void contestRank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int contestId = Integer.parseInt(request.getParameter("contest_id"));
		
		BaseService<Contest> userService = new BaseService<Contest>();
		Contest contest = userService.getById(Contest.class, contestId);
		List<Contestuser> contestUsers = new ArrayList<Contestuser>(contest.getContestusers());
		Collections.sort(contestUsers,new CUComparator());
		List<ContestRankInfo> crInfo = new ArrayList<ContestRankInfo>();
		ContestRankInfo contestRank =null;
		for (Contestuser contestuser : contestUsers) {
			List<Contestproblem> contestproblems = new ArrayList<Contestproblem>(contestuser.getContestproblems());
			Collections.sort(contestproblems,new CPComparator());
			contestRank = new ContestRankInfo(contestuser.getRank(), contestuser.getUserName(),contestuser.getSolved(), contestuser.getPenalty(), contestproblems);
			crInfo.add(contestRank);
		}
		Gson gson = new Gson();
		String json = gson.toJson(crInfo);
		out.write(json);
		
		out.flush();
		out.close();
	}
}
