package com.llwwlql.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;
import com.llwwlql.tool.BaseGson;
import com.llwwlql.tool.Property;

public class RankListServlet extends HttpServlet {

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
		int type = Integer.parseInt(request.getParameter("type"));
		if(type == 1)
			this.showRank(request, response);
		else if(type==2)
			this.searchContest(request, response);
	}
	
	public void showRank(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int page = Integer.parseInt(request.getParameter("page"));
		BaseService<User> userService =new BaseService<User>();
		QueryResult<User> qResult = userService.rankfindAll("User", 50*(page-1), 50);
		BaseGson userGson = new BaseGson();
		userGson.userGson();
		Gson gson = userGson.getGson();
		String str = gson.toJson(qResult);
		out.write(str.toString());
		out.flush();
		out.close();
	}
	
	public void searchContest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String nickName = new String(request.getParameter("nickName").getBytes("iso-8859-1"), "utf-8");
		BaseService<User> userService =new BaseService<User>();
		List<User> users = userService.getByParameter("User", "nickName", nickName);
		BaseGson userGson = new BaseGson();
		userGson.userGson();
		Gson gson = userGson.getGson();
		String str = gson.toJson(users);
		out.write(str);
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
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
