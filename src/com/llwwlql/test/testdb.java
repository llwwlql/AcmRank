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
		
		BaseService<User> userSerivce= new BaseService<User>();
		
		User user = userSerivce.getById(User.class, 5);
		System.out.println(user);
		
		/*AllUserInfo allUserInfo = new AllUserInfo();
		new Thread(allUserInfo).start();*/
	}
}