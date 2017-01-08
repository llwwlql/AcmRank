package com.llwwlql.computeRanting;

import java.util.List;
import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.spider.contest.HduContestInfo;
import com.llwwlql.spider.contest.HduContestUserInfo;
import com.llwwlql.spider.contest.VjudegeContestInfo;

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
		CPRating cpRating = null;
		for (User user : users) {
			contestRating = new ContestRating(user);
			contestRating.run();
			cpRating = new CPRating(user);
			cpRating.run();
			problemRating = new ProblemRating(user);
			problemRating.run();
		}
		UserRank userRank = new UserRank();
		userRank.Compute();
	}
	
	public void updateContestInfo() {
		HduContestInfo hduContestInfo = new HduContestInfo();
		hduContestInfo.run();
		VjudegeContestInfo vjudgeContestInfo = new VjudegeContestInfo();
		vjudgeContestInfo.run();
	}

	public void updateCUInfo() {
		List<Contest> contests = contestService.findAll("Contest");
		HduContestUserInfo hduContestLogin = null;
		for (Contest contest : contests) {
			if(contest.getOrigin()==1)
			{
				hduContestLogin = new HduContestUserInfo(contest);
				hduContestLogin.run();
			}
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("更新Contest信息");
		this.updateContestInfo();
		System.out.println("更新ContestUser信息和ContestProblem信息");
		this.updateCUInfo();
		System.out.println("更新User信息");
		this.updateUserInfo();
		System.out.println("所有信息更新完成");
	}

}
