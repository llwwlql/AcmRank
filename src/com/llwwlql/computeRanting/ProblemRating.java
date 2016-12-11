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
		BaseService<User> userService = new BaseService<User>();
		if(user.getHduuser()!=null)
		{
			BaseService<Hduuser> hduService = new BaseService<Hduuser>();
			HduUserInfo userInfo = new HduUserInfo(user.getHduuser());
			userInfo.doGet();
			//����HduUser��Ϣ
			hduService.update(user.getHduuser());
			//����User��Ϣ
			solved = solved + user.getHduuser().getHduSolve();
			submissions = submissions + user.getHduuser().getHduSubmission();
		}
		if(user.getPojuser()!=null)
		{
			BaseService<Pojuser> pojService = new BaseService<Pojuser>();
			PojUserInfo pojInfo = new PojUserInfo(user.getPojuser());
			pojInfo.doGet();
			//����PojUser��Ϣ
			pojService.update(user.getPojuser());
			//����User��Ϣ
			solved = solved + user.getPojuser().getPojSolved();
			submissions = submissions + user.getPojuser().getPojSubmission();			
		}
		//�������ݿ���User��Ϣ
		user.setSolved(solved);
		user.setSubmissions(submissions);
		userService.update(user);
	}
	
	/**
	 * �ú������ڼ���Rating
	 */
	public void Compute() {
		BaseService<User> userService = new BaseService<User>();
		
		rating = rating + user.getSolved();
		
		//�������ݿ���User��Ϣ
		user.setRating(rating);
		userService.update(user);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		this.ComputeAmount();
		this.Compute();
	}

}
