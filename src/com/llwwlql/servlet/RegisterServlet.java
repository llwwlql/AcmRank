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

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		int type = Integer.parseInt(request.getParameter("type"));
		if (type == 1) {
			this.checkUsername(request, response);
		} else {
			this.saveUserInfo(request, response);
		}
	}

	public void checkUsername(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String json = this
				.getJson("UserName", request.getParameter("UserName"));		
		out.write(json);

		out.flush();
		out.close();
	}

	public void checkNickname(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String json = this
				.getJson("NickName", request.getParameter("NickName"));

		out.write(json);

		out.flush();
		out.close();
	}

	private String getJson(String parameter, String value) {
		BaseService<User> userService = new BaseService<User>();
		List<User> user = userService.getByParameter("User", parameter, value);
		JsonObject jsonObject = new JsonObject();
		
		String isExist = null;
		if (user.size() != 0)
			isExist = "true";
		else
			isExist = "false";

		jsonObject.addProperty("isExist", isExist);
		String json = jsonObject.toString();

		return json;
	}

	private void saveUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String nickName = request.getParameter("nickName");
		byte[] password = request.getParameter("password").getBytes("UTF-8");
		String email = request.getParameter("email");
		System.out.println("userName:" + userName +"\tnickName:"+nickName +"\temail" + email);
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			String enPassword = base64en.encode(md5.digest(password));
			
			BaseService<User> userSerivce = new BaseService<User>();
			
			User user = new User(userName, nickName, enPassword, email, 0, 0);
			userSerivce.save(user);
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("result", 1);
			String json = jsonObject.toString();
			out.print(json);			
		} catch (NoSuchAlgorithmException e) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("result", 0);
			String json = jsonObject.toString();
			out.print(json);
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
}
