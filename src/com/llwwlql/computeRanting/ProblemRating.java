package com.llwwlql.computeRanting;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.llwwlql.bean.User;
import com.llwwlql.service.BaseService;
import com.llwwlql.spider.user.HduUserInfo;
import com.llwwlql.spider.user.PojUserInfo;
import com.llwwlql.spider.user.VjudgeUserInfo;

@Entity
public class ProblemRating implements Runnable, BaseCompute {

	private int solved = 0;
	private int submissions = 0;
	private int rating = 0;
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
	 * 
	 * 
	 * @param user
	 */
	public ProblemRating(User user) {
		this.user = user;
	}

	/**
	 * 
	 * problem
	 */
	public void ComputeAmount() {
		if (user.getHduuser() != null
				&& !user.getHduuser().getHduUserName().equals("")) {
			this.hduProRating();
		}
		if (user.getPojuser() != null
				&& !user.getPojuser().getPojUserName().equals("")) {
			this.pojProRating();
		}
		if (user.getVjudgeuser() != null
				&& !user.getVjudgeuser().getVjudgeUserName().equals("")) {
			this.vjudgeProRating();
		}
		this.solved = this.solved + user.getCpRating();
		user.setSolved(solved);
		user.setSubmissions(submissions);
	}

	/**
	 * 计算Rating
	 */
	public void Compute() {
		BaseService<User> userService = new BaseService<User>();

		rating = user.getSolved() * 1;
		user.setProblemRating(this.rating);
		if (user.getContestRating() == null)
			user.setContestRating(0);
		user.setRating(user.getProblemRating() + user.getContestRating());
		userService.update(user);
	}

	private void hduProRating() {
		HduUserInfo userInfo = new HduUserInfo(user);
		userInfo.run();
		if (user.getHduuser().getHduSolve() == null)
			user.getHduuser().setHduSolve(0);
		if (user.getHduuser().getHduSubmission() == null)
			user.getHduuser().setHduSubmission(0);
		solved = solved + user.getHduuser().getHduSolve();
		submissions = submissions + user.getHduuser().getHduSubmission();
	}

	private void pojProRating() {
		PojUserInfo pojInfo = new PojUserInfo(user);
		pojInfo.run();

		if (user.getPojuser().getPojSolved() == null)
			user.getPojuser().setPojSolved(0);
		if (user.getPojuser().getPojSubmission() == null)
			user.getPojuser().setPojSubmission(0);

		solved = solved + user.getPojuser().getPojSolved();
		submissions = submissions + user.getPojuser().getPojSubmission();
	}

	private void vjudgeProRating() {
		VjudgeUserInfo vjudgeInfo = new VjudgeUserInfo(user);
		vjudgeInfo.run();
		if (user.getVjudgeuser().getVjudgeSolved() == null)
			user.getVjudgeuser().setVjudgeSolved(0);
		if (user.getVjudgeuser().getVjudgeSubmission() == null)
			user.getVjudgeuser().setVjudgeSubmission(0);
		solved = solved + user.getVjudgeuser().getVjudgeSolved();
		submissions = submissions + user.getVjudgeuser().getVjudgeSubmission();
	}

	public void run() {
		this.ComputeAmount();
		this.Compute();
	}
}
