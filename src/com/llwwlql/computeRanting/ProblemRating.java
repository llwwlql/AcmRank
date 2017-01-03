package com.llwwlql.computeRanting;

import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.spider.user.HduUserInfo;
import com.llwwlql.spider.user.PojUserInfo;
import com.llwwlql.spider.user.VjudegeUserInfo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProblemRating implements Runnable,BaseCompute {

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
		if(user.getHduuser()!=null && user.getHduuser().getHduNickName()!="")
		{
			this.hduProRating();
		}
		if(user.getPojuser()!=null && user.getPojuser().getPojUserName()!="")
		{
			this.pojProRating();
		}
		if(user.getVjudgeuser()!=null && user.getVjudgeuser().getVjudgeUserName()!="")
		{
			this.vjudgeProRating();
		}
		//更改对象中User信息
		this.solved = this.solved + user.getCpRating();
		user.setSolved(solved);
		user.setSubmissions(submissions);
	}
	/**
	 * 该函数用于计算Rating
	 */
	public void Compute() {
		BaseService<User> userService = new BaseService<User>();
		
		//题目计算方式
		rating = user.getSolved()*1;
		//更新数据库中User信息
		user.setProblemRating(this.rating);
		if(user.getContestRating()==null)
			user.setContestRating(0);
		user.setRating(user.getProblemRating() + user.getContestRating());
		userService.update(user);
	}
	
	private void hduProRating(){
		HduUserInfo userInfo = new HduUserInfo(user);
		userInfo.run();
		//HDU信息在doGet中已经更改
		
		//防止空指针异常，赋初值
		if(user.getHduuser().getHduSolve()==null)
			user.getHduuser().setHduSolve(0);
		if(user.getHduuser().getHduSubmission()==null)
			user.getHduuser().setHduSubmission(0);
		//更改User信息
		solved = solved + user.getHduuser().getHduSolve();
		submissions = submissions + user.getHduuser().getHduSubmission();
	}
	private void pojProRating(){
		PojUserInfo pojInfo = new PojUserInfo(user);
		pojInfo.run();
		//Poj信息从doGet中已经更改
		
		//防止空指针异常，赋初值
		if(user.getPojuser().getPojSolved()==null)
			user.getPojuser().setPojSolved(0);
		if(user.getPojuser().getPojSubmission()==null)
			user.getPojuser().setPojSubmission(0);
		//更改User信息
		solved = solved + user.getPojuser().getPojSolved();
		submissions = submissions + user.getPojuser().getPojSubmission();
	}
	
	private void vjudgeProRating(){
		VjudegeUserInfo vjudgeInfo = new VjudegeUserInfo(user);
		vjudgeInfo.run();
		//Vjudge信息从doGet中已经更改
		//防止空指针异常，赋初值
		if(user.getVjudgeuser().getVjudgeSolved()==null)
			user.getVjudgeuser().setVjudgeSolved(0);
		if(user.getVjudgeuser().getVjudgeSubmission()==null)
			user.getVjudgeuser().setVjudgeSubmission(0);
		//更改User信息
		solved = solved + user.getVjudgeuser().getVjudgeSolved();
		submissions = submissions + user.getVjudgeuser().getVjudgeSubmission();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		this.ComputeAmount();
		this.Compute();
	}
}
