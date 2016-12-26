package com.llwwlql.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

import com.google.gson.JsonObject;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;

public class LoginServlet extends HttpServlet {

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
		int type = Integer.parseInt(request.getParameter("type"));
		if(type==1)
		{
			this.checkUser(request, response);
		}
	}
	
	@SuppressWarnings("unused")
	private void checkUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String userName = request.getParameter("userName");
		String enPassword = this.encode(request.getParameter("password").getBytes("UTF-8"));

		int result = 0;
		BaseService<User> userService = new BaseService<User>();
		List<User> users = userService.getByParameter("User", "userName", userName);
		JsonObject jsonObject = new JsonObject();
		if(users.size()==0)
		{
			result=2;
		}
		else
		{
			User user = users.get(0);
			if(!user.getPassword().equals(enPassword))
			{
				result=3;
			}
			else
			{
				result=1;
				request.getSession().setAttribute("user_id", user.getId());
				request.getSession().setAttribute("nickName", user.getNickName());
			}
		}
		jsonObject.addProperty("result", result);
		String json = jsonObject.toString();
		out.write(json);
		out.flush();
		out.close();
	}
	private String encode(byte[] password)
	{
		String enPassword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			enPassword = base64en.encode(md5.digest(password));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return enPassword;
	}

}
