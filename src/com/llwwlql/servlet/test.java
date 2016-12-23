package com.llwwlql.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class test extends HttpServlet {

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
		
//		StringBuffer str = new StringBuffer();
//		BaseService<Contest> contestService = new BaseService<Contest>();
//		QueryResult<Contest> qResult = contestService.findAll("Contest", 1, 10);
//		List<Contest> contests = qResult.getList();
//		ContestGson conGson = new ContestGson();
//		Gson gson = conGson.getGson();
//		
//		for (Contest contest : contests) {
//			str.append(gson.toJson(contest));
//		}
//		out.write(str.toString());
		System.out.println("********");
		out.write("11111");
		out.flush();
		out.close();
	}

}
