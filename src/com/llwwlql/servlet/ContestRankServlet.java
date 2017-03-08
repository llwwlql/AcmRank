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
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		int id;
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
			id = 0;
			if(contestuser.getUser()!=null)
				id = contestuser.getUser().getId();
			contestRank = new ContestRankInfo(id, contestuser.getRank(), contestuser.getUserName(),contestuser.getSolved(), contestuser.getPenalty(), contestproblems);
			crInfo.add(contestRank);
		}
		Gson gson = new Gson();
		String json = gson.toJson(crInfo);
		out.write(json);
		out.flush();
		out.close();
	}
}
