package com.llwwlql.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Pojuser entity. @author MyEclipse Persistence Tools
 */

public class Pojuser implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String pojUserName;
	private Integer pojSolved;
	private Integer pojSubmission;
	private Set users = new HashSet(0);

	// Constructors

	/** default constructor */
	public Pojuser() {
	}

	/** minimal constructor */
	public Pojuser(User user, String pojUserName) {
		this.user = user;
		this.pojUserName = pojUserName;
	}

	/** full constructor */
	public Pojuser(User user, String pojUserName, Integer pojSolved,
			Integer pojSubmission, Set users) {
		this.user = user;
		this.pojUserName = pojUserName;
		this.pojSolved = pojSolved;
		this.pojSubmission = pojSubmission;
		this.users = users;
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

	public String getPojUserName() {
		return this.pojUserName;
	}

	public void setPojUserName(String pojUserName) {
		this.pojUserName = pojUserName;
	}

	public Integer getPojSolved() {
		return this.pojSolved;
	}

	public void setPojSolved(Integer pojSolved) {
		this.pojSolved = pojSolved;
	}

	public Integer getPojSubmission() {
		return this.pojSubmission;
	}

	public void setPojSubmission(Integer pojSubmission) {
		this.pojSubmission = pojSubmission;
	}

	public Set getUsers() {
		return this.users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "PojUserName:" + this.pojUserName + "\tSolve:"+this.pojSolved + "\tSubmission:" + this.pojSubmission;
	}

}