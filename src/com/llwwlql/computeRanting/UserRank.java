package com.llwwlql.computeRanting;

import java.util.List;

import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;

public class UserRank implements BaseCompute{

	public void Compute() {
		// TODO Auto-generated method stub
		BaseService<User> userService = new BaseService<User>();
		List<User> users = userService.findAllSort("User", "Rating", "desc");
		int size = users.size();
		for(int i=0;i<size;i++)
			users.get(i).setRank(i+1);
		for (User user : users) {
			userService.update(user);
		}
	}
}
