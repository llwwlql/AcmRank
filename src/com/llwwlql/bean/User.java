package com.llwwlql.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private Vjudgeuser vjudgeuser;
	private Pojuser pojuser;
	private Hduuser hduuser;
	private Integer rank;
	private String userName;
	private String nickName;
	private String password;
	private String userBlog;
	private String motto;
	private Integer solved;
	private Integer submissions;
	private Integer rating;
	private String userType;
	private Set contestusers = new HashSet(0);
	private Set logs = new HashSet(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String userName, String nickName, String password,
			Integer solved, Integer rating) {
		this.userName = userName;
		this.nickName = nickName;
		this.password = password;
		this.solved = solved;
		this.rating = rating;
	}

	/** full constructor */
	public User(Vjudgeuser vjudgeuser, Pojuser pojuser, Hduuser hduuser,
			Integer rank, String userName, String nickName, String password,
			String userBlog, String motto, Integer solved, Integer rating,
			String userType, Set contestusers, Set logs) {
		this.vjudgeuser = vjudgeuser;
		this.pojuser = pojuser;
		this.hduuser = hduuser;
		this.rank = rank;
		this.userName = userName;
		this.nickName = nickName;
		this.password = password;
		this.userBlog = userBlog;
		this.motto = motto;
		this.solved = solved;
		this.rating = rating;
		this.userType = userType;
		this.contestusers = contestusers;
		this.logs = logs;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vjudgeuser getVjudgeuser() {
		return this.vjudgeuser;
	}

	public void setVjudgeuser(Vjudgeuser vjudgeuser) {
		this.vjudgeuser = vjudgeuser;
	}

	public Pojuser getPojuser() {
		return this.pojuser;
	}

	public void setPojuser(Pojuser pojuser) {
		this.pojuser = pojuser;
	}

	public Hduuser getHduuser() {
		return this.hduuser;
	}

	public void setHduuser(Hduuser hduuser) {
		this.hduuser = hduuser;
	}

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserBlog() {
		return this.userBlog;
	}

	public void setUserBlog(String userBlog) {
		this.userBlog = userBlog;
	}

	public String getMotto() {
		return this.motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public Integer getSolved() {
		return this.solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Set getContestusers() {
		return this.contestusers;
	}

	public void setContestusers(Set contestusers) {
		this.contestusers = contestusers;
	}

	public Set getLogs() {
		return this.logs;
	}

	public void setLogs(Set logs) {
		this.logs = logs;
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

	@Override
	public String toString() {
		return "userName:" + userName + "\tPassword:" + password + "    "
				+ this.pojuser;
	}
}