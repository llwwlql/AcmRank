package com.llwwlql.bean;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * Contestuser entity. @author MyEclipse Persistence Tools
 */

public class Contestuser implements java.io.Serializable {

	// Fields

	private Integer id;
	private transient Contest contest;
	private User user;
	private String userName;
	private Integer rank;
	private Integer solved;
	private Integer submissions;
	private String penalty;
	private Set<Contestproblem> contestproblems = new HashSet<Contestproblem>(0);	

	// Constructors

	/** default constructor */
	public Contestuser() {
	}
	
	public Contestuser(Contest contest,User user){
		this.contest = contest;
		this.user = user;
	}

	/** minimal constructor */
	public Contestuser(Contest contest, User user, String userName,
			Integer rank, Integer solved, String penalty) {
		this.contest = contest;
		this.user = user;
		this.userName = userName;
		this.rank = rank;
		this.solved = solved;
		this.penalty = penalty;
	}
	
	/** full constructor */
	public Contestuser(Contest contest, User user, String userName,
			Integer rank, Integer solved, Integer submissions,String penalty, Set contestproblems) {
		this.contest = contest;
		this.user = user;
		this.userName = userName;
		this.rank = rank;
		this.solved = solved;
		this.submissions = submissions;
		this.penalty = penalty;
		this.contestproblems = contestproblems;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Contest getContest() {
		return this.contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getSolved() {
		return this.solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	public String getPenalty() {
		return this.penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public Set getContestproblems() {
		return this.contestproblems;
	}

	public void setContestproblems(Set contestproblems) {
		this.contestproblems = contestproblems;
	}

	/**
	 * @return the submissions
	 */
	public Integer getSubmissions() {
		return submissions;
	}

	/**
	 * @param submissions the submissions to set
	 */
	public void setSubmissions(Integer submissions) {
		this.submissions = submissions;
	}
}