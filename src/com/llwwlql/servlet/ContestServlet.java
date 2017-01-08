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
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;
import com.llwwlql.tool.BaseGson;
import com.llwwlql.tool.Property;

public class ContestServlet extends HttpServlet {

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
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int type = Integer.parseInt(request.getParameter("type"));
		if(type==1)
			this.showContest(request, response);
		else if(type==2)
			this.searchContest(request, response);
	}
	
	public void showContest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int page = Integer.parseInt(request.getParameter("page"));
		BaseService<Contest> contestService =new BaseService<Contest>();
		QueryResult<Contest> qResult = contestService.contestfindAll("Contest", 50*(page-1), 50);
		BaseGson conGson = new BaseGson();
		conGson.contestGson();
		Gson gson = conGson.getGson();
		String str = gson.toJson(qResult);
		out.write(str.toString());
		out.flush();
		out.close();
	}
	public void searchContest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		BaseService<Contest> contestService =new BaseService<Contest>();
		
		Map <String,Object> propertyVlaue = new HashMap<String, Object>();
		if(request.getParameter("date")!=null && !request.getParameter("date").equals(""))
		{
			String date = new String(request.getParameter("date").getBytes("iso-8859-1"), "utf-8");
			propertyVlaue.put("StartTime", date);
		}
		if(request.getParameter("contestname")!=null && !request.getParameter("contestname").equals(""))
		{
			String contestname = new String(request.getParameter("contestname").getBytes("iso-8859-1"), "utf-8");
			propertyVlaue.put("contestname", contestname);
		}
		String[] key = Property.getProperty(propertyVlaue);
		Object[] value = Property.getValue(propertyVlaue);
		List<Contest> contests = contestService.getByParameters("Contest", key, value, false);
		BaseGson conGson = new BaseGson();
		conGson.contestGson();
		Gson gson = conGson.getGson();
		String str = gson.toJson(contests);
		out.write(str.toString());
		out.flush();
		out.close();
	}
}
