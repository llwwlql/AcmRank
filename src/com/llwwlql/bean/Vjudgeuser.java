package com.llwwlql.bean;

/**
 * Vjudgeuser entity. @author MyEclipse Persistence Tools
 */

public class Vjudgeuser implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String vjudgeUserName;
	private String vjudgeNickName;
	private Integer vjudgeSolved;
	private Integer vjudgeSubmission;
	private short vjudgeType;

	// Constructors

	/** default constructor */
	public Vjudgeuser() {
	}

	/** minimal constructor */
	public Vjudgeuser(User user) {
		this.user = user;
	}
	
	/** minimal constructor */
	public Vjudgeuser(User user, String vjudgeUserName) {
		this.user = user;
		this.vjudgeUserName = vjudgeUserName;
	}

	/** full constructor */
	public Vjudgeuser(User user, String vjudgeUserName, Integer vjudgeSolved,
			Integer vjudgeSubmission) {
		this.user = user;
		this.vjudgeUserName = vjudgeUserName;
		this.vjudgeSolved = vjudgeSolved;
		this.vjudgeSubmission = vjudgeSubmission;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getVjudgeUserName() {
		return this.vjudgeUserName;
	}

	public void setVjudgeUserName(String vjudgeUserName) {
		this.vjudgeUserName = vjudgeUserName;
	}

	public Integer getVjudgeSolved() {
		return this.vjudgeSolved;
	}

	public void setVjudgeSolved(Integer vjudgeSolved) {
		this.vjudgeSolved = vjudgeSolved;
	}

	public Integer getVjudgeSubmission() {
		return this.vjudgeSubmission;
	}

	public void setVjudgeSubmission(Integer vjudgeSubmission) {
		this.vjudgeSubmission = vjudgeSubmission;
	}

	/**
	 * @return the vjudgeType
	 */
	public short getVjudgeType() {
		return vjudgeType;
	}

	/**
	 * @param vjudgeType the vjudgeType to set
	 */
	public void setVjudgeType(short vjudgeType) {
		this.vjudgeType = vjudgeType;
	}

	/**
	 * @return the vjudgeNickName
	 */
	public String getVjudgeNickName() {
		return vjudgeNickName;
	}

	/**
	 * @param vjudgeNickName the vjudgeNickName to set
	 */
	public void setVjudgeNickName(String vjudgeNickName) {
		this.vjudgeNickName = vjudgeNickName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vjudgeuser [id=" + id + ", user=" + user + ", vjudgeUserName="
				+ vjudgeUserName + ", vjudgeNickName=" + vjudgeNickName
				+ ", vjudgeSolved=" + vjudgeSolved + ", vjudgeSubmission="
				+ vjudgeSubmission + ", vjudgeType=" + vjudgeType + "]";
	}
	

}