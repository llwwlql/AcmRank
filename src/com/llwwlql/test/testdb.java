package com.llwwlql.test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.llwwlql.bean.Contest;
import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Log;
import com.llwwlql.bean.User;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.computeRanting.AllUserInfo;
import com.llwwlql.service.BaseService;
import com.llwwlql.service.IBaseService;
import com.llwwlql.service.QueryResult;
import com.llwwlql.tool.EntityUtil;
import com.llwwlql.tool.Property;

import javax.persistence.Entity;

@Entity
public class testdb {

	public static void main(String[] args) {
		//不定属性查询
		/*
		BaseService<Contest> logService = new BaseService<Contest>();
		Map <String,Object> propertyVlaue = new HashMap<String, Object>();
		propertyVlaue.put("startTime", "2016");
		String[] key = Property.getProperty(propertyVlaue);
		Object[] value = Property.getValue(propertyVlaue);
		
		List<Contest> contest = logService.getByParameters("Contest", key, value, false);
		for (Contest contest2 : contest) {
			System.out.println(contest2.getContestName());
			System.out.println(contest2.getOrginId());
			System.out.println(contest2.getStartTime());
			System.out.println(contest2.getEndTime());
		}*/
//		BaseService<User> userService = new BaseService<User>();
//		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
//		User user = userService.getById(User.class, 5);
//		Vjudgeuser vjudgeUser = new Vjudgeuser(user, "llwwlql");
//		
//		user.setVjudgeuser(vjudgeUser);
//		
//		vjudgeService.save(vjudgeUser);
//		userService.update(user);
		
		AllUserInfo allUserInfo = new AllUserInfo();
		new Thread(allUserInfo).start();
	}
}