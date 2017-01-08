package com.llwwlql.computeRanting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;

import com.llwwlql.bean.Contestuser;
import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.tool.Property;
import com.llwwlql.tool.SaveLog;

public class CPRating implements BaseCompute,Runnable{

	private int solved=0;
	private int submissions=0;
	private int rating=0;
	@ManyToOne
	private User user = null;
	
	public CPRating() {
	}
	
	public CPRating(User user) {
		this.user = user;
	}

	
	
	/**
	 * @return the solved
	 */
	public int getSolved() {
		return solved;
	}

	/**
	 * @param solved the solved to set
	 */
	public void setSolved(int solved) {
		this.solved = solved;
	}

	/**
	 * @return the submissions
	 */
	public int getSubmissions() {
		return submissions;
	}

	/**
	 * @param submissions the submissions to set
	 */
	public void setSubmissions(int submissions) {
		this.submissions = submissions;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

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

	/**
	 * @return the cpAmount
	 */
	public void Compute() {
		BaseService<User> userService = new BaseService<User>();
		if(user.getCpRating()==null)
			user.setCpRating(0);
		if(this.rating>user.getCpRating())
		{
			SaveLog log = new SaveLog(user, this.getRating()-user.getCpRating(), (short)3);
			log.Save();
			user.setCpRating(this.rating);
		}
		userService.update(user);
	}
	
	public void CPAmount(){
		if(user.getHduuser()!=null)
		{
			this.hduCPRating();
		}
		this.rating = this.solved*1;
	}
	
	private void hduCPRating()
	{
		BaseService<Contestuser> cuService = new BaseService<Contestuser>();
		String hduNickName = user.getHduuser().getHduNickName();
		List<Contestuser> contestUsers = cuService.getByParameter("Contestuser", "userName", hduNickName);
		for (Contestuser contestuser : contestUsers) {
			this.solved+=contestuser.getSolved();
			this.submissions +=contestuser.getSubmissions();
		}
	}

	public void run() {
		this.CPAmount();
		this.Compute();
	}
}
