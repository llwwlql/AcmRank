package com.llwwlql.computeRanting;

import javax.persistence.ManyToOne;

import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.User;
import com.llwwlql.bean.Vjudgeuser;
import com.llwwlql.service.BaseService;

public class ContestRating implements BaseCompute,Runnable{

	private String vjudgeUserName;
	private String hduUserName;
	private int problemAmount = 0;
	private int solved=0;
	private int submissions=0;
	private int rating=0;
	private User user = null;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	protected ContestRating() {
	}
	
	/**
	 * 构造函数，传入User对象
	 * @param user
	 */
	public ContestRating(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}

	public void ComputeContestRank(){
		BaseService<User> userService = new BaseService<User>();
		BaseService<Vjudgeuser> vjudgeService = new BaseService<Vjudgeuser>();
		BaseService<Hduuser> hduService = new BaseService<Hduuser>();
		this.vjudgeUserName = user.getVjudgeuser().getVjudgeUserName();
		this.hduUserName = user.getHduuser().getHduUserName();
		
		
	}
	
	public void Compute() {
		// TODO Auto-generated method stub
		
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}
