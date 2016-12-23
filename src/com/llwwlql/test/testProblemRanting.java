package com.llwwlql.test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.computeRanting.AllUserInfo;
import com.llwwlql.computeRanting.ContestRating;
import com.llwwlql.computeRanting.ProblemRating;
import com.llwwlql.service.BaseService;
import com.llwwlql.spider.contest.HduContestInfo;
import com.llwwlql.spider.contest.HduContestLogin;
import com.llwwlql.spider.user.HduUserInfo;
import com.llwwlql.spider.user.PojUserInfo;

import javax.persistence.Entity;

@Entity
public class testProblemRanting {

	public static void main(String[] args) throws IOException {
		BaseService<User> userService = new BaseService<User>();
		User user = userService.getById(User.class, 5);
		//HDU信息赋值
//		BaseService<Hduuser> hduService = new BaseService<Hduuser>();
//		Hduuser hduUser = new Hduuser(user, "llwwlql");
//		user.setHduuser(hduUser);
//		userService.update(user);
//		hduService.save(hduUser);

		//poj信息赋值
//		BaseService<Pojuser> pojService = new BaseService<Pojuser>();
//		Pojuser pojUser = new Pojuser(user, "llwwlql");
//		
//		user.setPojuser(pojUser);
		
//		pojService.save(pojUser);
		
//		ProblemRating problemRating = new ProblemRating(user);
//		new Thread(problemRating).start();
		
//		HduContestInfo hduContestInfo = new HduContestInfo();
//		new Thread(hduContestInfo).start();
//		HduContestLogin hduContestLogin = new HduContestLogin(contest);
		
//		System.out.println(user.getNickName());
//		ContestRating contestRating = new ContestRating(user);
//		new Thread(contestRating).start();
		AllUserInfo allUser = new AllUserInfo();
		new Thread(allUser).start();
	}
}
