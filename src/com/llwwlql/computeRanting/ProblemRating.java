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
	 * ���캯��������User����
	 * 
	 * @param user
	 */
	public ProblemRating(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}

	/**
	 * ��������OJ�����Ŀ���� ÿ��oj��������ͳ�ƣ����жϸ��û��Ƿ��и�oj�˺ţ�����д������Ӳ���룬���ʺ���չ 
	 * problem����������
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
		//���Ķ�����User��Ϣ
		this.solved = this.solved + user.getCpRating();
		user.setSolved(solved);
		user.setSubmissions(submissions);
	}
	/**
	 * �ú������ڼ���Rating
	 */
	public void Compute() {
		BaseService<User> userService = new BaseService<User>();
		
		//��Ŀ���㷽ʽ
		rating = user.getSolved()*1;
		//�������ݿ���User��Ϣ
		user.setProblemRating(this.rating);
		if(user.getContestRating()==null)
			user.setContestRating(0);
		user.setRating(user.getProblemRating() + user.getContestRating());
		userService.update(user);
	}
	
	private void hduProRating(){
		HduUserInfo userInfo = new HduUserInfo(user);
		userInfo.run();
		//HDU��Ϣ��doGet���Ѿ�����
		
		//��ֹ��ָ���쳣������ֵ
		if(user.getHduuser().getHduSolve()==null)
			user.getHduuser().setHduSolve(0);
		if(user.getHduuser().getHduSubmission()==null)
			user.getHduuser().setHduSubmission(0);
		//����User��Ϣ
		solved = solved + user.getHduuser().getHduSolve();
		submissions = submissions + user.getHduuser().getHduSubmission();
	}
	private void pojProRating(){
		PojUserInfo pojInfo = new PojUserInfo(user);
		pojInfo.run();
		//Poj��Ϣ��doGet���Ѿ�����
		
		//��ֹ��ָ���쳣������ֵ
		if(user.getPojuser().getPojSolved()==null)
			user.getPojuser().setPojSolved(0);
		if(user.getPojuser().getPojSubmission()==null)
			user.getPojuser().setPojSubmission(0);
		//����User��Ϣ
		solved = solved + user.getPojuser().getPojSolved();
		submissions = submissions + user.getPojuser().getPojSubmission();
	}
	
	private void vjudgeProRating(){
		VjudegeUserInfo vjudgeInfo = new VjudegeUserInfo(user);
		vjudgeInfo.run();
		//Vjudge��Ϣ��doGet���Ѿ�����
		//��ֹ��ָ���쳣������ֵ
		if(user.getVjudgeuser().getVjudgeSolved()==null)
			user.getVjudgeuser().setVjudgeSolved(0);
		if(user.getVjudgeuser().getVjudgeSubmission()==null)
			user.getVjudgeuser().setVjudgeSubmission(0);
		//����User��Ϣ
		solved = solved + user.getVjudgeuser().getVjudgeSolved();
		submissions = submissions + user.getVjudgeuser().getVjudgeSubmission();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		this.ComputeAmount();
		this.Compute();
	}
}
