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
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.bean.Vjudgeuser;
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
		} else if (type == 2) {
			this.saveUserInfo(request, response);
		} else if (type == 3) {
			this.savaMoreInfo(request, response);
		}
	}

	private void savaMoreInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("user_id"));
		String hduUsername = request.getParameter("hduUsername");
		String pojUsername = request.getParameter("pojUsername");
		String VjudgeUsername = request.getParameter("VjudgeUsername");
		String blog = request.getParameter("blog");
		String motto = request.getParameter("motto");
		
		JsonObject jsonObject = new JsonObject();
		
		try {
			//±£¥ÊHduUser°¢PojUser°¢VjudgeUser
			//===========================
			BaseService<User> userSerivce = new BaseService<User>();
			BaseService<Hduuser> HUSerivce = new BaseService<Hduuser>();
			BaseService<Pojuser> PUSerivce = new BaseService<Pojuser>();
			BaseService<Vjudgeuser> VJUSerivce = new BaseService<Vjudgeuser>();
			
			User user = userSerivce.getById(User.class, id);
			
			Hduuser hduUser = new Hduuser(user, hduUsername);
			Pojuser pojUser = new Pojuser(user, pojUsername);
			Vjudgeuser vjudgeUser = new Vjudgeuser(user, VjudgeUsername);
			
			user.setHduuser(hduUser);
			user.setPojuser(pojUser);
			user.setVjudgeuser(vjudgeUser);
			user.setUserBlog(blog);
			user.setMotto(motto);
			
			userSerivce.update(user);
			HUSerivce.save(hduUser);
			PUSerivce.save(pojUser);
			VJUSerivce.save(vjudgeUser);
			//============================
			request.getSession().setAttribute("user_id", user.getId());
			request.getSession().setAttribute("nickName", user.getNickName());
			
			jsonObject.addProperty("result", 1);
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.addProperty("result", 0);
			e.printStackTrace();
		}finally{
			String json = jsonObject.toString();
			out.print(json);
			out.flush();
			out.close();
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
		System.out.println("userName:" + userName + "\tnickName:" + nickName
				+ "\temail" + email);
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			String enPassword = base64en.encode(md5.digest(password));

			BaseService<User> userSerivce = new BaseService<User>();

			User user = new User(userName, nickName, enPassword, email, 0, 0,
					(short) 1);
			userSerivce.save(user);
			List<User> user2 = userSerivce.getByParameter("User", "userName",
					user.getUserName());
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("result", 1);
			request.getSession().setAttribute("user_id", user2.get(0).getId());
			
			String json = jsonObject.toString();
			out.print(json);
		} catch (NoSuchAlgorithmException e) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("result", 0);
			String json = jsonObject.toString();
			out.print(json);
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
}
