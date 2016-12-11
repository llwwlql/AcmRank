package com.llwwlql.test;

import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.computeRanting.ProblemRating;
import com.llwwlql.service.BaseService;
import javax.persistence.Entity;

@Entity
public class testProblemRanting {

	public static void main(String[] args) {
		int count = 2;
		BaseService<User> userService = new BaseService<User>();
		BaseService<Pojuser> pojService = new BaseService<Pojuser>();
//		/**
//		 * ≤‚ ‘±£¥Ê
//		 */
		User user = userService.getById(User.class, count);
//		Pojuser pojUser = new Pojuser(user,"llwwlql");
//		
//		user.setPojuser(pojUser);
//		
//		pojService.save(pojUser);
//		userService.update(user);
		
		System.out.println(user);
		ProblemRating problem = new ProblemRating(user);
		problem.run();
		//problem.ComputeAmount();
		System.out.println(user);
	}
}
