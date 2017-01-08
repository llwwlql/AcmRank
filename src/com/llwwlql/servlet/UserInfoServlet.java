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
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Log;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.bean.UserInfo;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.computeRanting.AllUserInfo;
import com.llwwlql.computeRanting.CPRating;
import com.llwwlql.computeRanting.ContestRating;
import com.llwwlql.computeRanting.ProblemRating;
import com.llwwlql.computeRanting.UserRank;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.QueryResult;
import com.llwwlql.spider.user.HduUserInfo;
import com.llwwlql.spider.user.PojUserInfo;
import com.llwwlql.spider.user.VjudegeUserInfo;
import com.llwwlql.tool.BaseGson;
import com.llwwlql.tool.Property;

public class UserInfoServlet extends HttpServlet {
	private User user;

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

		response.setContentType("text/html;charset=utf-8");
		int type = Integer.parseInt(request.getParameter("type"));
		if (type == 1)
			this.userInfo(request, response);
		else if (type == 2)
			this.chartData(request, response);
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
		if (type == 3)
			this.updateInfo(request, response);
	}

	private void userInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("user_id"));
		BaseService<User> userService = new BaseService<User>();
		User user = userService.getById(User.class, id);
		
		//转成json字符串的信息
		UserInfo userInfo = new UserInfo(user.getId(), user.getVjudgeuser()
				.getVjudgeUserName(), user.getPojuser().getPojUserName(), user
				.getHduuser().getHduUserName(), user.getRank(),
				user.getNickName(), user.getUserBlog(), user.getMotto(),
				user.getSolved(), user.getSubmissions(), user.getRating(),
				user.getEmail(), user.getContestRating(), user.getHduuser()
						.getHduType(), user.getPojuser().getPojType(), user
						.getVjudgeuser().getVjudgeType());
		BaseGson userGson = new BaseGson();
		userGson.userInfoGson();
		Gson gson = userGson.getGson();
		String str = gson.toJson(userInfo);
		out.write(str.toString());
		out.flush();
		out.close();
	}

	private void chartData(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("user_id"));
		BaseService<Log> logService = new BaseService<Log>();
		BaseService<User> userService = new BaseService<User>();
		User user = userService.getById(User.class, id);
		List<Log> logs = logService.findAllSort("Log", "user", user, "time",
				"asc");
		BaseGson logGson = new BaseGson();
		logGson.logGson();
		Gson gson = logGson.getGson();
		String json = gson.toJson(logs);
		out.write(json);

		out.flush();
		out.close();
	}

	private void updateInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("user_id"));
		String nickName = request.getParameter("nickName");
		String hduName = request.getParameter("hduUser");
		String pojName = request.getParameter("pojUser");
		String vjudgeName = request.getParameter("vjudgeUser");
		String email = request.getParameter("email");
		String blog = request.getParameter("blog");

		BaseService<User> userService = new BaseService<User>();

		user = userService.getById(User.class, id);

		int peace = 0;
		if (!user.getHduuser().getHduUserName().equals(hduName)) {
			peace = 1;
			System.out.println("更新HduName");
			this.updateHdu(hduName);
			HduUserInfo userInfo = new HduUserInfo(user);
			userInfo.run();
		}
		if (!user.getPojuser().getPojUserName().equals(pojName)) {
			peace = 1;
			System.out.println("更新PojName");
			this.updatePoj(pojName);
			PojUserInfo pojInfo = new PojUserInfo(user);
			pojInfo.run();
		}
		if (!user.getVjudgeuser().getVjudgeUserName().equals(vjudgeName)) {
			peace = 1;
			System.out.println("更新VjudgeName");
			this.updateVjudge(vjudgeName);
			VjudegeUserInfo vjudgeInfo = new VjudegeUserInfo(user);
			vjudgeInfo.run();
		}
		user.setNickName(nickName);
		user.setUserBlog(blog);
		user.setEmail(email);
		userService.update(user);
		if (peace == 1) {
			new Thread() {
				public void run() {
					ContestRating contestRating = new ContestRating(user);
					System.out.println("更新contestRating");
					contestRating.run();
					CPRating cpRating = new CPRating(user);
					System.out.println("更新cpRating");
					cpRating.run();
					ProblemRating problemRating = new ProblemRating(user);
					System.out.println("更新problemRating");
					problemRating.run();
					UserRank userRank = new UserRank();
					userRank.Compute();
				}
			}.start();
		}
	}

	private void updateHdu(String hduName) {
		Hduuser hduUser = user.getHduuser();
		BaseService<Hduuser> hduService = new BaseService<Hduuser>();
		//删除之前Hdu的Log信息
		this.updateLog((short)3);
		this.updateLog((short)5);
		
		hduService.delete(hduUser);
		user.setCpRating(0);
		Hduuser hduNewUser = new Hduuser(user, hduName);
		hduService.save(hduNewUser);
		user.setHduuser(hduNewUser);
	}

	private void updatePoj(String pojName) {
		BaseService<Pojuser> pojService = new BaseService<Pojuser>();
		Pojuser pojUser = user.getPojuser();
		//删除之前Poj的Log信息
		this.updateLog((short)4);
		pojService.delete(pojUser);
		Pojuser pojNewUser = new Pojuser(user, pojName);
		pojService.save(pojNewUser);
		user.setPojuser(pojNewUser);
	}

	private void updateVjudge(String vjudgeName) {
		Vjudgeuser vjudgeUser = user.getVjudgeuser();
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
		//删除之前Vjudge的Log信息
		this.updateLog((short)4);
		vjudgeService.delete(vjudgeUser);
		Vjudgeuser vjudgeNewUser = new Vjudgeuser(user, vjudgeName);
		vjudgeService.save(vjudgeNewUser);
		user.setVjudgeuser(vjudgeNewUser);
	}
	private void updateLog(short origin)
	{
		BaseService<Log> logService = new BaseService<Log>();
		Map <String,Object> propertyVlaue = new HashMap<String, Object>();
		propertyVlaue.put("origin", origin);
		propertyVlaue.put("user", user);
		String[] key = Property.getProperty(propertyVlaue);
		Object[] value = Property.getValue(propertyVlaue);
		List<Log> logs = logService.getByParameters("Log", key, value, true);
		for (Log log : logs) {
			logService.delete(log);
		}
	}
}
