package com.llwwlql.computeRanting;

import com.llwwlql.bean.Hduuser;
import com.llwwlql.bean.Pojuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.spider.user.HduUserInfo;
import com.llwwlql.spider.user.PojUserInfo;
import com.llwwlql.spider.user.UserSpider;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProblemRating implements Runnable, BaseCompute {

	private int problemAmount = 0;
	private int solved=0;
	private int submissions=0;
	private int rating=0;
	@ManyToOne
	private User user = null;

	protected ProblemRating() {
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 构造函数，传入User对象
	 * 
	 * @param user
	 */
	public ProblemRating(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}

	/**
	 * 计算所有OJ解决题目数量 每个oj的数量都统计，先判断该用户是否有该oj账号，这里写的属于硬编码，不适合扩展 
	 * problem！！！！！
	 */
	public void ComputeAmount() {
		BaseService<User> userService = new BaseService<User>();
		if(user.getHduuser()!=null)
		{
			BaseService<Hduuser> hduService = new BaseService<Hduuser>();
			HduUserInfo userInfo = new HduUserInfo(user.getHduuser());
			userInfo.doGet();
			//更改HduUser信息
			hduService.update(user.getHduuser());
			//更改User信息
			solved = solved + user.getHduuser().getHduSolve();
			submissions = submissions + user.getHduuser().getHduSubmission();
		}
		if(user.getPojuser()!=null)
		{
			BaseService<Pojuser> pojService = new BaseService<Pojuser>();
			PojUserInfo pojInfo = new PojUserInfo(user.getPojuser());
			pojInfo.doGet();
			//更改PojUser信息
			pojService.update(user.getPojuser());
			//更改User信息
			solved = solved + user.getPojuser().getPojSolved();
			submissions = submissions + user.getPojuser().getPojSubmission();			
		}
		//更改数据库中User信息
		user.setSolved(solved);
		user.setSubmissions(submissions);
		userService.update(user);
	}
	
	/**
	 * 该函数用于计算Rating
	 */
	public void Compute() {
		BaseService<User> userService = new BaseService<User>();
		
		rating = rating + user.getSolved();
		
		//更新数据库中User信息
		user.setRating(rating);
		userService.update(user);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		this.ComputeAmount();
		this.Compute();
	}

}
