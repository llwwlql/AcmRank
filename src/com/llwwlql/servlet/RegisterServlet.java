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
import com.llwwlql.computeRanting.AllUserInfo;
import com.llwwlql.computeRanting.CPRating;
import com.llwwlql.computeRanting.ContestRating;
import com.llwwlql.computeRanting.ProblemRating;
import com.llwwlql.service.BaseService;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
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
		PrintWriter out = response.getWriter();
		int id = (Integer) request.getSession().getAttribute("user_id");
		String hduUsername = request.getParameter("hduUsername");
		String pojUsername = request.getParameter("pojUsername");
		String VjudgeUsername = request.getParameter("VjudgeUsername");
		String blog = request.getParameter("blog");
		String motto = request.getParameter("motto");
		
		JsonObject jsonObject = new JsonObject();
		
		try {
			//更新HduUser、PojUser、VjudgeUser
			//===========================
			BaseService<User> userSerivce = new BaseService<User>();
			BaseService<Hduuser> HUSerivce = new BaseService<Hduuser>();
			BaseService<Pojuser> PUSerivce = new BaseService<Pojuser>();
			BaseService<Vjudgeuser> VJUSerivce = new BaseService<Vjudgeuser>();
			
			User user = userSerivce.getById(User.class, id);
			user.setUserBlog(blog);
			user.setMotto(motto);
			Hduuser hduUser = user.getHduuser();
			Pojuser pojUser = user.getPojuser();
			Vjudgeuser vjudgeUser = user.getVjudgeuser();
			
			userSerivce.update(user);
			HUSerivce.update(hduUser);
			PUSerivce.update(pojUser);
			VJUSerivce.update(vjudgeUser);
			//============================
			request.getSession().setAttribute("user_id", user.getId());
			request.getSession().setAttribute("nickName", user.getNickName());
			
			//获取User信息
			ContestRating contestRating = new ContestRating(user);
			ProblemRating problemRating = new ProblemRating(user);
			CPRating cpRating = new CPRating(user);
			contestRating.run();
			cpRating.run();
			problemRating.run();
			
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
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			String enPassword = base64en.encode(md5.digest(password));

			BaseService<Hduuser> hduService = new BaseService<Hduuser>();
			BaseService<Pojuser> pojService = new BaseService<Pojuser>();
			BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
			
			BaseService<User> userSerivce = new BaseService<User>();
			long count = userSerivce.findAllCount("User");
			
			User user = new User(userName, nickName, enPassword, email, 0, 0,
					(short) 1);
			user.setRank((int)count);
			user.setSubmissions(0);
			user.setSolved(0);
			user.setContestRating(0);
			user.setRating(0);			
			Hduuser hduUser = new Hduuser(user,"");
			Pojuser pojUser = new Pojuser(user,"");
			Vjudgeuser vjudgeUser = new Vjudgeuser(user,"");
			user.setHduuser(hduUser);
			user.setPojuser(pojUser);
			user.setVjudgeuser(vjudgeUser);
			userSerivce.save(user);
			hduService.save(hduUser);
			pojService.save(pojUser);
			vjudgeService.save(vjudgeUser);
			
			System.out.println("保存用户HDU/POJ/VJUDGE信息成功");
			List<User> user2 = userSerivce.getByParameter("User", "userName",
					user.getUserName());
			user = user2.get(0);
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("result", 1);
			request.getSession().setAttribute("user_id", user2.get(0).getId());
			request.getSession().setAttribute("nickName",nickName);
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