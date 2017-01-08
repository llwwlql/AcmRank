package com.llwwlql.bean;

/**
 * Log entity. @author MyEclipse Persistence Tools
 */

public class Log implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Integer score;
	private short origin;
	private String time;

	// Constructors

	/** default constructor */
	public Log() {
	}

	/** full constructor */
	public Log(User user, Integer score, short origin, String time) {
		this.user = user;
		this.score = score;
		this.origin = origin;
		this.time = time;
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

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public short getOrigin() {
		return this.origin;
	}

	public void setOrigin(short origin) {
		this.origin = origin;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "score:" + score + "\ttime:"+time;
	}
}