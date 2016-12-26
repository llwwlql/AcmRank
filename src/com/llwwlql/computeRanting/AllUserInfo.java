package com.llwwlql.computeRanting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Log;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.spider.contest.HduContestInfo;
import com.llwwlql.spider.contest.HduContestLogin;
import com.llwwlql.spider.contest.VjudegeContestInfo;
import com.llwwlql.tool.Property;
import com.llwwlql.tool.SaveLog;

public class AllUserInfo implements Runnable{

	private static List<User> users;
	private static BaseService<User> userService;
	private static BaseService<Contest> contestService;
	private static BaseService<Contestuser> cUserService;

	static {
		userService = new BaseService<User>();
		contestService = new BaseService<Contest>();
		cUserService = new BaseService<Contestuser>();
		users = userService.findAll("User");
	}

	public void updateUserInfo() {
		ContestRating contestRating =null;
		ProblemRating problemRating = null;
		for (User user : users) {
			contestRating = new ContestRating(user);
			contestRating.run();
			problemRating = new ProblemRating(user);
			problemRating.run();
		}
		UserRank userRank = new UserRank();
		userRank.Compute();
	}
	
	public void updateContestInfo() {
		//HduContestInfo hduContestInfo = new HduContestInfo();
		//hduContestInfo.run();

		VjudegeContestInfo vjudgeContestInfo = new VjudegeContestInfo();
		vjudgeContestInfo.run();
	}

	public void updateCUInfo() {
		List<Contest> contests = contestService.findAll("Contest");
		HduContestLogin hduContestLogin = null;
		for (Contest contest : contests) {
			if(contest.getOrigin()==1)
			{
				hduContestLogin = new HduContestLogin(contest);
				hduContestLogin.run();
			}
				
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		this.updateContestInfo();
		this.updateCUInfo();
		this.updateUserInfo();
	}

}
