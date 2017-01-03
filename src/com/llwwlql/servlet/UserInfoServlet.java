package com.llwwlql.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.llwwlql.bean.User;
import com.llwwlql.bean.UserInfo;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;
import com.llwwlql.tool.BaseGson;

public class UserInfoServlet extends HttpServlet {

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

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("user_id"));
		BaseService<User> userService =new BaseService<User>();
		User user = userService.getById(User.class, id);
		UserInfo userInfo = new UserInfo(user.getId(), user.getVjudgeuser().getVjudgeUserName(), user.getPojuser().getPojUserName(), user.getHduuser().getHduUserName(), user.getRank(), user.getNickName(), user.getUserBlog(), user.getMotto(), user.getSolved(), user.getSubmissions(), user.getRating(), user.getEmail(),user.getContestRating()); 
		BaseGson userGson = new BaseGson();
		userGson.userInfoGson();
		Gson gson = userGson.getGson();
		String str = gson.toJson(userInfo);
		out.write(str.toString());
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
				
		out.flush();
		out.close();
	}

}
