package com.llwwlql.computeRanting;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.spider.contest.HduContestInfo;
import com.llwwlql.spider.contest.HduContestUserInfo;
import com.llwwlql.spider.contest.VjudegeContestInfo;
import com.llwwlql.spider.contest.VjudgeCUInfo;
import com.llwwlql.tool.CrawlerIp;

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
		HduContestUserInfo hduCUInfo = null;
		VjudgeCUInfo vjudgeCUInfo = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		for (Contest contest : contests) {
			if(contest.getEndTime().compareTo(time) < 0)
			{
				if(contest.getOrigin()==1)
				{
					hduCUInfo = new HduContestUserInfo(contest);
					hduCUInfo.run();
				}
				if(contest.getOrigin()==2)
				{
					vjudgeCUInfo = new VjudgeCUInfo(contest);
					vjudgeCUInfo.run();				
				}
			}
		}
	}

	public void run() {
		System.out.println("获取代理IP");
		CrawlerIp crawler = new CrawlerIp();
		//获取代理IP
		try {
			crawler.crawler();
			crawler.analysis();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("更新Contest信息");
		this.updateContestInfo();
		System.out.println("更新ContestUser、ContestProblem信息");
		this.updateCUInfo();
		System.out.println("更新User信息");
		this.updateUserInfo();
		System.out.println("所有信息更新完成");
	}

}
